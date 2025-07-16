package com.example.cancionero.search

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.graphics.Color
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cancionero.R
import android.view.Gravity



class BusquedaFragment : DialogFragment() {

    private lateinit var editTextBusqueda: EditText
    private lateinit var radioGroupOpciones: RadioGroup
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ResultadosAdapter // Define este adapter luego

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_busqueda, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val params = window.attributes

            // Tamaño personalizado
            params.width = (resources.displayMetrics.widthPixels * 0.85).toInt() // 85% del ancho
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT

            // Posición arriba
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            params.y = 100 // separación desde el top en píxeles

            window.attributes = params
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editTextBusqueda = view.findViewById(R.id.editTextBusqueda)
        radioGroupOpciones = view.findViewById(R.id.radioGroupOpciones)
        recyclerView = view.findViewById(R.id.recyclerViewResultados)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ResultadosAdapter()
        recyclerView.adapter = adapter

        // Aquí agregarás la lógica para buscar mientras el usuario escribe

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTransparent)
    }



}
