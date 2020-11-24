package com.btxdev.gasolinerias;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

public class GasolineriaDialog extends AppCompatDialog {
    private AppCompatActivity activity;
    public GasolineriaDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    private TextInputLayout tilNombre, tilEmpresa, tilDepartamento, tilMunicipio, tilLatitud, tilLongitud;
    private Button btnGuardar, btnCancelar, btnEliminar;
    private ActionListener actionListener;
    private Gasolineria gasolineria;
    private int index;
    private MapDialog mapDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gasolineria);

        mapDialog = new MapDialog(activity);

        tilNombre = findViewById(R.id.tilNombre);
        tilEmpresa = findViewById(R.id.tilEmpresa);
        tilDepartamento = findViewById(R.id.tilDepartamento);
        tilMunicipio = findViewById(R.id.tilMunicipio);
        tilLatitud = findViewById(R.id.tilLat);
        tilLongitud = findViewById(R.id.tilLng);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);

        tilLongitud.getEditText().setInputType(InputType.TYPE_NULL);
        tilLatitud.getEditText().setInputType(InputType.TYPE_NULL);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionListener !=null){
                    actionListener.onCancelar();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = tilNombre.getEditText().getText().toString();
                String empresa = tilEmpresa.getEditText().getText().toString();
                String departamento = tilDepartamento.getEditText().getText().toString();
                String municipio = tilMunicipio.getEditText().getText().toString();
                String latitud = tilLatitud.getEditText().getText().toString();
                String longitud = tilLongitud.getEditText().getText().toString();

                if(!TextUtils.isEmpty(nombre)&&!TextUtils.isEmpty(empresa)&&
                !TextUtils.isEmpty(departamento)&&!TextUtils.isEmpty(municipio)&&
                !TextUtils.isEmpty(latitud)&&!TextUtils.isEmpty(longitud)){

                    long id = 0;
                    if(gasolineria !=null){
                        id = gasolineria.getId();
                    }

                    if(actionListener!=null){
                        actionListener.onGuardar(index, new Gasolineria(id, nombre,empresa,departamento,municipio,latitud,longitud));
                    }
                }else{
                    Toast.makeText(getContext(), R.string.err_campo_invalido,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionListener !=null){
                    actionListener.onEliminar(index, gasolineria);
                }
            }
        });

        tilLatitud.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        tilLongitud.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getLocation();
            }
        });
    }

    private void getLocation(){
        Toast.makeText(getContext(),R.string.msg_select_location,Toast.LENGTH_SHORT).show();
        LatLng latLng = null;
        try {
            double lat = Double.parseDouble(tilLatitud.getEditText().getText().toString());
            double lng = Double.parseDouble(tilLongitud.getEditText().getText().toString());
            latLng = new LatLng(lat,lng);
        }catch (Exception ignore){}

        mapDialog.getMarkerLocation(latLng, new MapDialog.MarkerLocationCallback() {
            @Override
            public void onDismiss(LatLng markerLocation) {
                if(markerLocation!=null){
                    tilLatitud.getEditText().setText(Double.toString(markerLocation.latitude));
                    tilLongitud.getEditText().setText(Double.toString(markerLocation.longitude));
                }
            }
        });
    }

    @Override
    public void show() {
        this.gasolineria = null;
        this.index = -1;
        super.show();
        btnEliminar.setVisibility(View.INVISIBLE);
        tilLatitud.getEditText().setText("");
        tilLongitud.getEditText().setText("");
        tilMunicipio.getEditText().setText("");
        tilDepartamento.getEditText().setText("");
        tilEmpresa.getEditText().setText("");
        tilNombre.getEditText().setText("");
        tilNombre.requestFocus();
    }

    public void close(){
        this.gasolineria = null;
        this.index = -1;
        tilLatitud.getEditText().setText("");
        tilLongitud.getEditText().setText("");
        tilMunicipio.getEditText().setText("");
        tilDepartamento.getEditText().setText("");
        tilEmpresa.getEditText().setText("");
        tilNombre.getEditText().setText("");
        cancel();
    }

    public void show(int index, Gasolineria gasolineria){
        this.gasolineria = gasolineria;
        this.index = index;
        super.show();
        btnEliminar.setVisibility(View.VISIBLE);
        tilLatitud.getEditText().setText(gasolineria.getLatitud());
        tilLongitud.getEditText().setText(gasolineria.getLongitud());
        tilNombre.getEditText().setText(gasolineria.getNombre());
        tilDepartamento.getEditText().setText(gasolineria.getDepartamento());
        tilMunicipio.getEditText().setText(gasolineria.getMunicipio());
        tilEmpresa.getEditText().setText(gasolineria.getEmpresa());
        tilNombre.requestFocus();
    }

    interface ActionListener {
        void onGuardar(int index, Gasolineria gasolineria);
        void onEliminar(int index, Gasolineria gasolineria);
        void onCancelar();
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
