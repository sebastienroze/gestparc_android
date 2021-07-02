package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class Document implements Serializable {
    private Integer id;
    private String nom;
    private String extension;
    private String originalFilename;
    private Materiel materiel;
    private Reparation reparation;
    private Retour retour;

    public Document() {}

    public Document(Integer id, String nom,String extension,String originalFilename,
                             Materiel materiel,Reparation reparation,Retour retour
    ) {
        System.out.println(originalFilename);
        this.id = id;
        this.nom = nom;
        this.extension = extension;
        this.originalFilename = originalFilename;
        this.materiel = materiel;
        this.reparation = reparation;
        this.retour = retour;
    }

    public static List<Document> fromJsonArray(JSONArray jsonArray)   throws JSONException {
        List<Document> typeDocuments = new ArrayList<Document>();
        for(int j = 0; j < jsonArray.length(); j++) {
            typeDocuments.add(Document.fromJson(jsonArray.getJSONObject(j)));
        }
        return typeDocuments;
    }

    public static Document fromJson(JSONObject json) throws JSONException {
        Materiel materiel = null;
        if (!json.isNull("materiel")) {
            JSONObject materielJSON = json.getJSONObject("materiel");
            materiel = Materiel.fromJson(materielJSON);
        }
        Reparation reparation = null;
        if (!json.isNull("reparation")) {
            JSONObject reparationJSON = json.getJSONObject("reparation");
            reparation = Reparation.fromJson(reparationJSON);
        }
        Retour retour = null;
        if (!json.isNull("retour")) {
            JSONObject retourJSON = json.getJSONObject("retour");
            retour = Retour.fromJson(retourJSON);
        }

        return new Document(
                json.getInt("id"),
                Util.getJsonString(json,"nom"),
                Util.getJsonString(json,"extension"),
                Util.getJsonString(json,"originalFilename"),
                materiel,reparation,retour
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public Reparation getReparation() {
        return reparation;
    }

    public void setReparation(Reparation reparation) {
        this.reparation = reparation;
    }

    public Retour getRetour() {
        return retour;
    }

    public void setRetour(Retour retour) {
        this.retour = retour;
    }
}
