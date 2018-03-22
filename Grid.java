import java.awt.Point;
import java.util.Queue;


public class Grid {
	static final int NORTH = 0;
	static final int EAST = 1;
	static final int SOUTH = 2;
	static final int WEST = 3;

	static final int GRID_Y = 7;
	static final int GRID_X = 12;

	static int playerX = 1;
	static int playerY = 10;
	static int goalX = 5;
	static int goalY = 10;
	static int directionFacing = EAST;
	static Queue<Point> myQueue = new Queue<Point>();
	int [][] grid = new int [GRID_Y][GRID_X];

	
	public void createGrid(){
		for(int i = 0; i < GRID_Y; i++ ){
			for(int j= 0; j < GRID_X; j++){
				if(i == 0 || j == 0){
					grid[i][j] = 99;
				}
				else if(i == (GRID_Y-1) || j == (GRID_X-1))
					grid[i][j] = 99;

				else
					grid[i][j] = -1;
			}
		}
		grid[goalX][goalY] = 0;
		Point pt = new Point(goalX,goalY);
		createObstacles();

		myQueue.push(pt);
	}
	
	public void createObstacles(){
		grid[5][7] = 99;
		grid[4][7] = 99;
		grid[3][7] = 99;
		grid[1][7] = 99;
		grid[1][5] = 99;
		grid[2][5] = 99;
		grid[3][5] = 99;
		grid[4][5] = 99;
		grid[4][2] = 99;

	}
	
	public void waveprop(int[][] map){
		Point currPoint;
		int pointValue;

		while(!myQueue.isEmpty()){
			currPoint = (Point) myQueue.pop();
			pointValue = grid[currPoint.x][currPoint.y];
			
			for(int i = goalX; i > 0; i--){
				for(int j = goalY; j > 0;j--){
					if(grid[i][j] == pointValue){
						if(grid[i][j+1] == -1){
							grid[i][j+1] = grid[i][j]+1;
							myQueue.push(new Point(i,j+1));
						}
						if(grid[i][j-1] == -1){
							grid[i][j-1] = grid[i][j]+1;
							myQueue.push(new Point(i,j-1));
						}
						if(grid[i+1][j] == -1){
							grid[i+1][j] = grid[i][j]+1;
							myQueue.push(new Point(i+1,j));
						}
						if(grid[i-1][j] == -1){
							grid[i-1][j] = grid[i][j]+1;
							myQueue.push(new Point(i-1,j));
						}
					}
				}
			}
		}
	}
	
	
	//will check if we have reach the goal yet
	public boolean checkGoalState(int[][]map, int currx, int curry){
		if(map[currx][curry] == 0)
			return true;
		else 
			return false;
		
	}
	
	public int[][] getGrid(){
		return grid;
	}
	
	public Queue<Point> getQueue(){
		return myQueue;
	}
	
	public void pushToQueue(Point p){
		myQueue.push(p);
	}
	
	public int getPathLength(int x, int y){
		return grid[x][y];
	}
	
}
