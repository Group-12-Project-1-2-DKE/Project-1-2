package ui;


/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;



public class SelectScreen extends JFrame {
    static SelectScreen frame = new SelectScreen("Golf - Menu");
    GridLayout experimentLayout = new GridLayout(2,3);

    public SelectScreen(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        compsToExperiment.setBackground(Color.LIGHT_GRAY);

        //adds a picture to the frame
        ImageIcon icon = new ImageIcon("golf.jpg");
        JLabel label2 = new JLabel(icon);

        compsToExperiment.add(new JLabel(""));
        compsToExperiment.add(label2);
        compsToExperiment.add(new JLabel(""));


        //Add buttons to experiment with Grid Layout
        JButton Mode1Button = new JButton("Mode 1");
        compsToExperiment.add(Mode1Button);

        compsToExperiment.add(new JLabel(""));

        JButton Mode2Button = new JButton("Mode 2");
        compsToExperiment.add(Mode2Button);

        pane.add(compsToExperiment, BorderLayout.NORTH);


        Mode1Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                 Mode1.createAndShowGUI();
                 frame.setVisible(false);
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

