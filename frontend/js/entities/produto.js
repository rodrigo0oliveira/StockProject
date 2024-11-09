class Produto{
    constructor(nome,preco,quantidade,status){
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.status = status;
    }

    toJson(produto){
        return JSON.stringify(produto);
    }
}