package com.example.age_calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val month_spinner: Spinner = findViewById(R.id.Month_spinner)
        val day_spinner: Spinner = findViewById(R.id.day_spinner)
        val year_spinner: Spinner = findViewById(R.id.Year_spinner)
        val calc_Button: Button = findViewById(R.id.Calculate_button)
        val result_textView:TextView = findViewById(R.id.result_textView)

        val current_date = LocalDate.now()
        var month: Int = 0
        var day: Int = 0
        var year: Int = 0

        var no_of_days_inMonth: Int = 0
        var day_options: Array<String>
        day_options = arrayOf("Select a month")
        day_spinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            day_options
        )

        var thisRef = this

        val month_options = arrayOf(
            "Select a month",
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )
        month_spinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, month_options)

        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                month = p2

                no_of_days_inMonth = when (month) {
                    1 -> 31
                    3 -> 31
                    4 -> 30
                    5 -> 31
                    6 -> 30
                    7 -> 31
                    8 -> 31
                    9 -> 30
                    10 -> 31
                    11 -> 30
                    12 -> 31
                    else -> 0
                }

                if (year != 0 || month != 2){
                    day_options = fill_daysList(no_of_days_inMonth)
                    day_spinner.adapter = ArrayAdapter<String>(
                        thisRef,
                        android.R.layout.simple_list_item_1,
                        day_options
                    )

                }
                if(month == 2 && year == 0){
                    result_textView.text = "Please choose a year!"
                }
                else{
                    if(day == 0){
                        result_textView.text = "Please choose a day"
                    }
                    if(year == 0){
                        if(day != 0)
                            result_textView.text = result_textView.text.toString() + " Please choose a year"
                        else
                            result_textView.text = result_textView.text.toString() + ", and a year"
                    }
                    result_textView.text = result_textView.text.toString() + "!"
                }
                if(month != 0 && day != 0 && year != 0){
                    result_textView.text = "Your birth date is: ${month}/${day}/${year}"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        var formatter = DateTimeFormatter.ofPattern("yyyy")
        val current_year = current_date.format(formatter).toInt()
        val size: Int = current_year - 1900 + 1
        val year_options = arrayOfNulls<String>(size + 1)

        var year_iterator = 1899
        for (i in year_options.indices) {
            if (year_iterator == 1899) {
                year_options[i] = "Select a year"
                year_iterator++
                continue
            }
            year_options[i] = year_iterator.toString()
            year_iterator++
        }
        year_spinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, year_options)

        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0)
                    year = year_options.get(p2).toString().toInt()
                else
                    year = 0

                if (month == 2) {
                    if (year % 4 == 0) {
                        no_of_days_inMonth = 29
                    } else {
                        no_of_days_inMonth = 28
                    }
                    day_options = fill_daysList(no_of_days_inMonth)
                    day_spinner.adapter = ArrayAdapter<String>(
                        thisRef,
                        android.R.layout.simple_list_item_1,
                        day_options
                    )

                }
                if(month == 0){
                    result_textView.text = "Please choose a month!"
                }
                else if(day == 0){
                    result_textView.text = "Please choose a day"
                    if(year == 0)
                        result_textView.text = result_textView.text.toString() + ", and a year "
                    result_textView.text = result_textView.text.toString() + "!"
                }

                if(month != 0 && day != 0 && year != 0){
                    result_textView.text = "Your birth date is: ${month}/${day}/${year}"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }




        day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                day = p2

                if(day != 0 && year == 0){
                    result_textView.text = "Please choose a year!"
                }
                if(month != 0 && day != 0 && year != 0){
                    result_textView.text = "Your birth date is: ${month}/${day}/${year}"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        calc_Button.setOnClickListener {
            result_textView.text= if(day ==0) "Please enter a day" else if(month == 0) "Please enter a month" else "Please enter a year"
            if (month != 0 && year != 0 && day != 0) {
                var diff_in_years: Int = current_year.toInt() - year.toInt()
                var diff_in_months: Int =current_date.monthValue - month.toInt()
                var diff_in_days: Int = current_date.dayOfMonth - day.toInt()

                if (diff_in_months < 0 || diff_in_days < 0) {
                    if (diff_in_months < 0) {
                        diff_in_months = 12 - diff_in_months * (-1)
                        diff_in_years = diff_in_years - 1
                    }
                    if (diff_in_days < 0) {
                        diff_in_days = no_of_days_inMonth - diff_in_days * (-1)
                        diff_in_months = diff_in_months - 1
                    }

                }

                var resultText: String = ""
                if (diff_in_years != 0) {
                    resultText = resultText + "Your age is ${diff_in_years} year"
                    if (diff_in_years > 1)
                        resultText = resultText + "s"
                    resultText = resultText + ", "
                }
                else{
                    resultText = resultText + "Your age is "
                }
                resultText = resultText + " ${diff_in_months} month"
                if (diff_in_months > 1)
                    resultText = resultText + "s"
                resultText = resultText + ", "
                resultText = resultText + " ${diff_in_days} day"
                if (diff_in_days > 1)
                    resultText = resultText + "s"
                resultText = resultText + "."

                resultText += "\nYour age in months is ${diff_in_years * 12 + diff_in_months} month"
                if(diff_in_years*12 + diff_in_months > 1){
                    resultText+= "s"
                }
                resultText+=".\nYour age in days is ${diff_in_years*365 + diff_in_months*30} day"
                if(diff_in_years*365 + diff_in_months*30 > 1){
                    resultText+= "s"
                }
                resultText+=".\nYour age in hours is ${diff_in_years*365*24 + diff_in_months*30*24 + diff_in_days*24} hours"
                resultText+=".\nYour age in minutes is ${diff_in_years*365*24*60 + diff_in_months*30*24*60 + diff_in_days*24*60} minutes"
                resultText+=".\nYour age in seconds is ${diff_in_years*365*24*60*60 + diff_in_months*30*24*60*60 + diff_in_days*24*60*60} seconds."

                result_textView.text = resultText;

            }
        }
    }

    private fun fill_daysList(no_of_days:Int):Array<String>{
        val list = arrayOfNulls<String>(no_of_days + 1)
        var dayVal = 0
        for(i in list.indices){
            if(dayVal == 0){
                list[i] ="Select a day"
                dayVal++
                continue
            }
            list[i] = dayVal.toString()
            dayVal++
        }
        return list.requireNoNulls()
    }

}