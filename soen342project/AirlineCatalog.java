import java.util.ArrayList;

public class AirlineCatalog {
    public AirlineCatalog(ArrayList<Airline> airlineCatalog) {
        this.airlineCatalog = airlineCatalog;
    }

    public ArrayList<Airline> getAirlineCatalog() {
        return airlineCatalog;
    }

    public void setAirlineCatalog(ArrayList<Airline> airlineCatalog) {
        this.airlineCatalog = airlineCatalog;
    }

    private ArrayList<Airline> airlineCatalog;
    public AirlineCatalog(){

    }
}
