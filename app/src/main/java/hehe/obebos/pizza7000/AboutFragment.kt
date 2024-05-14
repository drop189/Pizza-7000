package hehe.obebos.pizza7000

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false) // Отсылает к XML
    }

//    fun onClickGoMain(view: View) {
//        val intent = Intent(activity, MainActivity2::class.java)
//        startActivity(intent)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = requireView().findViewById(R.id.button)
        button.setOnClickListener {
            // Создаем объект Intent для вызова активити MainActivity2
            val intent = Intent(activity, MainActivity2::class.java)

            // Если нам нужно передать какие-либо данные в активити, используйте putExtra
            intent.putExtra("key", "value")

            // Запускаем активити с помощью метода startActivity, передавая intent
            startActivity(intent)
        }
    }
}