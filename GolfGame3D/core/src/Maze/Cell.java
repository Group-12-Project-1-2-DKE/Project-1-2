package Maze;

import java.util.ArrayList;

/**
 * Object that represent a part of the maze. Holds information about its neighbors and its position.
 */
public class Cell {
    int x, y;                                       // coordinates of the cell
    ArrayList<Cell> neighbors = new ArrayList<>();  // List of the neighbors of the cell
    boolean open = true;                            // if true, has not been used yet in generation

    /**
     * Constructor method of the cell object.
     * @param x X position of the cell.
     * @param y Y position of the cell.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * add a neighbor to this cell, and this cell as a neighbor to the other.
     * @param other the neighbor cell.
     */
    public void addNeighbor(Cell other) {
        if (!this.neighbors.contains(other)) { // to avoid duplicates in the neighbor list.
            this.neighbors.add(other);
        }
        if (!other.neighbors.contains(this)) { // to avoid duplicates
            other.neighbors.add(this);
        }
    }

    /**
     * Check if the cell has a bellow neighbor or not.
     * @return true if the cell has a neighbor bellow her.
     */
    public boolean hasBelowNeighbor() {
        return this.neighbors.contains(new Cell(this.x, this.y + 1));
    }

    /**
     * Check if the cell has a right neighbor or not.
     * @return true if the cell has a neighbor at her right.
     */
    public boolean hasRightNeighbor() {
        return this.neighbors.contains(new Cell(this.x + 1, this.y));
    }

    /**
     * Translate the cell into a string
     * @return a string containing information about the cell.
     */
    @Override
    public String toString() {
        return String.format("Cell(%s, %s)", x, y);
    }

    /**
     * Check if the two cells are the same or not.
     * @param other Cell that we want to compare with the actual cell.
     * @return true if the cells are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Cell)) return false;
        Cell otherCell = (Cell) other;
        return (this.x == otherCell.x && this.y == otherCell.y);
    }
}