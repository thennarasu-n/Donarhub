const apiBase = '/api';

async function fetchJson(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  });

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.error || 'Request failed');
  }

  return response.status === 204 ? null : response.json();
}

async function loadDrives() {
  try {
    const drives = await fetchJson(`${apiBase}/drives`);
    const list = document.getElementById('driveList');

    if (!drives.length) {
      list.innerHTML = '<p class="small">No drives yet.</p>';
      return;
    }

    list.innerHTML = drives.map(drive => `
      <div class="drive-card">
        <h3>${drive.name}</h3>
        <div class="small">ID: ${drive.id}</div>
        <div class="small">Dates: ${drive.startDate} to ${drive.endDate}</div>
        <div class="small">Items: ${drive.items ? drive.items.length : 0}</div>
      </div>
    `).join('');
  } catch (error) {
    document.getElementById('driveList').innerHTML = `<p class="small">${error.message}</p>`;
  }
}

async function createDrive(event) {
  event.preventDefault();
  const data = {
    name: document.getElementById('driveName').value,
    startDate: document.getElementById('startDate').value,
    endDate: document.getElementById('endDate').value
  };

  try {
    await fetchJson(`${apiBase}/drives`, {
      method: 'POST',
      body: JSON.stringify(data)
    });
    event.target.reset();
    await loadDrives();
  } catch (error) {
    alert(error.message);
  }
}

async function addItem(event) {
  event.preventDefault();
  const driveId = document.getElementById('driveId').value;
  const data = {
    donorName: document.getElementById('donorName').value,
    itemName: document.getElementById('itemName').value,
    category: document.getElementById('category').value,
    condition: document.getElementById('condition').value
  };

  try {
    await fetchJson(`${apiBase}/drives/${driveId}/items`, {
      method: 'POST',
      body: JSON.stringify(data)
    });
    event.target.reset();
    await loadDrives();
  } catch (error) {
    alert(error.message);
  }
}

async function loadSummary() {
  const driveId = document.getElementById('summaryDriveId').value;
  if (!driveId) {
    alert('Please enter a drive ID');
    return;
  }

  try {
    const summary = await fetchJson(`${apiBase}/drives/${driveId}/summary`);
    document.getElementById('summaryResult').innerHTML = `
      <strong>Drive:</strong> ${summary.driveName}<br>
      <strong>Total Items:</strong> ${summary.totalItems}<br>
      <strong>Collected:</strong> ${summary.collected}<br>
      <strong>Distributed:</strong> ${summary.distributed}<br>
      <strong>Remaining Stock:</strong> ${summary.remainingStock}
    `;
  } catch (error) {
    document.getElementById('summaryResult').innerHTML = error.message;
  }
}

document.getElementById('driveForm').addEventListener('submit', createDrive);
document.getElementById('itemForm').addEventListener('submit', addItem);
document.getElementById('summaryButton').addEventListener('click', loadSummary);
loadDrives();
