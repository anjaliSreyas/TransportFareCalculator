import farecalculator.TripFareCalculatorImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class TripFareCalculatorImplTest {

    @Test
    internal fun `should calculate fees for a trip from stop1 to stop2 trips`() {
        val tripFareCalculator = TripFareCalculatorImpl()
        tripFareCalculator.addFare("Stop1", "Stop2", BigDecimal(3.25))
        var fare = tripFareCalculator.calculateFare("Stop1", "Stop2")

        assertThat(fare).isEqualTo(BigDecimal(3.25))

    }

    @Test
    internal fun `should calculate fees for a trip from stop2 to stop3 trips`() {
        val tripFareCalculator = TripFareCalculatorImpl()
        tripFareCalculator.addFare("Stop1", "Stop2", BigDecimal(3.25))
        tripFareCalculator.addFare("Stop2", "Stop3", BigDecimal(5.50))
        var fare = tripFareCalculator.calculateFare("Stop2", "Stop3")
        assertThat(fare).isEqualTo(BigDecimal(5.50))
    }

    @Test
    internal fun `should calculate same fees for a trip from stop1 to stop2 and stop2 to stop1`() {
        val tripFareCalculator = TripFareCalculatorImpl()
        tripFareCalculator.addFare("Stop1", "Stop2", BigDecimal(3.25))
        var fare = tripFareCalculator.calculateFare("Stop2", "Stop1")
        assertThat(fare).isEqualTo(BigDecimal(3.25))
    }

    @Test
    internal fun `should calculate highest fee for incomplete trip`() {
        val tripFareCalculator = TripFareCalculatorImpl()
        tripFareCalculator.addFare("Stop1", "Stop2", BigDecimal(3.25))
        tripFareCalculator.addFare("Stop1", "Stop3", BigDecimal(7.30))
        var fare = tripFareCalculator.calculateFare("Stop1", "")
        assertThat(fare).isEqualTo(BigDecimal(7.30))
    }

    @Test
    internal fun `should calculate highest fee for incomplete trip given the start stop is empty`() {
        val tripFareCalculator = TripFareCalculatorImpl()
        tripFareCalculator.addFare("Stop1", "Stop2", BigDecimal(3.25))
        tripFareCalculator.addFare("Stop1", "Stop3", BigDecimal(7.30))
        tripFareCalculator.addFare("Stop2", "Stop3", BigDecimal(5.50))

        var fare = tripFareCalculator.calculateFare("", "Stop2")
        assertThat(fare).isEqualTo(BigDecimal(5.50))
    }

    @Test
    internal fun `should throw an exception when fare is not available for a given trip `() {
        val tripFareCalculator = TripFareCalculatorImpl()
        tripFareCalculator.addFare("Stop1", "Stop2", BigDecimal(3.25))
        tripFareCalculator.addFare("Stop1", "Stop3", BigDecimal(7.30))
        tripFareCalculator.addFare("Stop2", "Stop3", BigDecimal(5.50))

        var exception = assertThrows<FareNotFoundException> { tripFareCalculator.calculateFare("Stop3", "Stop5")}
        assertThat(exception.message).isEqualTo("Fare is missing for the trip")
    }
}


