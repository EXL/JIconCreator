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

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import java.io.IOException;

/**
 *
 * @author exl
 */
public class JIconCreatorClipart extends javax.swing.JDialog {

    private final static String[] clipartNames = {
        "rating-important.png",
        "collections-cloud.png",
        "collections-collection.png",
        "content-discard.png",
        "content-edit.png",
        "content-email.png",
        "social-send-now.png",
        "location-web-site.png",
        "av-make-available-offline.png",
        "av-play.png",
        "device-access-accounts.png",
        "device-access-alarms.png",
        "device-access-bightness-low.png",
        "device-access-camera.png",
        "device-access-data-usage.png",
        "device-access-flash-on.png",
        "device-access-mic.png",
        "device-access-network-wifi.png",
        "device-access-not-secure.png",
        "device-access-ring-volume.png",
        "device-access-time.png",
        "device-access-volume-on.png",
        "hardware-gamepad.png",
        "hardware-headphones.png",
        "hardware-mouse.png"
    };

    /**
     * Creates new form JIconCreatorClipart
     */
    public JIconCreatorClipart(JIconCreatorGui parent, boolean modal,
                               JIconCreatorGlue glue, JIconCreatorOptions options) {
        super(parent, modal);

        initComponents();

        try {
            for (String clipartName : clipartNames) {
                BufferedImage buttonIcon = glue.getClipartImage(clipartName, false);
                JButton button = new JButton(new ImageIcon(buttonIcon));
                if (UIManager.getLookAndFeel().getName().equals("Metal")) {
                    button.setBorderPainted(false);
                    button.setContentAreaFilled(false);
                }
                button.setPreferredSize(new Dimension(50, 50));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        parent.setClipartNameOnForm(clipartName);
                        processWindowEvent(new WindowEvent(JIconCreatorClipart.this, WindowEvent.WINDOW_CLOSING));
                    }
                });
                jPanelClipart.add(button);
                if (options.getClipartName().equals(clipartName)) {
                    button.requestFocus();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelClipart = new javax.swing.JPanel();
        jButtonClipartClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("JIconCreatorClipart.title")); // NOI18N
        setBounds(new java.awt.Rectangle(0, 0, 280, 340));
        setMaximumSize(new java.awt.Dimension(280, 340));
        setMinimumSize(new java.awt.Dimension(280, 340));
        setName("clipartDialog"); // NOI18N
        setPreferredSize(new java.awt.Dimension(280, 340));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanelClipart.setMaximumSize(new java.awt.Dimension(260, 260));
        jPanelClipart.setMinimumSize(new java.awt.Dimension(260, 260));
        jPanelClipart.setPreferredSize(new java.awt.Dimension(260, 260));
        jPanelClipart.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        getContentPane().add(jPanelClipart, new java.awt.GridBagConstraints());

        jButtonClipartClose.setText(bundle.getString("JIconCreatorClipart.jButtonClipartClose.text")); // NOI18N
        jButtonClipartClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClipartCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        getContentPane().add(jButtonClipartClose, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClipartCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClipartCloseActionPerformed
        processWindowEvent(new WindowEvent(JIconCreatorClipart.this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButtonClipartCloseActionPerformed

    public void showCliparDialog() {
        /* Display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.gc();
                    }
                });
                setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClipartClose;
    private javax.swing.JPanel jPanelClipart;
    // End of variables declaration//GEN-END:variables
}
