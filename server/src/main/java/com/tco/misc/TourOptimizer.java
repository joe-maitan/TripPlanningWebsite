package com.tco.misc;

import java.util.Arrays;
import com.tco.misc.CalculatorFactory;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;

public abstract class TourOptimizer {

    private CalculatorFactory calcFact;
    private DistanceCalculator calc;
    private static final Logger LOGGER = Logger.getLogger(TourOptimizer.class.getName());
    
    private Places places;
    private Double radius;
    private Double response;
    
    private Place[] tour;
    private Place[] bestTour;
    private boolean[] visited;
    private long [][] distanceMatrix;

    private long startTime;
    private long endTime;
    private long currentTime;
    private Map<Place, Integer> placeToIndexMap = new HashMap<>();

    public long smallestDistance;

    public void initializeTourOpt(Places plc, Double rad, Double rsp, String formula) throws BadRequestException {
        this.places = plc;
        this.radius = rad;
        this.response = rsp;
        this.calcFact = new CalculatorFactory();
        this.calc = calcFact.get(formula);
    }

    public void initializeTour(Places places) {
        // LOGGER.info("Entering initializeTour");
        this.tour = new Place[places.size()];
        this.bestTour = new Place[places.size()];
        this.visited = new boolean[places.size()];
        this.distanceMatrix = new long[places.size()][places.size()];
        // LOGGER.info("Tour, BestTour, and Visited arrays initialized with size: " + places.size());
    }

    public void addToHashMap(Place place, int index) { this.placeToIndexMap.put(place, index);}

    public boolean distanceMatrixIsValid(int row, int col) { return this.distanceMatrix[row][col] == 0 && row != col; }

    public void calculateDistance(int row) {
        for (int col = 0; col < places.size(); ++col) {
            if (distanceMatrixIsValid(row, col)) {
                this.distanceMatrix[row][col] = getCalculator().between(places.get(row), places.get(col), getRadius());
                this.distanceMatrix[col][row] = this.distanceMatrix[row][col];
            }
        }
    }

    private boolean initializeDistanceMatrix(Places places) { 
        for (int i = 0; i < places.size(); i++) {
            addToHashMap(places.get(i), i);
            calculateDistance(i);
        
            currentTime = System.nanoTime();
            if (currentTime >= endTime){
                return false;
            }
        }
        return true;
    } // End initializeDistanceMatrix() method

    public boolean startOfNN(Places p) {
        initializeTour(p);

        if (!initializeDistanceMatrix(p)) { 
            return false; 
        } else {
            return true;
        } // end if-else
    } // End startOfNN() func

    public Places nearestNeighbor(Places places) {
        if (!startOfNN(places)) { return places; }
        
        long minimumDistance = Long.MAX_VALUE;

        for (int k = 0; k < places.size(); k++) {
            long totalDistance = 0;
            tour = new Place[places.size()];
            tour[0] = places.get(k);
            visited = new boolean[places.size()];
            visited[k] = true; //places.get removed

            for (int j = 1; j < places.size(); j++) {
                totalDistance += findNextClosestElement(j);
                if (totalDistance >= minimumDistance) { break; } // Exit the inner loop early if not optimal 
            }

            Integer lastIndex = placeToIndexMap.get(tour[tour.length - 1]);
            Integer firstIndex = placeToIndexMap.get(tour[0]);
            if (lastIndex != null && firstIndex != null) { totalDistance += this.distanceMatrix[lastIndex][firstIndex]; } 

            if (totalDistance < minimumDistance) {
                minimumDistance = totalDistance;
                bestTour = tour;
            }
 
            bestTour = rotateTourToStartPlace(bestTour, places.get(0));
 
            currentTime = System.nanoTime();
            if (currentTime >= endTime) { return createTour(bestTour); }
        } // End outermost for loop

        return createTour(bestTour);
    }

    public long findNextClosestElement(int endPointIndex) {
        Place start = tour[endPointIndex - 1];
        long smallestDistance = Long.MAX_VALUE;
        Place endPlace = null;
        int endPlaceIndex = -1;
    
        for (int i = 0; i < places.size(); i++) {
            if (visited[i]) continue;
            Place end = places.get(i);
            Integer startIndex = placeToIndexMap.get(start);
            Integer endIndex = placeToIndexMap.get(end);
            if (startIndex == null || endIndex == null) {
                LOGGER.warning("Place not found in placeToIndexMap");
                continue;
            }
            long length = this.distanceMatrix[startIndex][endIndex];
            if (length < smallestDistance) {
                smallestDistance = length;
                endPlace = end;
                endPlaceIndex = i;
            }
        }
        if (endPlace != null && endPlaceIndex != -1) {
            tour[endPointIndex] = endPlace;
            visited[endPlaceIndex] = true;
        }
        return smallestDistance;
    }
    
    private int findStartingIndex(Place[] tour, Place start) {
        for (int i = 0; i < tour.length; i++) {
            if (tour[i].equals(start)) {
                return i;
            }
        }
        return -1;
    }
    
    private Place[] rotateTourToStartPlace(Place[] tour, Place startPlace) {
        int startIndex = findStartingIndex(tour, startPlace);
        
        if (startIndex == -1) { return tour; }
    
        Place[] rotatedTour = new Place[tour.length];
        int rotateIndex = 0;
        for (int i = startIndex; i < tour.length; i++, rotateIndex++) {
            rotatedTour[rotateIndex] = tour[i];
        }

        for (int i = 0; i < startIndex; i++, rotateIndex++) {
            rotatedTour[rotateIndex] = tour[i];
        }
    
        return rotatedTour;
    }

    public Places createTour(Place[] tour) {
        Places tourArrayList = places;
        for (int i = 0; i < places.size(); i++) { tourArrayList.set(i, tour[i]); }
        return tourArrayList;
    }

    public double getRadius() {
        return this.radius;
    }

    public DistanceCalculator getCalculator() {
        return this.calc;
    }

    public long[][] getDistanceMatrix() {
        return this.distanceMatrix;
    }

    public boolean validatePlacesSize(Places p) { return p.size() < 4 || p.size() > 500; }

    public Places construct(Places places_, double radius_, String formula, double response_) throws BadRequestException {
        initializeTourOpt(places_, radius_, response_, formula);
        
        startTime = System.nanoTime();// I think we can just use postman for testing times
        endTime = startTime + (long) (750000000 * response); // 75% of the time to allow for overhead

        if (response == 0 || validatePlacesSize(places)) { return places; }

        return nearestNeighbor(places);
    }

    public void improve() {} 

} // End TourOptimizer class
