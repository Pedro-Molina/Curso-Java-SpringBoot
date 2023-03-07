// Call the dataTables jQuery plugin
$(document).ready(function() {
    loadUsers();
  $('#users').DataTable();
  refreshUserEmail();
});

function refreshUserEmail(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}
async function loadUsers() {

    const request = await fetch('api/users', {
    method: 'GET',
    headers: getHeaders()
    });
    const users = await request.json();

    let listadoHtml ='';
    for (let user of users){
            let deleteButton = '<a href="#" onclick="deleteUser('+user.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
            let userHTML = '<tr> <td>'+user.id+'</td><td>'+user.nombre+' '+user.apellido+'</td><td>'+user.email+'</td><td>'+user.telefono+'</td><td>'+deleteButton+'</td></tr>';
            listadoHtml+= userHTML;
    }

    console.log(users);

    document.querySelector('#users tbody').outerHTML = listadoHtml;
}
function getHeaders(){
    return {
                     'Accept': 'application/json',
                     'Content-Type' : 'application/json',
                     'Authorization': localStorage.token
                   }
}

async function deleteUser (id){
    if (!confirm('Desea eliminar este usuario?')){
        return;
    }
    const request = await fetch('api/users/'+id, {//toma informacion de la API, que toma informacino de la base de datos
        method: 'DELETE',
        headers: getHeaders()
        });
    location.reload();
}