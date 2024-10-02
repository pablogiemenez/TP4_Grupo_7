package com.example.tp4_grupo_7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ArticuloAdapter extends ArrayAdapter<Articulo> {
    private Context context;
    private List<Articulo> articulos;
    private TextView tvArticuloNombre;
    private TextView tvArticuloStock;
    private TextView tvArticuloIdCategoria;

    public ArticuloAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ArticuloAdapter(Context context, List<Articulo> articulos){
        super(context, 0, articulos);
        this.context = context;
        this.articulos = articulos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        Articulo articulo = getItem(position);
        if(v == null){
            v = LayoutInflater.from(context).inflate(R.layout.articulo_item, parent, false);

        }
        if(articulo != null){
            tvArticuloNombre = v.findViewById(R.id.tvArticuloNombre);
            tvArticuloStock = v.findViewById(R.id.tvArticuloStock);
            tvArticuloIdCategoria = v.findViewById(R.id.tvArticuloIdCategoria);
        }
        if(tvArticuloNombre != null){
            tvArticuloNombre.setText(articulo.getNombre());
        }
        if(tvArticuloStock != null){
            tvArticuloStock.setText(articulo.getStock());
        }
        if(tvArticuloIdCategoria != null){
            tvArticuloIdCategoria.setText(articulo.getIdCat());
        }

        return v;
    }
}
