package com.tco.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.BadRequestException;
import java.io.IOException;

import com.tco.requests.*;

import java.util.List;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class GeographicLocations {

    private static final Logger log = LoggerFactory.getLogger(GeographicLocations.class);
    private FindRequest findRequest;
    // private NearRequest nearRequest; 
    private CalculatorFactory calculatorFactory = new CalculatorFactory();
    
    public Places find(String match, List<String> type, List<String> where, int limit) {
        try {
            Places foundPlaces = new Places();
            foundPlaces = Database.places(match, limit);
            return foundPlaces;
        } catch (Exception e) {
            Places empty = new Places();
            return empty;
        }
    } // End find() method

    public int found(String match) {
        try {
            return Database.found(match);
        } catch (Exception e) {
            return 0;
        }
    } // End found() method

    public Places near(Place place_, double earthRadius_, double distance_, int limit_) {
        Places places = new Places();
        double degreeChangeLat = distance_ / 111.0; 
        double degreeChangeLon = distance_ / (111.0 * Math.cos(Math.toRadians(Double.parseDouble(place_.get("latitude")))));
    
        double latMin = Double.parseDouble(place_.get("latitude")) - degreeChangeLat;
        double latMax = Double.parseDouble(place_.get("latitude")) + degreeChangeLat;
        double lonMin = Double.parseDouble(place_.get("longitude")) - degreeChangeLon;
        double lonMax = Double.parseDouble(place_.get("longitude")) + degreeChangeLon;
    
        String sql = "SELECT * FROM world WHERE latitude BETWEEN ? AND ? AND longitude BETWEEN ? AND ? LIMIT ?";
    
        try (Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, latMin);
            stmt.setDouble(2, latMax);
            stmt.setDouble(3, lonMin);
            stmt.setDouble(4, lonMax);
            stmt.setInt(5, limit_);
    
            ResultSet results = stmt.executeQuery();
            places = Database.convertQueryResultsToPlaces(results, Database.COLUMNS);
        } catch (Exception e) {
            log.error("Error performing near query: ", e.getMessage());
        }
    
        return places;
    }//end near()

    public Distances distances() {
        Distances dist = new Distances();
        return dist; // temporary
        // return nearRequest.distances();
    } // End distances() method
    
} // End GeographicLocations class

