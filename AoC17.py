from itertools import permutations

input = open('17.in').readlines()

class Container:
    def __init__(self, size):
        self.size = size

    def __repr__(self):
        return 'Container({})'.format(self.size)

    def __add__(self, a):
        return self.size + a

    def __radd__(self, a):
        return a + self.size

    def __lt__(self, a):
        return self.size < a

    def __rlt__(self, a):
        return a < self.size

containers = []
for line in input:
    containers.append(Container(int(line)))

#containers = [Container(20), Container(15), Container(10), Container(5), Container(5)]
containers.sort()

solutions = []
for i in range(sum([2**i for i in range(len(containers))])):
    b = bin(i)[:1:-1]
    s = [containers[i] for i in range(len(b)) if b[i] == '1']
    if sum(s) == 150:
        solutions.append(s)

print len(solutions)

m = min([len(s) for s in solutions])
n = 0
for s in solutions:
    if len(s) == m:
        n += 1

print n
