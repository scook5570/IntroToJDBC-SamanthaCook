import java.sql.Connection;

public class DataMgr {
    private IntroToDAL dal;

    public DataMgr() {
        dal = new IntroToDAL();
    }

    public Connection getMealPlanningConnection(String user, String password) {
        return dal.getMySQLConnection("MealPlanning", user, password);
    }

    public Connection getArcadeGamesConnection(String user, String password) {
        return dal.getMySQLConnection("ArcadeGames", user, password);
    }

    public Connection getVideoGameSystemsConnection(String user, String password) {
        return dal.getMySQLConnection("VideoGameSystems", user, password);
    }
}