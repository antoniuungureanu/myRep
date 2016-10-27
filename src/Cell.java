import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Antoniu on 27-Oct-16.
 */
public abstract class Cell implements Runnable{
    private States state = States.Hungry;
    private final int tStarve = 5;
    private final int tFull = 5;
    private long startTime;
    private long currentTime;
    private long deltaTime;

    public void eat(){

    }

    public void die() {

    }

    public void run(){
        startTime = System.currentTimeMillis();


        while (true) {
            currentTime = System.currentTimeMillis();
            deltaTime = currentTime - startTime;

            switch (state) {
                case Normal:
                    if (deltaTime < tFull * 100)
                        break;
                    else {
                        startTime = System.currentTimeMillis();
                        state = States.Hungry;
                        break;
                    }

                case Hungry: {
                    if (deltaTime < tStarve * 100) {
                        eat();
                    } else {
                        state = States.Dead;
                    }
                    break;
                }
                case Horny: {
                    if (deltaTime < tFull * 100) {
                        reproduce();
                    } else {
                        state = States.Hungry;
                        startTime = System.currentTimeMillis();
                    }
                }
                break;
                case Dead: die(); break;
            }
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time * 100);
        } catch (InterruptedException e) {
            System.out.println("Thread sleep was interrupted");
        }
    }

    public abstract void reproduce();
}
