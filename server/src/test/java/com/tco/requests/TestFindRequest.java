package com.tco.requests;

import java.util.*;

import com.tco.misc.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFindRequest {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private String requestType;
    private String match;
    private List<String> type;
    private List<String> where;
    private int limit;
    private int found;
    private Places places;

    private FindRequest find;

    @BeforeEach
    public void initializeFindRequest() {
        this.match = "Colorado";
        this.limit = 1;
        this.type = new ArrayList<>();
        this.where = new ArrayList<>();
        this.places = new Places();
        this.find = new FindRequest(this.match, this.type, this.where, this.limit);
    }

    @Test
    @DisplayName("jjmaitan: Request type is \"find\"")
    public void testRequestType() {
        assertEquals("find", this.find.getRequestType());
    }

    @Test
    @DisplayName("jjmaitan: Test find()")
    public void testFind() {
        /* Change to equal a place that was found with the query */
        limit = 10;
        match = "Fort Collins";
        find = new FindRequest(match, null, null, limit);

        assertEquals(find.foundPlaces().size(), 2);
        assertEquals(find.foundPlaces().get(0).get("name"), "Fort Collins Downtown Airport");
        assertEquals(find.foundPlaces().get(1).get("name"), "Fort Collins Loveland Municipal Airport");
    }

    @Test
    @DisplayName("puleoja: finding locations in Denver")
    public void testFindDenver() {
        limit = 10;
        match = "Denver";
        find = new FindRequest(match, null, null, limit);

        //There are 7 places to be found, despite the limit being 10
        assertEquals(find.foundPlaces().size(), 7);
    }

    @Test
    @DisplayName("bagel: limit = 0 test")
    public void testZeroLimit() {
        limit = 0;
        match = "a";
        find = new FindRequest(match, null, null, limit);
        assertEquals(find.foundPlaces().size(), 47577);
    }
    
} // End TestFindRequest class
