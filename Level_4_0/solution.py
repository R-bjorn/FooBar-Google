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