package com.example.tp4_grupo_7;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataCategoria extends AsyncTask<String, Void, String> {
    private Spinner sp_categoria;
    private Context context;

    private static ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();

    // Nueva configuración de conexión
    private static final String URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10741357";
    private static final String USER = "sql10741357";
    private static final String PASSWORD = "vrVhL6AgjG";

    public DataCategoria(Spinner sp_categoria, Context context) {
        this.sp_categoria = sp_categoria;
        this.context = context;
    }

    public DataCategoria() {}

    ExecutorService executor = Executors.newSingleThreadExecutor();

    public void getExecutor() {
        executor.execute(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM categoria");
                System.out.println("Conexión completa");

                listaCategorias.clear();

                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setDescripcion(rs.getString("descripcion"));

                    listaCategorias.add(categoria);
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Conexión errónea");
            }
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                CategoriaAdapter adapter = new CategoriaAdapter(context, listaCategorias);
                sp_categoria.setAdapter(adapter);
            });
        });
    }

    public int ObtenerIdCategoria(String descripcion) {
        int idCategoria;
        Future<Integer> futureId = executor.submit(() -> {
            int idResult = -1;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement("SELECT id FROM categoria WHERE descripcion = ?");
                pst.setString(1, descripcion);
                ResultSet rs = pst.executeQuery();
                System.out.println("Conexión completa");

                if (rs.next()) {
                    idResult = rs.getInt("id");
                }
                rs.close();
                pst.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return idResult;
        });
        try {
            idCategoria = futureId.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return idCategoria;
    }

    public ArrayList<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    @Override
    protected String doInBackground(String... strings) {
        return "";
    }
}
