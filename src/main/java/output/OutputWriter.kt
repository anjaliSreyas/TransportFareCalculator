package output

import farecalculator.Trip

interface OutputWriter {

    fun writeOutput(trips: List<Trip>)

}