public abstract class Flight {
    protected int id;
    protected Aircraft aircraft; // Changed from String to Aircraft, le tme know if this does anything bad @Dopyo
    protected String flightCode;
    protected String actualDepartureTime;
    protected String scheduledDepartureTime;
    protected String scheduledArrivalTime;
    protected String estimatedArrivalTime;
    protected Airport source;
    protected Airport destination;

    public Flight(Aircraft aircraft, String flightCode, String actualDepartureTime, String scheduledDepartureTime, String scheduledArrivalTime, String estimatedArrivalTime, Airport source, Airport destination) {
        this.aircraft = aircraft;
        this.flightCode = flightCode;
        this.actualDepartureTime = actualDepartureTime;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.source = source;
        this.destination = destination;
    }

    public Flight(){

    }

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(String actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public String getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public void setScheduledDepartureTime(String scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public void setScheduledArrivalTime(String scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(String estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }


    @Override
    public String toString() {
        return "Flight{\n" +
                "\taircraft=" + aircraft +
                ",\n\tflightCode='" + flightCode + '\'' +
                ",\n\tactualDepartureTime='" + actualDepartureTime + '\'' +
                ",\n\tscheduledDepartureTime='" + scheduledDepartureTime + '\'' +
                ",\n\tscheduledArrivalTime='" + scheduledArrivalTime + '\'' +
                ",\n\testimatedArrivalTime='" + estimatedArrivalTime + '\'' +
                ",\n\tsource=" + source +
                ",\n\tdestination=" + destination +
                "\n}";
    }

    public String toStringUnregistered(){
        return "Flight{\n" +
                "\tflightCode='" + flightCode + '\'' +
                ",\n\tsource=" + source +
                ",\n\tdestination=" + destination +
                "\n}";
    }
}
