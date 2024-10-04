package com.example.tp4_grupo_7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabItem;

public class MainActivity2 extends AppCompatActivity {
    private EditText etID;
    private EditText etNombre;
    private EditText etStock;
    private Spinner spCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initVars();
        DataCategoria data=new DataCategoria(spCategoria,this);
        data.getExecutor();

    }

    public void initVars (){
        etID = findViewById(R.id.etID);
        etNombre = findViewById(R.id.etNombre);
        etStock = findViewById(R.id.etStock);
        spCategoria = findViewById(R.id.spCategoria);

    }
}