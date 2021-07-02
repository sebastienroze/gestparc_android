package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class Location implements Serializable {
    private Integer id;
//    private Cadre cadre;
    private Integer etat;
    private String date_debut;
    private String date_retour;
    private Materiel materiel;
    private TypeMateriel typeMateriel;
    private Utilisateur utilisateur;

    public Location() {}

    public Location(Integer id, Integer etat,  String date_debut,String date_retour,
                    TypeMateriel typeMateriel, Materiel materiel,Utilisateur utilisateur
    ) {
        this.id = id;
        this.etat = etat;
        this.date_debut = date_debut;
        this.date_retour = date_retour;
        this.typeMateriel = typeMateriel;
        this.materiel = materiel;
        this.utilisateur = utilisateur;
    }

    public static List<Location> fromJsonArray(JSONArray jsonArray)   throws JSONException {
        List<Location> locations = new ArrayList<Location>();
        for(int j = 0; j < jsonArray.length(); j++) {
            locations.add(Location.fromJson(jsonArray.getJSONObject(j)));
        }
        return locations;
    }

    public static Location fromJson(JSONObject json) throws JSONException {
        Materiel materiel = null;
        if (!json.isNull("materiel")) {
            JSONObject materielJSON = json.getJSONObject("materiel");
            materiel = Materiel.fromJson(materielJSON);
        }
        TypeMateriel typeMateriel = null;
        if (!json.isNull("typeMateriel")) {
            JSONObject typeMaterielJSON = json.getJSONObject("typeMateriel");
            typeMateriel = TypeMateriel.fromJson(typeMaterielJSON);
        }
        Utilisateur utilisateur = null;
        if (!json.isNull("utilisateur")) {
            JSONObject utilisateurJSON = json.getJSONObject("utilisateur");
            utilisateur = Utilisateur.fromJson(utilisateurJSON);
        }
        return new Location(
                json.getInt("id"),
                json.getInt("etat"),
                Util.getJsonString(json,"date_debut"),
                Util.getJsonString(json,"date_retour"),
                typeMateriel,
                materiel,
                utilisateur
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

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(String date_retour) {
        this.date_retour = date_retour;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public TypeMateriel getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(TypeMateriel typeMateriel) {
        this.typeMateriel = typeMateriel;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
