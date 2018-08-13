package app.first.my.registroabc;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class Sustitucion extends AppCompatActivity {

    GridView gridView;
    static final String[] alphabet = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    public String vletra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustitucion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        gridView = (GridView) findViewById(R.id.gridView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alphabet);

        //SpecialAdapter adapter2 = new SpecialAdapter(this,SpecialAdapter,R.layout.activity_sustitucion,1,5);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();



                vletra = alphabet[position];

                //if (position == 0) {
                    Intent intSust2 = new Intent(Sustitucion.this, Sustitucion2.class);
                    intSust2.putExtra("vletra",vletra);
                    startActivity(intSust2);
                    finish();
                //}
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

