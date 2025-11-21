function getQueryParam(name) {
  const url = new URL(window.location.href);
  return url.searchParams.get(name);
}

async function loadDashboard() {
  const studentId = getQueryParam('id');
  if (!studentId) {
    document.getElementById('welcome').textContent = 'Student ID missing';
    return;
  }
  document.getElementById('welcome').textContent = `Welcome, ${studentId}`;

  const res = await fetch(`/api/student/${encodeURIComponent(studentId)}/dashboard`);
  if (!res.ok) {
    document.getElementById('welcome').textContent = 'Failed to load data';
    return;
  }
  const exams = await res.json();
  const tbody = document.querySelector('#examTable tbody');
  tbody.innerHTML = '';

  exams.sort((a,b) => new Date(a.examDate) - new Date(b.examDate));

  const now = new Date();

  for (const ex of exams) {
    const tr = document.createElement('tr');

    const tdId = document.createElement('td'); tdId.textContent = ex.examId || '-';
    const tdCourse = document.createElement('td'); tdCourse.textContent = ex.courseName || '-';
    const tdType = document.createElement('td'); tdType.textContent = ex.eType || '-';

    const tdDate = document.createElement('td');
    if (ex.examDate) {
      const d = new Date(ex.examDate);
      tdDate.textContent = d.toLocaleString();
    } else {
      tdDate.textContent = '-';
    }

    const tdRoom = document.createElement('td'); tdRoom.textContent = ex.room || '-';

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
}

document.addEventListener('DOMContentLoaded', loadDashboard);