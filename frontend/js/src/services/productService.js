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
}