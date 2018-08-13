package app.first.my.registroabc;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cursos extends AppCompatActivity {

    public Button btnCurso;
    public Button btnVar;
    public Button btnCurso1;
    public Button btnCurso2;
    public Button btnCurso3;
    public Button btnCurso4;
    public Button btnCurso5;
    public Button btnCurso6;
    public String txtAlertCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goCurso();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void goCurso(){


        btnCurso1 = (Button)findViewById(R.id.btnCurso1);
        btnCurso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCurso = (Button)findViewById(R.id.btnCurso1);
                alertCurs();

            }
        });

        btnCurso2 = (Button)findViewById(R.id.btnCurso2);
        btnCurso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCurso = (Button)findViewById(R.id.btnCurso2);
                alertCurs();

            }
        });

        btnCurso3 = (Button)findViewById(R.id.btnCurso3);
        btnCurso3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCurso = (Button)findViewById(R.id.btnCurso3);
                alertCurs();

            }
        });

        btnCurso4 = (Button)findViewById(R.id.btnCurso4);
        btnCurso4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCurso = (Button)findViewById(R.id.btnCurso4);
                alertCurs();

            }
        });

        btnCurso5 = (Button)findViewById(R.id.btnCurso5);
        btnCurso5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCurso = (Button)findViewById(R.id.btnCurso5);
                alertCurs();

            }
        });

        btnCurso6 = (Button)findViewById(R.id.btnCurso6);
        btnCurso6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCurso = (Button)findViewById(R.id.btnCurso6);
                alertCurs();

            }
        });



    }

    public void alertCurs() {



            txtAlertCurso = btnCurso.getText().toString();

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Cursos.this);
                a_builder.setMessage("Gracias por registrar su entrada a "+txtAlertCurso)
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //go back to main menu

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
}
