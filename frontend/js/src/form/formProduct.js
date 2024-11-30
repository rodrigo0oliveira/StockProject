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
    const category = document.getElementById("category").value;

    if(Utils.verifyIsNull(code,name,quantity,price,category)){
        const product = new Product(name,price,quantity,code,category)
        try{
            const response = await productService.createProduct(product);
            productComponent.messageCreateProduct(response.status);
        }
        catch(error){
            console.log(error);
        }
    }
    
})