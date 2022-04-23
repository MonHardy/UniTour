package com.monhardy.unitourapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.monhardy.unitourapp.R;
import com.monhardy.unitourapp.config.ConfiguracaoFirebase;
import com.monhardy.unitourapp.model.Usuario;
import com.monhardy.unitourapp.model.Usuario;

public class cadastro extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputEditText campoNome, campoEmail, campoSenha;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().hide();

        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);

    }

    public void validarCadastroUsuario(View view) {

        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if (!textoNome.isEmpty()) {
            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {

                    Usuario usuario = new Usuario();
                    usuario.setNome( textoNome );
                    usuario.setEmail( textoEmail );
                    usuario.setSenha( textoSenha );

                    cadastrarUsuario( usuario );

                } else {
                    Toast.makeText(cadastro.this,
                            "preencha a senha!",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(cadastro.this,
                        "preencha o email!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(cadastro.this,
                    "preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastrarUsuario( Usuario usuario ){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {

                if ( task.isSuccessful() ){
                    Toast.makeText(cadastro.this,
                            "Sucesso ao cadastrar Usuário!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

