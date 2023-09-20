package com.example.arithmetic_exercisesapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Fragment for handling arithmetic exercise questions.
 * It generates questions based on the selected difficulty, operation, and number of questions.
 * Also, it tracks and displays the user's score.
 */
class exerciseFragment : Fragment() {
    private lateinit var questionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var doneButton: Button

    private var maxOperand: Int = 10 // Default max operand value
    private var selectedOperation: String = "addition" // Default operation
    private var numQuestions: Int = 0
    private var selectedDifficulty = "easy"
    private var currentAnswer: String = ""
    private var currentQuestion: Int = 0
    private var correctAnswers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)
        // Get arguments from SafeArgs
        val args = exerciseFragmentArgs.fromBundle(requireArguments())
        maxOperand = args.maxOperand
        selectedOperation = args.selectedOperation
        numQuestions = args.numQuestions
        selectedDifficulty = args.selectedDifficulty

        questionTextView = view.findViewById(R.id.questionTextView)
        answerEditText = view.findViewById(R.id.answerEditText)
        doneButton = view.findViewById(R.id.doneButton)

        // Initialize the exercise
        updateQuestion()

        // Set listener for the "DONE" button
        doneButton.setOnClickListener {
            checkAnswer()
            if (currentQuestion < numQuestions) {
                updateQuestion()
            } else {
                val accuracy = (correctAnswers.toDouble() / numQuestions.toDouble()) * 100
                val formattedAccuracy = String.format("%.2f", accuracy)
                val resultText = "You answered $correctAnswers out of $numQuestions correctly.\nAccuracy: $formattedAccuracy%"
                val action = exerciseFragmentDirections.actionExerciseFragmentToResultFragment(selectedDifficulty,resultText)
                // Navigate to the next screen
                view.findNavController().navigate(action)
            }
        }

        return view
    }
    /**
     * Initializes the exercise and sets up the first question.
     */
    private fun updateQuestion() {
        currentQuestion++
        questionTextView.text = generateQuestion()
        answerEditText.text.clear()
    }

    /**
     * Generates a random arithmetic question based on the selected operation and operands range.
     *
     * @return The generated arithmetic question as a formatted string.
     */
    private fun generateQuestion(): String {
        val operand1 = (0..maxOperand).random()
        val operand2 = (1..maxOperand).random()
        val operator = when (selectedOperation) {
            "addition" -> "+"
            "subtraction" -> "-"
            "multiplication" -> "×"
            "division" -> "÷"
            else -> ""
        }

        currentAnswer = when (selectedOperation) {
            "addition" -> (operand1 + operand2).toString()
            "subtraction" -> (operand1 - operand2).toString()
            "multiplication" -> (operand1 * operand2).toString()
            "division" -> {
                // Use floating-point division for more precise results
                val result = operand1.toDouble() / operand2.toDouble()
                String.format("%.2f", result) // Format the result to 2 decimal places
            }
            else -> ""
        }

        return "$operand1 $operator $operand2"
    }

    /**
     * Checks the user's answer against the expected answer and updates the score.
     */
    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString()
        if (userAnswer == currentAnswer) {
            correctAnswers++
        }
    }
}