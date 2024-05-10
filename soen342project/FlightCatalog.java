import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

//TODO: Need to implement Singleton pattern
public class FlightCatalog {
    private static FlightCatalog instance;
    private ArrayList<Flight> flightCatalog;
    private ArrayList<Airline> listAirline;
    private ArrayList<Airport> listAirport;

    private ArrayList<Aircraft> listAircraft;
    private ArrayList<City> listCity;

    public ArrayList<Flight> getFlightCatalog() {
        return flightCatalog;
    }

    public void setFlightCatalog(ArrayList<Flight> flightCatalog) {
        this.flightCatalog = flightCatalog;
    }

    public FlightCatalog(ArrayList<Flight> flightCatalog) {
        this.flightCatalog = flightCatalog;
    }
    public static FlightCatalog getInstance(){
        if(instance == null){
            instance = new FlightCatalog();
        }
        return instance;
    }
    private FlightCatalog(){

        flightCatalog = new ArrayList<Flight>();
        listAirline = new ArrayList<Airline>();
        listAirport = new ArrayList<Airport>();
        listCity = new ArrayList<City>();
        listAircraft = new ArrayList<Aircraft>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:soen342project.sqlite");
            String selectSQL = "SELECT * from airlines";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectSQL);

            while(rs.next()){
                Airline al = new Airline(rs.getString("name"), new ArrayList<Aircraft>(), new ArrayList<NonPrivateFlight>());
                al.setId(rs.getInt("id"));
                listAirline.add(al);
            }

            selectSQL = "SELECT * from aircrafts";
            rs = stmt.executeQuery(selectSQL);

            while(rs.next()){
                boolean default_aircraft = true;
                for(Airline al : listAirline){

                    if(al.getId() == rs.getInt("airline_owner_id")){
                        Aircraft ac = new Aircraft(al, rs.getBoolean("status"));
                        ac.setId(rs.getInt("id"));
                        listAircraft.add(ac);
                        al.addAircraft(ac);
                        default_aircraft = false;
                    }
                }
                if(default_aircraft){
                    Aircraft ac = new Aircraft();
                    ac.setStatus(false);
                    ac.setId(rs.getInt("id"));
                    listAircraft.add(ac);
                }

            }

            selectSQL = "SELECT * from cities";
            rs = stmt.executeQuery(selectSQL);
            while(rs.next()){
                City ct = new City(rs.getString("name"), rs.getString("country"), rs.getString("temperature_info"), new ArrayList<Airport>());
                ct.setId(rs.getInt("id"));
                listCity.add(ct);
            }

            selectSQL = "SELECT * from airports";
            rs = stmt.executeQuery(selectSQL);
            while(rs.next()){
                for(City ct : listCity){
                    if(ct.getId() == rs.getInt("city_id")){
                        Airport ap = new Airport(rs.getString("name"), ct, string_to_airportcode(rs.getString("airport_code")), new ArrayList<PrivateFlight>());
                        for(Aircraft ac : listAircraft){
                            String[] available = null;
                            if(rs.getString("available") != null){
                                available = rs.getString("available").split(" ");
                            }
                            if(available == null){
                                available = new String[0];
                            }

                            if(Arrays.asList(available).contains(""+ac.getId())){
                                ap.addAircraft(ac);
                            }
                        }
                        ap.setId(rs.getInt("id"));
                        listAirport.add(ap);
                    }
                }

            }

            selectSQL = "SELECT * from non_private_flights";
            rs = stmt.executeQuery(selectSQL);
            while(rs.next()){
                NonPrivateFlight f = new CommercialFlight();
                if(rs.getString("type").equals("CommercialFlight")){
                    f = new CommercialFlight();
                }
                if(rs.getString("type").equals("CargoFlight")){
                    f = new CargoFlight();
                }
                for(Aircraft ac : listAircraft){
                    if(ac.getId() == rs.getInt("aircraft_id")){
                        f.setAircraft(ac);
                    }
                }

                for(Airline al : listAirline){
                    if(al.getId() == rs.getInt("airline_handler_id")){
                        f.setHandler(al);
                    }
                }

                f.setFlightCode(rs.getString("flight_code"));
                f.setActualDepartureTime(rs.getString("actual_departure_time"));
                f.setScheduledDepartureTime(rs.getString("scheduled_departure_time"));
                f.setScheduledArrivalTime(rs.getString("scheduled_arrival_time"));

                for(Airport ap : listAirport){
                    if(ap.getId() == rs.getInt("airport_source_id")){
                        f.setSource(ap);
                    }
                    if(ap.getId() == rs.getInt("airport_destination_id")){
                        f.setDestination(ap);
                    }
                }
                f.setId(rs.getInt("id"));
                flightCatalog.add(f);
            }

            selectSQL = "SELECT * from private_flights";
            rs = stmt.executeQuery(selectSQL);
            while(rs.next()){
                PrivateFlight f = new PrivateFlight();
                for(Aircraft ac : listAircraft){
                    if(ac.getId() == rs.getInt("aircraft_id")){
                        f.setAircraft(ac);
                    }
                }

                f.setFlightCode(rs.getString("flight_code"));
                f.setActualDepartureTime(rs.getString("actual_departure_time"));
                f.setScheduledDepartureTime(rs.getString("scheduled_departure_time"));
                f.setScheduledArrivalTime(rs.getString("scheduled_arrival_time"));

                for(Airport ap : listAirport){
                    if(ap.getId() == rs.getInt("airport_handler_id")){
                        f.setHandler(ap);
                    }
                    if(ap.getId() == rs.getInt("airport_source_id")){
                        f.setSource(ap);
                    }
                    if(ap.getId() == rs.getInt("airport_destination_id")){
                        f.setDestination(ap);
                    }
                }
                f.setId(rs.getInt("id"));
                flightCatalog.add(f);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public ArrayList<Flight> findNonPrivateFlights(AirportCode source, AirportCode destination){
        ArrayList<Flight> foundFlights = new ArrayList<>();

        for(Flight element: this.flightCatalog){

            if(element instanceof PrivateFlight){
                continue;
            }
            if (element.getSource().getCode() == source && element.getDestination().getCode() == destination){
                foundFlights.add(element);
            }
        }
        return foundFlights;
    }

    public ArrayList<Flight> findPrivateFlights(AirportCode airport){
        ArrayList<Flight> foundFlights = new ArrayList<>();

        for(Flight element: this.flightCatalog){

            if(element instanceof PrivateFlight){
                if (element.getSource().getCode() == airport || element.getDestination().getCode() == airport){
                    foundFlights.add(element);
                }
            }
        }
        return foundFlights;
    }

    public boolean isScheduleConflict(String departure_time, String arrival_time, String source_airport, String destination_airport){
        String regex = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(departure_time).matches() || !pattern.matcher(arrival_time).matches()){
            return true;
        }
        for(Flight f : flightCatalog){
            if(f.getSource().getCode() == string_to_airportcode(source_airport)){
                if(f.getScheduledDepartureTime() == departure_time){
                    return true;
                }
            }
            if(f.getDestination().getCode() == string_to_airportcode(destination_airport)){
                if(f.getScheduledArrivalTime() == arrival_time){
                    return true;
                }
            }
        }
        return false;
    }


    //TODO: Change parameter names to match Java conventions, i.e. departureTime, arrivalTime
    public boolean registerPrivateFlight(String departure_time, String arrival_time, String source_airport, String destination_airport){
        PrivateFlight f = new PrivateFlight();
        f.setScheduledDepartureTime(departure_time);
        f.setScheduledArrivalTime(arrival_time);
        boolean source = false;
        boolean destination = false;
        for(Airport a : listAirport){
            if(a.getCode() == string_to_airportcode(source_airport) && !a.getAvailableAircrafts().isEmpty()){

                Aircraft freeac = a.findAndBookFreeAircraft();
                f.setSource(a);
                f.setHandler(a);
                f.setAircraft(freeac);
                source = true;
            }
            if(a.getCode() == string_to_airportcode(destination_airport)){
                f.setDestination(a);
                destination = true;
            }
        }
        if(!(source && destination)){
            return source && destination;
        }
        int id = 1;
        for(int i = 0; i < flightCatalog.size(); i++){
            if(flightCatalog.get(i).getId() == id){
                id++;
                i=-1;
            }
        }
        f.setId(id);
        flightCatalog.add(f);
        DatabaseInterface.savePrivateFlight(f);
        //TODO: Add flight to catalog/ arraylist
        return source && destination;
    }
    public boolean registerNonPrivateFlight(String departureTime, String arrivalTime, String sourceAirport, String destinationAirport, String flightType, String airlineOption){
        switch (flightType){
            case "1":{
                boolean sourceFound = false;
                boolean destinationFound = false;
                boolean airlineFound = false;
                boolean freePlaneFound = false;
                CommercialFlight commercialFlight = new CommercialFlight();
                commercialFlight.setScheduledDepartureTime(departureTime);
                commercialFlight.setScheduledArrivalTime(arrivalTime);
                for(Airport airport : listAirport){
                    if(airport.getCode() == string_to_airportcode(sourceAirport)){
                        commercialFlight.setSource(airport);
                        sourceFound = true;
                    }else{
                        System.out.println("No matching source airport was found");
                    }
                    if(airport.getCode() == string_to_airportcode(destinationAirport)){
                        commercialFlight.setDestination(airport);
                        destinationFound = true;
                    }else{
                        System.out.println("No matching destination airport was found");
                    }
                }
                if(!sourceFound || !destinationFound){
                    System.out.println("source or destination not found");
                }
                Airline handler = findAirline(airlineOption);
                if (handler != null){
                    commercialFlight.setHandler(handler);
                    airlineFound = true;
                } else{
                    System.out.println("No matching airline was found");
                    airlineFound = false;
                }
                Aircraft ac = handler.findAndBookFreeAircraft();
                if (ac != null){
                    System.out.println("Sucessfully added flight to catalog");
                    commercialFlight.setAircraft(ac);
                    int id = 1;
                    for(int i = 0; i < flightCatalog.size(); i++){
                        if(flightCatalog.get(i).getId() == id){
                            id++;
                            i=-1;
                        }
                    }
                    commercialFlight.setId(id);

                    DatabaseInterface.saveNonPrivateFlight(commercialFlight);
                    flightCatalog.add(commercialFlight);
                    freePlaneFound = true;
                }else{
                    System.out.println("No free aircrafts were found for that airline");
                    freePlaneFound = false;
                }
                return sourceFound && destinationFound && airlineFound && freePlaneFound;
            }
            case "2":{
                boolean sourceFound = false;
                boolean destinationFound = false;
                boolean airlineFound = false;
                boolean freePlaneFound = false;
                CargoFlight cargoFlight = new CargoFlight();
                cargoFlight.setScheduledDepartureTime(departureTime);
                cargoFlight.setScheduledArrivalTime(arrivalTime);
                for(Airport airport : listAirport){
                    if(airport.getCode() == string_to_airportcode(sourceAirport)){
                        cargoFlight.setSource(airport);
                        sourceFound = true;
                    }
                    if(airport.getCode() == string_to_airportcode(destinationAirport)){
                        cargoFlight.setDestination(airport);
                        destinationFound = true;
                    }
                }
                if(!sourceFound || !destinationFound){
                    System.out.println("source or destination not found");
                }

                Airline handler = findAirline(airlineOption);
                if (handler != null){
                    cargoFlight.setHandler(handler);
                    airlineFound = true;
                } else{
                    System.out.println("No matching airline was found");
                    airlineFound = false;
                }
                Aircraft ac = handler.findAndBookFreeAircraft();
                if (ac != null){
                    System.out.println("Sucessfully added flight to catalog");
                    cargoFlight.setAircraft(ac);
                    int id = 1;
                    for(int i = 0; i < flightCatalog.size(); i++){
                        if(flightCatalog.get(i).getId() == id){
                            id++;
                            i=-1;
                        }
                    }
                    cargoFlight.setId(id);
                    DatabaseInterface.saveNonPrivateFlight(cargoFlight);
                    flightCatalog.add(cargoFlight);
                    freePlaneFound = true;
                }else{
                    System.out.println("No free aircrafts were found for that airline");
                    freePlaneFound = false;
                }
                return sourceFound && destinationFound && airlineFound && freePlaneFound;
            }
            default:{
                return false;
            }
        }
    }

    private Airline findAirline(String airlineOption){
        switch(airlineOption){
            case "1":{
                //TODO: Opportunity to refactor here
                String search = "Air Canada";
                for(Airline airline: listAirline){
                    if (airline.getName().equals(search)){
                        return airline;
                    }
                }
                break;
            }

            case "2":{
                //TODO: Opportunity to refactor here
                String search = "Delta Airlines";
                for(Airline airline: listAirline){
                    if (airline.getName().equals(search)){
                        return airline;
                    }
                }
                break;
            }

            case "3": {
                //TODO: Opportunity to refactor here
                String search = "United Airlines";
                for(Airline airline: listAirline){
                    if (airline.getName().equals(search)){
                        return airline;
                    }
                }
                break;
            }

            default:{
                return null;
            }
        }
        return null;
    }

    public void registerAirport(String[] airport_string){
        Airport ap = new Airport();
        ap.setName(airport_string[0].trim());
        for(City ct : listCity){
            if(ct.getId() == Integer.parseInt(airport_string[1].trim())){
                ap.setCity(ct);
            }
        }
        ap.setCode(string_to_airportcode(airport_string[2].trim()));
        String[] available = airport_string[3].trim().split(" ");
        for(Aircraft ac : listAircraft){
            if(Arrays.asList(available).contains(""+ac.getId())){
                ap.addAircraft(ac);
            }
        }

        int id = 1;
        for(int i = 0; i < listAirport.size(); i++){
            if(listAirport.get(i).getId() == id){
                id++;
                i=-1;
            }
        }
        ap.setId(id);
        listAirport.add(ap);
        DatabaseInterface.saveAirport(ap);
        System.out.println(listAirport.toString());
    }


    public AirportCode string_to_airportcode(String arg){
        AirportCode ac = null;
        try{
            ac = AirportCode.valueOf(arg);
        }catch(IllegalArgumentException ex){
            System.out.println("AirportCode does not exist");
        }
        return ac;
    }

}
