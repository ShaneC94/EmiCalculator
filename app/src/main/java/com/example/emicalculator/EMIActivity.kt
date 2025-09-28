package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

//EMIActivity allows users to calculate their monthly mortgage payment
//based on loan amount, interest rate, and tenure (years and/or months)
class EMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emi)

        //UI elements for user input
        val loanInput = findViewById<EditText>(R.id.inputLoan)
        val rateInput = findViewById<EditText>(R.id.inputRate)
        val monthsInput = findViewById<EditText>(R.id.inputMonths)
        val yearsInput = findViewById<EditText>(R.id.inputYears)
        val calcBtn = findViewById<Button>(R.id.calculateBtn)
        val resultView = findViewById<TextView>(R.id.displayEmi)
        val backBtn = findViewById<Button>(R.id.backButton)

        //Calculate EMI when the button is clicked
        calcBtn.setOnClickListener {
            val principal = loanInput.text.toString().toDoubleOrNull() ?: 0.0
            val annualRate = rateInput.text.toString().toDoubleOrNull() ?: 0.0
            val months = monthsInput.text.toString().toIntOrNull() ?: 0
            val years = yearsInput.text.toString().toIntOrNull() ?: 0
            val totalMonths = (years * 12) + months

            //Semiannual compounding rule
            //Converts annual interest rate into a monthly rate
            // This brings the app inline with banks (TD, Scotia)
            val semiAnnualRate = annualRate / 2 / 100
            val monthlyRate = (1 + semiAnnualRate).pow(2.0 / 12.0) -1

            //Logic for EMI formula
            val emi = if (totalMonths > 0 && monthlyRate > 0) {
                (principal * monthlyRate * (1 + monthlyRate).pow(totalMonths)) /
                        ((1 + monthlyRate).pow(totalMonths) - 1)
            } else {
                0.0
            }
            //Display the result formatted to 2 decimal places
            resultView.text = getString(R.string.emi_result, emi)
        }
        backBtn.setOnClickListener {
            finish()
        }
    }
}