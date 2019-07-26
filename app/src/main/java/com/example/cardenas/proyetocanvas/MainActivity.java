package com.example.cardenas.proyetocanvas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ListView lvPinturas;
    String archivos[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvPinturas = (ListView)findViewById(R.id.lvPinturas);
        if(isExternalStoregeWritable())
            crearDirectorio();
        else
            Toast.makeText(this, "Memoria Externa No Disponible", Toast.LENGTH_SHORT).show();

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Dibujazo/");
        //File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"Dibujazo");
        archivos = file.list();
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,archivos);
        lvPinturas.setAdapter(adapter);
        final Intent intent = new Intent(this,LienzoActivity.class);
        lvPinturas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent.putExtra("pintura",lvPinturas.getItemAtPosition(position).toString());
                intent.putExtra("valor",1);
                startActivity(intent);
            }
        });

    }

    public boolean isExternalStoregeWritable()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
            return true;

        return false;
    }
    private void crearDirectorio()
    {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Dibujazo/");
        //File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),nombreDirectorio);
        if(!file.exists())
            file.mkdirs();
    }
    @Override
    protected void onResume() {
        super.onResume();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Dibujazo/");
        //File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"Dibujazo");
        archivos = file.list();
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,archivos);
        adapter.notifyDataSetChanged();
        lvPinturas.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itNuevo:
                Intent intent = new Intent(this,LienzoActivity.class);
                intent.putExtra("valor",0);
                startActivity(intent);
                return true;
            case R.id.itAcerca:
                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle("Acerca de Dibujazo")
                        .setMessage("Dibujazo APP ver 1.0 \n \n" +
                                "🤓 Desarrolladores: 🤓\n \n😉 José Alberto Cárdenas Hernández\n😎 Jonathan Osornio Mendoza\n😃 Joaquin Alejandro Macias Macias")
                        //.setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
