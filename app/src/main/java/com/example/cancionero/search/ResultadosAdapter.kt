package com.example.cancionero.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultadosAdapter : RecyclerView.Adapter<ResultadosAdapter.ViewHolder>() {

    // Lista de canciones para mostrar (por ahora con datos dummy)
    private var listaCanciones: List<String> = listOf(
        "Canción 1",
        "Canción 2",
        "Canción 3",
        "Otra canción",
        "Última canción"
    )

    // ViewHolder con un TextView simple para mostrar el título
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitulo: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listaCanciones.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titulo = listaCanciones[position]
        holder.textTitulo.text = titulo

        // Aquí luego agregaremos click listener si querés
    }

    // Método para actualizar la lista cuando implementes búsqueda
    fun actualizarLista(nuevaLista: List<String>) {
        listaCanciones = nuevaLista
        notifyDataSetChanged()
    }
}
