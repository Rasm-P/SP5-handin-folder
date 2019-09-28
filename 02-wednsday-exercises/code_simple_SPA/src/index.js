import 'bootstrap/dist/css/bootstrap.css'



        doWithFetchAll(funcAll);
function fetchWithErrorCheck(res) {
    if (!res.ok) {
        return Promise.reject({status: res.status, fullError: res.json()})
    }
    return res.json();
}




function funcAll(arr) {
    document.getElementById("table").innerHTML = toHTMLTable(arr);

}
function doWithFetchAll(callback) {
    fetch("http://localhost:3333/api/users")
            .then(res => res.json())
            .then(data => {
                data;
                callback(data);
            });
}
function toHTMLTable(arr) {
    var result = arr.map(user => "<tr>" + "<th>" + user.id + "</th><th>" + user.age + "</th><th>" + user.name + "</th><th>" + user.gender + "</th><th>" + user.email + "</th>" + "</tr>").join("");
    return "<thead><th>Id</th><th>Age</th><th>Name</th><th>Gender</th><th>Email</th></thead>" + result;
}
function clickAll() {
    doWithFetchAll(funcAll);
}
var click = document.getElementById("btncall");
click.onclick = clickAll;




function toHTMLSingleTable(user) {
    var result = "<tr>" + "<th>" + user.id + "</th><th>" + user.age + "</th><th>" + user.name + "</th><th>" + user.gender + "</th><th>" + user.email + "</th>" + "</tr>";
    return "<thead><th>Id</th><th>Age</th><th>Name</th><th>Gender</th><th>Email</th></thead>" + result;
}
function funcId(user) {
    document.getElementById("table").innerHTML = toHTMLSingleTable(user);

}
function doWithFetchId(callback) {
    fetch("http://localhost:3333/api/users/" + document.getElementById("addId").value)
            .then(res => res.json())
            .then(data => {
                data;
                callback(data);
            });
}
function clickGetById() {
    doWithFetchId(funcId);
}
var click1 = document.getElementById("subId");
click1.onclick = clickGetById;




function cleanUp() {
    document.getElementById("addNewName").value = "Name"
    document.getElementById("addNewAge").value = "Age"
    document.getElementById("addNewGender").value = "Gender"
    document.getElementById("addNewEmail").value = "Email"
    document.getElementById("addUpdateUser").value = "Add ID to update user!"
    document.getElementById("addDeleteUser").value = "Add ID to delete user!"
    doWithFetchAll(funcAll);
}
function doWithFetchNewUser() {
    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById("addNewName").value,
            age: parseInt(document.getElementById("addNewAge").value, 10),
            gender: document.getElementById("addNewGender").value,
            email: document.getElementById("addNewEmail").value
        })
    }
    console.log(options)
    fetch("http://localhost:3333/api/users/", options)
            .then(res => fetchWithErrorCheck(res))
            .then(data => console.log(data.name))
            .catch(err => {
                if (err.status) {
                    err.fullError.then(e => console.log(e.detail))
                } else {
                    console.log("Network error");
                }
            });
    doWithFetchAll(funcAll);
    cleanUp();
}
var click2 = document.getElementById("subNewUser");
click2.onclick = doWithFetchNewUser;




function doWithFetchUpdateUser() {
    let options = {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById("addNewName").value,
            age: parseInt(document.getElementById("addNewAge").value, 10),
            gender: document.getElementById("addNewGender").value,
            email: document.getElementById("addNewEmail").value
        })
    }
    fetch("http://localhost:3333/api/users/" + document.getElementById("addUpdateUser").value, options)
            .then(res => fetchWithErrorCheck(res))
            .then(data => console.log(data.name))
            .catch(err => {
                if (err.status) {
                    err.fullError.then(e => console.log(e.detail))
                } else {
                    console.log("Network error");
                }
            });
    doWithFetchAll(funcAll);
    cleanUp();
}
var click3 = document.getElementById("subUpdateUser");
click3.onclick = doWithFetchUpdateUser;




function doWithFetchdeleteUser() {
    let options = {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    }
    fetch("http://localhost:3333/api/users/" + document.getElementById("addDeleteUser").value, options)
            .then(res => fetchWithErrorCheck(res))
            .then(data => console.log(data.name))
            .catch(err => {
                if (err.status) {
                    err.fullError.then(e => console.log(e.detail))
                } else {
                    console.log("Network error");
                }
            });
    doWithFetchAll(funcAll);
    cleanUp();
}
var click4 = document.getElementById("subDeleteUser");
click4.onclick = doWithFetchdeleteUser;




//function clickNorth() {
//    var dir = document.getElementById("north").id;
//    document.getElementById("direction").innerHTML = dir;
//}
//document.getElementById("north").onclick = clickNorth;
//function clickEast() {
//    var dir = document.getElementById("east").id;
//    document.getElementById("direction").innerHTML = dir;
//}
//document.getElementById("east").onclick = clickEast;
//function clickSouth() {
//    var dir = document.getElementById("south").id;
//    document.getElementById("direction").innerHTML = dir;
//}
//document.getElementById("south").onclick = clickSouth;
//function clickWest() {
//    var dir = document.getElementById("west").id;
//    document.getElementById("direction").innerHTML = dir;
//}
//document.getElementById("west").onclick = clickWest;




//        setInterval(function () {
//            clickAll();
//        }, 1000 * 60 * 60);
//function funcAll(data) {
//    document.getElementById("divId").innerHTML = data.joke;
//
//}
//function doWithFetchAll(callback) {
//    fetch("https://studypoints.info/jokes/api/jokes/period/hour")
//            .then(res => res.json())
//            .then(data => {
//                console.log(data)
//                data;
//                callback(data);
//            });
//}
//function clickAll() {
//    doWithFetchAll(funcAll);
//}
//document.getElementById("btn1").onclick = clickAll;




//import jokes from "./jokes";
//
//const allJokes = jokes.getJokes().map(joke => "<li>"+joke+"</li>");
//document.getElementById("jokes").innerHTML = allJokes.join("");

//function clickJokeById() {
//    var id = document.getElementById("addJoke").value;
//    document.getElementById("jokeId").innerHTML = jokes.getJokeById(id);
//    document.getElementById("addJoke").value = "";
//}
//var click = document.getElementById("subJoke");
//click.onclick = clickJokeById;
//
//function clickAddJoke() {
//    var joke = document.getElementById("addJoke1").value;
//    jokes.addJoke(joke);
//    document.getElementById("addJoke1").value = "";
//}
//var click1 = document.getElementById("subJoke1");
//click1.onclick = clickAddJoke;