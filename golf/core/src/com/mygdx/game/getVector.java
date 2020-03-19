package com.mygdx.game;


/*
 * GridLayoutDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Physics.*;



public class GetVector extends JFrame {

    //GetVector frame2 = new GetVector("Golf 2D - Mode 2");
    MyGdxGame game = new MyGdxGame();
    GridLayout experimentLayout2 = new GridLayout(2,2);
    TextField vectorXField = new TextField("");
    TextField vectorYField = new TextField("");
    private double tempVectorX;
    private double tempVectorY;
    private boolean userEnteredValue = false;
    private PuttingSimulator p;

    public GetVector(String name, PuttingSimulator p) {
        super(name);
        setResizable(false);
        this.p = p;
    }

    public void addComponentsToPane(final Container pane) {

        final JPanel compsToExperiment2 = new JPanel();
        compsToExperiment2.setLayout(experimentLayout2);
        compsToExperiment2.setBackground(Color.LIGHT_GRAY);

        JPanel comboPanel2 = new JPanel();
        comboPanel2.setLayout(new GridLayout(2,2));

        JLabel label = new JLabel("Please give the vectors:        ");
        label.setFont(new Font("Tahoma", Font.BOLD, 12));
        comboPanel2.add(label);

        compsToExperiment2.add(new JLabel("vector X: "));
        compsToExperiment2.add(vectorXField);
        
        compsToExperiment2.add(new JLabel("vector Y: "));
        compsToExperiment2.add(vectorYField);
        

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(2,2));
        comboPanel.add(new JLabel(""));
        comboPanel.add(new JLabel(""));
        comboPanel.add(new JLabel(""));
        JButton shootButton = new JButton("Shoot!");
        comboPanel.add(shootButton);

       // JButton playButton = new JButton("Play!");
        //compsToExperiment.add(playButton);

        pane.add(comboPanel2, BorderLayout.NORTH);
        pane.add(compsToExperiment2, BorderLayout.CENTER);
        pane.add(comboPanel, BorderLayout.SOUTH);


        shootButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                try{
                    setTempVectorX(Double.valueOf(vectorXField.getText().toString()));
                    setTempVectorY(Double.valueOf(vectorYField.getText().toString()));
                    userEnteredValue = true;
                    System.out.println(getTempVectorX());
                    System.out.println(getTempVectorY());
                    p.take_shot(new Vector2D(getTempVectorX(), getTempVectorY()));

                    //frame2.setVisible(false);
                } catch (NumberFormatException n){
                    System.out.println("Not a number!");
                }

            }
        });
    }

    public void setTempVectorX(double tempVectorX){
        this.tempVectorX = tempVectorX;
    }

    public void setTempVectorY(double tempVectorY){
        this.tempVectorY = tempVectorY;
    }

    public double getTempVectorX(){
        return tempVectorX;
    }

    public double getTempVectorY(){
        return tempVectorY;
    }

    public boolean userEnteredValue(){
        return userEnteredValue;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        addComponentsToPane(getContentPane());
        //Display the window.
        pack();
        setVisible(true);
        userEnteredValue = false;
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
                //createAndShowGUI();
            }
        });
    }
}
