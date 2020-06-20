package com.mygdx.game;

import Physics.Vector2D;

public class Variables {
    public static float gravity = (float) 9.81;
    public static float ballMass;
    public static float coefficientOfFriction = (float)0.1;
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
    public static boolean singlePlayer = true;  // Set the single player a the default value.

    public static Vector2D upperBound = new Vector2D(100, 100);
    public static Vector2D lowerBound = new Vector2D(-100, -100);

    public static boolean maze = false;
    public static int mazeX;
    public static int mazeY;
}