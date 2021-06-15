package ru.guybydefault.powergain.serializer

import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.model.TrainingSet

class TrainingExerciseSerializer {

    fun serialize(training: TrainingExercise): String {
        val it: ListIterator<TrainingSet> = training.sets.listIterator()
        var prev: TrainingSet?
        var next: TrainingSet? = null
        val sb = StringBuilder()
        var count = 0
        while (it.hasNext()) {
            prev = next
            next = it.next()
            if (prev == null) {
                sb.append("${next.weight}кг ")
                count = 1
            } else if (next.weight != prev.weight) {
                sb.append("${count}x${prev.repetitions}\n")
                sb.append("${next.weight}кг ")
                count = 1
            } else {
                // weights are equal
                if (next.repetitions == prev.repetitions) {
                    count++
                } else {
                    sb.append("${count}x${prev.repetitions} ")
                    count = 1
                }
            }

            if (!it.hasNext()) {
                sb.append("${count}x${next.repetitions}")
            }
        }
        return sb.toString()
    }
}