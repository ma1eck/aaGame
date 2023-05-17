package Utils;

import java.util.Random;

public class GetRandom {
    private static Random random = null;
    private static Random random(){
        if (random == null) random = new Random();
        return random;
    }
    public static int getInt(int lowerBound, int upperBound){
        return random().nextInt(lowerBound,upperBound);
    }
}
