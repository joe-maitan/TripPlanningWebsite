package com.tco.requests;

import com.tco.misc.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTourRequest {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private TourRequest tour;
    private Places places;
    private double earthRadius;
    private String requestType;
    private String formula;
    private double response;

    @BeforeEach
    public void initializeTourTest() {
        places = new Places();
        earthRadius = 6371.0;
        formula = "vincenty";
        response = 1.0;
        tour = new TourRequest(places, earthRadius, response, formula);
    } // End initializeTourTest() method
    
} // End TestTourRequest class
