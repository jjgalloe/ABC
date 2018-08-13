package app.first.my.registroabc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Sustitucion2 extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname,editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button btnUpdate;

    ListView lstPersonas;

    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustitucion2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Bundle bundle = getIntent().getExtras();

        //String vText = bundle.getString("vletra");

        //textView = (TextView)findViewById(R.id.textView);

        //textView.setText(vText);

        //pasted code starts here

        myDb = new DatabaseHelper(this);

        //editName = (EditText)findViewById(R.id.editText_Name);
        //editSurname = (EditText)findViewById(R.id.editText_Surname);
        //editTextId = (EditText)findViewById(R.id.editText_Id);
        //editMarks = (EditText)findViewById(R.id.editText_Marks);
        //btnAddData = (Button)findViewById(R.id.button_Add);
        //btnViewAll = (Button)findViewById(R.id.button);
        //btnUpdate = (Button)findViewById(R.id.button_Update);
        //btnDelete = (Button)findViewById(R.id.button_Delete);

        lstPersonas = (ListView)findViewById(R.id.personas_listView);

        //String[] personas = {"A_persona_1","A_persona_2","A_persona_3","A_persona_4"};
        //ListAdapter personasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,personas);
        ListView personasListView = (ListView) findViewById(R.id.personas_listView);
        //personasListView.setAdapter(personasAdapter);

        viewAll();



        personasListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String personas = String.valueOf(adapterView.getItemAtPosition(i));

                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Sustitucion2.this);
                        a_builder.setMessage("¿Es correcto el nombre de la persona que sustituirá?\n\n"+personas)
                                .setCancelable(false)
                                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //got to sucursal
                                        Intent inMain = new Intent(Sustitucion2.this,Sucursal.class);
                                        startActivity(inMain);

                                        finish();

                                        //viewAll();

                                    }
                                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });


                        final AlertDialog alert = a_builder.create();
                        alert.setTitle("Sustitución");

                        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                            }
                        });

                        alert.show();

                    }
                }
        );

        //AddData();

        //UpdateData();
        //DeleteData();

        //pasted code ends here



    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



    public void viewAll(){
        //TODO commented lines belong to on click event from a button which does nos exist yet (fix)
        //btnViewAll.setOnClickListener(
                //new View.OnClickListener() {
                    //@Override
                    //public void onClick(View view) {


                    //Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE substr("+PERSONA_APPELLIDO+",1,2)="+vletra,null);
                        Bundle bundle = getIntent().getExtras();
                        String vletra = bundle.getString("vletra");
                        char cletra = vletra.charAt(0);
                        //Cursor res = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
                        Cursor res = myDb.getAllData();





                        if (res.getCount() == 0){
                            //show message
                            showMessage("Error","No data was found");
                            return;
                        }



                        StringBuffer buffer = new StringBuffer();

                        int i=0;
                        int count=0;
                        int bdCount;
                        char first;

                        bdCount = res.getCount();
                        String[] aPersonas = new String[bdCount];
                        String persona;

                        ArrayList<String> stringArrayList = new ArrayList<String>(bdCount);
                        String[] stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);


                        while (res.moveToNext()){
                            /*buffer.append("ID :"+ res.getString(0)+"\n");
                            buffer.append("Nombre :"+ res.getString(1)+"\n");
                            buffer.append("Apellido :"+ res.getString(2)+"\n");*/

                            persona = res.getString(2)+" "+res.getString(1);
                            first = persona.charAt(0);

                            //Adding string to current array position
                            aPersonas[i] = persona;
                            i++;

                            if(first == cletra){
                            count++;
                            }
                        }

                        //Create a new array where the registries matching the request get stored
                        String[] aPersonas2 = new String[count];
                        int i2=0;
                        i=0;

                        while (i2<count)
                        {
                            persona = aPersonas[i];
                            first = persona.charAt(0);

                            if(first == cletra){
                                aPersonas2[i2] = persona;
                                i2++;
                        }

                            i++;
                        }

                        if(i2 == 0){
                            showMessage("¡Alerta!","No se encontró ningún apellido que inicie con la letra "+vletra+"");
                        }


                        ListAdapter personasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,aPersonas2);
                        ListView personasLisyView = (ListView) findViewById(R.id.personas_listView);
                        personasLisyView.setAdapter(personasAdapter);


                        //SHOW ALL DATA
                        //showMessage("Data",buffer.toString());
                        //lstPersonas = (ListView)
                    }
                //}
        //);

    //}






    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}
