package com.monhardy.unitourapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.monhardy.unitourapp.R;
import com.monhardy.unitourapp.config.ConfiguracaoFirebase;
import com.monhardy.unitourapp.model.Usuario;

import helper.UsuarioFirebase;

public class MainActivity extends AppCompatActivity {

    private TextView editText;
    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
    }
    //public void telaInicial(View view){
       // startActivity(new Intent(this, telaInicial.class));
    //}
    public void cadastro(View view){
        startActivity(new Intent(this, cadastro.class));
    }

    public void validarLoginUsuario( View view){

        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoEmail.isEmpty() ){
            if( !textoSenha.isEmpty() ){
                Usuario usuario = new Usuario();
                usuario.setEmail( textoEmail );
                usuario.setSenha( textoSenha);

                logarUsuario( usuario );
            }else{
                Toast.makeText( MainActivity.this,
                        "Preencha asenha",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText( MainActivity.this,
                    "Preencha o email",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void logarUsuario( Usuario usuario ) {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {
                    if( task.isSuccessful() ){
                        UsuarioFirebase.redirecionaUsuarioLogado(MainActivity.this);
                    }else{

                    }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}