import org.junit.Before;
import org.junit.Test;

import javax.measure.unit.NonSI;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;

import static org.junit.Assert.assertEquals;

/**
 * Check the SI, NonSI and the UnitFormat classes (from jscience)
 * for all the available units (at the bottom of the class)
 */
public class JscienceTests {
    @Before
    public void addMoreAliases() throws Exception {
        UnitFormat unitFormat = UnitFormat.getInstance();
        unitFormat.alias(NonSI.NAUTICAL_MILE, "nm");
        unitFormat.alias(NonSI.METRIC_TON, "mt");
    }

    private double convert(double value, String fromUnit, String toUnit) {
        return Unit.valueOf(fromUnit)
                .getConverterTo(Unit.valueOf(toUnit))
                .convert(value);
    }

    private void assertConversion(double fromValue, String fromUnit,
                                  double toValue, String toUnit) {
        double convertedValue = convert(fromValue, fromUnit, toUnit);
        assertEquals(toValue, convertedValue, .0001);
    }

    @Test
    public void canConvert() {
        assertConversion(5.00, "nm", 9.26, "km");
        assertConversion(1000, "kg/nm", 539.956803456, "kg/km");
        assertConversion(1.00, "mt/nm", 539.956803456, "kg/km");
    }
}
