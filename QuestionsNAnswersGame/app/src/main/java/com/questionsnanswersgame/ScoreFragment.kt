package com.questionsnanswersgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScoreFragment : Fragment() {

    private var score = 0
    private var totalQuestions = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        score = arguments?.getInt("score") ?: 0
        totalQuestions = arguments?.getInt("totalQuestions") ?: 0

        val scoreTextView: TextView = view.findViewById(R.id.score_text_view)
        scoreTextView.text = getString(R.string.score_message, score, totalQuestions)

        val restartButton: Button = view.findViewById(R.id.restart_button)
        restartButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuestionFragment())
                .commit()
        }
    }

    companion object {
        fun newInstance(score: Int, totalQuestions: Int) = ScoreFragment().apply {
            arguments = Bundle().apply {
                putInt("score", score)
                putInt("totalQuestions", totalQuestions)
            }
        }
    }
}
