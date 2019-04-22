package ru.exlmoto.jiconcreator;

import com.oracle.docs.ImageFilter;
import com.oracle.docs.ImagePreview;
//////// REF
import ru.exlmoto.jiconcreator.unsorted.CreateAssetSetWizardState;
import ru.exlmoto.jiconcreator.unsorted.JIconCreatorExtrasLibraryHere;
/////// REF

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 *
 * @author exl
 */
public class JIconCreatorGui extends javax.swing.JFrame {
    private final int BIG_SIZE_PIX = 400;
    private final int DELAY_10S = 10000;

    private JIconCreatorOptions jIconCreatorOptions = null;

    private Timer statusTimer = null;
    private JFileChooser jFileChooser = null;
    private JFileChooser jDirectoryChooser = null;

    private boolean isMipmapScheme = false;

    ////// **** REFACTORING ******** ///////
    private CreateAssetSetWizardState createAssetSetWizardState = null;

    public void updatePreviewIcons() {
        int cnt = 0;
        Map<String, Map<String, BufferedImage>> categories =
                JIconCreatorExtrasLibraryHere.generateImages(createAssetSetWizardState, jIconCreatorOptions, true);
        if (JIconCreatorExtrasLibraryHere.wtf(cnt, categories,
                jLabelMdpiI, jLabelHdpiI, jLabelXhdpiI, jLabelXxhdpiI))
            return;

        System.gc();
    }

    public boolean saveImages(String fileName, String path, boolean mipmap) {
        boolean status = JIconCreatorExtrasLibraryHere.generateIcons(
                createAssetSetWizardState, jIconCreatorOptions, false, fileName, path, mipmap);

        System.gc();

        return status;
    }
    ////////////////////// ***** RECAT **** ////

    private void resetImage() {
        String sign = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.jTextFieldPathImage.text"); // NOI18N
        jTextFieldPathImage.setText(sign);
        jIconCreatorOptions.setImageFilePath(sign);
        updatePreviewIcons();
    }

    private void browseImage() {
        String title = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.openImageDialog.text"); // NOI18N
        if (jFileChooser.showDialog(this, title) == JFileChooser.APPROVE_OPTION) {
            File imageFile = jFileChooser.getSelectedFile();
            jTextFieldPathImage.setText(imageFile.getAbsolutePath());
            jIconCreatorOptions.setBigImage(isImageBigSize(imageFile));
            jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
            updatePreviewIcons();
        }
    }

    private void sliderPaddingHelper(JLabel percents, ChangeEvent evt, boolean imageIsBig) {
        JSlider slider = (JSlider) evt.getSource();
        int percent = slider.getValue();
        percents.setText(percent + "%");
        if (!imageIsBig) {
            jIconCreatorOptions.setPadding(percent);
            updatePreviewIcons();
        } else if (!slider.getValueIsAdjusting()) {
            jIconCreatorOptions.setPadding(percent);
            updatePreviewIcons();
        }
    }

    private void setColorByChooseButton(JLabel colorShowLabel, boolean backColor) {
        String title = (backColor) ?
                java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.colorDialogL.text") : // NOI18N
                java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.colorDialogH.text"); // NOI18N
        Color color = JColorChooser.showDialog(this, title, colorShowLabel.getBackground());
        if (color != null) {
            colorShowLabel.setBackground(color);
            setColorToImage(color, backColor);
        }
    }

    private boolean isImageBigSize(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            return ((image.getHeight() > BIG_SIZE_PIX) || (image.getWidth() > BIG_SIZE_PIX));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveFilesAction(String fileName, String path, boolean mipmap) {
        boolean status = saveImages(fileName, path, mipmap);
        String statusText = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.saveImageStatusBarFail.text");
        if (status) {
            statusText = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.saveImageStatusBar.text");
        }
        jLabelStatusBar.setText(statusText);
        statusTimer.restart();
    }

    private void saveFilesAction(String fileName) {
        saveFilesAction(fileName, null, false);
    }

    private void saveFilesToDirectory(boolean mipmap) {
        String title = java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.openImageDialogDir.text"); // NOI18N
        if (jDirectoryChooser.showDialog(this, title) == JFileChooser.APPROVE_OPTION) {
            saveFilesAction(jTextFieldStatusFileName.getText(), jDirectoryChooser.getSelectedFile().getAbsolutePath(), mipmap);
        }
    }

    private void updateOptionsFromForm() {
        int currentShape = JIconCreatorOptions.SHAPE_SQUARE;

        switch (jTabbedPane.getSelectedIndex()) {
            default:
            case JIconCreatorOptions.ICON_IMAGE: {
                jIconCreatorOptions.setIconType(JIconCreatorOptions.ICON_IMAGE);
                jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
                updateOptionsFromFormAux(currentShape, jCheckBoxTrimImage, jCheckBoxForeMaskImage, jSliderPaddingImage,
                        jRadioButtonCenterImage, jRadioButtonNoneImage, jRadioButtonCircleImage, jLabelColorShowImageL);
                break;
            }
            case JIconCreatorOptions.ICON_CLIPART: {
                jIconCreatorOptions.setIconType(JIconCreatorOptions.ICON_CLIPART);
                jIconCreatorOptions.setClipartName(jLabelNameClipart.getText());
                updateOptionsFromFormAux(currentShape, jCheckBoxTrimClipart, jCheckBoxForeMaskClipart, jSliderPaddingClipart,
                        jRadioButtonCenterClipart, jRadioButtonNoneClipart, jRadioButtonCircleClipart, jLabelColorShowClipartL);
                jIconCreatorOptions.setForeColor(jLabelColorShowClipartH.getBackground());
                break;
            }
            case JIconCreatorOptions.ICON_TEXT: {
                jIconCreatorOptions.setIconType(JIconCreatorOptions.ICON_TEXT);
                jIconCreatorOptions.setTextString(jTextFieldText.getText());
                updateOptionsFromFormAux(currentShape, jCheckBoxTrimText, jCheckBoxForeMaskText, jSliderPaddingText,
                        jRadioButtonCenterText, jRadioButtonNoneText, jRadioButtonCircleText, jLabelColorShowTextL);
                jIconCreatorOptions.setForeColor(jLabelColorShowTextH.getBackground());
                jIconCreatorOptions.setFont((String) jComboBoxFontText.getSelectedItem());
                break;
            }
        }
    }

    private void updateOptionsFromFormAux(int currentShape,
                                          JCheckBox jCheckBoxTrimImage,
                                          JCheckBox jCheckBoxForeMaskImage,
                                          JSlider jSliderPaddingImage,
                                          JRadioButton jRadioButtonCenterImage,
                                          JRadioButton jRadioButtonNoneImage,
                                          JRadioButton jRadioButtonCircleImage,
                                          JLabel jLabelColorShowImageL) {
        boolean currentScaling = jRadioButtonCenterImage.isSelected();
        jIconCreatorOptions.setTrim(jCheckBoxTrimImage.isSelected());
        jIconCreatorOptions.setMask(jCheckBoxForeMaskImage.isSelected());
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

    // Drag-and-drop files to JTextField.
    // See this for additional information:
    //  https://stackoverflow.com/a/9111327
    //  https://stackoverflow.com/a/13387897
    private void registerDropOnTextField() {
        jTextFieldPathImage.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    Object data = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (data instanceof List) {
                        List<?> droppedFiles = (List<?>) data;
                        File droppedFile = (File) droppedFiles.get(droppedFiles.size() - 1);

                        boolean isImageFile = ImageFilter.isImageFile(droppedFile);
                        if (isImageFile) {
                            jIconCreatorOptions.setBigImage(isImageBigSize(droppedFile));
                            jTextFieldPathImage.setText(droppedFile.getAbsolutePath());
                            jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
                            updatePreviewIcons();
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
        statusTimer = new Timer(DELAY_10S, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jLabelStatusBar.setText(java.util.ResourceBundle.getBundle("Bundle").getString("JIconCreatorGui.jLabelStatusBar.text"));
            }
        });
        statusTimer.setInitialDelay(DELAY_10S); // 10 seconds.
        statusTimer.setRepeats(false);
    }

    private void registerOpenFileDialog() {
        jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new ImageFilter());
        jFileChooser.setAccessory(new ImagePreview(jFileChooser));
        jFileChooser.setAcceptAllFileFilterUsed(false);
    }

    private void registerOpenDirectoryDialog() {
        jDirectoryChooser = new JFileChooser();
        jDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jDirectoryChooser.setAcceptAllFileFilterUsed(false);
    }

    private void generateStyleMenuItems() {
        HashMap<String, String> installedStyles = new HashMap<>();
        ArrayList<JRadioButtonMenuItem> styleMenuItems = new ArrayList<>();

        UIManager.LookAndFeelInfo[] styles = UIManager.getInstalledLookAndFeels();

        boolean isWindows = false;

        for (UIManager.LookAndFeelInfo info : styles) {
            String styleName = info.getName();

            styleMenuItems.add(new JRadioButtonMenuItem(styleName));
            installedStyles.put(styleName, info.getClassName());

            if (styleName.startsWith("Windows")) {
                isWindows = true;
            }
        }

        for (final JRadioButtonMenuItem menuItem : styleMenuItems) {
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        UIManager.setLookAndFeel(installedStyles.get(menuItem.getText()));
                        SwingUtilities.updateComponentTreeUI(getRootPane());
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }
                }
            });

            buttonGroupStyles.add(menuItem);
            jMenuStyle.add(menuItem);

            if (menuItem.getText().equals("Windows") && isWindows) {
                menuItem.setSelected(true);
            } else if (UIManager.getLookAndFeel().getName().equals(menuItem.getText())) {
                menuItem.setSelected(true);
            }
        }

        if (isWindows) {
            try {
                UIManager.setLookAndFeel(installedStyles.get("Windows"));
                SwingUtilities.updateComponentTreeUI(getRootPane());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
    }

    private void setColorToImage(Color color, boolean backColor) {
        if (backColor) {
            jIconCreatorOptions.setBackColor(color);
        } else {
            jIconCreatorOptions.setForeColor(color);
        }

        updatePreviewIcons();
    }

    private void setRandomColor(JLabel showColorLabel, boolean backColor) {
        Color randomColor = new Color(
                new Random().nextInt(256),
                new Random().nextInt(256),
                new Random().nextInt(256));

        showColorLabel.setBackground(randomColor);
        setColorToImage(randomColor, backColor);
    }

    private void generateFontComboboxItems() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontArray = graphicsEnvironment.getAvailableFontFamilyNames();
        for (String font : fontArray) {
            jComboBoxFontText.addItem(font);
        }
    }

    /**
     * Creates new form JIconCreator
     */
    public JIconCreatorGui() {
        jIconCreatorOptions = new JIconCreatorOptions();

        initComponents();

        registerDropOnTextField();
        registerStatusTimer();
        registerOpenFileDialog();
        registerOpenDirectoryDialog();

        generateStyleMenuItems();
        generateFontComboboxItems();

        createAssetSetWizardState = new CreateAssetSetWizardState();
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
        buttonGroupScheme = new javax.swing.ButtonGroup();
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
        jCheckBoxForeMaskImage = new javax.swing.JCheckBox();
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
        jCheckBoxForeMaskClipart = new javax.swing.JCheckBox();
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
        jCheckBoxForeMaskText = new javax.swing.JCheckBox();
        jComboBoxFontText = new javax.swing.JComboBox<>();
        jButtonSetText = new javax.swing.JButton();
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
        jMenuSaveAs = new javax.swing.JMenu();
        jMenuItemSaDrawable = new javax.swing.JMenuItem();
        jMenuItemSaMipmap = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuStyle = new javax.swing.JMenu();
        jMenuScheme = new javax.swing.JMenu();
        jRadioButtonMenuItemDrawable = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemMipmap = new javax.swing.JRadioButtonMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("JIconCreatorGui.title")); // NOI18N
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 1024, 768));
        setMaximumSize(new java.awt.Dimension(1024, 768));
        setMinimumSize(new java.awt.Dimension(725, 550));
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jSplitPane.setDividerLocation(580);

        jPanelPreview.setMinimumSize(new java.awt.Dimension(150, 500));
        jPanelPreview.setLayout(new javax.swing.BoxLayout(jPanelPreview, javax.swing.BoxLayout.PAGE_AXIS));

        jLabelMdpiL.setText(bundle.getString("JIconCreatorGui.jLabelMdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelMdpiL);

        jLabelMdpiI.setText(bundle.getString("JIconCreatorGui.jLabelMdpiI.text")); // NOI18N
        jLabelMdpiI.setMaximumSize(new java.awt.Dimension(48, 48));
        jLabelMdpiI.setMinimumSize(new java.awt.Dimension(48, 48));
        jLabelMdpiI.setPreferredSize(new java.awt.Dimension(48, 48));
        jPanelPreview.add(jLabelMdpiI);

        jLabelHdpiL.setText(bundle.getString("JIconCreatorGui.jLabelHdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelHdpiL);

        jLabelHdpiI.setText(bundle.getString("JIconCreatorGui.jLabelHdpiI.text")); // NOI18N
        jLabelHdpiI.setMaximumSize(new java.awt.Dimension(72, 72));
        jLabelHdpiI.setMinimumSize(new java.awt.Dimension(72, 72));
        jLabelHdpiI.setPreferredSize(new java.awt.Dimension(72, 72));
        jPanelPreview.add(jLabelHdpiI);

        jLabelXhdpiL.setText(bundle.getString("JIconCreatorGui.jLabelXhdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelXhdpiL);

        jLabelXhdpiI.setText(bundle.getString("JIconCreatorGui.jLabelXhdpiI.text")); // NOI18N
        jLabelXhdpiI.setMaximumSize(new java.awt.Dimension(96, 96));
        jLabelXhdpiI.setMinimumSize(new java.awt.Dimension(96, 96));
        jLabelXhdpiI.setPreferredSize(new java.awt.Dimension(96, 96));
        jPanelPreview.add(jLabelXhdpiI);

        jLabelXxhdpiL.setText(bundle.getString("JIconCreatorGui.jLabelXxhdpiL.text")); // NOI18N
        jPanelPreview.add(jLabelXxhdpiL);

        jLabelXxhdpiI.setText(bundle.getString("JIconCreatorGui.jLabelXxhdpiI.text")); // NOI18N
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

        jButtonPreview.setText(bundle.getString("JIconCreatorGui.jButtonPreview.text")); // NOI18N
        jButtonPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviewActionPerformed(evt);
            }
        });
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

        jTextFieldPathImage.setText(bundle.getString("JIconCreatorGui.jTextFieldPathImage.text")); // NOI18N
        jTextFieldPathImage.setMinimumSize(new java.awt.Dimension(237, 25));
        jTextFieldPathImage.setPreferredSize(new java.awt.Dimension(237, 25));
        jTextFieldPathImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPathImageActionPerformed(evt);
            }
        });

        jButtonBrowseImage.setText(bundle.getString("JIconCreatorGui.jButtonBrowseImage.text")); // NOI18N
        jButtonBrowseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseImageActionPerformed(evt);
            }
        });

        jButtonResetImage.setText(bundle.getString("JIconCreatorGui.jButtonResetImage.text")); // NOI18N
        jButtonResetImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetImageActionPerformed(evt);
            }
        });

        jCheckBoxTrimImage.setSelected(true);
        jCheckBoxTrimImage.setText(bundle.getString("JIconCreatorGui.jCheckBoxTrimImage.text")); // NOI18N
        jCheckBoxTrimImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTrimImageActionPerformed(evt);
            }
        });

        jSliderPaddingImage.setValue(15);
        jSliderPaddingImage.setMinimumSize(new java.awt.Dimension(237, 16));
        jSliderPaddingImage.setPreferredSize(new java.awt.Dimension(237, 16));
        jSliderPaddingImage.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderPaddingImageStateChanged(evt);
            }
        });

        jLabelPercentsImage.setText(bundle.getString("JIconCreatorGui.jLabelPercentsImage.text")); // NOI18N

        buttonGroupScalingImage.add(jRadioButtonCropImage);
        jRadioButtonCropImage.setSelected(true);
        jRadioButtonCropImage.setText(bundle.getString("JIconCreatorGui.jRadioButtonCropImage.text")); // NOI18N
        jRadioButtonCropImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCropImageActionPerformed(evt);
            }
        });

        buttonGroupScalingImage.add(jRadioButtonCenterImage);
        jRadioButtonCenterImage.setText(bundle.getString("JIconCreatorGui.jRadioButtonCenterImage.text")); // NOI18N
        jRadioButtonCenterImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCenterImageActionPerformed(evt);
            }
        });

        buttonGroupShapeImage.add(jRadioButtonNoneImage);
        jRadioButtonNoneImage.setText(bundle.getString("JIconCreatorGui.jRadioButtonNoneImage.text")); // NOI18N
        jRadioButtonNoneImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNoneImageActionPerformed(evt);
            }
        });

        buttonGroupShapeImage.add(jRadioButtonSquareImage);
        jRadioButtonSquareImage.setSelected(true);
        jRadioButtonSquareImage.setText(bundle.getString("JIconCreatorGui.jRadioButtonSquareImage.text")); // NOI18N
        jRadioButtonSquareImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSquareImageActionPerformed(evt);
            }
        });

        buttonGroupShapeImage.add(jRadioButtonCircleImage);
        jRadioButtonCircleImage.setText(bundle.getString("JIconCreatorGui.jRadioButtonCircleImage.text")); // NOI18N
        jRadioButtonCircleImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCircleImageActionPerformed(evt);
            }
        });

        jButtonChooseImageL.setText(bundle.getString("JIconCreatorGui.jButtonChooseImageL.text")); // NOI18N
        jButtonChooseImageL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseImageLActionPerformed(evt);
            }
        });

        jButtonRandomImageL.setText(bundle.getString("JIconCreatorGui.jButtonRandomImageL.text")); // NOI18N
        jButtonRandomImageL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRandomImageLActionPerformed(evt);
            }
        });

        jLabelColorShowImageL.setBackground(new java.awt.Color(255, 204, 0));
        jLabelColorShowImageL.setText(bundle.getString("JIconCreatorGui.jLabelColorShowImageL.text")); // NOI18N
        jLabelColorShowImageL.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowImageL.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowImageL.setOpaque(true);
        jLabelColorShowImageL.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelColorImageL.setText(bundle.getString("JIconCreatorGui.jLabelColorImageL.text")); // NOI18N

        jLabelShapeImage.setText(bundle.getString("JIconCreatorGui.jLabelShapeImage.text")); // NOI18N

        jLabelScalingImage.setText(bundle.getString("JIconCreatorGui.jLabelScalingImage.text")); // NOI18N

        jLabelPaddingImage.setText(bundle.getString("JIconCreatorGui.jLabelPaddingImage.text")); // NOI18N

        jLabelOptionsImage.setText(bundle.getString("JIconCreatorGui.jLabelOptionsImage.text")); // NOI18N

        jLabelImage.setText(bundle.getString("JIconCreatorGui.jLabelImage.text")); // NOI18N

        jCheckBoxForeMaskImage.setSelected(true);
        jCheckBoxForeMaskImage.setText(bundle.getString("JIconCreatorGui.jCheckBoxForeMaskImage.text")); // NOI18N
        jCheckBoxForeMaskImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxForeMaskImageActionPerformed(evt);
            }
        });

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
                        .addGap(6, 6, 6)
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
                        .addGap(6, 6, 6)
                        .addGroup(jPanelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPercentsImage)
                            .addComponent(jCheckBoxForeMaskImage)))
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
                    .addComponent(jLabelOptionsImage)
                    .addComponent(jCheckBoxForeMaskImage))
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
                .addContainerGap(283, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("JIconCreatorGui.jPanelImage.TabConstraints.tabTitle"), jPanelImage); // NOI18N

        jLabelOptionsClipart.setText(bundle.getString("JIconCreatorGui.jLabelOptionsClipart.text")); // NOI18N

        jCheckBoxTrimClipart.setSelected(true);
        jCheckBoxTrimClipart.setText(bundle.getString("JIconCreatorGui.jCheckBoxTrimClipart.text")); // NOI18N
        jCheckBoxTrimClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTrimClipartActionPerformed(evt);
            }
        });

        jLabelPaddingClipart.setText(bundle.getString("JIconCreatorGui.jLabelPaddingClipart.text")); // NOI18N

        jSliderPaddingClipart.setValue(15);
        jSliderPaddingClipart.setMinimumSize(new java.awt.Dimension(237, 16));
        jSliderPaddingClipart.setPreferredSize(new java.awt.Dimension(237, 16));
        jSliderPaddingClipart.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderPaddingClipartStateChanged(evt);
            }
        });

        jLabelPercentsClipart.setText(bundle.getString("JIconCreatorGui.jLabelPercentsClipart.text")); // NOI18N

        jLabelNameClipart.setText(bundle.getString("JIconCreatorGui.jLabelNameClipart.text")); // NOI18N
        jLabelNameClipart.setMaximumSize(new java.awt.Dimension(237, 25));
        jLabelNameClipart.setMinimumSize(new java.awt.Dimension(237, 25));
        jLabelNameClipart.setPreferredSize(new java.awt.Dimension(237, 25));

        jLabelScalingClipart.setText(bundle.getString("JIconCreatorGui.jLabelScalingClipart.text")); // NOI18N

        buttonGroupScalingClipart.add(jRadioButtonCropClipart);
        jRadioButtonCropClipart.setSelected(true);
        jRadioButtonCropClipart.setText(bundle.getString("JIconCreatorGui.jRadioButtonCropClipart.text")); // NOI18N
        jRadioButtonCropClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCropClipartActionPerformed(evt);
            }
        });

        buttonGroupScalingClipart.add(jRadioButtonCenterClipart);
        jRadioButtonCenterClipart.setText(bundle.getString("JIconCreatorGui.jRadioButtonCenterClipart.text")); // NOI18N
        jRadioButtonCenterClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCenterClipartActionPerformed(evt);
            }
        });

        jLabelShapeClipart.setText(bundle.getString("JIconCreatorGui.jLabelShapeClipart.text")); // NOI18N

        buttonGroupShapeClipart.add(jRadioButtonNoneClipart);
        jRadioButtonNoneClipart.setText(bundle.getString("JIconCreatorGui.jRadioButtonNoneClipart.text")); // NOI18N
        jRadioButtonNoneClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNoneClipartActionPerformed(evt);
            }
        });

        buttonGroupShapeClipart.add(jRadioButtonSquareClipart);
        jRadioButtonSquareClipart.setSelected(true);
        jRadioButtonSquareClipart.setText(bundle.getString("JIconCreatorGui.jRadioButtonSquareClipart.text")); // NOI18N
        jRadioButtonSquareClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSquareClipartActionPerformed(evt);
            }
        });

        buttonGroupShapeClipart.add(jRadioButtonCircleClipart);
        jRadioButtonCircleClipart.setText(bundle.getString("JIconCreatorGui.jRadioButtonCircleClipart.text")); // NOI18N
        jRadioButtonCircleClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCircleClipartActionPerformed(evt);
            }
        });

        jLabelColorClipartL.setText(bundle.getString("JIconCreatorGui.jLabelColorClipartL.text")); // NOI18N

        jButtonChooseClipartL.setText(bundle.getString("JIconCreatorGui.jButtonChooseClipartL.text")); // NOI18N
        jButtonChooseClipartL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseClipartLActionPerformed(evt);
            }
        });

        jButtonRandomClipartL.setText(bundle.getString("JIconCreatorGui.jButtonRandomClipartL.text")); // NOI18N
        jButtonRandomClipartL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRandomClipartLActionPerformed(evt);
            }
        });

        jLabelColorShowClipartL.setBackground(new java.awt.Color(153, 153, 153));
        jLabelColorShowClipartL.setText(bundle.getString("JIconCreatorGui.jLabelColorShowClipartL.text")); // NOI18N
        jLabelColorShowClipartL.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartL.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartL.setOpaque(true);
        jLabelColorShowClipartL.setPreferredSize(new java.awt.Dimension(25, 25));

        jButtonChooseClipart.setText(bundle.getString("JIconCreatorGui.jButtonChooseClipart.text")); // NOI18N
        jButtonChooseClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseClipartActionPerformed(evt);
            }
        });

        jButtonResetClipart.setText(bundle.getString("JIconCreatorGui.jButtonResetClipart.text")); // NOI18N
        jButtonResetClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetClipartActionPerformed(evt);
            }
        });

        jLabelColorClipartH.setText(bundle.getString("JIconCreatorGui.jLabelColorClipartH.text")); // NOI18N

        jButtonChooseClipartH.setText(bundle.getString("JIconCreatorGui.jButtonChooseClipartH.text")); // NOI18N
        jButtonChooseClipartH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseClipartHActionPerformed(evt);
            }
        });

        jButtonRandomClipartH.setText(bundle.getString("JIconCreatorGui.jButtonRandomClipartH.text")); // NOI18N
        jButtonRandomClipartH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRandomClipartHActionPerformed(evt);
            }
        });

        jLabelColorShowClipartH.setBackground(new java.awt.Color(255, 255, 153));
        jLabelColorShowClipartH.setText(bundle.getString("JIconCreatorGui.jLabelColorShowClipartH.text")); // NOI18N
        jLabelColorShowClipartH.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartH.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowClipartH.setOpaque(true);
        jLabelColorShowClipartH.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelClipart.setText(bundle.getString("JIconCreatorGui.jLabelClipart.text")); // NOI18N

        jCheckBoxForeMaskClipart.setSelected(true);
        jCheckBoxForeMaskClipart.setText(bundle.getString("JIconCreatorGui.jCheckBoxForeMaskClipart.text")); // NOI18N
        jCheckBoxForeMaskClipart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxForeMaskClipartActionPerformed(evt);
            }
        });

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
                        .addGap(6, 6, 6)
                        .addComponent(jButtonChooseClipart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonResetClipart))
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addComponent(jCheckBoxTrimClipart)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBoxForeMaskClipart))
                    .addGroup(jPanelClipartLayout.createSequentialGroup()
                        .addComponent(jSliderPaddingClipart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
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
                    .addComponent(jCheckBoxTrimClipart)
                    .addComponent(jCheckBoxForeMaskClipart))
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
                .addContainerGap(240, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("JIconCreatorGui.jPanelClipart.TabConstraints.tabTitle"), jPanelClipart); // NOI18N

        jLabelText.setText(bundle.getString("JIconCreatorGui.jLabelText.text")); // NOI18N

        jTextFieldText.setText(bundle.getString("JIconCreatorGui.jTextFieldText.text")); // NOI18N
        jTextFieldText.setMinimumSize(new java.awt.Dimension(237, 25));
        jTextFieldText.setPreferredSize(new java.awt.Dimension(237, 25));
        jTextFieldText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTextActionPerformed(evt);
            }
        });

        jButtonRestText.setText(bundle.getString("JIconCreatorGui.jButtonRestText.text")); // NOI18N
        jButtonRestText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRestTextActionPerformed(evt);
            }
        });

        jLabelOptionsText.setText(bundle.getString("JIconCreatorGui.jLabelOptionsText.text")); // NOI18N

        jCheckBoxTrimText.setSelected(true);
        jCheckBoxTrimText.setText(bundle.getString("JIconCreatorGui.jCheckBoxTrimText.text")); // NOI18N
        jCheckBoxTrimText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTrimTextActionPerformed(evt);
            }
        });

        jLabelPaddingText.setText(bundle.getString("JIconCreatorGui.jLabelPaddingText.text")); // NOI18N

        jSliderPaddingText.setValue(15);
        jSliderPaddingText.setMinimumSize(new java.awt.Dimension(237, 16));
        jSliderPaddingText.setPreferredSize(new java.awt.Dimension(237, 16));
        jSliderPaddingText.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderPaddingTextStateChanged(evt);
            }
        });

        jLabelPercentsText.setText(bundle.getString("JIconCreatorGui.jLabelPercentsText.text")); // NOI18N

        jLabelScalingText.setText(bundle.getString("JIconCreatorGui.jLabelScalingText.text")); // NOI18N

        buttonGroupScalingText.add(jRadioButtonCropText);
        jRadioButtonCropText.setSelected(true);
        jRadioButtonCropText.setText(bundle.getString("JIconCreatorGui.jRadioButtonCropText.text")); // NOI18N
        jRadioButtonCropText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCropTextActionPerformed(evt);
            }
        });

        buttonGroupScalingText.add(jRadioButtonCenterText);
        jRadioButtonCenterText.setText(bundle.getString("JIconCreatorGui.jRadioButtonCenterText.text")); // NOI18N
        jRadioButtonCenterText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCenterTextActionPerformed(evt);
            }
        });

        jLabelShapeText.setText(bundle.getString("JIconCreatorGui.jLabelShapeText.text")); // NOI18N

        buttonGroupShapeText.add(jRadioButtonNoneText);
        jRadioButtonNoneText.setText(bundle.getString("JIconCreatorGui.jRadioNoneText.text")); // NOI18N
        jRadioButtonNoneText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNoneTextActionPerformed(evt);
            }
        });

        buttonGroupShapeText.add(jRadioButtonSquareText);
        jRadioButtonSquareText.setSelected(true);
        jRadioButtonSquareText.setText(bundle.getString("JIconCreatorGui.jRadioButtonSquareText.text")); // NOI18N
        jRadioButtonSquareText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSquareTextActionPerformed(evt);
            }
        });

        buttonGroupShapeText.add(jRadioButtonCircleText);
        jRadioButtonCircleText.setText(bundle.getString("JIconCreatorGui.jRadioButtonCircle.text")); // NOI18N
        jRadioButtonCircleText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCircleTextActionPerformed(evt);
            }
        });

        jLabelColorTextL.setText(bundle.getString("JIconCreatorGui.jLabelColorTextL.text")); // NOI18N

        jButtonChooseTextL.setText(bundle.getString("JIconCreatorGui.jButtonChooseTextL.text")); // NOI18N
        jButtonChooseTextL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseTextLActionPerformed(evt);
            }
        });

        jButtonRandomTextL.setText(bundle.getString("JIconCreatorGui.jButtonRandomTextL.text")); // NOI18N
        jButtonRandomTextL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRandomTextLActionPerformed(evt);
            }
        });

        jLabelColorShowTextL.setBackground(new java.awt.Color(0, 51, 51));
        jLabelColorShowTextL.setText(bundle.getString("JIconCreatorGui.jLabelColorShowTextL.text")); // NOI18N
        jLabelColorShowTextL.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextL.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextL.setOpaque(true);
        jLabelColorShowTextL.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelColorTextH.setText(bundle.getString("JIconCreatorGui.jLabelColorTextH.text")); // NOI18N

        jButtonChooseTextH.setText(bundle.getString("JIconCreatorGui.jButtonChooseTextH.text")); // NOI18N
        jButtonChooseTextH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseTextHActionPerformed(evt);
            }
        });

        jButtonRandomTextH.setText(bundle.getString("JIconCreatorGui.jButtonRandomTextH.text")); // NOI18N
        jButtonRandomTextH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRandomTextHActionPerformed(evt);
            }
        });

        jLabelColorShowTextH.setBackground(new java.awt.Color(255, 51, 51));
        jLabelColorShowTextH.setText(bundle.getString("JIconCreatorGui.jLabelColorShowTextH.text")); // NOI18N
        jLabelColorShowTextH.setMaximumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextH.setMinimumSize(new java.awt.Dimension(25, 25));
        jLabelColorShowTextH.setOpaque(true);
        jLabelColorShowTextH.setPreferredSize(new java.awt.Dimension(25, 25));

        jLabelFontText.setText(bundle.getString("JIconCreatorGui.jLabelFontText.text")); // NOI18N

        jCheckBoxForeMaskText.setSelected(true);
        jCheckBoxForeMaskText.setText(bundle.getString("JIconCreatorGui.jCheckBoxForeMaskText.text")); // NOI18N
        jCheckBoxForeMaskText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxForeMaskTextActionPerformed(evt);
            }
        });

        jComboBoxFontText.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default" }));
        jComboBoxFontText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFontTextActionPerformed(evt);
            }
        });

        jButtonSetText.setText(bundle.getString("JIconCreatorGui.jButtonSetText.text")); // NOI18N
        jButtonSetText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetTextActionPerformed(evt);
            }
        });

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
                        .addComponent(jButtonSetText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRestText))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jCheckBoxTrimText)
                        .addGap(6, 6, 6)
                        .addComponent(jCheckBoxForeMaskText))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jSliderPaddingText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabelPercentsText))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jButtonChooseTextL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRandomTextL)
                        .addGap(6, 6, 6)
                        .addComponent(jLabelColorShowTextL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jRadioButtonCircleText))
                    .addGroup(jPanelTextLayout.createSequentialGroup()
                        .addComponent(jButtonChooseTextH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRandomTextH)
                        .addGap(6, 6, 6)
                        .addComponent(jLabelColorShowTextH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBoxFontText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanelTextLayout.setVerticalGroup(
            jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTextLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelText)
                    .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRestText)
                    .addComponent(jButtonSetText))
                .addGap(18, 18, 18)
                .addGroup(jPanelTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOptionsText)
                    .addComponent(jCheckBoxTrimText)
                    .addComponent(jCheckBoxForeMaskText))
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
                    .addComponent(jComboBoxFontText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("JIconCreatorGui.jPanelText.TabConstraints.tabTitle"), jPanelText); // NOI18N

        jPanelGeneral.add(jTabbedPane);

        jPanelStatusBar.setMaximumSize(new java.awt.Dimension(800, 30));
        jPanelStatusBar.setMinimumSize(new java.awt.Dimension(400, 30));
        jPanelStatusBar.setPreferredSize(new java.awt.Dimension(550, 30));
        jPanelStatusBar.setLayout(new javax.swing.BoxLayout(jPanelStatusBar, javax.swing.BoxLayout.LINE_AXIS));
        jPanelStatusBar.add(fillerSmallH1);

        jLabelStatusBar.setText(bundle.getString("JIconCreatorGui.jLabelStatusBar.text")); // NOI18N
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

        jButtonSave.setText(bundle.getString("JIconCreatorGui.jButtonSave.text")); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanelStatusBar.add(jButtonSave);
        jPanelStatusBar.add(fillerSmallH2);

        jButtonSaveAs.setText(bundle.getString("JIconCreatorGui.jButtonSaveAs.text")); // NOI18N
        jButtonSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveAsActionPerformed(evt);
            }
        });
        jPanelStatusBar.add(jButtonSaveAs);

        jPanelGeneral.add(jPanelStatusBar);

        jSplitPane.setLeftComponent(jPanelGeneral);

        getContentPane().add(jSplitPane);

        jMenuFile.setText(bundle.getString("JIconCreatorGui.jMenuFile.text")); // NOI18N

        jMenuItemPreview.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemPreview.setText(bundle.getString("JIconCreatorGui.jMenuItemPreview.text")); // NOI18N
        jMenuItemPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreviewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemPreview);
        jMenuFile.add(jSeparator1);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText(bundle.getString("JIconCreatorGui.jMenuItemSave.text")); // NOI18N
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);

        jMenuSaveAs.setText(bundle.getString("JIconCreatorGui.jMenuSaveAs.text")); // NOI18N

        jMenuItemSaDrawable.setText(bundle.getString("JIconCreatorGui.jMenuItemSaDrawable.text")); // NOI18N
        jMenuItemSaDrawable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaDrawableActionPerformed(evt);
            }
        });
        jMenuSaveAs.add(jMenuItemSaDrawable);

        jMenuItemSaMipmap.setText(bundle.getString("JIconCreatorGui.jMenuItemSaMipmap.text")); // NOI18N
        jMenuItemSaMipmap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaMipmapActionPerformed(evt);
            }
        });
        jMenuSaveAs.add(jMenuItemSaMipmap);

        jMenuFile.add(jMenuSaveAs);
        jMenuFile.add(jSeparator2);

        jMenuItemExit.setText(bundle.getString("JIconCreatorGui.jMenuItemExit.text")); // NOI18N
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar.add(jMenuFile);

        jMenuStyle.setText(bundle.getString("JIconCreatorGui.jMenuStyle.text")); // NOI18N
        jMenuBar.add(jMenuStyle);

        jMenuScheme.setText(bundle.getString("JIconCreatorGui.jMenuScheme.text")); // NOI18N

        buttonGroupScheme.add(jRadioButtonMenuItemDrawable);
        jRadioButtonMenuItemDrawable.setSelected(true);
        jRadioButtonMenuItemDrawable.setText(bundle.getString("JIconCreatorGui.jRadioButtonMenuItemDrawable.text")); // NOI18N
        jRadioButtonMenuItemDrawable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemDrawableActionPerformed(evt);
            }
        });
        jMenuScheme.add(jRadioButtonMenuItemDrawable);

        buttonGroupScheme.add(jRadioButtonMenuItemMipmap);
        jRadioButtonMenuItemMipmap.setText(bundle.getString("JIconCreatorGui.jRadioButtonMenuItemMipmap.text")); // NOI18N
        jRadioButtonMenuItemMipmap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemMipmapActionPerformed(evt);
            }
        });
        jMenuScheme.add(jRadioButtonMenuItemMipmap);

        jMenuBar.add(jMenuScheme);

        jMenuHelp.setText(bundle.getString("JIconCreatorGui.jMenuHelp.text")); // NOI18N

        jMenuItemAbout.setText(bundle.getString("JIconCreatorGui.jMenuItemAbout.text")); // NOI18N
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
        updateOptionsFromForm();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                updatePreviewIcons();
            }
        });
    }//GEN-LAST:event_jTabbedPaneStateChanged

    private void jButtonRandomImageLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRandomImageLActionPerformed
        setRandomColor(jLabelColorShowImageL, true);
    }//GEN-LAST:event_jButtonRandomImageLActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        JIconCreatorGui.this.processWindowEvent(
                new java.awt.event.WindowEvent(JIconCreatorGui.this,java.awt.event.WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jButtonChooseImageLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseImageLActionPerformed
        setColorByChooseButton(jLabelColorShowImageL, true);
    }//GEN-LAST:event_jButtonChooseImageLActionPerformed

    private void jRadioButtonNoneImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNoneImageActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_NONE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonNoneImageActionPerformed

    private void jRadioButtonSquareImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSquareImageActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_SQUARE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonSquareImageActionPerformed

    private void jRadioButtonCircleImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCircleImageActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_CIRCLE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCircleImageActionPerformed

    private void jRadioButtonCropImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCropImageActionPerformed
        jIconCreatorOptions.setCrop(false);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCropImageActionPerformed

    private void jRadioButtonCenterImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCenterImageActionPerformed
        jIconCreatorOptions.setCrop(true);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCenterImageActionPerformed

    private void jCheckBoxTrimImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTrimImageActionPerformed
        jIconCreatorOptions.setTrim(jCheckBoxTrimImage.isSelected());
        updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxTrimImageActionPerformed

    private void jButtonBrowseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseImageActionPerformed
        browseImage();
    }//GEN-LAST:event_jButtonBrowseImageActionPerformed

    private void jButtonResetImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetImageActionPerformed
        resetImage();
    }//GEN-LAST:event_jButtonResetImageActionPerformed

    private void jTextFieldPathImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPathImageActionPerformed
        jIconCreatorOptions.setImageFilePath(jTextFieldPathImage.getText());
        updatePreviewIcons();
    }//GEN-LAST:event_jTextFieldPathImageActionPerformed

    private void jSliderPaddingImageStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderPaddingImageStateChanged
        sliderPaddingHelper(jLabelPercentsImage, evt, jIconCreatorOptions.isBigImage());
    }//GEN-LAST:event_jSliderPaddingImageStateChanged

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        saveFilesAction(jTextFieldStatusFileName.getText());
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jCheckBoxForeMaskImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxForeMaskImageActionPerformed
        jIconCreatorOptions.setMask(jCheckBoxForeMaskImage.isSelected());
        updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxForeMaskImageActionPerformed

    private void jComboBoxFontTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontTextActionPerformed
        jIconCreatorOptions.setFont((String) jComboBoxFontText.getSelectedItem());
        updatePreviewIcons();
    }//GEN-LAST:event_jComboBoxFontTextActionPerformed

    private void jRadioButtonMenuItemDrawableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemDrawableActionPerformed
        isMipmapScheme = false;
    }//GEN-LAST:event_jRadioButtonMenuItemDrawableActionPerformed

    private void jRadioButtonMenuItemMipmapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemMipmapActionPerformed
        isMipmapScheme = true;
    }//GEN-LAST:event_jRadioButtonMenuItemMipmapActionPerformed

    private void jButtonSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveAsActionPerformed
        saveFilesToDirectory(isMipmapScheme);
    }//GEN-LAST:event_jButtonSaveAsActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        saveFilesAction(jTextFieldStatusFileName.getText());
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemSaDrawableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaDrawableActionPerformed
        saveFilesToDirectory(false);
    }//GEN-LAST:event_jMenuItemSaDrawableActionPerformed

    private void jMenuItemSaMipmapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaMipmapActionPerformed
        saveFilesToDirectory(true);
    }//GEN-LAST:event_jMenuItemSaMipmapActionPerformed

    private void jCheckBoxTrimClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTrimClipartActionPerformed
        jIconCreatorOptions.setTrim(jCheckBoxTrimClipart.isSelected());
        updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxTrimClipartActionPerformed

    private void jCheckBoxForeMaskClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxForeMaskClipartActionPerformed
        jIconCreatorOptions.setMask(jCheckBoxForeMaskClipart.isSelected());
        updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxForeMaskClipartActionPerformed

    private void jSliderPaddingClipartStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderPaddingClipartStateChanged
        sliderPaddingHelper(jLabelPercentsClipart, evt, true);
    }//GEN-LAST:event_jSliderPaddingClipartStateChanged

    private void jRadioButtonCropClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCropClipartActionPerformed
        jIconCreatorOptions.setCrop(false);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCropClipartActionPerformed

    private void jRadioButtonCenterClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCenterClipartActionPerformed
        jIconCreatorOptions.setCrop(true);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCenterClipartActionPerformed

    private void jRadioButtonNoneClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNoneClipartActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_NONE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonNoneClipartActionPerformed

    private void jRadioButtonSquareClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSquareClipartActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_SQUARE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonSquareClipartActionPerformed

    private void jRadioButtonCircleClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCircleClipartActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_CIRCLE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCircleClipartActionPerformed

    private void jButtonChooseClipartLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseClipartLActionPerformed
        setColorByChooseButton(jLabelColorShowClipartL, true);
    }//GEN-LAST:event_jButtonChooseClipartLActionPerformed

    private void jButtonRandomClipartLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRandomClipartLActionPerformed
        setRandomColor(jLabelColorShowClipartL, true);
    }//GEN-LAST:event_jButtonRandomClipartLActionPerformed

    private void jButtonChooseClipartHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseClipartHActionPerformed
        setColorByChooseButton(jLabelColorShowClipartH, false);
    }//GEN-LAST:event_jButtonChooseClipartHActionPerformed

    private void jButtonRandomClipartHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRandomClipartHActionPerformed
        setRandomColor(jLabelColorShowClipartH, false);
    }//GEN-LAST:event_jButtonRandomClipartHActionPerformed

    private void jButtonChooseClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseClipartActionPerformed
        // TODO add your handling code here:
        System.out.println("STUB!");
    }//GEN-LAST:event_jButtonChooseClipartActionPerformed

    private void jButtonResetClipartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetClipartActionPerformed
        // TODO add your handling code here:
        System.out.println("STUB!");
    }//GEN-LAST:event_jButtonResetClipartActionPerformed

    private void jCheckBoxTrimTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTrimTextActionPerformed
        jIconCreatorOptions.setTrim(jCheckBoxTrimText.isSelected());
        updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxTrimTextActionPerformed

    private void jCheckBoxForeMaskTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxForeMaskTextActionPerformed
        jIconCreatorOptions.setMask(jCheckBoxForeMaskText.isSelected());
        updatePreviewIcons();
    }//GEN-LAST:event_jCheckBoxForeMaskTextActionPerformed

    private void jSliderPaddingTextStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderPaddingTextStateChanged
        sliderPaddingHelper(jLabelPercentsText, evt, true);
    }//GEN-LAST:event_jSliderPaddingTextStateChanged

    private void jRadioButtonCropTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCropTextActionPerformed
        jIconCreatorOptions.setCrop(false);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCropTextActionPerformed

    private void jRadioButtonCenterTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCenterTextActionPerformed
        jIconCreatorOptions.setCrop(true);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCenterTextActionPerformed

    private void jRadioButtonNoneTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNoneTextActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_NONE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonNoneTextActionPerformed

    private void jRadioButtonSquareTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSquareTextActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_SQUARE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonSquareTextActionPerformed

    private void jRadioButtonCircleTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCircleTextActionPerformed
        jIconCreatorOptions.setShapeType(JIconCreatorOptions.SHAPE_CIRCLE);
        updatePreviewIcons();
    }//GEN-LAST:event_jRadioButtonCircleTextActionPerformed

    private void jButtonChooseTextLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseTextLActionPerformed
        setColorByChooseButton(jLabelColorShowTextL, true);
    }//GEN-LAST:event_jButtonChooseTextLActionPerformed

    private void jButtonRandomTextLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRandomTextLActionPerformed
        setRandomColor(jLabelColorShowTextL, true);
    }//GEN-LAST:event_jButtonRandomTextLActionPerformed

    private void jButtonChooseTextHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseTextHActionPerformed
        setColorByChooseButton(jLabelColorShowTextH, false);
    }//GEN-LAST:event_jButtonChooseTextHActionPerformed

    private void jButtonRandomTextHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRandomTextHActionPerformed
        setRandomColor(jLabelColorShowTextH, false);
    }//GEN-LAST:event_jButtonRandomTextHActionPerformed

    private void jButtonRestTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRestTextActionPerformed
        jTextFieldText.setText(JIconCreatorOptions.DEFAULT_TEXT);
        jIconCreatorOptions.setTextString(jTextFieldText.getText());
        updatePreviewIcons();
    }//GEN-LAST:event_jButtonRestTextActionPerformed

    private void jTextFieldTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTextActionPerformed
        jIconCreatorOptions.setTextString(jTextFieldText.getText());
        updatePreviewIcons();
    }//GEN-LAST:event_jTextFieldTextActionPerformed

    private void jButtonSetTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetTextActionPerformed
        jIconCreatorOptions.setTextString(jTextFieldText.getText());
        updatePreviewIcons();
    }//GEN-LAST:event_jButtonSetTextActionPerformed

    private void jButtonPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewActionPerformed
        // TODO add your handling code here:
        System.out.println("STUB!");
    }//GEN-LAST:event_jButtonPreviewActionPerformed

    private void jMenuItemPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPreviewActionPerformed
        // TODO add your handling code here:
        System.out.println("STUB!");
    }//GEN-LAST:event_jMenuItemPreviewActionPerformed

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        // TODO add your handling code here:
        System.out.println("STUB!");
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

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
    private javax.swing.ButtonGroup buttonGroupScheme;
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
    private javax.swing.JButton jButtonSetText;
    private javax.swing.JCheckBox jCheckBoxForeMaskClipart;
    private javax.swing.JCheckBox jCheckBoxForeMaskImage;
    private javax.swing.JCheckBox jCheckBoxForeMaskText;
    private javax.swing.JCheckBox jCheckBoxTrimClipart;
    private javax.swing.JCheckBox jCheckBoxTrimImage;
    private javax.swing.JCheckBox jCheckBoxTrimText;
    private javax.swing.JComboBox<String> jComboBoxFontText;
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
    private javax.swing.JMenuItem jMenuItemSaDrawable;
    private javax.swing.JMenuItem jMenuItemSaMipmap;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenu jMenuSaveAs;
    private javax.swing.JMenu jMenuScheme;
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
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemDrawable;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemMipmap;
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
}
