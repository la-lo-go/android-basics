package com.example.repaso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.repaso.models.Alumno;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    TextView txtUser;
    ImageView img;
    RecyclerView recycler;
    ArrayList<Alumno> listaNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtUser = findViewById(R.id.txtUser2);
        img = findViewById(R.id.imgLogo);
        recycler = findViewById(R.id.recicler);


        // RECUPERACION DE DATOS DE LA ACTIVIDAD ANTERIOR
        txtUser.setText(getIntent().getStringExtra("user"));

        // ANIMACION
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation);
        img.startAnimation(anim);

        // RECYCLER VIEW
        // 1. Crear el ArrayList
        listaNombres = new ArrayList<>();
        listaNombres.add(new Alumno("Juan", "Perez"));
        listaNombres.add(new Alumno("Maria", "Gomez"));
        listaNombres.add(new Alumno("Pedro", "Gonzalez"));
        listaNombres.add(new Alumno("Jose", "Lopez"));
        listaNombres.add(new Alumno("Luis", "Martinez"));

        // 2. Añadir el linear layout al recycler para que muestre los datos
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 3. Crear el adapter y pasarle el ArrayList
        AdapterDatos adapter = new AdapterDatos(listaNombres);

        // 4. Asignar el adapter al recycler
        recycler.setAdapter(adapter);
    }
}