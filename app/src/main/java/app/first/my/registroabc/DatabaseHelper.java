package app.first.my.registroabc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.List;

import app.first.my.registroabc.Data.Device;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "abcDB.db";
    public static final String TABLE_NAME = "persona";
    public static final String PERSONA_ID = "ID";
    public static final String PERSONA_NOMBRE = "NOMBRE";
    public static final String PERSONA_APPELLIDO = "APELLIDO";
    public static final String PERSONA_FECHADENAC = "FECHADENAC";
    public static final String PERSONA_CODIGO = "CODIGO";

    /*public static final String TABLE2_NAME = "registro";
    public static final String REGISTRO_ID = "ID";
    public static final String REGISTRO_FECHA = "FECHA";
    public static final String REGISTRO_HORA = "HORA";
    public static final String REGISTRO_DESC = "DESCRIPCION";
    public static final String REGISTRO_AREA = "AREA";

    public static final String TABLE3_NAME = "sustitucion";
    public static final String SUSTITUCION_ID = "ID";
    public static final String SUST_PERSONA_ID = "PERSONAID";
    public static final String SUST_REGISTRO_ID = "REGISTROID";

    public static final String TABLE4_NAME = "cursos";
    public static final String CURSO_ID = "ID";
    public static final String CURSO_NOMBRE = "NOMBRECURSO";*/




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_DEVICE_TABLE = "CREATE TABLE Devices ( " +
                "DeviceID INTEGER PRIMARY KEY , " +
                "Name TEXT, "+
                "Code TEXT, "+
                "DeviceTypeID TEXT, "+
                "Status INTEGER, " +
                "UsesFormSelection INTEGER, " +
                "UsesFormWithUbicheck INTEGER, " +
                "UsesClientValidation INTEGER, " +
                "UsesCreateBranch INTEGER, " +
                "UsesUbicheckDetails INTEGER, " +
                "UsesBiometric INTEGER, " +
                "UsesKioskMode INTEGER, " +
                "KioskBranchID INTEGER, " +
                "ImageWareRegister INTEGER, " +
                "BiometricID INTEGER, " +
                "Account TEXT)";

        String CREATE_BIOMETRICS_TABLE = "CREATE TABLE biometrics ( " +
                "BiometricID INTEGER PRIMARY KEY , " +
                "Name TEXT, " +
                "LastConecction INTEGER)";

        try {
            sqLiteDatabase.execSQL(CREATE_DEVICE_TABLE);
            sqLiteDatabase.execSQL(CREATE_BIOMETRICS_TABLE);

        } catch (Exception e){

        }
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOMBRE TEXT,APELLIDO TEXT,FECHADENAC DATE, CODIGO TEXT)");
        //sqLiteDatabase.execSQL("create table " + TABLE2_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,FECHA TEXT,HORA TEXT,DESCRIPCION TEXT,AREA TEXT)");
        //sqLiteDatabase.execSQL("create table " + TABLE3_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PERSONAID INTEGER,REGISTROID INTEGER,FOREIGN KEY (PERSONAID) REFERENCES persona(ID),FOREIGN KEY (REGISTROID) REFERENCES registro(ID))");
        //sqLiteDatabase.execSQL("create table " + TABLE4_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOMBRECURSO TEXT)");

        //SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSONA_ID, "1");
        contentValues.put(PERSONA_NOMBRE, "A_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "A_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "2");
        contentValues.put(PERSONA_NOMBRE, "B_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "B_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "3");
        contentValues.put(PERSONA_NOMBRE, "C_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "C_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "4");
        contentValues.put(PERSONA_NOMBRE, "D_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "D_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "5");
        contentValues.put(PERSONA_NOMBRE, "E_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "E_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "6");
        contentValues.put(PERSONA_NOMBRE, "F_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "F_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "7");
        contentValues.put(PERSONA_NOMBRE, "G_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "G_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "8");
        contentValues.put(PERSONA_NOMBRE, "H_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "H_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "9");
        contentValues.put(PERSONA_NOMBRE, "I_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "I_apellido1");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "10");
        contentValues.put(PERSONA_NOMBRE, "J_nombre1");
        contentValues.put(PERSONA_APPELLIDO, "J_apellido1");
        contentValues.put(PERSONA_FECHADENAC, "1988-08-13");
        contentValues.put(PERSONA_CODIGO, "010420136411604017708132");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        contentValues.put(PERSONA_ID, "11");
        contentValues.put(PERSONA_NOMBRE, "José Joaquín");
        contentValues.put(PERSONA_APPELLIDO, "Gallo");
        contentValues.put(PERSONA_FECHADENAC, "1988-08-13");
        contentValues.put(PERSONA_CODIGO, "010420136411604017708132");
        sqLiteDatabase.insert(TABLE_NAME, null,contentValues );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE3_NAME);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE4_NAME);
        onCreate(sqLiteDatabase);

    }

    /*public boolean insertData(String name, String surname){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSONA_NOMBRE, name);
        contentValues.put(PERSONA_APPELLIDO, surname);


        long result = sqLiteDatabase.insert(TABLE_NAME, null,contentValues );

        if (result == -1)
            return false;
        else
            return true;

    }*/


    public Cursor getAllData(){
        //Bundle bundle = Sustitucion2.
        //char vletra;
        //vletra = "a".;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE substr("+PERSONA_APPELLIDO+",1,2)="+vletra,null);
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        //Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE1_NAME+" where id is 1",null);

        return res;
    }

    public Device GetDevice() {
        String query = "SELECT Name,Code,DeviceTypeID,DeviceID,Status,UsesFormSelection,UsesFormWithUbicheck,UsesClientValidation,UsesCreateBranch,UsesUbicheckDetails,UsesBiometric,UsesKioskMode,KioskBranchID,ImageWareRegister,BiometricID,Account FROM Devices";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Device Device = null;
        if (cursor.moveToFirst()) {
            do {
                Device = new Device(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9),cursor.getInt(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),cursor.getInt(14),cursor.getString(15));
            } while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        db.close();
        return Device;
    }

    public void addDevice(Device Device){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", Device.Name);
        values.put("Code", Device.Code);
        values.put("DeviceID", Device.DeviceID);
        values.put("Status", Device.Status);
        values.put("DeviceTypeID", Device.DeviceTypeID);
        values.put("UsesFormSelection", Device.UsesFormSelection);
        values.put("UsesFormWithUbicheck", Device.UsesFormWithUbicheck);
        values.put("UsesClientValidation", Device.UsesClientValidation);
        values.put("UsesCreateBranch", Device.UsesCreateBranch);
        values.put("UsesUbicheckDetails", Device.UsesUbicheckDetails);
        values.put("UsesBiometric", Device.UsesBiometric);
        values.put("UsesKioskMode", Device.UsesKioskMode);
        values.put("KioskBranchID", Device.KioskBranchID);
        values.put("ImageWareRegister", Device.ImageWareRegister);
        values.put("BiometricID", Device.BiometricID);
        values.put("Account", Device.Account);
        db.insert("Devices",null,values);
        db.close();
    }

    public int updateDevice(Device Device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", Device.Name);
        values.put("Code", Device.Code);
        values.put("DeviceID", Device.DeviceID);
        values.put("Status", Device.Status);
        values.put("DeviceTypeID", Device.DeviceTypeID);
        values.put("UsesFormSelection", Device.UsesFormSelection);
        values.put("UsesFormWithUbicheck", Device.UsesFormWithUbicheck);
        values.put("UsesClientValidation", Device.UsesClientValidation);
        values.put("UsesCreateBranch", Device.UsesCreateBranch);
        values.put("UsesUbicheckDetails", Device.UsesUbicheckDetails);
        values.put("UsesBiometric", Device.UsesBiometric);
        values.put("UsesKioskMode", Device.UsesKioskMode);
        values.put("KioskBranchID", Device.KioskBranchID);
        values.put("ImageWareRegister", Device.ImageWareRegister);
        values.put("BiometricID", Device.BiometricID);
        values.put("Account", Device.Account);
        int i = db.update("Devices", values, "DeviceID"+" = ?",new String[] { String.valueOf(Device.DeviceID) });
        db.close();
        return i;
    }

    public void deleteDevice() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Devices");
        db.close();
    }

    public void deleteBiometrics() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM biometrics");
        db.close();
    }



    /*public boolean updateData(String id,String name, String surname){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSONA_ID, id);
        contentValues.put(PERSONA_NOMBRE, name);
        contentValues.put(PERSONA_APPELLIDO, surname);


        sqLiteDatabase.update(TABLE_NAME, contentValues,"ID = ?",new String[] {id});
        return true;
    }

    public Integer deleteData (String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSONA_ID, id);

        return sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[]{id});

    }*/
}

