/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author MaartenKo
 */
public class VisualisationField {

    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //aanmaken database connectie
        String connect = "jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;";
        String username = "Visualisation";
        String password = "Info2015";
        DataFlowHandler dataflowhandler = new DataFlowHandler(connect, username, password);

        //aanmaken van het frame
        final JFrame jf = new JFrame("IS DataFlow visualisation tool");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout());
        jf.setMinimumSize(new Dimension(500, 400));
        jf.setVisible(true);

        //aanmaken van de toolbar 
        JToolBar toolbar = new JToolBar();
        toolbar.setMargin(new Insets(5, 5, 5, 5));
        toolbar.setLayout(new FlowLayout(5));
        toolbar.setFloatable(false);

        //aanmaken combobox met de beschikbare systemen
        final JComboBox systems = new JComboBox();
        ResultSet availableSystems = dataflowhandler.getSystems();
                try {
            while (availableSystems.next()) {
               systems.addItem(availableSystems.getString(4));
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Er kan geen connectie worden gemaakt met de database", "Interne fout",JOptionPane.ERROR_MESSAGE);
        }
        //toevoegen van comboxbox aan toolbar
         toolbar.add(new JLabel("Systemen:"));
         toolbar.add(systems);

        //toevoegen van de toolbar aan het frame bovenin
        jf.add(toolbar, BorderLayout.NORTH);
        
    }
}
