package cinqdt1.Mod.core;

public class Color {
    private byte red;
    private byte green;
    private byte blue;
    private byte alpha;
    public Color(byte red, byte green, byte blue, byte alpha){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    public Color(byte red, byte green, byte blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = (byte) 255;
    }
    

    public int getColorInteger(){
        return (this.alpha << 24) | ((this.red & 255) << 16) | ((this.green & 255) << 8) | (this.blue & 255);
    }
    public Color getLightenColor(byte diff){
        return this.getLightenColor(diff, diff, diff, (byte) 0);
    }
    public Color getLightenColor(byte diffRed, byte diffGreen, byte diffBlue){
        return this.getLightenColor(diffRed, diffBlue, diffGreen, (byte) 0);
    }
    public Color getLightenColor(byte diffRed, byte diffGreen, byte diffBlue, byte diffAlpha){
        return new Color((byte) (red - diffRed), (byte) (green - diffGreen), (byte) (blue - diffBlue), (byte) (alpha - diffAlpha));
    }
    public Color getDarkenColor(byte diff){
            return this.getDarkenColor(diff, diff, diff, (byte) 0);
    }
    public Color getDarkenColor(byte diffRed, byte diffGreen, byte diffBlue){
        return this.getDarkenColor(diffRed, diffBlue, diffGreen, (byte) 0);
    }
    public Color getDarkenColor(byte diffRed, byte diffGreen, byte diffBlue, byte diffAlpha){
        return new Color((byte) (red + diffRed), (byte) (green + diffGreen), (byte) (blue + diffBlue), (byte) (alpha + diffAlpha));
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

    public float getAlpha(){
        return (float)(this.alpha & 0xFF) / 255;
    }
}
