import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class TapInformationReaderForCsvTest {

    val transportInformationReader = TapInformationReaderForCsv()

    @Test
    internal fun `given a csv file, in a certain format, should be able to read the csv and return a list of passengerTapInformation`() {
        val tapInfoCsv = File("src/test/resources/TapInfo.csv")
       with(transportInformationReader.readTapInfo(tapInfoCsv)){
           assertThat(this.size).isEqualTo(3)
           assertThat(this[0].tapId).isEqualTo(1)
           assertThat(this[0].busNumber).isEqualTo("Bus37")
           assertThat(this[0].passengerStop).isEqualTo("Stop1")
           assertThat(this[1].tapId).isEqualTo(2)
       }

    }

//    @Test
//    internal fun `given a csv file, if any field is null the csv reader should throw an exception`() {
//        TODO("Not yet implemented")
//    }
}