import collections

input = {}

for line in open('7.in').readlines():
    line = line.split()
    input[line[0]] = {
        'weight': int(line[1].strip('()')),
        'children': [l.strip(',') for l in line[3:]]
    }

def find_root(child):
    for node in input:
        if child in input[node]['children']:
            return find_root(node)
    return child

root = find_root(list(input.keys())[0])
print(root)

def build_tree(root):
    return {
        'weight': input[root]['weight'],
        'children': {child: build_tree(child) for child in input[root]['children']}
    }

def weight(tree):
    return tree['weight'] + sum(weight(node) for node in tree['children'].values())

def balanced(tree):
    return len(set(weight(child) for child in tree['children'].values())) == 1

def fixed(tree):
    current = tree
    while True:
        c = False
        for child in current['children'].values():
            if not balanced(child):
                current = child
                c = True
        if c: continue

        counter = collections.Counter(weight(child) for child in current['children'].values())
        counts = counter.most_common()
        for child in current['children'].values():
            if weight(child) == counts[-1][0]:
                return counts[0][0] - (weight(child) - child['weight'])

tree = build_tree(root)
print(fixed(tree))
