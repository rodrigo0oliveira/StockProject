import editProductDto from "../dtos/editProductDto.js";

export default class productService{

   static async createProduct(product){
        try{
            const response = await axios.post('https://localhost:8843/auth/product/create',{
                name:product.name,
                price:product.price,
                quantity:product.quantity,
                code:product.code,
                category:product.category
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

    static async getProducts() {
        try{
            const page = 1;
            const itens = 4;
            const response = await axios.get('https://localhost:8843/auth/product/findAll',{
            params:{
                page:0,
                itens:5
                
            },
            withCredentials:true
        });
           if(response.status==200){
                return response.data;
           }
        }catch(e){
            console.log(e);
        }
    }

    static async editProduct(code,editProductDto){
        try{
            const response = await axios.put('https://localhost:8843/auth/product/edit/'+code,{
                name : editProductDto.name,
                price : editProductDto.price,
                quantity : editProductDto.quantity,
                category : editProductDto.category
            },{
                validateStatus: (status) => {
                    return status >= 200 && status < 500;
                }, 
                withCredentials:true
            });
            if(response.status==200){
                return "Produdo editado";
            }
            else{
                return "Erro ao editar o produto";
            }
        }catch(e){
            console.log(e);
        }
    }
}