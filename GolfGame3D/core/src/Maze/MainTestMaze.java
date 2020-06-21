package Maze;

import AI.MazeAI;

public class MainTestMaze {

    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(5, 5);
        //MazeSolver solver = new MazeSolver(maze.getCells());
        //solver.solve();
        //maze.setCells(solver.getMaze());
        //maze.solve();
        Solver solver = new Solver(maze.getCells());
        solver.solve();


//        for (int i=0; i<solver.solution.size(); i++){
//            System.out.println(solver.solution.get(i));
//        }

        for (int i=0; i<solver.getLocations().size(); i++){
            System.out.println(solver.getLocations().get(i));
        }

//        for(int i=0; i<maze.getCell(0,0).neighbors.size(); i++){
//            System.out.print(maze.getCell(0,0).neighbors.get(i));
//        }
        System.out.print(maze);
    }
}
