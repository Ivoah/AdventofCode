import itertools
import math

report = list(map(int, open('1.in').readlines()))

for c in range(2, 4):
    _sum = next(filter(lambda x: sum(x) == 2020, itertools.combinations(report, c)))
    print(math.prod(_sum))
