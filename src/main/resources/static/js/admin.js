let token = null;

document.getElementById('adminLogin').addEventListener('submit', async (e) => {
  e.preventDefault();
  const id = document.getElementById('adminId').value.trim();
  const pw = document.getElementById('adminPassword').value;
  const res = await fetch('/api/auth/login', {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ studentId: id, password: pw })
  });
  if (!res.ok) {
    document.getElementById('adminMessage').textContent = 'Login failed';
    return;
  }
  const data = await res.json();
  if (data.token) {
    token = data.token;
    document.getElementById('adminMessage').textContent = 'Logged in as ' + data.id + ' ('+data.role+')';
    document.getElementById('adminPanel').style.display = 'block';
    loadStudents();
    loadExams();
  } else {
    document.getElementById('adminMessage').textContent = 'Login failed';
  }
});

async function authFetch(url, opts = {}) {
  opts.headers = opts.headers || {};
  if (token) opts.headers['Authorization'] = 'Bearer ' + token;
  const res = await fetch(url, opts);
  if (res.status === 401) {
    alert('Unauthorized - please login again');
  }
  return res;
}

/* Students */
document.getElementById('refreshStudents').addEventListener('click', loadStudents);
async function loadStudents() {
  const res = await authFetch('/api/admin/students');
  if (!res.ok) return;
  const students = await res.json();
  const tbody = document.querySelector('#studentsTable tbody');
  tbody.innerHTML = '';
  for (const s of students) {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${s.studentId}</td><td>${s.sName||''}</td><td>${s.phone||''}</td>
      <td><button data-id="${s.studentId}" class="del-stu">Delete</button></td>`;
    tbody.appendChild(tr);
  }
  document.querySelectorAll('.del-stu').forEach(btn => btn.addEventListener('click', async (e) => {
    const id = e.target.dataset.id;
    if (!confirm('Delete student ' + id + '?')) return;
    await authFetch('/api/admin/students/' + encodeURIComponent(id), { method: 'DELETE' });
    loadStudents();
  }));
}

document.getElementById('createStudentForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const id = document.getElementById('newStudentId').value.trim();
  const name = document.getElementById('newStudentName').value;
  const pw = document.getElementById('newStudentPassword').value;
  const res = await authFetch('/api/admin/students', {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ studentId: id, sName: name, password: pw })
  });
  if (res.ok) {
    loadStudents();
  } else {
    alert('Failed to create student');
  }
});

/* Exams */
document.getElementById('refreshExams').addEventListener('click', loadExams);
async function loadExams() {
  const res = await authFetch('/api/admin/exams');
  if (!res.ok) return;
  const exams = await res.json();
  const tbody = document.querySelector('#examsTable tbody');
  tbody.innerHTML = '';
  for (const e of exams) {
    const tr = document.createElement('tr');
    const courseName = e.course ? (e.course.courseId || '') : '';
    tr.innerHTML = `<td>${e.examId}</td><td>${courseName}</td><td>${e.testDateDATETIME||''}</td><td>${e.roomNumber||''}</td>
      <td><button data-id="${e.examId}" class="del-exam">Delete</button></td>`;
    tbody.appendChild(tr);
  }
  document.querySelectorAll('.del-exam').forEach(btn => btn.addEventListener('click', async (ev) => {
    const id = ev.target.dataset.id;
    if (!confirm('Delete exam ' + id + '?')) return;
    await authFetch('/api/admin/exams/' + encodeURIComponent(id), { method: 'DELETE' });
    loadExams();
  }));
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
  const res = await authFetch('/api/admin/exams', {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify(payload)
  });
  if (res.ok) {
    loadExams();
  } else {
    alert('Failed to create exam');
  }
});