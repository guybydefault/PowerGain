package ru.guybydefault.powergain.model

data class ExerciseType(
    val id: Int,
    val name: String,
    val exercises: MutableList<TrainingExercise>


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExerciseType

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }
}
