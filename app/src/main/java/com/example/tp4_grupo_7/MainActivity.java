package com.example.tp4_grupo_7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los elementos UI
    private TextInputEditText editTextID;
    private TextInputEditText editTextProductName;
    private TextInputEditText editTextStock;
    private Spinner spinnerCategory;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflar el layout de la actividad
        setContentView(R.layout.activity_main);

        // Vinculación de los elementos del layout con variables Java
        editTextID = findViewById(R.id.editTextID);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextStock = findViewById(R.id.editTextStock);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonAdd = findViewById(R.id.buttonAdd);

        // Acción al hacer clic en el botón "Agregar"
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de los campos de texto
                String id = editTextID.getText().toString();
                String productName = editTextProductName.getText().toString();
                String stock = editTextStock.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();

                // Validar que los campos no estén vacíos
                if (id.isEmpty() || productName.isEmpty() || stock.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    // Aquí puedes añadir la lógica para manejar el registro del producto
                    Toast.makeText(MainActivity.this, "Producto agregado: " + productName, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
