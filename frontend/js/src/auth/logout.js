document.addEventListener('DOMContentLoaded',()=>{
    const logout = document.querySelector('.exit').addEventListener('click',async()=>{
        try{
            const response = await axios.post("https://localhost:8843/auth/logout",null,{
                withCredentials:true
            });
            if(response.status==200){
                window.location.href='/login.html';
            }

        }catch(e){
            console.log(e);
        }
    })
})