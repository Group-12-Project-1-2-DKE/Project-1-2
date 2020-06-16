package Maze;

public class MainTestMaze {

    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(2, 2);
        //maze.solve(0, 0, 5 - 1, 5 -1);
        maze.draw();
    }
}
