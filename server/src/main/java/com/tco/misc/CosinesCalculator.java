package com.tco.misc;

import com.tco.misc.GeographicCoordinate;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.acos;
import static java.lang.Math.round;

public class CosinesCalculator extends DistanceCalculator {
    
    public Long between(
        GeographicCoordinate from,
        GeographicCoordinate to,
        double earthRadius) {
            double deltaLambda = abs(from.lonRadians() - to.lonRadians());
            double phiFrom = from.latRadians();
            double phiTo = to.latRadians();
            double deltaSigma = acos(sin(phiFrom)*sin(phiTo) + cos(phiFrom)*cos(phiTo)*cos(deltaLambda));
            long distance = (long) round(earthRadius * deltaSigma);

        return distance;
    }
    
}