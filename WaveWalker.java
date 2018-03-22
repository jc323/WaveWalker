
import java.awt.Point;
import java.util.Stack;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;


/*
 * 
 * Still need to clean
 * 
 * McNally Approved
 */
public class WaveWalker {

	static final int NORTH = 0;
	static final int EAST = 1;
	static final int SOUTH = 2;
	static final int WEST = 3;

	static final int GRID_X = 12;
	static final int GRID_Y = 7;
	
	static int playerX = 1;
	static int playerY = 10;
	static int goalX = 5;
	static int goalY = 10;

	static int directionFacing = EAST;

	//Updates the knowledge base of the robot when it turns a direction
	static int updateDirection(int currentDirection, int update) {
		if (update == 0) {
			return currentDirection;
		}
		else if (update == 1) {
			currentDirection += 1;
		}
		else if (update == 2) {
			currentDirection -= 1;
			if (currentDirection < 0)
				currentDirection = 3;
		}
		else if (update == 3) {
			currentDirection += 2;
		}
		return currentDirection % 4;
	}

	//Ensures that the robot never turns out of bounds and into a wall
	static boolean checkValidMove(int[][] grid, int x, int y, int direction) { //direction array { 0, 90, -90, 180 };
		int value = grid[y][x];
		if (direction == 0) 
			x = x - 1;
		else if (direction == 1) 
			y = y + 1;
		else if (direction == 2) 
			x = x + 1;
		else if (direction == 3) 
			y = y - 1;
		if (grid[y][x] != (value-1)) 
			return false;

		else {
			playerX = x;
			playerY = y;
			return true;
		}
	}

	public static void main(String[] args) {
		Grid myGrid = new Grid();
		Point pt = new Point(goalX, goalY);


		myGrid.createGrid();
		myGrid.waveprop(myGrid.getGrid());
		myGrid.pushToQueue(pt);


		System.out.println("Hello Master,\nJustin");
		System.out.println("Let me run \nAssignment 2 \nfor you");
		System.out.println("Press button to begin");
		Button.waitForAnyPress();

		DifferentialPilot bot = new DifferentialPilot(2.2f, 4.67f,
				Motor.A, Motor.C);

		int[] directions = new int[] { 0, 90, -90, 180 };
		Stack<Integer> moveList = new Stack<Integer>();

		bot.setTravelSpeed(3);
		bot.setRotateSpeed(60);
		int pathLength = myGrid.getPathLength(playerX, playerY);

		//can reach goal do it

		System.out.println(pathLength);


		if(pathLength != -1){
			for (int i = 0; i < pathLength; i++) { 
				int randDir = (int) (Math.random() * (directions.length));
				int tempDir = directionFacing;
				int turn = directions[randDir]; 
				directionFacing = updateDirection(directionFacing, randDir);
				if (checkValidMove(myGrid.getGrid(), playerX, playerY, directionFacing)) {
					bot.rotate(turn);
					bot.travel(9.2);
					if (i > 0)
						moveList.push(turn);
				} 
				else {
					directionFacing = tempDir;
					if (i > 0)
						i -= 1;
					else
						i = -1;
				}
			}
			System.out.println("Objective complete");
			Sound.beep();

			Button.waitForAnyPress();
		}


		else{
			System.out.println("This goal is\nunreachable\n sir.");
			Sound.beep();

			Button.waitForAnyPress();
		}

	}
}