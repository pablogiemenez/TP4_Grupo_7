package com.example.tp4_grupo_7;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataMainActivity extends AsyncTask<String, Void, String> {
    private ListView lvArticulos;
    private Context context;

    private static String result2;
    private static ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();

    public DataMainActivity(ListView lvArticulos, Context context) {
        this.lvArticulos = lvArticulos;
        this.context = context;
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();
    public ExecutorService getExecutor() {
        executor.execute(() -> {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10734808", "sql10734808", "aWgDljDA2v");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("Select * FROM articulo");

                while(rs.next()){
                    Articulo articulo = new Articulo();
                    articulo.setId(rs.getInt("id"));
                    articulo.setNombre(rs.getString("nombre"));
                    articulo.setIdCat(rs.getInt("idCategoria"));
                    articulo.setStock(rs.getInt("stock"));
                    listaArticulos.add(articulo);
                }
                rs.close();
            }
            catch (Exception e){e.printStackTrace();}
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                ArticuloAdapter adapter = new ArticuloAdapter(context, listaArticulos);
                lvArticulos.setAdapter(adapter);
            });
        });
        return executor;
    }


    @Override
    protected String doInBackground(String... strings) {
        return "";
    }
}
