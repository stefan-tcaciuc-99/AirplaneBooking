import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/r00192770ryanair?user=root&password=Stefan.0");
            System.out.println("Database connection established");
            Statement s = con.createStatement();
            boolean x = true;
            while (x) {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("User Scenario\n----------------");
                System.out.print("1.Sign Up\n2.Log in\n3.Flights\n\nAdmin Scenario\n----------------\n4.Add Aircraft\n5.Add Flight\n6.Update Aircraft Details\n7.Quit\nSelect an option>");
                int choice = scan1.nextInt();
                int scenario = 0;
                if (choice == 1) {
                    scenario = 1;
                }
                if (choice == 2) {
                    scenario = 2;
                }
                if (choice == 3) {
                    scenario = 3;
                }
                if (choice == 4) {
                    scenario = 4;
                }
                if (choice == 5) {
                    scenario = 5;
                }
                if (choice == 6) {
                    scenario = 6;
                }
                if (choice == 7) {
                    x = false;
                }

                switch (scenario) {
                    case 1:
                        SignUp a = new SignUp();
                        a.recordUser(con);
                        break;
                    case 2:
                        LogIn b = new LogIn();
                        if (b.isTrue(con)) {
                            System.out.println("Log in successful ");
                            String CID = b.getCID();
                            System.out.println("Your Current tickets are :");
                            ResultSet h = s.executeQuery("SELECT flight.* FROM booking, flight WHERE CostumerID ='" + CID + "'");
                            int z = 1;
                            while (h.next()) {
                                String flight = h.getString("FlightID");
                                String departure = h.getString("Departure");
                                String arrival = h.getString("Arrival");
                                String date = h.getString("Date");
                                String time3 = h.getString("Time");
                                String term = h.getString("Terminal");
                                String gate = h.getString("Gate");
                                System.out.println("Ticket nr:" + z++);
                                System.out.println("Flight ID\t\tDeparture\t\tArrival\t\tDate\t\tTime\t\tTerminal\t\tGate\n-----------------------------------------------------------------------------------------");
                                System.out.println(flight + "\t\t" + departure + "\t\t" + arrival + "\t\t" + date + "\t\t" + time3 + "\t\t" + term + "\t\t" + gate);
                                System.out.println("");
                            }

                        } else {
                            System.out.println("Email or password is incorrect");
                        }
                        break;
                    case 3:
                        int time = LocalTime.now().toSecondOfDay();
                        System.out.println("\t\t\t\tFLIGHTS\n\nDeparture\t\tArrival\t\tDate\t\tTime\t\tPrice\n------------------------------------------------------------------");
                        ResultSet f = s.executeQuery("SELECT * FROM flight");
                        ArrayList<String> list = new ArrayList<>();
                        int i = 1;
                        while (f.next()) {
                            String departure = f.getString("Departure");
                            String arrival = f.getString("Arrival");
                            String date = f.getString("Date");
                            String flighttime = f.getString("Time");
                            String flightId = f.getString("FlightID");
                            String price = f.getString("price");
                            list.add(flightId);
                            System.out.println((i++) + "." + departure + "\t\t" + arrival + "\t\t" + date + "\t\t" + flighttime + "\t\t" + price + "\u20AC");
                        }
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("1.Book a flight\n2.Back");
                        int ch = scan1.nextInt();
                        if (ch == 1) {
                            System.out.println("Choose a flight>");
                            int nr = scan1.nextInt();
                            String flightID = list.get(nr - 1);

//                        System.out.println("Press enter to continue");
//                        System.in.read();
                            System.out.println("You must log in or sign up to continue booking");
                            System.out.print("1.Sign Up\n2.Log in");
                            int nr1 = scan1.nextInt();
                            String bID = "";
                            if (nr1 == 1) {

                                SignUp c = new SignUp();
                                c.recordUser(con);
                                bID = c.getrID() + time;
                                Statement insertStmt = con.createStatement();
                                String query1 = "INSERT INTO booking VALUES " + "(" + "'" + bID + "'," + "'" + c.getrID() + "','" + flightID + "')";
                                int res = insertStmt.executeUpdate(query1);
                                insertStmt.close();

                            } else {
                                LogIn d = new LogIn();
                                if (d.isTrue(con)) {
                                    System.out.println("Log in successful ");
                                    System.out.println(d.getCID());
                                    bID = d.getCID() + time;
                                    Statement insertStmt = con.createStatement();
                                    String query1 = "INSERT INTO booking VALUES " + "(" + "'" + bID + "'," + "'" + d.getCID() + "','" + flightID + "')";
                                    int res = insertStmt.executeUpdate(query1);
                                    insertStmt.close();

                                } else {
                                    System.out.println("Email or password is incorrect");
                                }
                            }
                            System.out.println("Select Payment Method\n1.Visa\n2.MasterCard");
                            int p = scan1.nextInt();
                            String payment = "";
                            if (p == 1) {
                                payment = "Visa";
                            } else {
                                payment = "MasterCard";
                            }
                            String invID = "PY" + time;
                            Statement insert = con.createStatement();
                            String query3 = "INSERT INTO Payment VALUES " + "(" + "'" + invID + "'," + "'" + payment + "','" + bID + "')";
                            int res = insert.executeUpdate(query3);
                            insert.close();


                            System.out.println("You have successfully booked your flight\nThis is your InvoiceNR:" + invID);
                            System.out.println("1.Print Ticket\n2.Home");
                            int c = scan1.nextInt();
                            if (c == 1) {
                                ResultSet g = s.executeQuery("SELECT * FROM flight where flightid='" + flightID + "'");
                                while (g.next()) {
                                    String flight = g.getString("FlightID");
                                    String departure = g.getString("Departure");
                                    String arrival = g.getString("Arrival");
                                    String date = g.getString("Date");
                                    String time3 = g.getString("Time");
                                    String term = g.getString("Terminal");
                                    String gate = g.getString("Gate");
                                    System.out.println("Flight ID\t\tDeparture\t\tArrival\t\tDate\t\tTime\t\tTerminal\t\tGate\n-----------------------------------------------------------------------------------------");
                                    System.out.println(flight + "\t\t" + departure + "\t\t" + arrival + "\t\t" + date + "\t\t" + time3 + "\t\t" + term + "\t\t" + gate);

                                }
                            } else {
                                break;
                            }


                        } else {
                            break;
                        }

                    case 4:
                        System.out.println("Enter Aircraft ID");
                        String airID = scan1.next();
                        System.out.println("Enter Aircraft Model");
                        String airModel = scan1.next();
                        System.out.println("Enter Capacity");
                        String capacity = scan1.next();
                        System.out.println("Enter Airline Owner");
                        String owner = scan1.next();
                        Statement insertStmt = con.createStatement();
                        String query4 = "INSERT INTO aircraftdetails VALUES " + "(" + "'" + airID + "'," + "'" + airModel + "','" + capacity + "','" + owner + "')";
                        int res = insertStmt.executeUpdate(query4);
                        insertStmt.close();
                        break;


                    case 5:
                        AddFlight y = new AddFlight();
                        y.AddFlight(con);
                        break;
                    case 6:
                        Statement l = con.createStatement();
                        ArrayList<String> airlist = new ArrayList<>();

                        ResultSet k = l.executeQuery("SELECT * FROM aircraftdetails");
                        int o = 1;
                        while (k.next()) {
                            String aID = k.getString("AircraftID");
                            String AirModel = k.getString("AircraftModel");
                            String capcity = k.getString("Capacity");
                            String pof = k.getString("PropretyOf");
                            airlist.add(aID);
                            System.out.println((o++) + ".Aircraft ID:" + aID + " Aircraft Model:" + AirModel + "Capacity:" + capcity + "Property Of:" + pof);
                        }
                        System.out.println("Choose an Aircraft To Update");
                        int nr = scan1.nextInt();
                        String AicraftIdentification = airlist.get(nr - 1);
                        System.out.println("Enter Capacity");
                        String cap = scan1.next();
                        System.out.println("Enter Airline Owner");
                        String airown = scan1.next();
                        Statement aft = con.createStatement();
                        String query8 = "UPDATE aircraftdetails SET Capacity = '" + cap + "'," + "PropretyOf= '" + airown + "'" + " WHERE AircraftID= '" + AicraftIdentification + "'";
                        aft.executeUpdate(query8);
                        aft.close();
                        break;

                }


            }
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

}
