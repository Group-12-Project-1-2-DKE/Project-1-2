
 
/*
 * GridLayoutDemo.java
 *
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


 
public class Mode1 extends JFrame {

    GridLayout experimentLayout = new GridLayout(8,2);
     
    public Mode1(String name) {
        super(name);
        setResizable(false);
    }
   
    public void addComponentsToPane(final Container pane) {
      
        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
         

        //Add buttons to experiment with Grid Layout
        TextField gC = new TextField("9.81");
        compsToExperiment.add(new JLabel("Gravitational constant here"));
        compsToExperiment.add(gC);

        TextField massOfBall = new TextField("45.93");
        compsToExperiment.add(new JLabel("Fill in the mass of the ball here"));
        compsToExperiment.add(massOfBall);

        TextField frictionCoeficcient = new TextField("0.131");
        compsToExperiment.add(new JLabel("Fill in the friction coefficent here"));
        compsToExperiment.add(frictionCoeficcient);

        TextField maxSpeed = new TextField("3");
        compsToExperiment.add(new JLabel("Fill in the maximum speed here"));
        compsToExperiment.add(maxSpeed);

        TextField startCoordinates = new TextField("0.0, 0.0");
        compsToExperiment.add(new JLabel("Fill in the start coordinates in here"));
        compsToExperiment.add(startCoordinates);

        TextField goalCoordinates = new TextField("0.0, 10.0");
        compsToExperiment.add(new JLabel("Fill in the goal coordinates here"));
        compsToExperiment.add(goalCoordinates);

        compsToExperiment.add(new JLabel("fill in the x and y here"));
        compsToExperiment.add(new TextField("x, y"));
         
        JButton playButton = new JButton("Play!");
        compsToExperiment.add(playButton);
 
        pane.add(compsToExperiment, BorderLayout.NORTH);


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
