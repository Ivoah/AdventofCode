diag = open('19.txt').read().splitlines()

at = lambda t0, t1: tuple(t0[i] + t1[i] for i in range(min(len(t0), len(t1))))

pos = (diag[0].find('|'), 0)
dir = (0, 1)
letters = ''
steps = 0

while True:
    pos = at(pos, dir)
    steps += 1
    c = diag[pos[1]][pos[0]]
    if c == '+':
        if dir[0] == 0: # coming from top/bottom
            if diag[pos[1]][pos[0] - 1] == '-': # left
                dir = (-1, 0)
            else:
                dir = (1, 0)
        else: # coming from left/right
            if diag[pos[1] - 1][pos[0]] == '|': # top
                dir = (0, -1)
            else:
                dir = (0, 1)
    elif c in '|-':
        pass
    elif c == ' ':
        break
    else:
        letters += c

print(letters, steps)
