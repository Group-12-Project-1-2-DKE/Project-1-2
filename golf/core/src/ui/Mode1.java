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
    GridLayout experimentLayout = new GridLayout(9,2);
    TextField gC = new TextField("9.81");
    TextField massOfBall = new TextField("45.93");
    TextField frictionCoeficcient = new TextField("0.131");
    TextField maxSpeed = new TextField("3");
    TextField startCoordinatesX = new TextField("0.0");
    TextField startCoordinatesY = new TextField("0.0");
    TextField goalCoordinatesX = new TextField("0.0");
    TextField goalCoordinatesY = new TextField("10.0");
    TextField coX = new TextField("X");
    TextField coY = new TextField("Y");
    TextField acceleration = new TextField("0");
    TextField height = new TextField("0");


    public Mode1(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        compsToExperiment.setBackground(Color.LIGHT_GRAY);

        JPanel comboPanel2 = new JPanel();
        comboPanel2.setLayout(new GridLayout(2,2));

        JLabel label = new JLabel("Please specify your preferences:");
        label.setFont(new Font("Tahoma", Font.BOLD, 12));

        comboPanel2.add(label);

        //Add buttons to experiment with Grid Layout
        
        compsToExperiment.add(new JLabel("Gravitational constant: "));
        compsToExperiment.add(gC);

        compsToExperiment.add(new JLabel("Mass of the ball: "));
        compsToExperiment.add(massOfBall);

        compsToExperiment.add(new JLabel("Friction coefficient: "));
        compsToExperiment.add(frictionCoeficcient);
        
        compsToExperiment.add(new JLabel("Max speed:  "));
        compsToExperiment.add(maxSpeed);

        compsToExperiment.add(new JLabel("Acceleration:  "));
        compsToExperiment.add(acceleration);

        compsToExperiment.add(new JLabel("Heigth:  "));
        compsToExperiment.add(height);

        compsToExperiment.add(new JLabel("Start coordinates:"));
        JPanel start = new JPanel();
        start.setLayout(new GridLayout(1,2));
        start.add(startCoordinatesX);
        start.add(startCoordinatesY);

        compsToExperiment.add(start);

        
        compsToExperiment.add(new JLabel("Goal coordinates: "));
        JPanel end = new JPanel();
        end.setLayout(new GridLayout(1,2));
        end.add(goalCoordinatesX);
        end.add(goalCoordinatesY);

        compsToExperiment.add(end);

        compsToExperiment.add(new JLabel("The x and y: "));
        JPanel XY = new JPanel();
        XY.setLayout(new GridLayout(1,2));
        XY.add(coX);
        XY.add(coY);

        compsToExperiment.add(XY);

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
        Mode1 frame = new Mode1("Golf 2D - Mode 1");
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

