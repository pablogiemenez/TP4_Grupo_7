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

                int filasModificadas= pst.executeUpdate();
                if(filasModificadas>0){

                    System.out.println("Articulo guardado con exito");
                }
                pst.close();
                con.close();
            }catch(Exception e){
                e.printStackTrace();

                System.out.println("Error al gurdar articulo");
            }

        });
    }


    @Override
    protected String doInBackground(String... strings) {
        return "";
    }
}
