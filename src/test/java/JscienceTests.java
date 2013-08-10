import org.junit.Before;
import org.junit.Test;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
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
        // check the Unit class for more methods: plus, times, divide, inverse
        UnitFormat unitFormat = UnitFormat.getInstance();
        unitFormat.alias(NonSI.NAUTICAL_MILE, "nm");
        unitFormat.alias(NonSI.METRIC_TON, "mt");
        unitFormat.alias(SI.HERTZ.times(1.0 / 60.0), "rpm");

        unitFormat.alias(NonSI.LITER, "l");
        unitFormat.alias(NonSI.LITER.times(1e-3), "dl");
        unitFormat.alias(NonSI.LITER.times(1e-6), "cl");
        unitFormat.alias(NonSI.LITER.times(1e-9), "ml");
    }

    @Test
    public void canConvert() {
        assertThat(5.00, "nm").isEqualTo(9.26, "km");
        assertThat(1000, "kg/nm").isEqualTo(539.956803456, "kg/km");
        assertThat(1.00, "mt/nm").isEqualTo(539.956803456, "kg/km");
        assertThat(120, "rpm").isEqualTo(2.0, "Hz");
        assertThat(1000, "l").isEqualTo(1, "m^3");
        assertThat(1, "l").isEqualTo(1, "dm^3");
        assertThat(1, "dl").isEqualTo(1000, "cl");
        assertThat(1, "dl").isEqualTo(1000000, "ml");
        assertThat(1, "m^3").isEqualTo(1000000, "dl");
        assertThat(1000, "cl").isEqualTo(1, "dl");
    }

    public static ConversionAssert assertThat(double fromValue, String fromUnit) {
        return new ConversionAssert(fromValue, fromUnit);
    }

    private static class ConversionAssert {
        public static final double DELTA = .0001;
        private double fromValue;
        private String fromUnit;

        private ConversionAssert(double fromValue, String fromUnit) {
            this.fromValue = fromValue;
            this.fromUnit = fromUnit;
        }

        public void isEqualTo(double toValue, String toUnit) {
            double convertedValue = convert(fromValue, fromUnit, toUnit);
            String message = String.format("conversion from %s to %s", fromUnit, toUnit);
            assertEquals(message, toValue, convertedValue, DELTA);
        }

        private double convert(double value, String fromUnit, String toUnit) {
            return Unit.valueOf(fromUnit)
                    .getConverterTo(Unit.valueOf(toUnit))
                    .convert(value);
        }

    }
}
