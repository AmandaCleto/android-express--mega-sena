package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

/* Inherits AppCompatActivity to turn this MainActivity as one Activity */
/* This MainActivity is the first activity of the app, because it is set on AndroidManifest.xml,
   [you can change it]. */
class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        //SAVE sharedPreferences in sharedPref
        sharedPref = getSharedPreferences("bd", Context.MODE_PRIVATE)

        //GET if there is any saved data or not [null]
        val result = sharedPref.getString("result", "Nenhum regristro salvo")


        //ANOTHER way of doing the above line, is using let
//        if(result != null) {
//            txtResult.text = "Última aposta= $result"
//        }

//        like this:
        result?.let { txtResult.text = "Última aposta= $result" }

        //if getString had a default value when no data is saved inside the variable result,
        //we could only set when there is a data saved
//        txtResult.text = "Última aposta= $result"



        //How to listen events
        //option 1: by using XML - add onClick at the button that is filled with the name of a function
        //option 2: variable needs to be of type "View.OnClickListener (interface)"; to do that, create
        //          an object variable that implements View.OnClickListener
//        btnGenerate.setOnClickListener(buttonClickedListener)

        //option 3: easiest one, create a block of code that will be shot by setOnClickListener
        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            txtResult.text = ""
            return
        }

        val amount = text.toInt()
        if (amount < 6 || amount > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            txtResult.text = ""
            return
        }


        var arrangeOfNumbers = mutableSetOf<Int>();
        while (true) {
            if (arrangeOfNumbers.size == amount) {
                break
            } else {
                val random = Random.nextInt(60) //0..59
                arrangeOfNumbers.add(random + 1) //if 0 -> 1 | if 59 -> 60
                continue
            }
        }
        txtResult.text = arrangeOfNumbers.joinToString(" - ")
        val edit = sharedPref.edit()
        edit.putString("result", txtResult.text.toString())
        edit.apply()

        /*
            we could write the same code above as:
            sharedPref.edit().apply {
               putString("result", txtResult.text.toString())
               apply()
            }
        */

        /*
            commit = sync -> saves synchronously and returns true | false
            apply = async -> saves asynchronously and returns nothing
        */
    }


    //option 2:
    // version Lambda
//    val buttonClickedListener = View.OnClickListener { Log.i("Botão", "clicou nele opção 2") }

    // version larger of the above Lambda
//    val buttonClickedListener = object : View.OnClickListener {
//        //sdk android calls this onClick after the button is clicked
//        override fun onClick(v: View?) {
//            Log.i("Botão", "clicou nele opção 2")
//        }
//    }

    //option 1: Function listened by the button
//    fun generateNumbers(view: View) {
//        Log.i("Botão", "clicou nele opção 1")
//    }

}