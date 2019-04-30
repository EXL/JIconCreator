/*
 * Copyright (C) 2018-2019 EXL
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

import com.google.utils.GraphicsUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;

public class JIconCreatorGenerator {
    private static final String L_PATH = "/images/launcher_stencil/";

    private JIconCreatorOptions options = null;
    private JIconCreatorGlue glue = null;

    private static class PrecomputedRectTables {
        private static final Rectangle[] IMAGE_RECT = {
            new Rectangle(0, 0, 48, 48),
            new Rectangle(0, 0, 72, 72),
            new Rectangle(0, 0, 96, 96),
            new Rectangle(0, 0, 144, 144),
            new Rectangle(0, 0, 192, 192),
            new Rectangle(0, 0, 512, 512)
        };

        // For shapes: None, Square, Circle.
        private static final Rectangle[][] TARGET_RECTS = {
            {
                new Rectangle(3, 3, 42, 42),
                new Rectangle(4, 4, 64, 64),
                new Rectangle(6, 6, 84, 84),
                new Rectangle(9, 9, 126, 126),
                new Rectangle(12, 12, 168, 168),
                new Rectangle(32, 32, 448, 448)
            },
            {
                new Rectangle(3, 5, 42, 40),
                new Rectangle(4, 8, 64, 60),
                new Rectangle(6, 10, 84, 80),
                new Rectangle(9, 15, 126, 120),
                new Rectangle(12, 20, 168, 160),
                new Rectangle(32, 53, 448, 427)
            },
            {
                new Rectangle(3, 4, 42, 42),
                new Rectangle(4, 6, 64, 64),
                new Rectangle(6, 8, 84, 84),
                new Rectangle(9, 12, 126, 126),
                new Rectangle(12, 16, 168, 168),
                new Rectangle(32, 43, 448, 448)
            }
        };
    }

    public JIconCreatorGenerator(JIconCreatorOptions jIconCreatorOptions, JIconCreatorGlue jIconCreatorGlue) {
        options = jIconCreatorOptions;
        glue = jIconCreatorGlue;
    }

    public BufferedImage generateIcon(int iconResolution, BufferedImage sourceImage) {
        BufferedImage backImage = null;
        BufferedImage foreImage = null;
        BufferedImage maskImage = null;
        BufferedImage maskInnerImage = null;

        String density = JIconCreatorOptions.DPIS[iconResolution];
        String shape = JIconCreatorOptions.SHAPES[options.getShapeType()];
        if (options.getShapeType() != JIconCreatorOptions.SHAPE_NONE) {
            backImage = glue.loadImageResource(L_PATH + shape + "/" + density + "/back.png", true);

            // Here you can use styles, i.e. fore1.png, fore2.png etc.
            foreImage = glue.loadImageResource(L_PATH + shape + "/" + density + "/fore1.png", true);
            maskImage = glue.loadImageResource(L_PATH + shape + "/" + density + "/mask.png", true);
            maskInnerImage = glue.loadImageResource(L_PATH + shape + "/" + density + "/mask_inner.png", true);
        }

        Rectangle imageRect = PrecomputedRectTables.IMAGE_RECT[iconResolution];
        Rectangle targetRect = PrecomputedRectTables.TARGET_RECTS[options.getShapeType()][iconResolution];

        BufferedImage outImage = new BufferedImage(imageRect.width, imageRect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g1 = (Graphics2D) outImage.getGraphics();

        if (backImage != null) {
            g1.drawImage(backImage, 0, 0, null);
        }

        BufferedImage tempImage1 = new BufferedImage(imageRect.width, imageRect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) tempImage1.getGraphics();
        drawMask(maskImage, g2, imageRect);

        BufferedImage tempImage2 = new BufferedImage(imageRect.width, imageRect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g3 = (Graphics2D) tempImage2.getGraphics();
        drawMask(maskInnerImage, g3, imageRect);

        if (options.isCrop()) {
            GraphicsUtils.drawCenterCrop(g3, sourceImage, targetRect);
        } else {
            GraphicsUtils.drawCenterInside(g3, sourceImage, targetRect);
        }

        g2.drawImage(tempImage2, 0, 0, null);
        g1.drawImage(tempImage1, 0, 0, null);
        if (foreImage != null && options.isMask()) {
            g1.drawImage(foreImage, 0, 0, null);
        }

        g1.dispose();
        g2.dispose();

        return outImage;
    }

    private void drawMask(BufferedImage image, Graphics2D graphics, Rectangle rectangle) {
        if (image != null) {
            graphics.drawImage(image, 0, 0, null);
            graphics.setComposite(AlphaComposite.SrcAtop);
            graphics.setPaint(options.getBackColor());
            graphics.fillRect(0, 0, rectangle.width, rectangle.height);
        }
    }

    public BufferedImage generateSourceImage() {
        BufferedImage sourceImage = null;

        boolean trim = options.isTrim();
        int padding = options.getPadding();
        Color paint = options.getForeColor();

        switch (options.getIconType()) {
            default:
            case JIconCreatorOptions.ICON_IMAGE: {
                String path = options.getImageFilePath();
                try {
                    sourceImage = glue.getImage(path, false);
                    if (sourceImage != null) {
                        if (trim) {
                            sourceImage = GraphicsUtils.cropBlank(sourceImage, null, BufferedImage.TYPE_INT_ARGB);
                        }
                        if (padding != 0) {
                            sourceImage = GraphicsUtils.paddedImage(sourceImage, padding);
                        }
                    }
                } catch (IOException ioe) {
                    // Fallback to the ClipArt image.
                    options.setBigImage(true);
                    try {
                        sourceImage = glue.getResourceImage("android.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case JIconCreatorOptions.ICON_CLIPART: {
                try {
                    sourceImage = glue.getClipartImage(options.getClipartName(), true);
                    if (trim) {
                        sourceImage = GraphicsUtils.cropBlank(sourceImage, null, BufferedImage.TYPE_INT_ARGB);
                    }
                    sourceImage = GraphicsUtils.filledImage(sourceImage, paint);
                    if (padding != 0) {
                        sourceImage = GraphicsUtils.paddedImage(sourceImage, padding);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case JIconCreatorOptions.ICON_TEXT: {
                sourceImage = GraphicsUtils.renderTextImage(options.getFont(),
                        options.getTextString(),
                        JIconCreatorOptions.FONT_BOLD,
                        JIconCreatorOptions.DEFAULT_FONT_SIZE,
                        padding,
                        paint);
                if (trim) {
                    sourceImage = GraphicsUtils.cropBlank(sourceImage, null, BufferedImage.TYPE_INT_ARGB);
                }
                if (padding != 0 && sourceImage != null) {
                    sourceImage = GraphicsUtils.paddedImage(sourceImage, padding);
                }
                break;
            }
        }

        if (sourceImage == null) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        return sourceImage;
    }
}
