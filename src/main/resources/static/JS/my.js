$(document).ready(function () {
    allReadUsers();
});

function allReadUsers() {
    fetch("/admin/users")
        .then(res => res.json())
        .then(userslist => {
            let output = ``
            if (userslist.length > 0) {
                userslist.forEach(user => {
                    let rolesList = "";
                    user.authorities.forEach(role => rolesList += role.name + " ")
                    output += `<tr>
                  <td>${user.id}</td>
                  <td>${user.name}</td>
                  <td>${user.lastname}</td>
                  <td>${user.username}</td>
                  <td>${user.age}</td>
                  <td>${rolesList}</td>
                  <td>
                    <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-outline-secondary"
                    data-toggle="modal" data-target="#someDefaultModal"></button>
                  </td>
                  <td>
                    <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-outline-danger"
                    data-toggle="modal" data-target="#someDefaultModal"></button>
                  </td>
                </tr>`
                })
                document.getElementById("userslist").innerHTML = output;
            }
        })
}

function createUser() {
    let firstname = document.getElementById("newfirstName")
    let lastname = document.getElementById("newlastName")
    let age = document.getElementById("newAge")
    let username = document.getElementById("newusername")
    let password = document.getElementById("newpassword")

    let user = {
        name: firstname,
        lastname: lastname,
        age: age,
        username: username,
        password: password
    }
    fetch("/admin/users")
        // , {
        // method: 'POST',
        // body: JSON.stringify(user),
        // headers: {
        //     'Content-Type': 'application/json'
        // }
    // })

        .then(
            JSON.stringify(user)
                .then(() => allReadUsers())
}
