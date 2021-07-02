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

import ifa.devlog.gestparc.model.Retour;
import ifa.devlog.gestparc.utils.requestmanager.JsonArrayRequestWithToken;
import ifa.devlog.gestparc.utils.requestmanager.RequestManager;
import ifa.devlog.gestparc.view.adapter.ListRetourAdapter;

public class ActivityRetours extends AppCompatActivity {

    @Override
    public void onResume(){
        super.onResume();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_retours);
        RecyclerView recyclerViewListeRetours = findViewById(R.id.liste_retour);
        List<Retour> retours = null;
        recyclerViewListeRetours.setLayoutManager(new LinearLayoutManager(this));

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequestWithToken(
                this,
                Request.Method.GET,
                this.getResources().getString(R.string.url_spring) + "admin/retours",
                null,
                jsonRetours -> {
                    try {
                        recyclerViewListeRetours.setAdapter(
                                new ListRetourAdapter(Retour.fromJsonArray(jsonRetours),
                                        (retourClic) -> {
                                                Intent intent;
                                                intent= new Intent(this, ActivityDocuments.class);
                                                intent.putExtra("retour", retourClic);
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