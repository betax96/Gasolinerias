package com.btxdev.gasolinerias.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.btxdev.gasolinerias.R;

public class GasolineriaViewHolder extends GenericViewHolder {

    private TextView txtNombre, txtEmpresa, txtDepartamento, txtMunicipio, txtLatitud, txtLongitud;


    public GasolineriaViewHolder(@NonNull View itemView) {
        super(itemView);
        txtNombre = itemView.findViewById(R.id.txtNombre);
        txtEmpresa = itemView.findViewById(R.id.txtEmpresa);
        txtDepartamento = itemView.findViewById(R.id.txtDepartamento);
        txtMunicipio = itemView.findViewById(R.id.txtMunicipio);
        txtLatitud = itemView.findViewById(R.id.txtLat);
        txtLongitud = itemView.findViewById(R.id.txtLng);
    }

    public TextView getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(TextView txtNombre) {
        this.txtNombre = txtNombre;
    }

    public TextView getTxtEmpresa() {
        return txtEmpresa;
    }

    public void setTxtEmpresa(TextView txtEmpresa) {
        this.txtEmpresa = txtEmpresa;
    }

    public TextView getTxtDepartamento() {
        return txtDepartamento;
    }

    public void setTxtDepartamento(TextView txtDepartamento) {
        this.txtDepartamento = txtDepartamento;
    }

    public TextView getTxtMunicipio() {
        return txtMunicipio;
    }

    public void setTxtMunicipio(TextView txtMunicipio) {
        this.txtMunicipio = txtMunicipio;
    }

    public TextView getTxtLongitud() {
        return txtLongitud;
    }

    public void setTxtLongitud(TextView txtLongitud) {
        this.txtLongitud = txtLongitud;
    }

    public TextView getTxtLatitud() {
        return txtLatitud;
    }

    public void setTxtLatitud(TextView txtLatitud) {
        this.txtLatitud = txtLatitud;
    }
}
