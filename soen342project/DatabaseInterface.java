import java.sql.*;

public class DatabaseInterface {
    public static void savePrivateFlight(PrivateFlight pf){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:soen342project.sqlite");
            String insertSQL = "INSERT INTO private_flights(id, airport_handler_id, aircraft_id, flight_code, actual_departure_time, scheduled_departure_time, scheduled_arrival_time, airport_source_id, airport_destination_id) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
            pstmt.setInt(1, pf.getId());
            pstmt.setInt(2, pf.getHandler().getId());
            pstmt.setInt(3, pf.getAircraft().getId());
            pstmt.setString(4, pf.getFlightCode());
            pstmt.setString(5, pf.getActualDepartureTime());
            pstmt.setString(6, pf.getScheduledDepartureTime());
            pstmt.setString(7, pf.getScheduledArrivalTime());
            pstmt.setInt(8, pf.getSource().getId());
            pstmt.setInt(9, pf.getDestination().getId());
            pstmt.execute();

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

    public static void saveNonPrivateFlight(NonPrivateFlight npf){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:soen342project.sqlite");
            String insertSQL = "INSERT INTO non_private_flights(id, type, airline_handler_id, aircraft_id, flight_code, actual_departure_time, scheduled_departure_time, scheduled_arrival_time, airport_source_id, airport_destination_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
            pstmt.setInt(1, npf.getId());
            if(npf instanceof  CommercialFlight){
                pstmt.setString(2, "CommercialFlight");
            }
            if(npf instanceof  CargoFlight){
                pstmt.setString(2, "CargoFlight");
            }
            pstmt.setInt(3, npf.getHandler().getId());
            pstmt.setInt(4, npf.getAircraft().getId());
            pstmt.setString(5, npf.getFlightCode());
            pstmt.setString(6, npf.getActualDepartureTime());
            pstmt.setString(7, npf.getScheduledDepartureTime());
            pstmt.setString(8, npf.getScheduledArrivalTime());
            pstmt.setInt(9, npf.getSource().getId());
            pstmt.setInt(10, npf.getDestination().getId());
            pstmt.execute();

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

    public static void saveAirport(Airport ap){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:soen342project.sqlite");
            String insertSQL = "INSERT INTO airports(id, name, city_id, airport_code, available) VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
            pstmt.setInt(1, ap.getId());
            pstmt.setString(2, ap.getName());
            pstmt.setInt(3, ap.getCity().getId());
            pstmt.setString(4, ap.getCode().name());
            StringBuilder av = new StringBuilder();
            for(Aircraft ac : ap.getAvailableAircrafts()){
                av.append(ac.getId()).append(" ");
            }
            pstmt.setString(5, av.toString());
            pstmt.execute();

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

}
