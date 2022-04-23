package com.monhardy.unitourapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.monhardy.unitourapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }
    public void telaInicial(View view){
        startActivity(new Intent(this, telaInicial.class));
    }
    public void cadastro(View view){
        startActivity(new Intent(this, cadastro.class));
    }


}