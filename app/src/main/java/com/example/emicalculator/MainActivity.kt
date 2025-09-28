package com.example.emicalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

//Main screen with 2 buttons that go to the EMI Calculator or to the Budget
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToEmi = findViewById<Button>(R.id.goToEmi)
        val goToBudget = findViewById<Button>(R.id.goToBudget)


        //navigation using intents
        goToEmi.setOnClickListener {
            val intent = Intent(this, EMIActivity::class.java)
            startActivity(intent)
        }

        goToBudget.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
        }

    }
}