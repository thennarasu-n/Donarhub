async function loadRecipients() {
  try {
    const recipients = await apiGet('/recipients');
    const table = document.getElementById('recipientTable');
    setSelectOptions('distributionRecipientId', recipients, recipient => `${recipient.organization} (#${recipient.id})`, 'Select recipient');

    if (!recipients.length) {
      table.innerHTML = '<p>No recipients found.</p>';
      return;
    }

    table.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Name</th><th>Organization</th><th>Address</th><th>Phone</th><th>Actions</th></tr></thead>
        <tbody>
          ${recipients.map(r => `
            <tr>
              <td>${r.id}</td>
              <td>${r.name}</td>
              <td>${r.organization}</td>
              <td>${r.address}</td>
              <td>${r.phone || '-'}</td>
              <td>
                <button class="secondary" onclick="editRecipient(${r.id})">Edit</button>
                <button class="secondary" onclick="deleteRecipient(${r.id})">Delete</button>
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

async function submitRecipientForm(event) {
  event.preventDefault();
  const payload = {
    name: document.getElementById('recipientName').value,
    organization: document.getElementById('recipientOrganization').value,
    address: document.getElementById('recipientAddress').value,
    phone: document.getElementById('recipientPhone').value
  };

  try {
    const id = document.getElementById('recipientId').value;
    if (id) {
      await apiPut(`/recipients/${id}`, payload);
      showToast('Recipient updated');
    } else {
      await apiPost('/recipients', payload);
      showToast('Recipient added');
    }
    event.target.reset();
    document.getElementById('recipientId').value = '';
    await loadRecipients();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

async function deleteRecipient(id) {
  if (!confirm('Delete recipient?')) return;
  try {
    await apiDelete(`/recipients/${id}`);
    showToast('Recipient deleted');
    await loadRecipients();
  } catch (error) {
    showToast(error.message, 'error');
  }
}

function editRecipient(id) {
  apiGet(`/recipients/${id}`).then((recipient) => {
    document.getElementById('recipientId').value = recipient.id;
    document.getElementById('recipientName').value = recipient.name;
    document.getElementById('recipientOrganization').value = recipient.organization;
    document.getElementById('recipientAddress').value = recipient.address;
    document.getElementById('recipientPhone').value = recipient.phone || '';
    document.querySelector('[data-section="recipients"]').click();
  });
}

document.getElementById('recipientForm').addEventListener('submit', submitRecipientForm);
