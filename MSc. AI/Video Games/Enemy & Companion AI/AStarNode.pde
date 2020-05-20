// Taken from material provided in tutorial

public class AStarNode implements Comparable<AStarNode> {
  // The cost of shortest path found so far to here, and the estimate to the goal
  private int cost = 0, estimate = 0;
  // Coordinates in original map
  private int row, col ;
  // During A* search the node from which we arrived here
  private AStarNode prevNode ;
  // Book keeping for A*. Visited could be determined by membership of open list, but this is cheaper.
  private boolean visited, closed = false ;
  
  // An exciting constructor
  public AStarNode(int row, int col) {
    this.row = row ;
    this.col = col ;
  }
  
  // reset to avoid reconstruction
  public void reset() {
    cost = 0 ;
    estimate = 0 ;
    prevNode = null ;
    visited = false ;
    closed = false ;
  }  
  
  // Getters and setters
  public void setCost(int inCost) {cost = inCost ;}
  public int getCost() {return cost ;}
  public int getTotalCost() {return cost + estimate ;}

  public int getRow() {return row ;}
  public int getCol() {return col ;}

  public void close() {closed = true ;}
  public boolean isClosed() {return closed ;}
  
  public void setVisited() {visited = true ;}
  public boolean isVisited() {return visited ;}
  
  public boolean hasCoords(int inRow, int inCol) {
    return row == inRow && col == inCol ; 
  }
  
  // For building the path
  public void setPrevNode(AStarNode node) {prevNode = node ;}
  public AStarNode getPrevNode() {return prevNode ;}
  
  // Calculate estimate to goal.
  public void makeEstimate(int goalRow, int goalCol) {
    int rowDiff = goalRow - row;
    int colDiff = goalCol - col;
    //estimate = (int) Math.sqrt((rowDiff ^ 2) + (colDiff ^ 2)); 
    estimate = abs(rowDiff) + abs(colDiff);
  }
  
  // implements comparable interface for sorting the open list
  int compareTo(AStarNode inNode) {
    return cost + estimate - inNode.getTotalCost() ; 
  }
}
