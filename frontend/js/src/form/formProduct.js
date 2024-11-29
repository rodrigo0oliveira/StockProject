import Utils from "../utils/utilValidation.js"
import Product from "../entities/product.js"
import productService from "../services/productService.js";
import productComponent from "../components/productComponent.js";

const form = document.getElementById("form");

form.addEventListener('submit',async function (event) {
    event.preventDefault();
    const code = document.getElementById("code").value;
    const name = document.getElementById("name").value;
    const price = document.getElementById("price").value;
    const quantity = document.getElementById("quantity").value;

    if(Utils.verifyIsNull(code,name,quantity,price)){
        const product = new Product(name,price,quantity,code)
        try{
            const response = await productService.createProduct(product);
            productComponent.messageCreateProduct(response.status);
        }
        catch(error){
            console.log(error);
        }
    }
    
})