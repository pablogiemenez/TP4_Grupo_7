package com.example.tp4_grupo_7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

public class AltaFragment extends Fragment {
    private TextInputEditText editTextID;
    private TextInputEditText editTextProductName;
    private TextInputEditText editTextStock;
    private Spinner spinnerCategory;
    private Button buttonAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alta, container, false);

        initVars(view);

        DataCategoria data = new DataCategoria(spinnerCategory,getContext());
        data.getExecutor();

        // botón Agregar
        buttonAdd.setOnClickListener(v -> {
            // Obtener los datos de los campos de texto
            String id = editTextID.getText().toString();
            String productName = editTextProductName.getText().toString();
            String stock = editTextStock.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            // Validar que los campos no estén vacíos
            if (id.isEmpty() || productName.isEmpty() || stock.isEmpty()) {

                Toast.makeText(getContext(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí puedes añadir la lógica para manejar el registro del producto
                DataArticulo data1 = new DataArticulo(getContext());

                data1.InsertArticle(Integer.parseInt(id), productName, Integer.parseInt(stock), category);

                Toast.makeText(getContext(), "Producto agregado: " + productName + ", " + id + ", " + category + ", " + stock, Toast.LENGTH_SHORT).show();
            }

            editTextID.setText("");
            editTextStock.setText("");
            editTextProductName.setText("");
            spinnerCategory.setSelection(0);
        });
        // Inflar el layout de la actividad
        return view;
    }
    public void initVars(View view){
        editTextID = view.findViewById(R.id.etNombre);
        editTextProductName = view.findViewById(R.id.editTextProductName);
        editTextStock = view.findViewById(R.id.editTextStock);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        buttonAdd = view.findViewById(R.id.buttonAdd);
    }
}
