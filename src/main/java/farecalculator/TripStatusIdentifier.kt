package farecalculator

import TapInfo
import farecalculator.TripStatus.*
import java.math.BigDecimal
import java.time.LocalDateTime

open class TripStatusIdentifier {

    private var tapOnsByPAN = mutableMapOf<String, TapInfo>()

    fun identifyTripStatus(tapInfo: List<TapInfo>): List<Trip> {
        var trips = mutableListOf<Trip>()
        for (tap in tapInfo) {
            when {
                tap.tapType.name.equals("ON") -> {
                    trips.addAll(processTapOn(tap))
                }
                tap.tapType.name.equals("OFF") -> {
                    trips.addAll(processTapOff(tap))
                }
            }
        }
        trips.addAll(markAllOpenTripsIncomplete())
        return trips
    }


    private fun processTapOff(tapOff: TapInfo): List<Trip> {
        with(tapOff) {
            var tapOn = tapOnsByPAN.remove(passenger.panNumber)
            if (tapOn == null) {
                return listOf(createIncompleteTripFromTapOff(tapOff)) }
             else {
                return processTapOnTapOff(tapOn, tapOff)
            }
        }
    }

    private fun processTapOn(tapOn: TapInfo): List<Trip> {
        var retList = mutableListOf<Trip>()
        with(tapOn) {
            var existingTapOn = tapOnsByPAN.remove(passenger.panNumber);
            if (existingTapOn != null) {
                retList.add(createIncompleteTripFromTapOn(existingTapOn)) }
            tapOnsByPAN.put(passenger.panNumber, this)
            return retList
        }
    }

    private fun processTapOnTapOff(tapOn: TapInfo, tapOff: TapInfo): List<Trip> {
        if(tapOn.busNumber == tapOff.busNumber && tapOn.passengerStop == tapOff.passengerStop)
        {
            return listOf(createCancelledTrip(tapOn, tapOff))
        }
        else if (tapOn.busNumber == tapOff.busNumber)
        {
            return listOf(createCompletedTrip(tapOn, tapOff))
        }
        else
        {
            return listOf(createIncompleteTripFromTapOn(tapOn), createIncompleteTripFromTapOff(tapOff))
        }
    }

    private fun createCancelledTrip(tapOn: TapInfo, tapOff: TapInfo): Trip {
        return createTrip(tapOn, tapOff, CANCELLED)
    }
    private fun createCompletedTrip(tapOn: TapInfo, tapOff: TapInfo): Trip {
        return createTrip(tapOn, tapOff, COMPLETED)
    }

    private fun createTrip(tapOn: TapInfo, tapOff: TapInfo, status: TripStatus): Trip {
        return Trip(
            tapOn.busNumber,
            tapOn.passenger.panNumber,
            startStop = tapOn.passengerStop,
            endStop = tapOff.passengerStop,
            status = status,
            rideEndedAt = tapOff.cardTappedDateTime,
            rideStartedAt = tapOn.cardTappedDateTime,
            totalFare = BigDecimal.ZERO,
            companyId = tapOn.passenger.companyName
        )
    }

    private fun createIncompleteTripFromTapOn(tapOn: TapInfo): Trip{
        with(tapOn) {
            return Trip(
                busNumber,
                passenger.panNumber,
                startStop = passengerStop,
                endStop = "",
                status = INCOMPLETE,
                rideEndedAt = null,
                rideStartedAt = cardTappedDateTime,
                totalFare = BigDecimal.ZERO,
                companyId = tapOn.passenger.companyName
            )
        }
    }

    //This shouldn't happen in reality unless the terminal provides us invalid data (i.e tap off before tap on)
    private fun createIncompleteTripFromTapOff(tapOff: TapInfo) : Trip{
        with(tapOff) {
            return Trip(
                busNumber,
                passenger.panNumber,
                startStop = "",
                endStop = passengerStop,
                status = INCOMPLETE,
                rideEndedAt = cardTappedDateTime,
                rideStartedAt = LocalDateTime.MIN,
                totalFare = BigDecimal.ZERO,
                companyId = tapOff.passenger.companyName
            )
        }
    }

    private fun markAllOpenTripsIncomplete(): List<Trip> {
        var retList = mutableListOf<Trip>()
        for (tapOn in tapOnsByPAN.values)
        {
            retList.add(createIncompleteTripFromTapOn(tapOn))
        }
        tapOnsByPAN.clear()
        return retList
    }
}
