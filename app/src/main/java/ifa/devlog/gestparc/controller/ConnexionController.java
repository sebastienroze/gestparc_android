package ifa.devlog.gestparc.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ifa.devlog.gestparc.utils.requestmanager.RequestManager;
import ifa.devlog.gestparc.R;
import ifa.devlog.gestparc.model.Utilisateur;

public class ConnexionController {

    Utilisateur utilisateurConnecte = null;
    Map<Integer, Utilisateur> mapUtilisateur = new HashMap<>();

    private static ConnexionController instance = null;

    private ConnexionController() {
        super();
    }

    public static ConnexionController getInstance() {
        if(instance == null) {
            instance = new ConnexionController();
        }

        return instance;
    }

    public interface TelechargementListeUtilisateurListener {
        void onListeUtilisateurEstTelecharge(List<Utilisateur> listeUtilisateur);
    }

    public interface UtilisateurConnecteSuccessListener {
        void onUtilisateurConnecteSucces();
    }

    public interface UtilisateurConnecteErrorListener {
        void onUtilisateurConnecteError(String messageErreur);
    }

    public interface SuccesEcouteur {
        void onSuccesConnexion();
    }

    public interface ErreurEcouteur {
        void onErreurConnexion(String messageErreur);
    }


    public void connexion(
            Context context,
            String login,
            String password,
            SuccesEcouteur ecouteurSucces,
            ErreurEcouteur ecouteurErreur
    ){

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("login", login);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.POST,  context.getResources().getString(R.string.url_spring)+ "authentication" ,
                        jsonBody,
                        token -> {
                            SharedPreferences preference = context.getSharedPreferences(
                                    context.getResources().getString(R.string.fichier_preference), 0); // 0 - for private mode
                            SharedPreferences.Editor editor = preference.edit();
                            String bearer= null;
                            try {
                                bearer = token.getString("bearer");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editor.putString("token", bearer); // Storing string
                            editor.apply();

                            ecouteurSucces.onSuccesConnexion();
                        },
                        error -> {
                             ecouteurErreur.onErreurConnexion("Impossible de se connecter");
                        }
                ){
            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

        };
        RequestManager.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void deconnexion(
            Context context,
            SuccesEcouteur ecouteurSucces
    ){
        SharedPreferences preference = context.getSharedPreferences(
                context.getResources().getString(R.string.fichier_preference), 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove("token"); // Storing string
        editor.apply();
        ecouteurSucces.onSuccesConnexion();
    }
}
