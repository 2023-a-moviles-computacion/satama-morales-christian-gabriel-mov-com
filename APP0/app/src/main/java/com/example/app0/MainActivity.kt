package com.example.app0

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    val botonCicloVida = findViewById<Button>(R.id.btn_cicloVida)
    botonCicloVida.setOnClickListener{
        irActividad(AACicloVida::class.java)
    }

    val botonListView = findViewById<Button>(R.id.btn__ir_list_view)
    botonListView.setOnClickListener{
        irActividad(BListView::class.java)
    }

    }
    fun irActividad(
    clase: Class<*>
    ){
      val intent = Intent(this, clase)
      startActivity(intent)
    }
}