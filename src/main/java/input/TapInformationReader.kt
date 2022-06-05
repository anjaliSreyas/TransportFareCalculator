package input

import TapInfo
import java.io.File

interface TapInformationReader {

    fun readTapInfo(file: File): List<TapInfo>

}