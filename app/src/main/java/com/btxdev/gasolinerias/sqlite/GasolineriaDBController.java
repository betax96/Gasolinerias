package com.btxdev.gasolinerias.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.btxdev.gasolinerias.Gasolineria;

import java.util.ArrayList;
import java.util.List;

public class GasolineriaDBController {

    private SQLiteSimpleDatabase db;
    public static final String db_name = "gasolineria_db";

    public GasolineriaDBController(Context context){
        db = new SQLiteSimpleDatabase(context, db_name, TableGasolineria.create_query, 1);
    }

    public Gasolineria add(Gasolineria gasolineria){
        try{
            SQLiteDatabase sql = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TableGasolineria.col_nombre, gasolineria.getNombre());
            values.put(TableGasolineria.col_empresa, gasolineria.getEmpresa());
            values.put(TableGasolineria.col_departamento, gasolineria.getDepartamento());
            values.put(TableGasolineria.col_municipio, gasolineria.getMunicipio());
            values.put(TableGasolineria.col_latitud, gasolineria.getLatitud());
            values.put(TableGasolineria.col_longitud, gasolineria.getLongitud());
            long id = sql.insertOrThrow(TableGasolineria.table_name, null, values);
            gasolineria.setId(id);
            return gasolineria;
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return null;
        }
    }

    public boolean edit(Gasolineria gasolineria){
        try{
            SQLiteDatabase sql = db.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TableGasolineria.col_nombre, gasolineria.getNombre());
            values.put(TableGasolineria.col_empresa, gasolineria.getEmpresa());
            values.put(TableGasolineria.col_departamento, gasolineria.getDepartamento());
            values.put(TableGasolineria.col_municipio, gasolineria.getMunicipio());
            values.put(TableGasolineria.col_latitud, gasolineria.getLatitud());
            values.put(TableGasolineria.col_longitud, gasolineria.getLongitud());
            sql.update(TableGasolineria.table_name, values, TableGasolineria.col_id+"=?",new String[]{Long.toString(gasolineria.getId())});
            return true;
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return false;
        }
    }

    public boolean remove(long id){
        try{
            SQLiteDatabase sql = db.getWritableDatabase();
            sql.delete(TableGasolineria.table_name, TableGasolineria.col_id+"=?",new String[]{Long.toString(id)});
            return true;
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return false;
        }
    }

    public ArrayList<Gasolineria> list(){
        try{
            SQLiteDatabase sql = db.getReadableDatabase();
            Cursor cursor = sql.query(TableGasolineria.table_name, null, null,
                    null, null, null, null, null);
            ArrayList<Gasolineria> gasolinerias = new ArrayList<>();
            while(cursor.moveToNext()){
                long id = cursor.getLong(0);
                String nombre = cursor.getString(1);
                String empresa = cursor.getString(2);
                String departamento = cursor.getString(3);
                String municipio = cursor.getString(4);
                String latitud = cursor.getString(5);
                String longitud = cursor.getString(6);
                gasolinerias.add(new Gasolineria(id,nombre,empresa,departamento,municipio,latitud,longitud));
            }
            cursor.close();
            return gasolinerias;
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return null;
        }
    }

    public ArrayList<Gasolineria> listWithFilter(String column, String value){
        try{
            SQLiteDatabase sql = db.getReadableDatabase();
            Cursor cursor = sql.query(TableGasolineria.table_name, null, column+"=?",
                    new String[]{value}, null, null, null, null);
            ArrayList<Gasolineria> gasolinerias = new ArrayList<>();
            while(cursor.moveToNext()){
                long id = cursor.getLong(0);
                String nombre = cursor.getString(1);
                String empresa = cursor.getString(2);
                String departamento = cursor.getString(3);
                String municipio = cursor.getString(4);
                String latitud = cursor.getString(5);
                String longitud = cursor.getString(6);
                gasolinerias.add(new Gasolineria(id,nombre,empresa,departamento,municipio,latitud,longitud));
            }
            cursor.close();
            return gasolinerias;
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return null;
        }
    }


}
