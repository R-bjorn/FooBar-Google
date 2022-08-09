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