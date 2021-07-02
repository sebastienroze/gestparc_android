package ifa.devlog.gestparc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;


import java.util.List;

import ifa.devlog.gestparc.model.Document;
import ifa.devlog.gestparc.model.Materiel;
import ifa.devlog.gestparc.model.Reparation;
import ifa.devlog.gestparc.model.Retour;
import ifa.devlog.gestparc.utils.requestmanager.JsonArrayRequestWithToken;
import ifa.devlog.gestparc.utils.requestmanager.RequestManager;
import ifa.devlog.gestparc.view.adapter.ListDocumentAdapter;

public class ActivityDocuments extends AppCompatActivity {
    private Materiel materiel=null;
    private Reparation reparation=null;
    private Retour retour=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
    }

    @Override
    public void onResume(){
        super.onResume();
        init();
    }
    private void init() {
        setContentView(R.layout.activity_documents);
        initListe();
        initClicAjout();
    }

    private void initClicAjout() {
        Button buttonAjoutDocument = findViewById(R.id.button_ajouterDocument);
        buttonAjoutDocument.setOnClickListener(
                v-> {
                    Document document = new Document();
                    document.setMateriel(materiel);
                    document.setReparation(reparation);
                    document.setRetour(retour);
                    Intent intent;
                    intent= new Intent(this, ActivityEditionDocument.class);
                    intent.putExtra("document", document);
                    startActivity(intent);
                }
        );
    }
    private void initListe() {
        if (getIntent().hasExtra("materiel")) {
            this.materiel =(Materiel) getIntent().getSerializableExtra("materiel");
        }
        if (getIntent().hasExtra("reparation")) {
            this.reparation =(Reparation) getIntent().getSerializableExtra("reparation");
        }
        if (getIntent().hasExtra("retour")) {
            this.retour =(Retour) getIntent().getSerializableExtra("retour");
        }

        RecyclerView recyclerViewListeDocument = findViewById(R.id.liste_document);
        List<Document> documents = null;
        recyclerViewListeDocument.setLayoutManager(new LinearLayoutManager(this));

        String url = this.getResources().getString(R.string.url_spring) + "admin/documents/";
        if (materiel!=null) {url += "materiel/"+materiel.getId();}
        if (reparation!=null) {url += "reparation/"+reparation.getId();}
        if (retour!=null) {url += "retour/"+retour.getId();}
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequestWithToken(
                this,
                Request.Method.GET,
                url,
                null,
                jsonDocuments -> {
                    try {
                        recyclerViewListeDocument.setAdapter(
                                new ListDocumentAdapter(Document.fromJsonArray(jsonDocuments),
                                        (documentClic) -> {
                                            Intent intent;
                                            intent= new Intent(this, ActivityEditionDocument.class);
                                            intent.putExtra("document", documentClic);

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