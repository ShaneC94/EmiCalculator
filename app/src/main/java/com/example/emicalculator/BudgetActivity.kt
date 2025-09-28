package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//BudgetActivity allows user to input their income, expenses, EMI,
//and any extra expenses they may have.
//It calculates their monthly savings/deficit and allows the user to
//enter a list of extra expenses and incomes that can be managed independently
//and interactively.
class BudgetActivity : AppCompatActivity() {

    private val extraExpenses = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    private val extraIncomes = mutableListOf<Income>()
    private lateinit var incomeAdapter: IncomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        //UI elements for user input and output
        val incomeInput = findViewById<EditText>(R.id.inputIncome)
        val expenseInput = findViewById<EditText>(R.id.inputExpenses)
        val emiInput = findViewById<EditText>(R.id.inputEmi)
        val extraExpenseInput = findViewById<EditText>(R.id.inputExtraExpenses)
        val extraIncomeInput = findViewById<EditText>(R.id.inputExtraIncomes)

        val addExpsBtn = findViewById<Button>(R.id.addExpsButton)
        val totalExtraView = findViewById<TextView>(R.id.viewExtraTotal)
        val addIncBtn = findViewById<Button>(R.id.addIncButton)
        val totalIncomeView = findViewById<TextView>(R.id.viewIncomeTotal)
        val balanceBtn = findViewById<Button>(R.id.checkBalance)
        val balanceView = findViewById<TextView>(R.id.viewBalance)
        val backBtn = findViewById<Button>(R.id.backButton)

        totalExtraView.text = getString(R.string.extra_total, 0.00)
        totalIncomeView.text = getString(R.string.extra_income_total, 0.00)

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

        //RecyclerView setup for the extra incomes
        val incomeRecyclerView = findViewById<RecyclerView>(R.id.recyclerIncomes)
        incomeAdapter = IncomeAdapter(extraIncomes)
        incomeRecyclerView.layoutManager = LinearLayoutManager(this)
        incomeRecyclerView.adapter = incomeAdapter

        //Swipe to delete feature for removing extra incomes
        val incomeItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val removedAmount = extraIncomes[position].amount
                incomeAdapter.removeItem(position)

                //Updates the total after removing an income
                val totalIncome = extraIncomes.sumOf { it.amount }
                totalIncomeView.text = getString(R.string.extra_income_total, totalIncome)

                Toast.makeText(this@BudgetActivity, getString(R.string.removed_income, removedAmount), Toast.LENGTH_SHORT).show()
            }
        })
        incomeItemTouchHelper.attachToRecyclerView(incomeRecyclerView)

        //Adds an extra income to the list
        addIncBtn.setOnClickListener {
            val value = extraIncomeInput.text.toString().toDoubleOrNull()
            if (value != null) {
                extraIncomes.add(Income(value))
                incomeAdapter.notifyItemInserted(extraIncomes.size - 1)

                val totalIncome = extraIncomes.sumOf { it.amount }
                totalIncomeView.text = getString(R.string.extra_income_total, totalIncome)

                extraIncomeInput.text.clear()
            } else {
                Toast.makeText(this, getString(R.string.invalid_income), Toast.LENGTH_SHORT).show()
            }
        }

        //Calculates savings/deficit based on input
        balanceBtn.setOnClickListener {
            val income = incomeInput.text.toString().toDoubleOrNull() ?: 0.0
            val expenses = expenseInput.text.toString().toDoubleOrNull() ?: 0.0
            val emi = emiInput.text.toString().toDoubleOrNull() ?: 0.0
            val totalExtra = extraExpenses.sumOf { it.amount }
            val totalIncome = extraIncomes.sumOf { it.amount }

            val balance = (income + totalIncome) - (expenses + totalExtra + emi)

            //Displays savings/deficit with styled backgrounds & text colors for clarity
            if (balance >= 0) {
                balanceView.text = getString(R.string.savings, balance)
                balanceView.setBackgroundResource(R.drawable.balance_background_savings)
                balanceView.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                balanceView.text = getString(R.string.deficit, balance)
                balanceView.setBackgroundResource(R.drawable.balance_background_deficit)
                balanceView.setTextColor(ContextCompat.getColor(this, R.color.very_dark_red))
            }
        }
        backBtn.setOnClickListener {
            finish()
        }

    }
}