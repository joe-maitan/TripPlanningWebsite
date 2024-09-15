package com.tco.requests;

import com.tco.misc.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TourRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);

    private Places places;
    private final double earthRadius;
    private final String formula;
    private final double response;

    /* for testing purposes */
    public TourRequest(Places _places, Double _earthRadius, Double _response, String _formula) {
        super();
        this.requestType = "tour";
        this.places = _places;
        this.earthRadius = _earthRadius;
        this.formula = _formula;
        this.response = _response;
    } // End TourRequest() constructor

    @Override
    public void buildResponse() throws BadRequestException {
        try {
            NoOpt nearestNeighbor = new NoOpt();
            this.places = nearestNeighbor.construct(places, earthRadius, formula, response);
        } catch (BadRequestException e) {
            throw e;
        }
    } // End buildResponse() method

} // End TourRequest class
