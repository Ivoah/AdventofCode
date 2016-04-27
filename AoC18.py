#!/usr/bin/env python

rules = {'s': [2, 3], 'b': [3]}

grid = []
for line in open('18.in'):
    l = []
    for char in line.strip():
        l.append({'#': True, '.': False}[char])
    grid.append(l)
grid[0][0] = True
grid[0][-1] = True
grid[-1][0] = True
grid[-1][-1] = True

def print_grid(grid):
    for line in grid:
        s = ''
        for c in line:
            s += {True: '#', False: '.'}[c]
        print s

def neighbors(grid, x, y):
    n = 0
    for p in [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)]:
        try:
            if y + p[1] < 0 or x + p[0] < 0:
                continue
            if grid[y + p[1]][x + p[0]]:
                n += 1
        except IndexError:
            pass

    return n

def step(grid):
    new_grid = []
    for y, row in enumerate(grid):
        new_row = []
        for x, column in enumerate(row):
            n = neighbors(grid, x, y)
            new_row.append(n in rules['s'] and grid[y][x] or n in rules['b'])

        new_grid.append(new_row)

    new_grid[0][0] = True
    new_grid[0][-1] = True
    new_grid[-1][0] = True
    new_grid[-1][-1] = True
    return new_grid

for i in range(100):
    grid = step(grid)

t = 0
for line in grid:
    for light in line:
        if light:
            t += 1

print t
