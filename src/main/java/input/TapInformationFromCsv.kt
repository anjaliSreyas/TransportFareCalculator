package input

import Passenger
import TapInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class TapInformationFromCsv {
    var tapId: String? = ""
    var cardTappedDateTime: String? = null
    var tapType: String? = null
    var stopId: String? = null
    var company: String? = null
    var busNumber: String? = null
    var passengerPan: String? = null
}

fun TapInformationFromCsv.transformToTapInformation(): TapInfo {
    return TapInfo(
        tapId!!.toInt(),
        convertStringTimeToLocalDateTime(cardTappedDateTime!!.trim()),
        tapType = TapType.valueOf(tapType!!.trim()),
        passengerStop = stopId!!.trim(),
        busNumber = busNumber!!.trim(),
        passenger = Passenger(companyName = company!!.trim(), passengerPan!!.trim())
    )}



 fun convertStringTimeToLocalDateTime(dateToConvert: String?): LocalDateTime {

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return LocalDateTime.parse(dateToConvert, formatter);

}