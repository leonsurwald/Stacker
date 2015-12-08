/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import grid.Grid;
import java.awt.Graphics;

/**
 *
 * @author leonsurwald
 */
public final class StackData {

    public StackData(int rows, int columns, CellDataProviderIntf cellData) {
        gameGrid = new Block[rows][columns];
        this.cellData = cellData;
    }

    public void draw(Graphics graphics) {
        for (int row = 0; row < getGameGrid().length; row++) {
            for (int column = 0; column < getGameGrid()[row].length; column++) {
                if (getGameGrid()[row][column] != null) {
                    getGameGrid()[row][column].draw(graphics);
                }
            }
        }
    }

    public void stopMovement() {

        if (getCurrentRow() <= 0) {
            setDirection(getDirection().STOP);
        }

        eliminateBlocks();

        if (getCurrentRow() > 0) {
            setCurrentRow(getCurrentRow() - 1);

            int blocksToAdd = 3;
            
            if (getCurrentRow() <= 11) {
                blocksToAdd =  2;
            } else if (getCurrentRow() <= 5) {
                blocksToAdd =  1;
            }

            if (currentRow < gameGrid.length - 1){
                blocksToAdd = Math.min(countBlocks(currentRow + 1), blocksToAdd);            
            }
            addBlocksToRow(getCurrentRow(), blocksToAdd);
        }
    }

    public int countBlocks(int row){
        int counter = 0;
        for (int column = 0; column < gameGrid[row].length; column++) {
            if (gameGrid[row][column] != null) {
                counter++;
            }
        }
        return counter;
    }
    
    
    public void speed() {

    }

    public void eliminateBlocks() {
        if (getCurrentRow() < getGameGrid().length - 1) {
            for (int column = 0; column < getGameGrid()[getCurrentRow()].length; column++) {

                if ((getGameGrid()[getCurrentRow()][column] == null) && (getGameGrid()[getCurrentRow() + 1][column] == null)) {

                }

                if ((getGameGrid()[getCurrentRow()][column] != null) && (getGameGrid()[getCurrentRow() + 1][column] == null)) {
                    getGameGrid()[getCurrentRow()][column] = null;
                }

            }
        }
//        //check if blocks in row below have same x values
//        //if not delete blocks with new x values in current row
//        //remaining number of blocks is the new value of numberToAdd
    }

    public void addBlocksToRow(int row, int numberToAdd) {
        //clean out any existing blocks in that currentRow
        // start at position 0, add numberToAdd blocks, moving to the right each time
        // make sure you don't go outside of structure

        int addCounter = 0;
        if (getGameGrid() != null) {
            for (int column = 0; column < getGameGrid()[row].length; column++) {
                //clear the cell
                getGameGrid()[row][column] = null;

                //add a block if required
                if (addCounter < numberToAdd) {
                    addCounter++;
                    getGameGrid()[row][column] = new Block(column, row, getCellData());
                }
            }
        }

    }

    public void move() {

        // check if you're at the end of the currentRow
        //if yes reverse the direction
        if (getDirection() == Direction.RIGHT) {
            //if there is a block in the rightmost column, then we need to reverse direction
            if (getGameGrid()[getCurrentRow()][getGameGrid()[getCurrentRow()].length - 1] != null) {
                setDirection(Direction.LEFT);
            }
        } else { // direct MUST be left
            //if there is a block in the lefttmost column, then we need to reverse direction
            if (getGameGrid()[getCurrentRow()][0] != null) {
                setDirection(Direction.RIGHT);

            }
        }

        //if moving to the right increase the column number for all the blocks by 1
        if (getDirection() == Direction.RIGHT) {
            for (int column = getGameGrid()[getCurrentRow()].length - 2; column >= 0; column--) {
//                if there is a block, then move it to the RIGHT
                if (getGameGrid()[getCurrentRow()][column] != null) {
                    getGameGrid()[getCurrentRow()][column + 1] = getGameGrid()[getCurrentRow()][column];
                    getGameGrid()[getCurrentRow()][column] = null;
                    getGameGrid()[getCurrentRow()][column + 1].setX(column + 1);
                }
            }
        } else if (getDirection() == Direction.STOP) {

        } else if (getDirection() == Direction.LEFT) {
            //if moving to the left decrease the column number for all the blocks by 1
            for (int column = 1; column < getGameGrid()[getCurrentRow()].length; column++) {
//
                if (getGameGrid()[getCurrentRow()][column] != null) {
                    getGameGrid()[getCurrentRow()][column - 1] = getGameGrid()[getCurrentRow()][column];
                    getGameGrid()[getCurrentRow()][column] = null;
                    getGameGrid()[getCurrentRow()][column - 1].setX(column - 1);
                }
            }
        }

        // make it move automagically
        // make different speeds )(later)
        // set currentRow logic (stop, elimnate, new currentRow)
        // differetn block counts for (later)
    }

//<editor-fold defaultstate="collapsed" desc="Properties">
    private CellDataProviderIntf cellData;
    private Speed speed = Speed.SLOW;
    private int currentRow = 14;                      //how to ask the grid for the row number? game grid? why isn't it grid.getRows()
    private Direction direction = Direction.RIGHT;
    private GameState state;

    private Block[][] gameGrid;

    /**
     * @return the cellData
     */
    public CellDataProviderIntf getCellData() {
        return cellData;
    }

    /**
     * @param cellData the cellData to set
     */
    public void setCellData(CellDataProviderIntf cellData) {
        this.cellData = cellData;
    }

    /**
     * @return the speed
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * @return the currentRow
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * @param currentRow the currentRow to set
     */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the state
     */
    public GameState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * @return the gameGrid
     */
    public Block[][] getGameGrid() {
        return gameGrid;
    }

    /**
     * @param gameGrid the gameGrid to set
     */
    public void setGameGrid(Block[][] gameGrid) {
        this.gameGrid = gameGrid;
    }
//</editor-fold>
}
