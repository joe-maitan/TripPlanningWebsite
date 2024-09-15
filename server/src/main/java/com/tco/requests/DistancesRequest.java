package com.tco.requests;

import com.tco.misc.*;
import com.tco.misc.BadRequestException;
import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistancesRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private final Places places;
    private final Double earthRadius;
    private final String formula;
    private Distances distances;

    public DistancesRequest(Places _places, Double _earthRadius, String _formula) {
        super();
        this.requestType = "distances";
        this.places = _places;
        this.earthRadius = _earthRadius;
        this.formula = _formula;
    } // End DistancesRequest() constructor

    @Override
    public void buildResponse() throws BadRequestException{
        try {
            distances = buildDistanceList();
            log.trace("buildResponse -> {}", this);
        }
        catch(BadRequestException exception) {
            throw exception;
        }
    } // End buildResponse() method

    public DistanceCalculator getCalculator(CalculatorFactory factory) throws BadRequestException {
        return factory.get(this.formula);
    } // End getCalculator(CalculatorFactory) method

    public long useCalculator(Place from, Place to, DistanceCalculator calc) {
        long distance = -1L;
        distance = calc.between(from, to, this.earthRadius);
        return distance;
    } // End makeCalculator(formula) method

    public boolean isPlacesValid() {
        if (this.places != null) {
            return true;
        } else {
            return false;
        } // End if-else statement
    } // End isPlacesValid() method

    public void calculateDistances(Distances dist) throws BadRequestException {
        CalculatorFactory factory = new CalculatorFactory();
        DistanceCalculator calc = factory.get(this.formula);
        Long distance = 0L;

        for (int i = 0; i < this.places.size(); i++) {
            Place from = places.get(i);
            Place to;
            
            if (i + 1 >= places.size()) {
                to = places.get(0);
            } else {
                to = places.get(i + 1);
            }
    
            distance = useCalculator(from, to, calc);
            dist.add(distance);
        } // End for loop
    } // End calculateDistances

    public Distances buildDistanceList() throws BadRequestException{
        Distances dist = new Distances();
    
        if (isPlacesValid()) {
            calculateDistances(dist);
        } else {
            throw new BadRequestException();
        } // End if-else statement
    
        return dist;
    } // End buildDistanceList() method
    
} // End DistancesRequest class