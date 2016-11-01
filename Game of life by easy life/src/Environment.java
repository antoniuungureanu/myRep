import java.util.ArrayList;

/**
 * Created by Antoniu on 27-Oct-16.
 */
class Environment {
    private ArrayList<FoodUnit> foodUnits;
    private ArrayList<Cell> cells;
    private static Environment referance;

    private Environment() {
    }

    public void setEnvironment(ArrayList<FoodUnit> foodUnits,  ArrayList<Cell> cells) {
        this.foodUnits = foodUnits;
        this.cells = cells;
    }

    public static Environment getEnvironment() {
        if (referance == null) {
            referance = new Environment();
        }
        return referance;
    }

    public ArrayList<FoodUnit> getFoodUnits() {
        return foodUnits;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
}
