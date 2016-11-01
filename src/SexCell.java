import java.util.List;

/**
 * Created by Antoniu on 27-Oct-16.
 */
public class SexCell extends Cell {
    private volatile boolean searching = false;

    @Override
    public void reproduce() {
        Environment env = Environment.getEnvironment();
        LockReadWrite lockCells = env.getLockCells();
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
            if (c instanceof SexCell && c != this) {
                //System.out.println(">>>>  "+ this.toString() + " " + searching +  " " +((SexCell) c).searching );
                success = ((SexCell)c).tryToReproduce();
                synchronized (this) {
                    if (success) {
                        searching = false;
                        //System.out.println(">>>> After mate" + this.toString() + " " + searching);
                        System.out.println(this.toString() + " Mated hurray " + c.toString());
                        feedingCycles = 0;
                    }
                }
            }
        }
        lockCells.unlockRead();


        //if success set searching false
    }

    public boolean tryToReproduce() {
        synchronized (this) {
            if (searching == false) {
                return false;
            }
            feedingCycles = 0;
            searching = false;
           // System.out.println(">>>> After sex" + this.toString() + " " + searching);
            return true;
        }
    }

    public boolean isSearching() {
        return searching;
    }
}