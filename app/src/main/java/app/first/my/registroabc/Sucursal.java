package app.first.my.registroabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sucursal extends AppCompatActivity {

    public Button btnCurso1;
    public Button btnCurso2;
    public Boolean vHext = false;
    public String txtAlertEnt = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goTurno();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void goTurno() {


        btnCurso1 = (Button) findViewById(R.id.btnObs);
        btnCurso1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vHext = true;
                txtAlertEnt = " para horas extra";

                //Intent inHext = new Intent(MainActivity.this,EntradaSalida.class);
                Intent inTurno = new Intent(Sucursal.this,Turno.class);
                inTurno.putExtra("vHext",vHext);
                inTurno.putExtra("txtAlertEnt",txtAlertEnt);

                startActivity(inTurno);
                finish();



            }
        });

        btnCurso2 = (Button) findViewById(R.id.btnStFe);
        btnCurso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vHext = true;
                txtAlertEnt = " para horas extra";

                //Intent inHext = new Intent(MainActivity.this,EntradaSalida.class);
                Intent inTurno = new Intent(Sucursal.this,Turno.class);
                inTurno.putExtra("vHext",vHext);
                inTurno.putExtra("txtAlertEnt",txtAlertEnt);

                startActivity(inTurno);
                finish();


            }
        });

    }

}
