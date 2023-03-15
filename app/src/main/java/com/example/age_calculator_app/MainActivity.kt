package com.example.age_calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val month_spinner: Spinner = findViewById(R.id.Month_spinner)
        val day_spinner: Spinner = findViewById(R.id.Day_spinner)
        val year_spinner: Spinner = findViewById(R.id.Year_spinner)
        val calc_Button: Button = findViewById(R.id.Calculate_button)
        val result_textView: TextView = findViewById(R.id.Result_textView)


        val current_date = LocalDate.now()
        var month: Int = 0
        var day: Int = 0
        var year: Int = 0

        var no_of_days_inMonth: Int = 0
        var day_options: Array<String>
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
                if (year != 0 && month != 0) {
                    day_options = fill_daysList(no_of_days_inMonth)
                    day_spinner.adapter = ArrayAdapter<String>(
                        thisRef,
                        android.R.layout.simple_list_item_1,
                        day_options
                    )
                    day_spinner.isVisible = true;
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
                    year = 1899

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
                    day_spinner.isVisible = true;
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }




        day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                day = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        calc_Button.setOnClickListener {
            if (month != 0 && year != 0 && day != 0) {
                var diff_in_years: Int = current_year.toInt() - year.toInt()
                var diff_in_months: Int =current_date.monthValue - month.toInt()
                var diff_in_days: Int = current_date.dayOfMonth - day.toInt()

                if (diff_in_months < 0 || diff_in_days < 0) {
                    if (diff_in_months < 0) {
                        diff_in_months = 12 - diff_in_months * (-1)
                    }
                    if (diff_in_days < 0) {
                        diff_in_days = 30 - diff_in_days * (-1)
                    }
                    diff_in_years = diff_in_years - 1
                }
                /*else if(diff_in_months > 0){

                }*/
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

                result_textView.text = resultText;


                //result_textView.text = "The month: ${diff_in_months.toString()} \n day is ${diff_in_days.toString()} \n year is ${diff_in_years.toString()}"
            }
        }
    }

    private fun fill_daysList(no_of_days:Int):Array<String>{
        val list = arrayOfNulls<String>(no_of_days + 1)
        var day = 0
        for(i in list.indices){
            if(day == 0){
                list[i] ="Select a day"
                day++
                continue
            }
            list[i] = day.toString()
            day++
        }
        return list.requireNoNulls()
    }

}