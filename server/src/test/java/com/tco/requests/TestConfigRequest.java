package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestConfigRequest {

    private ConfigRequest conf;

    @BeforeEach
    public void createConfigurationForTestCases() {
        conf = new ConfigRequest();
        conf.buildResponse();
    }

    @Test
    @DisplayName("base: Request type is \"config\"")
    public void testType() {
        String type = conf.getRequestType();
        assertEquals("config", type);
    }

    @Test
    @DisplayName("base: Features includes \"config\"")
    public void testFeatures(){
        assertTrue(conf.validFeature("config"));
    }

    @Test
    @DisplayName("base: Team name is correct")
    public void testServerName() {
        String name = conf.getServerName();
        assertEquals("t25 What Are The Odds?", name);
    }

    @Test
    @DisplayName("bagel: vincenty formulae exists")
    public void testVincentyFormulae(){
        assertTrue(conf.validFormulae("vincenty"));
    }

    @Test
    @DisplayName("bagel: haversine formulae exists")
    public void testHaversineFormulae(){
        assertTrue(conf.validFormulae("haversine"));
    }

    @Test
    @DisplayName("bagel: cosines formulae exists")
    public void testCosinesFormulae(){
        assertTrue(conf.validFormulae("cosines"));
    }

    @Test
    @DisplayName("bagel: fake formulae does not exist")
    public void testFakeFormulae(){
        assertFalse(conf.validFormulae("yourmom"));
    }
}