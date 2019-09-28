//1)
function toHTMLSingleTable(user) {
    var result = "Country: " + user[0].name + " Popuation: " + user[0].population + " Area: " + user[0].area + " Borders: " + user[0].borders;
    return result;
}
function funcId(data) {
    document.getElementById("paragraph").innerText = toHTMLSingleTable(data)

}
function doWithFetchId(callback,id) {
    fetch("/SP5_AJAX_CORS_Proxy/api/generic/" + id)
            .then(res => res.json())
            .then(data => {
                data;
        console.log(data);
                callback(data)
            });
}
function clickDivOne(e) {
    document.getElementById(e.target.id).style.fill = "red";
    var id = e.target.id.substring(0, 2).toLowerCase()
    console.log(id);
    doWithFetchId(funcId,id);
}
var divClick = document.getElementById("outer");
divClick.onclick = clickDivOne;

//2)
//The same origin policy is centred around a web page having access to data 
//in another web page as long as they have the same origin. The reason we were 
//able to do fetch data while on a different page not from the same origin, 
//can be found in the http headers, where it can be seen that the target web 
//page that we fetch from allows access to the http GET requests from all "*" origins.
//
// These are the headers, which can be found under the networks tab with the Chrome Developer tool: 
//Access-Control-Allow-Headers: Accept, X-Requested-With
//Access-Control-Allow-Methods: GET
//Access-Control-Allow-Origin: *
