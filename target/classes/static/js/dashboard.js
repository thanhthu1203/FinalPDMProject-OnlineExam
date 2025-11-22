function getQueryParam(name) {
  const url = new URL(window.location.href);
  return url.searchParams.get(name);
}

async function loadDashboard() {
  const studentId = getQueryParam('id');
  const welcomeEl = document.getElementById('welcome');

  if (!studentId) {
    welcomeEl.textContent = 'Student ID missing';
    welcomeEl.style.color = 'red';
    return;
  }

  welcomeEl.textContent = `Welcome, ${studentId}`;

  try {
    console.log('Loading dashboard for student:', studentId);

    const res = await fetch(`/api/student/${encodeURIComponent(studentId)}/dashboard`);

    console.log('Dashboard response status:', res.status);

    if (!res.ok) {
      welcomeEl.textContent = `Failed to load data (Error: ${res.status})`;
      welcomeEl.style.color = 'red';
      return;
    }

    const exams = await res.json();
    console.log('Loaded exams:', exams);

    const tbody = document.querySelector('#examTable tbody');
    tbody.innerHTML = '';

    if (!exams || exams.length === 0) {
      const tr = document.createElement('tr');
      const td = document.createElement('td');
      td.colSpan = 6;
      td.textContent = 'No exams found';
      td.style.textAlign = 'center';
      td.style.padding = '20px';
      tr.appendChild(td);
      tbody.appendChild(tr);
      return;
    }

    exams.sort((a, b) => {
      const dateA = a.examDate ? new Date(a.examDate) : new Date(0);
      const dateB = b.examDate ? new Date(b.examDate) : new Date(0);
      return dateA - dateB;
    });

    const now = new Date();

    for (const ex of exams) {
      const tr = document.createElement('tr');

      const tdId = document.createElement('td');
      tdId.textContent = ex.examId || '-';

      const tdCourse = document.createElement('td');
      tdCourse.textContent = ex.courseName || '-';

      const tdType = document.createElement('td');
      tdType.textContent = ex.eType || '-';

      const tdDate = document.createElement('td');
      if (ex.examDate) {
        try {
          const d = new Date(ex.examDate);
          tdDate.textContent = d.toLocaleString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
          });
        } catch (e) {
          tdDate.textContent = ex.examDate;
        }
      } else {
        tdDate.textContent = '-';
      }

      const tdRoom = document.createElement('td');
      tdRoom.textContent = ex.room || '-';

      const tdStatus = document.createElement('td');
      if (ex.score !== null && ex.score !== undefined) {
        const span = document.createElement('span');
        span.className = 'score-pill';
        span.textContent = `Score: ${ex.score}`;
        tdStatus.appendChild(span);
      } else {
        if (ex.examDate && new Date(ex.examDate) > now) {
          const span = document.createElement('span');
          span.className = 'badge green';
          span.textContent = 'Upcoming';
          tdStatus.appendChild(span);
        } else {
          const span = document.createElement('span');
          span.className = 'badge blue';
          span.textContent = 'No Score';
          tdStatus.appendChild(span);
        }
      }

      tr.appendChild(tdId);
      tr.appendChild(tdCourse);
      tr.appendChild(tdType);
      tr.appendChild(tdDate);
      tr.appendChild(tdRoom);
      tr.appendChild(tdStatus);

      tbody.appendChild(tr);
    }
  } catch (error) {
    console.error('Error loading dashboard:', error);
    welcomeEl.textContent = 'Error loading dashboard';
    welcomeEl.style.color = 'red';
  }
}

document.addEventListener('DOMContentLoaded', loadDashboard);
