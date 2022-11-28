package cinqdt1.Mod.core.network;

public class Header {
    private final String headerName;
    private final String headerValue;

    public Header(String headerName, String headerValue){
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public String getHeaderName(){
        return headerName;
    }

    public String getHeaderValue(){
        return headerValue;
    }

}
