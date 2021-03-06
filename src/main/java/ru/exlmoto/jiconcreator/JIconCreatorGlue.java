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

import java.awt.image.BufferedImage;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;

import javax.imageio.ImageIO;

public class JIconCreatorGlue {
    private JIconCreatorGenerator imageGenerator = null;

    private HashMap<String, BufferedImage> imageCache = null;

    public JIconCreatorGlue(JIconCreatorOptions jIconCreatorOptions) {
        imageGenerator = new JIconCreatorGenerator(jIconCreatorOptions, this);
    }

    public JIconCreatorGenerator getImageGenerator() {
        return imageGenerator;
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

    public BufferedImage getImage(String path, boolean innerImage) throws IOException {
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

    public BufferedImage getResourceImage(String fileName) throws IOException {
        return getInnerImage("/images/" + fileName);
    }

    public BufferedImage getClipartImage(String clipartName, boolean big) throws IOException {
        String path = "/images/clipart/";
        path += (big) ? "big/" : "small/";
        path += clipartName;
        return getInnerImage(path);
    }

    private BufferedImage[] generateIcons(int start, int stop, BufferedImage sourceImage) {
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

    private boolean saveIconAux(File file, BufferedImage image) {
        try {
            ImageIO.write(image, "PNG", file);
            return true;
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            return false;
        }
    }

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
        boolean delete = true;
        if (bufferedImages != null) {
            for (int i = 0; i < bufferedImages.length; ++i) {
                File file = null;
                if (!saveToPath) {
                    file = new File(fileName + i + ".png");
                } else {
                    Path dpiPath = Paths.get(directories[i], fileName + ".png");
                    file = new File(dpiPath.toString());
                }

                if (file.exists()) {
                    delete = file.delete();
                }

                boolean res = saveIconAux(file, bufferedImages[i]);
                if (!res || !delete) {
                    failed++;
                }
            }
            System.gc();
        }
        return (failed == 0);
    }

    public BufferedImage[] generatePreviews() {
        return generateIcons(JIconCreatorOptions.ICON_M, JIconCreatorOptions.ICON_XXH,
                             imageGenerator.generateSourceImage());
    }

    public BufferedImage[] generateBigPreviews() {
        return generateIcons(JIconCreatorOptions.ICON_XXXH, JIconCreatorOptions.ICON_WEB,
                imageGenerator.generateSourceImage());
    }
}
