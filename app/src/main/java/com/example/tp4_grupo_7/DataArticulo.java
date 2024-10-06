package com.example.tp4_grupo_7;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataArticulo extends AsyncTask<String, Void, String> {
    private ListView lvArticulos;
    private Context context;

    private static String result2;
    private static ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();

    public DataArticulo(ListView lvArticulos, Context context) {
        this.lvArticulos = lvArticulos;
        this.context = context;
    }
    public DataArticulo(Context context){
        this.context=context;
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

    public void InsertArticle(int id, String nombre,int stock,String descripcionCategoria){

        executor.execute(()->{

            try{
                DataCategoria data=new DataCategoria();
                int idCategoria=data.ObtenerIdCategoria(descripcionCategoria);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10734808", "sql10734808", "aWgDljDA2v");
                PreparedStatement pst = con.prepareStatement("insert into articulo(id,nombre,stock,idCategoria) values (?,?,?,?)");
                pst.setInt(1,id);
                pst.setString(2,nombre);
                pst.setInt(3,stock);
                pst.setInt(4,idCategoria);

                pst.close();
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        });
    }

    public void buscarArticulo(int id, OnArticuloFoundListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10734808", "sql10734808", "aWgDljDA2v");
                PreparedStatement pst = con.prepareStatement("SELECT * FROM articulo WHERE id = ?");
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();

                Articulo articulo = null;
                if (rs.next()) {
                    articulo = new Articulo();
                    articulo.setId(rs.getInt("id"));
                    articulo.setNombre(rs.getString("nombre"));
                    articulo.setStock(rs.getInt("stock"));
                    articulo.setIdCat(rs.getInt("idCategoria"));
                }

                rs.close();
                pst.close();
                con.close();

                // Notificar el resultado en el hilo principal
                final Articulo finalArticulo = articulo;
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    if (finalArticulo != null) {
                        listener.onArticuloFound(finalArticulo);
                    } else {
                        listener.onArticuloNotFound();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public interface OnArticuloFoundListener {
        void onArticuloFound(Articulo articulo);
        void onArticuloNotFound();
    }

    public void modificarArticulo(int id, String nombre, int stock, String descripcionCategoria) {
        executor.execute(() -> {
            try {
                DataCategoria data = new DataCategoria();
                int idCategoria = data.ObtenerIdCategoria(descripcionCategoria);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10734808", "sql10734808", "aWgDljDA2v");
                PreparedStatement pst = con.prepareStatement("UPDATE articulo SET nombre=?, stock=?, idCategoria=? WHERE id=?");
                pst.setString(1, nombre);
                pst.setInt(2, stock);
                pst.setInt(3, idCategoria);
                pst.setInt(4, id);

                pst.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected String doInBackground(String... strings) {
        return "";
    }
}
