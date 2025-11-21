document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();
  const studentId = document.getElementById('studentId').value.trim();
  const password = document.getElementById('password').value;

  const res = await fetch('/api/login', {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ studentId, password })
  });

  const data = await res.json();
  const msg = document.getElementById('message');
  if (data.success) {
    window.location.href = `/dashboard.html?id=${encodeURIComponent(data.studentId)}`;
  } else {
    msg.textContent = data.message || 'Login failed';
  }
});