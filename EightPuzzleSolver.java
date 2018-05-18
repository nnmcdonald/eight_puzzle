import java.util.*;

public class EightPuzzleSolver
{
  private PuzzleNode head;
  //private PuzzleNode current;
  private int[][] goalState;
    
  public EightPuzzleSolver(int[][] startState, int[][] endState, int[] blankLocation)
  {
    head = new PuzzleNode(startState, blankLocation);
    goalState = endState;
  }
  
  //Recursively prints the path to solution
  public void printPath(PuzzleNode end)
  {
    if(end.getParent() == null)
    {
      System.out.println("-------------");
      for (int x = 0; x < 3; x++)
      {
        System.out.print("| ");
        for (int y = 0; y < 3; y++)
        {
          System.out.print(end.getState()[x][y] + " | ");
        }
        System.out.println("\n-------------");
      }
    }
    else
    {
      printPath(end.getParent());
      System.out.println("-------------");
      for (int x = 0; x < 3; x++)
      {
        System.out.print("| ");
        for (int y = 0; y < 3; y++)
        {
          System.out.print(end.getState()[x][y] + " | ");
        }
        System.out.println("\n-------------");
      }
    }
  }

  
  public void breadthFirst()
  {
    int nodesExpanded = 0;
    int totalNodes = 1;
    boolean noSolution = true;
    LinkedList<PuzzleNode> queue = new LinkedList<PuzzleNode>();
    queue.add(head);
    while(noSolution)
    {
      PuzzleNode current = queue.remove(); 
      int similarTiles = 0;
      //Compares this state to goal state
      for (int x = 0; x < 3; x++)
      {
        for (int y = 0; y < 3; y++)
        {
           if(current.getState()[x][y] == goalState[x][y])
           {
             similarTiles++;
           }
        }
      }
      //Goal achieved
      if(similarTiles == 9)
      {
        System.out.println("Goal achieved in " + current.getDepth() + " moves");
        System.out.println("Nodes Expanded: " + nodesExpanded);
        System.out.println("Total Nodes: " + totalNodes);
        System.out.println("Goal Path: ");
        printPath(current);
        noSolution = false;
      }
      else{
        //Try moving the blank up
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
          
          blank[0] -= 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0] + 1][blank[1]] = temp;
          PuzzleNode newChild = current.createChild(state, blank);
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //try moving the blank down
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
         
          blank[0] += 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0] - 1][blank[1]] = temp;
          PuzzleNode newChild = current.createChild(state, blank); 
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //Try moving the blank left
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
          
          blank[1] -= 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0]][blank[1] + 1] = temp;
          PuzzleNode newChild = current.createChild(state, blank); 
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //Try moving the blank right
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();

          blank[1] += 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0]][blank[1] - 1] = temp;
          PuzzleNode newChild = current.createChild(state, blank);
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
        //Do nothing
        }
      }
      //A node is expanded during each while loop
      nodesExpanded++;
    }
  }
  
  public void greedyBestFirst()
  {
    boolean noSolution = true;
    Vector<PuzzleNode> queue = new Vector<PuzzleNode>();
    queue.add(head);
    PuzzleNode current = head;
    int nodesExpanded = 0;
    int totalNodes = 1;
    int highValue = 500;
    
    while(noSolution)
    {    
      int index = 0;
      for(int i = 0; i < queue.size(); i++)
      {
        if(highValue > getHValueManhattan(queue.get(i).getState()))
        {
          highValue = getHValueManhattan(queue.get(i).getState());
          index = i;
        }
      }
      current = queue.get(index);
      queue.remove(index);
      int similarTiles = 0;
      //Compares current state to goal state
      for (int x = 0; x < 3; x++)
      {
        for (int y = 0; y < 3; y++)
        {
           if(current.getState()[x][y] == goalState[x][y])
           {
             similarTiles++;
           }
        }
      }
      //Goal achieved
      if(similarTiles == 9)
      {
        System.out.println("Goal achieved in " + current.getDepth() + " moves");
        System.out.println("Nodes Expanded: " + nodesExpanded);
        System.out.println("Total Nodes: " + totalNodes);
        System.out.println("Goal Path: ");
        printPath(current);
        noSolution = false;
      }
      else{
        //Try moving the blank up
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
          
          blank[0] -= 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0] + 1][blank[1]] = temp;
          PuzzleNode newChild = current.createChild(state, blank);
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //try moving the blank down
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
         
          blank[0] += 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0] - 1][blank[1]] = temp;
          PuzzleNode newChild = current.createChild(state, blank); 
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //Try moving the blank left
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
          
          blank[1] -= 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0]][blank[1] + 1] = temp;
          PuzzleNode newChild = current.createChild(state, blank); 
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //Try moving the blank right
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();

          blank[1] += 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0]][blank[1] - 1] = temp;
          PuzzleNode newChild = current.createChild(state, blank);
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
        //Do nothing
        }
      }
      //A node is expanded during each while loop
      nodesExpanded++;
    }
  }
  
  public void misplacedTiles()
  {
    boolean noSolution = true;
    Vector<PuzzleNode> queue = new Vector<PuzzleNode>();
    queue.add(head);
    PuzzleNode current = head;
    int nodesExpanded = 0;
    int totalNodes = 1;
    
    while(noSolution)
    {    
      int index = 0;
      current = queue.get(0);
      for(int i = 0; i < queue.size(); i++)
      {
        if((current.getDepth() + getHValueMT(current.getState())) > (queue.get(i).getDepth() + getHValueMT(queue.get(i).getState())))
        {
          index = i;
        }
      }
      current = queue.get(index);
      queue.remove(index);
      int similarTiles = 0;
      //Compares current state to goal state
      for (int x = 0; x < 3; x++)
      {
        for (int y = 0; y < 3; y++)
        {
           if(current.getState()[x][y] == goalState[x][y])
           {
             similarTiles++;
           }
        }
      }
      //Goal achieved
      if(similarTiles == 9)
      {
        System.out.println("Goal achieved in " + current.getDepth() + " moves");
        System.out.println("Nodes Expanded: " + nodesExpanded);
        System.out.println("Total Nodes: " + totalNodes);
        System.out.println("Goal Path: ");
        printPath(current);
        noSolution = false;
      }
      else{
        //Try moving the blank up
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
          
          blank[0] -= 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0] + 1][blank[1]] = temp;
          PuzzleNode newChild = current.createChild(state, blank);
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //try moving the blank down
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
         
          blank[0] += 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0] - 1][blank[1]] = temp;
          PuzzleNode newChild = current.createChild(state, blank); 
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //Try moving the blank left
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();
          
          blank[1] -= 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0]][blank[1] + 1] = temp;
          PuzzleNode newChild = current.createChild(state, blank); 
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          //Do nothing
        }
        //Try moving the blank right
        try
        {
          int[] blank = current.getBlank();
          int[][] state = current.getState();

          blank[1] += 1;
          int temp =  state[blank[0]][blank[1]];
          state[blank[0]][blank[1]] = 0;
          state[blank[0]][blank[1] - 1] = temp;
          PuzzleNode newChild = current.createChild(state, blank);
          queue.add(newChild);
          totalNodes++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
        //Do nothing
        }
      }
      //A node is expanded during each while loop
      nodesExpanded++;
    }
  }
  
  public void manhattanDistance()
  {
     boolean noSolution = true;
     Vector<PuzzleNode> queue = new Vector<PuzzleNode>();
     queue.add(head);
     PuzzleNode current = head;
     int nodesExpanded = 0;
     int totalNodes = 1;
    
     while(noSolution)
     {    
       int index = 0;
       current = queue.get(0);
       for(int i = 0; i < queue.size(); i++)
      {
        if((current.getDepth() + getHValueManhattan(current.getState())) > (queue.get(i).getDepth() + getHValueManhattan(queue.get(i).getState())))
        {
          index = i;
        }
       }
       current = queue.get(index);
       queue.remove(index);
       int similarTiles = 0;
       //Compares current state to goal state
       for (int x = 0; x < 3; x++)
       {
         for (int y = 0; y < 3; y++)
         {
           if(current.getState()[x][y] == goalState[x][y])
           {
             similarTiles++;
           }
         }
       }
       //Goal achieved
       if(similarTiles == 9)
       {
         System.out.println("Goal achieved in " + current.getDepth() + " moves");
         System.out.println("Nodes Expanded: " + nodesExpanded);
         System.out.println("Total Nodes: " + totalNodes);
         System.out.println("Goal Path: ");
         printPath(current);
         noSolution = false;
       }
       else{
         //Try moving the blank up
         try
         {
           int[] blank = current.getBlank();
           int[][] state = current.getState();
           
           blank[0] -= 1;
           int temp =  state[blank[0]][blank[1]];
           state[blank[0]][blank[1]] = 0;
           state[blank[0] + 1][blank[1]] = temp;
           PuzzleNode newChild = current.createChild(state, blank);
           queue.add(newChild);
           totalNodes++;
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
           //Do nothing
         }
         //try moving the blank down
         try
         {
           int[] blank = current.getBlank();
           int[][] state = current.getState();
         
           blank[0] += 1;
           int temp =  state[blank[0]][blank[1]];
           state[blank[0]][blank[1]] = 0;
           state[blank[0] - 1][blank[1]] = temp;
           PuzzleNode newChild = current.createChild(state, blank); 
           queue.add(newChild);
           totalNodes++;
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
           //Do nothing
         }
         //Try moving the blank left
         try
         {
           int[] blank = current.getBlank();
           int[][] state = current.getState();
          
           blank[1] -= 1;
           int temp =  state[blank[0]][blank[1]];
           state[blank[0]][blank[1]] = 0;
           state[blank[0]][blank[1] + 1] = temp;
           PuzzleNode newChild = current.createChild(state, blank); 
           queue.add(newChild);
           totalNodes++;
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
           //Do nothing
         }
         //Try moving the blank right
         try
         {
           int[] blank = current.getBlank();
           int[][] state = current.getState();

           blank[1] += 1;
           int temp =  state[blank[0]][blank[1]];
           state[blank[0]][blank[1]] = 0;
           state[blank[0]][blank[1] - 1] = temp;
           PuzzleNode newChild = current.createChild(state, blank);
           queue.add(newChild);
           totalNodes++;
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
           //Do nothing
         }
       }
       //A node is expanded during each while loop
       nodesExpanded++;
     }
  }
  
  public int getHValueMT(int[][] current)
  {
    int h_val = 0;
    for (int x = 0; x < 3; x++)
    {
      for (int y = 0; y < 3; y++)
      {
        if(current[x][y] != goalState[x][y])
          h_val++;
      }
    }
    return h_val;
  }
  
   public int getHValueManhattan(int[][] current)
  {
    int h_val = 0;
    for (int x = 0; x < 3; x++)
    {
      for (int y = 0; y < 3; y++)
      {
        for(int i = 0; i < 3; i++)
        {
          for(int j = 0; j < 3; j++)
          {
            if(current[x][y] == goalState[i][j])
            {
              h_val = h_val + Math.abs(x - i) + Math.abs(y-j);
            }
          }
        }
      }
    }
    return h_val;
  }
}
