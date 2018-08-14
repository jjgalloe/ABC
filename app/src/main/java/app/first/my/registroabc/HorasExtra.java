package app.first.my.registroabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HorasExtra extends AppCompatActivity {

    public Button btnHoEx;
    public Button btnHoExMa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_extra);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goHorasExtraManuales();
        goHorasExtra();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void goHorasExtra(){

        btnHoEx = (Button)findViewById(R.id.btnHoEx);
        btnHoEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inHoEx = new Intent(HorasExtra.this, Scanner.class);
                startActivity(inHoEx);
                finish();
            }
        });

    }

    public void goHorasExtraManuales(){

        btnHoExMa = (Button)findViewById(R.id.btnHoExMa);
        btnHoExMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inHoExMa = new Intent(HorasExtra.this, HorasExtraManuales.class);
                startActivity(inHoExMa);
                finish();
            }
        });

    }

}
