/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.exlmoto.jiconcreator;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        BufferedImage scaledBufImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
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
            // System.out.println("HERE");
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
            // System.out.println("HERE 2");
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
}
