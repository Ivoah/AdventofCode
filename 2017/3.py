from math import *

INPUT = 289326

nearest_odd_square = sq = lambda n: (floor(sqrt(n)) if floor(sqrt(n))%2 != 0 else floor(sqrt(n)) - 1)**2

n = INPUT - 1
p = nearest_odd_square(n)

x = (sqrt(p) + 1)/2
y = (sqrt(p) - 1)/2

if p + sqrt(p) >= n:
    y -= n - p
elif p + 2*sqrt(p) + 1 >= n:
    y -= sqrt(p)
    x -= n - p - sqrt(p)
elif p + 3*sqrt(p) + 3 >= n:
    y -= n - p - 3*sqrt(p) - 2
    x = -(sqrt(p) + 1)/2
else:
    y += 1
    x -= p + 4*sqrt(p) - n

print(f'({x}, {y})')
print(abs(x) + abs(y))

from requests import get

values = {int(row.split()[1]): True for row in get('https://oeis.org/A141481/b141481.txt').text.split('\n')[2:-8]}
i = INPUT
while True:
    if i in values:
        print(i)
        break
    i += 1
