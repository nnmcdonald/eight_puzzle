import java.util.*;

public class EightPuzzle
{
  public static void main(String[] args)
  {
    int[][] puzzleIndices = new int[3][3];
    int index = 1;
    int[][] puzzleStart = new int[3][3];
    int[][] puzzleEnd = new int[3][3];
    int[] tilesEntered = {0,0,0,0,0,0,0,0,0};
    int[] blankLocation = new int[2];
    
    Scanner keyboard = new Scanner(System.in);
    
    //Sets indices
    for (int x = 0; x < 3; x++)
    {
      for (int y = 0; y < 3; y++)
      {
        puzzleIndices[x][y] = index;
        index++;
      }
    }  
    index = 1;
    
    //Displays puzzle with each tile assigned a numeric identifier
    System.out.println("-------------");
    for (int x = 0; x < 3; x++)
    {
      System.out.print("| ");
      for (int y = 0; y < 3; y++)
      {
        System.out.print(puzzleIndices[x][y] + " | ");
      }
      System.out.println("\n-------------");
    }
    
    //Reads in the puzzle start state from user
    System.out.println("Please enter the tile values for the start state " +
                         "corresponding to the position numbers in the figure above. Enter '0' for the blank tile.");
    for (int x = 0; x < 3; x++)
    {
      for (int y = 0; y < 3; y++)
      {
        int temp = 0;
        do{
        System.out.print(index + ". ");
        temp = keyboard.nextInt();
        
        //Check if value is in range 0-8 inclusive and hasn't been entered already 
        if(temp >= 0 && temp <= 8 && tilesEntered[temp] == 1){
          System.out.println("Please only enter each value once.");
        }
        //Stores the location of the blank tile
        else if(temp == 0)
        {
          blankLocation[0] = x;
          blankLocation[1] = y;
        }
        else if(temp > 8 || temp < 0){
          System.out.println("Please only enter values 0-8.");
        }
        }while(temp > 8 || temp < 0 || tilesEntered[temp] == 1);
        
        tilesEntered[temp] = 1;
        index++;
        puzzleStart[x][y] = temp;
        }
     }
    index = 1;
    
    //Reads in the puzzle end state
    System.out.println("Please enter the tile values for the end state " +
                         "corresponding to the position numbers in the figure above. Enter '0' for the blank tile.");
    for (int x = 0; x < 3; x++)
    {
      for (int y = 0; y < 3; y++)
      {
        int temp = 0;
        do{
        System.out.print(index + ". ");
        temp = keyboard.nextInt();
        //Check if value is in range 0-8 inclusive and hasn't been entered already 
        if(temp >= 0 && temp <= 8 && tilesEntered[temp] == 0){
          System.out.println("Please only enter each value once.");
        }
        else if(temp > 8 || temp < 0){
          System.out.println("Please only enter values 0-8.");
        }
        }while(temp > 8 || temp < 0 || tilesEntered[temp] == 0);
        
        tilesEntered[temp] = 0;
        index++;
        puzzleEnd[x][y] = temp;
        }
     }
    index = 1;
    keyboard.close();
    
    EightPuzzleSolver theSolution = new EightPuzzleSolver(puzzleStart, puzzleEnd, blankLocation);
    theSolution.breadthFirst();
    theSolution.greedyBestFirst();
    theSolution.misplacedTiles();
    theSolution.manhattanDistance();
  }
}
