async function loadDashboardStats() {
  try {
    const stats = await apiGet('/dashboard/stats');
    const container = document.getElementById('statsGrid');
    const items = [
      ['Total Drives', stats.totalDrives],
      ['Total Donors', stats.totalDonors],
      ['Total Items', stats.totalItems],
      ['Total Collected', stats.totalCollected],
      ['Total Distributed', stats.totalDistributed],
      ['Undistributed', stats.totalUndistributed],
      ['Books', stats.totalBooks],
      ['Clothes', stats.totalClothes]
    ];

    container.innerHTML = items.map(([label, value]) => `
      <div class="stat-card">
        <h3>${label}</h3>
        <div class="value">${value}</div>
      </div>
    `).join('');

    document.getElementById('summaryTable').innerHTML = `
      <table>
        <tr><th>Metric</th><th>Count</th></tr>
        <tr><td>Total Donation Drives</td><td>${stats.totalDrives}</td></tr>
        <tr><td>Total Donors</td><td>${stats.totalDonors}</td></tr>
        <tr><td>Total Donated Items</td><td>${stats.totalItems}</td></tr>
        <tr><td>Total Collected</td><td>${stats.totalCollected}</td></tr>
        <tr><td>Total Distributed</td><td>${stats.totalDistributed}</td></tr>
        <tr><td>Total Undistributed</td><td>${stats.totalUndistributed}</td></tr>
      </table>
    `;
  } catch (error) {
    showToast(error.message, 'error');
  }
}
