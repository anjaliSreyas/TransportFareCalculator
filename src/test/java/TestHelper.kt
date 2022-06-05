//import com.opencsv.CSVWriter
//import java.io.File
//import java.io.FileWriter
//import java.io.IOException
//
//class TestHelperfun {
//
//    fun writeToCsv(passengerTapInfoList: List<PassengerTapInformation>) {
//        val CSV_HEADER = arrayOf<String>("id", "DateTimeUTC", "TapType", "StopId", "CompanyId", "BusID", "PAN")
//        var fileWriter: FileWriter? = null
//        var csvWriter: CSVWriter? = null
//        val filePath = File("PassengerTapInfo.csv")
//
//        try {
//            fileWriter = FileWriter("PassengerTapInfo.csv")
//
//            // write String Array
//            csvWriter = CSVWriter(
//                fileWriter,
//                CSVWriter.DEFAULT_SEPARATOR,
//                CSVWriter.NO_QUOTE_CHARACTER,
//                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
//                CSVWriter.DEFAULT_LINE_END
//            )
//
//            csvWriter.writeNext(CSV_HEADER)
//
//            for (tapInfo in passengerTapInfoList) {
//                val dataWrittenInCSV = arrayOf(
//                    tapInfo.tapId.toString(),
//                    tapInfo.cardTappedDateTime.toString(),
//                    tapInfo.tapType.toString(),
//                    tapInfo.company,
//                    tapInfo.busNumber,
//                    tapInfo.passangerPan
//                )
//                csvWriter.writeNext(dataWrittenInCSV)
//            }
//            println("Write CSV using CSVWriter successfully!")
//
//
//        } catch (e: Exception) {
//            println("Writing CSV error!")
//            e.printStackTrace()
//        } finally {
//            try {
//                fileWriter!!.flush()
//                fileWriter.close()
//                csvWriter!!.close()
//            } catch (e: IOException) {
//                println("Flushing/closing error!")
//                e.printStackTrace()
//            }
//        }
//    }
//}