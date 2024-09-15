package com.tco.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebApplication {

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    static final int DEFAULT_SERVER_PORT = 8088;
    static final int MAX_SERVER_PORT = 65535;
    static final int MIN_SERVER_PORT = 1024;

    public static void main(String[] commandLineArguments) {

        if (commandLineArguments.length > 1) {
            log.error("Too many command line arguments given. Expected 1 but found {}.", commandLineArguments.length);
            System.exit(1);
        }

        int serverPort = getServerPort(commandLineArguments);
        new MicroServer(serverPort);
    }

    private WebApplication() {}

    static int getServerPort(String[] commandLineArguments) {

        int serverPort = DEFAULT_SERVER_PORT;
        if (commandLineArguments.length > 0) {
            try {
                serverPort = Integer.parseInt(commandLineArguments[0]);
            } catch (java.lang.NumberFormatException e) {
                log.error("Error converting server port: \"{}\", defaulting to {}",
                        commandLineArguments[0], DEFAULT_SERVER_PORT);
            }
        }
        if (!portIsValid(serverPort)) {
            log.error("Command line port {} is out of acceptable range ({}-{}), defaulting to {}",
                    serverPort, MIN_SERVER_PORT, MAX_SERVER_PORT, DEFAULT_SERVER_PORT);
            serverPort = DEFAULT_SERVER_PORT;
        }
        log.info("Server port: {}", serverPort);
        return serverPort;
    }

    static boolean portIsValid(int port) {
        return port >= MIN_SERVER_PORT && port <= MAX_SERVER_PORT;
    }
}
