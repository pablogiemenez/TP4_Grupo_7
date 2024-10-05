package com.example.tp4_grupo_7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private EditText etID;
    private EditText etNombre;
    private EditText etStock;
    private Spinner spCategoria;
    private TabLayout tabLayout;
    private Button btnBuscar;
    private Button btnModificar;

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

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.getTabAt(1).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Intent altaIntent = new Intent(MainActivity2.this, MainActivity.class);
                        altaIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(altaIntent);
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent listadoIntent = new Intent(MainActivity2.this, ListadoActivity.class);
                        listadoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(listadoIntent);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No es necesario implementar lógica aquí
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No es necesario implementar lógica aquí
            }
        });

        DataCategoria data = new DataCategoria(spCategoria, this);
        data.getExecutor();

        btnBuscar.setOnClickListener(v -> buscarArticulo());
        btnModificar.setOnClickListener(v -> modificarArticulo());
    }

    public void initVars() {
        etID = findViewById(R.id.etID);
        etNombre = findViewById(R.id.etNombre);
        etStock = findViewById(R.id.etStock);
        spCategoria = findViewById(R.id.spCategoria);
        btnBuscar = findViewById(R.id.button);
        btnModificar = findViewById(R.id.btnModificar);
    }

    private void buscarArticulo() {
        String idStr = etID.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un ID", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(idStr);

        DataArticulo dataArticulo = new DataArticulo(MainActivity2.this); // Asegúrate de pasar los parámetros necesarios si es necesario
        dataArticulo.buscarArticulo(id, new DataArticulo.OnArticuloFoundListener() {
            @Override
            public void onArticuloFound(Articulo articulo) {
                etNombre.setText(articulo.getNombre());
                etStock.setText(String.valueOf(articulo.getStock()));

                // Obtener el id de categoría
                int idCategoria = articulo.getIdCat();
                DataCategoria dataCategoria = new DataCategoria(spCategoria, MainActivity2.this);
                ArrayList<Categoria> listaCategorias = dataCategoria.getListaCategorias();

                // Establecer el spinner a la categoría correspondiente
                for (int i = 0; i < listaCategorias.size(); i++) {
                    if (listaCategorias.get(i).getId() == idCategoria) {
                        spCategoria.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onArticuloNotFound() {
                Toast.makeText(MainActivity2.this, "Artículo no encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void modificarArticulo() {
        String idStr = etID.getText().toString();
        String nombre = etNombre.getText().toString();
        String stockStr = etStock.getText().toString();
        int stock = stockStr.isEmpty() ? 0 : Integer.parseInt(stockStr);
        int id = Integer.parseInt(idStr);

        Categoria categoriaSeleccionada = (Categoria) spCategoria.getSelectedItem();
        String descripcionCategoria = categoriaSeleccionada.getDescripcion();

        DataArticulo dataArticulo = new DataArticulo(MainActivity2.this);
        dataArticulo.modificarArticulo(id, nombre, stock, descripcionCategoria);

        Toast.makeText(this, "Artículo modificado", Toast.LENGTH_SHORT).show();
    }

}
