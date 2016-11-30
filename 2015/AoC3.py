from collections import Counter

input = open('3.in').read()
deliveries = [(0, 0)]
santa_deliveries = [(0, 0)]
robot_santa_deliveries = [(0, 0)]

for i, direction in enumerate(input.strip()):
    if direction == '^':
        new_dir = (0, 1)
    elif direction == 'v':
        new_dir = (0, -1)
    elif direction == '<':
        new_dir = (-1, 0)
    elif direction == '>':
        new_dir = (1, 0)

    deliveries.append(tuple(map(sum, zip(deliveries[-1], new_dir))))
    if i%2 == 0:
        santa_deliveries.append(tuple(map(sum, zip(santa_deliveries[-1], new_dir))))
    else:
        robot_santa_deliveries.append(tuple(map(sum, zip(robot_santa_deliveries[-1], new_dir))))

presents = Counter(deliveries).values()
combined_presents = Counter(santa_deliveries + robot_santa_deliveries).values()

print 'Houses that recieved at least one present from santa: {}\nHouses that recieved at least one present from santa and robo santa: {}'.format(len(presents), len(combined_presents))
