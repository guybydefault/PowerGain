package ru.guybydefault.powergain.calculation

class BasicOneRepMaxCalculator : AbstractOneRepMaxCalculator() {

    override fun calculate(reps: Int, weight: Double): Double {
        return weight * (1 + reps / 30.0)
    }

}