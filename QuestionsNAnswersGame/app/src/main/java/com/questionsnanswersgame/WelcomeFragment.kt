package com.questionsnanswersgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el click listener
        val startButton: Button = view.findViewById(R.id.start_button)
        startButton.setOnClickListener {
            // Logica para navegar al QuestionFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuestionFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
