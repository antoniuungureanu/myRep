import java.util.List;

/**
 * Created by Antoniu on 27-Oct-16.
 */
public class AsexCell extends Cell{

    @Override
    public void reproduce() {
        Environment env = Environment.getEnvironment();
        LockReadWrite lockCells = env.getLockCells();
        List<Cell> cells = env.getCells();
        try {
            lockCells.lockWrite();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Cell cell = new AsexCell();
        cells.add(cell);
        new Thread(cell).start();
        lockCells.unlockWrite();

        //set feedingcounter to 0
        feedingCycles = 0;
        System.out.println("I am happy");
    }
}
