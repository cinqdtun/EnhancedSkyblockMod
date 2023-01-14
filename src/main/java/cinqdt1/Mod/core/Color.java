package cinqdt1.Mod.core;

public class Color {
    private final int green;
    private final int blue;
    private final int red;
    private final int alpha;

    public Color(int red, int green, int blue, int alpha){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    public Color(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 255;
    }
    

    public int getColorInteger(){
        return (this.alpha << 24) | ((this.red & 255) << 16) | ((this.green & 255) << 8) | (this.blue & 255);
    }

    public int getRed(){
        return this.red & 0xFF;
    }

    public int getGreen(){
        return this.green & 0xFF;
    }

    public int getBlue(){
        return this.blue & 0xFF;
    }

    public int getAlpha(){
        return this.alpha & 0xFF;
    }
}
