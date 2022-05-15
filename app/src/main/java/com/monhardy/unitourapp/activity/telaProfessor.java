package com.monhardy.unitourapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.monhardy.unitourapp.R;

public class telaProfessor extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_professor);

        getSupportActionBar().hide();
    }
}