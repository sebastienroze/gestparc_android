package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class Retour implements Serializable {
    private Integer id;
    private String audit;
    private String etatEntrant;
    private String etatSortant;
    private Location location;
    public Retour() {}

    public Retour(Integer id, String audit,
                  String etatEntrant, String etatSortant,Location location
    ) {
        this.id = id;
        this.audit = audit;
        this.etatEntrant = etatEntrant;
        this.etatSortant = etatSortant;
        this.location = location;
    }

    public static List<Retour> fromJsonArray(JSONArray jsonArray)   throws JSONException {
        List<Retour> reparations = new ArrayList<Retour>();
        for(int j = 0; j < jsonArray.length(); j++) {
            reparations.add(Retour.fromJson(jsonArray.getJSONObject(j)));
        }
        return reparations;
    }

    public static Retour fromJson(JSONObject json) throws JSONException {
        Location location = null;
        if (!json.isNull("location")) {
            JSONObject locationJSON = json.getJSONObject("location");
            location = Location.fromJson(locationJSON);
        }
        return new Retour(
                json.getInt("id"),
                Util.getJsonString(json,"audit"),
                Util.getJsonString(json,"etatEntrant"),
                Util.getJsonString(json,"etatSortant"),
                location

        );
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getEtatEntrant() {
        return etatEntrant;
    }

    public void setEtatEntrant(String etatEntrant) {
        this.etatEntrant = etatEntrant;
    }

    public String getEtatSortant() {
        return etatSortant;
    }

    public void setEtatSortant(String etatSortant) {
        this.etatSortant = etatSortant;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
