package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.badge.BadgeState
import kotlin.math.pow

//Calculate the monthly EMI
class EMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emi)


        val loanInput = findViewById<EditText>(R.id.inputLoan)
        val rateInput = findViewById<EditText>(R.id.inputRate)
        val tenureInput = findViewById<EditText>(R.id.inputTenure)
        val calcBtn = findViewById<Button>(R.id.calculateBtn)
        val resultView = findViewById<TextView>(R.id.displayEmi)

        calcBtn.setOnClickListener{
            val principal
            val annualRate
            val months
            val monthlyRate = annualRate / 12 / 100
            val emi (if else logic?)
            resultView.text = "Monthly EMI = %.2f".format(emi)
        }
    }
}