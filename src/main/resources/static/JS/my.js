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
                                <td>${user.age}</td>
                                <td>${user.username}</td>
                                <td>${rolesList}</td>
                                <td><button class="btn btn-primary" id="editButton" data-toggle="modal" data-target="#editModal" onclick="editModal(${user.id})">Edit</button></td>
                                <td><button type="button" class="btn btn-danger" id="deleteButton" data-toggle="modal" data-target="#deleteModal" onclick="deleteModal(${user.id})">Delete</button></td>
                                </tr>`
                })
                document.getElementById("userslist").innerHTML = output;
            }
        })

}




function updateUser() {

    let id = document.getElementById("editUserId").value;
    let name = document.getElementById("editFirstname").value;
    let lastname = document.getElementById("editLastname").value;
    let age = document.getElementById("editAge").value;
    let username = document.getElementById("editLogin").value;
    let password = document.getElementById("editPassword").value;
    let roles = getUserRole(Array.from(document.getElementById("editRoles").selectedOptions).map(options => options.value))

    let user = {
        id: id,
        name: name,
        age: age,
        lastname: lastname,
        username: username,
        password: password,
        roles: roles
    }

    fetch('/admin/users/' + id, {
        method:"PUT",
        headers: {
            "Accept": "application/json",
            "Content-type":"application/json"
        },
        body:JSON.stringify(user)
    })
        .then(() => allReadUsers())
}

function editModal(id) {
    fetch("/admin/users/" + id)
        .then(res => res.json())
        .then(user => {
            $("#editUserId").attr("value", user.id)
            $("#editFirstname").attr("value", user.name)
            $("#editAge").attr("value", user.age)
            $("#editLastname").attr("value", user.lastname)
            $("#editLogin").attr("value", user.username)
            $("#editPassword").attr("value", user.password)
            $("#editRoles").attr("value", user.roles)
        })
}
function refreshTable() {
    let table = document.getElementById('userslist')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    setTimeout(allReadUsers, 10);
}
function getUserRole(setRole) {
    let roles = []

    if (setRole.indexOf("ROLE_ADMIN") !== -1) {
        roles.push({id: 1, name: "ROLE_ADMIN", authority: "ROLE_ADMIN"})
        alert("ADMIN")

    }else if (setRole.indexOf("ROLE_USER") !== -1) {
        roles.push({id: 2, name: "ROLE_USER", authority: "ROLE_USER"})
        alert("USER")

    }
    return roles

}

function deleteModal(id) {
    fetch('/admin/users/' + id)
        .then(res => res.json())
        .then(user => {
            $('#deleteUserId').attr('value', user.id)
            $('#deleteUserFirstname').attr('value', user.name)
            $('#deleteUserLastname').attr('value', user.lastname)
            $('#deleteUserLogin').attr('value', user.username)
            $('#deleteUserAge').attr('value', user.age)
            $('#deleteUserPassword').attr('value', user.password)
            $('#deleteRoles').attr('value', user.roles)
        })
}

function deleteUser() {
    let id = document.getElementById("deleteUserId").value;
    let name = document.getElementById("deleteUserFirstname").value;
    let lastname = document.getElementById("deleteUserLastname").value;
    let age = document.getElementById("deleteUserAge").value;
    let username = document.getElementById("deleteUserLogin").value;
    let password = document.getElementById("deleteUserPassword").value;

    let user = {
        id: id,
        firstName: name,
        age: age,
        lastname: lastname,
        username: username,
        password: password,
    }
    fetch('admin/users/' + id, {
        method:"DELETE",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }})
        .then(() => allReadUsers())
    refreshTable();
}

function createUser() {

    let firstname = document.getElementById("newFirstname").value
    let lastname = document.getElementById("newLastname").value
    let age = document.getElementById("newUserAge").value
    let username = document.getElementById("newUsername").value
    let password = document.getElementById("newPassword").value
    let roles = getUserRole(Array.from(document.getElementById("newRoles").selectedOptions).map(options => options.value))

    let user = {
        name: firstname,
        lastname: lastname,
        age: age,
        username: username,
        password: password,
        roles: roles
    }

    fetch("/admin/users", {
        method: "POST",
        headers: {
            // "Accept": "application/json",
            "Content-type": "application/json"
        },
        body: JSON.stringify(user),
    })
        .then(() => allReadUsers())
}