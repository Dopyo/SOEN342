public abstract class NonPrivateFlight extends Flight{
    protected Airline handler;

    public NonPrivateFlight(Aircraft aircraft, String flightCode, String actualDepartureTime, String scheduledDepartureTime, String scheduledArrivalTime, String estimatedArrivalTime, Airport source, Airport destination, Airline handler) {
        super(aircraft, flightCode, actualDepartureTime, scheduledDepartureTime, scheduledArrivalTime, estimatedArrivalTime, source, destination);
        this.handler = handler;
    }

    public NonPrivateFlight(Airline handler) {
        this.handler = handler;
    }

    public NonPrivateFlight(){

    }
    public Airline getHandler() {
        return handler;
    }

    public void setHandler(Airline handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        // Assuming the Airline class has a getName() method.
        String airlineHandlerInfo = (getHandler() != null) ? getHandler().getName() : "No handler assigned";
        return super.toString() + ", Airline Handler: " + airlineHandlerInfo;
    }
    @Override
    public String toStringUnregistered(){
        return super.toStringUnregistered();
    }
}
