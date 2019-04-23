package ru.exlmoto.jiconcreator;

import java.awt.image.BufferedImage;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class JIconCreatorGlue {
    private JIconCreatorOptions options = null;
    private JIconCreatorGenerator imageGenerator = null;

    private HashMap<String, BufferedImage> imageCache = null;

    public JIconCreatorGlue(JIconCreatorOptions jIconCreatorOptions) {
        options = jIconCreatorOptions;
        imageGenerator = new JIconCreatorGenerator(options, this);
    }

    public BufferedImage loadImageResource(String path, boolean innerImage) {
        try {
            BufferedImage image = imageCache != null ? imageCache.get(path) : null;
            if (image == null) {
                image = getImage(path, innerImage);
                if (imageCache == null) {
                    imageCache = new HashMap<>();
                }
                imageCache.put(path, image);
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage getImage(String path, boolean innerImage) throws IOException {
        BufferedImage image = null;
        if (innerImage) {
            image = getInnerImage(path);
        } else {
            File file = new File(path);
            image = ImageIO.read(file);
        }
        if (image == null) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        return image;
    }

    private BufferedImage getInnerImage(String relativePath) throws IOException {
        InputStream inputStream = JIconCreatorGlue.class.getResourceAsStream(relativePath);
        if (inputStream == null) {
            return null;
        }
        return ImageIO.read(inputStream);
    }

    public BufferedImage[] generateIcons(int start, int stop, BufferedImage sourceImage) {
        if (start > stop) {
            return null;
        }

        BufferedImage[] bufferedImages = new BufferedImage[stop - start + 1];
        for (int i = start; i <= stop; ++i) {
            BufferedImage image = imageGenerator.generateIcon(i, sourceImage);
            if (image != null) {
                bufferedImages[i - start] = image;
            }
        }

        return bufferedImages;
    }

    // TODO: See this, optimize, remove warinings.
    @SuppressWarnings("Duplicates")
    public boolean saveIconAux(File file, BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", stream);
            byte[] bytes = stream.toByteArray();
            InputStream is = new ByteArrayInputStream(bytes);

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);
            return true;
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            return false;
        }
    }

    // TODO: See this, optimize, remove warinings.
    // Delete Source Image link
    @SuppressWarnings("Duplicates")
    public boolean saveIcons(String path, String fileName, boolean mipmap, BufferedImage sourceImage) {
        // Create some directories if needed.
        boolean saveToPath = false;
        String[] directories = new String[JIconCreatorOptions.DPIS.length];
        if (path != null) {
            saveToPath = true;
            String directory = (mipmap) ? "mipmap-" : "drawable-";
            int failed = 0, cnt = 0;
            for (String dpi : JIconCreatorOptions.DPIS) {
                Path dpiPath = Paths.get(path, directory + dpi);
                String dir = dpiPath.toString();
                directories[cnt] = dir;
                boolean status = new File(dir).mkdir();
                if (!status) {
                    failed++;
                }
                cnt++;
            }
            if (failed != 0) {
                return false;
            }
        }

        // Generate all size of icons.
        BufferedImage[] bufferedImages = generateIcons(JIconCreatorOptions.ICON_M, JIconCreatorOptions.ICON_WEB, sourceImage);
        int failed = 0;
        for (int i = 0; i < bufferedImages.length; ++i) {
            File file = null;
            if (!saveToPath) {
                file = new File(fileName + i + ".png");
            } else {
                Path dpiPath = Paths.get(directories[i], fileName + ".png");
                file = new File(dpiPath.toString());
            }

            if (file.exists()) {
                // TODO: Remove file?
            }

            boolean res = saveIconAux(file, bufferedImages[i]);
            if (!res) {
                failed++;
            }
        }
        return (failed == 0);
    }

/*
    public BufferedImage loadImageResource(String relativeName) {
        try {
            return getCachedImage(relativeName, true);
        } catch (IOException e) {
            //AdtPlugin.log(e, null);
            return null;
        }
    }

    @NonNull
    static BufferedImage getImage(@NonNull String path, boolean isPluginRelative)
            throws IOException {
        BufferedImage image = null;
        if (isPluginRelative) {
            image = GraphicGenerator.getStencilImage(path);
        } else {
            /*
            if (path.equals(DEFAULT_LAUNCHER_ICON)) {
                File file = new File("test.png");
                //File file = TemplateManager.getTemplateLocation(
                //        "projects/NewAndroidApplication/root/res/drawable-xhdpi/ic_launcher.png"); //$NON-NLS-1$
                if (file != null) {
                    path = file.getPath();
                } else {
                    image = GraphicGenerator.getStencilImage("user.png");
                }
            }

            File file = new File(path);

            // Requires Batik
            //if (file.getName().endsWith(DOT_SVG)) {
            //    image = loadSvgImage(file);
            //}

            if (image == null) {
                image = ImageIO.read(file);
            }
        }

        if (image == null) {
            image = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        }

        return image;
    }
*/
}
