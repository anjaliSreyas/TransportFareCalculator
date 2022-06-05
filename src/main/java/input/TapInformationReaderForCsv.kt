import input.CsvReader
import input.TapInformationFromCsv
import input.TapInformationReader
import input.transformToTapInformation
import java.io.File
import java.nio.file.Paths

open class TapInformationReaderForCsv() : TapInformationReader {
    private val csvReader = CsvReader()

    override fun readTapInfo(tapInfoCsv: File): List<TapInfo> {
        val fileToRead = Paths.get(tapInfoCsv.toURI())
        val listOfTapInformationFromCsv: List<TapInformationFromCsv> =
            csvReader.read(fileToRead, TapInformationFromCsv::class.java) as List<TapInformationFromCsv>
        val passengerTapInformationList = listOfTapInformationFromCsv.map { it.transformToTapInformation() }
        return passengerTapInformationList
    }

}

enum class TapType {
    ON, OFF
}