/*
 * Created by JFormDesigner on Sun Aug 26 07:22:57 NOVT 2018
 */

package ru.exlmoto.jiconcreator;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * @author EXL
 */

public class JIconCreatorFrame extends JFrame {
    public JIconCreatorFrame() {
        initComponents();
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
        panelText = new JPanel();
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
            splitPane.setDividerLocation(500);

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
                                    .addComponent(checkBoxImageTrim)
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
                                        .addGroup(panelImageLayout.createParallelGroup()
                                            .addComponent(radioButtonImageCrop)
                                            .addComponent(radioButtonImageNone))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelImageLayout.createParallelGroup()
                                            .addGroup(panelImageLayout.createSequentialGroup()
                                                .addComponent(radioButtonImageSquare)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButtonImageCircle))
                                            .addComponent(radioButtonImageCenter)))
                                    .addGroup(panelImageLayout.createSequentialGroup()
                                        .addComponent(buttonImageColorChoose)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonImageColorRandom)))
                                .addContainerGap(7, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panelImageLayout.createSequentialGroup()
                                .addContainerGap(238, Short.MAX_VALUE)
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
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
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
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelImageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelImageColor)
                                    .addComponent(buttonImageColorChoose)
                                    .addComponent(buttonImageColorRandom))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
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

                    GroupLayout panelClipartLayout = new GroupLayout(panelClipart);
                    panelClipart.setLayout(panelClipartLayout);
                    panelClipartLayout.setHorizontalGroup(
                        panelClipartLayout.createParallelGroup()
                            .addGap(0, 499, Short.MAX_VALUE)
                    );
                    panelClipartLayout.setVerticalGroup(
                        panelClipartLayout.createParallelGroup()
                            .addGap(0, 371, Short.MAX_VALUE)
                    );
                }
                tabbedPane.addTab(bundle.getString("JIconCreatorFrame.panelClipart.tab.title"), panelClipart);

                //======== panelText ========
                {

                    GroupLayout panelTextLayout = new GroupLayout(panelText);
                    panelText.setLayout(panelTextLayout);
                    panelTextLayout.setHorizontalGroup(
                        panelTextLayout.createParallelGroup()
                            .addGap(0, 499, Short.MAX_VALUE)
                    );
                    panelTextLayout.setVerticalGroup(
                        panelTextLayout.createParallelGroup()
                            .addGap(0, 371, Short.MAX_VALUE)
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
    private JPanel panelText;
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
