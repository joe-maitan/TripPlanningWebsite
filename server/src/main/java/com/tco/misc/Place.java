package com.tco.misc;

import com.tco.misc.GeographicCoordinate;

import java.util.LinkedHashMap;

import static java.lang.Double.parseDouble;
import static java.lang.Math.toRadians;

public class Place extends LinkedHashMap<String,String> implements GeographicCoordinate {
    private String latitude;
    private String longitude;

    public Place () {}

    //for testing purposes
    public Place (String lat, String lon) {
        this.put("latitude", lat);
        this.put("longitude", lon);
    }

    public Double latRadians() {
        Double latDegree = Double.parseDouble(this.get("latitude"));
        return Math.toRadians(latDegree);
    }

    public Double lonRadians() {
        Double lonDegree = Double.parseDouble(this.get("longitude"));
        return Math.toRadians(lonDegree);
    }
}