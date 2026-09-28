async function loadDonors() {
  try {
    const donors = await apiGet('/donors');
    const table = document.getElementById('donorTable');
    setSelectOptions('itemDonorId', donors, donor => `${donor.name} (#${donor.id})`, 'Select donor', 'Create a donor first');
    updateItemPrerequisites();

    if (!donors.length) {
      table.innerHTML = '<p>No donors found.</p>';
      return;
    }

    table.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Actions</th></tr></thead>
        <tbody>
          ${donors.map(d => `
            <tr>
              <td>${d.id}</td>
              <td>${d.name}</td>
              <td>${d.email}</td>
              <td>${d.phone}</td>
              <td>
                <button class="secondary" onclick="editDonor(${d.id})">Edit</button>
                <button class="secondary" onclick="deleteDonor(${d.id})">Delete</button>
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

async function submitDonorForm(event) {
  event.preventDefault();
  const payload = {
    name: document.getElementById('donorName').value,
    email: document.getElementById('donorEmail').value,
    phone: document.getElementById('donorPhone').value
  };

  try {
    const id = document.getElementById('donorId').value;
    if (id) {
      await apiPut(`/donors/${id}`, payload);
      showToast('Donor updated');
    } else {
      await apiPost('/donors', payload);
      showToast('Donor created');
    }
    event.target.reset();
    document.getElementById('donorId').value = '';
    await loadDonors();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

async function deleteDonor(id) {
  if (!confirm('Delete donor?')) return;
  try {
    await apiDelete(`/donors/${id}`);
    showToast('Donor deleted');
    await loadDonors();
    await loadDashboardStats();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

function editDonor(id) {
  apiGet(`/donors/${id}`).then((donor) => {
    document.getElementById('donorId').value = donor.id;
    document.getElementById('donorName').value = donor.name;
    document.getElementById('donorEmail').value = donor.email;
    document.getElementById('donorPhone').value = donor.phone;
    document.querySelector('[data-section="donors"]').click();
  });
}

document.getElementById('donorForm').addEventListener('submit', submitDonorForm);
