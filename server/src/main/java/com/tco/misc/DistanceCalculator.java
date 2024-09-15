package com.tco.misc;

import static java.lang.Double.parseDouble;

import static java.lang.Math.toRadians;
import static java.lang.Math.atan2;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.random;


public abstract class DistanceCalculator {

    public abstract Long between(
        GeographicCoordinate from,
        GeographicCoordinate to,
        double earthRadius);
    
    
}
