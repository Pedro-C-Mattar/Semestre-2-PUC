// Small helper to check server-side session and get current user
async function isLogged() {
  try {
    // include credentials so the session cookie is sent
    const res = await fetch('/usuario/me', { credentials: 'same-origin' });
    return res.ok;
  } catch (e) {
    return false;
  }
}

async function getCurrentUser() {
  try {
    const res = await fetch('/usuario/me', { credentials: 'same-origin' });
    if (!res.ok) return null;
    return await res.json();
  } catch (e) {
    return null;
  }
}

// optional helper to logout via POST (returns true on success)
async function logout() {
  try {
    const res = await fetch('/usuario/logout', { method: 'POST', credentials: 'same-origin' });
    return res.ok;
  } catch (e) {
    return false;
  }
}

// export for module-like usage in older browsers
window.sessionHelpers = {
  isLogged,
  getCurrentUser,
  logout
};
