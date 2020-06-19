package Maze;

import Physics.Vector2D;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
    private Cell[][] maze;
    public ArrayList<Cell> visited = new ArrayList<>();
    private boolean pathFound;
    public Stack<Cell> actualPath = new Stack<Cell>();
    public ArrayList<Cell> solution = new ArrayList<>();
    private int count =0;
    ArrayList<Vector2D> locations = new ArrayList<>();

    public Solver(Cell[][] maze) {
        this.maze = maze;
        pathFound = false;
    }

    public void solve(){
        depthFirstSearch(maze[0][0]);
        strategicLocation(0);
        locations.add(new Vector2D(solution.get(solution.size()-1).x, solution.get(solution.size()-1).y));
        upgradeGrid();
        for(int i=0; i<locations.size(); i++){
            System.out.println("x: " + locations.get(i).getX() + "     y: " + locations.get(i).getY());
        }
    }

    private void depthFirstSearch(Cell cell){
        visited.add(cell);
        actualPath.add(cell);

        if (cell == maze[maze.length - 1][maze[0].length -1] || pathFound){
            pathFound = true;
            if (count == 0){
                solution.addAll(actualPath);
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

    private void strategicLocation(int index){
        System.out.println(solution.size());
        int localIndex = 0;
        int oldx = solution.get(index).x;
        int oldy = solution.get(index).y;
        int x = solution.get(index+1).x;
        int y = solution.get(index+1).y;
        if (oldx == x){
            for(int i=index; i<solution.size(); i++){
                if(solution.get(i).x != x && solution.get(i).x != oldx){
                    locations.add(new Vector2D(solution.get(i-1).x, solution.get(i-1).y));
                    strategicLocation(i-1);
                    return;
                }
            }
        }else if (oldy == y){
            for(int i=index; i<solution.size(); i++){
                if(solution.get(i).y != y && solution.get(i).y != oldy){
                    locations.add(new Vector2D(solution.get(i-1).x, solution.get(i-1).y));
                    strategicLocation(i-1);
                    return;
                }
            }
        }
    }
}
