package com.example.repaso;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repaso.models.BD;

public class MainActivity extends AppCompatActivity {
    EditText txtUser;
    EditText txtPassword;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        BD db = new BD(this);

        btnLogin.setOnClickListener(v -> {
            String user = txtUser.getText().toString();
            String password = txtPassword.getText().toString();
            Boolean validado = validar(user, password);

            if (validado) {
                if(db.checkUser(user)) {
                    if (db.checkUsernamePassword(user, password)) {
                        Intent intent = new Intent(this, MainActivity2.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Introduce datos", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister = findViewById(R.id.btnRegistrar);
        btnRegister.setOnClickListener(v -> {
            String user = txtUser.getText().toString();
            String password = txtPassword.getText().toString();

            if(!db.checkUser(user) && validar(user, password)) {
                db.insertUser(txtUser.getText().toString(), txtPassword.getText().toString());
                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Usuario ya estaba registrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validar(String user, String password) {
        return !user.isEmpty() && !password.isEmpty();
    }
}