package com.cinqdt1.Mod.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;
import java.util.regex.Pattern;

public class ModConfig {
    private final File configPath;
    private JsonObject config = new JsonObject();

    public ModConfig(File configPath){
        this.configPath = configPath;
    }

    public void init(){
        if(configPath.exists()) loadConfig();
        fixConfig(defaultConfig());
        saveConfig();
    }

    public void loadConfig(){
        try (FileInputStream inputStream = new FileInputStream(configPath)) {
            config = new Gson().fromJson(IOUtils.toString(inputStream), JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void newloadConfig(){
        try (FileInputStream inputStream = new FileInputStream(configPath)) {
           String file = IOUtils.toString(inputStream);
           Pattern pat = Pattern.compile("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        try {
            Writer output = new BufferedWriter(new FileWriter(configPath));
            output.write(config.toString());
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean getBoolean(String category, String subCategory, String variable) throws Exception {
        has(category, subCategory, variable);
        return config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().get(variable).getAsBoolean();

    }
    public String getString(String category, String subCategory, String variable) throws Exception {
        has(category, subCategory, variable);
        return config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().get(variable).getAsString();
    }
    public int getInteger(String category, String subCategory, String variable) throws Exception {
        has(category, subCategory, variable);
        return config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().get(variable).getAsInt();
    }
    public float getFloat(String category, String subCategory, String variable) throws Exception {
        has(category, subCategory, variable);
        return config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().get(variable).getAsFloat();
    }
    public double getDouble(String category, String subCategory, String variable) throws Exception {
        has(category, subCategory, variable);
        return config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().get(variable).getAsDouble();
    }

    public void set(String category, String subCategory, String variable, boolean value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    public void set(String category, String subCategory, String variable, String value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    public void set(String category, String subCategory, String variable, int value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }
    public void set(String category, String subCategory, String variable, double value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void add(JsonObject json, String category, String subCategory, String variable, boolean value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void add(JsonObject json, String category, String subCategory, String variable, String value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void add(JsonObject json, String category, String subCategory, String variable, int value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void add(JsonObject json, String category, String subCategory, String variable, float value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }
    protected void add(JsonObject json, String category, String subCategory, String variable, double value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    private void has(String category, String subCategory, String variable) throws Exception {
        if(!config.has(category)) throw new Exception("Category doesn't exist");
        if(!config.get(category).getAsJsonObject().has(subCategory)) throw new Exception("Sub Category doesn't exist");
        if(!config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().has(variable)) throw new Exception("Variable doesn't exist");
    }

    private void setKey(JsonObject json, String category, String subCategory){
        if(!json.has(category))
            json.add(category, new JsonObject());
        if(!json.get(category).getAsJsonObject().has(subCategory))
            json.get(category).getAsJsonObject().add(subCategory, new JsonObject());
    }

    private void fixConfig(JsonObject json){
        for(Map.Entry<String, JsonElement> category : json.entrySet()){
            if(!config.has(category.getKey()))
                config.add(category.getKey(), category.getValue());
            for(Map.Entry<String, JsonElement> subCategory : category.getValue().getAsJsonObject().entrySet()){
                if(!config.get(category.getKey()).getAsJsonObject().has(subCategory.getKey()))
                    config.get(category.getKey()).getAsJsonObject().add(subCategory.getKey(), subCategory.getValue());
                for(Map.Entry<String, JsonElement> variable : subCategory.getValue().getAsJsonObject().entrySet()){
                    if(!config.get(category.getKey()).getAsJsonObject().get(subCategory.getKey()).getAsJsonObject().has(variable.getKey()))
                        config.get(category.getKey()).getAsJsonObject().get(subCategory.getKey()).getAsJsonObject().add(variable.getKey(), variable.getValue());
                }
            }
        }
    }

    private JsonObject defaultConfig(){
        JsonObject json = new JsonObject();
        defaultConfigHandler(json);
        return json;
    }

    protected void defaultConfigHandler(JsonObject json){
        this.add(json, "ashfangHp", "position", "x", 0);
        this.add(json, "ashfangHp", "position", "y", 0);
        this.add(json, "ashfangHp", "position", "scale", 1.0f);

        this.add(json, "batFirework", "position", "x", 0);
        this.add(json, "batFirework", "position", "y", 0);
        this.add(json, "batFirework", "position", "scale", 1.0f);
        this.add(json, "batFirework", "loot", "candy", 0);
        this.add(json, "batFirework", "loot", "fireworks_lauched", 0);

        this.add(json, "bobberTimer", "position", "x", 0);
        this.add(json, "bobberTimer", "position", "y", 0);
        this.add(json, "bobberTimer", "position", "scale", 1.0f);

        this.add(json, "bundleTracker", "position", "x", 0);
        this.add(json, "bundleTracker", "position", "y", 0);
        this.add(json, "bundleTracker", "position", "scale", 1.0f);
        this.add(json, "bundleTracker", "loot", "bobomb", 0);
        this.add(json, "bundleTracker", "loot", "pickonimbus2000", 0);
        this.add(json, "bundleTracker", "loot", "prehistoricEgg", 0);
        this.add(json, "bundleTracker", "loot", "divanFragment", 0);
        this.add(json, "bundleTracker", "loot", "recallPotion", 0);
        this.add(json, "bundleTracker", "loot", "jaderald", 0);
        this.add(json, "bundleTracker", "loot", "divanAlloy", 0);
        this.add(json, "bundleTracker", "loot", "fortuneIV", 0);
        this.add(json, "bundleTracker", "loot", "quickClaw", 0);
        this.add(json, "bundleTracker", "loot", "gemstoneMixture", 0);
        this.add(json, "bundleTracker", "runs", "bundleNumber", 0);

        this.add(json, "fireFreeze", "position", "x", 0);
        this.add(json, "fireFreeze", "position", "y", 0);
        this.add(json, "fireFreeze", "position", "scale", 1.0f);

        this.add(json, "firePillar", "position", "x", 0);
        this.add(json, "firePillar", "position", "y", 0);
        this.add(json, "firePillar", "position", "scale", 1.0f);

        this.add(json, "fragRunTracker", "position", "x", 0);
        this.add(json, "fragRunTracker", "position", "y", 0);
        this.add(json, "fragRunTracker", "position", "scale", 1.0f);
        this.add(json, "fragRunTracker", "giant", "diamanteGiant", 0);
        this.add(json, "fragRunTracker", "giant", "jollyPinkGiant", 0);
        this.add(json, "fragRunTracker", "giant", "LASRGiant", 0);
        this.add(json, "fragRunTracker", "giant", "bigFootGiant", 0);
        this.add(json, "fragRunTracker", "loot", "diamanteItem", 0);
        this.add(json, "fragRunTracker", "loot", "jollyPinkItem", 0);
        this.add(json, "fragRunTracker", "loot", "LASRItem", 0);
        this.add(json, "fragRunTracker", "loot", "bigFootItem", 0);

        this.add(json, "endermanPetTracker", "position", "x", 0);
        this.add(json, "endermanPetTracker", "position", "y", 0);
        this.add(json, "endermanPetTracker", "position", "scale", 1.0f);
        this.add(json, "endermanPetTracker", "loot", "rareEndermanPet", 0);
        this.add(json, "endermanPetTracker", "loot", "epicEndermanPet", 0);
        this.add(json, "endermanPetTracker", "loot", "legendaryEndermanPet", 0);

        this.add(json, "lowestHpSummon", "position", "x", 0);
        this.add(json, "lowestHpSummon", "position", "y", 0);
        this.add(json, "lowestHpSummon", "position", "scale", 1.0f);

        this.add(json, "mythologicalLastInqui", "position", "x", 0);
        this.add(json, "mythologicalLastInqui", "position", "y", 0);
        this.add(json, "mythologicalLastInqui", "position", "scale", 1.0f);
        this.add(json, "mythologicalLastInqui", "stats", "lastInqui", 0);

        this.add(json, "mythologicalHp", "position", "x", 0);
        this.add(json, "mythologicalHp", "position", "y", 0);
        this.add(json, "mythologicalHp", "position", "scale", 1.0f);

        this.add(json, "scathaCoolodwn", "position", "x", 0);
        this.add(json, "scathaCoolodwn", "position", "y", 0);
        this.add(json, "scathaCoolodwn", "position", "scale", 1.0f);

        this.add(json, "scavengedStats", "position", "x", 0);
        this.add(json, "scavengedStats", "position", "y", 0);
        this.add(json, "scavengedStats", "position", "scale", 1.0f);
        this.add(json, "scavengedStats", "loot", "piece", 0);
        this.add(json, "scavengedStats", "stats", "time", 0);

        this.add(json, "xpRunTracker", "position", "x", 0);
        this.add(json, "xpRunTracker", "position", "y", 0);
        this.add(json, "xpRunTracker", "position", "scale", 1.0f);
        this.add(json, "xpRunTracker", "stats", "runs", 0);
        this.add(json, "xpRunTracker", "stats", "totalXp", 0);
    }
}
