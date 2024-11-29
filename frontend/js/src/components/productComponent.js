
export default class productComponent{
    static messageCreateProduct(status){
        const response = document.getElementById("response");

        if(status==201){
            response.textContent = "Produto criado com sucesso!";
        }
        else if(status==409){
            response.textContent = "Erro ao criar o produto!\nCódigo informado já existe!";
        }

    }
}