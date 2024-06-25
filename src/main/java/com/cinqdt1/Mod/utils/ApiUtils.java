package com.cinqdt1.Mod.utils;

import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.core.network.ApiRequest;
import com.cinqdt1.Mod.core.network.RequestResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ApiUtils {
    public static String getLatestProfileID(String UUID, String apiKey) {
        ApiRequest req = new ApiRequest("https://api.hypixel.net/skyblock/profiles?uuid=" + UUID + "&key=" + apiKey,null, ApiRequest.Method.GET,null);
        RequestResponse resp = req.getResponse();
        if(resp == null) return null;
        if(resp.getStatusCode() != 400) return null;
        JsonObject profilesResponse = resp.parseBody(JsonObject.class);
        if (!profilesResponse.get("success").getAsBoolean()) return null;
        if (profilesResponse.get("profiles").isJsonNull()) return null;

        JsonArray profilesArray = profilesResponse.get("profiles").getAsJsonArray();
        for (JsonElement profile : profilesArray) {
            JsonObject profileJSON = profile.getAsJsonObject();
            if (profileJSON.get("selected").getAsBoolean()) {
                return profileJSON.get("profile_id").getAsString();
            }
        }
        return null;
    }
    public static JsonObject getProfileJson(String UUID, String apiKey){
        String latestProfile =  ApiUtils.getLatestProfileID(UUID, ModConfiguration.apiKey);
        if (latestProfile == null) return null;
        ApiRequest req = new ApiRequest("https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + apiKey,null, ApiRequest.Method.GET,null);
        RequestResponse resp = req.getResponse();
        if(resp == null) return null;
        if(resp.getStatusCode() != 400) return null;
        JsonObject response = resp.parseBody(JsonObject.class);
        if (!response.get("success").getAsBoolean()) return null;
        return response;
    }

    public static String getPetName(JsonObject profile, String UUID){
        JsonArray petsArray = profile.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("pets").getAsJsonArray();
        for (JsonElement pet : petsArray) {
            JsonObject petJSON = pet.getAsJsonObject();
            if(petJSON.get("active").getAsBoolean()) {
                return petJSON.get("type").getAsString();
            }
        }
        return null;
    }

    public static double getActivePetXp(JsonObject profile, String UUID){
        JsonArray petsArray = profile.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("pets").getAsJsonArray();
        for (JsonElement pet : petsArray) {
            JsonObject petJSON = pet.getAsJsonObject();
            if(petJSON.get("active").getAsBoolean()) {
                return petJSON.get("exp").getAsDouble();
            }
        }
        return -1;
    }
}
