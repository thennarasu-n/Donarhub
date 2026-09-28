const API_BASE = '/api';

async function fetchJson(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options
  });

  let data = null;
  const contentType = response.headers.get('content-type') || '';
  if (contentType.includes('application/json')) {
    data = await response.json();
  }

  if (!response.ok) {
    const message = data?.message || data?.error || 'Request failed';
    throw new Error(message);
  }

  return data;
}

function showToast(message, type = 'success') {
  const toast = document.getElementById('toast');
  toast.textContent = message;
  toast.classList.add('show');
  toast.style.background = type === 'error' ? '#b91c1c' : '#111827';
  setTimeout(() => toast.classList.remove('show'), 2500);
}

async function apiGet(path) {
  return fetchJson(`${API_BASE}${path}`);
}

async function apiPost(path, body) {
  return fetchJson(`${API_BASE}${path}`, { method: 'POST', body: JSON.stringify(body) });
}

async function apiPut(path, body) {
  return fetchJson(`${API_BASE}${path}`, { method: 'PUT', body: JSON.stringify(body) });
}

async function apiDelete(path) {
  return fetchJson(`${API_BASE}${path}`, { method: 'DELETE' });
}

function setSelectOptions(selectId, records, label, placeholder, emptyPlaceholder = placeholder) {
  const select = document.getElementById(selectId);
  if (!select) return;

  const selectedValue = select.value;
  const prompt = records.length ? placeholder : emptyPlaceholder;
  select.innerHTML = `<option value="">${prompt}</option>${records.map(record =>
    `<option value="${record.id}">${label(record)}</option>`
  ).join('')}`;

  if (records.some(record => String(record.id) === selectedValue)) {
    select.value = selectedValue;
  }
}
