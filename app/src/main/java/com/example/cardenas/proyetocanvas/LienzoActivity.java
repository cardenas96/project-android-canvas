package com.example.cardenas.proyetocanvas;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class LienzoActivity extends AppCompatActivity {

    Paint pincel = new Paint();
    private ConstraintLayout miLayout;
    String archivos[],nombre;
    File archivoDibujo;
    Bitmap canvasBitmap,bitmap;
    private int tamano = 1,color,valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienzo);
        miLayout = (ConstraintLayout)findViewById(R.id.miLayout);
        Lienzo imagen = new Lienzo(this,null);
        imagen.setBackgroundColor(Color.WHITE);
        miLayout.addView(imagen);
        Bundle bundle = getIntent().getExtras();
        valor = bundle.getInt("valor");
        if (valor == 1)
        {
            nombre = bundle.getString("pintura");
            archivoDibujo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Dibujazo/",nombre);
           /* BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile(archivoDibujo.getAbsolutePath(),options);*/
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Dibujazo/");
        archivos = file.list();
        color = Color.BLACK;
        pincel.setAntiAlias(true);
        //pincel.setStrokeJoin(Paint.Join.ROUND);
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setStrokeWidth(tamano);
        pincel.setColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_iconos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cRojo:
                color = Color.RED;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.cVerde:
                color = Color.GREEN;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.cAzul:
                color = Color.BLUE;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.cNegro:
                color = Color.BLACK;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.cBlanco:
                color = Color.WHITE;
                cambiosPincel(false,tamano,color);
            case R.id.p1:
                tamano = 1;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.p2:
                tamano = 2;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.p4:
                tamano = 4;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.p6:
                tamano = 6;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.p8:
                tamano = 8;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.p10:
                tamano = 10;
                cambiosPincel(false,tamano,color);
                return true;
            case R.id.bChico:
                borradorActivo(true);
                pincel.setColor(Color.WHITE);
                pincel.setStrokeWidth(10);
                return true;
            case R.id.bNormal:
                borradorActivo(true);
                pincel.setColor(Color.WHITE);
                pincel.setStrokeWidth(20);
                return true;
            case R.id.bGrande:
                borradorActivo(true);
                pincel.setColor(Color.WHITE);
                pincel.setStrokeWidth(35);
                return true;
            case R.id.grabar:
                if(valor == 0)
                {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    final EditText etNombre = new EditText(this);
                    etNombre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    etNombre.setInputType(InputType.TYPE_CLASS_TEXT);
                    dialogo.setTitle("Nombre para Dibujo")
                            .setMessage("Introduzca un nombre:")
                            .setView(etNombre)
                            .setCancelable(false)
                            //.setView(inflater.inflate(R.layout.dialogo_clave,null))
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!etNombre.getText().toString().equals("")) {
                                        int resul = verificaNombre(etNombre.getText().toString());
                                        if (resul == 1) {
                                            alertaMensaje("\nNo se puede guardar con ese nombre\nEl primer caracter  debe de ser una letra minuscula");
                                        } else if (resul == 2)
                                            alertaMensaje("\nNo se puede guardar con ese nombre\nSolo se admniten letras minusculas, numeros y guion bajo");
                                        else
                                            guardarImagen(etNombre.getText().toString() + ".png");
                                    }
                                    else
                                        alertaMensaje("\nCampo Vacio\nIngrese una nombre valido");
                                }
                            })
                            .show();
                }
                else
                    guardarImagen(nombre);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void borradorActivo(boolean valor)
    {
        if(valor)
            pincel.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else
            pincel.setXfermode(null);
    }

    public void cambiosPincel(boolean valor,int tam,int color)
    {
        borradorActivo(valor);
        pincel.setColor(color);
        pincel.setStrokeWidth(tam);
    }
    public void guardarImagen(String nombre)
    {
        String tipo="";
        if(valor == 0)
            tipo = "Grabó";
        else
            tipo = "Modificó";

        try
        {
            File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String rutaCompleta = ruta.getAbsolutePath() + "/Dibujazo/";
            File archivo = new File(rutaCompleta,nombre);
            if(!archivo.exists() || valor == 1)
            {
                FileOutputStream fos = new FileOutputStream(archivo);
                canvasBitmap.compress(Bitmap.CompressFormat.PNG, 95, fos);
                Toast.makeText(this, "La imagen se " + tipo, Toast.LENGTH_LONG).show();
                finish();
            }
            else
                alertaMensaje("\nNo se puede guardar con ese nombre\nEse nombre de archivo ya existe");
            /*FileOutputStream fos = new FileOutputStream(archivo);
            canvasBitmap.compress(Bitmap.CompressFormat.PNG,95,fos);
            Toast.makeText(this,"La imagen se " + tipo,Toast.LENGTH_SHORT).show();*/
        }
        catch (IOException e)
        {
            Toast.makeText(this,"La imagen no se " + tipo,Toast.LENGTH_SHORT).show();
        }
    }
    public int verificaNombre(String nombret)
    {
        String dicc = "abcdefghijklmnñopqrstuvwxyz0123456789_";
        char cc = nombret.charAt(0);
        boolean primerMinuscula = false;
        for(int x=0;x < 27;x++)
        {
            if(cc == dicc.charAt(x))
            {
                primerMinuscula = true;
                break;
            }
        }
        if(primerMinuscula)
        {
            for(int y=0;y<nombret.length();y++)
            {
                if(!dicc.contains(String.valueOf(nombret.charAt(y))))
                    return 2;
            }
            return 0;
        }
        else
            return 1;

       /* if(nombret.charAt(0) == nombret.toLowerCase().charAt(0))
            return 0;
        return 1;*/
    }


    public void alertaMensaje(final String mensaje)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        TextView tvCadena = new TextView(this);
        tvCadena.setGravity(Gravity.CENTER);
        tvCadena.setTextColor(Color.BLACK);
        tvCadena.setTypeface(null, Typeface.NORMAL);
        tvCadena.setTextSize(15);
        tvCadena.setText(mensaje);
        dialogo.setTitle("Tenemos Un Problema")
                .setView(tvCadena)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public boolean isExternalStoregeWritable()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
            return true;

        return false;
    }

    class Lienzo extends View
    {
        private Paint bitmapPincel;
        float x,y;
        //Bitmap canvasBitmap;
        Path path = new Path();
        private Canvas canvas;

        public Lienzo(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            bitmapPincel = new Paint();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if(valor == 1) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 0;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeFile(archivoDibujo.getAbsolutePath(),options);
                canvasBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                canvas = new Canvas(canvasBitmap);

                //canvas.drawBitmap(canvasBitmap,0,0,bitmapPincel);
            }
            else {
                canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(canvasBitmap);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);

            if(valor != 1)
                canvas.drawRGB(255,255,255);
            else
                canvas.drawBitmap(canvasBitmap,0, 0, bitmapPincel);
              //  canvas.drawBitmap(canvasBitmap, 0, 0, bitmapPincel);
            canvas.drawBitmap(canvasBitmap, 0, 0, bitmapPincel);
            canvas.drawPath(path,pincel);
            //path.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x = event.getX();
            y = event.getY();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x,y);
                    break;

                case MotionEvent.ACTION_UP:
                    path.lineTo(x, y);
                    canvas.drawPath(path, pincel);//Dibujas en path
                    path.reset();//Reiniciamos el Path para nuevos pinceles
                    break;
            }
            invalidate();
            return true;
        }
    }
}
