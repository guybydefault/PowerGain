package ru.guybydefault.powergain.parser

import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.model.TrainingSet
import java.io.BufferedReader
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.time.LocalDate
import java.util.regex.Pattern
import kotlin.properties.Delegates

class Parser(bufferedReader: BufferedReader) {
    private val input: String
    private var currToken: String? = null
    private var currPos = 0

    private var currYear by Delegates.notNull<Int>()
    private var currDay by Delegates.notNull<Int>()
    private var currMonth by Delegates.notNull<Int>()

    companion object {
        val NEW_LINE_SEP = ','
        val ESCAPE_SYMBOLS = charArrayOf('\n', ' ', '\r', NEW_LINE_SEP)
        val COMMENT_OPEN_BRACKET = '('
        val COMMENT_CLOSE_BRACKET = ')'
        val SET_PARAM_DELIMITERS = charArrayOf('x', 'х')
        val WEIGHT_PARAM_DELIMITER = "кг"
        val WEIGHT_PATTERN = Pattern.compile("\\d+(.\\d+)?" + WEIGHT_PARAM_DELIMITER)
        val SET_PARAMS_PATTERN =
            Pattern.compile("\\d*[" + SET_PARAM_DELIMITERS.joinToString("") + "]\\d*")
        val DATE_PATTERN = Pattern.compile("\\d\\d.\\d\\d")
        val YEAR_PATTERN = Pattern.compile("\\d\\d\\d\\d")
    }

    init {
        input = bufferedReader.readLines().joinToString()
    }

    enum class TokenType {
        SET_PARAMS,
        SET_WEIGHT,
        DATE,
        YEAR,
        STR,
        OPEN_BRACKET,
        CLOSE_BRACKET
    }

    fun nextToken(): String? {
        currToken = parseNextToken()
        return currToken
    }

    private fun inputLeft(): Boolean {
        return currPos < input.length
    }

    private fun isEscapeSymbol(): Boolean {
        return input[currPos] in ESCAPE_SYMBOLS
    }

    fun parseNextToken(): String? {
        while (inputLeft() && isEscapeSymbol()) {
            currPos++
        }
        if (!inputLeft()) {
            return null
        }
        if (input[currPos] == COMMENT_OPEN_BRACKET) {
            currPos++
            return input[currPos - 1].toString()
        } else if (input[currPos] == COMMENT_CLOSE_BRACKET) {
            currPos++
            return input[currPos - 1].toString()
        } else {
            val stringBuilder = StringBuilder(6)
            while (inputLeft() && !isEscapeSymbol()) {
                stringBuilder.append(input[currPos])
                currPos++
            }
            return stringBuilder.toString()
        }
    }

    fun String.getTokenType(): TokenType {
        if (YEAR_PATTERN.matcher(this).matches()) {
            return TokenType.YEAR
        } else if (WEIGHT_PATTERN.matcher(this).matches()) {
            return TokenType.SET_WEIGHT
        } else if (SET_PARAMS_PATTERN.matcher(this).matches()) {
            return TokenType.SET_PARAMS
        } else if (DATE_PATTERN.matcher(this).matches()) {
            return TokenType.DATE
        } else if (COMMENT_OPEN_BRACKET.toString() == this) {
            return TokenType.OPEN_BRACKET
        } else if (COMMENT_CLOSE_BRACKET.toString() == this) {
            return TokenType.CLOSE_BRACKET
        } else {
            return TokenType.STR
        }
    }

    fun parseExerciseName(): String {
        val exerciseNameSb = StringBuilder()
        while (currToken!!.getTokenType() == TokenType.STR) {
            if (!exerciseNameSb.isEmpty()) {
                exerciseNameSb.append(' ')
            }
            exerciseNameSb.append(currToken)
            nextToken()
        }
        return exerciseNameSb.toString()
    }

    private val mapTrainingTypes = mutableMapOf<String, ExerciseType>()
    private var typeCount = 0

    private fun getExerciseType(exerciseName: String): ExerciseType {
        var trainingType = mapTrainingTypes[exerciseName]
        if (trainingType == null) {
            trainingType = ExerciseType(typeCount, exerciseName)
            mapTrainingTypes[exerciseName] = trainingType
            typeCount++
        }
        return trainingType
    }

    fun parseExercise(): TrainingExercise {
        val exerciseName = parseExerciseName()
        val trainingSets = mutableListOf<TrainingSet>()
        val exercise = TrainingExercise(
            getExerciseType(exerciseName),
            trainingSets,
            null,
            LocalDate.of(currYear, currMonth, currDay),
            null
        )
        var weight = 0.0
        var weightSet = false
        while (currToken != null) {
            when (currToken!!.getTokenType()) {
                TokenType.SET_PARAMS -> {
                    if (!weightSet) {
                        throw ParserException("Weight is not set ${currToken}")
                    }

                    for ((index, param_delimiter) in SET_PARAM_DELIMITERS.withIndex()) {
                        if (currToken!!.split(param_delimiter).size > 1) {
                            val setsCount = currToken!!.split(param_delimiter)[0].toInt()
                            val reps = currToken!!.split(param_delimiter)[1].toInt()

                            for (i in 0 until setsCount) {
                                trainingSets.add(TrainingSet(weight, reps, null))
                            }

                            break
                        } else if (index == SET_PARAM_DELIMITERS.size - 1) {
                            throw ParserException("Can't split set_param $currToken")
                        }
                    }

                }
                TokenType.SET_WEIGHT -> {
                    weight = currToken!!.split(WEIGHT_PARAM_DELIMITER)[0].toDouble()
                    weightSet = true
                }
                else -> {
                    if (trainingSets.isEmpty()) {
                        throw ParserException("Training sets is empty ${currToken}")
                    }
                    return exercise
                }
            }
            nextToken()
        }
        if (exercise.sets.isEmpty()) {
            throw ParserException("Can't parse exercise")
        } else {
            return exercise
        }
    }

    fun parseComment(): String {
        var commentText = StringBuilder()
        nextToken()
        while (currToken != null && currToken!!.getTokenType() != TokenType.CLOSE_BRACKET) {
            commentText.append(currToken)
            nextToken()
        }
        return commentText.toString()
    }

    fun getLinesCount(): Int {
        return input.substring(0, currPos).count { ch -> ch == NEW_LINE_SEP } + 1

    }

    fun parse(): MutableList<TrainingExercise> {
        try {
            return parseTrainings()
        } catch (e: ParserException) {
            throw IllegalStateException(e.message!! + " at line " + getLinesCount(), e)
        } catch (e: Exception) {
            throw RuntimeException("Exception happened at line ${getLinesCount()}", e)
        }
    }

    fun parseTrainings(): MutableList<TrainingExercise> {
        val result = mutableListOf<TrainingExercise>()
        nextToken()
        while (true) {
            if (currToken == null) {
                return result
            }
            when (currToken!!.getTokenType()) {
                TokenType.YEAR -> {
                    currYear = currToken!!.toInt()
                    nextToken()
                }
                TokenType.DATE -> {
                    currDay = currToken!!.split('.')[0].toInt()
                    currMonth = currToken!!.split('.')[1].toInt()
                    nextToken()
                }
                TokenType.OPEN_BRACKET -> {
                    parseComment()
                    nextToken()
                }
                TokenType.STR -> {
                    result.add(parseExercise())
                }
            }
        }
    }
}