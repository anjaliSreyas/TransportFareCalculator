import farecalculator.TripStatus.*
import farecalculator.TripStatusIdentifier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

open class TripStatusIdentifierTest {
    private lateinit var tripStatusIdentifier: TripStatusIdentifier

    @BeforeEach
    internal fun setUp() {
        tripStatusIdentifier = TripStatusIdentifier()

    }

    @Test
    internal fun `should be able to mark a trip completed trip given passenger gets on at stop1 and gets off at stop2`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val passengerTapInformationForPassenger1TapOn = createPassengerTapInformation(
            tapId = 1,
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555559")
        )

        val passengerTapInformationForPassenger1TapOff = createPassengerTapInformation(
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
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        assertThat(listOfPassengerWithCompletedTrips[0].status).isEqualTo(COMPLETED)
        assertThat(listOfPassengerWithCompletedTrips[0].startStop).isEqualTo("Stop1")
        assertThat(listOfPassengerWithCompletedTrips[0].endStop).isEqualTo("Stop2")


    }

    @Test
    internal fun `should be able to mark all trip complete given same passenger have multiple trips on the same bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555560"),
        )

        val Passenger1TapOffInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 15, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            passenger = Passenger("Company1", "5500005555555560"),
        )
        val Passenger1TapOnInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.ON,
            passengerStop = "Stop3",
            passenger = Passenger("Company1", "5500005555555560"),

            )
        val Passenger1TapOffInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 35, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            passenger = Passenger("Company1", "5500005555555560"),

            )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapOffInfoStop3,
                Passenger1TapOnInfoStop3,
                Passenger1TapOffInfoStop1
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop3")

        }
        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(startStop).isEqualTo("Stop3")
            assertThat(endStop).isEqualTo("Stop1")
        }
    }

    @Test
    internal fun `should be able to mark all trip complete given same passenger have multiple trips on the different bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"
        )

        val Passenger1TapOffInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 15, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus37"

        )
        val Passenger1TapOnInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.ON,
            passengerStop = "Stop3",
            busNumber = "Bus38"

        )
        val Passenger1TapOffInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 35, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus38"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapOffInfoStop3,
                Passenger1TapOnInfoStop3,
                Passenger1TapOffInfoStop1
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        assertThat(listOfPassengerWithCompletedTrips[0].status).isEqualTo(COMPLETED)
        assertThat(listOfPassengerWithCompletedTrips[1].status).isEqualTo(COMPLETED)
    }

    @Test
    internal fun `should be able to mark all trip completed given different passenger have multiple trips on the same bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"
        )

        val Passenger1TapOffInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 15, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus37"

        )
        val Passenger1TapOnInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.ON,
            passengerStop = "Stop3",
            busNumber = "Bus38"

        )
        val Passenger1TapOffInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 35, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus38"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapOffInfoStop3,
                Passenger1TapOnInfoStop3,
                Passenger1TapOffInfoStop1
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop3")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(startStop).isEqualTo("Stop3")
            assertThat(endStop).isEqualTo("Stop1")
            assertThat(busNumber).isEqualTo("Bus38")
        }


    }

    @Test
    internal fun `should be able to mark trip cancelled given a passenger taps on and taps off at the same stop`() {
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"
        )

        val Passenger1TapOffInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus37"

        )
    }

    @Test
    internal fun `should be able to identify cancelled trip given a passenger has multiple trips on different bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"
        )

        val Passenger1TapOffInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus37"

        )

        val Passenger1TapONInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus38"

        )
        val Passenger1TapOffInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 20, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus38"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapOffInfoStop1,
                Passenger1TapONInfoStop1,
                Passenger1TapOffInfoStop3
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        assertThat(listOfPassengerWithCompletedTrips[0].status).isEqualTo(CANCELLED)
        assertThat(listOfPassengerWithCompletedTrips[0].busNumber).isEqualTo("Bus37")
        assertThat(listOfPassengerWithCompletedTrips[1].status).isEqualTo(COMPLETED)
        assertThat(listOfPassengerWithCompletedTrips[1].busNumber).isEqualTo("Bus38")

    }

    @Test
    internal fun `should be able to identify cancelled trip given a passenger has multiple trips on the same bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"
        )

        val Passenger1TapOffInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus37"

        )

        val Passenger1TapONInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"

        )
        val Passenger1TapOffInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 20, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus37"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapOffInfoStop1,
                Passenger1TapONInfoStop1,
                Passenger1TapOffInfoStop3
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(CANCELLED)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop1")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop3")

        }

    }

    @Test
    internal fun `should be able to identify incomplete trip given a list of passenger tap info with completed trip on different bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37"
        )

        val Passenger1TapONInfoStop2 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus38"

        )

        val Passenger1TapONInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus38"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapONInfoStop2,
                Passenger1TapONInfoStop3
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(busNumber).isEqualTo("Bus38")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop3")

        }

    }

    @Test
    internal fun `should be able to identify incomplete trip given a list of passenger tap with multiple passengers`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37",
            passenger = Passenger("Company1", "5500005555555562")

        )

        val Passenger2TapONInfoStop2 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37",
            passenger = Passenger("Company1", "5500005555555561")

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger2TapONInfoStop2

            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(panNumber).isEqualTo("5500005555555562")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(panNumber).isEqualTo("5500005555555561")

        }
    }


    @Test
    internal fun `should be able to identify two incomplete trip given passenger taps on at one bus and tap offs at another bus`() {
        val tripStatusIdentifier = TripStatusIdentifier()
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37",
            passenger = Passenger("Company1", "5500005555555562")

        )

        val Passenger2TapONInfoStop2 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus38",
            passenger = Passenger("Company1", "5500005555555562")

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger2TapONInfoStop2

            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(panNumber).isEqualTo("5500005555555562")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus38")
            assertThat(panNumber).isEqualTo("5500005555555562")
            assertThat(endStop).isEqualTo("Stop1")

        }

    }

    @Test
    internal fun `should be able to identify incomplete trip given a list of passenger tap info with completed and cancelled trips`() {
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37",
        )

        val Passenger1TapONInfoStop2 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus38"

        )

        val Passenger1TapOFFInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus38"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapONInfoStop2,
                Passenger1TapOFFInfoStop3
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(busNumber).isEqualTo("Bus38")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop3")

        }
    }


    @Test
    internal fun `should be able to identify incomplete trip given there is only tap off information for a trip`() {
        val Passenger1TapOnInfoStop1 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus37",
        )

        val Passenger1TapONInfoStop2 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0, 0),
            tapType = TapType.ON,
            passengerStop = "Stop1",
            busNumber = "Bus38"

        )

        val Passenger1TapONInfoStop3 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop3",
            busNumber = "Bus38"

        )

        val Passenger1TapOFFInfoStop2 = createPassengerTapInformation(
            cardTappedDateTime = LocalDateTime.of(2022, 1, 1, 12, 30, 0),
            tapType = TapType.OFF,
            passengerStop = "Stop1",
            busNumber = "Bus37"

        )

        val passengerTapInformationList =
            listOf<TapInfo>(
                Passenger1TapOnInfoStop1,
                Passenger1TapONInfoStop2,
                Passenger1TapONInfoStop3,
                Passenger1TapOFFInfoStop2
            )
        val listOfPassengerWithCompletedTrips = tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        with(listOfPassengerWithCompletedTrips[0]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(startStop).isEqualTo("Stop1")
        }

        with(listOfPassengerWithCompletedTrips[1]) {
            assertThat(status).isEqualTo(COMPLETED)
            assertThat(busNumber).isEqualTo("Bus38")
            assertThat(startStop).isEqualTo("Stop1")
            assertThat(endStop).isEqualTo("Stop3")
        }

        with(listOfPassengerWithCompletedTrips[2]) {
            assertThat(status).isEqualTo(INCOMPLETE)
            assertThat(busNumber).isEqualTo("Bus37")
            assertThat(endStop).isEqualTo("Stop1")
        }
    }



    fun createPassengerTapInformation(
        tapId: Int = Random().nextInt(),
        cardTappedDateTime: LocalDateTime,
        tapType: TapType = TapType.ON,
        passengerStop: String = "Stop1",
        passenger: Passenger = Passenger("Company1", "5500005555555559"),
        busNumber: String = "Bus37"

    ): TapInfo {
        return TapInfo(
            tapId,
            cardTappedDateTime,
            tapType,
            passengerStop,
            busNumber,
            passenger,
        )
    }
}
