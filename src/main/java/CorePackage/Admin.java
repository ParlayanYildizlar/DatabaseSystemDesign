package CorePackage;

/**
 *
 * @author bayra
 */
public class Admin extends User{
    
    
    public Admin(String nameSurname, String password, String username, String phone, String NationalIdNumber ) {
       super(nameSurname, password, username, phone, NationalIdNumber);
    }
    
    public Admin(String nameSurname, String password, String username){
        super(nameSurname, password, username);
    }
    
    
    public Admin(){
        
    }
    
    
    
    
    
}
