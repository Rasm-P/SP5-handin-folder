function funcAll(arr) {
    const a = arr.map(mov => "<table><tr>" + "<th>" + mov.name + "</th><th>" + mov.director + "</th>" + "</tr></table>").join("");
    document.getElementById("div").innerHTML = a;
}
function clickSubAll() {
    allUsers = fetch("https://barfodpraetorius.dk/CORStravisMovies-1.0.1/api/movies/all")
            .then(res => res.json())
            .then(data => {
                funcAll(data);
            })
}
var click = document.getElementById("subAll");
click.onclick = clickSubAll;

function funcId(mov) {
    document.getElementById("div").innerHTML = "<ul>" + "<li>" + mov.name + "</li><li>" + mov.director + "</li><li>" + mov.genre + "</li><li>" + mov.year + "</li><li>" + mov.length + "</li><li>" + mov.productionPrice + "</li>" + "</ul>";
}
function clickSubId() {
    fetch("https://barfodpraetorius.dk/CORStravisMovies-1.0.1/api/movies/" + document.getElementById("addId").value)
            .then(res => res.json())
            .then(data => {
                data;
                funcId(data);
            })
}
var click = document.getElementById("subId");
click.onclick = clickSubId;

function funcName(mov) {
    var moviesName = mov.map(m => "<div><ul>" + "<li>" + m.name + "</li><li>" + m.director + "</li><li>" + m.genre + "</li><li>" + m.year + "</li><li>" + m.length + "</li><li>" + m.productionPrice + "</li>" + "</ul></div>").join("");
    document.getElementById("div").innerHTML = moviesName;
}
function clickSubName() {
    fetch("https://barfodpraetorius.dk/CORStravisMovies-1.0.1/api/movies/name/" + document.getElementById("addName").value)
            .then(res => res.json())
            .then(data => {
                data;
                funcName(data);
            })
}
var click = document.getElementById("subName");
click.onclick = clickSubName;