import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class AddFlight {


    public void AddFlight(java.sql.Connection con) {
        try {
            Scanner scan1 = new Scanner(System.in);
            System.out.print("Enter FlightID >");
            String FlightID = scan1.next();
            System.out.print("Enter Departure >");
            String Departure = scan1.next();
            System.out.print("Enter Arrival >");
            String Arrival = scan1.next();
            System.out.print("Enter Date >");
            String Date = scan1.next();
            System.out.print("Enter Time >");
            String Time = scan1.next();
            System.out.print("Enter Terminal >");
            String Terminal = scan1.next();
            System.out.print("Enter Gate >");
            String Gate = scan1.next();
            System.out.print("Enter Price >");
            String Price = scan1.next();

            Statement s = con.createStatement();
            ArrayList<String> Aircraft = new ArrayList<>();

            ResultSet f = s.executeQuery("SELECT * FROM aircraftdetails");
            int i = 1;
            while (f.next()) {
                String airID = f.getString("AircraftID");
                String AircraftModel = f.getString("AircraftModel");
                Aircraft.add(airID);
                System.out.println((i++) + ".Aircraft ID:"+airID+" Aircraft Model:"+AircraftModel);
            }
                System.out.println("Choose an Aircraft");
                int nr = scan1.nextInt();
                String aircraftid = Aircraft.get(nr - 1);

            String query1 = "INSERT INTO Flight " + "VALUES " + "(" + "'"+FlightID+"','" + Departure + "','" + Arrival + "','" + Date + "','" + Time + "','" + Terminal +"','"+Gate+"','"+aircraftid+"','"+Price+"')";
            int res = s.executeUpdate(query1);
            s.close();

        } catch (Exception io) {
            System.out.println("error" + io);
        }
    }
}
