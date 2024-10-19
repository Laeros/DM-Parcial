package com.questionsnanswersgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment() {

    private var currentQuestionIndex: Int = 0 // Índice de la pregunta actual
    private var selectedOptionIndex: Int = -1
    private lateinit var questions: List<Question> // Lista de preguntas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las preguntas. Puedes cambiar estas preguntas con tu propia lógica.
        questions = listOf(
            Question(
                text = "¿Cuál es la capital de Francia?",
                options = listOf("Berlin", "Madrid", "Paris", "Roma"),
                correctAnswerIndex = 2
            ),
            Question(
                text = "¿Qué planeta es conocido como el planeta rojo?",
                options = listOf("Tierra", "Marte", "Jupiter", "Venus"),
                correctAnswerIndex = 1
            ),
            Question(
                text = "¿Quién escribió 'Hamlet'?",
                options = listOf("Mark Twain", "William Shakespeare", "Charles Dickens", "J.K. Rowling"),
                correctAnswerIndex = 1
            )
        )

        // Cargar la primera pregunta
        loadQuestion(view)

        // Configurar el botón de envío
        val submitButton: Button = view.findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            if (selectedOptionIndex != -1) {
                val isCorrect = selectedOptionIndex == questions[currentQuestionIndex].correctAnswerIndex
                val explanation = questions[currentQuestionIndex].explanation()

                // Navegar al AnswerFragment con los datos de la respuesta
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AnswerFragment.newInstance(isCorrect, explanation))
                    .addToBackStack(null)
                    .commit()

                // Pasar a la siguiente pregunta si es la última
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    // Aquí, solo reseteamos el índice para un bucle infinito de preguntas
                    currentQuestionIndex = 0
                }

                // Recargar la siguiente pregunta
                loadQuestion(view)
            }
        }
    }

    // Función para cargar y mostrar una pregunta
    private fun loadQuestion(view: View) {
        val questionTextView: TextView = view.findViewById(R.id.question_text)
        val optionsRadioGroup: RadioGroup = view.findViewById(R.id.options_radio_group)

        // Limpiar el RadioGroup antes de agregar nuevas opciones
        optionsRadioGroup.removeAllViews()

        // Mostrar la pregunta actual
        val currentQuestion = questions[currentQuestionIndex]
        questionTextView.text = currentQuestion.text

        // Añadir las opciones al RadioGroup
        currentQuestion.options.forEachIndexed { index, optionText ->
            val radioButton = RadioButton(context)
            radioButton.id = View.generateViewId()
            radioButton.text = optionText
            optionsRadioGroup.addView(radioButton)

            // Detectar la opción seleccionada
            optionsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
                selectedOptionIndex = group.indexOfChild(selectedRadioButton)
            }
        }
    }
}
