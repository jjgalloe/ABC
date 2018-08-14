package app.first.my.registroabc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import app.first.my.registroabc.Business.ConnectionMethods;
import app.first.my.registroabc.Business.DialogMethods;
import app.first.my.registroabc.Data.Device;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db = new DatabaseHelper(this);

    Timer timer;
    TimerTask timerTask;
    static ProgressDialog progress;
    final Handler handler = new Handler();

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

        /*goShft();
        goCurs();
        goSust();
        goHext();*/
        Buttons(true);
        InitialVerification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //InitialVerification();
    }

    public void InitialVerification(){
        Device Device = db.GetDevice();
        //TextView txtfooter = (TextView) findViewById(R.id.txtfooter);
        if(Device != null){
            switch(Device.Status){
                case 1:
                    startTimer();
                    //txtfooter.setText("Dispositivo en espera de autorizacion");
                    break;
                case 2:
                    if(!progress.isShowing()){
                        Buttons(true);
                    }
                    break;
                case 3:
                    //txtfooter.setText("Dispositivo rechazado");
                    break;
                default:
                    break;
            }
        }
    }

    //================================================================================
    // Timer Methods
    //================================================================================

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1, 60000); //
    }

    public void stopTimer(View v) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(isConnected()){
                            Device Device = db.GetDevice();
                            String VersionName = "";
                            PackageInfo pInfo = null;
                            try {
                                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                VersionName = pInfo.versionName;
                            } catch (PackageManager.NameNotFoundException e) {

                            }
                            new AsyncCheckDevice().execute("/Devices?DeviceID=" + Device.DeviceID + "&AppVersion=" + VersionName);
                        }
                    }
                });
            }
        };
    }

    private class AsyncCheckDevice extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return ConnectionMethods.GET(MainActivity.this,urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            try
            {
                JSONObject JO = new JSONObject(result);
                switch(JO.getInt("StatusID")){
                    case 1:
                        Buttons(true);
                        break;
                    case 2:
                        Device Device = db.GetDevice();
                        Device.Status = 2;
                        if(JO.getBoolean("UsesFormSelection")){
                            Device.UsesFormSelection = 1;
                        }else{
                            Device.UsesFormSelection = 0;
                        }
                        if(JO.getBoolean("UsesFormWithUbicheck")){
                            Device.UsesFormWithUbicheck = 1;
                        }else{
                            Device.UsesFormWithUbicheck = 0;
                        }
                        if(JO.getBoolean("UsesClientValidation")){
                            Device.UsesClientValidation = 1;
                        }else{
                            Device.UsesClientValidation = 0;
                        }
                        if(JO.getBoolean("UsesCreateBranch")){
                            Device.UsesCreateBranch = 1;
                        }else{
                            Device.UsesCreateBranch = 0;
                        }
                        if(JO.getBoolean("UsesUbicheckDetails")){
                            Device.UsesUbicheckDetails = 1;
                        }else{
                            Device.UsesUbicheckDetails = 0;
                        }
                        if(JO.getBoolean("UsesBiometric")){
                            Device.UsesBiometric = 1;
                        }else{
                            Device.UsesBiometric = 0;
                        }
                        if(JO.getBoolean("UsesKioskMode")){
                            Device.UsesKioskMode = 1;
                        }else{
                            Device.UsesKioskMode = 0;
                        }
                        Device.KioskBranchID = JO.getInt("KioskBranchID");
                        Device.Account = JO.getString("Account");
                        Device.Name = JO.getString("Name");
                        db.updateDevice(Device);
                        //TextView txtfooter = (TextView) findViewById(R.id.txtfooter);
                        //txtfooter.setText("");
                        Buttons(true);
                        break;
                    case 3:
                        db.deleteDevice();
                        /*TextView txtSelectedSurveyHeader = (TextView) findViewById(R.id.txtSelectedSurveyHeader);
                        TextView txtSelectedSurveyText = (TextView) findViewById(R.id.txtSelectedSurveyText);
                        txtSelectedSurveyHeader.setText("");
                        txtSelectedSurveyText.setText("");*/
                        Intent intent = new Intent(getBaseContext(),app.first.my.registroabc.RegisterActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
            catch (Exception ex) {
                DialogMethods.showErrorDialog(MainActivity.this, "Ocurrio un error al momento de checar dispositivo. Info: " + ex.toString(), "Activity:Home | Method:AsyncCheckDevice | Error:" + ex.toString());
            }
        }
    }

    //================================================================================
    // Helpers
    //================================================================================

    public boolean isConnected(){
        if (ConnectionMethods.isInternetConnected(this,false).equals("")){
            /*TextView txtConnection = (TextView) findViewById(R.id.txtConnection);
            txtConnection.setText("Conectado");
            txtConnection.setTextColor(Color.rgb(71, 164, 71));*/
            return true;
        }
        else {
            /*TextView txtConnection = (TextView) findViewById(R.id.txtConnection);
            txtConnection.setText("Sin Conexion");
            txtConnection.setTextColor(Color.RED);*/
            return false;
        }
    }

    public void Buttons(boolean show){
        Toast.makeText(MainActivity.this, "Botones", Toast.LENGTH_SHORT).show();
        Button btnShift = (Button) findViewById(R.id.btnShift);
        Button btnCursos = (Button) findViewById(R.id.btnCursos);
        Button btnSust = (Button) findViewById(R.id.btnSust);
        Button btnExtra = (Button) findViewById(R.id.btnExtra);

        Device Device = db.GetDevice();
        boolean isAuthorized = false;
        if(Device != null){
            if(Device.Status == 2){
                isAuthorized = true;
            }
        }


        if(isAuthorized)
        {
            btnShift.setEnabled(true);
            btnCursos.setEnabled(true);
            btnSust.setEnabled(true);
            btnExtra.setEnabled(true);

            btnShift.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            btnCursos.setBackgroundColor(getResources().getColor(R.color.colorCyan));
            btnSust.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnExtra.setBackgroundColor(getResources().getColor(R.color.colorOrange));

            goShft();
            goCurs();
            goSust();
            goHext();
        }
        else
        {
            btnShift.setEnabled(false);
            btnCursos.setEnabled(false);
            btnSust.setEnabled(false);
            btnExtra.setEnabled(false);

            btnShift.setBackgroundColor(getResources().getColor(R.color.lightGray));
            btnCursos.setBackgroundColor(getResources().getColor(R.color.lightGray));
            btnSust.setBackgroundColor(getResources().getColor(R.color.lightGray));
            btnExtra.setBackgroundColor(getResources().getColor(R.color.lightGray));
        }
    }
}
