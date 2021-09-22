import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SignUp {
    public String rID;


    public void recordUser(java.sql.Connection con) {
        try {
            Scanner scan1 = new Scanner(System.in);
            System.out.print("Enter First Name >");
            String fName = scan1.next();
            System.out.print("Enter Last Name >");
            String lName = scan1.next();
            System.out.print("Enter Email >");
            String email = scan1.next();
            System.out.print("Enter Telephone Nr >");
            String telNr = scan1.next();
            System.out.print("Enter Your password >");
            String pass = scan1.next();
            Statement insertStmt = con.createStatement();
            String query1 = "INSERT INTO costumer " + "VALUES " + "(" + "'000','" + telNr + "','" + fName + "','" + lName + "','" + email + "','" + pass + "')";
            int res = insertStmt.executeUpdate(query1);
            System.out.println("Account created");
            insertStmt.close();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT CostumerID FROM costumer where email ='"+email+"'and password ='"+pass+"'");
            rs.next();
            rID = rs.getString("CostumerID");
            s.close();
        } catch (Exception io) {
            System.out.println("error" + io);
        }
    }
    public String getrID(){
        return rID;
    }

}