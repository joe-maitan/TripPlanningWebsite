package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDatabase {
    Database db;

    @BeforeEach
    public void initializeDatabase(){
        db = new Database();
    }

    @Test
    @DisplayName("puleoja: Does the database work")
    public void simplestTest() throws Exception{
        boolean correct = false;
        try{
            Integer found = db.found("denver");
            correct = found > 0;
        }catch(Exception e){
            throw e;
        }

        assert(correct);
    }

    @Test
    @DisplayName("puleoja: Getting a response from Database")
    public void databaseTest() throws Exception{
        Places denverPlaces = new Places();
        try{
            denverPlaces = db.places("denver", 5);
        }catch(Exception e){
            throw e;
        }
        String denverFirstName = "Denver Health Heliport";

        assertEquals(denverPlaces.get(0).get("name"),denverFirstName);
        
    }
}
