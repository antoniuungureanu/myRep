import java.util.concurrent.Semaphore;

/**
 * Created by Antoniu on 27-Oct-16.
 */
public class FoodUnit {
    private boolean food = true;

    private final int MAX_VAL = 1;
    private final Semaphore available = new Semaphore(MAX_VAL, true);

    public boolean eatFood() throws InterruptedException{
        available.acquire();
        if (food == true) {
            food = false;
            available.release();
            return true;
        } else {
            available.release();
            return false;
        }
    }

    public boolean setFood() throws InterruptedException{
        available.acquire();
        if(food == true) {
            available.release();
            return false;
        } else {
            food = true;
            available.release();
            return true;
        }
    }
}
