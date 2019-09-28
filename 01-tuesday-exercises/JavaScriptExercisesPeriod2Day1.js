//////////////////////////////////////////////////////////////////////////////////(1)
//1
var arr = ["Lars", "Jan", "Peter", "Bo", "Frederik"]
var arrNew1 = arr.filter(name => name.toLowerCase().includes("a"))
console.log(arrNew1)

//2
var arrNew2 = arr.map(name => name.split("").reverse().join(""))
console.log(arrNew2)

//////////////////////////////////////////////////////////////////////////////////(2)
//1
function myFilter1(array, callback) {
    let newArr = []
    for (let item of array) {
        if (callback(item)) {
            newArr.push(item)
        }
    }
    return newArr
}
var arrNew3 = myFilter1(arr, name => name.toLowerCase().includes("a"))
console.log(arrNew3)

//2
function myMap1(array, callback) {
    let newArr = []
    for (let item of array) {
        newArr.push(callback(item))
    }
    return newArr
}
var arrNew4 = myMap1(arr, name => name.split("").reverse().join(""))
console.log(arrNew4)

//////////////////////////////////////////////////////////////////////////////////(3)
//1
var names = ["Lars", "Peter", "Jan", "Bo"];
Array.prototype.myFilter2 = function (callback) {
    let newArr = []
    for (let item of this) {
        if (callback(item)) {
            newArr.push(item)
        }
    }
    return newArr
}
var newArray = names.myFilter2(name => name.toLowerCase().includes("a"))
console.log(newArray)

//2
Array.prototype.myMap2 = function (callback) {
    let newArr = []
    for (let item of this) {
        newArr.push(callback(item))
    }
    return newArr
}
var newArray = names.myMap2(name => name.split("").reverse().join(""))
console.log(newArray)

//////////////////////////////////////////////////////////////////////////////////(4)
//a
var numbers = [1, 3, 5, 10, 11]
var result = numbers.map(function (num, index) {
    if (index != numbers.length - 1) {
        return num + numbers[index + 1]
    } else {
        return num
    }
})
console.log(result)

//b
var NamesList = "<nav>" + names.map(name => "<a href=\"\">" + name + "</a>").join(" ") + "</nav>"
console.log(NamesList)

//c
var names2 = [{ name: "Lars", phone: "1234567" }, { name: "Peter", phone: "675843" }, { name: "Jan", phone: "98547" }, { name: "Bo", phone: "79345" }]
var result = names2.map(name => "<tr>" + "<th>" + name.name + "</th><th>" + name.phone + "</th>" + "</tr>").join(" ")
console.log(result)

//d
if (typeof document !== 'undefined') {
    document.getElementById("name").innerHTML = result
}

//e
function clickSortA() {
    var names3 = names2.myFilter2(name => name.name.toLowerCase().includes("a"))
    var result2 = names3.map(name => "<tr>" + "<th>" + name.name + "</th><th>" + name.phone + "</th>" + "</tr>").join(" ")
    document.getElementById("name").innerHTML = result2
}
if (typeof document !== 'undefined') {
    var click = document.getElementById("btn1")
    click.onclick = clickSortA
}

//////////////////////////////////////////////////////////////////////////////////reduce
//a
var all = ["Lars", "Peter", "Jan", "Bo"]
var allComma = all.join(",")
console.log(allComma)
var allSpace = all.join(" ")
console.log(allSpace)
var allHashtag = all.join("#")
console.log(allHashtag)

//b
var numbers = [2, 3, 67, 33]
var newResult = numbers.reduce(function (total, num) {
    return total + num;
})
console.log(newResult)

//c
var members = [
    { name: "Peter", age: 18 },
    { name: "Jan", age: 35 },
    { name: "Janne", age: 25 },
    { name: "Martin", age: 22 }]
var reducer = function (accumulator, member, index, array) {
    accumulator += member.age
    if (index === array.length - 1) {
        return accumulator / array.length
    } else {
        return accumulator
    }
};
var newMemebers = members.reduce(reducer, 0)
console.log(newMemebers)

//d
var votes = ["Clinton", "Trump", "Clinton", "Clinton", "Trump", "Trump", "Trump", "None"];
var reducer2 = function (tally, vote) {
    if (!tally[vote]) {
        tally[vote] = 1;
    } else {
        tally[vote] = tally[vote] + 1;
    }
    return tally;
}
var newVotes = votes.reduce(reducer2, {})
console.log(newVotes)

//////////////////////////////////////////////////////////////////////////////////Hoisting
//Function declarations are completely hoisted

function hoist() {
    a = 20;
    var b = 100;
}
hoist();
console.log(a) //global variable
console.log(b) //was declared

var hey1
hey1 = "Hey1"
console.log(hey1)

hey2 = "Hey2"
var hey2
console.log(hey2)

var Hey3
console.log(hey3)
hey3 = "Hey3"

var hey4 = "Hey4"
console.log(hey4)

console.log(hey5)
var hey5 = "Hey5"

//when Javascript compiles all of your code, all variable declarations using var are 
//hoisted/lifted to the top of their functional/local scope (if declared inside a function) 
//or to the top of their global scope (if declared outside of a function)

//var and let are both used for variable declaration in javascript but the difference 
//between them is that var is function scoped and let is block scoped. It can be said that 
//a variable declared with var is defined throughout the program as compared to let.

//////////////////////////////////////////////////////////////////////////////////this in JavaScript

// An object can be passed as the first argument to call or apply and this will be bound to it.
var obj = { a: 'Custom' }
// This property is set on the global object
var a = 'Global'
function whatsThis() {
    return this.a // The value of this is dependent on how the function is called
}
whatsThis()      // 'Global'
console.log(whatsThis.call(obj)) // 'Custom'
console.log(whatsThis.apply(obj)) // 'Custom'
console.log(whatsThis.bind(obj)) // obj

function f() {
    return this.a;
}
var g = f.bind({ a: 'azerty' });
console.log(g()); // azerty
var h = g.bind({ a: 'yoo' }); // bind only works once!
console.log(h()); // azerty
var o = { a: 37, f: f, g: g, h: h };
console.log(o.a, o.f(), o.g(), o.h()); // 37,37, azerty, azerty

var obj = { func: foo };
console.log(obj.func() === globalObject); // true
// Attempt to set this using call
console.log(foo.call(obj) === globalObject); // true
// Attempt to set this using bind
foo = foo.bind(obj);
console.log(foo() === globalObject); // true

//JavaScript code is run on a browser only, while Java creates applications that run in a 
//virtual machine or browser. Java is an OOP (object-oriented programming) language, and 
//JavaScript is specifically an OOP scripting language.

//You can use call() / apply() to invoke the function immediately. bind() returns a bound 
//function that, when executed later, will have the correct context ("this") for calling 
//the original function. So bind() can be used when the function needs to be called later 
//in certain events when it's useful.

//////////////////////////////////////////////////////////////////////////////////ES6 classes and Single Page Applications without Netbeans

