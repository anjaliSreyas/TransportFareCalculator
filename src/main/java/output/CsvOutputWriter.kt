package output

import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder
import farecalculator.Trip
import java.io.Writer
import java.nio.file.Files
import java.nio.file.Paths

class CsvOutputWriter : OutputWriter {
    override fun writeOutput(trips: List<Trip>) {
        try {
            // create a write
            val writer: Writer = Files.newBufferedWriter(Paths.get("output.csv"))

            // create a csv writer
            val csvWriter = StatefulBeanToCsvBuilder<Trip>(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(false)
                .build()

            // create a list of objects (`User`)

            // write list of objects
            csvWriter.write(trips)

            // close the writer
            writer.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun convertTripToCsvOutputFormat(trip: Trip): CsvOutputFormat {
        return CsvOutputFormat(
            trip.rideStartedAt.toString(),
            trip.rideEndedAt.toString(),
            trip.durationInSec,
            trip.startStop,
            trip.endStop,
            trip.totalFare,
            trip.companyId,
            trip.busNumber,
            trip.panNumber,
            trip.status.name
        )
    }


}