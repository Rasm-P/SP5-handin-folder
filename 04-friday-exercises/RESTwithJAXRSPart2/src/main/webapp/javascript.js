doWithFetchAll(funcAll);
function funcAll(arr) {
    document.getElementById("table").innerHTML = toHTMLTable(arr);

}
function doWithFetchAll(callback) {
    fetch("http://localhost:8080/jpareststarter/api/persons/all")
            .then(res => res.json())
            .then(data => {
                data;
                callback(data);
            });
}
function toHTMLTable(arr) {
    var result = arr.map(person => "<tr>" + "<th>" + person.id + "</th><th>" + person.firstName + "</th><th>" + person.lastName + "</th><th>" + person.phone + "</th><th>" + "<a href=\"#\" class=\"btndelete\" id=\"d" + person.id + "\">delete</a>" + " / " + "<a href=\"#\" class=\"btndelete\" id=\"e" + person.id + "\"data-toggle=\"modal\" data-target=\"#myEdit\">edit</a>" + "</th>" + "</tr>").join("");
    return "<table id=\"table\" class='table'>" + "<thead><th>ID</th><th>First name</th><th>Last name</th><th>Phone</th></thead>" + result + "</table";
}
function clickAll() {
    doWithFetchAll(funcAll);
}
var click = document.getElementById("btncall");
click.onclick = clickAll;




function update() {
    document.getElementById("firstName").value = "First name"
    document.getElementById("lastName").value = "Last name"
    document.getElementById("phone").value = "Phone"
}
function doWithFetchNewPerson() {
    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            phone: document.getElementById("phone").value
        })
    }
    console.log(options)
    fetch("http://localhost:8080/jpareststarter/api/persons", options)
    update()
}
var click2 = document.getElementById("subPerson");
click2.onclick = doWithFetchNewPerson;



function doWithFetchDelete(id) {
    let options = {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }
    fetch("http://localhost:8080/jpareststarter/api/persons/" + id, options)
}
function clickDivOne(e) {
    var id = e.target.id.substring(1, e.target.id.length)
    if (e.target.id.substring(0, 1) === "d") {
        doWithFetchDelete(id);
    }
    if (e.target.id.substring(0, 1) === "e") {
        document.getElementById("hiddenId").value = id
    }
}
var divClick = document.getElementById("outer");
divClick.onclick = clickDivOne;




function doWithFetchEdit(id) {
    console.log(id);
    let options = {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            firstName: document.getElementById("editFirstName").value,
            lastName: document.getElementById("editLastName").value,
            phone: document.getElementById("editPhone").value
        })
    }
    fetch("http://localhost:8080/jpareststarter/api/persons", options)
}
function clickDivTwo() {
    var id = document.getElementById("hiddenId").value
    doWithFetchEdit(id);
}
var divClick2 = document.getElementById("subEditPerson");
divClick2.onclick = clickDivTwo;