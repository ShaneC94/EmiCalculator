package com.example.emicalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//IncomeAdapter is a RecyclerView adapter that displays a list of incomes.
//These incomes are the extra incomes that the user may add, or swipe to delete.
class IncomeAdapter(private val incomes: MutableList<Income>) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    //ViewHolder represents a single income row
    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amountText: TextView = itemView.findViewById(R.id.incomeAmount)
    }

    //Inflate the income item layout and return the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_income, parent, false)
        return IncomeViewHolder(view)
    }

    //Bind the data to the TextView
    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomes[position]
        holder.amountText.text = holder.itemView.context.getString(R.string.income_amount, income.amount)
    }

    //Return the total number of incomes
    override fun getItemCount(): Int = incomes.size

    //Remove an item at the given position and update the RecyclerView
    fun removeItem(position: Int) {
        incomes.removeAt(position)
        notifyItemRemoved(position)
    }
}