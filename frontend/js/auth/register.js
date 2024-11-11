import User from "../entities/user.js"


const form = document.getElementById("form");

var emailValid = false;
var passwordValid = false;

const spanEmail = "span-email";
const spanPassword = "span-password";



document.getElementById("email").oninput = function(event){
    var emailRegex = /^[\w+.]+@\w+\.\w{2,}(?:\.\w{2})?$/;
    var email = event.target.value;
    const emailInput = document.getElementById("email");
    if(emailRegex.test(email)){  
        emailInput.style.border = "2px solid green";
        emailValid = true;
        removeError(spanEmail);
    }
    else{
        emailInput.style.border = "2px solid red";
        showError(spanEmail);
        emailValid = false;
    }
}

document.getElementById("password").oninput = function(event){
    let regex =  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@.#$!%*?&^])[A-Za-z\d@.#$!%*?&]{8,15}$/;
    var password = event.target.value;
    const passwordInput = document.getElementById("password");
    if(regex.test(password)){  
        passwordInput.style.border = "2px solid green";
        passwordValid = true;
        removeError(spanPassword);
    }
    else{
        passwordInput.style.border = "2px solid red";
        showError(spanPassword);
        passwordValid = false;
    }
}

function showError(string){
    var span = document.getElementById(string);
    span.style.display = "block";
}

function removeError(string){
    var span = document.getElementById(string);
    span.style.display = "none";
}

function showSubmitError(){
    var span = document.getElementById("error-submit");
    span.style.display = "block",

    setTimeout(function(){
        span.style.display = "none";
    },5000);
}

form.addEventListener('submit',function(event){
    event.preventDefault();
    
    var emailValue = document.getElementById("email").value;
    var passwordValue = document.getElementById("password").value;

    if(emailValid&&passwordValid==true){
        const user = new User(emailValue,passwordValue,"CLIENT");
        sendRequest(user);
    }
    else{
        showSubmitError();
    }
    
})

function sendRequest(user){
    const response = axios.post('http://localhost:8080/auth/register',{
        email : user.email,
        password : user.password,
        role : user.role
    })
    .then((response)=>{
        console.log(response.status);
    })
}

