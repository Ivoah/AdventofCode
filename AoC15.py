from itertools import permutations

input = open('15.in').readlines()
#input = [
#'Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n',
#'Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3'
#]

ingredients = {}
for line in input:
    line = line.split()
    ingredient = line.pop(0)[:-1]
    ingredients[ingredient] = {}
    for pair in zip(line[::2], line[1::2]):
        ingredients[ingredient][pair[0]] = int(pair[1].strip(','))
        ingredients[ingredient]['n'] = 0

def score(ing):
    t = {}
    for i in ing:
        for key in ing[i]:
            if key == 'n' or key == 'calories': continue
            try:
                t[key] += ing[i][key] * ing[i]['n']
            except KeyError:
                t[key] = ing[i][key] * ing[i]['n']

    return reduce(lambda x, y: x*y, [0 if v < 0 else v for v in t.values()])

m = 0
for a in range(1, 101):
    for b in range(a, 101 - a):
        for c in range(b, 101 - a - b):
            d = 100 - a - b - c
            for i in permutations([a, b, c, d]):
                ingredients['Sprinkles']['n'] = a
                ingredients['Butterscotch']['n'] = b
                ingredients['Chocolate']['n'] = c
                ingredients['Candy']['n'] = d
                m = max(m, score(ingredients))

print m
