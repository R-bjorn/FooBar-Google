> <div id="top"></div>

# Prepare the Bunnies' Escape

##  Description

You're awfully close to destroying the LAMBCHOP doomsday device and freeing Commander Lambda's bunny workers, but once they're free of the work duties the bunnies are going to need to escape Lambda's space station via the escape pods as quickly as possible. Unfortunately, the halls of the space station are a maze of corridors and dead ends that will be a deathtrap for the escaping bunnies. Fortunately, Commander Lambda has put you in charge of a remodeling project that will give you the opportunity to make things a little easier for the bunnies. Unfortunately (again), you can't just remove all obstacles between the bunnies and the escape pods - at most you can remove one wall per escape pod path, both to maintain structural integrity of the station and to avoid arousing Commander Lambda's suspicions.

<details><summary>Details about this assignment</summary>

> You have maps of parts of the space station, each starting at a work area exit and ending at the door to an escape pod. The map is represented as a matrix of 0s and 1s, where 0s are passable space and 1s are impassable walls. The door out of the station is at the top left (0,0) and the door into an escape pod is at the bottom right (w-1,h-1).
> 
> Write a function solution(map) that generates the length of the shortest path from the station door to the escape pod, where you are allowed to remove one wall as part of your remodeling plans. The path length is the total number of nodes you pass through, counting both the entrance and exit nodes. The starting and ending positions are always passable (0). The map will always be solvable, though you may or may not need to remove a wall. The height and width of the map can be from 2 to 20. Moves can only be made in cardinal directions; no diagonal moves are allowed.
> 
</details> 

## Test Cases

Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

### Python cases
### Test Case 1

Inputs:

    (int list list) bunny_workers_list = [[0, 1, 1, 0], 
        [0, 0, 0, 1], 
        [1, 1, 0, 0], 
        [1, 1, 1, 0]]

Output:

    7
    
### Test Case 2

Inputs:

    (int list list) bunny_workers_list = [0, 0, 0, 0, 0, 0], 
        [1, 1, 1, 1, 1, 0], 
        [0, 0, 0, 0, 0, 0], 
        [0, 1, 1, 1, 1, 1], 
        [0, 1, 1, 1, 1, 1], 
        [0, 0, 0, 0, 0, 0]])
    
Output:

    11

<a align="center" href="#top">(Back to top)</a>

## Languages

- To provide a Java solution, edit Solution.java
- To provide a Python solution, edit solution.py

## Solution 

```
from collections import deque

directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]

def bfs(row, col, m):
    rows = len(m)
    cols = len(m[0])
    arr = []
    for _ in range(rows):
        arr.append([None] * cols)
    arr[row][col] = 1
    queue = deque()
    queue.append((row, col))

    while queue:
        r, c = queue.popleft()
        for dr, dc in directions:
            nr, nc = (r + dr, c + dc)
            if 0 <= nr < rows and 0 <= nc < cols and arr[nr][nc] is None:
                arr[nr][nc] = arr[r][c] + 1
                if m[nr][nc] != 1:
                    queue.append((nr, nc))
    return arr


def solution(m):
    rows = len(m)
    cols = len(m[0])
    src = bfs(0, 0, m)
    dest = bfs(rows - 1, cols - 1, m)
    res = 20 * 20 + 1
    for i in range(rows):
        for j in range(cols):
            if src[i][j] and dest[i][j]:
                res = min(res, src[i][j] + dest[i][j] - 1)
                if res == rows + cols - 1:
                    return res
    return res
```

<a align="center" href="#top">(Back to top)</a>
