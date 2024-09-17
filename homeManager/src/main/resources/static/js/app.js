const API_URL = 'http://localhost:8080'; // Ajusta según tu configuración

// Login functionality
document.getElementById('loginForm')?.addEventListener('submit', async (event) => {
  event.preventDefault();
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  try {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
      window.location.href = 'dashboard.html';
    } else {
      document.getElementById('loginError').innerText = 'Invalid credentials';
    }
  } catch (error) {
    console.error('Login failed:', error);
  }
});

// Fetch user info for dashboard
if (document.getElementById('userInfo')) {
  (async () => {
    try {
      const response = await fetch(`${API_URL}/homemanager/user/current-user`, {
        credentials: 'include',
      });

      if (response.ok) {
        const user = await response.json();
        document.getElementById('userInfo').innerText = `Welcome, ${user.username}!`;
      } else {
        window.location.href = 'login.html';
      }
    } catch (error) {
      console.error('Failed to fetch user info:', error);
    }
  })();
}

// Fetch and manage casas
if (document.getElementById('createCasaForm')) {
  document.getElementById('createCasaForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const name = document.getElementById('casaName').value;

    try {
      const response = await fetch(`${API_URL}/homemanager/casa`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ name }),
      });

      if (response.ok) {
        alert('Casa created successfully');
        // Refresh list of casas or redirect
      } else {
        alert('Failed to create casa');
      }
    } catch (error) {
      console.error('Failed to create casa:', error);
    }
  });
}

// Similarly, implement functionality for tasks and invitations
