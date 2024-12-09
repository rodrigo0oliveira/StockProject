import LoginDto from "../dtos/loginDto.js"

const form = document.getElementById("form");

form.addEventListener('submit', async function(event){
    event.preventDefault();

    const emailValue = document.getElementById("email").value;
    const passwordValue = document.getElementById("password").value;

    const loginDto = new LoginDto(emailValue,passwordValue);

    try{
        const response = await sendRequestLogin(loginDto);
        responseMessageLogin(response.status,response.data.token,response.data.expiresIn);

    }catch(error){
        return error;
    }

})

async function sendRequestLogin(user) {
    try{
        const response = await axios.post('https://localhost:8843/auth/login',{
            email : user.email,
            password: user.password
        },{
            validateStatus:(status)=>{
                return status>=200 && status <500;
            },withCredentials:false
        });
        return response;
    }
    catch(errror){
        return errror;
    }
}

function responseMessageLogin(response,token,expiresIn){
    const spanCredentialError = document.getElementById("credential-error");
    if(response==200){
        spanCredentialError.textContent = "Credenciais válidas";
        spanCredentialError.style.color = "green";
        spanCredentialError.style.display = "block";
        saveCookie(token,expiresIn);
        
        
    }
    else{
        spanCredentialError.style.display = "block";
    }
}

function saveCookie(token,expiresIn){
    const name = "jwt";
    const date = new Date();
    date.setTime(date.getTime()+expiresIn);
    const expires = "expires="+date.toUTCString();
    document.cookie=`${name}=${token}; ${expires}; path=/; Secure; SameSite=None`;
    window.location.href="/dashboard.html";
}


