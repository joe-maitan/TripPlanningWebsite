package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCalculatorFactory {
    public CalculatorFactory calcFac;

    @BeforeEach
    public void initialsizeCalculatorFactory() {
        calcFac = new CalculatorFactory();
    }

    @Test
    @DisplayName("bagel: initial functionality test for get()")
    public void getInitialTest() throws BadRequestException {
        assert(calcFac.get("haversine") instanceof HaversineCalculator);
    }

    @Test
    @DisplayName("bagel: ensuring vincenty works")
    public void getVincentyTest() throws BadRequestException {
        assert(calcFac.get("vincenty") instanceof VincentyCalculator);
    }

    @Test
    @DisplayName("bagel: ensuring vincenty works with null")
    public void getVincentyNullTest() throws BadRequestException {
        assert(calcFac.get(null) instanceof VincentyCalculator);
    }

    @Test
    @DisplayName("bagel: ensuring cosines works")
    public void getCosinesTest() throws BadRequestException {
        assert(calcFac.get("cosines") instanceof CosinesCalculator);
    }

    @Test
    @DisplayName("puleoja: Throw Error")
    public void throwError() throws BadRequestException {
        boolean working = false;
        try {
            calcFac.get("Invalid");
        }
        catch(BadRequestException e) {
            working = true;
        }
        assert(working);
    }
}
