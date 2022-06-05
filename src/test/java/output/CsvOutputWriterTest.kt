package output

import farecalculator.Trip
import farecalculator.TripStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

class CsvOutputWriterTest {

    private lateinit var csvWriter: CsvOutputWriter

    @Test
    internal fun `should return a csv file given a list of trips`() {
        csvWriter = CsvOutputWriter()


        var tripList: List<Trip> = listOf(
            Trip(
                "Bus37",
                "5500005555555559",
                "Stop1",
                "Stop3",
                TripStatus.COMPLETED,
                LocalDateTime.of(2022, 6, 1, 11, 15, 16),
                LocalDateTime.of(2022, 6, 1, 11, 0, 0),
                BigDecimal(5.50),"Company1"
            ),
            Trip(
                "Bus37",
                "5500005555555561",
                "Stop1",
                "Stop3",
                TripStatus.COMPLETED,
                LocalDateTime.of(2022, 6, 1, 11, 15, 16),
                LocalDateTime.of(2022, 6, 1, 11, 0, 0),
                BigDecimal(5.50),"Company1"
            ),
            Trip(
                "Bus37",
                "5500005555555561",
                "Stop1",
                "",
                TripStatus.COMPLETED,
                LocalDateTime.of(2022, 6, 1, 11, 15, 16),
                LocalDateTime.of(2022, 6, 1, 11, 0, 0),
                BigDecimal(5.50),"Company1"
            )
        )
        csvWriter.writeOutput(tripList)
        var file = File("output.csv")
        var fileExists = file.exists()
        assertThat(fileExists).isEqualTo(true)

    }

}