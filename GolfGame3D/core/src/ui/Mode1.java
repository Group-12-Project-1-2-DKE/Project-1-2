package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Objects.*;
import Physics.*;
import Course.*;
import com.mygdx.game.*;

public class Mode1 extends JFrame {


    FileReaders reader = new FileReaders();
    GridLayout experimentLayout = new GridLayout(8,2);
    TextField gC = new TextField("9.81");
    TextField massOfBall = new TextField("45.93");
    TextField frictionCoeficcient = new TextField("0.131");
    TextField maxSpeed = new TextField("3");
    TextField startCoordinatesX = new TextField("0.0");
    TextField startCoordinatesY = new TextField("0.0");
    TextField goalCoordinatesX = new TextField("0.0");
    TextField goalCoordinatesY = new TextField("10.0");
    TextField tolerance = new TextField("3");

    TextField function = new TextField("-0.01* x + 0.003* x^2 + 0.04 * y");


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

        JLabel label = new JLabel("Please specify your preferences:", SwingConstants.CENTER);
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

        compsToExperiment.add(new JLabel("Heigth:  "));
        compsToExperiment.add(function);

        compsToExperiment.add(new JLabel("Tolerance:  "));
        compsToExperiment.add(tolerance);

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

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(2,2));
        comboPanel.add(new JLabel(""));
        comboPanel.add(new JLabel(""));
        comboPanel.add(new JLabel(""));

        JButton playButton = new JButton("Play!");
        comboPanel.add(playButton);

        //JButton playButton = new JButton("Play!");
        //compsToExperiment.add(playButton);

        pane.add(comboPanel2, BorderLayout.NORTH);
        pane.add(compsToExperiment, BorderLayout.CENTER);
        pane.add(comboPanel, BorderLayout.SOUTH);


        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                reader.setGravity(Double.valueOf(gC.getText().toString()));
                reader.setMass(Double.valueOf(massOfBall.getText().toString()));
                reader.setCoefficientOfFriction(Double.valueOf(frictionCoeficcient.getText().toString()));
                reader.setMaxVelocity(Double.valueOf(maxSpeed.getText().toString()));
                reader.setTolerance(Double.valueOf(tolerance.getText().toString()));
                reader.setXStart(Double.valueOf(startCoordinatesX.getText().toString()));
                reader.setYStart(Double.valueOf(startCoordinatesY.getText().toString()));
                reader.setXGoal(Double.valueOf(goalCoordinatesX.getText().toString()));
                reader.setyGoal(Double.valueOf(goalCoordinatesY.getText().toString()));
                String str = function.getText();
                reader.setFunction(str);

                PuttingCourse course = new PuttingCourse(reader.getFunction(), new Vector2D(reader.getXStart(), reader.getYStart()),
                        new Vector2D(reader.getXGoal(), reader.getYGoal()), new Ball(new Vector2D(reader.getXStart(), reader.getYStart()), reader.getMass(), (float)reader.getDiameter()),//Diameter has yet to be implemented
                        reader.getCoefficientOfFriction(), reader.getMaxVelocity(), reader.getTolerance());
                EulerSolver engine = new EulerSolver();
                engine.set_fric_coefficient(course.getFrictionCoefficient());
                engine.set_grav_constant(reader.getGravity());
                PuttingSimulator p = new PuttingSimulator(course, engine);

               // GetVector v = new GetVector("Golf 2D - Mode 1", p);
               // v.createAndShowGUI();

                //System.out.println("Waiting for user to enter value...");
                //while (!v.userEnteredValue()){

                //}


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
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
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
