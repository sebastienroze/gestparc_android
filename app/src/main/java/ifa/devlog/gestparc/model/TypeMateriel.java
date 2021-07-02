package ifa.devlog.gestparc.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ifa.devlog.gestparc.utils.Util;

public class TypeMateriel implements Serializable {
    private Integer id;
    private String description;

    public TypeMateriel() {}

    public TypeMateriel(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static List<TypeMateriel> fromJsonArray(JSONArray jsonArray)   throws JSONException {
        List<TypeMateriel> typeMateriels = new ArrayList<TypeMateriel>();
        for(int j = 0; j < jsonArray.length(); j++) {
            typeMateriels.add(TypeMateriel.fromJson(jsonArray.getJSONObject(j)));
        }
        return typeMateriels;
    }

    public static TypeMateriel fromJson(JSONObject json) throws JSONException {
        return new TypeMateriel(
                json.getInt("id"),
                Util.getJsonString(json,"description")
        );
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
