package Maze;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
    private Cell[][] maze;
    public ArrayList<Cell> visited = new ArrayList<>();
    private boolean pathFound;
    public Stack<Cell> actualPath = new Stack<Cell>();
    public ArrayList<Cell> solution = new ArrayList<>();
    private int count =0;

    public Solver(Cell[][] maze) {
        this.maze = maze;
        pathFound = false;
    }

    public void solve(){
        depthFirstSearch(maze[0][0]);
        upgradeGrid();
    }

//    private void depthFirstSearch(Cell cell){
//        while(!pathFound){
//            if (!visited.contains(cell)) {
//                visited.add(cell);
//                path.add(cell);
//                if(cell != maze[maze.length - 1][maze[0].length -1]){
//                    //cell.visited = true;
//                    if(!cell.neighbors.isEmpty()){
//                        for(int i=0; i<cell.neighbors.size(); i++){
//                            if(!visited.contains(cell.neighbors.get(i))){
//                                if (!pathFound){
//                                    depthFirstSearch(cell.neighbors.get(i));
//                                }
//                            }else{
//                                path.pop();
//                            }
//                        }
//                    }
//                }else{
//                    pathFound = true;
//                    break;
//                }
//            }
//        }
//    }

    private void depthFirstSearch(Cell cell){
        visited.add(cell);
        actualPath.add(cell);

        if (cell == maze[maze.length - 1][maze[0].length -1] || pathFound){
            pathFound = true;
            if (count == 0){
                for (int i=0; i<actualPath.size(); i++){
                    solution.add(actualPath.get(i));
                }
                count++;
            }
            return;
        }else{
            for(int i=0; i<cell.neighbors.size(); i++){
                if(!visited.contains(cell.neighbors.get(i))){
                    depthFirstSearch(cell.neighbors.get(i));
                }
            }
            actualPath.pop();
        }
    }

    private void upgradeGrid(){
        for (int i=0; i<solution.size(); i++){
            solution.get(i).inPath = true;
        }
    }
}
