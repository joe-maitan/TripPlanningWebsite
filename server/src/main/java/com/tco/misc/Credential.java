package com.tco.misc;

public class Credential {
    // shared user with read-only access
    final static String USER = "cs314-db";
    final static String PASSWORD = "eiK5liet1uej";
    // connection information when using port forwarding from localhost
    static String dburl = "jdbc:mariadb://127.0.0.1:56247/cs314";
    public static String url() {
        String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");
        String onDocker = System.getenv("CS314_DOCKER");
        // Note that if the variable isn't defined, System.getenv will return null
        if(useTunnel != null && useTunnel.equals("true")) {
            dburl = "jdbc:mariadb://127.0.0.1:56247/cs314";
        }
        else if(onDocker != null && onDocker.equals("true")) {
            dburl = "jdbc:mariadb://127.0.0.1:3306/cs314";
        }
        else {
            dburl = "jdbc:mariadb://faure.cs.colostate.edu/cs314";
        }

        return dburl;
    }
}
