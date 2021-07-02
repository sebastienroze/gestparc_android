package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class Reparation implements Serializable {
    private Integer id;
    private Integer etat;
    private Materiel materiel;
    private String date_envoi;
    private String date_retour;
    private String etatCasse;
    private String etatRepare;

    public Reparation() {}

    public Reparation(Integer id, Integer etat,Materiel materiel,String date_envoi,
                      String date_retour,String etatCasse,String etatRepare
    ) {
        this.id = id;
        this.etat = etat;
        this.materiel = materiel;
        this.date_envoi = date_envoi;
        this.date_retour = date_retour;
        this.etatCasse = etatCasse;
        this.etatRepare = etatRepare;
    }

    public static List<Reparation> fromJsonArray(JSONArray jsonArray)   throws JSONException {
        List<Reparation> reparations = new ArrayList<Reparation>();
        for(int j = 0; j < jsonArray.length(); j++) {
            reparations.add(Reparation.fromJson(jsonArray.getJSONObject(j)));
        }
        return reparations;
    }

    public static Reparation fromJson(JSONObject json) throws JSONException {
        Materiel materiel = null;
        if (!json.isNull("materiel")) {
            JSONObject materielJSON = json.getJSONObject("materiel");
            materiel = Materiel.fromJson(materielJSON);
        }
        return new Reparation(
                json.getInt("id"),
                json.getInt("etat"),
                materiel,
                Util.getJsonString(json,"date_envoi"),
                Util.getJsonString(json,"date_retour"),
                Util.getJsonString(json,"etatCasse"),
                Util.getJsonString(json,"etatRepare")
        );
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public String getDate_envoi() {
        if (date_envoi==null) return "inconnu";
        return date_envoi;
    }

    public void setDate_envoi(String date_envoi) {
        this.date_envoi = date_envoi;
    }

    public String getDate_retour() {
        if (date_retour==null) return "inconnu";
        return date_retour;
    }

    public void setDate_retour(String date_retour) {
        this.date_retour = date_retour;
    }

    public String getEtatCasse() {
        if (etatCasse==null) return "";
        return etatCasse;
    }

    public void setEtatCasse(String etatCasse) {
        this.etatCasse = etatCasse;
    }

    public String getEtatRepare() {
        if (etatRepare==null) return "";
        return etatRepare;
    }

    public void setEtatRepare(String etatRepare) {
        this.etatRepare = etatRepare;
    }
}
