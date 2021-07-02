package ifa.devlog.gestparc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

import ifa.devlog.gestparc.model.Document;
import ifa.devlog.gestparc.utils.DownloadImageTask;

public class ActivityVoirDocument extends AppCompatActivity {
    Button buttonTourner;
    ImageView imageContenu;
    Document document=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_document);
        init();
    }

    private void init() {

        buttonTourner = findViewById(R.id.button_tourner_document);
        imageContenu = findViewById(R.id.imageView_document);

        if (getIntent().hasExtra("document")) {
            Serializable serializable = getIntent().getSerializableExtra("document");
            this.document = (Document) serializable;;
        }

        String Url = this.getResources().getString(R.string.url_spring) + "docs/document/fichier/";

        if (this.document.getOriginalFilename() !=null) {
            new DownloadImageTask(imageContenu)
                    .execute(Url +this.document.getId()+"/"+this.document.getOriginalFilename());
            buttonTourner.setOnClickListener(v -> {
                imageContenu.setRotation(imageContenu.getRotation()+90);
            });
        } else {
            buttonTourner.setOnClickListener(v -> {
                Toast.makeText(this, "Pas de d'image !", Toast.LENGTH_LONG).show();
            });
            buttonTourner.setAlpha(0.2f);
        }
    }

}