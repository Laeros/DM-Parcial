package com.questionsnanswersgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AnswerFragment : Fragment() {

    private lateinit var correctAnswerText: TextView
    private lateinit var explanationText: TextView
    private lateinit var funFactText: TextView
    private lateinit var nextQuestionButton: Button

    companion object {
        private const val ARG_IS_CORRECT = "is_correct"
        private const val ARG_EXPLANATION = "explanation"
        private const val ARG_FUN_FACT = "fun_fact"

        fun newInstance(isCorrect: Boolean, explanation: String, funFact: String): AnswerFragment {
            val fragment = AnswerFragment()
            val args = Bundle().apply {
                putBoolean(ARG_IS_CORRECT, isCorrect)
                putString(ARG_EXPLANATION, explanation)
                putString(ARG_FUN_FACT, funFact)
            }
            fragment.arguments = args
            return fragment
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

        // Referencias a los elementos del layout
        correctAnswerText = view.findViewById(R.id.correct_answer_text)
        explanationText = view.findViewById(R.id.explanation_text)
        funFactText = view.findViewById(R.id.fun_fact_text)
        nextQuestionButton = view.findViewById(R.id.next_question_button) // Referencia al botón

        // Obtener argumentos del fragmento
        arguments?.let {
            val isCorrect = it.getBoolean(ARG_IS_CORRECT)
            val explanation = it.getString(ARG_EXPLANATION)
            val funFact = it.getString(ARG_FUN_FACT)

            // Mostrar el resultado
            correctAnswerText.text = if (isCorrect) "¡Correcto!" else "Incorrecto"
            explanationText.text = explanation
            funFactText.text = funFact
        }

        // Configurar el botón para continuar a la siguiente pregunta
        nextQuestionButton.setOnClickListener {
            // Volver al QuestionFragment o cargar la siguiente pregunta
            parentFragmentManager.popBackStack() // Regresar a la pregunta
        }
    }
}
