async function loadDrives() {
  try {
    const drives = await apiGet('/drives');
    const table = document.getElementById('driveTable');
    setSelectOptions('itemDriveId', drives, drive => `${drive.name} (#${drive.id})`, 'Select drive', 'Create a drive first');
    updateItemPrerequisites();

    if (!drives.length) {
      table.innerHTML = '<p>No drives found.</p>';
      return;
    }

    table.innerHTML = `
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Start</th>
            <th>End</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          ${drives.map(d => `
            <tr>
              <td>${d.id}</td>
              <td>${d.name}</td>
              <td>${d.description || '-'}</td>
              <td>${d.startDate}</td>
              <td>${d.endDate}</td>
              <td><span class="badge">${d.status}</span></td>
              <td>
                <button class="secondary" onclick="editDrive(${d.id})">Edit</button>
                <button class="secondary" onclick="deleteDrive(${d.id})">Delete</button>
              </td>
            </tr>
          `).join('')}
        </tbody>
      </table>
    `;
  } catch (error) {
    showToast(error.message, 'error');
  }
}

async function submitDriveForm(event) {
  event.preventDefault();
  const payload = {
    name: document.getElementById('driveName').value,
    description: document.getElementById('driveDescription').value,
    startDate: document.getElementById('driveStartDate').value,
    endDate: document.getElementById('driveEndDate').value,
    status: document.getElementById('driveStatus').value
  };

  try {
    const driveId = document.getElementById('driveId').value;
    if (driveId) {
      await apiPut(`/drives/${driveId}`, payload);
      showToast('Drive updated successfully');
    } else {
      await apiPost('/drives', payload);
      showToast('Drive created successfully');
    }

    event.target.reset();
    document.getElementById('driveId').value = '';
    await loadDrives();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

async function deleteDrive(id) {
  if (!confirm('Delete drive?')) return;
  try {
    await apiDelete(`/drives/${id}`);
    showToast('Drive deleted');
    await loadDrives();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

function editDrive(id) {
  apiGet(`/drives/${id}`).then((drive) => {
    document.getElementById('driveId').value = drive.id;
    document.getElementById('driveName').value = drive.name;
    document.getElementById('driveDescription').value = drive.description || '';
    document.getElementById('driveStartDate').value = drive.startDate;
    document.getElementById('driveEndDate').value = drive.endDate;
    document.getElementById('driveStatus').value = drive.status;
    document.querySelector('[data-section="drives"]').click();
  });
}

document.getElementById('driveForm').addEventListener('submit', submitDriveForm);
