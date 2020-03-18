package ui;



/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Mode1 extends JFrame {

    GridLayout experimentLayout = new GridLayout(7,2);

    public Mode1(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        compsToExperiment.setBackground(Color.LIGHT_GRAY);

        Font font = new Font("Courier", Font.BOLD,13);
 
        //set font for JTextField
        compsToExperiment.setFont(font);

        JPanel comboPanel2 = new JPanel();
        comboPanel2.setLayout(new GridLayout(2,2));

        JLabel label = new JLabel("Please specify your preferences:");
        label.setFont(new Font("Tahoma", Font.BOLD, 12));

        comboPanel2.add(label);

        

        //Add buttons to experiment with Grid Layout
        TextField gC = new TextField("9.81");
        compsToExperiment.add(new JLabel("Gravitational constant: "));
        compsToExperiment.add(gC);

        TextField massOfBall = new TextField("45.93");
        compsToExperiment.add(new JLabel("Mass of the ball: "));
        compsToExperiment.add(massOfBall);

        TextField frictionCoeficcient = new TextField("0.131");
        compsToExperiment.add(new JLabel("Friction coefficient: "));
        compsToExperiment.add(frictionCoeficcient);

        TextField maxSpeed = new TextField("3");
        compsToExperiment.add(new JLabel("Maximum speed:  "));
        compsToExperiment.add(maxSpeed);

        TextField startCoordinates = new TextField("0.0, 0.0");
        compsToExperiment.add(new JLabel("Start coordinates:"));
        compsToExperiment.add(startCoordinates);

        TextField goalCoordinates = new TextField("0.0, 10.0");
        compsToExperiment.add(new JLabel("Goal coordinates: "));
        compsToExperiment.add(goalCoordinates);

        compsToExperiment.add(new JLabel("The x and y: "));
        compsToExperiment.add(new TextField("x, y"));

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
        pane.add(compsToExperiment, BorderLayout.CENTER);
        pane.add(comboPanel, BorderLayout.SOUTH);


        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

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
        Mode1 frame = new Mode1("Golf - Mode 1");
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

