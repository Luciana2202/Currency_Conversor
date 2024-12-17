import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.currencyconverter.Currency
import com.example.currencyconverter.R

class SpinnerAdapter(
    context: Context,
    private val currencies: List<Currency>
) : ArrayAdapter<Currency>(context, R.layout.spinner_item, currencies) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflando a view do item do Spinner
        val view: View = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)

        // Obtendo a moeda para a posição atual
        val currency = getItem(position)

        // Configurando as views
        val imageView: ImageView = view.findViewById(R.id.flag_image)
        val textView: TextView = view.findViewById(R.id.currency_name)

        // Verificando se a moeda existe e configurando a imagem e o nome da moeda
        if (currency != null) {
            imageView.setImageResource(currency.flagResource) // A bandeira
            textView.text = currency.currencyCode // A sigla da moeda
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Retornando a mesma view para o dropdown
        return getView(position, convertView, parent)
    }
}
