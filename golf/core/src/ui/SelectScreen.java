package ui;


/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class SelectScreen extends JFrame {

    GridLayout experimentLayout = new GridLayout(0,2);

    public SelectScreen(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);


        //Add buttons to experiment with Grid Layout
        JButton Mode1Button = new JButton("Mode 1");
        compsToExperiment.add(Mode1Button);


        JButton Mode2Button = new JButton("Mode 2");
        compsToExperiment.add(Mode2Button);

        pane.add(compsToExperiment, BorderLayout.NORTH);


        Mode1Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){


            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        SelectScreen frame = new SelectScreen("GridLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
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

