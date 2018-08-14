package app.first.my.registroabc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import app.first.my.registroabc.Business.ConnectionMethods;
import app.first.my.registroabc.Data.Device;

public class SplashScreenActivity extends AppCompatActivity {

    public DatabaseHelper db = new DatabaseHelper(SplashScreenActivity.this);
    private static TextView txtSplashMessage;


    //================================================================================
    // Create Activity
    //================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        txtSplashMessage = (TextView)findViewById(R.id.txtSplashMessage);
        myHandler.sendEmptyMessage(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(2);
                if (ContextCompat.checkSelfPermission(SplashScreenActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(SplashScreenActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(SplashScreenActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    int isPermited = 0;
                    ActivityCompat.requestPermissions(SplashScreenActivity.this,new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE},isPermited);
                }else{
                    ValidateDevice();
                }
            }
        }, 1000);
    }

    //================================================================================
    // Destroy Activity
    //================================================================================

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    //================================================================================
    // Assign Message
    //================================================================================

    private static final Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            final int what = msg.what;
            switch(what) {
                case 1: updateMessage("Cargando..."); break;
                case 2: updateMessage("Validando Permisos..."); break;
                case 3: updateMessage("Verificando Dispositivo..."); break;
                case 4: updateMessage("Enviando Informacion..."); break;
                case 5: updateMessage("Procesando Respuesta..."); break;
                case 6: updateMessage("Sin conexion..."); break;
                case 7: updateMessage("Eliminando Datos..."); break;
                case 8: updateMessage("Verificando Version..."); break;
                case 9: updateMessage("Solicitando Actualizacion..."); break;
            }
        }
    };

    private static void updateMessage(String strMessage) {
        txtSplashMessage.setText(strMessage);
    }

    //================================================================================
    // Request Permissions Result
    //================================================================================

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        ValidateDevice();
    }

    //================================================================================
    // Validate Device
    //================================================================================

    public void ValidateDevice(){
        myHandler.sendEmptyMessage(3);
        Device Device = db.GetDevice();
        int DeviceID = 0;
        boolean DeviceExists = false;
        String VersionName = "";
        PackageInfo pInfo = null;
        if(Device != null){
            DeviceExists = true;
            DeviceID = Device.DeviceID;
        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            VersionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        new AsyncCheckDevice(DeviceExists,VersionName).execute("/Devices?DeviceID=" + DeviceID + "&AppVersion=" + VersionName);
    }

    //================================================================================
    // Redirect to Home
    //================================================================================

    public void RedirectToHome(){
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    //================================================================================
    // Redirect to Register
    //================================================================================

    public void RedirectToRegister(){
        Intent i = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(i);
        finish();
    }

    //================================================================================
    // Device Check Method
    //================================================================================

    private class AsyncCheckDevice extends AsyncTask<String, Void, String> {
        Boolean DeviceExists;
        String VersionName;
        public AsyncCheckDevice(Boolean DeviceExists,String VersionName) {
            this.DeviceExists = DeviceExists;
            this.VersionName = VersionName;
        }
        @Override
        protected String doInBackground(String... urls) {
            myHandler.sendEmptyMessage(4);
            return ConnectionMethods.GET(SplashScreenActivity.this, urls[0]);
        }
        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            myHandler.sendEmptyMessage(5);
            if(result.startsWith("Error:")){
                myHandler.sendEmptyMessage(6);
                RedirectToHome();
            }else{
                try
                {
                    JSONObject JO = new JSONObject(result);
                    if(DeviceExists && !JO.getBoolean("IsValid"))
                    {
                        myHandler.sendEmptyMessage(7);
                        //db.deleteQuestionOptions();
                        //db.deleteQuestionSentences();
                        //db.deleteQuestions();
                        //db.deleteAllSurveys();
                        db.deleteDevice();
                        //db.deleteSelectedSurveys();
                        db.deleteBiometrics();
                        RedirectToRegister();
                    }else{
                        myHandler.sendEmptyMessage(8);
                        if(DeviceExists){
                            Device Device = db.GetDevice();
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
                                Device.BiometricID = 0;
                            }
                            if(JO.getBoolean("UsesKioskMode")){
                                Device.UsesKioskMode = 1;
                                Device.ImageWareRegister = 0;
                                Device.BiometricID = 0;
                            }else{
                                Device.UsesKioskMode = 0;
                                db.deleteBiometrics();
                                if(Device.BiometricID == 0){
                                    Device.ImageWareRegister = 0;
                                }
                            }
                            Device.KioskBranchID = JO.getInt("KioskBranchID");
                            Device.Account = JO.getString("Account");
                            Device.Name = JO.getString("Name");
                            db.updateDevice(Device);
                            RedirectToHome();
                        }
                        else {
                            RedirectToRegister();
                        }
                    }
                }
                catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    RedirectToHome();
                }
            }
        }
    }

    //================================================================================
    // Device Check Method
    //================================================================================

    public void ShowOldVersionWarning(){
        myHandler.sendEmptyMessage(9);
        if(!isFinishing()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Version obsoleta de iTT Formax Detectada")
                    .setMessage("Es necesario tener la ultima version de iTT Formax �Desea Actualizar Ahora?")
                    .setCancelable(false)
                    .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                            finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
