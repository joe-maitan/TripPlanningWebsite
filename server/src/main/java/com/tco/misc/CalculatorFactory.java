package com.tco.misc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.BadRequestException;
import java.io.IOException;

public class CalculatorFactory {

    private static final transient Logger log = LoggerFactory.getLogger(CalculatorFactory.class);

    public CalculatorFactory() {}

    public boolean isVincenty(String formula) {
        return formula == null || formula.equals("vincenty");
    } // End isVincenty(formula) method

    public boolean isHaversine(String formula) {
        return formula.equals("haversine");
    } // End isHaversine(formula) method

    public boolean isCosines(String formula) {
        return formula.equals("cosines");
    } // End isCosines(formula) method
    
    public DistanceCalculator get(String formula) throws BadRequestException {
        try {
            if (isVincenty(formula)) {
                VincentyCalculator calc = new VincentyCalculator();
                return calc;
            } else if (isHaversine(formula)) {
                HaversineCalculator calc = new HaversineCalculator();
                return calc;
            } else if (isCosines(formula)) {
                CosinesCalculator calc = new CosinesCalculator();
                return calc;
            } else {
                throw new BadRequestException();
            }
        } catch(BadRequestException e) {
            log.info("Bad Request - Invalid Formula");
            throw new BadRequestException();
        } // End try-catch block
    } // End get(formula) method

} // End CalculatorFactory class
