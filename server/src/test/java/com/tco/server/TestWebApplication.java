package com.tco.server;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TestWebApplication {

    @Test
    @DisplayName("base: Fail with more than 1 command line argument")
    public void testTooManyCommandLineArgs() throws Exception {
        String[] commandLineArguments = { "10", "20" };
        int status = SystemLambda.catchSystemExit(() ->
            WebApplication.main(commandLineArguments)
        );
        assertEquals(1, status);
    }

    @Test
    @DisplayName("base: Port between min and max is valid")
    public void testPortIsValid() {
        assertTrue(WebApplication.portIsValid((WebApplication.MIN_SERVER_PORT + WebApplication.MAX_SERVER_PORT) / 2));
    }

    @Test
    @DisplayName("base: Port below min is invalid")
    public void testPortIsInvalidLow() {
        assertFalse(WebApplication.portIsValid(WebApplication.MIN_SERVER_PORT - 1));
    }

    @Test
    @DisplayName("base: Port above max is invalid")
    public void testPortIsInvalidHigh() {
        assertFalse(WebApplication.portIsValid(WebApplication.MAX_SERVER_PORT + 1));
    }

    @Test
    @DisplayName("base: getServerPort uses default port")
    public void testGetServerPortDefault() {
        String[] commandLineArguments = new String[0];
        assertEquals(WebApplication.DEFAULT_SERVER_PORT, WebApplication.getServerPort(commandLineArguments));
    }

    @Test
    @DisplayName("base: getServerPort uses specified port")
    public void testGetServerPortSpecifiedValid() {
        String port = "31400";
        String[] commandLineArguments = {port};
        assertEquals(Integer.parseInt(port), WebApplication.getServerPort(commandLineArguments));
    }

    @Test
    @DisplayName("base: getServerPort uses default for invalid argument")
    public void testGetServerPortSpecifiedNotInteger() {
        String[] commandLineArguments = {".123"};
        assertEquals(WebApplication.DEFAULT_SERVER_PORT, WebApplication.getServerPort(commandLineArguments));
    }

    @Test
    @DisplayName("base: getServerPort uses default for out of range port")
    public void testGetServerPortSpecifiedOutOfRange() {
        String[] commandLineArguments = {"25"};
        assertEquals(WebApplication.DEFAULT_SERVER_PORT, WebApplication.getServerPort(commandLineArguments));
    }
}
