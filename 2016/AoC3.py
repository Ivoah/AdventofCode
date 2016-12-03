valid = 0

tris = open('3.in').read()
for tri in tris.split('\n'):
    a, b, c = sorted(map(int, tri.split()))
    if c < a + b:
        valid += 1

print(valid)

spl = tris.split()
valid = 0

ntris = []

c = [[], [], []]
for i, t in enumerate(spl):
    i = i%3
    c[i].append(int(t))
    if len(c[i]) == 3:
        ntris.append(c[i])
        c[i] = []

for tri in ntris:
    a, b, c = sorted(tri)
    if c < a + b:
        valid += 1

print(valid)
