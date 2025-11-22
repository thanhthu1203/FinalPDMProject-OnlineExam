
let token = null;

document.getElementById('adminLogin').addEventListener('submit', async (e) => {
  e.preventDefault();

  const id = document.getElementById('adminId').value.trim();
  const pw = document.getElementById('adminPassword').value;
  const msgEl = document.getElementById('adminMessage');

  msgEl.textContent = '';

  if (!id || !pw) {
    msgEl.textContent = 'Please enter Admin ID and Password';
    return;
  }

  try {
    console.log('Admin login attempt for:', id);

    const res = await fetch('/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ studentId: id, password: pw })
    });

    console.log('Admin login response status:', res.status);

    if (!res.ok) {
      msgEl.textContent = `Login failed (Error: ${res.status})`;
      return;
    }

    const data = await res.json();
    console.log('Admin login response:', data);

    if (data.token) {
      token = data.token;
      msgEl.textContent = `Logged in as ${data.id} (${data.role})`;
      msgEl.style.color = 'green';
      document.getElementById('adminPanel').style.display = 'block';
      loadStudents();
      loadExams();
    } else {
      msgEl.textContent = 'Login failed - no token received';
    }
  } catch (error) {
    console.error('Admin login error:', error);
    msgEl.textContent = 'Connection error';
  }
});

async function authFetch(url, opts = {}) {
  opts.headers = opts.headers || {};
  if (token) {
    opts.headers['Authorization'] = 'Bearer ' + token;
  }

  try {
    const res = await fetch(url, opts);
    if (res.status === 401) {
      alert('Unauthorized - please login again');
      token = null;
      document.getElementById('adminPanel').style.display = 'none';
    }
    return res;
  } catch (error) {
    console.error('Auth fetch error:', error);
    throw error;
  }
}

/* Students */
document.getElementById('refreshStudents').addEventListener('click', loadStudents);

async function loadStudents() {
  try {
    console.log('Loading students...');
    const res = await authFetch('/api/admin/students');

    if (!res.ok) {
      console.error('Failed to load students:', res.status);
      return;
    }

    const students = await res.json();
    console.log('Loaded students:', students);

    const tbody = document.querySelector('#studentsTable tbody');
    tbody.innerHTML = '';

    if (!students || students.length === 0) {
      tbody.innerHTML = '<tr><td colspan="4" style="text-align:center">No students found</td></tr>';
      return;
    }

    for (const s of students) {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${s.studentId || '-'}</td>
        <td>${s.sName || '-'}</td>
        <td>${s.phone || '-'}</td>
        <td><button data-id="${s.studentId}" class="del-stu btn-primary">Delete</button></td>
      `;
      tbody.appendChild(tr);
    }

    document.querySelectorAll('.del-stu').forEach(btn => {
      btn.addEventListener('click', async (e) => {
        const id = e.target.dataset.id;
        if (!confirm('Delete student ' + id + '?')) return;

        try {
          const res = await authFetch('/api/admin/students/' + encodeURIComponent(id), {
            method: 'DELETE'
          });

          if (res.ok) {
            console.log('Student deleted:', id);
            loadStudents();
          } else {
            alert('Failed to delete student');
          }
        } catch (error) {
          console.error('Error deleting student:', error);
          alert('Error deleting student');
        }
      });
    });
  } catch (error) {
    console.error('Error loading students:', error);
  }
}

document.getElementById('createStudentForm').addEventListener('submit', async (e) => {
  e.preventDefault();

  const id = document.getElementById('newStudentId').value.trim();
  const name = document.getElementById('newStudentName').value.trim();
  const pw = document.getElementById('newStudentPassword').value;

  if (!id || !name || !pw) {
    alert('Please fill all fields');
    return;
  }

  try {
    const res = await authFetch('/api/admin/students', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ studentId: id, sName: name, password: pw })
    });

    if (res.ok) {
      console.log('Student created:', id);
      document.getElementById('newStudentId').value = '';
      document.getElementById('newStudentName').value = '';
      document.getElementById('newStudentPassword').value = '';
      loadStudents();
    } else {
      const errorText = await res.text();
      console.error('Failed to create student:', errorText);
      alert('Failed to create student: ' + errorText);
    }
  } catch (error) {
    console.error('Error creating student:', error);
    alert('Error creating student');
  }
});

/* Exams */
document.getElementById('refreshExams').addEventListener('click', loadExams);

async function loadExams() {
  try {
    console.log('Loading exams...');
    const res = await authFetch('/api/admin/exams');

    if (!res.ok) {
      console.error('Failed to load exams:', res.status);
      return;
    }

    const exams = await res.json();
    console.log('Loaded exams:', exams);

    const tbody = document.querySelector('#examsTable tbody');
    tbody.innerHTML = '';

    if (!exams || exams.length === 0) {
      tbody.innerHTML = '<tr><td colspan="5" style="text-align:center">No exams found</td></tr>';
      return;
    }

    for (const e of exams) {
      const tr = document.createElement('tr');
      const courseName = e.course ? (e.course.courseId || e.course.courseName || '') : '';
      tr.innerHTML = `
        <td>${e.examId || '-'}</td>
        <td>${courseName}</td>
        <td>${e.testDateDATETIME || '-'}</td>
        <td>${e.roomNumber || '-'}</td>
        <td><button data-id="${e.examId}" class="del-exam btn-primary">Delete</button></td>
      `;
      tbody.appendChild(tr);
    }

    document.querySelectorAll('.del-exam').forEach(btn => {
      btn.addEventListener('click', async (ev) => {
        const id = ev.target.dataset.id;
        if (!confirm('Delete exam ' + id + '?')) return;

        try {
          const res = await authFetch('/api/admin/exams/' + encodeURIComponent(id), {
            method: 'DELETE'
          });

          if (res.ok) {
            console.log('Exam deleted:', id);
            loadExams();
          } else {
            alert('Failed to delete exam');
          }
        } catch (error) {
          console.error('Error deleting exam:', error);
          alert('Error deleting exam');
        }
      });
    });
  } catch (error) {
    console.error('Error loading exams:', error);
  }
}

document.getElementById('createExamForm').addEventListener('submit', async (e) => {
  e.preventDefault();

  const payload = {
    examId: document.getElementById('newExamId').value.trim(),
    courseId: document.getElementById('newExamCourse').value.trim(),
    eType: document.getElementById('newExamType').value.trim(),
    testDateDATETIME: document.getElementById('newExamDate').value.trim(),
    roomNumber: document.getElementById('newExamRoom').value.trim(),
    duration: document.getElementById('newExamDuration').value.trim()
  };

  if (!payload.examId || !payload.courseId) {
    alert('Please fill required fields (Exam ID, Course ID)');
    return;
  }

  try {
    const res = await authFetch('/api/admin/exams', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (res.ok) {
      console.log('Exam created:', payload.examId);
      document.getElementById('createExamForm').reset();
      loadExams();
    } else {
      const errorText = await res.text();
      console.error('Failed to create exam:', errorText);
      alert('Failed to create exam: ' + errorText);
    }
  } catch (error) {
    console.error('Error creating exam:', error);
    alert('Error creating exam');
  }
});