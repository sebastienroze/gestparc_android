package ifa.devlog.gestparc.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ifa.devlog.gestparc.ActivityMateriels;
import ifa.devlog.gestparc.ActivityReparations;
import ifa.devlog.gestparc.ActivityRetours;
import ifa.devlog.gestparc.R;

public class Activity_accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_accueil);

        Button btnMateriels = findViewById(R.id.button_materiels);
        btnMateriels.setOnClickListener( v -> {
            startActivity(new Intent(this, ActivityMateriels.class));
        });

        Button btnRetours = findViewById(R.id.button_retours);
        btnRetours.setOnClickListener( v -> {
            startActivity(new Intent(this, ActivityRetours.class));
        });
        Button btnReparations = findViewById(R.id.button_reparation);
        btnReparations.setOnClickListener( v -> {
            startActivity(new Intent(this, ActivityReparations.class));
        });
    }
}