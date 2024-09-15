package com.tco.requests;

import java.util.List;

import com.tco.misc.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NearRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(NearRequest.class);

    private Place place;
    private double distance;
    private double earthRadius;
    private int limit;
    
    

    public NearRequest(Place _place, double _distance, double _earthRadius, int _limit) {
        GeographicLocations geo = new GeographicLocations();
        this.requestType = "near";
        this.place = _place;
        this.distance = _distance;
        this.earthRadius = _earthRadius;
        this.limit = _limit;
    } // End NearRequest() constructor

    @Override
    public void buildResponse(){
        {
            GeographicLocations geo = new GeographicLocations();
            geo.near(place, distance, earthRadius, limit);
        }
        
    } // End buildResponse() method
    
} // End NearRequest class


