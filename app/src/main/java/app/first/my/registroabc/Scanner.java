package app.first.my.registroabc;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Scanner extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 100;
    SurfaceView surfaceView;
    CameraSource camerasource;
    TextView textView;
    BarcodeDetector barcodeDetector;
    public String code;
    public String vName;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DatabaseHelper(this);

        surfaceView = (SurfaceView)findViewById(R.id.camerapreview);
        textView = (TextView)findViewById(R.id.textView);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS).build();

        camerasource = new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(640,480)
                //.setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setAutoFocusEnabled(true)
                .build();

        //Check cmera permisions to finish activity before moving forward
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Scanner.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            finish();

        }


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {


                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                    return;
                }
                    try {
                        camerasource.start(surfaceHolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    camerasource.stop();
            }
        });



        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barCode = detections.getDetectedItems();

                //TODO cambiar valor de "code" de fijo a dinamico
                final String value = barCode.valueAt(0).displayValue;

                Cursor res = myDb.getAllData();

                if (res.getCount() == 0){
                    //show message
                    showMessage("Error","No se encontraron datos");
                    return;
                }

                int i=0;
                int count=0;
                int bdCount;
                char first;

                bdCount = res.getCount();
                String[] aPersonas = new String[bdCount];
                String persona;
                String bcode;

                ArrayList<String> stringArrayList = new ArrayList<String>(bdCount);
                String[] stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);

                int flg=0;
                String bday;
                String dbBday="";

                while (res.moveToNext() && flg == 0){


                    persona = res.getString(2)+" "+res.getString(1);
                    bcode = res.getString(4);
                    bday = res.getString(3);

                    if (value.equals(bcode)){
                        vName = persona;
                        code = bcode;
                        dbBday = bday;
                        flg++;
                    }
                }



                if(flg == 0){

                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(100);
                            textView.setText("Su código de barras no se encuentra registrado.");


                            //finish();

                        }
                    });
                }

                //code = "7502000818751";
                String vCumple;
                vCumple="\n\n¡Feliz cumpleaños!";


                Date date = Calendar.getInstance().getTime();
                System.out.println("Current time => " + date);

                SimpleDateFormat df = new SimpleDateFormat("MM-dd");
                String formattedDate = df.format(date);

                dbBday = dbBday.substring(5,10);


                //TODO this should be inside an if block checking if date = DOB from database
                if (formattedDate.equals(dbBday))
                {
                    vName = vName + vCumple;
                }



                //if (barCode.size() !=0)
                    //if (value.equals(code))
                if (flg !=0)
                {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(100);
                            textView.setText(value);

                            //TODO Clean intent code
                            Intent inEntSal = getIntent();
                            inEntSal.putExtra("vName",vName);  // insert your extras here
                            setResult(Activity.RESULT_OK, inEntSal);
                            finish();

                        }
                    });
                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
