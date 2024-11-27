import Product from "../entities/product.js";

const form = document.getElementById("form");

form.addEventListener('submit',async function (event) {
    event.preventDefault();
    const code = document.getElementById("code").value;
    const name = document.getElementById("name").value;
    const price = document.getElementById("price").value;
    const quantity = document.getElementById("quantity").value;

    if(verifyIsNull(code,name,quantity,price)){
        const product = new Product(name,price,quantity,code)
        try{
            const response = await createProduct(product);
            messageCreateProduct(response.status);
        }
        catch(error){
            console.log(error);
        }
    }
    
})

function verifyIsNull(code,name,quantity,price){
    return  code!=null||name!=null||quantity!=null||price!=null;
}

async function createProduct(product){
    try{
        const response = await axios.post('https://localhost:8843/auth/product/create',{
            name:product.name,
            price:product.price,
            quantity:product.quantity,
            code:product.code
        },{
            validateStatus: (status) => {
                return status >= 200 && status < 500;
            }, 
            withCredentials:true
    });
    return response;
    }catch(error){
        console.log(error);
    }
}

function messageCreateProduct(status){
    const response = document.getElementById("response");
    const span = document.getElementById("span-message");
    if(status==201){
        response.textContent = "Produto cadastrado com sucesso!";
        span.style.backgroundColor = "rgba(0, 128, 0, 0.285)";
    }
    else  if(status==409){
        span.style.backgroundColor = "rgba(110, 23, 6, 0.285)";
        response.textContent = "O código informado já existe.";
    }
    else{
        span.style.backgroundColor = "rgba(110, 23, 6, 0.285)";
        response.textContent = "Algo deu errado ao cadastrar o produto";
    }
}