package com.tco.misc;

import static java.lang.Double.parseDouble;
import com.tco.misc.GeographicCoordinate;

import static java.lang.Math.toRadians;
import static java.lang.Math.atan2;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;

public class VincentyCalculator extends DistanceCalculator {
    
    public Long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {

        double phi1 = from.latRadians();
        double phi2 = to.latRadians();
        double lambda1 = from.lonRadians();
        double lambda2 = to.lonRadians();
        double dLambda = deltaLambdaCalc(lambda1,lambda2);

        double arctanTop = sqrt(pow((cos(phi2)*sin(dLambda)),2) + pow((cos(phi1)*sin(phi2) - sin(phi1)*cos(phi2)*cos(dLambda)),2));
        double arctanBottom = (sin(phi1)*sin(phi2) + cos(phi1)*cos(phi2)*cos(dLambda));

        double dSigma = Math.atan2(arctanTop,arctanBottom);

        return (long) (round(earthRadius * dSigma));
    }

    public Double deltaLambdaCalc(Double from, Double to) {
      
        Double deltaLambda = abs(from - to);

        return deltaLambda;
    }
    
}