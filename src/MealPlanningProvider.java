import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealPlanningProvider {
    private Connection connection;

    public MealPlanningProvider(Connection connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        if (connection == null) {
            return recipes;
        }

        try {
            CallableStatement stmt = connection.prepareCall("{Call GetRecipes()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String recipeName = rs.getString("RecipeName");
                String cookbookName = rs.getString("CookbookName");
                int totalServings = rs.getInt("TotalServings");
                recipes.add(new Recipe(recipeName, cookbookName, totalServings));
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute stored procedure: " + e.getMessage());
        }

        return recipes;
    }
}