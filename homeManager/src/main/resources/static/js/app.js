document.addEventListener('DOMContentLoaded', () => {

    // Create Casa
    document.getElementById('createCasaForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const nombre = document.getElementById('nombre').value;
        const response = await fetch('/casa', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nombre })
        });
        const result = await response.json();
        alert(`Casa created: ${JSON.stringify(result)}`);
    });

    // Get Casa
    document.getElementById('getCasaForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = document.getElementById('getCasaId').value;
        const response = await fetch(`/casa/${id}`);
        const result = await response.json();
        document.getElementById('casaDetails').innerText = JSON.stringify(result, null, 2);
    });

    // Add Miembro
    document.getElementById('addMiembroForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const casaId = document.getElementById('addMiembroId').value;
        const userId = document.getElementById('userId').value;
        const response = await fetch(`/casa/addMiembro/${casaId}?idUser=${userId}`, {
            method: 'PUT'
        });
        const result = await response.json();
        alert(`Miembro added: ${JSON.stringify(result)}`);
    });

    // Delete Casa
    document.getElementById('deleteCasaForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = document.getElementById('deleteCasaId').value;
        await fetch(`/casa/${id}`, {
            method: 'DELETE'
        });
        alert('Casa deleted');
    });

});
