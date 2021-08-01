package ru.guybydefault.powergain.calculation

interface OneRepMaxCalculator {

    /**
     * Calculate one repetition maximum weight based on the best reps & weight result.
     * @return Calculated one rep max
     */
    fun calculateOneRepMax(reps: Int, weight: Double): Double

}