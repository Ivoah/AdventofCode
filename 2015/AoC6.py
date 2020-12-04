input = open('6.txt').readlines()

lights = [[False for i in range(1000)] for i in range(1000)]
lights2 = [[0 for i in range(1000)] for i in range(1000)]

for l in input:
    l = l.split()
    if l[0] == 'toggle':
        command = lambda: not lights[y][x]
        bob = 2
        l = l[1:]
    else:
        if l[1] == 'on':
            command = lambda: True
            bob = 1
        else:
            command = lambda: False
            bob = -1
        l = l[2:]

    l = ','.join(l).split(',')
    c1 = [int(l[0]), int(l[1])]
    c2 = [int(l[3]), int(l[4])]

    for y in range(c1[1], c2[1]+1):
        for x in range(c1[0], c2[0]+1):
            lights[y][x] = command()
            lights2[y][x] = max(0, lights2[y][x] + bob)

n = 0
for y in lights:
    for x in y:
        n += 1 if x else 0

n2 = 0
for y in lights2:
    for x in y:
        n2 += x

print 'Number of lights: {}\nTotal brightness: {}'.format(n, n2)
