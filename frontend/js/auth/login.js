import LoginDto from "../dtos/loginDto.js"

const form = document.getElementById("form");

form.addEventListener('submit', async function(event){
    event.preventDefault();

    const emailValue = document.getElementById("email").value;
    const passwordValue = document.getElementById("password").value;

    const loginDto = new LoginDto(emailValue,passwordValue);

    try{
        const response = await sendRequestLogin(loginDto);
        responseMessageLogin(response.status);

    }catch(error){
        return error;
    }

})

async function sendRequestLogin(user) {
    try{
        const response = await axios.post('http://localhost:8080/auth/login',{
            email : user.email,
            password: user.password
        },{
            validateStatus:(status)=>{
                return status>=200 && status <500;
            }
        });
        return response;
    }
    catch(errror){
        return errror;
    }
}

function responseMessageLogin(response){
    const spanCredentialError = document.getElementById("credential-error");
    if(response==200){
        spanCredentialError.textContent = "Credenciais válidas";
        spanCredentialError.style.color = "green";
        spanCredentialError.style.display = "block";
    }
    else{
        spanCredentialError.style.display = "block";
    }
}