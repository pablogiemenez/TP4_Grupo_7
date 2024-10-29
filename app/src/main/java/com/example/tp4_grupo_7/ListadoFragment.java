package com.example.tp4_grupo_7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class ListadoFragment extends Fragment {
    private ListView lvArticulos;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_listado, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvArticulos = view.findViewById(R.id.lvArticulos);

        DataArticulo articulo = new DataArticulo(lvArticulos, getContext());
        articulo.getExecutor();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        lvArticulos = view.findViewById(R.id.lvArticulos);

        DataArticulo articulo = new DataArticulo(lvArticulos, getContext());
        articulo.getExecutor();

       
    }

    public void initVars(View view){
    }
}
