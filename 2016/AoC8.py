import drawille

instructions = open('8.in').readlines()
screen = [[' ']*50 for i in range(6)]

def shift(seq, n):
    n = -(n % len(seq))
    return seq[n:] + seq[:n]

for instr in instructions:
    if 'rect' in instr:
        a, b = map(int, instr.split()[1].split('x'))
        for i in range(b):
            screen[i][:a] = ['#']*a
    elif 'row' in instr:
        y = int(instr.split()[2][2:])
        n = int(instr.split()[4])
        screen[y] = shift(screen[y], n)
    elif 'column' in instr:
        x = int(instr.split()[2][2:])
        n = int(instr.split()[4])
        for i in range(n):
            temp = screen[5][x]
            for j in range(4, -1, -1):
                screen[j + 1][x] = screen[j][x]
            screen[0][x] = temp

lit = 0
for row in screen:
    for c in row:
        if c == '#':
            lit += 1

print(lit)
print('\n'.join([''.join(c) for c in screen]))
canvas = drawille.Canvas()
for y, row in enumerate(screen):
    for x, c in enumerate(row):
        if c == '#':
            canvas.set(x, y)

print(canvas.frame())
