input = {}

for line in '''pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)'''.split('\n'): #open('7.in').readlines():
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
    if balanced(tree):
        return sum(fixed(child) for child in tree['children'].values())
    else:
        pass

tree = build_tree(root)
#print(fixed(tree))
