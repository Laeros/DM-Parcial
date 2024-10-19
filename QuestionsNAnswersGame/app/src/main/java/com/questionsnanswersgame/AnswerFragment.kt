package com.questionsnanswersgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AnswerFragment : Fragment() {

    private var isCorrect: Boolean = false
    private var explanation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isCorrect = it.getBoolean(ARG_IS_CORRECT)
            explanation = it.getString(ARG_EXPLANATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar si la respuesta fue correcta o incorrecta
        val resultTextView: TextView = view.findViewById(R.id.result_text_view)
        resultTextView.text = if (isCorrect) getString(R.string.correct_answer) else getString(R.string.incorrect_answer)

        // Mostrar la explicación de la respuesta correcta
        val explanationTextView: TextView = view.findViewById(R.id.explanation_text_view)
        explanationTextView.text = explanation

        // Botón para pasar a la siguiente pregunta
        val nextButton: Button = view.findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val ARG_IS_CORRECT = "is_correct"
        private const val ARG_EXPLANATION = "explanation"

        @JvmStatic
        fun newInstance(isCorrect: Boolean, explanation: String) =
            AnswerFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_CORRECT, isCorrect)
                    putString(ARG_EXPLANATION, explanation)
                }
            }
    }
}
