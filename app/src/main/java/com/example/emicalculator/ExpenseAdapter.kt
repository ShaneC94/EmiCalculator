package com.example.emicalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//ExpenseAdapter is a RecyclerView adapter that displays a list of expenses.
//These expenses are the extra expenses that the user may add, or swipe to delete.
class ExpenseAdapter(private val expenses: MutableList<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    //ViewHolder represents a single expense row
    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountText: TextView = itemView.findViewById(R.id.expenseAmount)
    }

    //Inflate the expense item layout and return the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    //Bind the data to the TextView
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.amountText.text = holder.itemView.context.getString(R.string.expense_amount, expense.amount)
    }

    //Return the total number of expenses
    override fun getItemCount(): Int = expenses.size

    //Remove an item at the given position and update the RecyclerView
    fun removeItem(position: Int) {
        expenses.removeAt(position)
        notifyItemRemoved(position)
    }
}