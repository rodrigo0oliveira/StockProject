
import User from "../entities/user.js"


const form = document.getElementById("form");

var emailValid = false;
var passwordValid = false;

const spanEmail = "span-email";
const spanPassword = "span-password";



document.getElementById("email").oninput = function(event){//Evento quando o usuário vai digitando e-mail
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

document.getElementById("password").oninput = function(event){//Evento quando o usuário vai digitando a senha
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

function showError(string){//Mostra erro
    var span = document.getElementById(string);
    span.style.display = "block";
}

function removeError(string){//Remove erro
    var span = document.getElementById(string);
    span.style.display = "none";
}

function showSubmitError(){//Mostra erro de submit
    var span = document.getElementById("error-submit");
    span.style.display = "block",

    setTimeout(function(){
        span.style.display = "none";
    },5000);
}

form.addEventListener('submit',async function(event) {//Evento ao clicar em no botão de submit
    event.preventDefault();
    var emailValue = document.getElementById("email").value;
    var passwordValue = document.getElementById("password").value;

    if(emailValid&&passwordValid==true){
        const user = new User(emailValue,passwordValue,"CLIENT");
        try{
            const response = await sendRequest(user);
            responseMessage(response.status);
        }
        catch(error){
            console.log(error);
        }
    }
    else{
        showSubmitError();
    }
    
})

async function sendRequest(user){//Envia requisição de registro
   try {
    const response = await axios.post('https://localhost:8843/auth/register',{
        email:user.email,
        password:user.password,
        role:user.role
    },{
        validateStatus: (status) => {
            return status >= 200 && status < 500;
        },withCredentials:false
    });
    return response;
   } catch (error) {
        console.log(error);
   }
    
}

function responseMessage(response){//Retorna mensagem na tela a partir do código http;
    
    const spanRegister = document.getElementById("message-register");
    const erroEmail = document.getElementById("span-email");

    if(response==201){
        spanRegister.style.display = "block";
    }
    else if(response==409){
        erroEmail.style.display="block";
        erroEmail.style.fontSize="18px";
        erroEmail.textContent="O e-mail informado já esta cadastrado";
        
    }
    else{
        spanRegister.textContent="Algo deu errado!Por favor tente novamente."
    }
}

