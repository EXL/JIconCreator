package ru.exlmoto.jiconcreator;

import javax.swing.*;

public class JIconCreatorForm {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JButton saveButton;
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
    private JPanel mdpiPane;
    private JPanel hdpiPane;
    private JPanel xhdpiPane;
    private JPanel xxhdpiPane;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getMainPane() {
        return mainPanel;
    }
}
