package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class Utilisateur implements Serializable {

    private int id;

    private String login;

    private String motDePasse;

    private List<Role> listeRole = new ArrayList<>();

    public Utilisateur(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public static Utilisateur fromJson(JSONObject jsonUtilisateur) throws JSONException {
        Utilisateur utilisateur = new Utilisateur(
                jsonUtilisateur.getInt("id"),
                Util.getJsonString(jsonUtilisateur,"login"));
        if (jsonUtilisateur.has("listeRole")) {
            JSONArray jsonListeRole = jsonUtilisateur.getJSONArray("listeRole");
            for (int j = 0; j < jsonListeRole.length(); j++) {
                JSONObject jsonRole = jsonListeRole.getJSONObject(j);
                Role role = Role.fromJson(jsonRole);
                utilisateur.getListeRole().add(role);
            }
        }

        return utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Role> getListeRole() {
        return listeRole;
    }

    public void setListeRole(List<Role> listeRole) {
        this.listeRole = listeRole;
    }

    public boolean isAdmin() {
        for (Role role : getListeRole() ) {
            if ("ROLE_ADMINISTRATEUR".equals(role.getDenomination())) {
                return true;
            }
        }
        return false;
    }

}
