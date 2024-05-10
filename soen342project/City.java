import java.util.ArrayList;

public class City {
    private int id;
    private String name;
    private String country;
    private String temperatureInfo;
    private ArrayList<Airport> airports;

    public City(){

    }

    public City(String name, String country, String temperatureInfo, ArrayList<Airport> airports) {
        this.name = name;
        this.country = country;
        this.temperatureInfo = temperatureInfo;
        this.airports = airports;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){ this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperatureInfo() {
        return temperatureInfo;
    }

    public void setTemperatureInfo(String temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }

    public void setAirports(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    @Override
    public String toString() {
        return "City{\n" +
                "\tname='" + name + '\'' +
                ",\n\tcountry='" + country + '\'' +
                ",\n\ttemperatureInfo='" + temperatureInfo + '\'' +
                ",\n\tairports=" + airports +
                "\n}";
    }
}
