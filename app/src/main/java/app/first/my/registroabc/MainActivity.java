package app.first.my.registroabc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public Button btnShift;
    public Button btnCurs;
    public Button btnSust;
    public Button btnHext;
    public Boolean vShift = false;
    public Boolean vSust = false;
    public Boolean vCurs = false;
    public Boolean vHext = false;
    public String txtAlertEnt = "";



    public void goShft() {

        btnShift = (Button)findViewById(R.id.btnShift);
        btnShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vShift = true;
                txtAlertEnt = " para Shift";

                Intent inShift = new Intent(MainActivity.this,EntradaSalida.class);
                //Intent inScan = new Intent(MainActivity.this,Scanner.class);
                inShift.putExtra("vShift",vShift);
                inShift.putExtra("txtAlertEnt",txtAlertEnt);

                //startActivity(inScan);

                startActivity(inShift);



            }
        });

    }

    public void goSust() {

        btnSust = (Button)findViewById(R.id.btnSust);
        btnSust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vSust = true;

                Intent inSust = new Intent(MainActivity.this,Sustitucion.class);
                inSust.putExtra("vSust",vSust);

                startActivity(inSust);



            }
        });

    }

    public void goCurs() {

        btnCurs = (Button)findViewById(R.id.btnCursos);
        btnCurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vCurs = true;

                Intent inCurs = new Intent(MainActivity.this,EntradaSalida.class);
                inCurs.putExtra("vCurs",vCurs);

                startActivity(inCurs);



            }
        });

    }

    public void goHext() {

        btnHext = (Button)findViewById(R.id.btnExtra);
        btnHext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vHext = true;
                txtAlertEnt = " para horas extra";

                Intent inHext = new Intent(MainActivity.this,HorasExtra.class);
                inHext.putExtra("vHext",vHext);
                inHext.putExtra("txtAlertEnt",txtAlertEnt);

                startActivity(inHext);



            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goShft();
        goCurs();
        goSust();
        goHext();
    }
}
