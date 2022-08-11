package com.example.simplecalccomposeviewmodel


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import java.text.DecimalFormat


class MainViewModel: ViewModel()
{

    var input1 by mutableStateOf("1")
    var input2 by mutableStateOf("2")
    var result by mutableStateOf("")

    val radioOptions = listOf("+", "-", "*", "/")
    var selectedOption: String by mutableStateOf(radioOptions[0])

    fun validate(): Boolean {
        return validateNumberInput(input1) and validateNumberInput(input2)
    }

    fun calculate() {
        if (!validate()) {
            result = ""
            return
        }
        val number1 = input1.toDouble()
        val number2 = input2.toDouble()
        var x = 0.0
        when (selectedOption) {
            "+" ->  x = (number1 + number2)
            "*" ->  x = (number1 * number2)
            "-" ->  x = (number1 - number2)
            "/" ->  x = (number1 / number2)
        }
        result = DecimalFormat("#.###").format(x)
    }

    fun setSelection(option: String) {
        selectedOption = option
    }

    fun validateNumberInput(input: String): Boolean {
        try {
            input.toDouble()
            Log.i(">>>>>>>>", "No error $input")
            return true
        } catch (e: NumberFormatException) {
            Log.i(">>>>>>", "Input Error")
            return false
        }
    }

    val countDownTimer = object : CountDownTimer(1000, 1000) {
    override fun onTick(millisUntilFinished: Long) {

    }
    override fun onFinish() {

    }
}

}