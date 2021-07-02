package ifa.devlog.gestparc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ifa.devlog.gestparc.R;
import ifa.devlog.gestparc.controller.ConnexionController;
import ifa.devlog.gestparc.utils.JWTUtils;

public class LoginActivity extends AppCompatActivity {
    private boolean loginParToken = true;
    TextView textViewLogin;
    TextView textViewPassword;
    Button boutonConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginParToken = true;
    }
    private void startLogin() {
        setContentView(R.layout.activity_login);
        textViewLogin = findViewById(R.id.login);
        textViewPassword = findViewById(R.id.password);
        boutonConnexion = findViewById(R.id.button_connexion);

        boutonConnexion.setOnClickListener((View v) -> {
            ConnexionController.getInstance().connexion(
                    this,
                    textViewLogin.getText().toString(),
                    textViewPassword.getText().toString(),
                    () -> startActivity(new Intent(this, Activity_accueil.class)),
//                    () -> startActivity(new Intent(this, ListeQuestionActivity.class)),
                    (String messageErreur) -> Toast.makeText(this, messageErreur, Toast.LENGTH_LONG).show()
            );
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        if (loginParToken && JWTUtils.isTokenValide(this)) {
            startActivity(new Intent(this, Activity_accueil.class));
        } else {
            startLogin();
        }
        loginParToken = false;
    }
}
