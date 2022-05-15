package com.monhardy.unitourapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.monhardy.unitourapp.R;
import com.monhardy.unitourapp.config.ConfiguracaoFirebase;
import com.monhardy.unitourapp.model.Usuario;

import helper.UsuarioFirebase;

public class telaInicial extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        getSupportActionBar().hide();
    }
    public void perfilAluno( View view ) {startActivity(new Intent(this, telaAluno.class));}
    public void telaLogin ( View view ) {startActivity(new Intent(this, MainActivity.class));}
}
