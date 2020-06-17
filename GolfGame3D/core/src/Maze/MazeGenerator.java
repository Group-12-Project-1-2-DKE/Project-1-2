package Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

// https://stackoverflow.com/questions/21815839/simple-java-2d-array-maze-sample

public class MazeGenerator {
    private int dimensionX;
    int dimensionY; // dimension of maze
    private int[][] grid; // output grid
    private Cell[][] cells; // 2d array of Cells
    private Random random = new Random(); // The random object

    // constructor
    public MazeGenerator(int xDimension, int yDimension) {
        dimensionX = xDimension;
        dimensionY = yDimension;
        grid = new int[xDimension * 4 + 1][yDimension * 2 + 1];
        init();
        generateMaze(cells[0][0]);
    }

    private void init() {
        // create cells array and filled it with cells
        cells = new Cell[dimensionX][dimensionY];
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
//        start.projectedDist = getProjectedDistance(start, 0, endCell);
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
//                double projDist = getProjectedDistance(neighbor,
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

    // draw the maze
    public void updateGrid() {
        int open = 0;
        int wall = 1;
        int pathChar = 2;
        // fill background
        for (int x = 0; x < grid.length; x ++) {
            for (int y = 0; y < grid[x].length; y ++) {
                grid[x][y] = open;
            }
        }
        // build walls
        for (int x = 0; x < grid.length; x ++) {
            for (int y = 0; y < grid[x].length; y ++) {
                if (x % 4 == 0 || y % 2 == 0)
                    grid[x][y] = wall;
            }
        }
        // make meaningful representation
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                Cell current = getCell(x, y);
                int gridX = x * 4 + 2;
                int gridY = y * 2 + 1;
                if (current.inPath) {
                    grid[gridX][gridY] = pathChar;
                    if (current.hasBelowNeighbor())
                        if (getCell(x, y + 1).inPath) {
                            grid[gridX][gridY + 1] = pathChar;
                            grid[gridX + 1][gridY + 1] = open;
                            grid[gridX - 1][gridY + 1] = open;
                        } else {
                            grid[gridX][gridY + 1] = open;
                            grid[gridX + 1][gridY + 1] = open;
                            grid[gridX - 1][gridY + 1] = open;
                        }
                    if (current.hasRightNeighbor())
                        if (getCell(x + 1, y).inPath) {
                            grid[gridX + 2][gridY] = pathChar;
                            grid[gridX + 1][gridY] = pathChar;
                            grid[gridX + 3][gridY] = pathChar;
                        } else {
                            grid[gridX + 2][gridY] = open;
                            grid[gridX + 1][gridY] = open;
                            grid[gridX + 3][gridY] = open;
                        }
                } else {
                    grid[gridX][gridY] = open;
                    if (current.hasBelowNeighbor()) {
                        grid[gridX][gridY + 1] = open;
                        grid[gridX + 1][gridY + 1] = open;
                        grid[gridX - 1][gridY + 1] = open;
                    }
                    if (current.hasRightNeighbor()) {
                        grid[gridX + 2][gridY] = open;
                        grid[gridX + 1][gridY] = open;
                        grid[gridX + 3][gridY] = open;
                    }
                }
            }
        }
        System.out.print("Hello1");
    }

    // simply prints the map
    public void draw() {
        System.out.print(this);
    }
    // forms a meaningful representation
    @Override
    public String toString() {
        updateGrid();
        addStartAndEnd();
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                output.append(grid[x][y]);
            }
            output.append("\n");
        }
        System.out.println("");
        return output.toString();
    }

    public int[][] getGrid(){
        return grid;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void addStartAndEnd(){
        System.out.print("hello");
        grid[1][1] = 8;         // Start position
        grid[grid.length-2][grid[0].length-2] = 9; // End position
    }
}