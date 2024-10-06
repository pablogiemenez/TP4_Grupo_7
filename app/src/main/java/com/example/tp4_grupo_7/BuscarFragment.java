package com.example.tp4_grupo_7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BuscarFragment extends Fragment {
    private EditText etID;
    private EditText etNombre;
    private EditText etStock;
    private Spinner spCategoria;
    private Button btnBuscar;
    private Button btnModificar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initVars(view);

        DataCategoria data = new DataCategoria(spCategoria, getContext());
        data.getExecutor();

        btnBuscar.setOnClickListener(v -> buscarArticulo());
        btnModificar.setOnClickListener(v -> modificarArticulo());

        return view;
    }

    public void initVars(View view) {
        etID = view.findViewById(R.id.etID);
        etNombre = view.findViewById(R.id.etNombre);
        etStock = view.findViewById(R.id.etStock);
        spCategoria = view.findViewById(R.id.spCategoria);
        btnBuscar = view.findViewById(R.id.button);
        btnModificar = view.findViewById(R.id.btnModificar);
    }

    private void buscarArticulo() {
        String idStr = etID.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, ingrese un ID", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(idStr);

        DataArticulo dataArticulo = new DataArticulo(getContext()); // Asegúrate de pasar los parámetros necesarios si es necesario
        dataArticulo.buscarArticulo(id, new DataArticulo.OnArticuloFoundListener() {
            @Override
            public void onArticuloFound(Articulo articulo) {
                etNombre.setText(articulo.getNombre());
                etStock.setText(String.valueOf(articulo.getStock()));

                // Obtener el id de categoría
                int idCategoria = articulo.getIdCat();
                DataCategoria dataCategoria = new DataCategoria(spCategoria, getContext());
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
                Toast.makeText(getContext(), "Artículo no encontrado", Toast.LENGTH_SHORT).show();
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

        DataArticulo dataArticulo = new DataArticulo(getContext());
        dataArticulo.modificarArticulo(id, nombre, stock, descripcionCategoria);

        Toast.makeText(getContext(), "Artículo modificado", Toast.LENGTH_SHORT).show();
    }

}
