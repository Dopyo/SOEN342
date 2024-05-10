public class Aircraft {

    private int id;
    private Airline owner;
    private Boolean status;
    private boolean available;
    public Aircraft(){

    }

    public Aircraft(String ac001) {
    }


    public Aircraft(Airline owner, Boolean status) {
        this.owner = owner;
        this.status = status;
        this.available = true; // false = available, true = busy
    }

    public int getId() {
        return id;
    }

    public void setId(int id){ this.id = id;}

    public Airline getOwner() {
        return owner;
    }
    public void setOwner(Airline owner) {
        this.owner = owner;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    // Getter for aircraft availability
    public boolean isAvailable() {
        return available;
    }

    // Setter for aircraft availability
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        String oname = "null";
        if(owner != null){
            oname = owner.getName();
        }
        return "Aircraft{\n" +
                "\towner=" + oname +
                ",\n\tstatus=" + status +
                "\n}";
    }
}
