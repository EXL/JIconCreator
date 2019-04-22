package ru.exlmoto.jiconcreator;

import com.oracle.docs.ImageFilter;
import com.oracle.docs.ImagePreview;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author exl
 */
public class JIconCreatorGui extends javax.swing.JFrame {

    JIconCreatorGuiHelper jIconCreatorGuiHelper = null;
    JIconCreatorOptions jIconCreatorOptions = null;

    Timer statusTimer = null;

    private void updateOptionsFromForm() {
        int currentShape = JIconCreatorOptions.SHAPE_SQUARE;

        switch (jTabbedPane.getSelectedIndex()) {
            default:
            case JIconCreatorOptions.ICON_IMAGE: {
                jIconCreatorOptions.setIconType(JIconCreatorOptions.ICON_IMAGE);
                jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
                updateOptionsFromFormAux(currentShape, jCheckBoxTrimImage, jSliderPaddingImage,
                        jRadioButtonCenterImage, jRadioButtonNoneImage, jRadioButtonCircleImage, jLabelColorShowImageL);
                break;
            }
            case JIconCreatorOptions.ICON_CLIPART: {
                jIconCreatorOptions.setIconType(JIconCreatorOptions.ICON_CLIPART);
                jIconCreatorOptions.setClipartName(jLabelNameClipart.getText());
                updateOptionsFromFormAux(currentShape, jCheckBoxTrimClipart, jSliderPaddingClipart,
                        jRadioButtonCenterClipart, jRadioButtonNoneClipart, jRadioButtonCircleClipart, jLabelColorShowClipartL);
                jIconCreatorOptions.setForeColor(jLabelColorShowClipartH.getBackground());
                break;
            }
            case JIconCreatorOptions.ICON_TEXT: {
                jIconCreatorOptions.setIconType(JIconCreatorOptions.ICON_TEXT);
                jIconCreatorOptions.setTextString(jTextFieldText.getText());
                updateOptionsFromFormAux(currentShape, jCheckBoxTrimText, jSliderPaddingText,
                        jRadioButtonCenterText, jRadioButtonNoneText, jRadioButtonCircleText, jLabelColorShowTextL);
                jIconCreatorOptions.setForeColor(jLabelColorShowTextH.getBackground());
                
                jIconCreatorOptions.setFont(jLabelFontNameText.getText());
                break;
            }
        }
    }

    private void updateOptionsFromFormAux(int currentShape,
                                          JCheckBox jCheckBoxTrimImage,
                                          JSlider jSliderPaddingImage,
                                          JRadioButton jRadioButtonCenterImage,
                                          JRadioButton jRadioButtonNoneImage,
                                          JRadioButton jRadioButtonCircleImage,
                                          JLabel jLabelColorShowImageL) {
        boolean currentScaling = jRadioButtonCenterImage.isSelected();
        jIconCreatorOptions.setTrim(jCheckBoxTrimImage.isSelected());
        jIconCreatorOptions.setPadding(jSliderPaddingImage.getValue());

        if (jRadioButtonNoneImage.isSelected()) {
            currentShape = JIconCreatorOptions.SHAPE_NONE;
        } else if (jRadioButtonCircleImage.isSelected()) {
            currentShape = JIconCreatorOptions.SHAPE_CIRCLE;
        }
        jIconCreatorOptions.setCrop(currentScaling);
        jIconCreatorOptions.setShapeType(currentShape);

        jIconCreatorOptions.setBackColor(jLabelColorShowImageL.getBackground());
    }

    // https://stackoverflow.com/a/9111327
    // https://stackoverflow.com/a/13387897
    public void registerDropOnTextField() {
        jTextFieldPathImage.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    Object data = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (data instanceof ArrayList) {
                        List<?> droppedFiles = (List<?>) data;

                        File droppedFile = (File) droppedFiles.get(droppedFiles.size() - 1);
                        boolean isImageFile = ImageFilter.isImageFile(droppedFile);
                        if (isImageFile) {
                            jTextFieldPathImage.setText(droppedFile.getAbsolutePath());
                            jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
                            jIconCreatorGuiHelper.updatePreviewIcons();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                event.dropComplete(true);
            }
        });
    }

    private void registerStatusTimer() {
        statusTimer = new Timer(10000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jLabelStatusBar.setText(java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreator.jLabelStatusBar.text"));
            }
        });
        statusTimer.setInitialDelay(10000); // 10 seconds.
        statusTimer.setRepeats(false);
    }

    /**
     * Creates new form JIconCreator
     */
    public JIconCreatorGui() {
        jIconCreatorOptions = new JIconCreatorOptions();

        initComponents();
        registerDropOnTextField();
        registerStatusTimer();

        jIconCreatorGuiHelper = new JIconCreatorGuiHelper(jIconCreatorOptions,this);
        jIconCreatorGuiHelper.generateStyleMenuItems();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupScalingImage = new javax.swing.ButtonGroup();
        buttonGroupShapeImage = new javax.swing.ButtonGroup();
        buttonGroupScalingClipart = new javax.swing.ButtonGroup();
        buttonGroupShapeClipart = new javax.swing.ButtonGroup();
        buttonGroupScalingText = new javax.swing.ButtonGroup();
        buttonGroupShapeText = new javax.swing.ButtonGroup();
        buttonGroupStyles = new javax.swing.ButtonGroup();
        jSplitPane = new javax.swing.JSplitPane();
        jPanelPreview = new javax.swing.JPanel();
        jLabelMdpiL = new javax.swing.JLabel();
        jLabelMdpiI = new javax.swing.JLabel();
        jLabelHdpiL = new javax.swing.JLabel();
        jLabelHdpiI = new javax.swing.JLabel();
        jLabelXhdpiL = new javax.swing.JLabel();
        jLabelXhdpiI = new javax.swing.JLabel();
        jLabelXxhdpiL = new javax.swing.JLabel();
        jLabelXxhdpiI = new javax.swing.JLabel();
        fillerLargeV1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jPanelPreviewButton = new javax.swing.JPanel();
        fillerLargeH2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButtonPreview = new javax.swing.JButton();
        fillerLargeH3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanelGeneral = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelImage = new javax.swing.JPanel();
        jTextFieldPathImage = new javax.swing.JTextField();
        jButtonBrowseImage = new javax.swing.JButton();
        jButtonResetImage = new javax.swing.JButton();
        jCheckBoxTrimImage = new javax.swing.JCheckBox();
        jSliderPaddingImage = new javax.swing.JSlider();
        jLabelPercentsImage = new javax.swing.JLabel();
        jRadioButtonCropImage = new javax.swing.JRadioButton();
        jRadioButtonCenterImage = new javax.swing.JRadioButton();
        jRadioButtonNoneImage = new javax.swing.JRadioButton();
        jRadioButtonSquareImage = new javax.swing.JRadioButton();
        jRadioButtonCircleImage = new javax.swing.JRadioButton();
        jButtonChooseImageL = new javax.swing.JButton();
        jButtonRandomImageL = new javax.swing.JButton();
        jLabelColorShowImageL = new javax.swing.JLabel();
        jLabelColorImageL = new javax.swing.JLabel();
        jLabelShapeImage = new javax.swing.JLabel();
        jLabelScalingImage = new javax.swing.JLabel();
        jLabelPaddingImage = new javax.swing.JLabel();
        jLabelOptionsImage = new javax.swing.JLabel();
        jLabelImage = new javax.swing.JLabel();
        jPanelClipart = new javax.swing.JPanel();
        jLabelOptionsClipart = new javax.swing.JLabel();
        jCheckBoxTrimClipart = new javax.swing.JCheckBox();
        jLabelPaddingClipart = new javax.swing.JLabel();
        jSliderPaddingClipart = new javax.swing.JSlider();
        jLabelPercentsClipart = new javax.swing.JLabel();
        jLabelNameClipart = new javax.swing.JLabel();
        jLabelScalingClipart = new javax.swing.JLabel();
        jRadioButtonCropClipart = new javax.swing.JRadioButton();
        jRadioButtonCenterClipart = new javax.swing.JRadioButton();
        jLabelShapeClipart = new javax.swing.JLabel();
        jRadioButtonNoneClipart = new javax.swing.JRadioButton();
        jRadioButtonSquareClipart = new javax.swing.JRadioButton();
        jRadioButtonCircleClipart = new javax.swing.JRadioButton();
        jLabelColorClipartL = new javax.swing.JLabel();
        jButtonChooseClipartL = new javax.swing.JButton();
        jButtonRandomClipartL = new javax.swing.JButton();
        jLabelColorShowClipartL = new javax.swing.JLabel();
        jButtonChooseClipart = new javax.swing.JButton();
        jButtonResetClipart = new javax.swing.JButton();
        jLabelColorClipartH = new javax.swing.JLabel();
        jButtonChooseClipartH = new javax.swing.JButton();
        jButtonRandomClipartH = new javax.swing.JButton();
        jLabelColorShowClipartH = new javax.swing.JLabel();
        jLabelClipart = new javax.swing.JLabel();
        jPanelText = new javax.swing.JPanel();
        jLabelText = new javax.swing.JLabel();
        jTextFieldText = new javax.swing.JTextField();
        jButtonRestText = new javax.swing.JButton();
        jLabelOptionsText = new javax.swing.JLabel();
        jCheckBoxTrimText = new javax.swing.JCheckBox();
        jLabelPaddingText = new javax.swing.JLabel();
        jSliderPaddingText = new javax.swing.JSlider();
        jLabelPercentsText = new javax.swing.JLabel();
        jLabelScalingText = new javax.swing.JLabel();
        jRadioButtonCropText = new javax.swing.JRadioButton();
        jRadioButtonCenterText = new javax.swing.JRadioButton();
        jLabelShapeText = new javax.swing.JLabel();
        jRadioButtonNoneText = new javax.swing.JRadioButton();
        jRadioButtonSquareText = new javax.swing.JRadioButton();
        jRadioButtonCircleText = new javax.swing.JRadioButton();
        jLabelColorTextL = new javax.swing.JLabel();
        jButtonChooseTextL = new javax.swing.JButton();
        jButtonRandomTextL = new javax.swing.JButton();
        jLabelColorShowTextL = new javax.swing.JLabel();
        jLabelColorTextH = new javax.swing.JLabel();
        jButtonChooseTextH = new javax.swing.JButton();
        jButtonRandomTextH = new javax.swing.JButton();
        jLabelColorShowTextH = new javax.swing.JLabel();
        jLabelFontText = new javax.swing.JLabel();
        jButtonChooseFontText = new javax.swing.JButton();
        jLabelFontNameText = new javax.swing.JLabel();
        jPanelStatusBar = new javax.swing.JPanel();
        fillerSmallH1 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        jLabelStatusBar = new javax.swing.JLabel();
        fillerLargeH1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabelStatusFileName = new javax.swing.JLabel();
        fillerSmallH4 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        jTextFieldStatusFileName = new javax.swing.JTextField();
        fillerSmallH3 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        jButtonSave = new javax.swing.JButton();
        fillerSmallH2 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        jButtonSaveAs = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemPreview = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuStyle = new javax.swing.JMenu();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("JIconCreator.title")); // NOI18N
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 1024, 768));
        setMaximumSize(new java.awt.Dimension(1024, 768));
        setMinimumSize(new java.awt.Dimension(725, 550));
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jSplitPane.setDividerLocation(580);

        jPanelPreview.setMinimumSize(new java.awt.Dimension(150, 500));
        jPanelPreview.setLayout(new javax.swing.BoxLayout(jPanelPreview, javax.swing.BoxLayout.PAGE_AXIS));

        jLabelMdpiL.setText(bundle.getString("JIconCreator.jLabelMdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelMdpiL);

        jLabelMdpiI.setText(bundle.getString("JIconCreator.jLabelMdpiI.text")); // NOI18N
        jLabelMdpiI.setMaximumSize(new java.awt.Dimension(48, 48));
        jLabelMdpiI.setMinimumSize(new java.awt.Dimension(48, 48));
        jLabelMdpiI.setPreferredSize(new java.awt.Dimension(48, 48));
        jPanelPreview.add(jLabelMdpiI);

        jLabelHdpiL.setText(bundle.getString("JIconCreator.jLabelHdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelHdpiL);

        jLabelHdpiI.setText(bundle.getString("JIconCreator.jLabelHdpiI.text")); // NOI18N
        jLabelHdpiI.setMaximumSize(new java.awt.Dimension(72, 72));
        jLabelHdpiI.setMinimumSize(new java.awt.Dimension(72, 72));
        jLabelHdpiI.setPreferredSize(new java.awt.Dimension(72, 72));
        jPanelPreview.add(jLabelHdpiI);

        jLabelXhdpiL.setText(bundle.getString("JIconCreator.jLabelXhdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelXhdpiL);

        jLabelXhdpiI.setText(bundle.getString("JIconCreator.jLabelXhdpiI.text")); // NOI18N
        jLabelXhdpiI.setMaximumSize(new java.awt.Dimension(96, 96));
        jLabelXhdpiI.setMinimumSize(new java.awt.Dimension(96, 96));
        jLabelXhdpiI.setPreferredSize(new java.awt.Dimension(96, 96));
        jPanelPreview.add(jLabelXhdpiI);

        jLabelXxhdpiL.setText(bundle.getString("JIconCreator.jLabelXxhdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelXxhdpiL);

        jLabelXxhdpiI.setText(bundle.getString("JIconCreator.jLabelXxhdpiI.text")); // NOI18N
        jLabelXxhdpiI.setMaximumSize(new java.awt.Dimension(144, 144));
        jLabelXxhdpiI.setMinimumSize(new java.awt.Dimension(144, 144));
        jLabelXxhdpiI.setPreferredSize(new java.awt.Dimension(144, 144));
        jPanelPreview.add(jLabelXxhdpiI);
        jPanelPreview.add(fillerLargeV1);

        jPanelPreviewButton.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanelPreviewButton.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelPreviewButton.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanelPreviewButton.setLayout(new javax.swing.BoxLayout(jPanelPreviewButton, javax.swing.BoxLayout.LINE_AXIS));
        jPanelPreviewButton.add(fillerLargeH2);

        jButtonPreview.setText(bundle.getString("JIconCreator.jButtonPreview.text")); // NOI18N
        jPanelPreviewButton.add(jButtonPreview);
        jPanelPreviewButton.add(fillerLargeH3);

        jPanelPreview.add(jPanelPreviewButton);

        jSplitPane.setRightComponent(jPanelPreview);

        jPanelGeneral.setLayout(new javax.swing.BoxLayout(jPanelGeneral, javax.swing.BoxLayout.PAGE_AXIS));

        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        jTextFieldPathImage.setText(bundle.getString("JIconCreator.jTextFieldPathImage.text")); // NOI18N
        jTextFieldPathImage.setMinimumSize(new java.awt.Dimension(237, 25));
        jTextFieldPathImage.setPreferredSize(new java.awt.Dimension(237, 25));
        jTextFieldPathImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPathImageActionPerformed(evt);
            }
        });

        jButtonBrowseImage.setText(bundle.getString("JIconCreator.jButtonBrowseImage.text")); // NOI18N
        jButtonBrowseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseImageActionPerformed(evt);
            }
        });

        jButtonResetImage.setText(bundle.getString("JIconCreator.jButtonResetImage.text")); // NOI18N
        jButtonResetImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetImageActionPerformed(evt);
            }
        });

        jCheckBoxTrimImage.setSelected(true);
        jCheckBoxTrimImage.setText(bundle.getString("JIconCreator.jCheckBoxTrimImage.text")); // NOI18N
        jCheckBoxTrimImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTrimImageActionPerformed(evt);
            }
        });

        jSliderPaddingImage.setMinimum(-100);
        jSliderPaddingImage.setValue(15);
        jSliderPaddingImage.setMinimumSize(new java.awt.Dimension(237, 16));
        jSliderPaddingImage.setPreferredSize(new java.awt.Dimension(237, 16));
        jSliderPaddingImage.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderPaddingImageStateChanged(evt);
            }
        });

        jLabelPercentsImage.setText(bundle.getString("JIconCreator.jLabelPercentsImage.text")); // NOI18N

        buttonGroupScalingImage.add(jRadioButtonCropImage);
        jRadioButtonCropImage.setSelected(true);
        jRadioButtonCropImage.setText(bundle.getString("JIconCreator.jRadioButtonCropImage.text")); // NOI18N
        jRadioButtonCropImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCropImageActionPerformed(evt);
            }
        });

        buttonGroupScalingImage.add(jRadioButtonCenterImage);
        jRadioButtonCenterImage.setText(bundle.getString("JIconCreator.jRadioButtonCenterImage.text")); // NOI18N
        jRadioButtonCenterImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCenterImageActionPerformed(evt);
            }
        });

        buttonGroupShapeImage.add(jRadioButtonNoneImage);
        jRadioButtonNoneImage.setText(bundle.getString("JIconCreator.jRadioButtonNoneImage.text")); // NOI18N
        jRadioButtonNoneImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNoneImageActionPerformed(evt);
            }
        });

        buttonGroupShapeImage.add(jRadioButtonSquareImage);
        jRadioButtonSquareImage.setSelected(true);
        jRadioButtonSquareImage.setText(bundle.getString("JIconCreator.jRadioButtonSquareImage.text")); // NOI18N
        jRadioButtonSquareImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSquareImageActionPerformed(evt);
            }
        });

        buttonGroupShapeImage.add(jRadioButtonCircleImage);
        jRadioButtonCircleImage.setText(bundle.getString("JIconCreator.jRadioButtonCircleImage.text")); // NOI18N
        jRadioButtonCircleImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCircleImageActionPerformed(evt);
            }
        });

        jButtonChooseImageL.setText(bundle.getString("JIconCreator.jButtonChooseImageL.text")); // NOI18N
        jButtonChooseImageL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseImageLActionPerformed(evt);
            }
        });

        jButtonRandomImageL.setText(bundle.getString("JIconCreator.jButtonRandomImageL.text")); // NOI18N
        jButtonRandomImageL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRandomImageLActionPerformed(evt);
            }
        });

        jLabelColorShowImageL.setBackground(new java.awt.Color(255, 204, 0));
        jLabelColorShowImageL.setText(bundle.getString("JIconCreator.jLabelColorShowImageL.text")); // NOI18N
        jLabelColorShowImageL.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowImageL.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowImageL.setOpaque(true);
        jLabelColorShowImageL.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelColorImageL.setText(bundle.getString("JIconCreator.jLabelColorImageL.text")); // NOI18N

        jLabelShapeImage.setText(bundle.getString("JIconCreator.jLabelShapeImage.text")); // NOI18N

        jLabelScalingImage.setText(bundle.getString("JIconCreator.jLabelScalingImage.text")); // NOI18N

        jLabelPaddingImage.setText(bundle.getString("JIconCreator.jLabelPaddingImage.text")); // NOI18N

        jLabelOptionsImage.setText(bundle.getString("JIconCreator.jLabelOptionsImage.text")); // NOI18N

        jLabelImage.setText(bundle.getString("JIconCreator.jLabelImage.text")); // NOI18N

        javax.swing.GroupLayout jPanelImageLayout = new javax.swing.GroupLayout(jPanelImage);
        jPanelImage.setLayout(jPanelImageLayout);
        jPanelImageLayout.setHorizontalGroup(
            jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelImageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelColorImageL)
                    .addComponent(jLabelShapeImage)
                    .addComponent(jLabelScalingImage)
                    .addComponent(jLabelPaddingImage)
                    .addComponent(jLabelOptionsImage)
                    .addComponent(jLabelImage))
                .addGap(4, 4, 4)
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelImageLayout.createSequentialGroup()
                        .addComponent(jRadioButtonNoneImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonSquareImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCircleImage))
                    .addGroup(jPanelImageLayout.createSequentialGroup()
                        .addComponent(jTextFieldPathImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBrowseImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonResetImage))
                    .addGroup(jPanelImageLayout.createSequentialGroup()
                        .addComponent(jButtonChooseImageL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRandomImageL)
                        .addGap(6, 6, 6)
                        .addComponent(jLabelColorShowImageL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelImageLayout.createSequentialGroup()
                        .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSliderPaddingImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBoxTrimImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPercentsImage))
                    .addGroup(jPanelImageLayout.createSequentialGroup()
                        .addComponent(jRadioButtonCropImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCenterImage)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelImageLayout.setVerticalGroup(
            jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelImageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPathImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBrowseImage)
                    .addComponent(jButtonResetImage)
                    .addComponent(jLabelImage))
                .addGap(18, 18, 18)
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxTrimImage)
                    .addComponent(jLabelOptionsImage))
                .addGap(18, 18, 18)
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSliderPaddingImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPercentsImage, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPaddingImage, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonCropImage)
                    .addComponent(jRadioButtonCenterImage)
                    .addComponent(jLabelScalingImage))
                .addGap(18, 18, 18)
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonNoneImage)
                    .addComponent(jRadioButtonSquareImage)
                    .addComponent(jRadioButtonCircleImage)
                    .addComponent(jLabelShapeImage))
                .addGap(18, 18, 18)
                .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelColorShowImageL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonChooseImageL)
                        .addComponent(jButtonRandomImageL)
                        .addComponent(jLabelColorImageL)))
                .addContainerGap(282, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("JIconCreator.jPanelImage.TabConstraints.tabTitle"), jPanelImage); // NOI18N

        jLabelOptionsClipart.setText(bundle.getString("JIconCreator.jLabelOptionsClipart.text")); // NOI18N

        jCheckBoxTrimClipart.setSelected(true);
        jCheckBoxTrimClipart.setText(bundle.getString("JIconCreator.jCheckBoxTrimClipart.text")); // NOI18N

        jLabelPaddingClipart.setText(bundle.getString("JIconCreator.jLabelPaddingClipart.text")); // NOI18N

        jSliderPaddingClipart.setMinimum(-100);
        jSliderPaddingClipart.setValue(15);
        jSliderPaddingClipart.setMinimumSize(new java.awt.Dimension(237, 16));
        jSliderPaddingClipart.setPreferredSize(new java.awt.Dimension(237, 16));

        jLabelPercentsClipart.setText(bundle.getString("JIconCreator.jLabelPercentsClipart.text")); // NOI18N

        jLabelNameClipart.setText(bundle.getString("JIconCreator.jLabelNameClipart.text")); // NOI18N
        jLabelNameClipart.setMaximumSize(new java.awt.Dimension(237, 25));
        jLabelNameClipart.setMinimumSize(new java.awt.Dimension(237, 25));
        jLabelNameClipart.setPreferredSize(new java.awt.Dimension(237, 25));

        jLabelScalingClipart.setText(bundle.getString("JIconCreator.jLabelScalingClipart.text")); // NOI18N

        buttonGroupScalingClipart.add(jRadioButtonCropClipart);
        jRadioButtonCropClipart.setSelected(true);
        jRadioButtonCropClipart.setText(bundle.getString("JIconCreator.jRadioButtonCropClipart.text")); // NOI18N

        buttonGroupScalingClipart.add(jRadioButtonCenterClipart);
        jRadioButtonCenterClipart.setText(bundle.getString("JIconCreator.jRadioButtonCenterClipart.text")); // NOI18N

        jLabelShapeClipart.setText(bundle.getString("JIconCreator.jLabelShapeClipart.text")); // NOI18N

        buttonGroupShapeClipart.add(jRadioButtonNoneClipart);
        jRadioButtonNoneClipart.setText(bundle.getString("JIconCreator.jRadioButtonNoneClipart.text")); // NOI18N

        buttonGroupShapeClipart.add(jRadioButtonSquareClipart);
        jRadioButtonSquareClipart.setSelected(true);
        jRadioButtonSquareClipart.setText(bundle.getString("JIconCreator.jRadioButtonSquareClipart.text")); // NOI18N

        buttonGroupShapeClipart.add(jRadioButtonCircleClipart);
        jRadioButtonCircleClipart.setText(bundle.getString("JIconCreator.jRadioButtonCircleClipart.text")); // NOI18N

        jLabelColorClipartL.setText(bundle.getString("JIconCreator.jLabelColorClipartL.text")); // NOI18N

        jButtonChooseClipartL.setText(bundle.getString("JIconCreator.jButtonChooseClipartL.text")); // NOI18N

        jButtonRandomClipartL.setText(bundle.getString("JIconCreator.jButtonRandomClipartL.text")); // NOI18N

        jLabelColorShowClipartL.setBackground(new java.awt.Color(153, 153, 153));
        jLabelColorShowClipartL.setText(bundle.getString("JIconCreator.jLabelColorShowClipartL.text")); // NOI18N
        jLabelColorShowClipartL.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartL.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartL.setOpaque(true);
        jLabelColorShowClipartL.setPreferredSize(new java.awt.Dimension(25, 25));

        jButtonChooseClipart.setText(bundle.getString("JIconCreator.jButtonChooseClipart.text")); // NOI18N

        jButtonResetClipart.setText(bundle.getString("JIconCreator.jButtonResetClipart.text")); // NOI18N

        jLabelColorClipartH.setText(bundle.getString("JIconCreator.jLabelColorClipartH.text")); // NOI18N

        jButtonChooseClipartH.setText(bundle.getString("JIconCreator.jButtonChooseClipartH.text")); // NOI18N

        jButtonRandomClipartH.setText(bundle.getString("JIconCreator.jButtonRandomClipartH.text")); // NOI18N

        jLabelColorShowClipartH.setBackground(new java.awt.Color(255, 255, 153));
        jLabelColorShowClipartH.setText(bundle.getString("JIconCreator.jLabelColorShowClipartH.text")); // NOI18N
        jLabelColorShowClipartH.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartH.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartH.setOpaque(true);
        jLabelColorShowClipartH.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelClipart.setText(bundle.getString("JIconCreator.jLabelClipart.text")); // NOI18N

        javax.swing.GroupLayout jPanelClipartLayout = new javax.swing.GroupLayout(jPanelClipart);
        jPanelClipart.setLayout(jPanelClipartLayout);
        jPanelClipartLayout.setHorizontalGroup(
            jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClipartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelColorClipartH)
                    .addComponent(jLabelColorClipartL)
                    .addComponent(jLabelShapeClipart)
                    .addComponent(jLabelScalingClipart)
                    .addComponent(jLabelOptionsClipart)
                    .addComponent(jLabelClipart)
                    .addComponent(jLabelPaddingClipart))
                .addGap(4, 4, 4)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addComponent(jLabelNameClipart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonChooseClipart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonResetClipart))
                    .addComponent(jCheckBoxTrimClipart)
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addComponent(jSliderPaddingClipart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPercentsClipart))
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addComponent(jRadioButtonCropClipart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCenterClipart))
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addComponent(jRadioButtonNoneClipart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonSquareClipart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCircleClipart))
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelClipartLayout.createSequentialGroup()
                                .addComponent(jButtonChooseClipartL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonRandomClipartL))
                            .addGroup(jPanelClipartLayout.createSequentialGroup()
                                .addComponent(jButtonChooseClipartH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonRandomClipartH)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelColorShowClipartH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelColorShowClipartL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelClipartLayout.setVerticalGroup(
            jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClipartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNameClipart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonChooseClipart)
                    .addComponent(jButtonResetClipart)
                    .addComponent(jLabelClipart))
                .addGap(18, 18, 18)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOptionsClipart)
                    .addComponent(jCheckBoxTrimClipart))
                .addGap(18, 18, 18)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPaddingClipart)
                    .addComponent(jSliderPaddingClipart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPercentsClipart))
                .addGap(18, 18, 18)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelScalingClipart)
                    .addComponent(jRadioButtonCropClipart)
                    .addComponent(jRadioButtonCenterClipart))
                .addGap(18, 18, 18)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelShapeClipart)
                    .addComponent(jRadioButtonNoneClipart)
                    .addComponent(jRadioButtonSquareClipart)
                    .addComponent(jRadioButtonCircleClipart))
                .addGap(18, 18, 18)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelColorShowClipartL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelColorClipartL)
                        .addComponent(jButtonChooseClipartL)
                        .addComponent(jButtonRandomClipartL)))
                .addGap(18, 18, 18)
                .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelColorShowClipartH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelClipartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelColorClipartH)
                        .addComponent(jButtonChooseClipartH)
                        .addComponent(jButtonRandomClipartH)))
                .addContainerGap(239, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("JIconCreator.jPanelClipart.TabConstraints.tabTitle"), jPanelClipart); // NOI18N

        jLabelText.setText(bundle.getString("JIconCreator.jLabelText.text")); // NOI18N

        jTextFieldText.setText(bundle.getString("JIconCreator.jTextFieldText.text")); // NOI18N
        jTextFieldText.setMinimumSize(new java.awt.Dimension(237, 25));
        jTextFieldText.setPreferredSize(new java.awt.Dimension(237, 25));

        jButtonRestText.setText(bundle.getString("JIconCreator.jButtonRestText.text")); // NOI18N

        jLabelOptionsText.setText(bundle.getString("JIconCreator.jLabelOptionsText.text")); // NOI18N

        jCheckBoxTrimText.setSelected(true);
        jCheckBoxTrimText.setText(bundle.getString("JIconCreator.jCheckBoxTrimText.text")); // NOI18N

        jLabelPaddingText.setText(bundle.getString("JIconCreator.jLabelPaddingText.text")); // NOI18N

        jSliderPaddingText.setMinimum(-100);
        jSliderPaddingText.setValue(15);
        jSliderPaddingText.setMinimumSize(new java.awt.Dimension(237, 16));
        jSliderPaddingText.setPreferredSize(new java.awt.Dimension(237, 16));

        jLabelPercentsText.setText(bundle.getString("JIconCreator.jLabelPercentsText.text")); // NOI18N

        jLabelScalingText.setText(bundle.getString("JIconCreator.jLabelScalingText.text")); // NOI18N

        buttonGroupScalingText.add(jRadioButtonCropText);
        jRadioButtonCropText.setSelected(true);
        jRadioButtonCropText.setText(bundle.getString("JIconCreator.jRadioButtonCropText.text")); // NOI18N

        buttonGroupScalingText.add(jRadioButtonCenterText);
        jRadioButtonCenterText.setText(bundle.getString("JIconCreator.jRadioButtonCenterText.text")); // NOI18N

        jLabelShapeText.setText(bundle.getString("JIconCreator.jLabelShapeText.text")); // NOI18N

        buttonGroupShapeText.add(jRadioButtonNoneText);
        jRadioButtonNoneText.setText(bundle.getString("JIconCreator.jRadioNoneText.text")); // NOI18N

        buttonGroupShapeText.add(jRadioButtonSquareText);
        jRadioButtonSquareText.setSelected(true);
        jRadioButtonSquareText.setText(bundle.getString("JIconCreator.jRadioButtonSquareText.text")); // NOI18N

        buttonGroupShapeText.add(jRadioButtonCircleText);
        jRadioButtonCircleText.setText(bundle.getString("JIconCreator.jRadioButtonCircle.text")); // NOI18N

        jLabelColorTextL.setText(bundle.getString("JIconCreator.jLabelColorTextL.text")); // NOI18N

        jButtonChooseTextL.setText(bundle.getString("JIconCreator.jButtonChooseTextL.text")); // NOI18N

        jButtonRandomTextL.setText(bundle.getString("JIconCreator.jButtonRandomTextL.text")); // NOI18N

        jLabelColorShowTextL.setBackground(new java.awt.Color(0, 51, 51));
        jLabelColorShowTextL.setText(bundle.getString("JIconCreator.jLabelColorShowTextL.text")); // NOI18N
        jLabelColorShowTextL.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextL.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextL.setOpaque(true);
        jLabelColorShowTextL.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelColorTextH.setText(bundle.getString("JIconCreator.jLabelColorTextH.text")); // NOI18N

        jButtonChooseTextH.setText(bundle.getString("JIconCreator.jButtonChooseTextH.text")); // NOI18N

        jButtonRandomTextH.setText(bundle.getString("JIconCreator.jButtonRandomTextH.text")); // NOI18N

        jLabelColorShowTextH.setBackground(new java.awt.Color(255, 51, 51));
        jLabelColorShowTextH.setText(bundle.getString("JIconCreator.jLabelColorShowTextH.text")); // NOI18N
        jLabelColorShowTextH.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextH.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextH.setOpaque(true);
        jLabelColorShowTextH.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelFontText.setText(bundle.getString("JIconCreator.jLabelFontText.text")); // NOI18N

        jButtonChooseFontText.setText(bundle.getString("JIconCreator.jButtonChooseFontText.text")); // NOI18N

        jLabelFontNameText.setText(bundle.getString("JIconCreator.jLabelFontNameText.text")); // NOI18N

        javax.swing.GroupLayout jPanelTextLayout = new javax.swing.GroupLayout(jPanelText);
        jPanelText.setLayout(jPanelTextLayout);
        jPanelTextLayout.setHorizontalGroup(
            jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTextLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelFontText)
                    .addComponent(jLabelColorTextH)
                    .addComponent(jLabelColorTextL)
                    .addComponent(jLabelPaddingText)
                    .addComponent(jLabelOptionsText)
                    .addComponent(jLabelText)
                    .addComponent(jLabelScalingText)
                    .addComponent(jLabelShapeText))
                .addGap(4, 4, 4)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRestText))
                    .addComponent(jCheckBoxTrimText)
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jSliderPaddingText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPercentsText))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jButtonChooseTextL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRandomTextL)
                        .addGap(6, 6, 6)
                        .addComponent(jLabelColorShowTextL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonChooseTextH)
                            .addComponent(jButtonChooseFontText))
                        .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTextLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonRandomTextH)
                                .addGap(6, 6, 6)
                                .addComponent(jLabelColorShowTextH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelTextLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelFontNameText))))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelTextLayout.createSequentialGroup()
                                .addComponent(jRadioButtonCropText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonCenterText))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelTextLayout.createSequentialGroup()
                                .addComponent(jRadioButtonNoneText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonSquareText)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCircleText)))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        jPanelTextLayout.setVerticalGroup(
            jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTextLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelText)
                    .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRestText))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOptionsText)
                    .addComponent(jCheckBoxTrimText))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderPaddingText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPercentsText)
                    .addComponent(jLabelPaddingText))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelScalingText)
                    .addComponent(jRadioButtonCropText)
                    .addComponent(jRadioButtonCenterText))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonNoneText)
                    .addComponent(jRadioButtonSquareText)
                    .addComponent(jRadioButtonCircleText)
                    .addComponent(jLabelShapeText))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelColorTextL)
                        .addComponent(jButtonChooseTextL)
                        .addComponent(jButtonRandomTextL))
                    .addComponent(jLabelColorShowTextL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelColorTextH)
                        .addComponent(jButtonChooseTextH)
                        .addComponent(jButtonRandomTextH))
                    .addComponent(jLabelColorShowTextH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFontText)
                    .addComponent(jButtonChooseFontText)
                    .addComponent(jLabelFontNameText))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("JIconCreator.jPanelText.TabConstraints.tabTitle"), jPanelText); // NOI18N

        jPanelGeneral.add(jTabbedPane);

        jPanelStatusBar.setMaximumSize(new java.awt.Dimension(800, 30));
        jPanelStatusBar.setMinimumSize(new java.awt.Dimension(400, 30));
        jPanelStatusBar.setPreferredSize(new java.awt.Dimension(550, 30));
        jPanelStatusBar.setLayout(new javax.swing.BoxLayout(jPanelStatusBar, javax.swing.BoxLayout.LINE_AXIS));
        jPanelStatusBar.add(fillerSmallH1);

        jLabelStatusBar.setText(bundle.getString("JIconCreator.jLabelStatusBar.text")); // NOI18N
        jPanelStatusBar.add(jLabelStatusBar);
        jPanelStatusBar.add(fillerLargeH1);

        jLabelStatusFileName.setText(bundle.getString("JIconCreatorGui.jLabelStatusFileName.text")); // NOI18N
        jPanelStatusBar.add(jLabelStatusFileName);
        jPanelStatusBar.add(fillerSmallH4);

        jTextFieldStatusFileName.setText(bundle.getString("JIconCreatorGui.jTextFieldStatusFileName.text")); // NOI18N
        jTextFieldStatusFileName.setMaximumSize(new java.awt.Dimension(150, 25));
        jTextFieldStatusFileName.setMinimumSize(new java.awt.Dimension(150, 25));
        jTextFieldStatusFileName.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanelStatusBar.add(jTextFieldStatusFileName);
        jPanelStatusBar.add(fillerSmallH3);

        jButtonSave.setText(bundle.getString("JIconCreator.jButtonSave.text")); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanelStatusBar.add(jButtonSave);
        jPanelStatusBar.add(fillerSmallH2);

        jButtonSaveAs.setText(bundle.getString("JIconCreator.jButtonSaveAs.text")); // NOI18N
        jPanelStatusBar.add(jButtonSaveAs);

        jPanelGeneral.add(jPanelStatusBar);

        jSplitPane.setLeftComponent(jPanelGeneral);

        getContentPane().add(jSplitPane);

        jMenuFile.setText(bundle.getString("JIconCreator.jMenuFile.text")); // NOI18N

        jMenuItemPreview.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemPreview.setText(bundle.getString("JIconCreator.jMenuItemPreview.text")); // NOI18N
        jMenuFile.add(jMenuItemPreview);
        jMenuFile.add(jSeparator1);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText(bundle.getString("JIconCreator.jMenuItemSave.text")); // NOI18N
        jMenuFile.add(jMenuItemSave);

        jMenuItemSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSaveAs.setText(bundle.getString("JIconCreator.jMenuItemSaveAs.text")); // NOI18N
        jMenuFile.add(jMenuItemSaveAs);
        jMenuFile.add(jSeparator2);

        jMenuItemExit.setText(bundle.getString("JIconCreator.jMenuItemExit.text")); // NOI18N
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar.add(jMenuFile);

        jMenuStyle.setText(bundle.getString("JIconCreator.jMenuStyle.text")); // NOI18N
        jMenuBar.add(jMenuStyle);

        jMenuHelp.setText(bundle.getString("JIconCreator.jMenuHelp.text")); // NOI18N

        jMenuItemAbout.setText(bundle.getString("JIconCreator.jMenuItemAbout.text")); // NOI18N
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRandomImageLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRandomImageLActionPerformed
        jIconCreatorGuiHelper.setRandomColor(jLabelColorShowImageL, true);
    }//GEN-LAST:event_jButtonRandomImageLActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        JIconCreatorGui.this.processWindowEvent(
                new java.awt.event.WindowEvent(JIconCreatorGui.this,java.awt.event.WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
        updateOptionsFromForm();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jIconCreatorGuiHelper.updatePreviewIcons();
            }
        });
    }//GEN-LAST:event_jTabbedPaneStateChanged

    private void jButtonChooseImageLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseImageLActionPerformed
        String title = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreator.colorDialogL.text"); // NOI18N
        Color color = JColorChooser.showDialog(this, title, jLabelColorShowImageL.getBackground());
        if (color != null) {
            jLabelColorShowImageL.setBackground(color);
            jIconCreatorGuiHelper.setColorToImage(color, true);
        }
    }//GEN-LAST:event_jButtonChooseImageLActionPerformed

    private void jRadioButtonNoneImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNoneImageActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_NONE);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonNoneImageActionPerformed

    private void jRadioButtonSquareImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSquareImageActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_SQUARE);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonSquareImageActionPerformed

    private void jRadioButtonCircleImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCircleImageActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_CIRCLE);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCircleImageActionPerformed

    private void jRadioButtonCropImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCropImageActionPerformed
        jIconCreatorOptions.setCrop(false);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCropImageActionPerformed

    private void jRadioButtonCenterImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCenterImageActionPerformed
        jIconCreatorOptions.setCrop(true);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCenterImageActionPerformed

    private void jCheckBoxTrimImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTrimImageActionPerformed
        jIconCreatorOptions.setTrim(jCheckBoxTrimImage.isSelected());
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxTrimImageActionPerformed

    private void jButtonBrowseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseImageActionPerformed
        String title = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreator.openImageDialog.text"); // NOI18N
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new ImageFilter());
        jFileChooser.setAccessory(new ImagePreview(jFileChooser));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        if (jFileChooser.showDialog(this, title) == JFileChooser.APPROVE_OPTION) {
            jTextFieldPathImage.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
            jIconCreatorGuiHelper.updatePreviewIcons();
        }
    }//GEN-LAST:event_jButtonBrowseImageActionPerformed

    private void jButtonResetImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetImageActionPerformed
        String sign = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreator.jTextFieldPathImage.text"); // NOI18N
        jTextFieldPathImage.setText(sign);
        jIconCreatorOptions.setImageFilePath(sign);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jButtonResetImageActionPerformed

    private void jTextFieldPathImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPathImageActionPerformed
        jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jTextFieldPathImageActionPerformed

    private void jSliderPaddingImageStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderPaddingImageStateChanged
        int percent = jSliderPaddingImage.getValue();
        jLabelPercentsImage.setText(percent + "%");
        jIconCreatorOptions.setPadding(percent);
        jIconCreatorGuiHelper.updatePreviewIcons();
    }//GEN-LAST:event_jSliderPaddingImageStateChanged

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        jIconCreatorGuiHelper.saveImages(jTextFieldStatusFileName.getText(), null);
        jLabelStatusBar.setText(java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreator.saveImageStatusBar.text"));
        statusTimer.restart();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JIconCreatorGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupScalingClipart;
    private javax.swing.ButtonGroup buttonGroupScalingImage;
    private javax.swing.ButtonGroup buttonGroupScalingText;
    private javax.swing.ButtonGroup buttonGroupShapeClipart;
    private javax.swing.ButtonGroup buttonGroupShapeImage;
    private javax.swing.ButtonGroup buttonGroupShapeText;
    private javax.swing.ButtonGroup buttonGroupStyles;
    private javax.swing.Box.Filler fillerLargeH1;
    private javax.swing.Box.Filler fillerLargeH2;
    private javax.swing.Box.Filler fillerLargeH3;
    private javax.swing.Box.Filler fillerLargeV1;
    private javax.swing.Box.Filler fillerSmallH1;
    private javax.swing.Box.Filler fillerSmallH2;
    private javax.swing.Box.Filler fillerSmallH3;
    private javax.swing.Box.Filler fillerSmallH4;
    private javax.swing.JButton jButtonBrowseImage;
    private javax.swing.JButton jButtonChooseClipart;
    private javax.swing.JButton jButtonChooseClipartH;
    private javax.swing.JButton jButtonChooseClipartL;
    private javax.swing.JButton jButtonChooseFontText;
    private javax.swing.JButton jButtonChooseImageL;
    private javax.swing.JButton jButtonChooseTextH;
    private javax.swing.JButton jButtonChooseTextL;
    private javax.swing.JButton jButtonPreview;
    private javax.swing.JButton jButtonRandomClipartH;
    private javax.swing.JButton jButtonRandomClipartL;
    private javax.swing.JButton jButtonRandomImageL;
    private javax.swing.JButton jButtonRandomTextH;
    private javax.swing.JButton jButtonRandomTextL;
    private javax.swing.JButton jButtonResetClipart;
    private javax.swing.JButton jButtonResetImage;
    private javax.swing.JButton jButtonRestText;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSaveAs;
    private javax.swing.JCheckBox jCheckBoxTrimClipart;
    private javax.swing.JCheckBox jCheckBoxTrimImage;
    private javax.swing.JCheckBox jCheckBoxTrimText;
    private javax.swing.JLabel jLabelClipart;
    private javax.swing.JLabel jLabelColorClipartH;
    private javax.swing.JLabel jLabelColorClipartL;
    private javax.swing.JLabel jLabelColorImageL;
    private javax.swing.JLabel jLabelColorShowClipartH;
    private javax.swing.JLabel jLabelColorShowClipartL;
    private javax.swing.JLabel jLabelColorShowImageL;
    private javax.swing.JLabel jLabelColorShowTextH;
    private javax.swing.JLabel jLabelColorShowTextL;
    private javax.swing.JLabel jLabelColorTextH;
    private javax.swing.JLabel jLabelColorTextL;
    private javax.swing.JLabel jLabelFontNameText;
    private javax.swing.JLabel jLabelFontText;
    private javax.swing.JLabel jLabelHdpiI;
    private javax.swing.JLabel jLabelHdpiL;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelMdpiI;
    private javax.swing.JLabel jLabelMdpiL;
    private javax.swing.JLabel jLabelNameClipart;
    private javax.swing.JLabel jLabelOptionsClipart;
    private javax.swing.JLabel jLabelOptionsImage;
    private javax.swing.JLabel jLabelOptionsText;
    private javax.swing.JLabel jLabelPaddingClipart;
    private javax.swing.JLabel jLabelPaddingImage;
    private javax.swing.JLabel jLabelPaddingText;
    private javax.swing.JLabel jLabelPercentsClipart;
    private javax.swing.JLabel jLabelPercentsImage;
    private javax.swing.JLabel jLabelPercentsText;
    private javax.swing.JLabel jLabelScalingClipart;
    private javax.swing.JLabel jLabelScalingImage;
    private javax.swing.JLabel jLabelScalingText;
    private javax.swing.JLabel jLabelShapeClipart;
    private javax.swing.JLabel jLabelShapeImage;
    private javax.swing.JLabel jLabelShapeText;
    private javax.swing.JLabel jLabelStatusBar;
    private javax.swing.JLabel jLabelStatusFileName;
    private javax.swing.JLabel jLabelText;
    private javax.swing.JLabel jLabelXhdpiI;
    private javax.swing.JLabel jLabelXhdpiL;
    private javax.swing.JLabel jLabelXxhdpiI;
    private javax.swing.JLabel jLabelXxhdpiL;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemPreview;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenu jMenuStyle;
    private javax.swing.JPanel jPanelClipart;
    private javax.swing.JPanel jPanelGeneral;
    private javax.swing.JPanel jPanelImage;
    private javax.swing.JPanel jPanelPreview;
    private javax.swing.JPanel jPanelPreviewButton;
    private javax.swing.JPanel jPanelStatusBar;
    private javax.swing.JPanel jPanelText;
    private javax.swing.JRadioButton jRadioButtonCenterClipart;
    private javax.swing.JRadioButton jRadioButtonCenterImage;
    private javax.swing.JRadioButton jRadioButtonCenterText;
    private javax.swing.JRadioButton jRadioButtonCircleClipart;
    private javax.swing.JRadioButton jRadioButtonCircleImage;
    private javax.swing.JRadioButton jRadioButtonCircleText;
    private javax.swing.JRadioButton jRadioButtonCropClipart;
    private javax.swing.JRadioButton jRadioButtonCropImage;
    private javax.swing.JRadioButton jRadioButtonCropText;
    private javax.swing.JRadioButton jRadioButtonNoneClipart;
    private javax.swing.JRadioButton jRadioButtonNoneImage;
    private javax.swing.JRadioButton jRadioButtonNoneText;
    private javax.swing.JRadioButton jRadioButtonSquareClipart;
    private javax.swing.JRadioButton jRadioButtonSquareImage;
    private javax.swing.JRadioButton jRadioButtonSquareText;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSlider jSliderPaddingClipart;
    private javax.swing.JSlider jSliderPaddingImage;
    private javax.swing.JSlider jSliderPaddingText;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextFieldPathImage;
    private javax.swing.JTextField jTextFieldStatusFileName;
    private javax.swing.JTextField jTextFieldText;
    // End of variables declaration//GEN-END:variables

    public javax.swing.ButtonGroup getButtonGroupStyles() {
        return buttonGroupStyles;
    }

    public javax.swing.JMenu getMenuStyle() {
        return jMenuStyle;
    }

    public javax.swing.JLabel getLabelMdpiI() {
        return jLabelMdpiI;
    }

    public javax.swing.JLabel getLabelHdpiI() {
        return jLabelHdpiI;
    }

    public javax.swing.JLabel getLabelXhdpiI() {
        return jLabelXhdpiI;
    }

    public javax.swing.JLabel getLabelXxhdpiI() {
        return jLabelXxhdpiI;
    }
}
