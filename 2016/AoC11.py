from itertools import permutations

floors = []

for i, floor in enumerate(open('11.in').readlines()):
    items = ' '.join(floor.split()[4:]).split(', ')
    it = []
    for item in items:
        if item == 'nothing relevant.': continue
        item = item.split()[1:]
        if item[0] == 'a': item.pop(0)
        item = '{}{}'.format(item[0][0], item[1][0])
        it.append(item)
    floors.append(it)

def valid(flrs):
    for floor in flrs:
        generator = False
        micros = []
        generators = []
        for item in floor:
            if item[1] == 'g':
                generator = True
                generators.append(item)
            else:
                micros.append(item)
        for micro in micros[::]:
            if micro[0] + 'g' in generators:
                micros.pop(micros.index(micro))
                generators.pop(generators.index(micro[0] + 'g'))
        if len(micros) > 0 and generator:
            return False
    return True

seen_floors = []

def move(flrs, pos, mv = None):
    global seen_floors
    if flrs in seen_floors:
        return 0
    seen_floors.append(flrs[::])
    if mv is not None:
        if mv[0] == 'up':
            flrs[pos + 1][mv[1]] = flrs[pos][mv[1]]

print('\n'.join([str(f) for f in floors][::-1]), '\n')
print(valid(floors))
