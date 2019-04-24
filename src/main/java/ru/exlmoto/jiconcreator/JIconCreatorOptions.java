package ru.exlmoto.jiconcreator;

import java.awt.Color;

public class JIconCreatorOptions {
    public static final int ICON_M = 0;
    public static final int ICON_H = 1;
    public static final int ICON_XH = 2;
    public static final int ICON_XXH = 3;
    public static final int ICON_XXXH = 4;
    public static final int ICON_WEB = 5;
    public static final String[] DPIS = { "mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi", "web" };

    public static final String DEFAULT_TEXT = "aA";
    public static final String DEFAULT_CLIPART = "12-hardware-headphones.png";
    public static final String DEFAULT_IMAGE = "Please choose the image file.";

    private boolean bigImage = false;

    public static final int ICON_IMAGE = 0;
    public static final int ICON_CLIPART = 1;
    public static final int ICON_TEXT = 2;

    public static final int SHAPE_NONE = 0;
    public static final int SHAPE_SQUARE = 1;
    public static final int SHAPE_CIRCLE = 2;
    public static final String[] SHAPES = { "none", "square", "circle" };

    public static final int FONT_NORMAL = 0;
    public static final int FONT_BOLD = 1;
    public static final int FONT_ITALIC = 2;
    public static final int DEFAULT_FONT_SIZE = 512;

    private int iconType = ICON_IMAGE;

    private String clipartName = DEFAULT_CLIPART;
    private String imageFilePath = DEFAULT_CLIPART;
    private String textString = DEFAULT_TEXT;

    private boolean trim = true;

    private boolean mask = true;
    private int padding = 15;

    private boolean crop = false;
    private int shapeType = SHAPE_SQUARE;

    private Color backColor = new Color(0xFF, 0xFF, 0xFF);
    private Color foreColor = new Color(0x00, 0x00, 0x00);

    private String font = "Default";

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
