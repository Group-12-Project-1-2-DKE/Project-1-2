package com.mygdx.game;

public class Variables {
    public static float gravity;
    //public static float ballDiameter;
    public static float ballMass;
    public static float coefficientOfFriction;
    public static float tolerance;
    public static String function;
    public static float goalX;
    public static float goalY;
    public static float startX;
    public static float startY;
    public static float shootX;
    public static float shootY;

    // Booleans corresponding to the physic engine the user choose.
    public static boolean euler = true;             // Set the euler solver as the default value.
    public static boolean rungeKutta = false;
    public static boolean verlet = false;
    // Booleans corresponding to the game mode the user choose.
    public static boolean ai = false;
    public static boolean singlePlayer = true;      // Set the single player a the default value.
}
