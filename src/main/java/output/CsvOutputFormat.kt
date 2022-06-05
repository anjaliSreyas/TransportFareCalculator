package output

import com.opencsv.bean.CsvBindByName
import java.math.BigDecimal

class CsvOutputFormat(
    @CsvBindByName(column = "Started", required = true)
    var started: String,
    @CsvBindByName(column = "Finished", required = true)
    var finished: String,
    @CsvBindByName(column = "Duration", required = true)
    var duration: Long,
    @CsvBindByName(column = "FromStopId", required = true)
    var fromStopId: String,
    @CsvBindByName(column = "ToStopId", required = true)
    var toStopId: String,
    @CsvBindByName(column = "ChargeAmount", required = true)
    var chargeAmount: BigDecimal,
    @CsvBindByName(column = "CompanyId", required = true)
    var companyId: String,
    @CsvBindByName(column = "BusId", required = true)
    var busId: String,
    @CsvBindByName(column = "Pan", required = true)
    var pan: String,
    @CsvBindByName(column = "Status", required = true)
    var status: String
) {

}
