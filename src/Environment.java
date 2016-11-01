import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Antoniu on 27-Oct-16.
 */
class Environment {
    private final LockReadWrite lockCells= new LockReadWrite();
    private final LockReadWrite foodLocker = new LockReadWrite();
    private List<FoodUnit> foodUnits;
    private List<Cell> cells;
    private static Environment referance;

    private Environment() {
    }

    public void setEnvironment(List<FoodUnit> foodUnits, List<Cell> cells) {
        this.foodUnits = foodUnits;
        this.cells = cells;
    }

    public LockReadWrite getFoodLocker() {
        return foodLocker;
    }

    public LockReadWrite getLockCells() {
        return lockCells;
    }

    public static Environment getEnvironment() {
        if (referance == null) {
            referance = new Environment();
        }
        return referance;
    }

    public List<FoodUnit> getFoodUnits() {
        return foodUnits;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
