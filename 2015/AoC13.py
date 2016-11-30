from itertools import permutations
from sys import maxint

input = open('13.in').readlines()

people = {}

for line in input:
    line = line.split()
    a, happiness, b = line[0], int(line[3]) * (-1 if line[2] == 'lose' else 1), line[10][:-1]
    try:
        people[a][b] = happiness
    except KeyError:
        people[a] = {b: happiness}

max_happiness = 0

for arrangment in [list(p) for p in permutations(people.keys())]:
    h = 0
    arrangment.append(arrangment[0])
    for pair in zip(arrangment, arrangment[1:]):
        h += people[pair[0]][pair[1]]
        h += people[pair[1]][pair[0]]
    max_happiness = max(h, max_happiness)

print 'Best arrangment: {}'.format(max_happiness)

people['me'] = {}
for person in people.keys():
    people[person]['me'] = 0
    people['me'][person] = 0

max_happiness = 0

for arrangment in [list(p) for p in permutations(people.keys())]:
    h = 0
    arrangment.append(arrangment[0])
    for pair in zip(arrangment, arrangment[1:]):
        h += people[pair[0]][pair[1]]
        h += people[pair[1]][pair[0]]
    max_happiness = max(h, max_happiness)
    min_happiness = min(h, min_happiness)

print 'Best arrangment: {}'.format(max_happiness)
