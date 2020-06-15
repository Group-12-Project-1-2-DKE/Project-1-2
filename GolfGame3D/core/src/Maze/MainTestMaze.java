package Maze;

public class MainTestMaze {

    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(5, 5);
        maze.solve(0, 0, 5 - 1, 5 -1);
        maze.draw();
    }
}
