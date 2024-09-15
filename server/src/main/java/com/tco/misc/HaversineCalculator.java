package com.tco.misc;

import static java.lang.Double.parseDouble;
import com.tco.misc.GeographicCoordinate;

import static java.lang.Math.toRadians;
import static java.lang.Math.atan2;
import static java.lang.Math.asin;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;

public class HaversineCalculator extends DistanceCalculator {

    public Long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
        double phi1 = from.latRadians();
        double phi2 = to.latRadians();
        double lambda1 = from.lonRadians();
        double lambda2 = to.lonRadians();
        double dPhi = deltaPhiCalc(phi1,phi2);
        double dLambda = deltaLambdaCalc(lambda1,lambda2);

        double dSigma = 2*asin(sqrt(pow(sin(dPhi/2),2) + ( pow(cos((phi1+phi2)/2),2) - pow(sin(dPhi/2),2) ) * pow(sin(dLambda/2),2)));
        Long distance = (long) (round(dSigma * earthRadius));

        return distance;
    }

    public Double deltaLambdaCalc(Double from, Double to){
        Double deltaLambda = abs(from - to);

        return deltaLambda;
    }

    public Double deltaPhiCalc(Double from, Double to){
        Double deltaPhi = abs(from - to);

        return deltaPhi;
    }

    
}