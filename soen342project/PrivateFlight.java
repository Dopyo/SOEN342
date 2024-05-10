public class PrivateFlight extends Flight{
    private Airport handler;
    public PrivateFlight(){

    }

    public PrivateFlight(Aircraft aircraft, String flightCode, String actualDepartureTime, String scheduledDepartureTime, String scheduledArrivalTime, String estimatedArrivalTime, Airport source, Airport destination, Airport handler) {
        super(aircraft, flightCode, actualDepartureTime, scheduledDepartureTime, scheduledArrivalTime, estimatedArrivalTime, source, destination);
        this.handler = handler;
    }

    public PrivateFlight(Airport handler) {
        this.handler = handler;
    }

    public Airport getHandler() {
        return handler;
    }

    public void setHandler(Airport handler) {
        this.handler = handler;
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
