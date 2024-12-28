package CorePackage;

/**
 *
 * @author bayra
 */
public class User {
  
    private String nameSurname;
    private String password;
    private String username;
    private String phone;
    private String NationalIdNumber;
    
    
    
    public User(String nameSurname, String password, String username, String phone, String NationalIdNumber ) {
        this.nameSurname = this.nameSurname;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.NationalIdNumber = NationalIdNumber;
    }
    
    public User(String nameSurname, String password, String username){
         this.nameSurname = this.nameSurname;
        this.password = password;
        this.username = username;
    }

    public User(){
        
    }
   
    public String getNameSurname() {
        return nameSurname;
    }

    
    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }


    public String getPassword() {
        return password;
    }

   
    public void setPassword(String password) {
        this.password = password;
    }

   
    public String getUsername() {
        return username;
    }

   
    public void setUsername(String username) {
        this.username = username;
    }

   
    public String getPhone() {
        return phone;
    }

   
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getNationalIdNumber() {
        return NationalIdNumber;
    }

    
    public void setNationalIdNumber(String NationalIdNumber) {
        this.NationalIdNumber = NationalIdNumber;
    }

}
