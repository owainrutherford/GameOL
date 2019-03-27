

import processing.core.*;

public class GameOfLife extends PApplet{
	int sizeOfCell = 10;
	// probability between 0 and 1
	double initialProbability = 0.1;
	int timeStep = 200;
	int lastTime = 0;
	int aliveColor = color(255,255,255);
	int deadColor = color(0,0,0);
	int[][] golGridBuffer;
	int[][] golGrid;
	int[][] countGrid;
	boolean pause = false;

	public static void main(String[] args) {
		PApplet.main("GameOfLife");
	}



	public void settings() {
		size(1000,1000);
	}

	public void setup() {
		background(200,200,200);
		// create gol arrays with dimensions equal to the length/sizeOfCell
		golGrid = new int[width/sizeOfCell][height/sizeOfCell];
		golGridBuffer = new int[width/sizeOfCell][height/sizeOfCell];





		// starting values for the grid will be randomly generated

		for(int i=0; i<width/sizeOfCell; i++) {
			for(int j=0;j<height/sizeOfCell;j++) {
				double isAlive = random(1);
				if (isAlive > initialProbability) {
					isAlive = 0;
				} else {
					isAlive = 1;
				}
				golGrid[i][j] = (int) isAlive;
			}
		}
		background(0);
	}


	public void draw() {
		for(int i=0; i< width/sizeOfCell; i++) {
			for(int j=0; j<height/sizeOfCell; j++) {
				if (golGrid[i][j] == 1) {
					fill(aliveColor);
				} else {
					fill(deadColor);
				}
				// here i color the cells based on their coordinates in the Applet window
				rect(i*sizeOfCell,j*sizeOfCell,sizeOfCell,sizeOfCell);
			}
		}
		// repaint canvas when the elapsed time is greater than timeStep
		if (millis() - lastTime > timeStep) {
			newPaint();
			lastTime = millis();
		}
	}

	public void newPaint() {
		for(int i=0; i< width/sizeOfCell; i++) {
			for(int j=0; j<height/sizeOfCell; j++) {
				golGridBuffer[i][j] = golGrid[i][j];
			}
		}
		countNeighbours();
		golRules();



	}

	// count the number of alive cells in the grid.
    public void countNeighbours(){
// produce a grid where the integer value is the number of alive neighbours for that position.
        int[][] count = new int[width/sizeOfCell][height/sizeOfCell];
        for (int i=0; i < width/sizeOfCell; i++){
            for (int j=0; j < height/sizeOfCell; j++){
// if statement which ensures no alive cells on edge of grid (for infinite game of life).
                if (i != 0 && i != width/sizeOfCell-1 && j != 0 && j != height/sizeOfCell-1){
// iterates through 9 cells. (8 neighbours plus the cell itself).
                    for(int m = i - 1; m <= i + 1; m++){
                        for (int n = j - 1; n <= j + 1; n++){
// counts the alive NEIGHBOUR cells and NOT counting the cell[i][j] itself.
                            if (golGridBuffer[m][n]==1 && !(m == i && n == j)){
                                count[i][j] = count[i][j] + 1;
                            }
                        }
                    }
                } else{
                    count[i][j] = 0;
                }
            }
        }
        countGrid = count;
    }

    public void  golRules(){
// moves the game forward one generation, golGrid[i][j] is alive or
// dead based on the integer value of count[i][j]
        for (int i = 0; i < width/sizeOfCell; i++){
            for (int j = 0; j < height/sizeOfCell; j++){
                // for underpopulation
                if (countGrid[i][j] < 2 && golGridBuffer[i][j]==1){
                    golGrid[i][j] = 0;
                // for overpopulation.
            } else if(countGrid[i][j] > 3 && golGridBuffer[i][j]==1){
                    golGrid[i][j] = 0;
                // creation of life
            } else if(countGrid[i][j] == 3 && !(golGridBuffer[i][j]==1) ){
                    golGrid[i][j] = 1;
            } else if(countGrid[i][j] < 2 && !(golGridBuffer[i][j]==1) ){
                golGrid[i][j] = 0;
                // survival
            }   else if(  golGridBuffer[i][j]==1 && (countGrid[i][j] == 2 || countGrid[i][j] ==3)  ){
                golGrid[i][j] = 1;
            }
            }
        }
    }



}
