import math

with open('3.txt') as f:
    _map = f.read().splitlines()

def trees(slope):
    t = 0
    for i, y in enumerate(range(0, len(_map), slope[1])):
        if _map[y][i*slope[0]%len(_map[y])] == '#':
            t += 1
    return t

print(trees((3, 1)))
print(math.prod(trees(slope) for slope in [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]))
