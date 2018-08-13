package app.first.my.registroabc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class HorasExtraManuales extends AppCompatActivity {

    Button btnFechaIni;
    Button btnHoraIni;
    Button btnNumHoras;
    Button btnSubmit;
    TextView txtFechaIni;
    TextView txtHoraIni;
    TextView txtNumHoras;


    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    NumberPicker numberPicker;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int currentDate;
    int currentMonth;
    int currentYear;
    String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_extra_manuales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTime();
        setDate();
        setHours();
        sbmtHrs();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void setTime(){

        txtHoraIni = findViewById(R.id.txtHoraIni);
        btnHoraIni = findViewById(R.id.btnHoraIni);
        btnHoraIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog  = new TimePickerDialog(HorasExtraManuales.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12){
                            amPm = " PM";
                        }else {
                            amPm = " AM";
                        }

                        txtHoraIni.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                        //txtFechaIni.setText(hourOfDay + ":" + minutes + amPm);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
            }
        });

    }

    public void setDate(){

        txtFechaIni = findViewById(R.id.txtFechaIni);
        btnFechaIni = findViewById(R.id.btnFechaIni);
        btnFechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentYear = calendar.get(Calendar.YEAR);
                currentDate = calendar.get(Calendar.DATE);
                currentMonth = calendar.get(Calendar.MONTH);


                datePickerDialog  = new DatePickerDialog(HorasExtraManuales.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        //txtFechaIni.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                        int realmonth;
                        realmonth  = month +1;
                        txtFechaIni.setText(day + "/" + realmonth + "/" + year);
                    }
                },currentYear,currentMonth,currentDate);
                datePickerDialog.show();
            }
        });

    }

    public void setHours(){
        txtNumHoras = findViewById(R.id.txtNumHoras);
        btnNumHoras = findViewById(R.id.btnNumHoras);
        btnNumHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 numberPicker = new NumberPicker(HorasExtraManuales.this);
                numberPicker.setMaxValue(24);
                 numberPicker.setMinValue(0);
                 NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
                     @Override
                     public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                         txtNumHoras.setText(""+newVal);

                     }
                 };
                 numberPicker.setOnValueChangedListener(myValChangedListener);
                AlertDialog.Builder builder = new AlertDialog.Builder(HorasExtraManuales.this).setView(numberPicker);
                builder.setTitle("Número de horas extra");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();

            }
        });

    }

    public void sbmtHrs(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
