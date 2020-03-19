package ui;


/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Mode2 extends JFrame {

    GridLayout experimentLayout2 = new GridLayout(2,2);
    TextField file = new TextField("");

    public Mode2(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment2 = new JPanel();
        compsToExperiment2.setLayout(experimentLayout2);
        compsToExperiment2.setBackground(Color.LIGHT_GRAY);

        JPanel comboPanel2 = new JPanel();
        comboPanel2.setLayout(new GridLayout(2,2));

        JLabel label = new JLabel("Please give the file name:           ");
        label.setFont(new Font("Tahoma", Font.BOLD, 12));
        comboPanel2.add(label);

        compsToExperiment2.add(new JLabel("File: "));
        compsToExperiment2.add(file);
        

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(2,2));
        comboPanel.add(new JLabel(""));
        comboPanel.add(new JLabel(""));
        comboPanel.add(new JLabel(""));
        JButton playButton = new JButton("Play!");
        comboPanel.add(playButton);

       // JButton playButton = new JButton("Play!");
        //compsToExperiment.add(playButton);

        pane.add(comboPanel2, BorderLayout.NORTH);
        pane.add(compsToExperiment2, BorderLayout.CENTER);
        pane.add(comboPanel, BorderLayout.SOUTH);


        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                //READER NEEDS TO BE IMPLEMENTED HERE (TO READ THE FILE NAME)

            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    static void createAndShowGUI() {
        //Create and set up the window.
        Mode2 frame2 = new Mode2("Golf 2D - Mode 2");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame2.addComponentsToPane(frame2.getContentPane());
        //Display the window.
        frame2.pack();
        frame2.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
