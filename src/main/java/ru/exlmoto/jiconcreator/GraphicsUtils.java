/*
 * Copyright (C) 2010-2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 and Eclipse Public License,
 * Version 1.0 (the "Licenses"); you may not use this file except in
 * compliance with the Licenses. You may obtain a copy of the Licenses at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      http://www.eclipse.org/org/documents/epl-v10.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licenses is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 */

package ru.exlmoto.jiconcreator;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

// Extracted from:
//  1. com/android/assetstudiolib/Util.java
//  2. com/android/assetstudiolib/TextRenderUtil.java
//  3. com/android/ide/eclipse/adt/internal/editors/layout/gle2/ImageUtils.java
public class GraphicsUtils {
    /**
     * Smoothly scales the given {@link BufferedImage} to the given width and height using the
     * {@link Image#SCALE_SMOOTH} algorithm (generally bicubic resampling or bilinear filtering).
     *
     * @param source The source image.
     * @param width  The destination width to scale to.
     * @param height The destination height to scale to.
     * @return A new, scaled image.
     */
    public static BufferedImage scaledImage(BufferedImage source, int width, int height) {
        Image scaledImage = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledBufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = scaledBufImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return scaledBufImage;
    }

    /**
     * Draws the given {@link BufferedImage} to the canvas, centered, wholly contained within the
     * bounds defined by the destination rectangle, and with preserved aspect ratio.
     *
     * @param g       The destination canvas.
     * @param source  The source image.
     * @param dstRect The destination rectangle in the destination canvas into which to draw the
     *                image.
     */
    public static void drawCenterInside(Graphics2D g, BufferedImage source, Rectangle dstRect) {
        final int srcWidth = source.getWidth();
        final int srcHeight = source.getHeight();
        if (srcWidth * 1.0 / srcHeight > dstRect.width * 1.0 / dstRect.height) {
            final int scaledWidth = Math.max(1, dstRect.width);
            final int scaledHeight = Math.max(1, dstRect.width * srcHeight / srcWidth);
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x,
                    dstRect.y + (dstRect.height - scaledHeight) / 2,
                    dstRect.x + dstRect.width,
                    dstRect.y + (dstRect.height - scaledHeight) / 2 + scaledHeight,
                    0,
                    0,
                    0 + scaledWidth,
                    0 + scaledHeight,
                    null);
        } else {
            final int scaledWidth = Math.max(1, dstRect.height * srcWidth / srcHeight);
            final int scaledHeight = Math.max(1, dstRect.height);
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x + (dstRect.width - scaledWidth) / 2,
                    dstRect.y,
                    dstRect.x + (dstRect.width - scaledWidth) / 2 + scaledWidth,
                    dstRect.y + dstRect.height,
                    0,
                    0,
                    0 + scaledWidth,
                    0 + scaledHeight,
                    null);
        }
    }

    /**
     * Draws the given {@link BufferedImage} to the canvas, centered and cropped to fill the
     * bounds defined by the destination rectangle, and with preserved aspect ratio.
     *
     * @param g       The destination canvas.
     * @param source  The source image.
     * @param dstRect The destination rectangle in the destination canvas into which to draw the
     *                image.
     */
    public static void drawCenterCrop(Graphics2D g, BufferedImage source, Rectangle dstRect) {
        final int srcWidth = source.getWidth();
        final int srcHeight = source.getHeight();
        if (srcWidth * 1.0 / srcHeight > dstRect.width * 1.0 / dstRect.height) {
            final int scaledWidth = dstRect.height * srcWidth / srcHeight;
            final int scaledHeight = dstRect.height;
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x,
                    dstRect.y,
                    dstRect.x + dstRect.width,
                    dstRect.y + dstRect.height,
                    0 + (scaledWidth - dstRect.width) / 2,
                    0,
                    0 + (scaledWidth - dstRect.width) / 2 + dstRect.width,
                    0 + dstRect.height,
                    null);
        } else {
            final int scaledWidth = dstRect.width;
            final int scaledHeight = dstRect.width * srcHeight / srcWidth;
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x,
                    dstRect.y,
                    dstRect.x + dstRect.width,
                    dstRect.y + dstRect.height,
                    0,
                    0 + (scaledHeight - dstRect.height) / 2,
                    0 + dstRect.width,
                    0 + (scaledHeight - dstRect.height) / 2 + dstRect.height,
                    null);
        }
    }

    /**
     * Pads the given {@link BufferedImage} on all sides by the given padding amount.
     *
     * @param source  The source image.
     * @param padding The amount to pad on all sides, in pixels.
     * @return A new, padded image, or the source image if no padding is performed.
     */
    public static BufferedImage paddedImage(BufferedImage source, int padding) {
        if (padding == 0) {
            return source;
        }
        BufferedImage newImage = new BufferedImage(source.getWidth() + padding * 2,
                                                   source.getHeight() + padding * 2,
                                                   BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(source, padding, padding, null);
        return newImage;
    }

    /**
     * Fills the given {@link BufferedImage} with a {@link Paint}, preserving its alpha channel.
     *
     * @param source The source image.
     * @param paint  The paint to fill with.
     * @return A new, painted/filled image.
     */
    public static BufferedImage filledImage(BufferedImage source, Paint paint) {
        BufferedImage newImage = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setPaint(paint);
        g.fillRect(0, 0, source.getWidth(), source.getHeight());
        return newImage;
    }

    /**
     * Renders the given string to a {@link BufferedImage}.
     *
     * @param fontName The font to render.
     * @param text The text to render.
     * @param fontStyle The font style: 0 - no style, 1 - bold, 2 - italic.
     * @param fontSize The font size: default is 512.
     * @param paddingPercentage If nonzero, a percentage of the width or height
     *            (whichever is smaller) to add as padding around the text.
     * @param color The font color.
     * @return An BufferedImage image, suitable for general use.
     */
    public static BufferedImage renderTextImage(String fontName, String text,
                                                int fontStyle, int fontSize, int paddingPercentage,
                                                Color color) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        if (text.length() > 10) {
            text = "Too long!";
        }

        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempG = (Graphics2D) tempImage.getGraphics();

        Font font = new Font(fontName, fontStyle, fontSize);

        FontRenderContext frc = tempG.getFontRenderContext();

        TextLayout layout = new TextLayout(text, font, frc);
        Rectangle2D bounds = layout.getBounds();

        // The padding is a percentage relative to the overall minimum of the width or height.
        if (paddingPercentage != 0) {
            double minDimension = Math.min(bounds.getWidth(), bounds.getHeight());
            double delta = minDimension * paddingPercentage / 100;
            bounds.setRect(bounds.getMinX() - delta,
                           bounds.getMinY() - delta,
                           bounds.getWidth() + 2 * delta,
                           bounds.getHeight() + 2 * delta);
        }

        BufferedImage image = new BufferedImage(Math.max(1, (int) bounds.getWidth()),
                                                Math.max(1, (int) bounds.getHeight()),
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(new Color(color.getRGB(), true));
        g.setFont(font);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawString(text, (float) -bounds.getX(), (float) -bounds.getY());

        g.dispose();
        tempG.dispose();

        return image;
    }

    /**
     * Crops blank pixels from the edges of the image and returns the cropped result. We
     * crop off pixels that are blank (meaning they have an alpha value = 0). Note that
     * this is not the same as pixels that aren't opaque (an alpha value other than 255).
     *
     * @param image the image to be cropped
     * @param initialCrop If not null, specifies a rectangle which contains an initial
     *            crop to continue. This can be used to crop an image where you already
     *            know about margins in the image
     * @param imageType the type of {@link BufferedImage} to create
     * @return a cropped version of the source image, or null if the whole image was blank
     *         and cropping completely removed everything
     */
    public static BufferedImage cropBlank(BufferedImage image, Rectangle initialCrop, int imageType) {
        if (image == null) {
            return null;
        }

        // First, determine the dimensions of the real image within the image
        int x1, y1, x2, y2;
        if (initialCrop != null) {
            x1 = initialCrop.x;
            y1 = initialCrop.y;
            x2 = initialCrop.x + initialCrop.width;
            y2 = initialCrop.y + initialCrop.height;
        } else {
            x1 = 0;
            y1 = 0;
            x2 = image.getWidth();
            y2 = image.getHeight();
        }

        // Nothing left to crop
        if (x1 == x2 || y1 == y2) {
            return null;
        }

        // This algorithm is a bit dumb -- it just scans along the edges looking for
        // a pixel that shouldn't be cropped. I could maybe try to make it smarter by
        // for example doing a binary search to quickly eliminate large empty areas to
        // the right and bottom -- but this is slightly tricky with components like the
        // AnalogClock where I could accidentally end up finding a blank horizontal or
        // vertical line somewhere in the middle of the rendering of the clock, so for now
        // we do the dumb thing -- not a big deal since we tend to crop reasonably
        // small images.

        // First determine top edge
        topEdge: for (; y1 < y2; y1++) {
            for (int x = x1; x < x2; x++) {
                if (!cropPixel(image, x, y1)) {
                    break topEdge;
                }
            }
        }

        if (y1 == image.getHeight()) {
            // The image is blank
            return null;
        }

        // Next determine left edge
        leftEdge: for (; x1 < x2; x1++) {
            for (int y = y1; y < y2; y++) {
                if (!cropPixel(image, x1, y)) {
                    break leftEdge;
                }
            }
        }

        // Next determine right edge
        rightEdge: for (; x2 > x1; x2--) {
            for (int y = y1; y < y2; y++) {
                if (!cropPixel(image, x2 - 1, y)) {
                    break rightEdge;
                }
            }
        }

        // Finally determine bottom edge
        bottomEdge: for (; y2 > y1; y2--) {
            for (int x = x1; x < x2; x++) {
                if (!cropPixel(image, x, y2 - 1)) {
                    break bottomEdge;
                }
            }
        }

        // No need to crop?
        if (x1 == 0 && y1 == 0 && x2 == image.getWidth() && y2 == image.getHeight()) {
            return image;
        }

        if (x1 == x2 || y1 == y2) {
            // Nothing left after crop -- blank image
            return null;
        }

        int width = x2 - x1;
        int height = y2 - y1;

        // Now extract the sub-image
        if (imageType == -1) {
            imageType = image.getType();
        }
        if (imageType == BufferedImage.TYPE_CUSTOM) {
            imageType = BufferedImage.TYPE_INT_ARGB;
        }
        BufferedImage cropped = new BufferedImage(width, height, imageType);
        Graphics g = cropped.getGraphics();
        g.drawImage(image, 0, 0, width, height, x1, y1, x2, y2, null);

        g.dispose();

        return cropped;
    }

    /**
     * Returns true if the pixel is should be cropped.
     *
     * @param bufferedImage the image containing the pixel in question
     * @param x the x position of the pixel
     * @param y the y position of the pixel
     * @return true if the pixel should be cropped (for example, is blank)
     */
    public static boolean cropPixel(BufferedImage bufferedImage, int x, int y) {
        int rgb = bufferedImage.getRGB(x, y);
        return (rgb & 0xFF000000) == 0x00000000;
        // TODO: Do a threshold of 80 instead of just 0? Might give better
        // visual results -- e.g. check <= 0x80000000
    }
}
