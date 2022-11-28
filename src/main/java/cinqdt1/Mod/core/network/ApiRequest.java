package cinqdt1.Mod.core.network;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiRequest {
    private String endpoint;
    private List<Header> headers;
    private Method method;
    private String jsonBodyContent;

    public ApiRequest(String endpoint, @Nullable List<Header> headers, Method method, @Nullable String jsonBodyContent){
        this.endpoint = endpoint;
        this.headers = headers;
        this.method = method;
        this.jsonBodyContent = jsonBodyContent;
    }

    public RequestResponse getResponse(){
        try{
            URL url = new URL(endpoint);
            HttpURLConnection req = (HttpURLConnection) url.openConnection();
            switch(method){
                case GET:
                    req.setRequestMethod("GET");
                    req.setRequestProperty("User-Agent", "Enhanced Skyblock Mod/1.0");
                    req.setConnectTimeout(5000);
                    if(headers != null) {
                        for (Header header : headers) {
                            req.setRequestProperty(header.getHeaderName(), header.getHeaderValue());
                        }
                    }
                    req.connect();
                    break;
                case POST:
                    req.setDoOutput(true);
                    req.setRequestMethod("POST");
                    req.setRequestProperty("content-type", "application/json");
                    req.setRequestProperty("User-Agent", "Enhanced Skyblock Mod/1.0");
                    req.setConnectTimeout(5000);
                    for(Header header : headers){
                        req.setRequestProperty(header.getHeaderName(), header.getHeaderValue());
                    }
                    OutputStream reqOutputStream = req.getOutputStream();
                    OutputStreamWriter reqOutputWriter = new OutputStreamWriter(reqOutputStream, StandardCharsets.UTF_8);
                    if(jsonBodyContent == null) return null;
                    reqOutputWriter.write(jsonBodyContent);
                    reqOutputWriter.flush();
                    reqOutputWriter.close();
                    reqOutputStream.close();
                    req.connect();
                    break;
            }
            if (req.getResponseCode() == HttpURLConnection.HTTP_OK){
                return new RequestResponse(readStream(req.getInputStream()), req.getResponseCode());
            }else {
                return new RequestResponse(readStream(req.getErrorStream()), req.getResponseCode());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String readStream(InputStream inStream) throws IOException {
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
        StringBuilder responseBuilder = new StringBuilder();
        String tempInput;
        while ((tempInput = bufReader.readLine()) != null){
            responseBuilder.append(tempInput);
        }
        bufReader.close();
        return responseBuilder.toString();
    }
    public enum Method{
        GET,
        POST
    }
}
