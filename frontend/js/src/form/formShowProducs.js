import productService from "../services/productService.js"

document.addEventListener('DOMContentLoaded',async ()=>{
    if(document.body.id === "products"){
        const page = 1;
        const itens = 10;
        const body = document.getElementById("body");
        const newRow = document.createElement("tr");
    
        const products = await productService.getProducts();
        products.forEach(products => {
            newRow.innerHTML = `
                <td>${products.code}</td>
                <td>${products.name}</td>
                <td>${products.price}</td>
                <td>${products.quantity}</td>
                <td>${products.category}</td>
                <td>${products.status}</td>
            `;

            body.appendChild(newRow);
        });
    }
})