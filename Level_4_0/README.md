<div id="top"></div>

# Escape Pods

##  Description

You've blown up the LAMBCHOP doomsday device and relieved the bunnies of their work duries -- and now you need to escape from the space station as quickly and as orderly as possible! The bunnies have all gathered in various locations throughout the station, and need to make their way towards the seemingly endless amount of escape pods positioned in other parts of the station. You need to get the numerous bunnies through the various rooms to the escape pods. Unfortunately, the corridors between the rooms can only fit so many bunnies at a time. What's more, many of the corridors were resized to accommodate the LAMBCHOP, so they vary in how many bunnies can move through them at a time.

Given the starting room numbers of the groups of bunnies, the room numbers of the escape pods, and how many bunnies can fit through at a time in each direction of every corridor in between, figure out how many bunnies can safely make it to the escape pods at a time at peak.

<details><summary>Details about this assignment</summary>

> Write a function solution(entrances, exits, path) that takes an array of integers denoting where the groups of gathered bunnies are, an array of integers denoting where the escape pods are located, and an array of an array of integers of the corridors, returning the total number of bunnies that can get through at each time step as an int. The entrances and exits are disjoint and thus will never overlap. The path element path[A][B] = C describes that the corridor going from A to B can fit C bunnies at each time step. There are at most 50 rooms connected by the corridors and at most 2000000 bunnies that will fit at a time.
> ```
> For example, if you have:
> entrances = [0, 1]
> exits = [4, 5]
> path = [
> [0, 0, 4, 6, 0, 0], # Room 0: Bunnies
> [0, 0, 5, 2, 0, 0], # Room 1: Bunnies
> [0, 0, 0, 0, 4, 4], # Room 2: Intermediate room
> [0, 0, 0, 0, 6, 6], # Room 3: Intermediate room
> [0, 0, 0, 0, 0, 0], # Room 4: Escape pods
> [0, 0, 0, 0, 0, 0], # Room 5: Escape pods
> ]
> ```
> Then in each time step, the following might happen:<br/>
> ```0 sends 4/4 bunnies to 2 and 6/6 bunnies to 3```<br/>
> ```1 sends 4/5 bunnies to 2 and 2/2 bunnies to 3```<br/>
> ```2 sends 4/4 bunnies to 4 and 4/4 bunnies to 5```<br/>
> ```3 sends 4/6 bunnies to 4 and 4/6 bunnies to 5```<br/>
> 
> So, in total, 16 bunnies could make it to the escape pods at 4 and 5 at each time step. (Note that in this example, room 3 could have sent any variation of 8 bunnies to 4 and 5, such as 2/6 and 6/6, but the final solution remains the same.)

<a href="#top">(Back to top)</a>
</details> 

## Test Cases

Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

### Python cases
### Test Case 1

Inputs:

    solution.solution([0], [3], [[0, 7, 0, 0], [0, 0, 6, 0], [0, 0, 0, 8], [9, 0, 0, 0]])

Output:

    6
    
### Test Case 2

Inputs:

    solution.solution([0, 1], [4, 5], [[0, 0, 4, 6, 0, 0], [0, 0, 5, 2, 0, 0], [0, 0, 0, 0, 4, 4], [0, 0, 0, 0, 6, 6], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]])
    
Output:

    16

<a align="center" href="#top">(Back to top)</a>

## Languages

- To provide a Java solution, edit Solution.java
- To provide a Python solution, edit solution.py

## Approach

It's a max flow problem in graph theory (check wiki for introduction about a graph ) and the rule is simple:

### For a given graph, find the maximum amount of flow from Source to Target.

That is, for a graph as following, we have a source node, a target node and four nodes between them. We can conceive arrows in the graph are pipelines and each of them has different capacity of flow, such as 9 from Source to Node 1 and 3 from Node 1 to Node 2. Moreover, pipelines have direction which means, for example, the flow can only go from Node 1 to Node 2 but it can travel between Node 3 and Node 4.

<img src="https://user-images.githubusercontent.com/81584201/184208208-55409af3-2349-45c1-9011-4bc5abf23d28.jpg" height="300"/>

We have many algorithms to approach and the most intuitive one is greedy algorithm - we always choose the pipeline with max amount of flow in each step and randomly select if all optionals have the same outcome.

For example, there are two pipes from Source with the same amount of flow. We can either select Node 1 or Node 2. If the selection is { S - 1 }, then by greedy algorithm, the path should be { S - 1 - 2 - 3 - T } and the max flow is 7 on this path.

<img src="https://user-images.githubusercontent.com/81584201/184208260-36491ff4-f7e2-4e20-a45e-2438c13d3971.jpg" height="300"/>


Thus, by subtracting 7, the capacity of Node 2 and Node 3 is drained and no longer walkable. Repeatedly processing until no path from Source to Target left as following, we call the graph is disconnected. And the max flow is 16.

<img src="https://user-images.githubusercontent.com/81584201/184208307-54b2d674-b2f8-4e45-b7d4-f37267983de8.jpg" height="300"/>

However, the answer is wrong and the correct max flow is 17. That is, greedy algorithm potentially wastes capacities of pipes so it cannot always guarantee to find the right one.

### Edmonds-Karp Algorithm

Many mathematicians has offered their ideas. We are now going to try Edmonds-Karp algorithm ( check this and this for more information and this for mathematic proofs ), a wildly used and efficient method. There are three procedures to follow:

1. Find the shortest path from Source to Target.
2. Find the max possible flow on the path and subtract that amount on all pipes' capacities.
3. Add opposite Augmenting paths on pipes in the path.

Repeatedly processing until the graph is disconnected, then we can find the right answer. For example, we choose { S - 2 - 3 - T } as our first path and the flow is 7. Then, we subtract all pipes by 7 and **add opposite augmenting paths with 7 on all pipes we walked **( blue arrows ).

<img src="https://user-images.githubusercontent.com/81584201/184208353-6a9c0b3e-83af-4608-ae2c-efeaa9e31d30.jpg" height="300"/>

The main difference to greedy algorithm is augmenting paths are walkable, which means the path { S - 1 - 3 - 2 - 4 - T } is feasible.

### Breadth-first Search

Therefore, we need to define the shortest path in every loop of Edmonds-Karp algorithm. Also there are many ways to achieve and we are going to apply breadth-first search ( check this and this for more information).

Breadth-first search is a violent algorithm to scan whole graph but in this case, we only need to find a short path from Source to Target. It has the following processes:

1. Select a node and put it in Position list.
2. Find all neighbors around this node and put them into Output list.
3. Record all possible combinations of the node in Position and nodes in Output.
4. Discard and replace nodes from Output to Position.
5. Select one node in Position. If the node hasn't be chosen before, keep and find all its neighbors; otherwise, discard it.
6. If neighbor nodes haven't be chosen before, keep and put them in Output; otherwise, discard them.
7. Repeat step 3 to step 6 until only Target present in Output.

For example, using the graph above, we first choose node Source and it has two neighbors Node 1 and Node 2. So Position list is Source and Output list is { 1 2 } and the total combinations are { S - 1 } and { S - 2 }.

Next, Replace { 1 2 } into Position list. We now can either select Node 1 or Node 2 as a new star point. Here, we choose Node 1 and thus, the Output are { 2 3 }. The total combinations of nodes are therefore { S - 1 }, { S - 2 }, { 1 - 2 }, { 1 - 3 }.

For Node 2, its neighbors are { 3 4 }; however, Node 3 had been selected by Node 1 so we abandon it and only Node 4 left. The total combinations are { S - 1 }, { S - 2 }, { 1 - 2 }, { 1 - 3 }, { 2 - 4 }.

When all nodes in Position were selected, we can replace { 2 3 } and { 3 4 } into Position. But Node 2 was chosen and Node 3 are double so we truncate them and leave only { 3 4 }. In the end, Node 3 and Node 4 have the same neighbor Target and thus stopping the algorithm.

<img src="https://user-images.githubusercontent.com/81584201/184208400-1b78ad18-2b40-498e-a25f-c3c555956baf.jpg" height="500"/>

As above, the shortest path is { S - 2 - 4 - T }. Additionally, it's easy to observe that these paths are not unique and in turn, can be multiple. But it doesn't matter how we choose and won't affect the final result.

### Expanded Matrix, Super Source and Super Target

If we have a graph with multiple Source or Target nodes, then it might be complicated for coding. One way to solve is adding a Super Source and Super Target by which we always keep a one-Source-and-one-Target graph. It will look like an example as following:

<img src="https://user-images.githubusercontent.com/81584201/184208438-8b2864af-7e50-4984-83ea-a001df9fd33e.jpg" height="300"/>

The red nodes are Super Source and Super Target respectively. This modified graph always has the same result with pervious one but simplifies algorithms. Note that we should assign a huge enough capacities for pipes connected to Super Source and Super Target ( red arrows ) for preventing drained.

While fully preparing all mentioned above, we can start our code:

```

from collections import deque
CORRIDOR_CAPACITY = 2000001


# Great ref: https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm
class EscapePods:
    def __init__(self, entrances, exits, path):
        n = len(path)
        m = n + 2

        # graph[0] is the new single source s
        # graph[-1] is the new single sink t
        self.graph = []
        self.size = m
        for i in range(m):
            self.graph.append([0] * m)

        for i in range(n):
            for j in range(n):
                self.graph[i+1][j+1] = path[i][j]

        for num in entrances:
            self.graph[0][num + 1] = CORRIDOR_CAPACITY

        for num in exits:
            self.graph[num + 1][m - 1] = CORRIDOR_CAPACITY

    def bfs(self):
        parents = [-1] * self.size
        queue = deque()
        queue.append(0)
        while queue and parents[-1] == -1:
            u = queue.popleft()
            for v in range(self.size):
                if self.graph[u][v] > 0 and parents[v] == -1:
                    queue.append(v)
                    parents[v] = u
        path = []
        u = parents[-1]
        while u != 0:
            if u == -1:
                return None
            path.append(u)
            u = parents[u]
        path.reverse()
        return path

    def solve(self):
        max_flow = 0
        path = self.bfs()

        while path:
            cap = CORRIDOR_CAPACITY
            u = 0
            for v in path:
                cap = min(cap, self.graph[u][v])
                u = v
            max_flow += cap
            u = 0
            for v in path:
                self.graph[u][v] -= cap
                self.graph[v][u] += cap
                u = v
            path = self.bfs()
        return max_flow


def solution(entrances, exits, path):
    escape_pods = EscapePods(entrances, exits, path)
    res = escape_pods.solve()
    return res

```

<a align="center" href="#top">(Back to top)</a>
