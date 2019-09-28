doWithFetchAll(funcAll);
function cleanUp() {
    doWithFetchAll(funcAll);
    document.getElementById("addId").value = "Id"
    document.getElementById("userId").value = "User ID for update"
    document.getElementById("addNewUser").value = "Name"
    document.getElementById("addUpdateUser").value = "Add ID to update user!"
    document.getElementById("addDeleteUser").value = "Add ID to delete user!"
}




function funcAll(arr) {
    document.getElementById("table").innerHTML = toHTMLTable(arr);

}
function doWithFetchAll(callback) {
    fetch("http://localhost:8080/CORSwithJavaAndJax-rs/api/generic/all")
            .then(res => res.json())
            .then(data => {
                data;
                callback(data);
            });
}
function toHTMLTable(arr) {
    var result = arr.map(user => "<tr>" + "<th>" + user + "</th>" + "<tr>").join("");
    return "<thead><th>Name</th></thead>" + result;
}
function clickAll() {
    doWithFetchAll(funcAll);
}
var click = document.getElementById("btncall");
click.onclick = clickAll;




function toHTMLSingleTable(user) {
    var result = "<tr>" + "<th>" + user + "</th>" + "<tr>";
    return "<thead><th>Name</th></thead>" + result;
}
function funcId(user) {
    document.getElementById("table").innerHTML = toHTMLSingleTable(user);

}
function doWithFetchId(callback) {
    fetch("http://localhost:8080/CORSwithJavaAndJax-rs/api/generic/" + document.getElementById("addId").value)
            .then(res => res.json())
            .then(data => {
                data;
                callback(data);
            });
    cleanUp()
}
function clickGetById() {
    doWithFetchId(funcId);
}
var click1 = document.getElementById("subId");
click1.onclick = clickGetById;





function doWithFetchNewUser() {
    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById("addNewUser").value
        })
    }
    console.log(options)
    fetch("http://localhost:8080/CORSwithJavaAndJax-rs/api/generic/add/", options)
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
            name: document.getElementById("addUpdateUser").value
        })
    }
    fetch("http://localhost:8080/CORSwithJavaAndJax-rs/api/generic/" + document.getElementById("userId").value, options)
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
    fetch("http://localhost:8080/CORSwithJavaAndJax-rs/api/generic/" + document.getElementById("addDeleteUser").value, options)
    cleanUp();
}
var click4 = document.getElementById("subDeleteUser");
click4.onclick = doWithFetchdeleteUser;