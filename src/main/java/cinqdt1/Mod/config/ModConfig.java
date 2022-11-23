package cinqdt1.Mod.config;

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

    public void setBoolean(String category, String subCategory, String variable, boolean value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    public void setString(String category, String subCategory, String variable, String value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    public void setInteger(String category, String subCategory, String variable, int value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    public void setFloat(String category, String subCategory, String variable, float value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }public void setDouble(String category, String subCategory, String variable, double value) throws Exception {
        has(category, subCategory, variable);
        config.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void addBoolean(JsonObject json, String category, String subCategory, String variable, boolean value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void addString(JsonObject json, String category, String subCategory, String variable, String value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void addInteger(JsonObject json, String category, String subCategory, String variable, int value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }

    protected void addFloat(JsonObject json, String category, String subCategory, String variable, float value){
        setKey(json, category, subCategory);
        json.get(category).getAsJsonObject().get(subCategory).getAsJsonObject().addProperty(variable, value);
    }
    protected void addDouble(JsonObject json, String category, String subCategory, String variable, double value){
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
        addDouble(json, "bobberTimer", "position", "x", 0);
        addDouble(json, "bobberTimer", "position", "y", 0);
        addFloat(json, "bobberTimer", "position", "scale", 1.0f);

        addDouble(json, "bundleTracker", "position", "x", 0);
        addDouble(json, "bundleTracker", "position", "y", 0);
        addFloat(json, "bundleTracker", "position", "scale", 1.0f);
        addInteger(json, "bundleTracker", "loot", "bobomb", 0);
        addInteger(json, "bundleTracker", "loot", "pickonimbus2000", 0);
        addInteger(json, "bundleTracker", "loot", "prehistoricEgg", 0);
        addInteger(json, "bundleTracker", "loot", "divanFragment", 0);
        addInteger(json, "bundleTracker", "loot", "recallPotion", 0);
        addInteger(json, "bundleTracker", "loot", "jaderald", 0);
        addInteger(json, "bundleTracker", "loot", "divanAlloy", 0);
        addInteger(json, "bundleTracker", "loot", "fortuneIV", 0);
        addInteger(json, "bundleTracker", "loot", "quickClaw", 0);
        addInteger(json, "bundleTracker", "loot", "gemstoneMixture", 0);
        addInteger(json, "bundleTracker", "runs", "bundleNumber", 0);

        addDouble(json, "fireFreeze", "position", "x", 0);
        addDouble(json, "fireFreeze", "position", "y", 0);
        addFloat(json, "fireFreeze", "position", "scale", 1.0f);

        addDouble(json, "firePillar", "position", "x", 0);
        addDouble(json, "firePillar", "position", "y", 0);
        addFloat(json, "firePillar", "position", "scale", 1.0f);

        addDouble(json, "fragRunTracker", "position", "x", 0);
        addDouble(json, "fragRunTracker", "position", "y", 0);
        addFloat(json, "fragRunTracker", "position", "scale", 1.0f);
        addInteger(json, "fragRunTracker", "giant", "diamanteGiant", 0);
        addInteger(json, "fragRunTracker", "giant", "jollyPinkGiant", 0);
        addInteger(json, "fragRunTracker", "giant", "LASRGiant", 0);
        addInteger(json, "fragRunTracker", "giant", "bigFootGiant", 0);
        addInteger(json, "fragRunTracker", "loot", "diamanteItem", 0);
        addInteger(json, "fragRunTracker", "loot", "jollyPinkItem", 0);
        addInteger(json, "fragRunTracker", "loot", "LASRItem", 0);
        addInteger(json, "fragRunTracker", "loot", "bigFootItem", 0);

        addDouble(json, "endermanPetTracker", "position", "x", 0);
        addDouble(json, "endermanPetTracker", "position", "y", 0);
        addFloat(json, "endermanPetTracker", "position", "scale", 1.0f);
        addInteger(json, "endermanPetTracker", "loot", "rareEndermanPet", 0);
        addInteger(json, "endermanPetTracker", "loot", "epicEndermanPet", 0);
        addInteger(json, "endermanPetTracker", "loot", "legendaryEndermanPet", 0);

        addDouble(json, "lowestHpSummon", "position", "x", 0);
        addDouble(json, "lowestHpSummon", "position", "y", 0);
        addFloat(json, "lowestHpSummon", "position", "scale", 1.0f);

        addDouble(json, "scavengedStats", "position", "x", 0);
        addDouble(json, "scavengedStats", "position", "y", 0);
        addFloat(json, "scavengedStats", "position", "scale", 1.0f);
        addInteger(json, "scavengedStats", "loot", "piece", 0);
        addInteger(json, "scavengedStats", "stats", "time", 0);

        addDouble(json, "xpRunTracker", "position", "x", 0);
        addDouble(json, "xpRunTracker", "position", "y", 0);
        addFloat(json, "xpRunTracker", "position", "scale", 1.0f);
        addInteger(json, "xpRunTracker", "stats", "runs", 0);
        addInteger(json, "xpRunTracker", "stats", "totalXp", 0);
    }
}
