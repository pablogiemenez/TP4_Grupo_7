package com.example.tp4_grupo_7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los elementos UI
    private TextInputEditText editTextID;
    private TextInputEditText editTextProductName;
    private TextInputEditText editTextStock;
    private Spinner spinnerCategory;
    private Button buttonAdd;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflar el layout de la actividad
        setContentView(R.layout.activity_main);

        // Vinculación de los elementos del layout con variables Java
        editTextID = findViewById(R.id.etNombre);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextStock = findViewById(R.id.editTextStock);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonAdd = findViewById(R.id.buttonAdd);

        DataCategoria data= new DataCategoria(spinnerCategory,this);
        data.getExecutor();

        tabLayout = findViewById(R.id.tabLayout);
        // Seleccionar la pestaña "Alta" (0)
        tabLayout.getTabAt(0).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        // Cambiar a la actividad de Modificación
                        Intent modificarIntent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(modificarIntent);
                        break;
                    case 2:
                        // Cambiar a la actividad de Listado
                        Intent listarIntent = new Intent(MainActivity.this, ListadoActivity.class);
                        startActivity(listarIntent);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No se necesita hacer nada aquí, pero se debe implementar el método
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No se necesita hacer nada aquí, pero se debe implementar el método
            }
        });


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
                    DataArticulo data= new DataArticulo(MainActivity.this);
                    data.InsertArticle(Integer.parseInt(id),productName,Integer.parseInt(stock),category);
                    Toast.makeText(MainActivity.this, "Producto agregado: " + productName+", "+id+ ", "+category+", "+stock, Toast.LENGTH_SHORT).show();
                }
                editTextID.setText("");
                editTextStock.setText("");
                editTextProductName.setText("");
                spinnerCategory.setSelection(0);
            }
        });
    }
}
