import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Antoniu on 27-Oct-16.
 */
public class theMain {
    private static ArrayList<FoodUnit> fUnits= new ArrayList<FoodUnit>();
    private static ArrayList<Cell> cells = new ArrayList<>();
    private static final int startingFoodNo = 10;
    private static final int startingAsexNo = 15;
    private static final int startingSexNo = 10;


    public static void main(String args[]) {
        for (int i = 0; i < (startingAsexNo > startingSexNo? startingAsexNo:startingSexNo); i++) {
            if (i < startingAsexNo) {
                 cells.add(new AsexCell());
            }
            if(i < startingSexNo) {
                cells.add(new SexCell());
            }
        }

        for (int i=0; i < startingFoodNo; i++) {
            fUnits.add(new FoodUnit());
        }

        Environment env = Environment.getEnvironment();
        env.setEnvironment(fUnits, cells);

        for (int i = 0; i < startingSexNo + startingAsexNo; i++) {
            new Thread(cells.get(i)).start();
        }




    }

}
