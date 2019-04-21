package ru.exlmoto.jiconcreator.unsorted;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.assetstudiolib.*;
import com.android.utils.Pair;
import ru.exlmoto.jiconcreator.JIconCreatorOptions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class JIconCreatorExtrasLibraryHere {

    private final static String DEFAULT_LAUNCHER_ICON = "test.png";

    /*
    public static void main(String[] args) {

        JIconCreatorFrame jIconCreatorFrame = new JIconCreatorFrame();
        jIconCreatorFrame.setSize(800, 600);
        jIconCreatorFrame.setVisible(true);

        //JFrame jFrame = new JFrame("JIconCreator");
        //jFrame.setContentPane(new JIconCreatorForm().getMainPane());
        //jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jFrame.setSize(new Dimension(800, 600));
        //jFrame.pack();
        //jFrame.setVisible(true);

        System.out.println(42);
        //CreateAssetSetWizardState createAssetSetWizardState = new CreateAssetSetWizardState();
        //generateIcons(createAssetSetWizardState,false);
    }
    */

    /**
     * Generate images using the given wizard state
     *
     * @param mValues the state to use
     * @param previewOnly whether we are only generating previews
     * @return a map of image objects
     */
    public static Map<String, Map<String, BufferedImage>> generateImages(
            @NonNull CreateAssetSetWizardState mValues,
            JIconCreatorOptions jIconCreatorOptions,
            boolean previewOnly) {

        // Map of ids to images: Preserve insertion order (the densities)
        Map<String, Map<String, BufferedImage>> categoryMap =
                new LinkedHashMap<String, Map<String, BufferedImage>>();

        AssetType type = mValues.type; // DELETE THIS CODE!!!!!

        boolean trim = jIconCreatorOptions.isTrim();

        BufferedImage sourceImage = null;

        // TODO: Disable this shitty converting
        CreateAssetSetWizardState.SourceType sourceType = CreateAssetSetWizardState.SourceType.IMAGE;
        int iconType = jIconCreatorOptions.getIconType();
        if (iconType == JIconCreatorOptions.ICON_CLIPART) {
            sourceType = CreateAssetSetWizardState.SourceType.CLIPART;
        } else if (iconType == JIconCreatorOptions.ICON_TEXT) {
            sourceType = CreateAssetSetWizardState.SourceType.TEXT;
        }

        switch (sourceType) {
            case IMAGE: {
                // Load the image
                // TODO: Only do this when the source image type is image
                // String path = mValues.imagePath != null ? mValues.imagePath.getPath() : "";
                String path = jIconCreatorOptions.getImageFilePath();
                /*
                if (path.length() == 0) {
                    //if (page != null) {
                        System.out.println("Enter a filename");
                        //page.setErrorMessage("Enter a filename");
                    //}
                    return Collections.emptyMap();
                }

                if (!path.equals(DEFAULT_LAUNCHER_ICON)) {
                    File file = new File(path);
                    if (!file.isFile()) {
                        //if (page != null) {
                            System.out.println("File not found");
                            // page.setErrorMessage(String.format("%1$s does not exist", file.getPath()));
                        //}
                        return Collections.emptyMap();
                    }
                }*/

                //if (page != null) {
                    // page.setErrorMessage(null);
                    // System.out.println("Null");
                //}
                try {
                    // ???????????????????????????????????????????????????????????????????????????????????????????????????????????!
                    sourceImage = mValues.getCachedImage(path, false);
                    if (sourceImage != null) {
                        if (trim) {
                            sourceImage = ImageUtils.cropBlank(sourceImage, null, TYPE_INT_ARGB);
                        }
                        if (jIconCreatorOptions.getPadding() != 0) {
                            sourceImage = Util.paddedImage(sourceImage, jIconCreatorOptions.getPadding());
                        }
                    }
                } catch (IOException ioe) {
                    //if (page != null) {
                        System.out.println("IOE Error: Fallback to Clipart Image");
                        // page.setErrorMessage(ioe.getLocalizedMessage());
                    try {
                        sourceImage = GraphicGenerator.getClipartImage("android.png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //}
                }
                break;
            }
            case CLIPART: {
                try {
                    sourceImage = GraphicGenerator.getClipartImage(jIconCreatorOptions.getClipartName());

                    // boolean isActionBar = mValues.type == AssetType.ACTIONBAR;
                    if (trim) {
                        sourceImage = ImageUtils.cropBlank(sourceImage, null, TYPE_INT_ARGB);
                    }

                    if (type.needsColors()) { // ALWAYS TRUE???????????????????????????????????????????????????????
                        // RGB fg = mValues.foreground;
                        Color fg = jIconCreatorOptions.getForeColor();

                        int color = 0xFF000000 | (fg.getRed() << 16) | (fg.getGreen() << 8) | fg.getBlue();
                        Paint paint = new java.awt.Color(color);
                        sourceImage = Util.filledImage(sourceImage, paint);
                    }

                    int padding = jIconCreatorOptions.getPadding();
                    if (padding != 0) {
                        sourceImage = Util.paddedImage(sourceImage, padding);
                    }
                } catch (IOException e) {
                    System.out.println("Clipart error");
                    // AdtPlugin.log(e, null);
                    return categoryMap;
                }
                break;
            }
            case TEXT: {
                String text = jIconCreatorOptions.getTextString();
                TextRenderUtil.Options options = new TextRenderUtil.Options();

                /// TODO: DROP THIS AND CHANGE TO FONT
                options.font = mValues.getTextFont();

                int color;
                if (type.needsColors()) { // ALWAYS TRUE?????
                    // RGB fg = mValues.foreground;
                    Color fg = jIconCreatorOptions.getForeColor();

                    color = 0xFF000000 | (fg.getRed() << 16) | (fg.getGreen() << 8) | fg.getBlue();
                } else {
                    color = 0xFFFFFFFF;
                }

                options.foregroundColor = color;

                sourceImage = TextRenderUtil.renderTextImage(text, jIconCreatorOptions.getPadding() /* 15?? */, options);

                if (trim) {
                    sourceImage = ImageUtils.cropBlank(sourceImage, null, TYPE_INT_ARGB);
                }

                int padding = jIconCreatorOptions.getPadding();
                if (padding != 0) {
                    sourceImage = Util.paddedImage(sourceImage, padding);
                }
                break;
            }
        }

        GraphicGenerator generator = null;
        GraphicGenerator.Options options = null;

        switch (type) {
            case LAUNCHER: {
                generator = new LauncherIconGenerator();
                LauncherIconGenerator.LauncherOptions launcherOptions =
                        new LauncherIconGenerator.LauncherOptions();

                //// TODO: Rewrite this.

                int shape = jIconCreatorOptions.getShapeType();
                switch (shape) {
                    case JIconCreatorOptions.SHAPE_NONE:
                        launcherOptions.shape = GraphicGenerator.Shape.NONE;
                        break;
                    case JIconCreatorOptions.SHAPE_SQUARE:
                        launcherOptions.shape = GraphicGenerator.Shape.SQUARE;
                        break;
                    case JIconCreatorOptions.SHAPE_CIRCLE:
                        launcherOptions.shape = GraphicGenerator.Shape.CIRCLE;
                        break;
                }
                // launcherOptions.shape = mValues.shape;

                launcherOptions.crop = jIconCreatorOptions.isCrop();

                /// ??????????????????????????????????????????????????????????
                launcherOptions.style = GraphicGenerator.Style.SIMPLE;

                // RGB bg = mValues.background;
                Color bg = jIconCreatorOptions.getBackColor();
                int color = (bg.getRed() << 16) | (bg.getRed() << 8) | bg.getBlue();
                launcherOptions.backgroundColor = color;

                // Flag which tells the generator iterator to include a web graphic
                launcherOptions.isWebGraphic = !previewOnly;

                options = launcherOptions;
                break;
            }
            /*
            case MENU:
                generator = new MenuIconGenerator();
                options = new GraphicGenerator.Options();
                break;
            case ACTIONBAR: {
                generator = new ActionBarIconGenerator();
                ActionBarIconGenerator.ActionBarOptions actionBarOptions =
                        new ActionBarIconGenerator.ActionBarOptions();
                actionBarOptions.theme = mValues.holoDark
                        ? ActionBarIconGenerator.Theme.HOLO_DARK
                        : ActionBarIconGenerator.Theme.HOLO_LIGHT;
                actionBarOptions.sourceIsClipart = (mValues.sourceType == CreateAssetSetWizardState.SourceType.CLIPART);

                options = actionBarOptions;
                break;
            }
            case NOTIFICATION: {
                generator = new NotificationIconGenerator();
                options = new NotificationIconGenerator.NotificationOptions();
                break;
            }
            case TAB:
                generator = new TabIconGenerator();
                options = new TabIconGenerator.TabOptions();
                break;
                */
            default:
                System.out.println("Unsupported asset type: %1$s" + type);
                // AdtPlugin.log(IStatus.ERROR, "Unsupported asset type: %1$s", type);
                return categoryMap;
        }

        options.sourceImage = sourceImage;

        /*
        IProject project = mValues.project;
        if (mValues.minSdk != -1) {
            options.minSdk = mValues.minSdk;
        } else {
            Pair<Integer, Integer> v = ManifestInfo.computeSdkVersions(project);
            options.minSdk = v.getFirst();
        }
        */

        String baseName = mValues.outputName;
        //if (baseName != null) //baseName = "android.png";

        /// WHY mVALUES????????????????????????????????????????????????
        generator.generate(null, categoryMap, mValues, options, baseName);

        return categoryMap;
    }

    /**
     * Generate custom icons into the project based on the asset studio wizard
     * state
     *
     * @param values the wizard state to read configuration settings from
     * @param previewOnly whether we are only generating a preview. For example,
     *            the launcher icons won't generate a huge 512x512 web graphic
     *            in preview mode
     */
    public static void generateIcons(/*final IProject newProject,*/
                                     @NonNull CreateAssetSetWizardState values,
                                     JIconCreatorOptions jIconCreatorOptions,
                                     boolean previewOnly//,
                                     /*@Nullable WizardPage page*/) {
        // Generate the custom icons
        Map<String, Map<String, BufferedImage>> categories = generateImages(values, jIconCreatorOptions,
                false /*previewOnly*/);
        int cnt = 0;
        for (Map<String, BufferedImage> previews : categories.values()) {
            for (Map.Entry<String, BufferedImage> entry : previews.entrySet()) {
                String relativePath = entry.getKey();

                //IPath dest = new Path(relativePath);
                //IFile file = newProject.getFile(dest);

                File file = new File("image_" + String.valueOf(cnt) + "_" + DEFAULT_LAUNCHER_ICON);
                cnt++;

                // In case template already created icons (should remove that)
                // remove them first
                if (file.exists()) {
                    /*
                    try {
                        file.delete(true, new NullProgressMonitor());
                    } catch (CoreException e) {
                        AdtPlugin.log(e, null);
                    }
                    */
                    // DELETE
                }
                // AdtUtils.createWsParentDirectory(file.getParent());
                BufferedImage image = entry.getValue();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(image, "PNG", stream); //$NON-NLS-1$
                    byte[] bytes = stream.toByteArray();
                    InputStream is = new ByteArrayInputStream(bytes);

                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    OutputStream outStream = new FileOutputStream(file);
                    outStream.write(buffer);

                    // file.create(is, true /*force*/, null /*progress*/);
                    // File create
                } catch (IOException e) {
                    System.out.println("IOException error");
                    // AdtPlugin.log(e, null);
                } /*catch (CoreException e) {
                    AdtPlugin.log(e, null);
                } */
/*
                try {
                    file.getParent().refreshLocal(1, new NullProgressMonitor());
                } catch (CoreException e) {
                    AdtPlugin.log(e, null);
                }
                */
            }
        }
    }

    /*
    private void updateColor(Display display, RGB color, boolean isBackground) {
        // Button.setBackgroundColor does not work (at least not on OSX) so
        // we instead have to use Button.setImage with an image of the given
        // color
        BufferedImage coloredImage = ImageUtils.createColoredImage(60, 20, color);
        Image image = SwtUtils.convertToSwt(display, coloredImage, false, -1);

        if (isBackground) {
            //mBgColor = color;
            mBgButton.setImage(image);
        } else {
            mFgColor = color;
            mFgButton.setImage(image);
        }
    }*/
}
