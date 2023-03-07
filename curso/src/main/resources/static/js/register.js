// Call the dataTables jQuery plugin
$(document).ready(function() {
});

async function registerUser() {
    let data = {};
    data.nombre = document.getElementById('txtFirstName').value;
    data.apellido = document.getElementById('txtLastName').value;
    data.email= document.getElementById('txtInputEmail').value;
    data.password = document.getElementById('txtInputPassword').value;

    let repetirPassword = document.getElementById('txtRepeatPassword').value;

    if (repetirPassword != data.password ){
        alert ('Las contraseñas no coinciden');
        return;
    }
    const request = await fetch('api/users', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type' : 'application/json'
    },
    body: JSON.stringify(data)
    });

    alert("La cuenta fue creada con exito");
    window.location.href = 'login.html'
}
