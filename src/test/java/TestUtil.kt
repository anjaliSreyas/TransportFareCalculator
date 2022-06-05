import java.time.LocalDateTime
import java.util.*

class TestUtil {

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