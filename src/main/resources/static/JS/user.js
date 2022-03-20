$(document).ready(function () {
    readUserByUsername();
});
let url = "/user/" + document.getElementById("urlTo").getAttribute("value")

function readUserByUsername() {
    fetch(url)
        .then(res => res.json())
        .then(user => {
            let output = ``
            let rolesList = ""
            user.authorities.forEach(role => rolesList += role.name + " ")
            output +=`<tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.lastname}</td>
                        <td>${user.age}</td>
                        <td>${user.username}</td>
                        <td>${rolesList}</td>
                        </tr>`
            document.getElementById("userReport").innerHTML = output;
        })
}