import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LogIn {
    public String cID;

    public boolean  isTrue(java.sql.Connection con){
        boolean res = true;
        try {
            Scanner scan1 = new Scanner(System.in);
            System.out.print("Enter Your Email>");
            String email = scan1.next();
            System.out.print("Enter Your Password>");
            String pass = scan1.next();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT CostumerID FROM costumer where email ='"+email+"'and password ='"+pass+"'");
            res = rs.next();
            cID = rs.getString("CostumerID");
            s.close();

        }catch (Exception io) {
            System.out.println("error"+io);
        }
        return res;
    }
    public String getCID(){
        return cID;
    }


}
