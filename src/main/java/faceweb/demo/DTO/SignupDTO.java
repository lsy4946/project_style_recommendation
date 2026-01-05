package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {

    private String username;//ID
    private String password;//비밀번호
    private String confirm_password;
    private String name;//이름
    private String email;
    private String gender;//성별
    private String role;//역할
    //private String verificationCode;

    public void printAll(){
        System.out.println("username : "+username);
        System.out.println("password : " + password);
        System.out.println("name : " + name);
        System.out.println("email : " + email);
        System.out.println("gender : " + gender);
    }
}
