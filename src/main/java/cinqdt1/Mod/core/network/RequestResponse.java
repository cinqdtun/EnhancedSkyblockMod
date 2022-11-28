package cinqdt1.Mod.core.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RequestResponse {
    private final String bodyResp;
    private final int httpStatusCode;

    public RequestResponse(String bodyResp, int httpStatusCode){
        this.bodyResp = bodyResp;
        this.httpStatusCode = httpStatusCode;
    }

    public <T> T parseBody(Class<T> type){
        try {
            Gson gson = new Gson();
            return gson.fromJson(bodyResp, type);
        }catch (Exception ex){
            throw ex;
        }
    }

    public String getString(){
        return bodyResp;
    }

    public int getStatusCode(){
        return httpStatusCode;
    }

}
