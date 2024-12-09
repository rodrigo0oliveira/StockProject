import productService from "../services/productService.js"

document.addEventListener('DOMContentLoaded',async ()=>{
    if(document.body.id === "products"){
        const body = document.getElementById("body");
        
        const products = await productService.getProducts();
        products.forEach(products => {
            const newRow = document.createElement("tr");

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