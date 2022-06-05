import farecalculator.TripFareCalculatorImpl
import farecalculator.TripGenerator
import output.CsvOutputWriter
import java.io.File
import java.math.BigDecimal

class Application {

    companion object{

            @JvmStatic
            fun main(args: Array<String>) {
                val tapInfoReader  = TapInformationReaderForCsv()
                val tripGenerator = TripGenerator()
                val tripFareCalculator = TripFareCalculatorImpl()
                val csvWriter = CsvOutputWriter()
                val tapInfoCsv = File("src/main/resources/TapInfo.csv")

                val fareList = mutableMapOf<Set<String>, BigDecimal>()
                fareList[setOf("Stop1", "Stop2")] = BigDecimal(3.50)
                fareList[setOf("Stop2", "Stop3")] = BigDecimal(5.50)
                fareList[setOf("Stop1", "Stop3")] = BigDecimal(7.50)
                tripFareCalculator.addFare(fareList)

                val tapList = tapInfoReader.readTapInfo(tapInfoCsv)
                val trips = tripGenerator.generateTrips(tapList)
                csvWriter.writeOutput(trips)

            }


    }
}