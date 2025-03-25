import java.util.List;
import java.util.Scanner;

public class IntroToPresentationLayer {
    public static void main(String[] args) {
        Scanner userInformation = new Scanner(System.in);
        System.out.println("Enter username and password:");
        String userName = userInformation.nextLine();
        String password = userInformation.nextLine();

        DataMgr dataMgr = new DataMgr();
        MealPlanningProvider mealPlanningProvider = new MealPlanningProvider(
                dataMgr.getMealPlanningConnection(userName, password));
        ArcadeGamesProvider arcadeGamesProvider = new ArcadeGamesProvider(
                dataMgr.getArcadeGamesConnection(userName, password));

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Run GetRecipes stored procedure against the MealPlanning database");
            System.out.println("2. Run method that returns the results of a statement (ArcadeGames)");
            System.out.println("3. Run method that returns the results of a prepared statement (ArcadeGames)");
            System.out.println("4. Run method that returns the results of a callable statement (ArcadeGames)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = userInformation.nextInt();
            userInformation.nextLine(); 

            switch (choice) {
                case 1:
                    runGetRecipesStoredProcedure(mealPlanningProvider);
                    break;
                case 2:
                    runStatementMethod(arcadeGamesProvider);
                    break;
                case 3:
                    runPreparedStatementMethod(arcadeGamesProvider, userInformation);
                    break;
                case 4:
                    runCallableStatementMethod(arcadeGamesProvider, userInformation);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void runGetRecipesStoredProcedure(MealPlanningProvider mealPlanningProvider) {
        if (mealPlanningProvider.isConnected()) {
            System.out.println("Successfully connected to the MealPlanning database");

            List<Recipe> recipes = mealPlanningProvider.getRecipes();
            for (Recipe recipe : recipes) {
                System.out.println(recipe);
            }
        } else {
            System.out.println("Failed to connect to the MealPlanning database");
        }
    }

    private static void runStatementMethod(ArcadeGamesProvider arcadeGamesProvider) {
        if (arcadeGamesProvider.isConnected()) {
            System.out.println("Successfully connected to the ArcadeGames database");

            List<String> games = arcadeGamesProvider.getGames();
            System.out.println("All Games:");
            for (String game : games) {
                System.out.println(game);
            }
        } else {
            System.out.println("Failed to connect to the ArcadeGames database");
        }
    }

    private static void runPreparedStatementMethod(ArcadeGamesProvider arcadeGamesProvider, Scanner userInformation) {
        if (arcadeGamesProvider.isConnected()) {
            System.out.println("Enter developer name:");
            String developerName = userInformation.nextLine();

            List<String> gamesByDeveloper = arcadeGamesProvider.getGamesByDeveloper(developerName);
            System.out.println("Games by Developer:");
            for (String game : gamesByDeveloper) {
                System.out.println(game);
            }
        } else {
            System.out.println("Failed to connect to the ArcadeGames database");
        }
    }

    private static void runCallableStatementMethod(ArcadeGamesProvider arcadeGamesProvider, Scanner userInformation) {
        if (arcadeGamesProvider.isConnected()) {
            System.out.println("Enter release year:");
            int releaseYear = userInformation.nextInt();
            userInformation.nextLine(); // Consume newline

            List<String> gamesByReleaseYear = arcadeGamesProvider.getGamesByReleaseYear(releaseYear);
            System.out.println("Games Released in " + releaseYear + ":");
            for (String game : gamesByReleaseYear) {
                System.out.println(game);
            }
        } else {
            System.out.println("Failed to connect to the ArcadeGames database");
        }
    }
}