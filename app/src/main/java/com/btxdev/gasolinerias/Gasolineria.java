package com.btxdev.gasolinerias;

import android.os.Parcel;
import android.os.Parcelable;

public class Gasolineria implements Parcelable {
    private long id;
    private String nombre;
    private String empresa;
    private String departamento;
    private String municipio;
    private String latitud;
    private String longitud;

    public Gasolineria(long id, String nombre, String empresa, String departamento, String municipio, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.empresa = empresa;
        this.departamento = departamento;
        this.municipio = municipio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    protected Gasolineria(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        empresa = in.readString();
        departamento = in.readString();
        municipio = in.readString();
        latitud = in.readString();
        longitud = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeString(empresa);
        dest.writeString(departamento);
        dest.writeString(municipio);
        dest.writeString(latitud);
        dest.writeString(longitud);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Gasolineria> CREATOR = new Creator<Gasolineria>() {
        @Override
        public Gasolineria createFromParcel(Parcel in) {
            return new Gasolineria(in);
        }

        @Override
        public Gasolineria[] newArray(int size) {
            return new Gasolineria[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
