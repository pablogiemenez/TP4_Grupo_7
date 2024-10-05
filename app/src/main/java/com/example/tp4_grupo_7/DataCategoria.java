package com.example.tp4_grupo_7;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

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
    private static ArrayList<Categoria> listaCategorias= new ArrayList<Categoria>();
    public DataCategoria(Spinner sp_categoria,Context context){
        this.sp_categoria=sp_categoria;
        this.context=context;
    }
    public DataCategoria(){

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
    public int ObtenerIdCategoria(String descripcion){
        int idCategoria;
        Future<Integer> futureId=executor.submit(() -> {

            int idResult=-1;
            try{


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10734808", "sql10734808", "aWgDljDA2v");
                PreparedStatement pst = con.prepareStatement("SELECT id FROM categoria where descripcion=?");
                pst.setString(1, descripcion);
                ResultSet rs= pst.executeQuery();
                System.out.println("conexion completa");
                if(rs.next()){
                    idResult=rs.getInt("id");

                }
                rs.close();
                pst.close();
                con.close();

            }catch(Exception e){
                e.printStackTrace();
            }
            return idResult;
    });
        try {
            idCategoria=futureId.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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
