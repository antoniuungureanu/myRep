import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;


/**
 * Created by Antoniu on 27-Oct-16.
 */
public abstract class Cell implements Runnable{
    private States state = States.Hungry;
    protected int feedingCycles = 0;
    private final int tStarve = 5;
    private final int tFull = 5;
    private final int dyingFood = 5;
    private long startTime;
    private long currentTime;
    private long deltaTime;

    public void eat(){
        Environment env = Environment.getEnvironment();
        List<FoodUnit> food = env.getFoodUnits();
        List<Cell> cells = env.getCells();
        LockReadWrite foodLocker = env.getFoodLocker();

        try {
            foodLocker.lockRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(FoodUnit f : food) {
            try {
                if (f.eatFood()) {
                    System.out.println(this.toString() + " cell ate");
                    if(feedingCycles >= 10)
                        state = States.Horny;
                    else
                        state = States.Normal;
                    feedingCycles++;
                    foodLocker.unlockRead();
                    return;
                }
            } catch (InterruptedException e) {
                System.out.println("I could not eat because threads...");
                e.printStackTrace();
            }
        }
        foodLocker.unlockRead();
    }

    public void die() {
        int foodToReplace = new Random().nextInt(dyingFood - 1) + 1;
        Environment env = Environment.getEnvironment();
        List<Cell> cells = env.getCells();
        LockReadWrite lockCells = env.getLockCells();
        List<FoodUnit> food = env.getFoodUnits();
        LockReadWrite foodLock = env.getFoodLocker();

        try {
            foodLock.lockRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (FoodUnit f : food) {
            if (foodToReplace <= 0) {
                break;
            }
            try {
                if (f.setFood()) {
                    foodToReplace--;
                }
            } catch (InterruptedException e) {
                System.out.println("Could not replace food");
                e.printStackTrace();
            }
        }

        foodLock.unlockRead();

        try {
           foodLock.lockWrite();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (foodToReplace > 0) {
                FoodUnit fUnit = new FoodUnit();
                food.add(fUnit);
                foodToReplace--;
        }

        foodLock.unlockWrite();

        try {
            lockCells.lockWrite();

            for (int i = 0; i < cells.size(); i++) {
                if (cells.get(i) == this) {
                    cells.remove(i);
                    break;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lockCells.unlockWrite();
        }
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
                        System.out.println(this.toString() + " cell got Hungry");
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
                    if (deltaTime < tFull * 100 && feedingCycles >= 10) {
                        reproduce();
                    } else {
                        System.out.println(this.toString() + " cell is Horny no more");
                        state = States.Hungry;
                        startTime = System.currentTimeMillis();
                    }
                }
                break;
                case Dead: {
                    System.out.println(this.toString() + " cell is Dead");
                    die();
                    return;
                }
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
