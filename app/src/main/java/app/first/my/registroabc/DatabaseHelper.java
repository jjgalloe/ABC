package app.first.my.registroabc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.List;

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

