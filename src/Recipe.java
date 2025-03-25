public class Recipe {
    private String recipeName;
    private String cookbookName;
    private int totalServings;

    public Recipe(String recipeName, String cookbookName, int totalServings) {
        this.recipeName = recipeName;
        this.cookbookName = cookbookName;
        this.totalServings = totalServings;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getCookbookName() {
        return cookbookName;
    }

    public void setCookbookName(String cookbookName) {
        this.cookbookName = cookbookName;
    }

    public int getTotalServings() {
        return totalServings;
    }

    public void setTotalServings(int totalServings) {
        this.totalServings = totalServings;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", cookbookName='" + cookbookName + '\'' +
                ", totalServings=" + totalServings +
                '}';
    }
}