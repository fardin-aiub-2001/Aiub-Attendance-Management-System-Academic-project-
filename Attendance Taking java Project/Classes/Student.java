package Classes;
import java.lang.*;

public class Student{
	private String name,id,pnum,password;
	
    public Student(String name, String id, String pnum, String password) {
        this.name=name;
        this.id=id;
        this.pnum=pnum;
        this.password=password;
		
    }

 
    public void shows() {
        System.out.println("Name: "+this.name);
        System.out.println("ID: "+this.id);
        System.out.println("Phone Number: "+this.pnum);
        System.out.println("Password: "+this.password);
    }

}