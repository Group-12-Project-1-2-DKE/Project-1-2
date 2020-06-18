package Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MazeSolver {
    private Cell[][] maze;

    public MazeSolver(Cell[][] maze) {
        this.maze = maze;
    }

    // solve the maze starting from the start state (A-star algorithm)
    public void solve() {
        int startX = 0;
        int startY = 0;
        int endX = maze.length - 1;
        int endY = maze[0].length -1;

        // cells still being considered
        ArrayList<Cell> openCells = new ArrayList<>();
        // cell being considered
        Cell endCell = maze[endX][endY];
        if (endCell == null) {
            return;
        }
        // anonymous block to delete start, because not used later
        Cell start = maze[startX][startY];
        if (start == null) {
            return;
        }
        start.projectedDist = getDistance(start, 0, endCell);
        start.visited = true;       // Mark the cell as visited
        openCells.add(start);       // add the cell the the list so that it can be considered.

        // boolean solving = true;
        while (true) {
            if (openCells.isEmpty()) {
                return; // quit, no path
            }
            // sort openCells according to least projected.
            Collections.sort(openCells, new Comparator<Cell>() {
                @Override
                public int compare(Cell cell1, Cell cell2) {
                    double diff = cell1.projectedDist - cell2.projectedDist;
                    if (diff > 0) return 1;
                    else if (diff < 0) return -1;
                    else return 0;
                }
            });
            Cell current = openCells.remove(0); // pop cell with the smallest projectedDist
            if (current == endCell) {
                break; // We reached the end
            }
            for (Cell neighbor : current.neighbors) {
                double projDist = getDistance(neighbor, current.travelled + 1, endCell);
                if (!neighbor.visited || // not visited yet
                        projDist < neighbor.projectedDist) { // better path
                    neighbor.parent = current;
                    neighbor.visited = true;
                    neighbor.projectedDist = projDist;
                    neighbor.travelled = current.travelled + 1;
                    if (!openCells.contains(neighbor))
                        openCells.add(neighbor);
                }
            }
        }
        // create path from end to beginning
        Cell backtracking = endCell;
        backtracking.inPath = true;
        while (backtracking.parent != null) {
            backtracking = backtracking.parent;
            backtracking.inPath = true;
        }
    }

    public Cell[][] getMaze(){
        return maze;
    }

    // get the projected distance
    // Calculate the distance between the current cell and the end cell.
    public double getDistance(Cell current,double travelled, Cell end){
        return travelled + Math.abs(current.x - end.x) +
                Math.abs(current.y - current.x);
    }
}