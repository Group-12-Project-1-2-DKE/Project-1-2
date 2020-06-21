package Maze;

import java.util.ArrayList;
import java.util.Random;

/**
 * Object that generates a maze.
 * Original code is taken from the source below :
 *      https://stackoverflow.com/questions/21815839/simple-java-2d-array-maze-sample
 */
public class MazeGenerator {
    private int dimX;                       // dimension x of maze
    private int dimY;                       // dimension y of maze
    private int[][] maze;                   // 2D array of integer
    private Cell[][] cells;                 // 2D array of Cells
    private Random random = new Random();   // The random object

    /**
     * constructor of the mazeGenerator object
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
        updateGrid();
        addStartAndEnd();
    }

    /**
     * Initialize the maze by creating an array of certain dimensions and complete it with cells.
     */
    private void initializeMaze() {
        // create cells array and filled it with cells
        cells = new Cell[dimX][dimY];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                cells[x][y] = new Cell(x, y); // Create the cell not as a wall
            }
        }
    }

    /**
     * THis method generate the maze from a start cell.
     * @param startAt The call from which the maze will start to be generated.
     */
    private void generateMaze(Cell startAt) {
        // don't start at cells that do not exist
        if (startAt == null) {
            return;
        }

        startAt.open = false; // Mark the cell as opened.
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

    /**
     * Method that translate the array of cells into an array of integer.
     */
    public void updateGrid() {
        int open = 0;   // Integer that represent open path.
        int wall = 1;   // Integer that represent a wall
        //int path = 2;
        // fill the array with only open path.
        for (int x = 0; x < maze.length; x ++) {
            for (int y = 0; y < maze[x].length; y ++) {
                maze[x][y] = open;
            }
        }
        // build the outside walls.
        for (int x = 0; x < maze.length; x ++) {
            for (int y = 0; y < maze[x].length; y ++) {
                if (x % 4 == 0 || y % 2 == 0)
                    maze[x][y] = wall;
            }
        }
        // Translate the array of cell into an array of integers.
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {
                Cell current = getCell(x, y);
                int gridX = x * 4 + 2;
                int gridY = y * 2 + 1;
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

    /**
     * Translate the maze into a String.
     * @return a String that contains the detailed array.
     */
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

    /**
     * Getter method.
     * @return the array of integer that describe the maze.
     */
    public int[][] getGrid(){
        return maze;
    }

    /**
     * Getter method
     * @return the array of Cell that contains information about the maze.
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Add the start and the end position to the array of integer.
     */
    public void addStartAndEnd(){
        maze[1][1] = 8;                             // Start position
        maze[maze.length-2][maze[0].length-2] = 9; // End position
    }

    /**
     * Getter method. Used to get a Cell at x, y; returns if that cell does not exist.
     * @param x The x position of the cell in the array.
     * @param y The y position of the cell into the array.
     * @return The researched cell.
     */
    public Cell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
            return null;
        }
    }
}