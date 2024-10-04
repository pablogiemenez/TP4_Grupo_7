package com.example.tp4_grupo_7;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    private Context context;
    private ArrayList<Categoria> listaCategorias;
    public CategoriaAdapter(Context context, ArrayList<Categoria> listaCategorias){
        super(context,android.R.layout.simple_spinner_item,listaCategorias);
        this.listaCategorias=listaCategorias;
        this.context=context;
    }

}
