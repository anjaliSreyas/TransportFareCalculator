import farecalculator.TripFareCalculatorImpl
import farecalculator.TripGenerator
import farecalculator.TripStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class TripGeneratorTest {
    private lateinit var testUtil: TestUtil

    @BeforeEach
    internal fun setUp() {
        testUtil = TestUtil()
        val tripFareCalculator = TripFareCalculatorImpl()
        val fareList = mutableMapOf<Set<String>, BigDecimal>()
        fareList[setOf("Stop1", "Stop2")] = BigDecimal(3.50)
        fareList[setOf("Stop2", "Stop3")] = BigDecimal(5.50)
        fareList[setOf("Stop1", "Stop3")] = BigDecimal(7.50)
        tripFareCalculator.addFare(fareList)
    }


    @Test
    internal fun `generate trip with fare for a given list of taps`() {
        val tripGenerator = TripGenerator()


        val passengerTapInformationForPassenger1TapOn = testUtil.createPassengerTapInformation(
            tapId = 1,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555559")
        )

        val passengerTapInformationForPassenger1TapOff = testUtil.createPassengerTapInformation(
            tapId = 2,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop2",
            passenger = Passenger("Company1", "5500005555555559")

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                passengerTapInformationForPassenger1TapOn,
                passengerTapInformationForPassenger1TapOff
            )

        val fareList = mutableMapOf<Set<String>, BigDecimal>()
        fareList[setOf("Stop1", "Stop2")] = BigDecimal(3.50)
        fareList[setOf("Stop2", "Stop3")] = BigDecimal(5.50)


      val trips = tripGenerator.generateTrips(passengerTapInformationList)
        with(trips[0]) {
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop2")
            assertThat(status).isEqualTo(TripStatus.COMPLETED)
            assertThat(totalFare).isEqualTo(BigDecimal(3.50))
        }


    }

    @Test
    internal fun `generate trip with fare for a given list of taps with some incomplete trips`() {
        val tripGenerator = TripGenerator()


        val passengerTapInformationForPassenger1TapOn = testUtil.createPassengerTapInformation(
            tapId = 1,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555559")
        )

        val passengerTapInformationForPassenger1TapOff = testUtil.createPassengerTapInformation(
            tapId = 2,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop2",
            passenger = Passenger("Company1", "5500005555555559")

        )
        val passengerTapInformationForTapOn = testUtil.createPassengerTapInformation(
            tapId = 1,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555559"),
            busNumber = "Bus37"
        )


        val passengerTapInformationList =
            listOf<TapInfo>(
                passengerTapInformationForPassenger1TapOn,
                passengerTapInformationForPassenger1TapOff,
                passengerTapInformationForTapOn
            )


        val trips = tripGenerator.generateTrips(passengerTapInformationList)
        with(trips[0]) {
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop2")
            assertThat(status).isEqualTo(TripStatus.COMPLETED)
            assertThat(totalFare).isEqualTo(BigDecimal(3.50))
        }
        with(trips[1]) {
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEmpty()
            assertThat(status).isEqualTo(TripStatus.INCOMPLETE)
            assertThat(totalFare).isEqualTo(BigDecimal(7.50))
        }

    }
    @Test
    internal fun `generate trip with max fare for a given list of taps with edge case when system error records tap off only`() {
        val tripGenerator = TripGenerator()

        val passengerTapInformationForPassenger1TapOn = testUtil.createPassengerTapInformation(
            tapId = 1,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555559")
        )

        val passengerTapInformationForPassenger1TapOff = testUtil.createPassengerTapInformation(
            tapId = 2,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop2",
            passenger = Passenger("Company1", "5500005555555559")

        )
        val passengerTapInformationForTapOn = testUtil.createPassengerTapInformation(
            tapId = 1,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555559"),
            busNumber = "Bus37"
        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                passengerTapInformationForPassenger1TapOn,
                passengerTapInformationForPassenger1TapOff,
                passengerTapInformationForTapOn
            )

        val trips = tripGenerator.generateTrips(passengerTapInformationList)
        with(trips[0]) {
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop2")
            assertThat(status).isEqualTo(TripStatus.COMPLETED)
            assertThat(totalFare).isEqualTo(BigDecimal(3.50))
        }
        with(trips[1]) {
            assertThat(startStop).isEmpty()
            assertThat(endStop).isEqualTo("Stop1")
            assertThat(status).isEqualTo(TripStatus.INCOMPLETE)
            assertThat(totalFare).isEqualTo(BigDecimal(7.50))
        }
    }

    }