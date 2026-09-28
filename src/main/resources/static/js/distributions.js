async function loadDistributions() {
  try {
    const distributions = await apiGet('/distributions');
    const table = document.getElementById('distributionTable');
    if (!distributions.length) {
      table.innerHTML = '<p>No distributions found.</p>';
      return;
    }
    table.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Item ID</th><th>Recipient ID</th><th>Distributed At</th></tr></thead>
        <tbody>
          ${distributions.map(d => `
            <tr>
              <td>${d.id}</td>
              <td>${d.item?.id ?? d.itemId}</td>
              <td>${d.recipient?.id ?? d.recipientId}</td>
              <td>${d.distributedAt}</td>
            </tr>
          `).join('')}
        </tbody>
      </table>
    `;
  } catch (error) {
    showToast(error.message, 'error');
  }
}

async function submitDistributionForm(event) {
  event.preventDefault();
  const payload = {
    itemId: Number(document.getElementById('distributionItemId').value),
    recipientId: Number(document.getElementById('distributionRecipientId').value)
  };

  try {
    await apiPost('/distributions', payload);
    showToast('Item distributed successfully');
    event.target.reset();
    await loadDistributions();
    await loadItems();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

document.getElementById('distributionForm').addEventListener('submit', submitDistributionForm);
