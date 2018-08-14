package app.first.my.registroabc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import app.first.my.registroabc.Business.ConnectionMethods;
import app.first.my.registroabc.Data.Device;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);
    Button btn_register_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Device Device = db.GetDevice();

        btn_register_device = (Button)findViewById(R.id.btn_register_device);
        btn_register_device.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RegisterDevice(view);
            }
        });
    }

    //================================================================================
    // Register Device
    //================================================================================

    public void RegisterDevice(View view){
        try
        {
            CheckBox checkTerms = (CheckBox) findViewById(R.id.checkTerms);
            if(checkTerms.isChecked()){
                if (ConnectionMethods.isInternetConnected(RegisterActivity.this, false).equals("")){
                    TextView txtCodigo = (TextView) findViewById(R.id.et_device_code);
                    TextView txtNombre = (TextView) findViewById(R.id.et_device_name);
                    String sCodigo = txtCodigo.getText().toString();
                    String sNombre = txtNombre.getText().toString();
                    if(!sCodigo.equals("")&& !sNombre.equals("")){
                        if(sCodigo.matches("[a-zA-Z0-9.? ]*") && sNombre.matches("[a-zA-Z0-9.? ]*")){
                            int Type = 3;
                            if(isTablet(getApplicationContext())){
                                Type = 4;
                            }
                            PackageInfo pInfo = null;
                            try {
                                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            } catch (PackageManager.NameNotFoundException e) {
                            }
                            String OSVersion = Build.VERSION.RELEASE;
                            String Model = getDeviceName();
                            Device DeviceVar = new Device(sNombre,sCodigo,String.valueOf(Type),0,1,1,0,0,0,0,0,0,0,0,0,"");
                            //Button btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
                            //btnCheckIn.setEnabled(false);
                            HttpAsyncTask httpAsyncTask = new HttpAsyncTask(DeviceVar,pInfo.versionName,Model,OSVersion);
                            httpAsyncTask.execute("/Devices/");
                        }else{
                            Toast.makeText(getBaseContext(), "No son permitidos Caracteres Especiales", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getBaseContext(), "Ingrese Nombre y Codigo", Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Toast.makeText(getBaseContext(), "Para continuar debe Aceptar los Terminos y Condiciones", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    //================================================================================
    // Check Device type
    //================================================================================

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    //================================================================================
    // Web Task
    //================================================================================

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        Device DeviceVar;
        String AppVersion;
        String Model;
        String OSVersion;
        public HttpAsyncTask(Device DeviceVar,String AppVersion,String Model,String OSVersion) {
            this.DeviceVar=DeviceVar;
            this.AppVersion = AppVersion;
            this.Model = Model;
            this.OSVersion = OSVersion;
        }
        @Override
        protected String doInBackground(String... params) {
            String resultado = "0";
            try {
                JSONObject item = new JSONObject();
                item.put("Name", DeviceVar.Name);
                item.put("Code", DeviceVar.Code);
                item.put("DeviceTypeID", DeviceVar.DeviceTypeID);
                item.put("AppVersion", AppVersion);
                item.put("Model", Model);
                item.put("OSVersion", OSVersion);
                resultado = ConnectionMethods.Post(RegisterActivity.this,item.toString(), params[0],false);
            } catch (Exception e) {
                resultado = "0";
            }
            return resultado;
        }
        @Override
        protected void onPostExecute(String result) {
            if(!result.equals("\"0\""))
            {
                try{
                    db.deleteDevice();
                    DeviceVar.DeviceID = Integer.parseInt(result.replace("\"", ""));
                    db.addDevice(DeviceVar);
                    Toast.makeText(getBaseContext(), "Dispositivo Registrado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(),app.first.my.registroabc.MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Exception", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getBaseContext(), "El codigo que ingreso es incorrecto", Toast.LENGTH_LONG).show();
            }
            /*Button btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
            btnCheckIn.setEnabled(true);*/
        }
    }


    //================================================================================
    // Get Device Information
    //================================================================================

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }
}
