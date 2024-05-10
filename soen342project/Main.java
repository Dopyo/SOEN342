import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.sql.*;

public class Main {
    private Map<String, String> registeredUsers;

    static boolean isRegistered = false;
    public static void main(String[] args) {
        FlightCatalog flightCatalog = FlightCatalog.getInstance();
        Scanner scanner = new Scanner(System.in);
        String input = "1";
        if(!isRegistered){
            System.out.println("Hello unregistered client, please \n "+
                    "(1) Login \n "+
                    "(2) Find flights \n ");
            //TODO: Improve logic robustness here: prevent user from entering nonsensical values

            input = scanner.nextLine();
        }

        
        switch (input){
            case "1":{
                //TODO create logic for login/auth
                if(!isRegistered){
                    Main UserRegistrationSystem= new Main();
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Enter username:");
                    // Example verification
                    String username = scan.next();
                    System.out.println("Enter password:");
                    String password = scan.next();
                    isRegistered =UserRegistrationSystem.isUserRegistered(username, password);
                }

                if (isRegistered) {
                    System.out.println("Hello registered client, please make your choice : \n (1) Find flights \n (2) Find private flights as an Airport \n "+
                            "(3) Register private flight as an Airport \n "+
                            "(4) Register non-private flight as an Airport \n "+
                            "(5) Register airport as System Administrator");
                    //TODO: Improve logic robustness here: prevent user from entering nonsensical values
                    String choice = scanner.nextLine();
                    switch(choice){
                        case "1":
                            System.out.println("Enter source airport code (e.g. YUL)");
                            String source = scanner.nextLine();
                            AirportCode sourceCode = stringToAirportCode(source);
                            System.out.println("Enter destination airport code (e.g. YYZ)");
                            String destination = scanner.nextLine();
                            AirportCode destinationCode = stringToAirportCode(destination);
                            ArrayList<Flight> foundFlights = flightCatalog.findNonPrivateFlights(sourceCode, destinationCode);
                            printRegisteredFlights(foundFlights);
                            System.out.println("================================================");
                            break;
                        case"2":
                            System.out.println("Enter airport code (e.g. YUL)");
                            String port = scanner.nextLine();
                            AirportCode portCode = stringToAirportCode(port);
                            ArrayList<Flight> foundPFlights = flightCatalog.findPrivateFlights(portCode);
                            printRegisteredFlights(foundPFlights);
                            System.out.println("************************************************");
                            break;
                        case "3":{
                            System.out.println("Enter the scheduledDepartureTime, scheduledArrivalTime, source_airport, destination_airport");
                            //TODO: Improve logic robustness here: prevent user from entering nonsensical values
                            int num_of_param = 4;
                            String flightinfo = scanner.nextLine();
                            String flightstrings[] = flightinfo.split(" ");
                            if(flightstrings.length != num_of_param){
                                System.out.println("Number of arguments not equal to " + num_of_param);
                                break;
                            }
                            boolean error = flightCatalog.isScheduleConflict(flightstrings[0], flightstrings[1], flightstrings[2], flightstrings[3]);
                            if(error){
                                System.out.println("There is an error");
                                break;
                            }
                            boolean success = flightCatalog.registerPrivateFlight(flightstrings[0], flightstrings[1], flightstrings[2], flightstrings[3]);
                            if(success){
                                System.out.println("Successfully registered private flight");
                            }else{
                                System.out.println("Source or Destination not available: " + flightstrings[2] +" "+ flightstrings[3]);
                                System.out.println("The source airport doesn't have any aircrafts in its fleet. ");
                            }

                            break;
                        }
                        case "4":{
                            System.out.println("Enter the Scheduled Departure Time, Scheduled Arrival Time, Source Airport, Destination Airport");
                            //TODO: Improve logic robustness here: prevent user from entering nonsensical values
                            int num_of_param = 4;
                            String flightinfo = scanner.nextLine();
                            String flightstrings[] = flightinfo.split(" ");
                            System.out.println("Please enter the Non Private Flight type: \n1. Commercial \n 2. Cargo ");
                            //TODO: Improve logic robustness here
                            String nonPrivateOption = scanner.nextLine();
                            System.out.println("Please enter the Airline you are: \n1. Air Canada \n2. Delta \n3. United ");
                            //TODO: Improve logic robustness here
                            String airlineOption = scanner.nextLine();
                            if(flightstrings.length != num_of_param){
                                System.out.println("Number of arguments not equal to " + num_of_param);
                                break;
                            }
                            boolean error = flightCatalog.isScheduleConflict(flightstrings[0], flightstrings[1], flightstrings[2], flightstrings[3]);
                            if(error){
                                System.out.println("There is an error");
                                break;
                            }
                            boolean success = flightCatalog.registerNonPrivateFlight(flightstrings[0], flightstrings[1], flightstrings[2], flightstrings[3], nonPrivateOption, airlineOption);
                            if(success){
                                System.out.println("Successfully registered non-private flight");
                            }else{
                                System.out.println("Error occurred...Possibly with airports, schedule. airline or no free aircrafts were found");
                            }
                            break;
                        }
                        case "5":{
                            System.out.println("Enter the name, city_id, airport_code, available_aircraft_ids (separated by spaces) ");
                            String[] airport_string = scanner.nextLine().split(",");
                            flightCatalog.registerAirport(airport_string);

                        }

                    }

                } else{
                    System.out.println("User is not registered or incorrect credentials.");}
                    System.out.println("Press any key to return to the main menu...");
                    scanner.nextLine(); // Wait for the user to press a key
                     main(null);
                }
                break;

            case "2":{
                System.out.println("Enter source airport code (e.g. YUL)");
                String source = scanner.nextLine();
                AirportCode sourceCode = stringToAirportCode(source);
                System.out.println("Enter destination airport code (e.g. YYZ)");
                String destination = scanner.nextLine();
                AirportCode destinationCode = stringToAirportCode(destination);
                ArrayList<Flight> foundFlights = flightCatalog.findNonPrivateFlights(sourceCode, destinationCode);
                printUnregisteredFlights(foundFlights);
                System.out.println("================================================");
                break;
            }

        }


        scanner.close();
    }

    private static void printUnregisteredFlights(ArrayList<Flight> foundFlights) {
        for (Flight element: foundFlights){
            System.out.println(element.toStringUnregistered());
        }
    }

    private static void printRegisteredFlights(ArrayList<Flight> foundFlights) {
        for (Flight element: foundFlights){
            System.out.println(element.toString());
        }
    }

    private static AirportCode stringToAirportCode(String value){
        try {
            return AirportCode.valueOf(value);
        }catch (Exception e){
            System.out.println("The value you entered is either wrong or does not exist in our system");
            return null;
        }

    }

    public Main() {
        this.registeredUsers = new HashMap<>();
        // Pre-populate with some users for demonstration purposes
        registerUser("user1", "password123");
    }

    // Method to add a new user
    public void registerUser(String username, String password) {
        registeredUsers.put(username, password);
    }
    // Method to check if a user is registered
    public boolean isUserRegistered(String username, String password) {
        String registeredPassword = registeredUsers.get(username);
        return registeredPassword != null && registeredPassword.equals(password);
    }






}