package app.first.my.registroabc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import app.first.my.registroabc.Business.ConnectionMethods;
import app.first.my.registroabc.Business.DialogMethods;
import app.first.my.registroabc.Data.Device;
import app.first.my.registroabc.Data.UbicheckRequest;

public class EntradaSalida extends AppCompatActivity {

    public DatabaseHelper db = new DatabaseHelper(EntradaSalida.this);
    private ProgressDialog progressDialog;

    public Button btnEntrada;
    public Button btnSalida;
    public TextView tvEmployeeName;
    public String txtAlertEnt;
    public String bcode;
    public String EmployeeName;
    public int EmployeeID;

    private int CheckInBranchID = 0;
    private int KioskBranchID = 0;
    private double Latitude = 0;
    private double Longitude = 0;
    private List<Integer> ElementsValue;
    private int OpenUbicheckDetailID = 0;

    Boolean vShift = false;
    Boolean vSust = false;
    Boolean vCurs = false;
    Boolean vHext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_salida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();


        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        };
        //pass data values
        vShift = bundle.getBoolean("vShift");
        vSust = bundle.getBoolean("vSust");
        vCurs = bundle.getBoolean("vCurs");
        vHext = bundle.getBoolean("vHext");


        txtAlertEnt = bundle.getString("txtAlertEnt");

        Intent inScan = new Intent(EntradaSalida.this,Scanner.class);


        //Start scanner
        startActivityForResult(inScan, 0);



        /*if (vCurs) {
            goCurList();

        }
        else if (vSust) {
            goSust();
        }
        else {
            alertEnt();
        }*/
        alertEnt();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 0:
                if (resultCode == Activity.RESULT_OK){
                    //Get message
                    //TODO remove text variable
                    //bcode = data.getStringExtra("code");


                    Device Device = db.GetDevice();
                    KioskBranchID = Device.KioskBranchID;
                    EmployeeID = data.getIntExtra("EmployeeID",0);
                    EmployeeName = data.getStringExtra("vName");
                    tvEmployeeName = (TextView)findViewById(R.id.tv_employee_name);
                    tvEmployeeName.setText("Empleado: " + EmployeeName);

                    showSpinner("Buscando registros");
                    UbicheckRequest UbicheckRequest = new UbicheckRequest(0, Device.DeviceID,new Date(),0,EmployeeID);
                    AsyncUbicheck AsyncUbicheck = new AsyncUbicheck(UbicheckRequest);
                    AsyncUbicheck.execute("/Ubicheck");

                }else{
                    finish();
                }
                break;
        }
    }


    //================================================================================
    // Web Services
    //================================================================================

    @SuppressLint({"SimpleDateFormat", "StaticFieldLeak"})
    private class AsyncUbicheck extends AsyncTask<String, Void, String> {
        UbicheckRequest UbicheckRequest;
        AsyncUbicheck(UbicheckRequest UbicheckRequest) {
            this.UbicheckRequest = UbicheckRequest;
        }
        @Override
        protected String doInBackground(String... params) {
            String resultado;
            try {
                JSONObject item = new JSONObject();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                item.put("Status", UbicheckRequest.Status);
                item.put("DeviceID", UbicheckRequest.DeviceID);
                item.put("Date", ft.format(UbicheckRequest.Date));
                item.put("BranchID", UbicheckRequest.BranchID);
                item.put("BiometricID", UbicheckRequest.BiometricID);
                resultado = ConnectionMethods.Post(EntradaSalida.this,item.toString(), params[0],false);
            } catch (Exception e) {
                resultado = "";
            }
            return resultado;
        }
        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            hideSpinner();
            try {
                if(!result.equals("\"\""))
                {
                    final Device Device = db.GetDevice();
                    btnEntrada = findViewById(R.id.btnEntrada);
                    btnSalida = findViewById(R.id.btnSalida);
                    final JSONObject JO = new JSONObject(result);
                    final int Status = JO.getInt("Status");
                    if(Status == 0)
                    {
                        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        };
                        DialogMethods.showInformationDialog(EntradaSalida.this, "Mensaje", "Error al buscar registros",onClickListener);
                    }
                    else if(Status == 1)
                    {
                        JSONArray jBranches = JO.getJSONArray("Branches");
                        boolean containsKioskBranch = false;
                        if(jBranches.length() > 0){
                            for (int j = 0; j < jBranches.length(); j++) {
                                JSONObject jBranch = jBranches.getJSONObject(j);
                                if(jBranch.getInt("BranchID") == KioskBranchID){
                                    containsKioskBranch = true;
                                    break;
                                }
                            }
                        }
                        if(containsKioskBranch){
                            CheckInBranchID = KioskBranchID;
                            btnEntrada.setVisibility(View.VISIBLE);
                            btnSalida.setVisibility(View.GONE);

                            showSpinner("Buscando coordenadas");
                            new AsyncBranchCoordinates().execute("/Branches?BranchID=" + CheckInBranchID);
                        }
                    }
                    else if(Status == 2)
                    {
                        if (vShift){
                            btnEntrada.setVisibility(View.GONE);
                            btnSalida.setVisibility(View.VISIBLE);
                        }
                        else if (vCurs) {

                            List<String> ElementsText = new ArrayList<String>();
                            String OpenUbicheckDetailName = "";
                            if(Device.UsesUbicheckDetails== 1){
                                if(JO.getInt("OpenUbicheckDetailID") == 0){

                                    JSONArray jElements = JO.getJSONArray("Elements");
                                    ElementsValue = new ArrayList<Integer>();
                                    if(jElements.length() > 0){
                                        for (int j = 0; j < jElements.length(); j++) {
                                            JSONObject jElement = jElements.getJSONObject(j);
                                            ElementsText.add(jElement.getString("Name"));
                                            ElementsValue.add(jElement.getInt("ElementID"));
                                        }
                                    }

                                }else{
                                    OpenUbicheckDetailName = JO.getString("OpenUbicheckDetailName");
                                    OpenUbicheckDetailID = JO.getInt("OpenUbicheckDetailID");
                                }
                            }

                            Intent inCurlist = new Intent(EntradaSalida.this,Cursos.class);

                            inCurlist.putExtra("EmployeeID",EmployeeID);
                            inCurlist.putExtra("ElementsText", (Serializable) ElementsText);
                            inCurlist.putExtra("ElementsValue", (Serializable) ElementsValue);
                            inCurlist.putExtra("OpenUbicheckDetailName",OpenUbicheckDetailName);
                            inCurlist.putExtra("OpenUbicheckDetailID",OpenUbicheckDetailID);

                            finish();
                            startActivity(inCurlist);
                        }
                    }
                    else if (Status == 3){
                        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (vShift){
                                    finish();
                                }
                                else if (vCurs){

                                    List<String> ElementsText = new ArrayList<String>();
                                    String OpenUbicheckDetailName = "";
                                    if(Device.UsesUbicheckDetails== 1){
                                        try {
                                            if(JO.getInt("OpenUbicheckDetailID") == 0){

                                                JSONArray jElements = JO.getJSONArray("Elements");
                                                ElementsValue = new ArrayList<Integer>();
                                                if(jElements.length() > 0){
                                                    for (int j = 0; j < jElements.length(); j++) {
                                                        JSONObject jElement = jElements.getJSONObject(j);
                                                        ElementsText.add(jElement.getString("Name"));
                                                        ElementsValue.add(jElement.getInt("ElementID"));
                                                    }
                                                }

                                            }else{
                                                OpenUbicheckDetailName = JO.getString("OpenUbicheckDetailName");
                                                OpenUbicheckDetailID = JO.getInt("OpenUbicheckDetailID");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    Intent inCurlist = new Intent(EntradaSalida.this,Cursos.class);

                                    inCurlist.putExtra("EmployeeID",EmployeeID);
                                    inCurlist.putExtra("ElementsText", (Serializable) ElementsText);
                                    inCurlist.putExtra("ElementsValue", (Serializable) ElementsValue);
                                    inCurlist.putExtra("OpenUbicheckDetailName",OpenUbicheckDetailName);
                                    inCurlist.putExtra("OpenUbicheckDetailID",OpenUbicheckDetailID);

                                    finish();
                                    startActivity(inCurlist);
                                }
                            }
                        };
                        DialogMethods.showInformationDialog(EntradaSalida.this, "Mensaje", "Registro enviado exitosamente",onClickListener);
                    }
                }
            } catch (Exception ex) {
                DialogMethods.showErrorDialog(EntradaSalida.this, "Ocurrio un error al momento de buscar registros. Result:" + result + " Info: " + ex.toString()," Result:" + result + " Activity:EntradaSalida | Method:AsyncUbicheck | Error:" + ex.toString());
            }
        }
    }

    private class AsyncBranchCoordinates extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return ConnectionMethods.GET(EntradaSalida.this,urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            hideSpinner();
            try
            {
                String[] coords = result.split(",");
                String lat = coords[0].replace("\"","");
                String lon = coords[1].replace("\"","");
                Latitude = Double.parseDouble(lat);
                Longitude = Double.parseDouble(lon);
            }
            catch (Exception ex) {
                DialogMethods.showErrorDialog(EntradaSalida.this, "Ocurrio un error al verificar las coordenadas. Info: " + ex.toString(), "Activity:EntradaSalida | Method:AsyncBranchCoordinates | Error:" + ex.toString());
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

                Device Device = db.GetDevice();

                showSpinner("Registrando entrada");
                UbicheckRequest UbicheckRequest = new UbicheckRequest(1, Device.DeviceID,new Date(),CheckInBranchID,EmployeeID);
                AsyncUbicheck AsyncUbicheck = new AsyncUbicheck(UbicheckRequest);
                AsyncUbicheck.execute("/Ubicheck");

                /*AlertDialog.Builder a_builder = new AlertDialog.Builder(EntradaSalida.this);
                a_builder.setMessage("Gracias por registrar su entrada "+ EmployeeName)
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

                alert.show();*/






            }
        });

        btnSalida = (Button)findViewById(R.id.btnSalida);
        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Device Device = db.GetDevice();

                showSpinner("Registrando salida");
                UbicheckRequest UbicheckRequest = new UbicheckRequest(2, Device.DeviceID,new Date(),0,EmployeeID);
                AsyncUbicheck AsyncUbicheck = new AsyncUbicheck(UbicheckRequest);
                AsyncUbicheck.execute("/Ubicheck");

                /*AlertDialog.Builder a_builder = new AlertDialog.Builder(EntradaSalida.this);
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

                alert.show();*/

            }
        });

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
