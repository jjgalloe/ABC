package app.first.my.registroabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Turno extends AppCompatActivity {

    public Button btnAm;
    public Button btnPm;
    public Boolean vHext = false;
    public String txtAlertEnt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goEntSal();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void goEntSal() {


        btnAm = (Button) findViewById(R.id.btnAm);
        btnAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                vHext = true;
                txtAlertEnt = " para horas extra";

                //Intent inHext = new Intent(MainActivity.this,EntradaSalida.class);
                Intent inEnS = new Intent(Turno.this,EntradaSalida.class);
                inEnS.putExtra("vHext",vHext);
                inEnS.putExtra("txtAlertEnt",txtAlertEnt);

                startActivity(inEnS);
                finish();



            }
        });

        btnPm = (Button) findViewById(R.id.btnPm);
        btnPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vHext = true;
                txtAlertEnt = " para horas extra";

                //Intent inHext = new Intent(MainActivity.this,EntradaSalida.class);
                Intent inEnS = new Intent(Turno.this,EntradaSalida.class);
                inEnS.putExtra("vHext",vHext);
                inEnS.putExtra("txtAlertEnt",txtAlertEnt);

                startActivity(inEnS);
                finish();


            }
        });

    }
}
