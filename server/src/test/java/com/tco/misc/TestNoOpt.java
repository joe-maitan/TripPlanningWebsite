package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tco.requests.DistancesRequest;

public class TestNoOpt {

    private NoOpt noOpt;
    private double earthRadius;
    private String formula;
    private Double response;

    @BeforeEach
    public void createOpt(){
        noOpt = new NoOpt();
        earthRadius = 1000.0;
        formula = "vincenty";
        response = 1.0;
    }

    @Test
    @DisplayName("puleoja: Star shapped test")
    public void starTest() throws BadRequestException {

        Place place1 = new Place("0.0","0.0");
        Place place2 = new Place("25.0","10.0");
        Place place3 = new Place("0.0","20.0");
        Place place4 = new Place("15.0","-5.0");
        Place place5 = new Place("15.0","25.0");
        Places star = new Places();
        Places circle = new Places();
        
        star.add(place1);
        star.add(place2);
        star.add(place3);
        star.add(place4);
        star.add(place5);
        
        circle.add(place1);
        circle.add(place4);
        circle.add(place2);
        circle.add(place5);
        circle.add(place3);

        Places tour = noOpt.construct(star,earthRadius,formula,1.0);
        Distances distA = new Distances();
        Distances distB = new Distances();
        DistancesRequest distReqA = new DistancesRequest(tour,earthRadius,formula);
        DistancesRequest distReqB = new DistancesRequest(circle,earthRadius,formula);

        distReqA.calculateDistances(distA);
        distReqB.calculateDistances(distB);
        
        assert distA.total() == distB.total();
    }

    @Test
    @DisplayName("puleoja: Best Test 3 from finh123")
    public void finhTest() throws BadRequestException {
        earthRadius = 4000;
        Place place1 = new Place("10.5891","-85.5333");
        Place place2 = new Place("-17.5570","-149.5552");
        Place place3 = new Place("36.2927","59.5708");
        Place place4 = new Place("54.1531","-4.5215");
        Place place5 = new Place("53.3433","-6.2869");
        Place place6 = new Place("47.1765","9.5152");
        Place place7 = new Place("-22.5224","17.0787");
        Place place8 = new Place("38.5940","125.4824");
        Place place9 = new Place("54.9947","73.3796");

        Places places = new Places();
        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);
        places.add(place6);
        places.add(place7);
        places.add(place8);
        places.add(place9);

        Places tour = noOpt.construct(places,earthRadius,formula,0.75);

        Distances distA = new Distances();
        Distances distB = new Distances();
        DistancesRequest distReqA = new DistancesRequest(tour,earthRadius,formula);
        DistancesRequest distReqB = new DistancesRequest(places,earthRadius,formula);

        distReqA.calculateDistances(distA);
        distReqB.calculateDistances(distB);
        
        assert distA.total() <= distB.total();
    }

    @Test
    @DisplayName("caden147: sprint 3 best test from nwhisler")
    public void nwhislerTest() throws BadRequestException {
        earthRadius = 2789;
        Place place1 = new Place("0.0000", "100.0000");
        Place place2 = new Place("52.0000", "0.0000");
        Place place3 = new Place("0.0000", "-32.0000");
        Place place4 = new Place("37.5522", "-99.2435");
        Place place5 = new Place("19.2609", "-99.0805");
        Place place6 = new Place("4.4239", "-74.0419");
        Place place7 = new Place("-14.0345", "-63.2657");
        Place place8 = new Place("-19.0558", "-66.2061");
        Place place9 = new Place("-22.9655", "-63.7259");
        Place place10 = new Place("35.9283", "137.9596");
        Place place11 = new Place("-26.0867", "131.8187");
        Place place12 = new Place("-35.1307", "86.6177");

        Places places = new Places();
        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);
        places.add(place6);
        places.add(place7);
        places.add(place8);
        places.add(place9);
        places.add(place10);
        places.add(place11);
        places.add(place12);

        Places tour = noOpt.construct(places, earthRadius, formula, 0.5);

        Distances distA = new Distances();
        Distances distB = new Distances();
        DistancesRequest distReqA = new DistancesRequest(tour,earthRadius,formula);
        DistancesRequest distReqB = new DistancesRequest(places,earthRadius,formula);

        distReqA.calculateDistances(distA);
        distReqB.calculateDistances(distB);
        
        assert distA.total() <= distB.total();
    }
    @Test
    @DisplayName("tjolsonx: Best Test 3 from ayushad")
    public void ayushadTest() throws BadRequestException {
        earthRadius = 1234;
        Place place1 = new Place("-54.8019","-68.3030");
        Place place2 = new Place("82.5018","-62.3481");
        Place place3 = new Place("-42.8821","147.3272");
        Place place4 = new Place("63.4641","142.7737");
        Place place5 = new Place("-26.6480","15.1594");
        Place place6 = new Place("78.2232","15.6267");
        Place place7 = new Place("-3.8549","-32.4278");
        Place place8 = new Place("34.1526","77.5771");
        Place place9 = new Place("36.7199","139.6982");
        Place place10 = new Place("71.2906","-156.7887");
        Place place11 = new Place("-46.9537","168.3538");
        Place place12 = new Place("46.7811","-56.1764");

        Places places = new Places();
        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);
        places.add(place6);
        places.add(place7);
        places.add(place8);
        places.add(place9);
        places.add(place10);
        places.add(place11);
        places.add(place12);

        Places tour = noOpt.construct(places, earthRadius, formula, 0.01);

        Distances distA = new Distances();
        Distances distB = new Distances();
        DistancesRequest distReqA = new DistancesRequest(tour,earthRadius,formula);
        DistancesRequest distReqB = new DistancesRequest(places,earthRadius,formula);

        distReqA.calculateDistances(distA);
        distReqB.calculateDistances(distB);
        
        assert distA.total() <= distB.total();
    }

    @Test 
    @DisplayName("jjmaitan: A Best Test 3 etaketa")
    public void etaketaTourTest() throws BadRequestException {
        earthRadius = 151.157763;
        response = 0.7;

        Place place1 = new Place("44.1114","15.2261");
        Place place2 = new Place("20.84","-156.49");
        Place place3 = new Place("35.3168","139.5357");
        Place place4 = new Place("-33.856159","151.2153");
        Place place5 = new Place("63.0692","-151.197418");
        Place place6 = new Place("48.8584","2.2945");
        Place place7 = new Place("-13.163068","-72.545128");
        Place place8 = new Place("27.1751","78.0421");
        Place place9 = new Place("-27.1239","-109.2861");
        // Place place10 = new Place("44.1114","15.2261");

        Places places = new Places();
        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);
        places.add(place6);
        places.add(place7);
        places.add(place8);
        places.add(place9);
        // places.add(place10);

        Places tour = noOpt.construct(places, earthRadius, formula, response);

        Distances distA = new Distances();
        Distances distB = new Distances();
        DistancesRequest distReqA = new DistancesRequest(tour, earthRadius, formula);
        DistancesRequest distReqB = new DistancesRequest(places, earthRadius, formula);

        distReqA.calculateDistances(distA);
        distReqB.calculateDistances(distB);
        
        assert distA.total() <= distB.total();

    } // End etaketaTourTest() method
    
    @Test
    @DisplayName("bagel: Best Test 3 from beverlyn")
    public void beverlynTest() throws BadRequestException {
        earthRadius = 2000;
        String formula = "vincenty"; 
        Places places = new Places(); 
        double response = 0.22;
        Place Reykjavik = new Place("-64.1466","-21.9426");
        Place Marrakech = new Place("31.6295","-7.9811");
        Place Hanoi = new Place("21.0285","105.8542");
        Place Banff = new Place("51.1784","-115.5708");
        Place Osaka = new Place("34.6937","135.5022");
        Place Auckland = new Place("-36.8485","174.7633");
        Place Siem_Reap = new Place("13.3671","103.8449");
        Place Bergen = new Place("60.3913","5.3221");
        Place Salzburg = new Place("47.8095","13.0550");
        Place Casablanca = new Place("33.5731","-7.5898");
        Place Santorini = new Place("36.3932","25.4615");
        Place Zurich = new Place("47.3769","8.5417");

        places.add(Reykjavik);
        places.add(Marrakech);
        places.add(Hanoi);
        places.add(Banff);
        places.add(Osaka);
        places.add(Auckland);
        places.add(Siem_Reap);
        places.add(Bergen);
        places.add(Salzburg);
        places.add(Casablanca);
        places.add(Santorini);
        places.add(Zurich);

        long startTime = System.nanoTime();
        Places tour = noOpt.construct(places, earthRadius, formula, response);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        long durationInMilliseconds = duration / 1_000_000; // Convert ns to ms
        assertTrue(durationInMilliseconds < 220, "Execution should complete within 900 milliseconds");
    }

    @Test
    @DisplayName("bagel: Erin's amazing tour test")
    public void nearNeighborTest() throws BadRequestException {
        //double response = 0.22;
        Place p1 = new Place("40.31","-105.95");
        Place p2 = new Place("40.96","-104.71");
        Place p3 = new Place("40.32","-104.80");
        Place p4 = new Place("40.89","-105.67");
        Place p5 = new Place("40.87","-102.30");

        Places places = new Places();
        places.add(p1);
        places.add(p2);
        places.add(p3);
        places.add(p4);
        places.add(p5);

        Places expected = new Places();
        expected.add(p1); //will need to change to p4, p1, p3, p2, p5 when looking at all starting cities
        expected.add(p3); // after rotation should be p1, p3, p2, p5,p4
        expected.add(p2);
        expected.add(p5);
        expected.add(p4);
        
        places = noOpt.construct(places, 3959, "vincenty", 1.0);
        assertEquals(expected, places);
    }

    @Test
    @DisplayName("tjolsonx: Execution Time Test")
    public void executionTimeTest() throws BadRequestException {
    double earthRadius = 6371.0; 
    String formula = "vincenty"; 
    double response = 1.0; 
    Places places = new Places(); 

    // Add places for testing
    places.add(new Place("40.31", "-105.95"));
    places.add(new Place("40.96", "-104.71"));
    places.add(new Place("40.32", "-104.80"));
    

    long startTime = System.nanoTime();
    Places tour = noOpt.construct(places, earthRadius, formula, response);
    long endTime = System.nanoTime();
    long duration = endTime - startTime;
    long durationInMilliseconds = duration / 1_000_000; // Convert ns to ms
    
    assertTrue(durationInMilliseconds < 900, "Execution should complete within 900 milliseconds");
}

@Test
@DisplayName("tjolsonx: Starting location test")
public void startLocationTest() throws BadRequestException {
    Places places = new Places();
    Place p1 = new Place("44.79", "-107.58"); 
    Place p2 = new Place("42.89", "66.09");
    Place p3 = new Place("19.61", "0.53");
    Place p4 = new Place("55.53", "-92.81");
    Place p5 = new Place("32.50", "-93.87");
    Place p6 = new Place("50.58", "43.95");
    Place p7 = new Place("24.33", "31.82");

        places.add(p1);
        places.add(p2);
        places.add(p3);
        places.add(p4);
        places.add(p5);
        places.add(p6);
        places.add(p7);

        Places expected = new Places();
        expected.add(p1);
        expected.add(p4);
        expected.add(p6);
        expected.add(p2);
        expected.add(p7);
        expected.add(p3);
        expected.add(p5);
        
    places = noOpt.construct(places, 6371.0, "haversine", 1.0);
        assertEquals(expected, places);
}
    



@Test
@DisplayName("bagel: Test initializeTours")
public void initializeToursTest() throws BadRequestException {
    Places places = new Places(); 
    Place Reykjavik = new Place("-64.1466","-21.9426");
    Place Marrakech = new Place("31.6295","-7.9811");
    Place Hanoi = new Place("21.0285","105.8542");
    Place Banff = new Place("51.1784","-115.5708");
    Place Osaka = new Place("34.6937","135.5022");
    Place Auckland = new Place("-36.8485","174.7633");
    Place Siem_Reap = new Place("13.3671","103.8449");
    Place Bergen = new Place("60.3913","5.3221");


    places.add(Reykjavik);
    places.add(Marrakech);
    places.add(Hanoi);
    places.add(Banff);
    places.add(Osaka);
    places.add(Auckland);
    places.add(Siem_Reap);
    places.add(Bergen);
    
    Places tour = noOpt.construct(places, earthRadius, formula, response);

    assert(tour.size() == places.size());
}

@Test
@DisplayName("bagel: Test initializeDistanceMatrix")
public void initializeDistanceMatrixTest() throws BadRequestException {
    earthRadius = 3950;
    Place p1 = new Place("40.31","-105.95");
    Place p2 = new Place("40.96","-104.71");
    Place p3 = new Place("40.32","-104.80");
    Place p4 = new Place("40.89","-105.67");
    Place p5 = new Place("40.87","-102.30");

    Places places = new Places();
    places.add(p1);
    places.add(p2);
    places.add(p3);
    places.add(p4);
    places.add(p5);

    long[][] equivalent = {{0,79,60, 43, 195},{79, 0, 44, 50, 126},{60, 44, 0, 60, 136},{43, 50, 60, 0, 176},{195, 126, 136, 176, 0}};
    Places tour = noOpt.construct(places, earthRadius, formula, response);
    long[][] fromTour = noOpt.getDistanceMatrix();
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {

            assert(equivalent[i][j] == fromTour[i][j]);
        }
    }
}

@Test
@DisplayName("bagel: createTours Test")
public void createToursTest() throws BadRequestException {

    Place Hanoi = new Place("21.0285","105.8542");
    Place Banff = new Place("51.1784","-115.5708");
    Place Osaka = new Place("34.6937","135.5022");
    Place p2 = new Place("40.96","-104.71");
    Place p3 = new Place("40.32","-104.80");
    Place p4 = new Place("40.89","-105.67");

    Place[] tour = {Hanoi, Banff ,Osaka ,p2 ,p3 ,p4};

    Places expected = new Places();
    expected.add(Hanoi);
    expected.add(Banff);
    expected.add(Osaka);
    expected.add(p2);
    expected.add(p3);
    expected.add(p4);

    noOpt.construct(expected, earthRadius, formula, response);
    Places result = noOpt.createTour(tour);

    for (int i = 0; i < expected.size(); i++) {
        assert(expected.get(i) == result.get(i));
    }
    
}


@Test
@DisplayName("tjolsonx: Should not optimize with fewer than four places")
public void testOptimizationNotRunForFewerThanFourPlaces() {
    try {
        Places places = new Places();
        places.add(new Place("40.7128", "-74.0060")); // New York
        places.add(new Place("34.0522", "-118.2437")); // Los Angeles
        places.add(new Place("41.8781", "-87.6298")); // Chicago

        Places result = noOpt.construct(places, earthRadius, formula, response);

        assertEquals(places, result, "Places should be returned unchanged when fewer than four are provided");
    } catch (BadRequestException e) {
        fail("BadRequestException should not be thrown");
    }
}

/*@Test
@DisplayName("bagel: findNextClosestElement Test")
public void testFindNextClosestElement() throws BadRequestException {
    Place p1 = new Place("40.31","-105.95");
    Place p2 = new Place("40.96","-104.71");
    Place p3 = new Place("40.32","-104.80");
    Place p4 = new Place("40.89","-105.67");
    Place p5 = new Place("40.87","-102.30");

    Places places = new Places();
    places.add(p1);
    places.add(p2);
    places.add(p3);
    places.add(p4);
    places.add(p5);
    
    places = noOpt.construct(places, 3959, "vincenty", 1.0);
    assert(noOpt.smallestDistance == 43);
}*/

@Test
    @DisplayName("puleoja: Grid20 Test w/ extra time")
    public void grid20() throws BadRequestException {
        Place p1 = new Place("41.6", "-41.9");
        Place p2 = new Place("-18.1", "41.0");
        Place p3 = new Place("-61.6", "-100.5");
        Place p4 = new Place("80.8", "-41.8");
        Place p5 = new Place("-81.3", "118.2");
        Place p6 = new Place("1.6", "-38.6");
        Place p7 = new Place("-42.1", "101.2");
        Place p8 = new Place("17.9", "-78.3");
        Place p9 = new Place("21.0", "-101.8");
        Place p10 = new Place("38.3", "-81.5");
        Place p11 = new Place("-79.7", "-59.0");
        Place p12 = new Place("-0.9", "-101.5");
        Place p13 = new Place("59.8", "-102.2");
        Place p14 = new Place("58.4", "-118.7");
        Place p15 = new Place("81.1", "-58.4");
        Place p16 = new Place("-21.9", "-139.2");
        Place p17 = new Place("21.8", "141.3");
        Place p18 = new Place("80.4", "99.0");
        Place p19 = new Place("-39.8", "-60.5");
        Place p20 = new Place("79.5", "2.1");
        Place p21 = new Place("-37.8", "39.1");
        Place p22 = new Place("60.7", "-57.9");
        Place p23 = new Place("-40.5", "-78.6");
        Place p24 = new Place("2.1", "100.6");
        Place p25 = new Place("-79.6", "-20.8");
        Place p26 = new Place("79.0", "-21.4");
        Place p27 = new Place("-78.9", "139.2");
        Place p28 = new Place("19.8", "-61.7");
        Place p29 = new Place("79.6", "-139.9");
        Place p30 = new Place("39.1", "-62.2");
        Place p31 = new Place("38.2", "140.6");
        Place p32 = new Place("61.3", "-18.3");
        Place p33 = new Place("42.2", "40.2");
        Place p34 = new Place("-80.2", "-141.5");
        Place p35 = new Place("21.6", "101.5");
        Place p36 = new Place("-20.7", "58.1");
        Place p37 = new Place("-41.6", "119.6");
        Place p38 = new Place("78.5", "59.5");
        Place p39 = new Place("-40.2", "-0.1");
        Place p40 = new Place("41.0", "-158.2");
        Place p41 = new Place("-18.1", "-38.3");
        Place p42 = new Place("-61.4", "-41.9");
        Place p43 = new Place("57.9", "-159.3");
        Place p44 = new Place("-0.7", "161.1");
        Place p45 = new Place("-21.1", "-78.6");
        Place p46 = new Place("-38.7", "-21.1");
        Place p47 = new Place("-81.8", "38.7");
        Place p48 = new Place("79.7", "161.6");
        Place p49 = new Place("1.2", "121.5");
        Place p50 = new Place("37.9", "98.5");
        Place p51 = new Place("-59.1", "142.0");
        Place p52 = new Place("-38.3", "58.4");
        Place p53 = new Place("-78.6", "18.3");
        Place p54 = new Place("-20.9", "-60.7");
        Place p55 = new Place( "-1.5", "-162.1");
        Place p56 = new Place("-19.7", "81.7");
        Place p57 = new Place("80.6", "120.1");
        Place p58 = new Place("1.2", "78.6");
        Place p59 = new Place("-59.8", "79.4");
        Place p60 = new Place("20.3", "21.7");
        Place p61 = new Place("-77.8", "98.8");
        Place p62 = new Place("-58.5", "-160.5");
        Place p63 = new Place("82.1", "81.9");
        Place p64 = new Place("58.1", "59.2");
        Place p65 = new Place("0.3", "139.8");
        Place p66 = new Place("18.6", "-20.5");
        Place p67 = new Place("-80.8", "-41.5");
        Place p68 = new Place("0.3", "59.1");
        Place p69 = new Place("21.6", "-39.6");
        Place p70 = new Place("79.6", "-78.1");
        Place p71 = new Place("60.8", "99.9");
        Place p72 = new Place("42.1", "121.3");
        Place p73 = new Place("-38.0", "-99.0");
        Place p74 = new Place("79.7", "22.2");
        Place p75 = new Place("-82.0", "-159.3");
        Place p76 = new Place("-2.2", "-21.4");
        Place p77 = new Place("20.4", "38.9");
        Place p78 = new Place("17.8", "-159.9");
        Place p79 = new Place("39.6", "-19.8");
        Place p80 = new Place("38.0", "-119.1");
        Place p81 = new Place("19.6", "61.7");
        Place p82 = new Place("-60.7", "18.1");
        Place p83 = new Place("39.6", "158.1");
        Place p84 = new Place("-78.4", "-100.4");
        Place p85 = new Place("39.3", "20.4");
        Place p86 = new Place("-0.7", "-120.5");
        Place p87 = new Place("82.1", "141.5");
        Place p88 = new Place("-22.0", "-119.0");
        Place p89 = new Place("19.3", "-1.4");
        Place p90 = new Place("-79.9", "78.9");
        Place p91 = new Place("-59.1", "121.8");
        Place p92 = new Place("-80.1", "160.3");
        Place p93 = new Place("-59.7", "161.1");
        Place p94 = new Place("38.2", "61.3");
        Place p95 = new Place("61.3", "-1.7");
        Place p96 = new Place("-20.4", "2.0");
        Place p97 = new Place("60.8", "38.0");
        Place p98 = new Place("-21.5", "161.4");
        Place p99 = new Place("-58.1", "-80.1");
        Place p100 = new Place("80.6", "38.5");
        Place p101 = new Place("78.2", "-121.4");
        Place p102 = new Place("-0.2", "-59.9");
        Place p103 = new Place("-40.7", "160.3");
        Place p104 = new Place("40.3", "79.6");
        Place p105 = new Place("-39.6", "139.8");
        Place p106 = new Place("21.9", "-121.6");
        Place p107 = new Place("22.0", "-142.1");
        Place p108 = new Place("-60.5", "39.4");
        Place p109 = new Place("58.2", "159.7");
        Place p110 = new Place("-60.2", "-120.5");
        Place p111 = new Place("-40.6", "-161.3");
        Place p112 = new Place("-40.1", "-138.9");
        Place p113 = new Place("78.6", "-101.5");
        Place p114 = new Place("58.3", "-42.0");
        Place p115 = new Place("-62.1", "-59.7");
        Place p116 = new Place("60.6", "80.5");
        Place p117 = new Place("59.4", "-140.4");
        Place p118 = new Place("-2.0", "20.1");
        Place p119 = new Place("-1.0", "-79.3");
        Place p120 = new Place("59.4", "21.2");
        Place p121 = new Place("-39.1", "-40.1");
        Place p122 = new Place("20.2", "81.9");
        Place p123 = new Place("-39.3", "78.3");
        Place p124 = new Place("-79.8", "-2.1");
        Place p125 = new Place("-19.5", "-160.9");
        Place p126 = new Place("-19.7", "140.6");
        Place p127 = new Place("78.7", "-158.4");
        Place p128 = new Place("-59.4", "-139.9");
        Place p129 = new Place("19.2", "120.4");
        Place p130 = new Place("-60.9", "0.4");
        Place p131 = new Place("1.9", "40.7");
        Place p132 = new Place("-0.8", "-140.6");
        Place p133 = new Place("-19.9", "122.1");
        Place p134 = new Place("-81.1", "60.9");
        Place p135 = new Place("-59.5", "-17.8");
        Place p136 = new Place("-60.6", "98.8");
        Place p137 = new Place("-41.5", "-121.5");
        Place p138 = new Place("-80.2", "-119.3");
        Place p139 = new Place("39.1", "-101.2");
        Place p140 = new Place("59.8", "-79.6");
        Place p141 = new Place("-81.6", "-79.2");
        Place p142 = new Place("-38.4", "19.6");
        Place p143 = new Place("-20.0", "98.5");
        Place p144 = new Place("-22.2", "-98.4");
        Place p145 = new Place("40.5", "-139.5");
        Place p146 = new Place("42.2", "-1.3");
        Place p147 = new Place("59.3", "140.4");
        Place p148 = new Place("-22.2", "-21.0");
        Place p149 = new Place("-18.1", "19.9");
        Place p150 = new Place("19.9", "160.6");
        Place p151 = new Place("0.5", "1.0");
        Place p152 = new Place("58.4", "121.1");
        Place p153 = new Place("-60.2", "61.7");

        Places places = new Places();
        places.add(p1);
        places.add(p2);
        places.add(p3);
        places.add(p4);
        places.add(p5);
        places.add(p6);
        places.add(p7);
        places.add(p8);
        places.add(p9);
        places.add(p10);
        places.add(p11);
        places.add(p12);
        places.add(p13);
        places.add(p14);
        places.add(p15);
        places.add(p16);
        places.add(p17);
        places.add(p18);
        places.add(p19);
        places.add(p20);
        places.add(p21);
        places.add(p22);
        places.add(p23);
        places.add(p24);
        places.add(p25);
        places.add(p26);
        places.add(p27);
        places.add(p28);
        places.add(p29);
        places.add(p30);
        places.add(p31);
        places.add(p32);
        places.add(p33);
        places.add(p34);
        places.add(p35);
        places.add(p36);
        places.add(p37);
        places.add(p38);
        places.add(p39);
        places.add(p40);
        places.add(p41);
        places.add(p42);
        places.add(p43);
        places.add(p44);
        places.add(p45);
        places.add(p46);
        places.add(p47);
        places.add(p48);
        places.add(p49);
        places.add(p50);
        places.add(p51);
        places.add(p52);
        places.add(p53);
        places.add(p54);
        places.add(p55);
        places.add(p56);
        places.add(p57);
        places.add(p58);
        places.add(p59);
        places.add(p60);
        places.add(p61);
        places.add(p62);
        places.add(p63);
        places.add(p64);
        places.add(p65);
        places.add(p66);
        places.add(p67);
        places.add(p68);
        places.add(p69);
        places.add(p70);
        places.add(p71);
        places.add(p72);
        places.add(p73);
        places.add(p74);
        places.add(p75);
        places.add(p76);
        places.add(p77);
        places.add(p78);
        places.add(p79);
        places.add(p80);
        places.add(p81);
        places.add(p82);
        places.add(p83);
        places.add(p84);
        places.add(p85);
        places.add(p86);
        places.add(p87);
        places.add(p88);
        places.add(p89);
        places.add(p90);
        places.add(p91);
        places.add(p92);
        places.add(p93);
        places.add(p94);
        places.add(p95);
        places.add(p96);
        places.add(p97);
        places.add(p98);
        places.add(p99);
        places.add(p100);
        places.add(p101);
        places.add(p102);
        places.add(p103);
        places.add(p104);
        places.add(p105);
        places.add(p106);
        places.add(p107);
        places.add(p108);
        places.add(p109);
        places.add(p110);
        places.add(p111);
        places.add(p112);
        places.add(p113);
        places.add(p114);
        places.add(p115);
        places.add(p116);
        places.add(p117);
        places.add(p118);
        places.add(p119);
        places.add(p120);
        places.add(p121);
        places.add(p122);
        places.add(p123);
        places.add(p124);
        places.add(p125);
        places.add(p126);
        places.add(p127);
        places.add(p128);
        places.add(p129);
        places.add(p130);
        places.add(p131);
        places.add(p132);
        places.add(p133);
        places.add(p134);
        places.add(p135);
        places.add(p136);
        places.add(p137);
        places.add(p138);
        places.add(p139);
        places.add(p140);
        places.add(p141);
        places.add(p142);
        places.add(p143);
        places.add(p144);
        places.add(p145);
        places.add(p146);
        places.add(p147);
        places.add(p148);
        places.add(p149);
        places.add(p150);
        places.add(p151);
        places.add(p152);
        places.add(p153);

        response = 10.0;

        Places tour = noOpt.construct(places, earthRadius, formula, response);

        Distances distA = new Distances();
        Distances distB = new Distances();
        DistancesRequest distReqA = new DistancesRequest(tour, earthRadius, formula);
        DistancesRequest distReqB = new DistancesRequest(places, earthRadius, formula);

        distReqA.calculateDistances(distA);
        distReqB.calculateDistances(distB);

        assert distA.total() <= distB.total();
    }

}