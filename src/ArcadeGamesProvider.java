import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArcadeGamesProvider {
    private Connection connection;

    public ArcadeGamesProvider(Connection connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

    // Method that returns the results of a statement to the caller
    public List<String> getGames() {
        List<String> games = new ArrayList<>();
        if (connection == null) {
            return games;
        }

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GameName FROM Game");

            while (rs.next()) {
                games.add(rs.getString("GameName"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute statement: " + e.getMessage());
        }

        return games;
    }

    // Method that returns the results of a prepared statement to the caller
    public List<String> getGamesByDeveloper(String developerName) {
        List<String> games = new ArrayList<>();
        if (connection == null) {
            return games;
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT GameName FROM Game WHERE DeveloperName = ?");
            pstmt.setString(1, developerName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                games.add(rs.getString("GameName"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute prepared statement: " + e.getMessage());
        }

        return games;
    }

    // Method that returns the results of a callable statement to the caller
    public List<String> getGamesByReleaseYear(int releaseYear) {
        List<String> games = new ArrayList<>();
        if (connection == null) {
            return games;
        }

        try {
            CallableStatement cstmt = connection.prepareCall("{Call GetGamesByReleaseYear(?)}");
            cstmt.setInt(1, releaseYear);
            ResultSet rs = cstmt.executeQuery();

            while (rs.next()) {
                games.add(rs.getString("GameName"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute callable statement: " + e.getMessage());
        }

        return games;
    }
}