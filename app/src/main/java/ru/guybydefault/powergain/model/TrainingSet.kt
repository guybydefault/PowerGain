package ru.guybydefault.powergain.model

data class TrainingSet(
    /**
     * Kilograms
     */
    val weight: Double,
    val repetitions: Int,
    /**
     * Rest time in seconds before next set
     */
    val rest: Int?

) {
    fun multiply(times: Int): List<TrainingSet> {
        val list = mutableListOf<TrainingSet>()
        for (i in 0..times) {
            list.add(copy(weight, repetitions, rest))
        }
        return list
    }
}
