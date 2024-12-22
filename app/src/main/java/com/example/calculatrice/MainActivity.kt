package com.example.calculatrice

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var result: EditText
    private var currentInput: String = ""
    private var previousInput: String = ""
    private var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        result = findViewById(R.id.result)

        val buttons = listOf(
            findViewById<Button>(R.id.clear),
            findViewById<Button>(R.id.remove),
            findViewById<Button>(R.id.parCent),
            findViewById<Button>(R.id.divise),
            findViewById<Button>(R.id.nbr7),
            findViewById<Button>(R.id.nbr8),
            findViewById<Button>(R.id.nbr9),
            findViewById<Button>(R.id.multiply),
            findViewById<Button>(R.id.nbr4),
            findViewById<Button>(R.id.nbr5),
            findViewById<Button>(R.id.nbr6),
            findViewById<Button>(R.id.minus),
            findViewById<Button>(R.id.nbr1),
            findViewById<Button>(R.id.nbr2),
            findViewById<Button>(R.id.nbr3),
            findViewById<Button>(R.id.plus),
            findViewById<Button>(R.id.rotation),
            findViewById<Button>(R.id.nbr0),
            findViewById<Button>(R.id.point),
            findViewById<Button>(R.id.equal)
        )

        // Set listeners for buttons
        buttons.forEach { button ->
            button.setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "C" -> clear()
            "=" -> calculateResult()
            "÷", "×", "-", "+", "%" -> setOperator(buttonText)
            "R" -> rotateInput()
            else -> updateInput(buttonText)
        }
    }

    private fun clear() {
        currentInput = ""
        previousInput = ""
        operator = ""
        display.text = "0"
        result.setText("")
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            previousInput = currentInput
            currentInput = ""
            operator = op
            display.text = "$previousInput $operator"
        }
    }

    private fun updateInput(input: String) {
        currentInput += input
        display.text = currentInput
        result.setText(currentInput)
    }

    private fun calculateResult() {
        if (previousInput.isNotEmpty() && currentInput.isNotEmpty()) {
            val num1 = previousInput.toDouble()
            val num2 = currentInput.toDouble()

            val resultValue = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "×" -> num1 * num2
                "÷" -> if (num2 != 0.0) num1 / num2 else {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                }
                "%" -> num1 % num2
                else -> 0.0
            }

            result.setText(resultValue.toString())
            display.text = "$previousInput $operator $currentInput ="
            previousInput = resultValue.toString()
            currentInput = ""
            operator = ""
        }
    }

    private fun rotateInput() {
        val rotatedInput = currentInput.reversed()
        currentInput = rotatedInput
        display.text = rotatedInput
    }



}