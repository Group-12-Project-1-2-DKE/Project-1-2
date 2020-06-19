package Maze;

import java.util.ArrayList;
import java.util.Random;

// https://stackoverflow.com/questions/21815839/simple-java-2d-array-maze-sample

public class MazeGenerator {
    private int dimX;                       // dimension x of maze
    private int dimY;                       // dimension y of maze
    private int[][] maze;                   // 2D array of integer
    private Cell[][] cells;                 // 2D array of Cells
    private Random random = new Random();   // The random object

    /**
     * ctor of the mazeGenerator object
     * @param xDimension The x dimension of the array of the maze.
     * @param yDimension The y dimension of the array of the maze.
     */
    public MazeGenerator(int xDimension, int yDimension) {
        dimX = xDimension;
        dimY = yDimension;
        // Create a maze of the dimension x*4+1 and y*2+1.
        maze = new int[xDimension * 4 + 1][yDimension * 2 + 1];
        initializeMaze();
        generateMaze(cells[0][0]);
    }

    private void initializeMaze() {
        // create cells array and filled it with cells
        cells = new Cell[dimX][dimY];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                cells[x][y] = new Cell(x, y); // Create the cell not as a wall
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

    // used to get a Cell at x, y; returns null out of bounds
    public Cell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
            return null;
        }
    }
}