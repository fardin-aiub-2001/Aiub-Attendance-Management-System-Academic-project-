import Wlc.*;
import java.lang.*;
import javax.swing.SwingUtilities;

public class Start{
	public static void main(String[] args){
		SwingUtilities.invokeLater(() -> {
			Welcome wlc=new Welcome();
			//Login l=new Login();
			//Registration r=new Registration();
			//StudentDashboard ss=new StudentDashboard(1);
			//FacultyDashboard ff=new FacultyDashboard(2);
			//new Course();
			//TakeAttendance t=new TakeAttendance(2);
		});
	}
}