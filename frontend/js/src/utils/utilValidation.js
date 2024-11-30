export default class Utils{
    
    static verifyIsNull(code,name,quantity,price,category){
        return  code!=null||name!=null||quantity!=null||price!=null;
    }
}