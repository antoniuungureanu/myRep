import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Antoniu on 27-Oct-16.
 */
public class theMain {
    private static List<FoodUnit> fUnits= Collections.synchronizedList(new ArrayList<FoodUnit>());
    private static List<Cell> cells = Collections.synchronizedList(new ArrayList<Cell>());
    private static final int startingFoodNo = 190;
    private static final int startingAsexNo = 7;
    private static final int startingSexNo = 8;


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
