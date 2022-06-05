package farecalculator

import FareNotFoundException
import java.math.BigDecimal

class TripFareCalculatorImpl() : TripFareCalculator {
    companion object{
        private var fares: MutableMap<Set<String>, BigDecimal> = mutableMapOf()
        private var highestFare: MutableMap<String, BigDecimal> = mutableMapOf()
    }
    override fun calculateFare(startStop: String, endStop: String): BigDecimal? {
        when{
            endStop.isNullOrEmpty() -> {
                return highestFare[startStop] ?: throw FareNotFoundException("Fare is missing for the trip")
            }
            startStop.isNullOrEmpty() -> {
                return highestFare[endStop]?: throw FareNotFoundException("Fare is missing for the trip")
            }}

            var key = setOf<String>(startStop, endStop)
            return fares[key]?: throw FareNotFoundException("Fare is missing for the trip")
        }


    override fun addFare(toStop: String, fromStop: String, fare: BigDecimal): MutableMap<Set<String>, BigDecimal> {
        fares[setOf(toStop, fromStop)] = fare
        var highestFareForAStop = highestFare.get(toStop)?: highestFare.put(toStop, fare)
        if(highestFareForAStop?.compareTo(fare) == -1 ){
            highestFare[toStop] = fare
        }
        return fares
    }

    override fun addFare(fareList: MutableMap<Set<String>, BigDecimal>) : MutableMap<Set<String>, BigDecimal>{
        for(fare in fareList) {
            var stopSet: Set<String> = fare.key
            var fromStop = stopSet.elementAt(1)
            var toStop =  stopSet.elementAt(0)
            var highestFareForToStop = highestFare.get(toStop) ?: highestFare.put(toStop, fare.value)
            var highestFareFromStop = highestFare.get(toStop) ?: highestFare.put(fromStop, fare.value)
            if(highestFareForToStop?.compareTo(fare.value) == -1 ){
                highestFare[toStop] = fare.value
            }
            if(highestFareFromStop?.compareTo(fare.value) == -1 ){
                highestFare[fromStop] = fare.value
            }

        }
        fares = fareList
        return fares
    }

}

