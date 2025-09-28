package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//BudgetActivity allows user to input their income, expenses, EMI,
//and any extra expenses they may have.
//It calculates their monthly savings/deficit and allows the user to
//enter a list of extra expenses that can be managed independently
//and interactively.
class BudgetActivity : AppCompatActivity() {

    private val extraExpenses = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        //UI elements for user input and output
        val incomeInput = findViewById<EditText>(R.id.inputIncome)
        val expenseInput = findViewById<EditText>(R.id.inputExpenses)
        val emiInput = findViewById<EditText>(R.id.inputEmi)
        val extraExpenseInput = findViewById<EditText>(R.id.inputExtraExpenses)

        val addExpsBtn = findViewById<Button>(R.id.addExpsButton)
        val totalExtraView = findViewById<TextView>(R.id.viewExtraTotal)
        val balanceBtn = findViewById<Button>(R.id.checkBalance)
        val balanceView = findViewById<TextView>(R.id.viewBalance)
        val backBtn = findViewById<Button>(R.id.backButton)

        totalExtraView.text = getString(R.string.extra_total, 0.00)

        //RecyclerView setup for the extra expenses
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerExpenses)
        adapter = ExpenseAdapter(extraExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Swipe to delete feature for removing extra expenses
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val removedAmount = extraExpenses[position].amount
                adapter.removeItem(position)

                //Updates the total after removing an expense
                val totalExtra = extraExpenses.sumOf { it.amount }
                totalExtraView.text = getString(R.string.extra_total, totalExtra)

                Toast.makeText(this@BudgetActivity, getString(R.string.removed_expense, removedAmount), Toast.LENGTH_SHORT).show()
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Adds an extra expense to the list
        addExpsBtn.setOnClickListener {
            val value = extraExpenseInput.text.toString().toDoubleOrNull()
            if (value != null) {
                extraExpenses.add(Expense(value))
                adapter.notifyItemInserted(extraExpenses.size - 1)

                val totalExtra = extraExpenses.sumOf { it.amount }
                totalExtraView.text = getString(R.string.extra_total, totalExtra)

                extraExpenseInput.text.clear()
            } else {
                Toast.makeText(this, getString(R.string.invalid_expense), Toast.LENGTH_SHORT).show()
            }
        }

        //Calculates savings/deficit based on input
        balanceBtn.setOnClickListener {
            val income = incomeInput.text.toString().toDoubleOrNull() ?: 0.0
            val expenses = expenseInput.text.toString().toDoubleOrNull() ?: 0.0
            val emi = emiInput.text.toString().toDoubleOrNull() ?: 0.0
            val totalExtra = extraExpenses.sumOf { it.amount }

            val balance = income - (expenses + totalExtra + emi)

            //Displays savings or deficit with styled backgrounds for clarity
            if (balance >= 0) {
                balanceView.text = getString(R.string.savings, balance)
                balanceView.setBackgroundResource(R.drawable.balance_background_savings)
            } else {
                balanceView.text = getString(R.string.deficit, balance)
                balanceView.setBackgroundResource(R.drawable.balance_background_deficit)
            }
        }
        backBtn.setOnClickListener {
            finish()
        }

    }
}