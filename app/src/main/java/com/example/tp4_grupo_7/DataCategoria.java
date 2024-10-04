package com.example.tp4_grupo_7;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataCategoria extends AsyncTask<String, Void, String> {
    private Spinner sp_categoria;
    private Context context;
    private static ArrayList<Categoria> listaCategorias= new ArrayList<Categoria>();
    public DataCategoria(Spinner sp_categoria,Context context){
        this.sp_categoria=sp_categoria;
        this.context=context;
    }
    ExecutorService executor = Executors.newSingleThreadExecutor();
    public void getExecutor() {
        executor.execute(() -> {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10734808", "sql10734808", "aWgDljDA2v");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM categoria");
                System.out.println("conexion completa");

                while(rs.next()){
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setDescripcion(rs.getString("descripcion"));

                    listaCategorias.add(categoria);
                }
                rs.close();

            }
            catch (Exception e){e.printStackTrace();
            System.out.println("conexion erronea");

            }
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {

                CategoriaAdapter adapter = new CategoriaAdapter(context, listaCategorias);
                sp_categoria.setAdapter(adapter);
            });
        });

    }

    @Override
    protected String doInBackground(String... strings) {
        return "";
    }
}
