document.querySelectorAll('.nav-btn').forEach((button) => {
  button.addEventListener('click', () => {
    document.querySelectorAll('.nav-btn').forEach((btn) => btn.classList.remove('active'));
    document.querySelectorAll('.section').forEach((section) => section.classList.remove('active'));
    button.classList.add('active');
    const target = button.getAttribute('data-section');
    document.getElementById(target).classList.add('active');
    document.getElementById('pageTitle').textContent = button.textContent.trim();
  });
});

document.getElementById('todayLabel').textContent = new Intl.DateTimeFormat(undefined, {
  weekday: 'short', month: 'long', day: 'numeric', year: 'numeric'
}).format(new Date());

async function initializeApp() {
  await loadDashboardStats();
  await loadDrives();
  await loadDonors();
  await loadItems();
  await loadRecipients();
  await loadDistributions();
  await loadUndistributedStock();
}

async function loadUndistributedStock() {
  try {
    const items = await apiGet('/items/undistributed');
    const table = document.getElementById('stockTable');
    if (!items.length) {
      table.innerHTML = '<p>No undistributed items.</p>';
      return;
    }

    table.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Category</th><th>Condition</th><th>Drive ID</th><th>Donor ID</th></tr></thead>
        <tbody>
          ${items.map(item => `
            <tr>
              <td>${item.id}</td>
              <td>${item.category}</td>
              <td>${item.condition}</td>
              <td>${item.drive?.id ?? '-'}</td>
              <td>${item.donor?.id ?? '-'}</td>
            </tr>
          `).join('')}
        </tbody>
      </table>
    `;
  } catch (error) {
    showToast(error.message, 'error');
  }
}

initializeApp();
