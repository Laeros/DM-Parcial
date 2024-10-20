package com.questionsnanswersgame

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var timerTextView: TextView
    private var selectedOption: Int = -1
    private var countDownTimer: CountDownTimer? = null // Nullable para evitar errores de inicialización

    // Lista de preguntas
    private val questions = listOf(
        Question(
            question = "¿Cuál es el planeta más grande del sistema solar?",
            options = listOf("Marte", "Venus", "Tierra", "Júpiter"),
            correctAnswerIndex = 3,
            explanation = "La respuesta correcta es Júpiter.",
            funFact = "Júpiter es tan grande que cabrían más de 1,300 planetas del tamaño de la Tierra dentro de él."
        ),
        Question(
            question = "¿Quién pintó la Mona Lisa?",
            options = listOf("Miguel Ángel", "Van Gogh", "Leonardo da Vinci", "Picasso"),
            correctAnswerIndex = 2,
            explanation = "La respuesta correcta es Leonardo da Vinci.",
            funFact = "Se dice que Leonardo da Vinci tardó unos cuatro años en pintar la Mona Lisa y nunca se separó de ella."
        ),
        Question(
            question = "¿Cuál es el metal más ligero de todos?",
            options = listOf("Litio", "Plata", "Aluminio", "Oro"),
            correctAnswerIndex = 0,
            explanation = "La respuesta correcta es Litio.",
            funFact = "El litio es tan ligero que flota en el agua y se utiliza en baterías recargables."
        ),
        Question(
            question = "¿Cuál es el país más grande en términos de superficie?",
            options = listOf("Estados Unidos", "Canadá", "China", "Rusia"),
            correctAnswerIndex = 3,
            explanation = "La respuesta correcta es Rusia.",
            funFact = "Rusia es tan grande que cubre 11 zonas horarias diferentes."
        ),
        Question(
            question = "¿Cuál es el océano más profundo del mundo?",
            options = listOf("Atlántico", "Índico", "Pacífico", "Ártico"),
            correctAnswerIndex = 2,
            explanation = "La respuesta correcta es Pacífico.",
            funFact = "El punto más profundo del océano Pacífico, la Fosa de las Marianas, tiene una profundidad de 11 km, más que la altura del Monte Everest."
        ),
        Question(
            question = "¿En qué año llegó el hombre a la luna?",
            options = listOf("1962", "1969", "1972", "1965"),
            correctAnswerIndex = 1,
            explanation = "La respuesta correcta es 1969.",
            funFact = "Neil Armstrong fue el primer ser humano en caminar sobre la luna el 20 de julio de 1969."
        ),
        Question(
            question = "¿Cuál es el idioma más hablado en el mundo?",
            options = listOf("Español", "Inglés", "Mandarín", "Árabe"),
            correctAnswerIndex = 2,
            explanation = "La respuesta correcta es Mandarín.",
            funFact = "El mandarín tiene más de 900 millones de hablantes nativos, superando por mucho al inglés."
        ),
        Question(
            question = "¿Qué vitamina se obtiene principalmente de la exposición al sol?",
            options = listOf("Vitamina C", "Vitamina E", "Vitamina D", "Vitamina B12"),
            correctAnswerIndex = 2,
            explanation = "La respuesta correcta es Vitamina D.",
            funFact = "La vitamina D ayuda a absorber el calcio y es esencial para la salud de los huesos."
        ),
        Question(
            question = "¿Cuál es el animal terrestre más rápido?",
            options = listOf("León", "Guepardo", "Antílope", "Halcón"),
            correctAnswerIndex = 1,
            explanation = "La respuesta correcta es Guepardo.",
            funFact = "El guepardo puede alcanzar una velocidad de hasta 120 km/h en distancias cortas."
        ),
        Question(
            question = "¿Quién escribió 'Don Quijote de la Mancha'?",
            options = listOf("Gabriel García Márquez", "Miguel de Cervantes", "Federico García Lorca", "Jorge Luis Borges"),
            correctAnswerIndex = 1,
            explanation = "La respuesta correcta es Miguel de Cervantes.",
            funFact = "'Don Quijote de la Mancha' es considerada una de las primeras novelas modernas y fue publicada en dos partes, en 1605 y 1615."
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a los elementos del layout
        questionText = view.findViewById(R.id.question_text)
        optionsRadioGroup = view.findViewById(R.id.options_radio_group)
        submitButton = view.findViewById(R.id.submit_button)
        timerTextView = view.findViewById(R.id.timer_text_view)

        loadQuestion()

        // Habilitar el botón solo si una opción ha sido seleccionada
        optionsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedOption = checkedId
            submitButton.isEnabled = selectedOption != -1
        }

        // Lógica para enviar la respuesta
        submitButton.setOnClickListener {
            val selectedRadioButton = view.findViewById<RadioButton>(selectedOption)
            val selectedAnswerIndex = optionsRadioGroup.indexOfChild(selectedRadioButton)

            // Obtener la pregunta actual
            val currentQuestion = questions[currentQuestionIndex]

            // Lógica para evaluar la respuesta
            val isCorrect = selectedAnswerIndex == currentQuestion.correctAnswerIndex
            if (isCorrect) score++ // Incrementar la puntuación solo si es correcta

            // Pasar a AnswerFragment con los detalles de la respuesta
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    AnswerFragment.newInstance(isCorrect, currentQuestion.explanation, currentQuestion.funFact)
                )
                .addToBackStack(null)
                .commit()

            // Ir a la siguiente pregunta o finalizar si ya no hay más
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                loadQuestion()
            } else {
                // Finalizar juego y mostrar puntuación
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ScoreFragment.newInstance(score, questions.size))
                    .commit()
            }
        }
    }

    private fun loadQuestion() {
        val currentQuestion = questions[currentQuestionIndex]

        // Cargar texto de la pregunta
        questionText.text = currentQuestion.question

        // Limpiar opciones anteriores
        optionsRadioGroup.clearCheck()

        // Cargar opciones en el RadioGroup
        optionsRadioGroup.removeAllViews()
        currentQuestion.options.forEachIndexed { _, optionText ->
            val radioButton = RadioButton(context)
            radioButton.id = View.generateViewId()
            radioButton.text = optionText
            optionsRadioGroup.addView(radioButton)
        }

        // Reiniciar temporizador para la nueva pregunta
        countDownTimer?.cancel() // Cancelar el temporizador anterior si existe
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = getString(R.string.time_remaining, millisUntilFinished / 1000)
            }

            override fun onFinish() {
                submitButton.performClick() // Fuerza el envío de la respuesta al acabar el tiempo
            }
        }
        countDownTimer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel() // Cancelar el temporizador cuando la vista se destruye
    }
}
