package ru.exlmoto.jiconcreator;

import ru.exlmoto.jiconcreator.unsorted.CreateAssetSetWizardState;
import ru.exlmoto.jiconcreator.unsorted.JIconCreatorExtrasLibraryHere;
import ru.exlmoto.jiconcreator.unsorted.RGB;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class JIconCreatorGuiHelper {
    private CreateAssetSetWizardState createAssetSetWizardState = null;
    private JIconCreatorGui jIconCreatorGui = null;

    JIconCreatorGuiHelper(JIconCreatorGui jIconCreatorGuiArg) {
        jIconCreatorGui = jIconCreatorGuiArg;

        createAssetSetWizardState = new CreateAssetSetWizardState();
    }

    static boolean wtf(int cnt, Map<String, Map<String, BufferedImage>> categories, JLabel labelMdpiImage, JLabel labelHpdiImage, JLabel labelXhdpiImage, JLabel labelXxhdpiImage) {
        for (Map<String, BufferedImage> previews : categories.values()) {
            for (Map.Entry<String, BufferedImage> entry : previews.entrySet()) {
                BufferedImage image = entry.getValue();

                if (image != null) {
                    switch (cnt) {
                        default:
                            return true;
                        case 0:
                            labelMdpiImage.setIcon(new ImageIcon(image));
                            break;
                        case 1:
                            labelHpdiImage.setIcon(new ImageIcon(image));
                            break;
                        case 2:
                            labelXhdpiImage.setIcon(new ImageIcon(image));
                            break;
                        case 3:
                            labelXxhdpiImage.setIcon(new ImageIcon(image));
                            break;
                    }
                    cnt++;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /************/
    void imageTabBackColorRandomButton() {
        int red = new Random().nextInt(255 + 1);
        int green = new Random().nextInt(255 + 1);
        int blue = new Random().nextInt(255 + 1);
        System.out.println("Clicked Random button: " + red + " " + green + " " + blue);
        createAssetSetWizardState.background = new RGB(red, green, blue);
        jIconCreatorGui.getLabelColorShowImageL().setBackground(new Color(red, green, blue));
        updatePreviewIcons();
    }

    void updatePreviewIcons(/*labels*/) {
        int cnt = 0;
        Map<String, Map<String, BufferedImage>> categories = JIconCreatorExtrasLibraryHere.generateImages(createAssetSetWizardState, true);
        if (wtf(cnt, categories,
                jIconCreatorGui.getLabelMdpiI(), jIconCreatorGui.getLabelHdpiI(), jIconCreatorGui.getLabelXhdpiI(), jIconCreatorGui.getLabelXxhdpiI()))
            return;

        System.gc();
    }

    /************/

    void generateStyleMenuItems() {
        HashMap<String, String> installedStyles = new HashMap<>();
        ArrayList<JRadioButtonMenuItem> styleMenuItems = new ArrayList<>();

        LookAndFeelInfo[] styles = UIManager.getInstalledLookAndFeels();

        boolean isWindows = false;

        for (LookAndFeelInfo info : styles) {
            String styleName = info.getName();

            styleMenuItems.add(new JRadioButtonMenuItem(styleName));
            installedStyles.put(styleName, info.getClassName());

            if (styleName.startsWith("Windows")) {
                isWindows = true;
            }
        }

        for (final JRadioButtonMenuItem menuItem : styleMenuItems) {
            if (menuItem.getText().equals("Windows") && isWindows) {
                menuItem.setSelected(true);
            } else if (UIManager.getLookAndFeel().getName().equals(menuItem.getText())) {
                menuItem.setSelected(true);
            }

            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        UIManager.setLookAndFeel(installedStyles.get(menuItem.getText()));
                        SwingUtilities.updateComponentTreeUI(jIconCreatorGui.getRootPane());
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }
                }
            });

            jIconCreatorGui.getButtonGroupStyles().add(menuItem);
            jIconCreatorGui.getMenuStyle().add(menuItem);
        }

        if (isWindows) {
            try {
                UIManager.setLookAndFeel(installedStyles.get("Windows"));
                SwingUtilities.updateComponentTreeUI(jIconCreatorGui.getRootPane());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
    }
}
