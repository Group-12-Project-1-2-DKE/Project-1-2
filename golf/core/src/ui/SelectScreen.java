package ui;


/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectScreen extends JFrame {
    static SelectScreen frame = new SelectScreen("Golf 2D - Menu");
    GridLayout experimentLayout = new GridLayout(1,2);

    public SelectScreen(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        compsToExperiment.setBackground(Color.LIGHT_GRAY);

        //adds a picture to the frame
      //  ImageIcon icon = new ImageIcon("golf.jpg");
       // JLabel label2 = new JLabel(icon);

        JPanel comboPanel2 = new JPanel();
        comboPanel2.setLayout(new GridLayout(2,1));

        JLabel label = new JLabel(" --- GOLF GAME 2D ---");
        label.setFont(new Font("Tahoma", Font.BOLD, 13));

        comboPanel2.add(label);
        comboPanel2.add(new JLabel(""));

        JLabel label3 = new JLabel("Please select:");

        pane.add(comboPanel2, BorderLayout.NORTH);

       // pane.add(label2, BorderLayout.CENTER);

        pane.add(label3, BorderLayout.CENTER);

        //Add buttons to experiment with Grid Layout
        JButton Mode1Button = new JButton("Mode 1");
        compsToExperiment.add(Mode1Button);

        JButton Mode2Button = new JButton("Mode 2");
        compsToExperiment.add(Mode2Button);

        pane.add(compsToExperiment, BorderLayout.SOUTH);


        Mode1Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                 Mode1.createAndShowGUI();
                 frame.setVisible(false);
            }
        });

        Mode2Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                 Mode2.createAndShowGUI();
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

