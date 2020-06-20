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
    private ArrayList<Vector2D> locations = new ArrayList<>();

    /**
     * Constructor of the object
     * @param maze Array of cells that contains information about the maze (wall positions).
     */
    public Solver(Cell[][] maze) {
        this.maze = maze;
        pathFound = false;
    }

    /**
     * Launch the depthFirstSearch method.
     * Add the end position to the solution list.
     */
    public void solve(){
        depthFirstSearch(maze[0][0]);
        strategicLocation(0);
        locations.add(new Vector2D((solution.get(solution.size()-1).x * 4 + 2) -20, ((solution.get(solution.size()-1).y)*2 +1)-20));
        upgradeGrid();
    }

    /**
     * Depth First Search algoithm.
     * It search for a solution of the maze thanks to recursion.
     * @param cell The cell that we are working with.
     */
    private void depthFirstSearch(Cell cell){
        // Mark the cell as visited and add it to the path.
        visited.add(cell);
        actualPath.add(cell);

        // Check it the cell is the end of the maze or not or if the path is found or not.
        if (cell == maze[maze.length - 1][maze[0].length -1] || pathFound){
            // If it is the end of the maze, copy the actual path into the solution
            // And set pathFound is true.
            pathFound = true;
            if (count == 0){
                solution.addAll(actualPath);
                count++;
            }
            return;
        }else{
            // If the previous condition is not respected, go through the neighbors of the cell.
            for(int i=0; i<cell.neighbors.size(); i++){
                // If one of the neighbors is not visited yet, call depthFirstSearch with the neighbor.
                if(!visited.contains(cell.neighbors.get(i))){
                    depthFirstSearch(cell.neighbors.get(i));
                }
            }
            actualPath.pop();
        }
    }

    private void upgradeGrid(){
        // Mark all the cell that are contained in the solution as path.
        for (int i=0; i<solution.size(); i++){
            solution.get(i).inPath = true;
        }
    }

    /**
     * Tis method goes through the list containing the path.
     * When it noticed that the path change location, it stored the position
     * at which the location has changed.
     * @param index The position at the list
     */
    private void strategicLocation(int index){
        int oldx = solution.get(index).x;
        int oldy = solution.get(index).y;
        int x = solution.get(index+1).x;
        int y = solution.get(index+1).y;
        if (oldx == x){
            // Goes through the list of solution.
            for(int i=index; i<solution.size(); i++){
                // If the x is different, store the location and call the recursion.
                if(solution.get(i).x != x && solution.get(i).x != oldx){
                    locations.add(new Vector2D((solution.get(i-1).x * 4 + 2) - 20, (solution.get(i-1).y * 2 + 1) - 20));
                    strategicLocation(i-1);
                    return;
                }
            }
        }else if (oldy == y){
            for(int i=index; i<solution.size(); i++){
                // If the y is different, store the location and call the recursion.
                if(solution.get(i).y != y && solution.get(i).y != oldy){
                    locations.add(new Vector2D((solution.get(i-1).x * 4 + 2) - 20,  (solution.get(i-1).y * 2 + 1) - 20));
                    strategicLocation(i-1);
                    return;
                }
            }
        }
    }

    public ArrayList<Vector2D> getLocations() {
        return locations;
    }
}