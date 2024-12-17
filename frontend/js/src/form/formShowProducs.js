import productService from "../services/productService.js"
import editProductDto from "../dtos/editProductDto.js";

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
                <td>
                    <button class="edit">
                    <img src="../../../imgs/lapis.png" alt="edit" >
                    </button>
                    <button class="delete">
                    <img src="../../../imgs/lixeira.png" alt="delete">
                    </button>
                </td>
            `;

            newRow.querySelector(".edit").addEventListener("click",()=>edit(products.code,products.name));
            

            body.appendChild(newRow);
        });
    }
})

function edit(code,name){
   showEditForm(name);

   const button = document.getElementById("send-edit");

   button.addEventListener("click",(event)=>{
        
        event.preventDefault();

        
        const newName = document.getElementById("new-name").value;
        const newPrice = document.getElementById("new-price").value;
        const newQuantity = document.getElementById("new-quantity").value;
        const newCategory = document.getElementById("new-category").value;

        const productDto = new editProductDto(newName,newPrice,newQuantity,newCategory);

        productService.editProduct(code,productDto);
   })
}

function showEditForm(name){
    const header = document.getElementById("header");
    const table = document.getElementById("table");
    const editDiv = document.getElementById("edit-show");
    const inform = document.getElementById("inform");

    header.style.display = "none";
    table.style.display = "none";

    inform.textContent = "Editar "+name;
    editDiv.style.display = "flex";
}