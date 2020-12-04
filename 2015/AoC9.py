from itertools import permutations
from sys import maxint

input = open('9.txt').readlines()

dists = {}
locations = []

for line in input:
    line = line.split()
    source, destination, distance = line[0], line[2], int(line[4])
    if source not in locations: locations.append(source)
    if destination not in locations: locations.append(destination)
    try:
        dists[source][destination] = distance
    except KeyError:
        dists[source] = {destination: distance}

    try:
        dists[destination][source] = distance
    except KeyError:
        dists[destination] = {source: distance}

shortest = maxint
longest = 0

for route in permutations(locations):
    dist = 0
    for path in zip(route, route[1:]):
        dist += dists[path[0]][path[1]]
    shortest = min(dist, shortest)
    longest = max(dist, longest)

print 'Shortest route: {}'.format(shortest)
print 'Longest route: {}'.format(longest)
