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

    public static String getString(int length) {
        StringBuilder str = new StringBuilder();
        int size = GetRandom.getInt(2, length+1);
        for (int i = 0; i < size; i++) {
            str.append((char) GetRandom.getInt('a', 'z'+1));
        }
        for (int i = size; i < length; i++) {
            str.append((char) GetRandom.getInt('1', '9'+1));
        }
        return str.toString();
    }
}
