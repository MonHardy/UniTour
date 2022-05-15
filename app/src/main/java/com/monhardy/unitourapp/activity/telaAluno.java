package com.monhardy.unitourapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.monhardy.unitourapp.R;
import com.monhardy.unitourapp.config.ConfiguracaoFirebase;

public class telaAluno extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_aluno);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch( item.getItemId() ) {
            case  R.id.menuSair :
                deslogarUsuario();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void deslogarUsuario(){

        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}