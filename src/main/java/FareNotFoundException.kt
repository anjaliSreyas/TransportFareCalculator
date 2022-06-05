import java.lang.RuntimeException

class FareNotFoundException(private val detailMessage: String) : RuntimeException(detailMessage) {

}
