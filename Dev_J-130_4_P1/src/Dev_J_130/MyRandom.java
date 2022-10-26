
package Dev_J_130;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MyRandom {
    
    public static int getTimeSleep(){
        
        final Random random = new Random();
        int tm = random.nextInt(10000);
        if (tm < 100)
            tm *= 100;
        else if (tm < 1000)
            tm *= 10;
        return tm;
    }
    public static int getNumberOfProduct()
    {
        final Random random = new Random();
        int np = random.nextInt(10);
        if (np==0)
            np = 1;
        return np;
    } 
    public static String timeNow(){
        
        return "Текущее время операции: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ". ";
    }
}
