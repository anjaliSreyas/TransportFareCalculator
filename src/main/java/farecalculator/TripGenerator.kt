package farecalculator

import FareNotFoundException
import TapInfo

class TripGenerator {
    private lateinit var tripStatusIdentifier: TripStatusIdentifier
    private lateinit var tripFareCalculatorImpl: TripFareCalculatorImpl

    fun generateTrips(passengerTapInformationList: List<TapInfo>) : List<Trip>{
        tripStatusIdentifier= TripStatusIdentifier()
        tripFareCalculatorImpl= TripFareCalculatorImpl()
    val trips =  tripStatusIdentifier.identifyTripStatus(passengerTapInformationList)
        for(trip in trips){
         val tripFare =  tripFareCalculatorImpl.calculateFare(trip.startStop, trip.endStop)
            if (tripFare != null) {
                trip.totalFare = tripFare
            }
            else {
                throw FareNotFoundException("Trip fare value is not present.")
            }
        }
        return trips
    }
}