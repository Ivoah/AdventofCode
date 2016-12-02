import sys
import matplotlib.pyplot as plt

data = [(a[0], int(a[1:])) for a in open('1.in').read().split(', ')]
visited = [(0, 0)]
hq = ()
dir = 0
for instr in data:
    dir = (dir + (1 if instr[0] == 'R' else -1))%4
    for i in range(instr[1]):
        if dir == 0:
            visited.append((visited[-1][0], visited[-1][1]+1))
        elif dir == 1:
            visited.append((visited[-1][0]+1, visited[-1][1]))
        elif dir == 2:
            visited.append((visited[-1][0], visited[-1][1]-1))
        elif dir == 3:
            visited.append((visited[-1][0]-1, visited[-1][1]))
        if hq == () and (visited[-1] in visited[:-1]):
            hq = visited[-1]

print(abs(visited[-1][0]) + abs(visited[-1][1]))
print(abs(hq[0]) + abs(hq[1]))
plt.plot(*zip(*visited))
plt.show()
