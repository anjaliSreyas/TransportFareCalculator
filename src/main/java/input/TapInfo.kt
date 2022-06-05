import java.time.LocalDateTime

data class TapInfo(
    var tapId: Int,
    var cardTappedDateTime: LocalDateTime,
    var tapType: TapType,
    var passengerStop: String,
    var busNumber: String,
    var passenger: Passenger

) {

}

class Passenger(
    var companyName: String,
    var panNumber: String
) {
}
