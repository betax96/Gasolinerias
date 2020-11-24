package com.btxdev.gasolinerias;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.btxdev.gasolinerias.adapter.GenericAdapter;
import com.btxdev.gasolinerias.adapter.GasolineriaViewHolder;
import com.btxdev.gasolinerias.sqlite.GasolineriaDBController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GasolineriasActivity extends AppCompatActivity {

    private RecyclerView recMultas;
    private GenericAdapter<GasolineriaViewHolder> adapterMultas;
    private ArrayList<Gasolineria> listGasolinerias;
    private GasolineriaDBController gasolineriaDBController;
    private FloatingActionButton fabAdd;
    private GasolineriaDialog addGasolineriaDialog;
    private GasolineriaDialog viewGasolineriaDialog;

    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 56;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasolinerias);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        recMultas = findViewById(R.id.recMultas);
        fabAdd = findViewById(R.id.fabAdd);
        listGasolinerias = new ArrayList<>();
        gasolineriaDBController = new GasolineriaDBController(this);
        addGasolineriaDialog = new GasolineriaDialog(this);
        viewGasolineriaDialog = new GasolineriaDialog(this);

        addGasolineriaDialog.setActionListener(new GasolineriaDialog.ActionListener() {
            @Override
            public void onGuardar(int index, Gasolineria gasolineria) {
                Gasolineria gasolineriaWithId = gasolineriaDBController.add(gasolineria);
                if(gasolineriaWithId !=null){
                    listGasolinerias.add(gasolineriaWithId);
                    adapterMultas.notifyItemInserted(listGasolinerias.size()-1);
                }
                addGasolineriaDialog.close();
            }

            @Override
            public void onEliminar(int index, Gasolineria gasolineria) {

            }


            @Override
            public void onCancelar() {
                addGasolineriaDialog.close();
            }
        });

        viewGasolineriaDialog.setActionListener(new GasolineriaDialog.ActionListener() {
            @Override
            public void onGuardar(int index, Gasolineria gasolineriaEdit) {
                if(gasolineriaDBController.edit(gasolineriaEdit)){
                    Gasolineria gasolineria = listGasolinerias.get(index);
                    gasolineria.setNombre(gasolineriaEdit.getNombre());
                    gasolineria.setLongitud(gasolineriaEdit.getLongitud());
                    gasolineria.setLatitud(gasolineriaEdit.getLatitud());
                    gasolineria.setDepartamento(gasolineriaEdit.getDepartamento());
                    gasolineria.setMunicipio(gasolineriaEdit.getMunicipio());
                    gasolineria.setEmpresa(gasolineriaEdit.getEmpresa());
                    adapterMultas.notifyItemChanged(index);
                }
                viewGasolineriaDialog.close();
            }

            @Override
            public void onEliminar(int index, Gasolineria gasolineria) {
                if(gasolineriaDBController.remove(gasolineria.getId())){
                    listGasolinerias.remove(index);
                    adapterMultas.notifyItemRemoved(index);
                }
                viewGasolineriaDialog.close();
            }

            @Override
            public void onCancelar() {
                viewGasolineriaDialog.close();
            }
        });

        adapterMultas = new GenericAdapter<>(this, R.layout.view_holder_gasolineria, new GenericAdapter.InstanceCallback<GasolineriaViewHolder>() {
            @Override
            public GasolineriaViewHolder onCreateViewHolder(View view) {
                return new GasolineriaViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull final GasolineriaViewHolder viewHolder, final int i) {
                final Gasolineria gasolineria = listGasolinerias.get(viewHolder.getAdapterPosition());

                viewHolder.getTxtNombre().setText(gasolineria.getNombre());
                viewHolder.getTxtEmpresa().setText(gasolineria.getEmpresa());
                viewHolder.getTxtDepartamento().setText(gasolineria.getDepartamento());
                viewHolder.getTxtMunicipio().setText(gasolineria.getMunicipio());
                viewHolder.getTxtLatitud().setText(gasolineria.getLatitud());
                viewHolder.getTxtLongitud().setText(gasolineria.getLongitud());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewGasolineriaDialog.show(viewHolder.getAdapterPosition(), gasolineria);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return listGasolinerias.size();
            }
        });

        recMultas.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabAdd.isShown())
                    fabAdd.hide();
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fabAdd.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGasolineriaDialog.show();
            }
        });

        adapterMultas.setOn(recMultas, false);

        listGasolinerias = gasolineriaDBController.list();

        PermissionsUtil.requestLocationPermissions(this, REQUEST_CODE_LOCATION_PERMISSIONS);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gasolinerias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.itemMap){
            Intent intent = new Intent(this, GasolineriasMapActivity.class);
            intent.putExtra(GasolineriasMapActivity.EXTRA_GASOLINERIAS_LIST, listGasolinerias);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
