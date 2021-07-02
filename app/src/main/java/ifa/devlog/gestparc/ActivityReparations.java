package ifa.devlog.gestparc;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;

import java.util.List;

import ifa.devlog.gestparc.model.Reparation;
import ifa.devlog.gestparc.utils.requestmanager.JsonArrayRequestWithToken;
import ifa.devlog.gestparc.utils.requestmanager.RequestManager;
import ifa.devlog.gestparc.view.adapter.ListReparationAdapter;

public class ActivityReparations extends AppCompatActivity {

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_reparations);
        RecyclerView recyclerViewListeReparations = findViewById(R.id.liste_reparation);
        List<Reparation> reparations = null;
        recyclerViewListeReparations.setLayoutManager(new LinearLayoutManager(this));

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequestWithToken(
                this,
                Request.Method.GET,
                this.getResources().getString(R.string.url_spring) + "admin/reparations",
                null,
                jsonReparations -> {
                    try {
                        System.out.println(jsonReparations);
                        recyclerViewListeReparations.setAdapter(
                                new ListReparationAdapter(Reparation.fromJsonArray(jsonReparations),
                                        (reparationClic) -> {
                                                Intent intent;
                                                intent= new Intent(this, ActivityDocuments.class);
                                                intent.putExtra("reparation", reparationClic);
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