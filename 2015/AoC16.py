input = open('16.txt').readlines()

aunts = []
target = {'children': 3, 'cats': 7, 'samoyeds': 2, 'pomeranians': 3, 'akitas': 0, 'vizslas': 0, 'goldfish': 5, 'trees': 3, 'cars': 2, 'perfumes': 1}
for i, line in enumerate(input):
    line = line.split()[2:]
    aunts.append({})
    for pair in zip(line[::2], line[1::2]):
        aunts[i][pair[0][:-1]] = int(pair[1].strip(','))

for i, aunt in enumerate(aunts):
    for j, item in enumerate(aunt):
        if aunt[item] != target[item]:
            break
        if j == len(aunt) - 1: print i + 1

for i, aunt in enumerate(aunts):
    for j, item in enumerate(aunt):
        if item in ['cats', 'trees']:
            if aunt[item] <= target[item]:
                break
        elif item in ['pomeranians', 'goldfish']:
            if aunt[item] >= target[item]:
                break
        elif aunt[item] != target[item]:
            break
        if j == len(aunt) - 1: print i + 1
