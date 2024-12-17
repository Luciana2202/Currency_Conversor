package com.example.currencyconverter

import SpinnerAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val currencies = listOf(
        Currency(R.drawable.eua, "USD", 1.0),  // Estados Unidos
        Currency(R.drawable.europe, "EUR", 0.95),  // Euro
        Currency(R.drawable.brasil, "BRL", 6.06),  // Brasil
        Currency(R.drawable.reino_unido, "GBP", 0.79),  // Reino Unido
        Currency(R.drawable.canada, "CAD", 1.43),  // Canadá
        Currency(R.drawable.australia, "AUD", 1.58),  // Austrália
        Currency(R.drawable.india, "INR", 84.91),  // Índia
        Currency(R.drawable.japan, "JPY", 153.96),  // Japão
        Currency(R.drawable.china, "CNY", 7.28),  // China
        Currency(R.drawable.suica, "CHF", 0.90),  // Suíça
        Currency(R.drawable.mexico, "MXN", 20.13),  // México
        Currency(R.drawable.russia, "RUB", 102.75),  // Rússia
        Currency(R.drawable.south_africa, "ZAR", 18.12),  // África do Sul
        Currency(R.drawable.singapore, "SGD", 1.35),  // Cingapura
        Currency(R.drawable.turquia, "TRY", 34.99),  // Turquia
        Currency(R.drawable.coreia_sul, "KRW", 1436.66),  // Coreia do Sul
        Currency(R.drawable.argentina, "ARS", 1020.96),  // Argentina
        Currency(R.drawable.suecia, "SEK", 9.5),  // Suécia
        Currency(R.drawable.nova_zelandia, "NZD", 1.74),  // Nova Zelândia
        Currency(R.drawable.dinamarca, "DKK", 7.11),  // Dinamarca
        Currency(R.drawable.israel, "ILS", 3.6),  // Israel
        Currency(R.drawable.noruega, "NOK", 11.21),  // Noruega
        Currency(R.drawable.bitcoin, "BTC", 0.0000094),  // Bitcoin (exemplo)
    )

    // Variáveis para armazenar as moedas selecionadas
    private var selectedInputCurrency: Currency? = null
    private var selectedOutputCurrency: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando o adapter para o Spinner de moedas de entrada e saída
        val adapter = SpinnerAdapter(this, currencies)
        binding.spInput.adapter = adapter
        binding.spOutput.adapter = adapter

        // Detectar alterações no Spinner de Moeda de Entrada
        binding.spInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedInputCurrency = currencies[position]
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Detectar alterações no Spinner de Moeda de Saída
        binding.spOutput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedOutputCurrency = currencies[position]
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

// Detectar alterações no EditText (valor de entrada)
        binding.edtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateConversion()
            }
        })

// Configurar o listener para o botão de swap (btn_converter)
        binding.btnConverter.setOnClickListener {
            swapCurrencies()
            updateConversion() // Após a troca, realiza a conversão e atualiza o resultado
        }

        //Limpar os campos
        binding.btnClear.setOnClickListener { clear() }
    }


    // Função para atualizar a conversão de moeda
    private fun updateConversion() {
        val inputText = binding.edtInput.text.toString()

        // Verifica se o valor no EditText é válido e se as moedas de entrada e saída estão selecionadas
        if (inputText.isNotEmpty() && selectedInputCurrency != null && selectedOutputCurrency != null) {
            val inputAmount = inputText.toDoubleOrNull()

            if (inputAmount != null) {
                // Realiza a conversão
                val inputCurrencyRate = selectedInputCurrency!!.value
                val outputCurrencyRate = selectedOutputCurrency!!.value

                // Calcula o valor convertido
                val convertedAmount = (inputAmount / inputCurrencyRate) * outputCurrencyRate

                // Exibe o resultado no tv_output
                binding.tvValorOutput.text = String.format("%.2f", convertedAmount)

                val formattedString = "1 ${selectedInputCurrency!!.currencyCode} = %.2f ${selectedOutputCurrency!!.currencyCode}"
                binding.tvCurrency.text = String.format(formattedString, outputCurrencyRate / inputCurrencyRate)
            }
        }
    }

    private fun clear() {
        binding.edtInput.text?.clear()
        binding.tvValorOutput.text = ""
        binding.spInput.setSelection(0)
        binding.spOutput.setSelection(0)
    }

    // Função para trocar as moedas selecionadas nos Spinners
    private fun swapCurrencies() {
        // Troca os itens selecionados nos Spinners
        val temp = selectedInputCurrency
        selectedInputCurrency = selectedOutputCurrency
        selectedOutputCurrency = temp

        // Atualiza as seleções dos Spinners
        binding.spInput.setSelection(currencies.indexOf(selectedInputCurrency))
        binding.spOutput.setSelection(currencies.indexOf(selectedOutputCurrency))
    }
}