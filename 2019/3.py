p0 = set()
p1 = set()

with open('3.txt') as f:
    i0 = f.readline().strip().split(',')
    i1 = f.readline().strip().split(',')

a = lambda p0, p1: tuple(p0[i] + p1[i] for i in range(min(len(p0), len(p1))))

p = (0, 0)
for instr in i0:
    d, l = instr[0], int(instr[1:])
    for i in range(l):
        p = a(p, {'U': (0, 1), 'D': (0, -1), 'L': (-1, 0), 'R': (1, 0)}[d])
        p0.add(p)

p = (0, 0)
for instr in i1:
    d, l = instr[0], int(instr[1:])
    for i in range(l):
        p = a(p, {'U': (0, 1), 'D': (0, -1), 'L': (-1, 0), 'R': (1, 0)}[d])
        p1.add(p)

print(min(abs(p[0]) + abs(p[1]) for p in p0.intersection(p1)))
