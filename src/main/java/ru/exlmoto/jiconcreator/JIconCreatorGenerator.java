package ru.exlmoto.jiconcreator;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        static {
            // TODO:
            System.out.println("Check this!");
        }
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
}
