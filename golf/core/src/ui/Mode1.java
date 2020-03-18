package ui;



/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Mode1 extends JFrame {
    FileReaders reader = new FileReaders();
    GridLayout experimentLayout = new GridLayout(11,2);
    TextField gC = new TextField("9.81");
    TextField massOfBall = new TextField("45.93");
    TextField frictionCoeficcient = new TextField("0.131");
    TextField maxSpeed = new TextField("3");
    TextField startCoordinates = new TextField("0.0, 0.0");
    TextField goalCoordinates = new TextField("0.0, 10.0");
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

        JLabel label = new JLabel("Please specify your preferences:");
        label.setFont(new Font("Tahoma", Font.BOLD, 12));

        compsToExperiment.add(label);

        //for empty space between lines
        compsToExperiment.add(new JLabel(""));
        compsToExperiment.add(new JLabel(""));
        compsToExperiment.add(new JLabel(""));

        //Add buttons to experiment with Grid Layout
        
        compsToExperiment.add(new JLabel("Gravitational constant: "));
        compsToExperiment.add(gC);

        
        compsToExperiment.add(new JLabel("Mass of the ball: "));
        compsToExperiment.add(massOfBall);

        
        compsToExperiment.add(new JLabel("Friction coefficient: "));
        compsToExperiment.add(frictionCoeficcient);

        
        compsToExperiment.add(new JLabel("Maximum speed:  "));
        compsToExperiment.add(maxSpeed);

        
        compsToExperiment.add(new JLabel("Start coordinates:"));
        compsToExperiment.add(startCoordinates);

        
        compsToExperiment.add(new JLabel("Goal coordinates: "));
        compsToExperiment.add(goalCoordinates);

        compsToExperiment.add(new JLabel("The x and y: "));
        compsToExperiment.add(new TextField("x, y"));

        //for empty space between lines and button
        compsToExperiment.add(new JLabel(""));
        compsToExperiment.add(new JLabel(""));

        JButton playButton = new JButton("Play!");
        compsToExperiment.add(playButton);

        pane.add(compsToExperiment, BorderLayout.NORTH);


        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                    
                    reader.setGravity(Double.valueOf(gC.getText().toString()));
                    reader.setMass(Double.valueOf(massOfBall.getText().toString()));
                    reader.setCoefficientOfFriction(Double.valueOf(frictionCoeficcient.getText().toString()));
                    reader.setInitialSpeed(Double.valueOf(maxSpeed.getText().toString()));



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
        Mode1 frame = new Mode1("Mode 1");
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

