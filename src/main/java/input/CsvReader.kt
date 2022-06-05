package input

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.Reader
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path

open class CsvReader {
    // TODO: make this class more generic

    @Throws(Exception::class)
    open fun read(path: Path?, beanToMap: Class<TapInformationFromCsv>): List<Any?> {
        val reader: Reader = Files.newBufferedReader(path)
        val columnMappingString: Array<String> = arrayOf("tapId", "cardTappedDateTime", "tapType", "stopId", "company", "busNumber", "passengerPan")
        val cb: CsvToBean<Any> = CsvToBeanBuilder<Any>(reader)
            .withType(beanToMap)
            .withMappingStrategy(setColumMapping(beanToMap, columnMappingString))
            .withSkipLines(1)
            .build()
        var list = cb.parse()
        return list

    }

    private fun setColumMapping(beanToMap: Class<TapInformationFromCsv>, columnMappingString: Array<String>): ColumnPositionMappingStrategy<Any?> {
        var strategy = ColumnPositionMappingStrategy<Any?>()
        strategy.type = beanToMap
        strategy.setColumnMapping(*columnMappingString)
        return strategy;
    }
}