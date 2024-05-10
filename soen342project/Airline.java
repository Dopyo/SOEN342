import java.util.ArrayList;

public class Airline {
    private int id;
    private String name;
    private ArrayList<Aircraft> fleet;
    private ArrayList<NonPrivateFlight> flights;
    public Airline(){

    }

    public Airline(String name, ArrayList<Aircraft> fleet, ArrayList<NonPrivateFlight> flights) {
        this.name = name;
        this.fleet = fleet;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Aircraft> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Aircraft> fleet) {
        this.fleet = fleet;
    }

    public ArrayList<NonPrivateFlight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<NonPrivateFlight> flights) {
        this.flights = flights;
    }

    public void addAircraft(Aircraft aircraft){
        fleet.add(aircraft);
    }

    public Aircraft findAndBookFreeAircraft(){
        for(Aircraft aircraft: fleet){
            if (!aircraft.getStatus()){
                aircraft.setStatus(true);
                return aircraft;
            }else{
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Airline{\n" +
                "\tname='" + name + '\'' +
                ",\n\tfleet=" + fleet +
                ",\n\tflights=" + flights +
                "\n}";
    }
}
