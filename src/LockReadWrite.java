/**
 * Created by Antoniu on 01-Nov-16.
 */
public class LockReadWrite {
    private int readers = 0;
    private int writers = 0;
    private int writeRequest = 0;

    public synchronized void lockRead() throws InterruptedException{
        while (writers > 0 || writeRequest > 0) {
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        if (readers > 0)
            readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequest++;
        while (readers > 0 || writers > 0) {
            wait();
        }
        writeRequest--;
        writers++;
    }

    public synchronized void unlockWrite() {
        if(writers > 0)
            writers--;
        notifyAll();
    }
}
