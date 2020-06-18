package Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

// https://stackoverflow.com/questions/21815839/simple-java-2d-array-maze-sample

public class MazeGenerator {
    private int dimX; // dimension of maze
    private int dimY;
    private int[][] maze; // output grid
    private Cell[][] cells; // 2d array of Cells
    private Random random = new Random(); // The random object

    // constructor
    public MazeGenerator(int xDimension, int yDimension) {
        dimX = xDimension;
        dimY = yDimension;
        maze = new int[xDimension * 4 + 1][yDimension * 2 + 1];
        initializeMaze();
        generateMaze(cells[0][0]);
    }

    private void initializeMaze() {
        // create cells array and filled it with cells
        cells = new Cell[dimX][dimY];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                cells[x][y] = new Cell(x, y, false); // Create the cell not as a wall
            }
        }
    }

    private void generateMaze(Cell startAt) {
        // don't start at cells that do not exist
        if (startAt == null) {
            return;
        }

        startAt.open = false; // is used in the generation of the maze.
        ArrayList<Cell> cellsList = new ArrayList<>();
        cellsList.add(startAt);

        // While all the cells are not empty
        while (!cellsList.isEmpty()) {
            Cell cell;
            // this is to reduce the number of long branches
            if (random.nextInt(1)==0)
                cell = cellsList.remove(random.nextInt(cellsList.size()));
            else {
                cell = cellsList.remove(cellsList.size() - 1);
            }
            // collection of neighbors of the cell.
            ArrayList<Cell> neighbors = new ArrayList<>();
            // Potential neighbors of the cell.
            Cell[] potentialNeighbors = new Cell[]{
                    getCell(cell.x + 1, cell.y),
                    getCell(cell.x, cell.y + 1),
                    getCell(cell.x - 1, cell.y),
                    getCell(cell.x, cell.y - 1)
            };

            for (Cell potentialNeighbor : potentialNeighbors) {
                // skip if outside, is a wall or is not opened
                if (potentialNeighbor == null || !potentialNeighbor.open) { //  || potentialNeighbor.wall
                    continue;
                }
                // If the neighbor exist --> add it to the list of neighbor.
                neighbors.add(potentialNeighbor);
            }
            // If there is no neighbors left keep going.
            if (neighbors.isEmpty()) {
                continue;
            }
            // get random cell among the neighbors of the cell.
            Cell selected = neighbors.get(random.nextInt(neighbors.size()));
            // add as neighbor
            selected.open = false; // indicate cell closed for generation
            // Add the selected cell to the neighbor of the cell --> There is no wall between those 2 cells.
            cell.addNeighbor(selected);
            cellsList.add(cell);
            cellsList.add(selected);
        }
    }

    // used to get a Cell at x, y; returns null out of bounds
    public Cell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
            return null;
        }
    }

//    // solve the maze starting from the start state (A-star algorithm)
//    public void solve(int startX, int startY, int endX, int endY) {
//        // re initialize cells for path finding
//        for (Cell[] cellrow : cells) {
//            for (Cell cell : cellrow) {
//                cell.parent = null;
//                cell.visited = false;
//                cell.inPath = false;
//                cell.travelled = 0;
//                cell.projectedDist = -1;
//            }
//        }
//        // cells still being considered
//        ArrayList<Cell> openCells = new ArrayList<>();
//        // cell being considered
//        Cell endCell = getCell(endX, endY);
//        if (endCell == null){
//            return;
//        }
//        // anonymous block to delete start, because not used later
//        Cell start = getCell(startX, startY);
//        if (start == null){
//            return;
//        }
//        start.projectedDist = getDistance(start, 0, endCell);
//        start.visited = true;       // Mark the cell as visited
//        openCells.add(start);       // add the cell the the list so that it can be considered.
//
//        // boolean solving = true;
//        while (true) {
//            if (openCells.isEmpty()) return; // quit, no path
//            // sort openCells according to least projected distance
//            Collections.sort(openCells, new Comparator<Cell>(){
//                @Override
//                public int compare(Cell cell1, Cell cell2) {
//                    double diff = cell1.projectedDist - cell2.projectedDist;
//                    if (diff > 0) return 1;
//                    else if (diff < 0) return -1;
//                    else return 0;
//                }
//            });
//            Cell current = openCells.remove(0); // pop cell least projectedDist
//            if (current == endCell) break; // at end
//            for (Cell neighbor : current.neighbors) {
//                double projDist = getDistance(neighbor,
//                        current.travelled + 1, endCell);
//                if (!neighbor.visited || // not visited yet
//                        projDist < neighbor.projectedDist) { // better path
//                    neighbor.parent = current;
//                    neighbor.visited = true;
//                    neighbor.projectedDist = projDist;
//                    neighbor.travelled = current.travelled + 1;
//                    if (!openCells.contains(neighbor))
//                        openCells.add(neighbor);
//                }
//            }
//        }
//        // create path from end to beginning
//        Cell backtracking = endCell;
//        backtracking.inPath = true;
//        while (backtracking.parent != null) {
//            backtracking = backtracking.parent;
//            backtracking.inPath = true;
//        }
//    }

    public double getDistance(Cell current,double travelled, Cell end){
        return travelled + Math.abs(current.x - end.x) +
                Math.abs(current.y - current.x);
    }

    // draw the maze
    public void updateGrid() {
        int open = 0;
        int wall = 1;
        int path = 2;
        // fill background
        for (int x = 0; x < maze.length; x ++) {
            for (int y = 0; y < maze[x].length; y ++) {
                maze[x][y] = open;
            }
        }
        // build walls
        for (int x = 0; x < maze.length; x ++) {
            for (int y = 0; y < maze[x].length; y ++) {
                if (x % 4 == 0 || y % 2 == 0)
                    maze[x][y] = wall;
            }
        }
        // make meaningful representation
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {
                Cell current = getCell(x, y);
                int gridX = x * 4 + 2;
                int gridY = y * 2 + 1;
                if (current.inPath) {
                    System.out.print("hi");
                    maze[gridX][gridY] = path;
                    if (current.hasBelowNeighbor())
                        if (getCell(x, y + 1).inPath) {
                            maze[gridX][gridY + 1] = path;
                            maze[gridX + 1][gridY + 1] = open;
                            maze[gridX - 1][gridY + 1] = open;
                        } else {
                            maze[gridX][gridY + 1] = open;
                            maze[gridX + 1][gridY + 1] = open;
                            maze[gridX - 1][gridY + 1] = open;
                        }
                    if (current.hasRightNeighbor())
                        if (getCell(x + 1, y).inPath) {
                            maze[gridX + 2][gridY] = path;
                            maze[gridX + 1][gridY] = path;
                            maze[gridX + 3][gridY] = path;
                        } else {
                            maze[gridX + 2][gridY] = open;
                            maze[gridX + 1][gridY] = open;
                            maze[gridX + 3][gridY] = open;
                        }
                } else {
                    maze[gridX][gridY] = open;
                    if (current.hasBelowNeighbor()) {
                        maze[gridX][gridY + 1] = open;
                        maze[gridX + 1][gridY + 1] = open;
                        maze[gridX - 1][gridY + 1] = open;
                    }
                    if (current.hasRightNeighbor()) {
                        maze[gridX + 2][gridY] = open;
                        maze[gridX + 1][gridY] = open;
                        maze[gridX + 3][gridY] = open;
                    }
                }
            }
        }
    }

    // forms a meaningful representation
    @Override
    public String toString() {
        updateGrid();
        addStartAndEnd();
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < maze[0].length; y++) {
            for (int x = 0; x < maze.length; x++) {
                output.append(maze[x][y]);
            }
            output.append("\n");
        }
        System.out.println("");
        return output.toString();
    }

    public int[][] getGrid(){
        return maze;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells){
        for (int i=0; i<cells.length; i++){
            for (int j=0; j<cells[i].length; j++){
                this.cells[i][j] = cells[i][j];
            }
        }
    }

    public void addStartAndEnd(){
        maze[1][1] = 8;         // Start position
        maze[maze.length-2][maze[0].length-2] = 9; // End position
    }
}