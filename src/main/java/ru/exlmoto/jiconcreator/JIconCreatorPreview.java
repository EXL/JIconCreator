/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlmoto.jiconcreator;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author exl
 */
public class JIconCreatorPreview extends javax.swing.JDialog {

    /**
     * Creates new form JIconCreatorPreview
     */
    public JIconCreatorPreview(java.awt.Frame parent, boolean modal, BufferedImage[] previews) {
        super(parent, modal);
        initComponents();

        jLabel3.setIcon(new ImageIcon(previews[1]));
        jLabel4.setIcon(new ImageIcon(previews[0]));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("JIconCreatorPreview.title")); // NOI18N
        getContentPane().setLayout(new java.awt.GridLayout(2, 2, 2, 2));

        jLabel1.setText(bundle.getString("JIconCreatorPreview.jLabel1.text")); // NOI18N
        getContentPane().add(jLabel1);

        jLabel2.setText(bundle.getString("JIconCreatorPreview.jLabel2.text")); // NOI18N
        getContentPane().add(jLabel2);

        jLabel3.setText(bundle.getString("JIconCreatorPreview.jLabel3.text")); // NOI18N
        getContentPane().add(jLabel3);

        jLabel4.setText(bundle.getString("JIconCreatorPreview.jLabel4.text")); // NOI18N
        getContentPane().add(jLabel4);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showPreviewDialog() {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        // Try to drop huge icons from memory.
                        System.gc();
                    }
                });
                setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
