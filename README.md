# Documentation for the Maze Generation project.
## How to use the program
### Initialization
1. When the program starts, a user should enter either *1* or *2* to choose either *Kruskal's* or *Hunt and Kill* algorithms respectively.
```
Welcome to the maze!
Choose desired algorithm for maze generation (enter the number)!
1. Kruskal's algorithm generation
2. Hunt and Kill algorithm generation
```
2. Enter *height* and *width*
```
Well! Time to choose the height for the maze!
Enter any positive integer!
10
Choose the width for the maze
Enter any positive integer!
10
```
**Important note:** since in these maze generation algorithms walls represented as a separate cells (not as strips), maze's width and height are always odd for convenience traversing within a maze. \
3. After that there will be generated maze in the console:
```
🛏 is the start point
📍 is the end point
🐸 is a swamp
🟠 is a passage
🟣 is the cheapest (not the case for BFS) path
🧱 is a wall
🌊 is a lake
💷 is a coin
🧱 🟠 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 
🧱 🟠 🟠 🟠 🟠 🟠 🟠 🟠 🐸 🐸 🧱 
🧱 🧱 🧱 🧱 🧱 🟠 🧱 🟠 🧱 🧱 🧱 
🧱 🟠 🟠 🟠 🐸 🌊 🟠 🟠 🌊 🐸 🧱 
🧱 🌊 🧱 🟠 🧱 🟠 🧱 🐸 🧱 🟠 🧱 
🧱 🟠 🐸 🟠 🧱 🌊 🟠 🐸 🐸 🟠 🧱 
🧱 🟠 🧱 🧱 🧱 🟠 🧱 🟠 🧱 🐸 🧱 
🧱 🟠 🐸 🟠 🟠 🟠 🟠 🌊 🟠 🌊 🧱 
🧱 🐸 🧱 🐸 🧱 🟠 🧱 🟠 🧱 🟠 🧱 
🧱 🐸 🌊 🟠 💷 🟠 🟠 🐸 🐸 🟠 🟠 
🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 
```
**Important note:** different types of cells are costs differently:

🐸 (swamp) costs 5 point \
🟠 (passage) costs 1 point\
🌊 (lake) costs 4 points \
💷 (coin) costs 0 point (free) \
🧱 (wall) impossible to traverse

4. After that a user should enter *x* and *y* coordinates for the start point
```
Choose coordinates for the start!
Enter the x-coordinate within the maze (starting from 0-index)!
Note that x-coordinate is a row number counting from the left top corner!
0
Enter the y-coordinate! (starting from 0-index)!
Note that y-coordinate is a row number counting from the left top corner!
1
```
5. After that a user should enter *x* and *y* coordinates for the end point
```
Choose coordinates for the end!
Enter the x-coordinate within the maze (starting from 0-index)!
Note that x-coordinate is a row number counting from the left top corner!
7
Enter the y-coordinate! (starting from 0-index)!
Note that y-coordinate is a row number counting from the left top corner!
8
```
**Important note:** user should enter coordinates using 0-indexing starting from left top corner \
6. Pick solving algorithm
```
Choose the method you want to apply to find the shortest path from (0, 1) to (7, 8) 
1. DFS
2. BFS
3. A* algorithm
```
6. After that, algorithm either will find the shortest path or will write *Such a path doesn't exist!*
```
Here is your maze!
🛏 is the start point
📍 is the end point
🐸 is a swamp
🟠 is a passage
🟣 is the cheapest (not the case for BFS) path
🧱 is a wall
🌊 is a lake
💷 is a coin
🧱 🛏 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 
🧱 🟣 🟣 🟣 🟣 🟣 🟠 🟠 🐸 🐸 🧱 
🧱 🧱 🧱 🧱 🧱 🟣 🧱 🟠 🧱 🧱 🧱 
🧱 🟠 🟠 🟠 🐸 🟣 🟠 🟠 🌊 🐸 🧱 
🧱 🌊 🧱 🟠 🧱 🟣 🧱 🐸 🧱 🟠 🧱 
🧱 🟠 🐸 🟠 🧱 🟣 🟠 🐸 🐸 🟠 🧱 
🧱 🟠 🧱 🧱 🧱 🟣 🧱 🟠 🧱 🐸 🧱 
🧱 🟠 🐸 🟠 🟠 🟣 🟣 🟣 📍 🌊 🧱 
🧱 🐸 🧱 🐸 🧱 🟠 🧱 🟠 🧱 🟠 🧱 
🧱 🐸 🌊 🟠 💷 🟠 🟠 🐸 🐸 🟠 🟠 
🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 🧱 
```


## Maze generation algorithms

### Kruskal's algorithm
One of the algorithm generating a random maze is Kruskal's algorithm:
it produces minimum spanning tree (no loops inside maze), but in these implementation there could be loops inside.

**Key steps:**
1. Iterating through all possible cells creating passages on odd coordinates with corresponding cells sets and edges.
```java
for (int i = 0; i < height; i++) {
    for (int j = 0; j < width; j++) {
        boolean add = !(i % 2 != 0 && j % 2 != 0);
        maze[i][j] = add ? new Cell(i, j, Cell.Type.WALL) : new Cell(i, j, Cell.Type.PASSAGE);

        if (!add) {
            List<int[]> newArrayList = new ArrayList<>();
            newArrayList.add(new int[] {i, j});
            sets.add(newArrayList);
        }
        if (i != height - 2 && !add) {
            edges.add(new int[] {i + 1, j});
        }
        if (j != width - 2 && !add) {
            edges.add(new int[] {i, j + 1});
        }
    }
}
```
2. Select a random edge and join the cells it connects if they are not already connected by a path.
```java
while (!edges.isEmpty()) {
    int index = random.nextInt(edges.size());
    int[] removed = edges.remove(index);
    ...
    // Find the indices of the sets containing cell1 and cell2
    int index1 = indexOfSet(sets, cell1);
    int index2 = indexOfSet(sets, cell2);
    // If they are different, merge them
}
```
3. Remove random walls in the maze to create loops intentionally. 
4. Generate random specific cells: lakes, swamps, coins.

*Source: https://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm*

### Hunt and Kill algorithm
1. Choose a starting location.
2. Perform a random walk, carving passages to unvisited neighbors, until the current cell has no unvisited neighbors.
```java
List<int[]> currentNeighbors = neighbors(maze, currentCell[zeroCoord], currentCell[oneCoord], true);
```
3. Enter “hunt” mode, where algorithm scan the grid looking for an unvisited cell that is adjacent to a visited cell. If found, carve a passage between the two and let the formerly unvisited cell be the new starting location.
```java
int[][] temp = findCoord(maze);
    if (temp != null && temp[0] != null) {
    currentCell = temp[0];

    // Mark current cell as a passage
    maze[currentCell[zeroCoord]][currentCell[oneCoord]] = new Cell(
        currentCell[zeroCoord], currentCell[oneCoord], Cell.Type.PASSAGE);
    // Calculate the midpoint (avgZero, avgOne) between currentCell and its new neighbor (temp[1]).
    int avgZero = currentCell[zeroCoord] + (temp[oneCoord][zeroCoord] - currentCell[zeroCoord]) / 2;
    int avgOne = currentCell[oneCoord] + (temp[oneCoord][oneCoord] - currentCell[oneCoord]) / 2;
   // Marks the midpoint as a passage.
    maze[avgZero][avgOne] = new Cell(avgZero, avgOne, Cell.Type.PASSAGE);
}
```
4. Repeat steps 2 and 3 until the hunt mode scans the entire grid and finds no unvisited cells.
5. Generate random specific cells: lakes, swamps, coins.


*Source: https://weblog.jamisbuck.org/2011/1/24/maze-generation-hunt-and-kill-algorithm*

## Shortest path searching algorithm
### A* algorithm
A* algorithm uses heuristic to pick the optimal cell on each iteration calculating the traveled and left distance. Priority queue was used to achieve the optimal cell picking on each iteration.
```
while(!priorityTraverse.isEmpty()) {
    current = priorityTraverse.poll();
    // Traverse through the neighbors
    for (int[] nextStep : DIRECTIONS) {
        int newX = current.x + nextStep[xStep];
        int newY = current.y + nextStep[yStep];
        Node neighborNode;
        if (isValid(newX, newY)) {
            neighborNode = new Node(newX, newY,
                current.g + maze.grid()[newX][newY].cellCost(), heuristic(newX, newY), current);
            destinations[newX][newY] = neighborNode;
            priorityTraverse.add(neighborNode);
        }
    }
}
// Heuristic calculation
private int heuristic(int x, int y) {
    return Math.abs(x - end.row()) + Math.abs(y - end.col());
}
```
### Depth-first Search
DFS uses recursion and backtracking to traverse through the whole maze. \
Since usual DFS doesn't work on weighted graphs, here is implemented its "modernization" for finding the shortest path: brute force approach but using DFS concept:
```java
// If the algorithm found some path, it continue execution
if (start.row() == end.row() && start.col() == end.col()) {
    if (length < currentLength) {
        shortestPath = new ArrayList<>(currentPath);
        currentLength = length;
    }
}
currentPath.removeLast();
visited[start.row()][start.col()] = false;
```
Thus, such an approach has poor performance on big mazes (e.g. 20x20 size), but it always finds the optimal solution.

### BFS
BFS uses alternate visiting of neighbors of the cell that was pulled from the queue.
BFS also doesn't work on weighted graphs, so here's default BFS implementation which finds the shortest path by amount of steps, not by it cost.
```java
while (!queueTraverse.isEmpty()) {
    Node current = queueTraverse.poll();
    ...
    Node neighborNode = new Node(newX, newY, 0, 0, current);
    queueTraverse.add(neighborNode);
}
```

