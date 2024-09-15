package com.tco.requests;

import java.util.List;

import com.tco.misc.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);

    String match;
    List<String> type;
    List<String> where;
    int limit;
    int found;
    Places places;

    // For testing purposes (TEMPORARY)
    public FindRequest(String match, List<String> type, List<String> where, int limit) {
        GeographicLocations geo = new GeographicLocations();
        this.requestType = "find";
        this.match = match;
        this.type = type;
        this.where = where;
        this.limit = limit;
        this.found = geo.found(match);
        buildResponse();
    } // End FindRequest() constructor

    @Override
    public void buildResponse() { // Does this need to throw a bad request exception?
        GeographicLocations geo = new GeographicLocations();
        if (limit == 0){
            places = geo.find(match, type, where, 50427);
        } else {
            places = geo.find(match, type, where, limit);
        }
        found = geo.found(match);
    } // End buildResponse() method

    public Places foundPlaces(){
        return places;
    }
    
} // End FindRequest class


