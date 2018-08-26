/*
 * Created by JFormDesigner on Sun Aug 26 07:22:57 NOVT 2018
 */

package ru.exlmoto.jiconcreator;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

/**
 * @author EXL
 */

public class JIconCreatorFrame extends JFrame {

    private CreateAssetSetWizardState createAssetSetWizardState = null;

    public JIconCreatorFrame() {
        initComponents();

        createAssetSetWizardState = new CreateAssetSetWizardState();
        updatePreviewIcons();
    }

    private void buttonImageColorRandomActionPerformed(ActionEvent e) {
        System.out.println("Clicked!");
        createAssetSetWizardState.background = new RGB(
                new Random().nextInt(255 + 1),
                new Random().nextInt(255 + 1),
                new Random().nextInt(255 + 1));
        updatePreviewIcons();
    }
    public void updatePreviewIcons() {
        int cnt = 0;
        Map<String, Map<String, BufferedImage>> categories = JIconCreator.generateImages(createAssetSetWizardState, true);
        if (wtf(cnt, categories, labelMdpiImage, labelHpdiImage, labelXhdpiImage, labelXxhdpiImage)) return;

        System.gc();
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Serg Koles
        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuItemPreview = new JMenuItem();
        menuItemSave = new JMenuItem();
        menuItemSaveTo = new JMenuItem();
        menuItemExit = new JMenuItem();
        menuStyle = new JMenu();
        menuHelp = new JMenu();
        menuItem4 = new JMenuItem();
        splitPane = new JSplitPane();
        tabbedPane = new JTabbedPane();
        panelImage = new JPanel();
        labelImageFile = new JLabel();
        textFieldImageFile = new JTextField();
        buttonImageBrowse = new JButton();
        buttonImageReset = new JButton();
        labelImageOptions = new JLabel();
        checkBoxImageTrim = new JCheckBox();
        labelImagePadding = new JLabel();
        sliderImagePadding = new JSlider();
        labelImagePaddingPercent = new JLabel();
        labelImageScaling = new JLabel();
        radioButtonImageCrop = new JRadioButton();
        radioButtonImageCenter = new JRadioButton();
        labelImageShape = new JLabel();
        radioButtonImageNone = new JRadioButton();
        radioButtonImageSquare = new JRadioButton();
        radioButtonImageCircle = new JRadioButton();
        labelImageColor = new JLabel();
        buttonImageColorChoose = new JButton();
        buttonImageColorRandom = new JButton();
        buttonImagePreview = new JButton();
        buttonImageSaveTo = new JButton();
        buttonImageSave = new JButton();
        panelClipart = new JPanel();
        labelClipartText = new JLabel();
        labelClipartPreview = new JLabel();
        buttonClipartChoose = new JButton();
        buttonClipartReset = new JButton();
        labelClipartOptions = new JLabel();
        checkBoxClipartTrim = new JCheckBox();
        labelClipartPadding = new JLabel();
        sliderClipartPadding = new JSlider();
        labelClipartPaddingPercent = new JLabel();
        labelClipartScaling = new JLabel();
        labelClipartShape = new JLabel();
        radioButtonClipartCrop = new JRadioButton();
        radioButtonClipartNone = new JRadioButton();
        radioButtonClipartCenter = new JRadioButton();
        radioButtonClipartSquare = new JRadioButton();
        radioButtonClipartCircle = new JRadioButton();
        labelClipartColor = new JLabel();
        buttonClipartColorChoose = new JButton();
        labelClipartColorF = new JLabel();
        buttonClipartColorChooseF = new JButton();
        buttonClipartPreview = new JButton();
        buttonClipartSaveTo = new JButton();
        buttonClipartSave = new JButton();
        buttonClipartColorRandom = new JButton();
        buttonClipartColorRandomF = new JButton();
        panelText = new JPanel();
        labelText = new JLabel();
        textFieldText = new JTextField();
        buttonTextReset = new JButton();
        labelTextFont = new JLabel();
        buttonTextFontChoose = new JButton();
        labelTextOptions = new JLabel();
        checkBoxTextTrim = new JCheckBox();
        labelTextPadding = new JLabel();
        sliderTextPadding = new JSlider();
        labelTextPaddingPercent = new JLabel();
        labelTextScaling = new JLabel();
        labelTextShape = new JLabel();
        radioButtonTextCrop = new JRadioButton();
        radioButtonTextCenter = new JRadioButton();
        radioButtonTextNone = new JRadioButton();
        radioButtonTextSquare = new JRadioButton();
        radioButtonTextCircle = new JRadioButton();
        labelTextColor = new JLabel();
        labelTextColorF = new JLabel();
        buttonTextColorChoose = new JButton();
        buttonTextColorChooseF = new JButton();
        buttonTextPreview = new JButton();
        buttonTextSaveTo = new JButton();
        buttonTextSave = new JButton();
        buttonTextColorRandom = new JButton();
        buttonTextColorRandomF = new JButton();
        panelPreview = new JPanel();
        labelMdpiText = new JLabel();
        labelMdpiImage = new JLabel();
        labelHdpiText = new JLabel();
        labelHpdiImage = new JLabel();
        labelXhdpiText = new JLabel();
        labelXhdpiImage = new JLabel();
        labelXxhdpiText = new JLabel();
        labelXxhdpiImage = new JLabel();

        //======== this ========
        setName("frame");
        setTitle("JIconCreator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

        //======== menuBar ========
        {

            //======== menuFile ========
            {
                menuFile.setText(bundle.getString("JIconCreatorFrame.menuFile.text"));

                //---- menuItemPreview ----
                menuItemPreview.setText(bundle.getString("JIconCreatorFrame.menuItemPreview.text"));
                menuFile.add(menuItemPreview);
                menuFile.addSeparator();

                //---- menuItemSave ----
                menuItemSave.setText(bundle.getString("JIconCreatorFrame.menuItemSave.text"));
                menuFile.add(menuItemSave);

                //---- menuItemSaveTo ----
                menuItemSaveTo.setText(bundle.getString("JIconCreatorFrame.menuItemSaveTo.text"));
                menuFile.add(menuItemSaveTo);
                menuFile.addSeparator();

                //---- menuItemExit ----
                menuItemExit.setText(bundle.getString("JIconCreatorFrame.menuItemExit.text"));
                menuFile.add(menuItemExit);
            }
            menuBar.add(menuFile);

            //======== menuStyle ========
            {
                menuStyle.setText(bundle.getString("JIconCreatorFrame.menuStyle.text"));
            }
            menuBar.add(menuStyle);

            //======== menuHelp ========
            {
                menuHelp.setText(bundle.getString("JIconCreatorFrame.menuHelp.text"));

                //---- menuItem4 ----
                menuItem4.setText(bundle.getString("JIconCreatorFrame.menuItem4.text"));
                menuHelp.add(menuItem4);
            }
            menuBar.add(menuHelp);
        }
        setJMenuBar(menuBar);

        //======== splitPane ========
        {
            splitPane.setDividerLocation(550);

            //======== tabbedPane ========
            {

                //======== panelImage ========
                {

                    // JFormDesigner evaluation mark
                    panelImage.setBorder(new javax.swing.border.CompoundBorder(
                        new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), panelImage.getBorder())); panelImage.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


                    //---- labelImageFile ----
                    labelImageFile.setText(bundle.getString("JIconCreatorFrame.labelImageFile.text"));

                    //---- buttonImageBrowse ----
                    buttonImageBrowse.setText(bundle.getString("JIconCreatorFrame.buttonImageBrowse.text"));

                    //---- buttonImageReset ----
                    buttonImageReset.setText(bundle.getString("JIconCreatorFrame.buttonImageReset.text"));

                    //---- labelImageOptions ----
                    labelImageOptions.setText(bundle.getString("JIconCreatorFrame.labelImageOptions.text"));

                    //---- checkBoxImageTrim ----
                    checkBoxImageTrim.setText(bundle.getString("JIconCreatorFrame.checkBoxImageTrim.text"));

                    //---- labelImagePadding ----
                    labelImagePadding.setText(bundle.getString("JIconCreatorFrame.labelImagePadding.text"));

                    //---- labelImagePaddingPercent ----
                    labelImagePaddingPercent.setText(bundle.getString("JIconCreatorFrame.labelImagePaddingPercent.text"));

                    //---- labelImageScaling ----
                    labelImageScaling.setText(bundle.getString("JIconCreatorFrame.labelImageScaling.text"));

                    //---- radioButtonImageCrop ----
                    radioButtonImageCrop.setText(bundle.getString("JIconCreatorFrame.radioButtonImageCrop.text"));

                    //---- radioButtonImageCenter ----
                    radioButtonImageCenter.setText(bundle.getString("JIconCreatorFrame.radioButtonImageCenter.text"));

                    //---- labelImageShape ----
                    labelImageShape.setText(bundle.getString("JIconCreatorFrame.labelImageShape.text"));

                    //---- radioButtonImageNone ----
                    radioButtonImageNone.setText(bundle.getString("JIconCreatorFrame.radioButtonImageNone.text"));

                    //---- radioButtonImageSquare ----
                    radioButtonImageSquare.setText(bundle.getString("JIconCreatorFrame.radioButtonImageSquare.text"));

                    //---- radioButtonImageCircle ----
                    radioButtonImageCircle.setText(bundle.getString("JIconCreatorFrame.radioButtonImageCircle.text"));

                    //---- labelImageColor ----
                    labelImageColor.setText(bundle.getString("JIconCreatorFrame.labelImageColor.text"));

                    //---- buttonImageColorChoose ----
                    buttonImageColorChoose.setText(bundle.getString("JIconCreatorFrame.buttonImageColorChoose.text"));

                    //---- buttonImageColorRandom ----
                    buttonImageColorRandom.setText(bundle.getString("JIconCreatorFrame.buttonImageColorRandom.text"));
                    buttonImageColorRandom.addActionListener(e -> buttonImageColorRandomActionPerformed(e));

                    //---- buttonImagePreview ----
                    buttonImagePreview.setText(bundle.getString("JIconCreatorFrame.buttonImagePreview.text"));

                    //---- buttonImageSaveTo ----
                    buttonImageSaveTo.setText(bundle.getString("JIconCreatorFrame.buttonImageSaveTo.text"));

                    //---- buttonImageSave ----
                    buttonImageSave.setText(bundle.getString("JIconCreatorFrame.buttonImageSave.text"));

                    GroupLayout panelImageLayout = new GroupLayout(panelImage);
                    panelImage.setLayout(panelImageLayout);
                    panelImageLayout.setHorizontalGroup(
                        panelImageLayout.createParallelGroup()
                            .addGroup(panelImageLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelImageColor)
                                    .addComponent(labelImageShape)
                                    .addComponent(labelImageScaling)
                                    .addComponent(labelImagePadding)
                                    .addComponent(labelImageOptions)
                                    .addComponent(labelImageFile))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelImageLayout.createParallelGroup()
                                    .addGroup(panelImageLayout.createSequentialGroup()
                                        .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(sliderImagePadding, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                            .addComponent(textFieldImageFile, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelImageLayout.createParallelGroup()
                                            .addGroup(panelImageLayout.createSequentialGroup()
                                                .addComponent(buttonImageBrowse)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonImageReset))
                                            .addComponent(labelImagePaddingPercent)))
                                    .addGroup(panelImageLayout.createSequentialGroup()
                                        .addComponent(radioButtonImageNone)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radioButtonImageSquare)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radioButtonImageCircle))
                                    .addGroup(panelImageLayout.createSequentialGroup()
                                        .addComponent(buttonImageColorChoose)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonImageColorRandom))
                                    .addGroup(panelImageLayout.createSequentialGroup()
                                        .addComponent(radioButtonImageCrop)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(radioButtonImageCenter))
                                    .addComponent(checkBoxImageTrim))
                                .addContainerGap(57, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panelImageLayout.createSequentialGroup()
                                .addContainerGap(288, Short.MAX_VALUE)
                                .addComponent(buttonImageSave)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonImageSaveTo)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonImagePreview)
                                .addContainerGap())
                    );
                    panelImageLayout.setVerticalGroup(
                        panelImageLayout.createParallelGroup()
                            .addGroup(panelImageLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelImageFile)
                                    .addComponent(textFieldImageFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonImageBrowse)
                                    .addComponent(buttonImageReset))
                                .addGap(22, 22, 22)
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelImageLayout.createSequentialGroup()
                                        .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelImageOptions)
                                            .addComponent(checkBoxImageTrim))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(labelImagePadding)
                                            .addComponent(sliderImagePadding, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(labelImagePaddingPercent))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelImageScaling)
                                    .addComponent(radioButtonImageCrop)
                                    .addComponent(radioButtonImageCenter))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelImageShape)
                                    .addComponent(radioButtonImageNone)
                                    .addComponent(radioButtonImageSquare)
                                    .addComponent(radioButtonImageCircle))
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelImageColor)
                                    .addComponent(buttonImageColorChoose)
                                    .addComponent(buttonImageColorRandom))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonImagePreview)
                                    .addComponent(buttonImageSaveTo)
                                    .addComponent(buttonImageSave))
                                .addContainerGap())
                    );
                }
                tabbedPane.addTab(bundle.getString("JIconCreatorFrame.panelImage.tab.title"), panelImage);

                //======== panelClipart ========
                {

                    //---- labelClipartText ----
                    labelClipartText.setText(bundle.getString("JIconCreatorFrame.labelClipartText.text"));

                    //---- labelClipartPreview ----
                    labelClipartPreview.setMaximumSize(new Dimension(32, 32));
                    labelClipartPreview.setMinimumSize(new Dimension(32, 32));

                    //---- buttonClipartChoose ----
                    buttonClipartChoose.setText(bundle.getString("JIconCreatorFrame.buttonClipartChoose.text"));

                    //---- buttonClipartReset ----
                    buttonClipartReset.setText(bundle.getString("JIconCreatorFrame.buttonClipartReset.text"));

                    //---- labelClipartOptions ----
                    labelClipartOptions.setText(bundle.getString("JIconCreatorFrame.labelClipartOptions.text"));

                    //---- checkBoxClipartTrim ----
                    checkBoxClipartTrim.setText(bundle.getString("JIconCreatorFrame.checkBoxClipartTrim.text"));

                    //---- labelClipartPadding ----
                    labelClipartPadding.setText(bundle.getString("JIconCreatorFrame.labelClipartPadding.text"));

                    //---- labelClipartPaddingPercent ----
                    labelClipartPaddingPercent.setText(bundle.getString("JIconCreatorFrame.labelClipartPaddingPercent.text"));

                    //---- labelClipartScaling ----
                    labelClipartScaling.setText(bundle.getString("JIconCreatorFrame.labelClipartScaling.text"));

                    //---- labelClipartShape ----
                    labelClipartShape.setText(bundle.getString("JIconCreatorFrame.labelClipartShape.text"));

                    //---- radioButtonClipartCrop ----
                    radioButtonClipartCrop.setText(bundle.getString("JIconCreatorFrame.radioButtonClipartCrop.text"));

                    //---- radioButtonClipartNone ----
                    radioButtonClipartNone.setText(bundle.getString("JIconCreatorFrame.radioButtonClipartNone.text"));

                    //---- radioButtonClipartCenter ----
                    radioButtonClipartCenter.setText(bundle.getString("JIconCreatorFrame.radioButtonClipartCenter.text"));

                    //---- radioButtonClipartSquare ----
                    radioButtonClipartSquare.setText(bundle.getString("JIconCreatorFrame.radioButtonClipartSquare.text"));

                    //---- radioButtonClipartCircle ----
                    radioButtonClipartCircle.setText(bundle.getString("JIconCreatorFrame.radioButtonClipartCircle.text"));

                    //---- labelClipartColor ----
                    labelClipartColor.setText(bundle.getString("JIconCreatorFrame.labelClipartColor.text"));

                    //---- buttonClipartColorChoose ----
                    buttonClipartColorChoose.setText(bundle.getString("JIconCreatorFrame.buttonClipartColorChoose.text"));

                    //---- labelClipartColorF ----
                    labelClipartColorF.setText(bundle.getString("JIconCreatorFrame.labelClipartColorF.text"));

                    //---- buttonClipartColorChooseF ----
                    buttonClipartColorChooseF.setText(bundle.getString("JIconCreatorFrame.buttonClipartColorChooseF.text"));

                    //---- buttonClipartPreview ----
                    buttonClipartPreview.setText(bundle.getString("JIconCreatorFrame.buttonClipartPreview.text"));

                    //---- buttonClipartSaveTo ----
                    buttonClipartSaveTo.setText(bundle.getString("JIconCreatorFrame.buttonClipartSaveTo.text"));

                    //---- buttonClipartSave ----
                    buttonClipartSave.setText(bundle.getString("JIconCreatorFrame.buttonClipartSave.text"));

                    //---- buttonClipartColorRandom ----
                    buttonClipartColorRandom.setText(bundle.getString("JIconCreatorFrame.buttonClipartColorRandom.text"));

                    //---- buttonClipartColorRandomF ----
                    buttonClipartColorRandomF.setText(bundle.getString("JIconCreatorFrame.buttonClipartColorRandomF.text"));

                    GroupLayout panelClipartLayout = new GroupLayout(panelClipart);
                    panelClipart.setLayout(panelClipartLayout);
                    panelClipartLayout.setHorizontalGroup(
                        panelClipartLayout.createParallelGroup()
                            .addGroup(panelClipartLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelClipartColorF)
                                    .addComponent(labelClipartColor)
                                    .addComponent(labelClipartShape)
                                    .addComponent(labelClipartScaling)
                                    .addComponent(labelClipartPadding)
                                    .addComponent(labelClipartOptions)
                                    .addComponent(labelClipartText))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelClipartLayout.createParallelGroup()
                                    .addGroup(panelClipartLayout.createSequentialGroup()
                                        .addComponent(buttonClipartChoose)
                                        .addGroup(panelClipartLayout.createParallelGroup()
                                            .addGroup(panelClipartLayout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(labelClipartPreview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelClipartLayout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonClipartReset))))
                                    .addComponent(checkBoxClipartTrim)
                                    .addGroup(panelClipartLayout.createSequentialGroup()
                                        .addComponent(sliderClipartPadding, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelClipartPaddingPercent))
                                    .addGroup(panelClipartLayout.createSequentialGroup()
                                        .addGroup(panelClipartLayout.createParallelGroup()
                                            .addComponent(radioButtonClipartCrop)
                                            .addComponent(radioButtonClipartNone))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelClipartLayout.createParallelGroup()
                                            .addGroup(panelClipartLayout.createSequentialGroup()
                                                .addComponent(radioButtonClipartSquare)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButtonClipartCircle))
                                            .addComponent(radioButtonClipartCenter)))
                                    .addGroup(panelClipartLayout.createSequentialGroup()
                                        .addComponent(buttonClipartColorChoose)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonClipartColorRandom))
                                    .addGroup(panelClipartLayout.createSequentialGroup()
                                        .addComponent(buttonClipartColorChooseF)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonClipartColorRandomF)))
                                .addContainerGap(207, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panelClipartLayout.createSequentialGroup()
                                .addContainerGap(288, Short.MAX_VALUE)
                                .addComponent(buttonClipartSave)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonClipartSaveTo)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonClipartPreview)
                                .addContainerGap())
                    );
                    panelClipartLayout.setVerticalGroup(
                        panelClipartLayout.createParallelGroup()
                            .addGroup(panelClipartLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelClipartPaddingPercent)
                                    .addGroup(panelClipartLayout.createSequentialGroup()
                                        .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelClipartText)
                                            .addComponent(labelClipartPreview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buttonClipartChoose)
                                            .addComponent(buttonClipartReset))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelClipartOptions)
                                            .addComponent(checkBoxClipartTrim))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(labelClipartPadding)
                                            .addComponent(sliderClipartPadding, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelClipartScaling)
                                    .addComponent(radioButtonClipartCrop)
                                    .addComponent(radioButtonClipartCenter))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelClipartShape)
                                    .addComponent(radioButtonClipartNone)
                                    .addComponent(radioButtonClipartSquare)
                                    .addComponent(radioButtonClipartCircle))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelClipartColor)
                                    .addComponent(buttonClipartColorChoose)
                                    .addComponent(buttonClipartColorRandom))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelClipartColorF)
                                    .addComponent(buttonClipartColorChooseF)
                                    .addComponent(buttonClipartColorRandomF))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                                .addGroup(panelClipartLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonClipartPreview)
                                    .addComponent(buttonClipartSaveTo)
                                    .addComponent(buttonClipartSave))
                                .addContainerGap())
                    );
                }
                tabbedPane.addTab(bundle.getString("JIconCreatorFrame.panelClipart.tab.title"), panelClipart);

                //======== panelText ========
                {

                    //---- labelText ----
                    labelText.setText(bundle.getString("JIconCreatorFrame.labelText.text"));

                    //---- buttonTextReset ----
                    buttonTextReset.setText(bundle.getString("JIconCreatorFrame.buttonTextReset.text"));

                    //---- labelTextFont ----
                    labelTextFont.setText(bundle.getString("JIconCreatorFrame.labelTextFont.text"));

                    //---- buttonTextFontChoose ----
                    buttonTextFontChoose.setText(bundle.getString("JIconCreatorFrame.buttonTextFontChoose.text"));

                    //---- labelTextOptions ----
                    labelTextOptions.setText(bundle.getString("JIconCreatorFrame.labelTextOptions.text"));

                    //---- checkBoxTextTrim ----
                    checkBoxTextTrim.setText(bundle.getString("JIconCreatorFrame.checkBoxTextTrim.text"));

                    //---- labelTextPadding ----
                    labelTextPadding.setText(bundle.getString("JIconCreatorFrame.labelTextPadding.text"));

                    //---- labelTextPaddingPercent ----
                    labelTextPaddingPercent.setText(bundle.getString("JIconCreatorFrame.labelTextPaddingPercent.text"));

                    //---- labelTextScaling ----
                    labelTextScaling.setText(bundle.getString("JIconCreatorFrame.labelTextScaling.text"));

                    //---- labelTextShape ----
                    labelTextShape.setText(bundle.getString("JIconCreatorFrame.labelTextShape.text"));

                    //---- radioButtonTextCrop ----
                    radioButtonTextCrop.setText(bundle.getString("JIconCreatorFrame.radioButtonTextCrop.text"));

                    //---- radioButtonTextCenter ----
                    radioButtonTextCenter.setText(bundle.getString("JIconCreatorFrame.radioButtonTextCenter.text"));

                    //---- radioButtonTextNone ----
                    radioButtonTextNone.setText(bundle.getString("JIconCreatorFrame.radioButtonTextNone.text"));

                    //---- radioButtonTextSquare ----
                    radioButtonTextSquare.setText(bundle.getString("JIconCreatorFrame.radioButtonTextSquare.text"));

                    //---- radioButtonTextCircle ----
                    radioButtonTextCircle.setText(bundle.getString("JIconCreatorFrame.radioButtonTextCircle.text"));

                    //---- labelTextColor ----
                    labelTextColor.setText(bundle.getString("JIconCreatorFrame.labelTextColor.text"));

                    //---- labelTextColorF ----
                    labelTextColorF.setText(bundle.getString("JIconCreatorFrame.labelTextColorF.text"));

                    //---- buttonTextColorChoose ----
                    buttonTextColorChoose.setText(bundle.getString("JIconCreatorFrame.buttonTextColorChoose.text"));

                    //---- buttonTextColorChooseF ----
                    buttonTextColorChooseF.setText(bundle.getString("JIconCreatorFrame.buttonTextColorChooseF.text"));

                    //---- buttonTextPreview ----
                    buttonTextPreview.setText(bundle.getString("JIconCreatorFrame.buttonTextPreview.text"));

                    //---- buttonTextSaveTo ----
                    buttonTextSaveTo.setText(bundle.getString("JIconCreatorFrame.buttonTextSaveTo.text"));

                    //---- buttonTextSave ----
                    buttonTextSave.setText(bundle.getString("JIconCreatorFrame.buttonTextSave.text"));

                    //---- buttonTextColorRandom ----
                    buttonTextColorRandom.setText(bundle.getString("JIconCreatorFrame.buttonTextColorRandom.text"));

                    //---- buttonTextColorRandomF ----
                    buttonTextColorRandomF.setText(bundle.getString("JIconCreatorFrame.buttonTextColorRandomF.text"));

                    GroupLayout panelTextLayout = new GroupLayout(panelText);
                    panelText.setLayout(panelTextLayout);
                    panelTextLayout.setHorizontalGroup(
                        panelTextLayout.createParallelGroup()
                            .addGroup(panelTextLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTextColorF)
                                    .addComponent(labelTextColor)
                                    .addComponent(labelTextShape)
                                    .addComponent(labelTextScaling)
                                    .addComponent(labelTextOptions)
                                    .addComponent(labelTextFont)
                                    .addComponent(labelText)
                                    .addComponent(labelTextPadding))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTextLayout.createParallelGroup()
                                    .addComponent(buttonTextFontChoose)
                                    .addComponent(checkBoxTextTrim)
                                    .addGroup(panelTextLayout.createSequentialGroup()
                                        .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(sliderTextPadding, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                            .addComponent(textFieldText, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelTextLayout.createParallelGroup()
                                            .addComponent(buttonTextReset)
                                            .addComponent(labelTextPaddingPercent)))
                                    .addGroup(panelTextLayout.createSequentialGroup()
                                        .addGroup(panelTextLayout.createParallelGroup()
                                            .addComponent(radioButtonTextCrop)
                                            .addComponent(radioButtonTextNone))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelTextLayout.createParallelGroup()
                                            .addGroup(panelTextLayout.createSequentialGroup()
                                                .addComponent(radioButtonTextSquare)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButtonTextCircle))
                                            .addComponent(radioButtonTextCenter)))
                                    .addGroup(panelTextLayout.createSequentialGroup()
                                        .addComponent(buttonTextColorChoose)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonTextColorRandom))
                                    .addGroup(panelTextLayout.createSequentialGroup()
                                        .addComponent(buttonTextColorChooseF)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonTextColorRandomF)))
                                .addContainerGap(146, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panelTextLayout.createSequentialGroup()
                                .addContainerGap(288, Short.MAX_VALUE)
                                .addComponent(buttonTextSave)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonTextSaveTo)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonTextPreview)
                                .addContainerGap())
                    );
                    panelTextLayout.setVerticalGroup(
                        panelTextLayout.createParallelGroup()
                            .addGroup(panelTextLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelText)
                                    .addComponent(textFieldText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonTextReset))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelTextLayout.createSequentialGroup()
                                        .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelTextFont)
                                            .addComponent(buttonTextFontChoose))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelTextOptions)
                                            .addComponent(checkBoxTextTrim))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(labelTextPadding)
                                            .addComponent(sliderTextPadding, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(labelTextPaddingPercent))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelTextScaling)
                                    .addComponent(radioButtonTextCrop)
                                    .addComponent(radioButtonTextCenter))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelTextShape)
                                    .addComponent(radioButtonTextNone)
                                    .addComponent(radioButtonTextSquare)
                                    .addComponent(radioButtonTextCircle))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelTextColor)
                                    .addComponent(buttonTextColorChoose)
                                    .addComponent(buttonTextColorRandom))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelTextColorF)
                                    .addComponent(buttonTextColorChooseF)
                                    .addComponent(buttonTextColorRandomF))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(panelTextLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonTextPreview)
                                    .addComponent(buttonTextSaveTo)
                                    .addComponent(buttonTextSave))
                                .addContainerGap())
                    );
                }
                tabbedPane.addTab(bundle.getString("JIconCreatorFrame.panelText.tab.title"), panelText);
            }
            splitPane.setLeftComponent(tabbedPane);

            //======== panelPreview ========
            {
                panelPreview.setLayout(new BoxLayout(panelPreview, BoxLayout.PAGE_AXIS));

                //---- labelMdpiText ----
                labelMdpiText.setText(bundle.getString("JIconCreatorFrame.labelMdpiText.text"));
                panelPreview.add(labelMdpiText);
                panelPreview.add(labelMdpiImage);

                //---- labelHdpiText ----
                labelHdpiText.setText(bundle.getString("JIconCreatorFrame.labelHdpiText.text"));
                panelPreview.add(labelHdpiText);
                panelPreview.add(labelHpdiImage);

                //---- labelXhdpiText ----
                labelXhdpiText.setText(bundle.getString("JIconCreatorFrame.labelXhdpiText.text"));
                panelPreview.add(labelXhdpiText);
                panelPreview.add(labelXhdpiImage);

                //---- labelXxhdpiText ----
                labelXxhdpiText.setText(bundle.getString("JIconCreatorFrame.labelXxhdpiText.text"));
                panelPreview.add(labelXxhdpiText);
                panelPreview.add(labelXxhdpiImage);
            }
            splitPane.setRightComponent(panelPreview);
        }
        contentPane.add(splitPane);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Serg Koles
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuItemPreview;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemSaveTo;
    private JMenuItem menuItemExit;
    private JMenu menuStyle;
    private JMenu menuHelp;
    private JMenuItem menuItem4;
    private JSplitPane splitPane;
    private JTabbedPane tabbedPane;
    private JPanel panelImage;
    private JLabel labelImageFile;
    private JTextField textFieldImageFile;
    private JButton buttonImageBrowse;
    private JButton buttonImageReset;
    private JLabel labelImageOptions;
    private JCheckBox checkBoxImageTrim;
    private JLabel labelImagePadding;
    private JSlider sliderImagePadding;
    private JLabel labelImagePaddingPercent;
    private JLabel labelImageScaling;
    private JRadioButton radioButtonImageCrop;
    private JRadioButton radioButtonImageCenter;
    private JLabel labelImageShape;
    private JRadioButton radioButtonImageNone;
    private JRadioButton radioButtonImageSquare;
    private JRadioButton radioButtonImageCircle;
    private JLabel labelImageColor;
    private JButton buttonImageColorChoose;
    private JButton buttonImageColorRandom;
    private JButton buttonImagePreview;
    private JButton buttonImageSaveTo;
    private JButton buttonImageSave;
    private JPanel panelClipart;
    private JLabel labelClipartText;
    private JLabel labelClipartPreview;
    private JButton buttonClipartChoose;
    private JButton buttonClipartReset;
    private JLabel labelClipartOptions;
    private JCheckBox checkBoxClipartTrim;
    private JLabel labelClipartPadding;
    private JSlider sliderClipartPadding;
    private JLabel labelClipartPaddingPercent;
    private JLabel labelClipartScaling;
    private JLabel labelClipartShape;
    private JRadioButton radioButtonClipartCrop;
    private JRadioButton radioButtonClipartNone;
    private JRadioButton radioButtonClipartCenter;
    private JRadioButton radioButtonClipartSquare;
    private JRadioButton radioButtonClipartCircle;
    private JLabel labelClipartColor;
    private JButton buttonClipartColorChoose;
    private JLabel labelClipartColorF;
    private JButton buttonClipartColorChooseF;
    private JButton buttonClipartPreview;
    private JButton buttonClipartSaveTo;
    private JButton buttonClipartSave;
    private JButton buttonClipartColorRandom;
    private JButton buttonClipartColorRandomF;
    private JPanel panelText;
    private JLabel labelText;
    private JTextField textFieldText;
    private JButton buttonTextReset;
    private JLabel labelTextFont;
    private JButton buttonTextFontChoose;
    private JLabel labelTextOptions;
    private JCheckBox checkBoxTextTrim;
    private JLabel labelTextPadding;
    private JSlider sliderTextPadding;
    private JLabel labelTextPaddingPercent;
    private JLabel labelTextScaling;
    private JLabel labelTextShape;
    private JRadioButton radioButtonTextCrop;
    private JRadioButton radioButtonTextCenter;
    private JRadioButton radioButtonTextNone;
    private JRadioButton radioButtonTextSquare;
    private JRadioButton radioButtonTextCircle;
    private JLabel labelTextColor;
    private JLabel labelTextColorF;
    private JButton buttonTextColorChoose;
    private JButton buttonTextColorChooseF;
    private JButton buttonTextPreview;
    private JButton buttonTextSaveTo;
    private JButton buttonTextSave;
    private JButton buttonTextColorRandom;
    private JButton buttonTextColorRandomF;
    private JPanel panelPreview;
    private JLabel labelMdpiText;
    private JLabel labelMdpiImage;
    private JLabel labelHdpiText;
    private JLabel labelHpdiImage;
    private JLabel labelXhdpiText;
    private JLabel labelXhdpiImage;
    private JLabel labelXxhdpiText;
    private JLabel labelXxhdpiImage;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
