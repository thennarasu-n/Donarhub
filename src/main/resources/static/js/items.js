function updateItemPrerequisites() {
  const donorMissing = document.getElementById('itemDonorId').options.length === 1;
  const driveMissing = document.getElementById('itemDriveId').options.length === 1;
  const note = document.getElementById('itemPrerequisiteNote');
  const saveButton = document.getElementById('saveItemButton');
  const links = [];

  if (donorMissing) links.push('<button type="button" data-open-section="donors">Add a donor</button>');
  if (driveMissing) links.push('<button type="button" data-open-section="drives">Create a drive</button>');

  note.hidden = links.length === 0;
  note.innerHTML = links.length ? `Inventory needs ${links.join(' and ')} first.` : '';
  saveButton.disabled = links.length > 0;
  note.querySelectorAll('[data-open-section]').forEach(button => {
    button.addEventListener('click', () => {
      document.querySelector(`[data-section="${button.dataset.openSection}"]`).click();
    });
  });
}

async function loadItems(category = '') {
  try {
    const items = await apiGet(category ? `/items/category/${category}` : '/items');
    const availableItems = await apiGet('/items/undistributed');
    const table = document.getElementById('itemTable');
    setSelectOptions('distributionItemId', availableItems, item => `${item.category} / ${item.condition} (#${item.id})`, 'Select collected item');

    if (!items.length) {
      table.innerHTML = '<p>No items found.</p>';
      return;
    }

    table.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Category</th><th>Condition</th><th>Collected</th><th>Drive ID</th><th>Donor ID</th><th>Date</th><th>Actions</th></tr></thead>
        <tbody>
          ${items.map(item => `
            <tr>
              <td>${item.id}</td>
              <td>${item.category}</td>
              <td>${item.condition}</td>
              <td><span class="badge">${item.collected ? 'YES' : 'NO'}</span></td>
              <td>${item.drive ? item.drive.id : item.driveId || '-'}</td>
              <td>${item.donor ? item.donor.id : item.donorId || '-'}</td>
              <td>${item.donatedDate}</td>
              <td>
                <button class="secondary" onclick="editItem(${item.id})">Edit</button>
                <button class="secondary" onclick="deleteItem(${item.id})">Delete</button>
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

async function submitItemForm(event) {
  event.preventDefault();
  const payload = {
    category: document.getElementById('itemCategory').value,
    condition: document.getElementById('itemCondition').value,
    donatedDate: document.getElementById('itemDate').value,
    donor: { id: Number(document.getElementById('itemDonorId').value) },
    drive: { id: Number(document.getElementById('itemDriveId').value) },
    collected: true
  };

  try {
    const id = document.getElementById('itemId').value;
    if (id) {
      await apiPut(`/items/${id}`, payload);
      showToast('Item updated');
    } else {
      await apiPost('/items', payload);
      showToast('Item logged successfully');
    }
    event.target.reset();
    document.getElementById('itemId').value = '';
    await loadItems();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

async function deleteItem(id) {
  if (!confirm('Delete item?')) return;
  try {
    await apiDelete(`/items/${id}`);
    showToast('Item deleted');
    await loadItems();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

function editItem(id) {
  apiGet(`/items/${id}`).then((item) => {
    document.getElementById('itemId').value = item.id;
    document.getElementById('itemCategory').value = item.category;
    document.getElementById('itemCondition').value = item.condition;
    document.getElementById('itemDonorId').value = item.donor ? item.donor.id : '';
    document.getElementById('itemDriveId').value = item.drive ? item.drive.id : '';
    document.getElementById('itemDate').value = item.donatedDate;
    document.querySelector('[data-section="items"]').click();
  });
}

document.getElementById('itemForm').addEventListener('submit', submitItemForm);
document.getElementById('itemFilterBtn').addEventListener('click', () => {
  const category = document.getElementById('itemCategoryFilter').value;
  loadItems(category);
});
