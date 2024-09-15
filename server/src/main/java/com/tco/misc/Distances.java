package com.tco.misc;

import java.util.ArrayList;

public class Distances extends ArrayList<Long> {

    public Distances () {}

    public long total() {
        long total = 0;

        for (Long l : this) {
            total += l;
        } // End for each loop

        return total;
    } // End total() method
    
} // End Distances class
