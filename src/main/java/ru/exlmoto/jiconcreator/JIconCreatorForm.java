package ru.exlmoto.jiconcreator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class JIconCreatorForm {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JCheckBox trimSurroundingBlankSpaceCheckBox;
    private JButton chooseColorButton;
    private JButton cropButton;
    private JButton centerButton;
    private JButton noneButton;
    private JTextField imageTextField;
    private JButton browseImageButton;
    private JSlider paddingSlider;
    private JButton squareButton;
    private JButton circleButton;
    private JLabel mdpiLabel;
    private JLabel hdpiLabel;
    private JLabel xhdpiLabel;
    private JLabel xxhdpiLabel;
    private JButton randomColorButton;

    private CreateAssetSetWizardState createAssetSetWizardState = null;

    public JIconCreatorForm() {
        createAssetSetWizardState = new CreateAssetSetWizardState();
        // TODO: TODO: TODO: First settings

        createAssetSetWizardState.imagePath =
                new File(Objects.requireNonNull(getClass().getClassLoader().getResource("images/clipart/big/android.png")).getFile());

        randomColorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Clicked!");
                createAssetSetWizardState.background = new RGB(
                        new Random().nextInt(255 + 1),
                        new Random().nextInt(255 + 1),
                        new Random().nextInt(255 + 1));
                updatePreviewIcons();
            }
        });

        browseImageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(mainPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    //log.append("Opening: " + file.getName() + "." + newline);

                    imageTextField.setText(file.getAbsolutePath());

                    createAssetSetWizardState.imagePath = file;
                    updatePreviewIcons();
                } else {
                    //log.append("Open command cancelled by user." + newline);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
/*
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image != null)
            mdpiLabel.setIcon(new ImageIcon(image));
*/
        /*
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image != null) {
            final BufferedImage finalBufferedImage = image;
            mdpiPane = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(finalBufferedImage , 0, 0, null);
                }
            };

            mdpiPane.setSize(48, 48);
        } */
    }

    // public???
    public void updatePreviewIcons() {
        int cnt = 0;
        Map<String, Map<String, BufferedImage>> categories = JIconCreator.generateImages(createAssetSetWizardState, true);
        for (Map<String, BufferedImage> previews : categories.values()) {
            for (Map.Entry<String, BufferedImage> entry : previews.entrySet()) {
                BufferedImage image = entry.getValue();

                if (image != null) {
                    switch (cnt) {
                        default:
                            return;
                        case 0:
                            mdpiLabel.setIcon(new ImageIcon(image));
                            break;
                        case 1:
                            hdpiLabel.setIcon(new ImageIcon(image));
                            break;
                        case 2:
                            xhdpiLabel.setIcon(new ImageIcon(image));
                            break;
                        case 3:
                            xxhdpiLabel.setIcon(new ImageIcon(image));
                            break;
                    }
                    cnt++;
                } else {
                    return;
                }
            }
        }

        System.gc();
    }

    public JPanel getMainPane() {
        createUIComponents();
        updatePreviewIcons();
        return mainPanel;
    }
}
