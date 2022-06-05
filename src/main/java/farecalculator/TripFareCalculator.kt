package farecalculator

import java.math.BigDecimal

interface TripFareCalculator {
    fun calculateFare(startStop: String, endStop: String): BigDecimal?
    fun addFare(toStop: String, fromStop: String, fare: BigDecimal): MutableMap<Set<String>, BigDecimal>
    fun addFare(fareList: MutableMap<Set<String>, BigDecimal>): MutableMap<Set<String>, BigDecimal>
}