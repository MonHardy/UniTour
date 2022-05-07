package com.monhardy.unitourapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.monhardy.unitourapp.R;
import com.monhardy.unitourapp.config.ConfiguracaoFirebase;
import com.monhardy.unitourapp.model.Usuario;
import com.monhardy.unitourapp.model.Usuario;

import helper.UsuarioFirebase;

public class cadastro extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputEditText campoNome, campoEmail, campoSenha;
    private Switch switchTipoUsuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().hide();

        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        switchTipoUsuario = findViewById(R.id.switchTipoUsuario);

    }

    public void validarCadastroUsuario(View view) {

        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if (!textoNome.isEmpty()) {
            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {

                    Usuario usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);
                    usuario.setTipo( verificaTipoUsuario() );

                    cadastrarUsuario(usuario);

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
        public String verificaTipoUsuario(){
            return switchTipoUsuario.isChecked() ? "P" : "A";
        }


    public void cadastrarUsuario( Usuario usuario ){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {

                if ( task.isSuccessful() ) {

                    try {

                        String idUsuario = task.getResult().getUser().getUid();
                        usuario.setId(idUsuario);
                        usuario.salvar();

                        /* Atualizar nome UserProfile */
                        UsuarioFirebase.atualizarNomeUsuario( usuario.getNome() );

                        /* Próxima tela -- Depois da autenticação */
                        /* Se for aluno, chama a telainicial, se não for, chama a MapsActivity */

                        if (verificaTipoUsuario() == "A") {
                            startActivity(new Intent(cadastro.this, telaInicial.class));
                            finish();

                            Toast.makeText(cadastro.this,
                                    "Sucesso ao cadastrar Aluno!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(cadastro.this, MapsActivity.class));
                            finish();

                            Toast.makeText(cadastro.this,
                                    "Sucesso ao cadastrar Professor!",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }else{

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e) {
                        excecao = "digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário" + e.getMessage();
                        e.printStackTrace();

                    }

                    Toast.makeText (cadastro.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

