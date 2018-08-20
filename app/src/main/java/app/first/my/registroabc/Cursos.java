package app.first.my.registroabc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.first.my.registroabc.Business.ConnectionMethods;
import app.first.my.registroabc.Business.DialogMethods;
import app.first.my.registroabc.Data.Device;
import app.first.my.registroabc.Data.UbicheckDetailsRequest;
import app.first.my.registroabc.Data.UbicheckRequest;

public class Cursos extends AppCompatActivity {

    public DatabaseHelper db = new DatabaseHelper(Cursos.this);
    private ProgressDialog progressDialog;

    public Button btnCurso;
    public Button btnVar;
    public Button btnCurso1;
    public Button btnCurso2;
    public Button btnCurso3;
    public Button btnCurso4;
    public Button btnCurso5;
    public Button btnCurso6;
    public String txtAlertCurso;

    public int EmployeeID;
    public int OpenUbicheckDetailID;
    public String OpenUbicheckDetailName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();


        List<String> ElementsText = (List<String>) getIntent().getSerializableExtra("ElementsText");
        List<Integer> ElementsValue = (List<Integer>) getIntent().getSerializableExtra("ElementsValue");

        goCurso();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(16,16,16,16);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_row_1);

        EmployeeID = getIntent().getIntExtra("EmployeeID",0);
        OpenUbicheckDetailID = getIntent().getIntExtra("OpenUbicheckDetailID",0);
        OpenUbicheckDetailName = getIntent().getStringExtra("OpenUbicheckDetailName");

        if (OpenUbicheckDetailID == 0){
            for (int i = 0; i < ElementsText.size(); i++){
                Integer j = i / 3;

                switch (j){
                    case 1:
                        linearLayout = (LinearLayout)findViewById(R.id.layout_row_2);
                        break;
                    case 2:
                        linearLayout = (LinearLayout)findViewById(R.id.layout_row_3);
                        break;
                    case 3:
                }

                //Toast.makeText(this, ElementsValue.get(i) + " - " + ElementsText.get(i), Toast.LENGTH_SHORT).show();

                final Button button = new Button(this);
                button.setLayoutParams(params);
                button.setText(ElementsText.get(i));
                button.setHeight(250);
                button.setTextSize(24);
                button.setId(ElementsValue.get(i));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Device Device = db.GetDevice();
                        showSpinner("Registrando inicio de curso");
                        UbicheckDetailsRequest UbicheckDetailsRequest = new UbicheckDetailsRequest(0, button.getId(), Device.DeviceID,new Date(),EmployeeID);
                        AsyncUbicheckDetails AsyncUbicheckDetails = new AsyncUbicheckDetails(UbicheckDetailsRequest);
                        AsyncUbicheckDetails.execute("/UbicheckDetails");
                    }
                });

                linearLayout.addView(button);
            }

            Integer complete3 = 3 - (ElementsText.size() % 3);

            if (complete3 != 3){
                for (int i = 0; i < complete3; i++) {
                    Button button = new Button(this);
                    button.setLayoutParams(params);
                    button.setText("Boton");
                    button.setHeight(250);
                    button.setTextSize(24);
                    button.setId(Integer.parseInt("1"));
                    button.setVisibility(View.INVISIBLE);

                    linearLayout.addView(button);
                }
            }
        }
        else {

            linearLayout.setGravity(Gravity.CENTER_VERTICAL);

            TextView textView = new TextView(this);
            textView.setTextSize(32);
            textView.setText("Salir de curso");
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);

            linearLayout.addView(textView);

            final Button button = new Button(this);
            button.setLayoutParams(params);
            button.setText(OpenUbicheckDetailName);
            button.setHeight(250);
            button.setTextSize(24);
            button.setPadding(10,10,10,10);
            button.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            button.setId(OpenUbicheckDetailID);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Device Device = db.GetDevice();
                    showSpinner("Registrando fin de curso");
                    UbicheckDetailsRequest UbicheckDetailsRequest = new UbicheckDetailsRequest(OpenUbicheckDetailID, 0, Device.DeviceID,new Date(),EmployeeID);
                    AsyncUbicheckDetails AsyncUbicheckDetails = new AsyncUbicheckDetails(UbicheckDetailsRequest);
                    AsyncUbicheckDetails.execute("/UbicheckDetails");
                }
            });

            linearLayout.addView(button);
        }

        //linearLayout = (LinearLayout)findViewById(R.id.layout_row_3);

        /*Button button = new Button(this);
        button.setLayoutParams(params);
        button.setText("Boton");
        button.setHeight(250);
        button.setTextSize(24);
        button.setId(Integer.parseInt("1"));

        linearLayout.addView(button);

        button = new Button(this);
        button.setLayoutParams(params);
        button.setText("Boton");
        button.setHeight(250);
        button.setTextSize(24);
        button.setId(Integer.parseInt("1"));

        linearLayout.addView(button);

        button = new Button(this);
        button.setLayoutParams(params);
        button.setText("Boton");
        button.setHeight(250);
        button.setTextSize(24);
        button.setId(Integer.parseInt("1"));

        linearLayout.addView(button);*/
        /*

        Button button2 = new Button(this);
        button2.setLayoutParams(params);
        button2.setText("Boton 2");
        button2.setWidth(50);
        button2.setId(Integer.parseInt("24"));

        linearLayout.addView(button2);

        Button button3 = new Button(this);
        button3.setLayoutParams(params);
        button3.setText("Boton 3");
        button3.setWidth(50);
        button3.setId(Integer.parseInt("24"));

        linearLayout.addView(button3);



        LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout_second_row);

        Button button4 = new Button(this);
        button4.setLayoutParams(params);
        button4.setText("Boton 4");
        button4.setWidth(50);
        button4.setId(Integer.parseInt("24"));

        linearLayout2.addView(button4);

        Button button5 = new Button(this);
        button5.setLayoutParams(params);
        button5.setText("Boton 5");
        button5.setWidth(50);
        button5.setId(Integer.parseInt("24"));

        linearLayout2.addView(button5);

        Button button6 = new Button(this);
        button6.setLayoutParams(params);
        button6.setText("Boton 6");
        button6.setWidth(50);
        button6.setId(Integer.parseInt("24"));

        linearLayout2.addView(button6);*/

    }

    @SuppressLint("SimpleDateFormat")
    private class AsyncUbicheckDetails extends AsyncTask<String, Void, String> {
        UbicheckDetailsRequest UbicheckDetailsRequest;
        public AsyncUbicheckDetails(UbicheckDetailsRequest UbicheckDetailsRequest) {
            this.UbicheckDetailsRequest = UbicheckDetailsRequest;
        }
        @Override
        protected String doInBackground(String... params) {
            String resultado = "";
            try {
                JSONObject item = new JSONObject();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                item.put("UbicheckDetailID", UbicheckDetailsRequest.UbicheckDetailID);
                item.put("ElementID", UbicheckDetailsRequest.ElementID);
                item.put("DeviceID", UbicheckDetailsRequest.DeviceID);
                item.put("Date", ft.format(UbicheckDetailsRequest.Date));
                item.put("BiometricID", UbicheckDetailsRequest.BiometricID);
                resultado = ConnectionMethods.Post(Cursos.this,item.toString(), params[0],false);
            } catch (Exception e) {
                resultado = "";
            }
            return resultado;
        }
        @Override
        protected void onPostExecute(String result) {
            hideSpinner();
            try {
                if(result.equals("\"1\""))
                {
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    };
                    DialogMethods.showInformationDialog(Cursos.this, "Mensaje", "Curso registrado exitosamente",onClickListener);
                }else{
                    DialogMethods.showErrorDialog(Cursos.this, "Ocurrio un error al momento de utilizar Actividades Ubicheck. Result:" + result," Result:" + result + " Activity:Ubicheck | Method:AsyncUbicheckDetails");
                }
            } catch (Exception ex) {
                DialogMethods.showErrorDialog(Cursos.this, "Ocurrio un error al momento de utilizar Actividades Ubicheck. Result:" + result + " Info: " + ex.toString()," Result:" + result + " Activity:Ubicheck | Method:AsyncUbicheckDetails | Error:" + ex.toString());
            }
        }
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


    private void showSpinner(String Message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideSpinner() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (progressDialog !=null)
                    progressDialog.cancel();
            }
        });
    }
}
