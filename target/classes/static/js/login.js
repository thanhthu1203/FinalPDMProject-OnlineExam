/*document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();

  const studentId = document.getElementById('studentId').value.trim();
  const password = document.getElementById('password').value;
  const msg = document.getElementById('message');

  // Clear previous messages
  msg.textContent = '';
  msg.style.color = '#b00020';

  // Validate inputs
  if (!studentId || !password) {
    msg.textContent = 'Please enter both Student ID and Password';
    return;
  }

  try {
    console.log('Attempting login for:', studentId);

    const res = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ studentId, password })
    });

    console.log('Response status:', res.status);

    if (!res.ok) {
      msg.textContent = `Server error: ${res.status}`;
      return;
    }

    const data = await res.json();
    console.log('Response data:', data);

    if (data.success) {
      msg.textContent = 'Login successful! Redirecting...';
      msg.style.color = 'green';
      setTimeout(() => {
        window.location.href = `/dashboard.html?id=${encodeURIComponent(data.studentId)}`;
      }, 500);
    } else {
      msg.textContent = data.message || 'Login failed';
    }
  } catch (error) {
    console.error('Login error:', error);
    msg.textContent = 'Connection error. Please check if the server is running.';
  }
});*/