package ifa.devlog.gestparc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;

import java.util.List;

import ifa.devlog.gestparc.model.Materiel;
import ifa.devlog.gestparc.utils.requestmanager.JsonArrayRequestWithToken;
import ifa.devlog.gestparc.utils.requestmanager.RequestManager;
import ifa.devlog.gestparc.view.adapter.ListMaterielAdapter;

public class ActivityMateriels extends AppCompatActivity {

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_materiels);
        RecyclerView recyclerViewListeMateriels = findViewById(R.id.liste_materiel);
        List<Materiel> materiels = null;
        recyclerViewListeMateriels.setLayoutManager(new LinearLayoutManager(this));

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequestWithToken(
                this,
                Request.Method.GET,
                this.getResources().getString(R.string.url_spring) + "admin/materiels",
                null,
                jsonMateriels -> {
                    try {
                        recyclerViewListeMateriels.setAdapter(
                                new ListMaterielAdapter(Materiel.fromJsonArray(jsonMateriels),
                                        (materielClic) -> {

                                                Intent intent;
                                                intent= new Intent(this, ActivityDocuments.class);
                                                intent.putExtra("materiel", materielClic);
                                                startActivity(intent);
                                            }
                                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Erreur", error.toString())){
        };
        RequestManager.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}