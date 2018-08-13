package app.first.my.registroabc;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class EntradaSalida extends AppCompatActivity {

    public Button btnEntrada;
    public Button btnSalida;
    public String txtAlertEnt;
    public String bcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_salida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();

        //pass data values
        Boolean vShift = bundle.getBoolean("vShift");
        Boolean vSust = bundle.getBoolean("vSust");
        Boolean vCurs = bundle.getBoolean("vCurs");
        Boolean vHext = bundle.getBoolean("vHext");


        txtAlertEnt = bundle.getString("txtAlertEnt");

        Intent inScan = new Intent(EntradaSalida.this,Scanner.class);


        //Start scanner
        startActivityForResult(inScan, 0);


        if (vCurs || vSust ) {

            if (vCurs) {
                goCurList();

            }
            else {
                goSust();

            }

        }else{

            alertEnt();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    //Get message
                    //TODO remove text variable
                    //bcode = data.getStringExtra("code");

                    bcode = data.getStringExtra("vName");

                }else{
                    finish();
                }
        }
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void goCurList() {

        btnEntrada = (Button)findViewById(R.id.btnEntrada);
        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent inCurlist = new Intent(EntradaSalida.this,Cursos.class);

                finish();
                startActivity(inCurlist);


            }
        });

        btnSalida = (Button)findViewById(R.id.btnSalida);
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(EntradaSalida.this);
                a_builder.setMessage("Gracias por registrar su salida")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //go back to main menu
                                //Intent inMain = new Intent(EntradaSalida.this,MainActivity.class);
                                //startActivity(inMain);

                                finish();

                            }
                        });


                final AlertDialog alert = a_builder.create();
                alert.setTitle("Salida");

                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    }
                });

                alert.show();

            }
        });

    }

    public void goSust() {

        btnEntrada = (Button)findViewById(R.id.btnEntrada);
        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent inSust = new Intent(EntradaSalida.this,Sustitucion.class);


                startActivity(inSust);


            }
        });

    }

    public void alertEnt() {


        btnEntrada = (Button)findViewById(R.id.btnEntrada);
        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(EntradaSalida.this);
                a_builder.setMessage("Gracias por registrar su entrada "+bcode)
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                              //go back to main menu
                                //Intent inMain = new Intent(EntradaSalida.this,MainActivity.class);
                                //startActivity(inMain);

                                finish();

                            }
                        });

                 final AlertDialog alert = a_builder.create();
                alert.setTitle("Entrada");

                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    }
                });

                alert.show();






            }
        });

        btnSalida = (Button)findViewById(R.id.btnSalida);
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(EntradaSalida.this);
                a_builder.setMessage("Gracias por registrar su salida")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //go back to main menu
                                //Intent inMain = new Intent(EntradaSalida.this,MainActivity.class);
                                //startActivity(inMain);

                                finish();

                            }
                        });

                final AlertDialog alert = a_builder.create();
                alert.setTitle("Salida");
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    }
                });

                alert.show();

            }
        });

    }


}
