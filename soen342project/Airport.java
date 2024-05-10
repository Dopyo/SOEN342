import java.util.ArrayList;

public class Airport {

    private int id;
    private String name;
    private City city;

    private AirportCode code;

    private ArrayList<PrivateFlight> flights;

    private ArrayList<Aircraft> availableAircrafts;


    public Airport(){
        this.availableAircrafts = new ArrayList<>();
    }

    public Airport(String name, City city, AirportCode code, ArrayList<PrivateFlight> flights) {
        this.name = name;
        this.city = city;
        this.code = code;
        this.flights = flights;
        this.availableAircrafts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id){this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public AirportCode getCode() {
        return code;
    }

    public void setCode(AirportCode code) {
        this.code = code;
    }

    public ArrayList<PrivateFlight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<PrivateFlight> flights) {
        this.flights = flights;
    }

    // Getter method for available aircraft list
    public ArrayList<Aircraft> getAvailableAircrafts() {
        return availableAircrafts;
    }

    public void setAvailableAircrafts(ArrayList<Aircraft> availableAircrafts) {

        this.availableAircrafts= availableAircrafts;
    }

    // Method to add an aircraft to the available aircraft list
    public void addAircraft(Aircraft aircraft) {
        availableAircrafts.add(aircraft);
    }

    public Aircraft findAndBookFreeAircraft(){
        for(Aircraft aircraft: availableAircrafts){
            if (!aircraft.getStatus()){
                aircraft.setStatus(true);
                return aircraft;
            }else{
                return null;
            }
        }
        return null;
    }
    // Method to remove an aircraft from the available aircraft list
    public void removeAircraft(Aircraft aircraft) {
        availableAircrafts.remove(aircraft);
    }
    @Override
    public String toString() {

        return "Airport{\n" +
                "\tname='" + name + '\'' +
                ",\n\tcity=" + city.toString() +
                ",\n\tcode=" + code.toString() +
                "\n}";
    }
}
