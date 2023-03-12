package com.example.repaso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repaso.models.Alumno;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolder>{
    ArrayList<Alumno> listaNombres;

    // Constructor, se le pasa la lista de nombres
    public AdapterDatos(ArrayList<Alumno> listaNombres) {
        this.listaNombres = listaNombres;
    }

    // Crea las "tarjetas" que se van a mostrar en el RecyclerView
    @NonNull
    @Override
    public AdapterDatos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se crea la vista de la tarjeta
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);

        // Se crea el ViewHolder (la clase interna de mas abajo) y se le pasa la vista
        return new ViewHolder(view);
    }

    // Asigna los datos a cada tarjeta (ViewHolder) con los datos de la lista
    @Override
    public void onBindViewHolder(@NonNull AdapterDatos.ViewHolder holder, int position) {
        holder.asignarDatos(listaNombres.get(position));
    }

    // Devuelve el tamaño de la lista
    @Override
    public int getItemCount() {
        return listaNombres.size();
    }

    // CLASE DEL VIEWHOLDER, EL CONTROLADOR DE LAS TARJETAS
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Se declaran los elementos de la tarjeta
        TextView nombre;
        TextView apellido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Se asignan los elementos de la tarjeta
            nombre = itemView.findViewById(R.id.txtAlumno);
            apellido = itemView.findViewById(R.id.txtApellido);

            // Se le asigna un evento al hacer click en la tarjeta
            this.itemView.setOnClickListener(v -> Toast.makeText(itemView.getContext(), "Click en " + nombre.getText(), Toast.LENGTH_SHORT).show());
        }

        // Se asignan los datos a los elementos de la tarjeta
        public void asignarDatos(Alumno alumno) {
            nombre.setText(alumno.getNombre());
            apellido.setText(alumno.getApellido());
        }
    }
}
