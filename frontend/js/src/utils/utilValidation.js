export default class Utils{
    
    static verifyIsNull(code,name,quantity,price){
        return  code!=null||name!=null||quantity!=null||price!=null;
    }
}