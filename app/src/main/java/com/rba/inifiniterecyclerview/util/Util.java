package com.rba.inifiniterecyclerview.util;

import java.util.Random;

/**
 * Created by Ricardo Bravo on 13/07/16.
 */

public class Util {

    public static int generateNumber(){
        Random r = new Random();
        return r.nextInt(2) + 1;
    }

}
