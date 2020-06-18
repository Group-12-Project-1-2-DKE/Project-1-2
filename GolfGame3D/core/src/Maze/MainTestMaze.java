package Maze;

public class MainTestMaze {

    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(3, 3);
        MazeSolver solver = new MazeSolver(maze.getCells());
        solver.solve();
        //maze.setCells(solver.getMaze());
        //maze.solve();
        System.out.print(maze);
    }
}
