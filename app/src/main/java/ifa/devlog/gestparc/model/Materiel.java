package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class Materiel implements Serializable {
    private Integer id;
    private String nom;
    private String reference;
    private String etat;
    private TypeMateriel typeMateriel;



    public Materiel() {}

    public Materiel(Integer id, String nom, String reference, String etat,TypeMateriel typeMateriel) {
        this.id = id;
        this.nom = nom;
        this.reference = reference;
        this.etat = etat;
        this.typeMateriel = typeMateriel;
    }

    public static List<Materiel> fromJsonArray(JSONArray jsonArray)   throws JSONException {
        List<Materiel> materiels = new ArrayList<Materiel>();
        for(int j = 0; j < jsonArray.length(); j++) {
            materiels.add(Materiel.fromJson(jsonArray.getJSONObject(j)));
        }
        return materiels;
    }

    public static Materiel fromJson(JSONObject json) throws JSONException {
        TypeMateriel typeMateriel = null;
        if (!json.isNull("materiel")) {
            JSONObject niveauJSON = json.getJSONObject("typeMateriel");
            typeMateriel = TypeMateriel.fromJson(niveauJSON);
        }
        return new Materiel(
                json.getInt("id"),
                Util.getJsonString(json,"nom"),
                Util.getJsonString(json,"reference"),
                Util.getJsonString(json,"etat"),
                typeMateriel
        );
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public TypeMateriel getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(TypeMateriel typeMateriel) {
        this.typeMateriel = typeMateriel;
    }
}
