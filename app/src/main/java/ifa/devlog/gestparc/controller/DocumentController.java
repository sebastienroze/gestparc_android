package ifa.devlog.gestparc.controller;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import ifa.devlog.gestparc.R;
import ifa.devlog.gestparc.model.Document;
import ifa.devlog.gestparc.utils.requestmanager.RequestManager;
import ifa.devlog.gestparc.utils.requestmanager.StringRequestWithToken;

public final class DocumentController {

    private static DocumentController instance = null;

    private DocumentController() {
    }

    public static DocumentController getInstance() {

        if(instance == null) {
            instance = new DocumentController();
        }

        return instance;
    }

    public interface SuccesEcouteur {
        void onSuccesSauvegarde();
    }

    public interface ErreurEcouteur {
        void onErreurSauvegarde(String messageErreur);
    }

    public void sauvegarder(
            Context context,
            Document document,
            SuccesEcouteur ecouteurSucces,
            ErreurEcouteur ecouteurErreur) {

    }

}
