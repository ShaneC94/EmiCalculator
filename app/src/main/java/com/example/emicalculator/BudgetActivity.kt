package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

//Check your savings/deficit based on income, expenses, and EMI
class BudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        //include everything in the emi/requirements
        val incomeInput = findViewById<EditText>(R.id.inputIncome)
        val expenseInput = findViewById<EditText>(R.id.inputExpenses)
        val emiInput = findViewById<EditText>(R.id.inputEmi)
        val balanceBtn = findViewById<Button>(R.id.checkBalance)
        val balanceView = findViewById<TextView>(R.id.viewBalance)
        val backBtn = findViewById<Button>(R.id.backButton)

        balanceBtn.setOnClickListener {
            val income = incomeInput.text.toString().toDoubleOrNull() ?: 0.0
            val expenses = expenseInput.text.toString().toDoubleOrNull() ?: 0.0
            val emi = emiInput.text.toString().toDoubleOrNull() ?: 0.0

            val balance = income - (expenses + emi)

            if (balance >= 0) {
                balanceView.text = "Savings: %.2f".format(balance)
            } else {
                balanceView.text = "Deficit: %.2f".format(balance)
            }
        }
        backBtn.setOnClickListener {
            finish()
        }

    }
}