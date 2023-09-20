package com.example.arithmetic_exercisesapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Fragment for displaying the result of the arithmetic exercise.
 * It shows the user's score, the selected difficulty, and provides an option to start a new exercise.
 */
class resultFragment : Fragment() {
    private lateinit var difficultyTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var tryAgainButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        difficultyTextView = view.findViewById(R.id.difficultyTextView)
        scoreTextView = view.findViewById(R.id.scoreTextView)
        tryAgainButton = view.findViewById(R.id.tryAgainButton)

        // Get the result text and difficulty from arguments
        val args = resultFragmentArgs.fromBundle(requireArguments())
        val resultText = args.resultText
        val difficulty = args.selectedDifficulty

        // Display the result text and difficulty
        difficultyTextView.setText("Difficulty your choice: $difficulty")
        scoreTextView.setText(resultText)

        // Set listener for the "Do it Again" button
        tryAgainButton.setOnClickListener {
            // Navigate back to the start screen (mainFragment)
            view.findNavController().navigate(R.id.action_resultFragment_to_MainFragment)
        }

        return view
    }
}