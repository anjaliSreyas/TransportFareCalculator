package farecalculator

import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.absoluteValue

data class Trip(
    var busNumber: String,
    var panNumber: String,
    var startStop: String,
    var endStop: String,
    var status: TripStatus,
    var rideEndedAt: LocalDateTime?,
    var rideStartedAt: LocalDateTime,
    var totalFare: BigDecimal,
    var companyId: String,
    var durationInSec: Long = calculateDurationOfTrip(rideStartedAt, rideEndedAt)

) {
}

fun calculateDurationOfTrip(rideStartedAt: LocalDateTime, rideEndedAt: LocalDateTime?): Long {
    if(rideEndedAt!=null){
      return  Duration.between(rideStartedAt, rideStartedAt).seconds.absoluteValue
    }
    else return 86400
}
