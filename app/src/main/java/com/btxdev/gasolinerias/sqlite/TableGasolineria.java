package com.btxdev.gasolinerias.sqlite;


    public class TableGasolineria {

        public static final String table_name = "gasolineria";
        public static final String col_id = "id";
        public static final String col_nombre = "nombre";
        public static final String col_empresa = "empresa";
        public static final String col_departamento = "departamento";
        public static final String col_municipio = "municipio";
        public static final String col_latitud = "latitud";
        public static final String col_longitud = "longitud";

        public static final String create_query = "CREATE TABLE IF NOT EXISTS " + TableGasolineria.table_name + " ( " +
                TableGasolineria.col_id +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TableGasolineria.col_nombre + " TEXT," +
                TableGasolineria.col_empresa + " TEXT," +
                TableGasolineria.col_departamento + " TEXT," +
                TableGasolineria.col_municipio + " TEXT," +
                TableGasolineria.col_latitud + " TEXT," +
                TableGasolineria.col_longitud + " TEXT" +
                ");";


}
