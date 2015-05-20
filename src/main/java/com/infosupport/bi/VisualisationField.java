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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.netbeans.api.visual.graph.GraphScene;

/**
 *
 * @author MaartenKo
 */
public class VisualisationField extends JPanel {

    private final String connect;
    private final String username;
    private final String password;
    private final DataFlowHandler dataflowhandler;
    private int datamodelID;
    private GraphScene scene;

    public VisualisationField() {
        connect = "jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;";
        username = "Visualisation";
        password = "Info2015";
        dataflowhandler = new DataFlowHandler(connect, username, password);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        final JScrollPane scrollPane = new JScrollPane();

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
                systems.addItem(new ComboboxItem(availableSystems.getInt(1), availableSystems.getString(4)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Er kan geen connectie worden gemaakt met de database", "Interne fout", JOptionPane.ERROR_MESSAGE);
        }

        systems.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ComboboxItem datamodel = (ComboboxItem) systems.getSelectedItem();
                datamodelID = datamodel.getId();
                ResultSet mappings = dataflowhandler.getMappingSets(datamodelID);
                dataflowhandler.createMappingList(mappings);
                List<DestinationAttribute> dataflow = dataflowhandler.getDataFlow();
                scene = new GraphSceneImpl(dataflow);
                scrollPane.setViewportView(scene.createView());
            }
        });

        //aanmaken van een opslaan knop
        JButton saveAction = new JButton("Save");
        saveAction.setPreferredSize(new Dimension(100, 40));

        saveAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                GraphSceneImpl graphScene = (GraphSceneImpl) scene;
                ChangeHandler changes = graphScene.getChanges();
                changes.checkDoubleEntries();
                boolean saved = changes.savesChanges();

                if (saved) {
                    JOptionPane.showMessageDialog(null, "De wijzigingen zijn opgeslagen", "Opgeslagen", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "De gemaakte wijzigingen veroorzaken conflicten. \nDe wijzigingen zijn niet opgeslagen en worden ongedaan gemaakt", "Syntax fout", JOptionPane.ERROR_MESSAGE);
                }

                ComboboxItem datamodel = (ComboboxItem) systems.getSelectedItem();
                datamodelID = datamodel.getId();
                ResultSet mappings = dataflowhandler.getMappingSets(datamodelID);
                dataflowhandler.createMappingList(mappings);
                List<DestinationAttribute> dataflow = dataflowhandler.getDataFlow();
                scene = new GraphSceneImpl(dataflow);
                scrollPane.setViewportView(scene.createView());
            }

        });

        //toevoegen van comboxbox aan toolbar
        toolbar.add(new JLabel("Systemen:"));
        toolbar.add(systems);
        toolbar.add(saveAction);

        //toevoegen van de components aan het frame bovenin
        add(toolbar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        ResultSet mappings = dataflowhandler.getMappingSets(datamodelID);
        dataflowhandler.createMappingList(mappings);

        List<DestinationAttribute> dataflow = dataflowhandler.getDataFlow();
        scene = new GraphSceneImpl(dataflow);
        scrollPane.setViewportView(scene.createView());

    }

    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //aanmaken van het frame
        final JFrame jf = new JFrame("IS DataFlow visualisation tool");
        jf.setMinimumSize(new Dimension(700, 500));
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setContentPane(new VisualisationField());
        jf.pack();
        jf.setVisible(true);

    }

}
