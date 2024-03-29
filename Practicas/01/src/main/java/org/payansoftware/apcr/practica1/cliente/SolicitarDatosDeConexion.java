/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.payansoftware.apcr.practica1.cliente;

import java.net.Socket;

/**
 *
 * @author rene
 */
public class SolicitarDatosDeConexion extends javax.swing.JFrame {
    private String ipServidor;
    private int puertoServidor;
    private Socket conexion;
    
    /**
     * Creates new form SolicitarDatosDeConexion
     */
    public SolicitarDatosDeConexion() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtextIP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtextPuerto = new javax.swing.JTextField();
        jbtnConectar = new javax.swing.JButton();
        jlblError = new javax.swing.JLabel();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("IP:");

        jtextIP.setText("localhost");
        jtextIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextIPActionPerformed(evt);
            }
        });

        jLabel2.setText("Puerto:");

        jtextPuerto.setText("3016");
        jtextPuerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextPuertoActionPerformed(evt);
            }
        });

        jbtnConectar.setText("Conectar");
        jbtnConectar.setToolTipText("");
        jbtnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnConectarActionPerformed(evt);
            }
        });

        jlblError.setForeground(new java.awt.Color(255, 0, 0));
        jlblError.setText("Error al conectar con el servidor, verifique los datos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jbtnConectar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtextPuerto))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jtextIP, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtextIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtextPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jlblError)
                .addGap(30, 30, 30)
                .addComponent(jbtnConectar)
                .addGap(30, 30, 30))
        );

        jlblError.setVisible(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnConectarActionPerformed
        jlblError.setVisible(false);
        try{
            this.ipServidor = jtextIP.getText();
            this.puertoServidor = Integer.parseInt(jtextPuerto.getText());
            if(this.puertoServidor <= 0 || this.puertoServidor >= 36000){
                throw new Error("Puerto invalido");             
            }
            this.conexion = new Socket(ipServidor, puertoServidor);
            ListaDeProductos ldp = new ListaDeProductos(conexion);
            ldp.inicializarFlujos();
            ldp.cargarProductos();
            this.setVisible(false);
            ldp.setVisible(true);          
            ldp.mostrarProductos();
        }catch(Exception e){
            e.printStackTrace();
            jlblError.setVisible(true);
        }
    }//GEN-LAST:event_jbtnConectarActionPerformed

    private void jtextIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextIPActionPerformed

    private void jtextPuertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextPuertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextPuertoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton jbtnConectar;
    private javax.swing.JLabel jlblError;
    private javax.swing.JTextField jtextIP;
    private javax.swing.JTextField jtextPuerto;
    // End of variables declaration//GEN-END:variables
}
