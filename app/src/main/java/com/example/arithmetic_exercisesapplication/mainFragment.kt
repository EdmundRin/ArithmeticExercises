package com.example.arithmetic_exercisesapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * A Fragment representing the main screen of the arithmetic exercises application.
 * Allows users to select difficulty level, operation, and number of questions.
 */
class mainFragment : Fragment() {

    private lateinit var difficultyRadioGroup: RadioGroup
    private lateinit var operationRadioGroup: RadioGroup
    private lateinit var numQuestionsEditText: EditText
    private lateinit var increaseButton: Button
    private lateinit var decreaseButton: Button

    private var maxOperand = 10 // Default to the max number of questions
    private var selectedDifficulty = "easy" // Default to the easy difficulty level
    private var selectedOperation = "addition" // Default operation
    private var numQuestions = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        difficultyRadioGroup = view.findViewById(R.id.difficultyRadioGroup)
        operationRadioGroup = view.findViewById(R.id.operationRadioGroup)
        numQuestionsEditText = view.findViewById(R.id.numQuestionsEditText)
        increaseButton = view.findViewById(R.id.increaseButton)
        decreaseButton = view.findViewById(R.id.decreaseButton)

        // Set listeners for the difficulty and operation RadioGroups
        difficultyRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.easyRadioButton -> {
                    maxOperand = 10
                    selectedDifficulty = "easy"
                }

                R.id.mediumRadioButton -> {
                    maxOperand = 25
                    selectedDifficulty = "medium"
                }
                R.id.hardRadioButton -> {
                    maxOperand = 50
                    selectedDifficulty = "hard"
                }

            }
        }
        difficultyRadioGroup.check(R.id.easyRadioButton)

        operationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.additionRadioButton -> selectedOperation = "addition"
                R.id.multiplicationRadioButton -> selectedOperation = "multiplication"
                R.id.divisionRadioButton -> selectedOperation = "division"
                R.id.subtractionRadioButton -> selectedOperation = "subtraction"
            }
        }
        operationRadioGroup.check(R.id.additionRadioButton)

        // Set listeners for increase and decrease buttons
        increaseButton.setOnClickListener {
            numQuestions += 1
            numQuestionsEditText.setText(numQuestions.toString())

        }

        decreaseButton.setOnClickListener {
            val currentNumQuestions = numQuestionsEditText.text.toString().toInt()
            if (currentNumQuestions == 1) {
                Toast.makeText(requireContext(), "Number of questions must be greater than 0.", Toast.LENGTH_SHORT).show()
            }else {
                numQuestions -= 1
                numQuestionsEditText.setText(numQuestions.toString())
            }
        }

        val startButton = view.findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            numQuestions = numQuestionsEditText.text.toString().toInt()
            if (numQuestions == 0) {
                Toast.makeText(requireContext(), "Number of questions must be greater than 0.", Toast.LENGTH_SHORT).show()}
            else{
                val action = mainFragmentDirections.actionMainFragmentToExerciseFragment(selectedDifficulty, maxOperand, selectedOperation, numQuestions)
                view.findNavController().navigate(action)
            }
        }
        return view
    }
}
