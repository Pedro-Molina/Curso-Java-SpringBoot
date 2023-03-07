// Call the dataTables jQuery plugin
$(document).ready(function() {
});

async function login() {
    let data = {};
    data.email= document.getElementById('txtInputEmail').value;
    data.password = document.getElementById('txtInputPassword').value;

    const request = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type' : 'application/json'
    },
    body: JSON.stringify(data)
    });
    const answer = await request.text(); //aca se recive el token
    if (answer != "FAIL"){
        localStorage.token = answer;
        localStorage.email = data.email;
        window.location.href = 'user.html'
    }
    else{
        alert ("Las credenciales son incorrectas. Intente nuevamente");
    }
}
