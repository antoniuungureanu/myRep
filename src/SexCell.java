import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Antoniu on 27-Oct-16.
 */
public class SexCell extends Cell {
    private volatile boolean searching = false;
    private boolean readyToMultiply = false;
//    private Semaphore multiply = new Semaphore(1, true);

    @Override
    public void reproduce() {
        Environment env = Environment.getEnvironment();
        LockReadWrite lockCells = env.getLockCells();
        boolean createCell = false;
        synchronized (this) {
            if (feedingCycles >= 10)
                searching = true;
            // System.out.println(">>>> " + this.toString() + " " + searching);
        }
        List<Cell> cells = env.getCells();
        boolean success = false;
        try {
            lockCells.lockRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Cell c : cells) {
            if (c instanceof SexCell && c != this && searching) {
                //System.out.println(">>>>  "+ this.toString() + " " + searching +  " " +((SexCell) c).searching );
                success = ((SexCell) c).tryToReproduce();

                synchronized (env) {
                    if (success && ((SexCell) c).readyToMultiply) {
//                        reproduceertyr
                        createCell = true;
                        System.out.println(cells.size() + ">>>>>>>>>>>reproduuuce once!");
                        readyToMultiply = false;
                        searching = false;
                        feedingCycles = 0;
                        break;
                    }
                }

               /*synchronized (this) {
                    if (success) {
                        searching = false;
                        //System.out.println(">>>> After mate" + this.toString() + " " + searching);
                        System.out.println(this.toString() + " Mated hurray " + c.toString());
                        feedingCycles = 0;
                    }
                }*/
            }
        }
        lockCells.unlockRead();
        if (createCell) {
            try {
                lockCells.lockWrite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I create it");
            Cell cell = new SexCell();
            cells.add(cell);
            new Thread(cell).start();
            lockCells.unlockWrite();

            //if success set searching false
        }
    }

    public boolean tryToReproduce() {
        synchronized (this) {
            if (searching == false) {
                return false;
            }
            feedingCycles = 0;
            readyToMultiply = true;
            searching = false;
           // System.out.println(">>>> After sex" + this.toString() + " " + searching);
            return true;
        }
    }

    public boolean isSearching() {
        return searching;
    }

}