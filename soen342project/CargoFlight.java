public class CargoFlight extends NonPrivateFlight{

    public CargoFlight(){

    }

    public CargoFlight(Aircraft aircraft, String flightCode, String actualDepartureTime, String scheduledDepartureTime, String scheduledArrivalTime, String estimatedArrivalTime, Airport source, Airport destination, Airline handler) {
        super(aircraft, flightCode, actualDepartureTime, scheduledDepartureTime, scheduledArrivalTime, estimatedArrivalTime, source, destination, handler);
    }

    public CargoFlight(Airline handler) {
        super(handler);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String toStringUnregistered(){
        return super.toStringUnregistered();
    }
}
