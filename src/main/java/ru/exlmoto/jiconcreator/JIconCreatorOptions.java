package ru.exlmoto.jiconcreator;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JIconCreatorOptions {
    public static final int PREVIEW_M = 0;
    public static final int PREVIEW_H = 1;
    public static final int PREVIEW_XH = 2;
    public static final int PREVIEW_XXH = 3;
    public static final int PREVIEW_WEB = 4;

    public static final String[] DPIS = { "mdpi", "hdpi", "xhdpi", "xxdpi", "xxxdpi", "web" };

    private final int BIG_SIZE_PIX = 300;

    private boolean bigImage = false;

    public static final int ICON_IMAGE = 0;
    public static final int ICON_CLIPART = 1;
    public static final int ICON_TEXT = 2;

    public static final int SHAPE_NONE = 0;
    public static final int SHAPE_SQUARE = 1;
    public static final int SHAPE_CIRCLE = 2;

    private int iconType = ICON_IMAGE;

    /// Todo: ??????????
    private String clipartName = "android.png";
    /// TODO: ????
    private String imageFilePath = "";

    private String textString = "aA";

    private boolean trim = true;

    private boolean mask = true;
    private int padding = 15;

    private boolean crop = false;
    private int shapeType = SHAPE_SQUARE;

    private Color backColor = new Color(0xFF, 0xFF, 0xFF);
    private Color foreColor = new Color(0x00, 0x00, 0x00);

    private String font = "Default";

    public boolean isImageBigSize(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            return ((image.getHeight() > BIG_SIZE_PIX) || (image.getWidth() > BIG_SIZE_PIX));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getClipartName() {
        return clipartName;
    }

    public void setClipartName(String clipartName) {
        this.clipartName = clipartName;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public boolean isCrop() {
        return crop;
    }

    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public Color getForeColor() {
        return foreColor;
    }

    public void setForeColor(Color foreColor) {
        this.foreColor = foreColor;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public boolean isBigImage() {
        return bigImage;
    }

    public void setBigImage(boolean bigImage) {
        this.bigImage = bigImage;
    }

    public boolean isMask() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }
}
