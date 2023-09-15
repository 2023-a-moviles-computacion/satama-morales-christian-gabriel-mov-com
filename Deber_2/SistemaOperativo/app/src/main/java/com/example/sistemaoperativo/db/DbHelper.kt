package com.example.sistemaoperativo.db

import android.annotation.SuppressLint
import android.app.DownloadManager.Query
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.example.sistemaoperativo.Dispositivo
import com.example.sistemaoperativo.Models.ControladorModel
import com.example.sistemaoperativo.Models.DispositivoModel
import java.io.Serializable
import java.lang.Exception


class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "sistema.db"
        const val TABLE_CONTROLADOR = "T_Controladores"
        const val TABLE_DISPOSITIVO = "T_Dispositivos"

        private val QUERY_T_CONTROLADOR =
            "CREATE TABLE "+ "T_Controladores" +
                    "( identificador INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL," +
                    "lastFechaInstalacion TEXT  NOT NULL,"+
                    "version REAL NOT NULL,"+
                    "isActive INTEGER NOT NULL)"

        private val QUERY_T_DISPOSITIVO = "CREATE TABLE T_Dispositivos ( " +
                "    identificador INTEGER PRIMARY KEY," +
                "    nombre TEXT NOT NULL," +
                "    puertoUsed TEXT NOT NULL," +
                "    estado INTEGER NOT NULL," +
                "    upTime REAL NOT NULL," +
                "    idControlador INTEGER," +
                "    FOREIGN KEY(idControlador) REFERENCES T_Controladores(identificador) " +
                ");"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblControlador = (QUERY_T_CONTROLADOR)
        val createTblDispositivo = (QUERY_T_DISPOSITIVO)
        db?.execSQL(createTblControlador)
        db?.execSQL(createTblDispositivo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE " + TABLE_CONTROLADOR)
        db.execSQL("DROP TABLE " + TABLE_DISPOSITIVO)
        onCreate(db)
    }
    fun insertarControlador(std: ControladorModel): Long{
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("identificador", std.identificador)
        values.put("nombre", std.nombre)
        values.put("lastFechaInstalacion", std.lastFechaInstalacion)
        values.put("version", std.version)
        values.put("isActive", std.isActive)

        val id = db.insert(TABLE_CONTROLADOR, null, values)
        db.close()

        return id
    }
    fun insertarDispositivo(std: DispositivoModel): Long{
        val db = this.writableDatabase

        val values = ContentValues()
        values.put("identificador", std.identificador)
        values.put("nombre", std.nombre)
        values.put("puertoUsed", std.puertoUsed)
        values.put("estado", std.estado)
        values.put("upTime", std.upTime)
        values.put("idControlador", std.idControladorFk)

        val id = db.insert(TABLE_DISPOSITIVO, null, values)
        db.close()

        return id
    }
    @SuppressLint("Range")
    fun getAllControladores(): ArrayList<ControladorModel>{
        val stdList: ArrayList<ControladorModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_CONTROLADOR"
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var identificador: Int
        var nombre: String
        var lastFechaInstalacion: String
        var version: Double
        var isActive: Int

        if (cursor.moveToFirst()){
            do {
                identificador = cursor.getInt(cursor.getColumnIndex("identificador"))
                nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                lastFechaInstalacion = cursor.getString(cursor.getColumnIndex("lastFechaInstalacion"))
                version = cursor.getDouble(cursor.getColumnIndex("version"))
                isActive = cursor.getInt(cursor.getColumnIndex("isActive"))
                val std = ControladorModel(identificador = identificador,nombre = nombre,lastFechaInstalacion = lastFechaInstalacion,version = version,isActive = isActive)
                stdList.add(std)
            }while (cursor.moveToNext())
        }
        return stdList
    }
    @SuppressLint("Range")
    fun getControlador(id:Int): ControladorModel? {
       val selectQuery = "SELECT * FROM "+ TABLE_CONTROLADOR+" where identificador= ?"
        val db = this.writableDatabase
        db.rawQuery(selectQuery, arrayOf(id.toString())).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = ControladorModel()
                result.identificador = it.getInt(it.getColumnIndex("identificador"))
                result.nombre = it.getString(it.getColumnIndex("nombre"))
                result.lastFechaInstalacion = it.getString(it.getColumnIndex("lastFechaInstalacion"))
                result.version = it.getString(it.getColumnIndex("version")).toDouble()
                result.isActive = it.getString(it.getColumnIndex("isActive")).toInt()
                return result
            }
        }
        return null
    }

    @SuppressLint("Range")
    fun getDispositivo(id: Int): DispositivoModel?{
        val selectQuery = "SELECT * FROM "+ TABLE_DISPOSITIVO+" where identificador= ?"
        val db = this.writableDatabase
        db.rawQuery(selectQuery, arrayOf(id.toString())).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = DispositivoModel()
                result.identificador = it.getInt(it.getColumnIndex("identificador"))
                result.nombre = it.getString(it.getColumnIndex("nombre"))
                result.puertoUsed = it.getString(it.getColumnIndex("puertoUsed"))
                result.estado = it.getInt(it.getColumnIndex("estado"))
                result.upTime = it.getDouble(it.getColumnIndex("upTime"))
                return result
            }
        }
        return null
    }
    @SuppressLint("Range")
    fun getDispositivoByControladorId(idControlador:Int): ArrayList<DispositivoModel> {
        //val selectQuery = "SELECT * FROM "+ TABLE_DISPOSITIVO+" JOIN "+ TABLE_CONTROLADOR+" ON "+TABLE_DISPOSITIVO+".identificador = "+TABLE_CONTROLADOR+".identificador"
        val selectQuery = "SELECT * FROM T_Dispositivos JOIN T_Controladores ON T_Dispositivos.identificador = T_Controladores.identificador"

        /*SELECT Lists.Id, Lists.Name, Items.ItemName FROM Lists JOIN Items ON Lists.Id = Items.IdList*/
        val db = this.readableDatabase
        val dispositivosList: ArrayList<DispositivoModel> = ArrayList()

        db.rawQuery(selectQuery,null).use {
            if (it.moveToFirst()){
                do {

                    val result = DispositivoModel()
                    result.identificador = it.getInt(it.getColumnIndex("identificador"))
                    result.nombre = it.getString(it.getColumnIndex("nombre"))
                    result.puertoUsed = it.getString(it.getColumnIndex("puertoUsed"))
                    result.estado = it.getInt(it.getColumnIndex("estado"))
                    result.upTime = it.getDouble(it.getColumnIndex("upTime"))
                    dispositivosList.add(result)
                }while (it.moveToNext())
            }
        }

        return dispositivosList
    }
    @SuppressLint("Range")
    fun getAllDispositivos(): ArrayList<DispositivoModel>{
        val stdList: ArrayList<DispositivoModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_DISPOSITIVO"
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var identificador: Int
        var nombre: String
        var puertoUsed: String
        var estado: Int
        var upTime: Double
        var idControladorFk: Int

        if (cursor.moveToFirst()){
            do {
                identificador = cursor.getInt(cursor.getColumnIndex("identificador"))
                nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                puertoUsed = cursor.getString(cursor.getColumnIndex("puertoUsed"))
                estado = cursor.getInt(cursor.getColumnIndex("estado"))
                upTime = cursor.getDouble(cursor.getColumnIndex("upTime"))
                //idControladorFk = cursor.getInt(cursor.getColumnIndex("idControlador"))
                val std = DispositivoModel(identificador = identificador,nombre = nombre,puertoUsed = puertoUsed,estado = estado,upTime = upTime,idControladorFk = 100 )
                stdList.add(std)
            }while (cursor.moveToNext())
        }
        return stdList
    }
    fun updateControlador(controlador: ControladorModel): Int {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put("identificador", controlador.identificador)
        values.put("nombre", controlador.nombre)
        values.put("lastFechaInstalacion", controlador.lastFechaInstalacion)
        values.put("version", controlador.version)
        values.put("isActive", controlador.isActive)

        val succes = db.update(TABLE_CONTROLADOR,values,"identificador = ?",arrayOf(controlador.identificador.toString()))
        db.close()
        return succes
    }
    fun updateDispositivo(dispositivo: DispositivoModel): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("identificador", dispositivo.identificador)
        values.put("nombre", dispositivo.nombre)
        values.put("puertoUsed", dispositivo.puertoUsed)
        values.put("estado", dispositivo.estado)
        values.put("upTime", dispositivo.upTime)
        values.put("idControlador", dispositivo.idControladorFk)

        val success = db.update(TABLE_DISPOSITIVO,values,"identificador = ?",arrayOf(dispositivo.identificador.toString()))
        db.close()
        return success

    }

    fun deleteControlador(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("identificador", id)

        val success = db.delete(TABLE_CONTROLADOR,"identificador = ?",arrayOf(id.toString()))
        db.close()
        return success
    }
    fun deleteDispositivo(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("identificador", id)

        val success = db.delete(TABLE_DISPOSITIVO, "identificador = ?", arrayOf(id.toString()))
        db.close()
        return success
    }
}