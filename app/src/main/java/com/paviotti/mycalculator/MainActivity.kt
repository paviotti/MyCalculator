package com.paviotti.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.paviotti.mycalculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

    }

    //esta função é chamada direto
    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text) //append acrescenta números
        lastNumeric = true //se algum número for digitado pode usar o ponto
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = binding.tvInput.text.toString() //pega o conteúdo do visor em string
            var prefix = "" //declara uma string em branco
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1) //pega a string à partir do 1
                }

                //operação subtração
                //se a expressão tive o "-"
                if (tvValue.contains("-")) {
                    val splitValue =
                        tvValue.split("-") //splitValue é um array que recebe as partes de tvValue

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //se tiver prefixo, adiciona ao número antes de fazer a conta
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterdot((one.toDouble() - two.toDouble()).toString())

                    //operação multiplicação
                } else if (tvValue.contains("*")) {
                    val splitValue =
                        tvValue.split("*") //splitValue é um array que recebe as partes de tvValue

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //se tiver prefixo, adiciona ao número antes de fazer a conta
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterdot((one.toDouble() * two.toDouble()).toString())

                    //operação soma
                } else if (tvValue.contains("+")) {
                    val splitValue =
                        tvValue.split("+") //splitValue é um array que recebe as partes de tvValue

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //se tiver prefixo, adiciona ao número antes de fazer a conta
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterdot((one.toDouble() + two.toDouble()).toString())

                    //operação divisão
                } else if (tvValue.contains("/")) {
                    val splitValue =
                        tvValue.split("/") //splitValue é um array que recebe as partes de tvValue

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //se tiver prefixo, adiciona ao número antes de fazer a conta
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text =
                        removeZeroAfterdot((one.toDouble() / two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    //função que elimina os zeros a direita
    fun removeZeroAfterdot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    //se for verdeiro lastNumeric && não houver nenhum operador
    //pega cada operador digitado mas verifíca antes em isOperatorAdded
    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNumeric = false //reset na variavel
            lastDot = false //reset
        }
    }

    //se o primeiro número for negativo, ignora o sinal
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}