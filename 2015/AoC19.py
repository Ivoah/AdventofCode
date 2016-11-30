import re

patt = re.compile(r'(\S+) => (\S+)')
replacements = {}
molecule = ''
for line in open('19.in'):
    m = patt.match(line)
    if m:
        try:
            replacements[m.group(1)].append(m.group(2))
        except KeyError:
            replacements[m.group(1)] = [m.group(2)]
    else:
        molecule = line.strip()

#replacements = {'H': ['HO', 'OH'], 'O': ['HH']}
#molecule = 'HOH'

seen_ = []
def seen(mol):
    if mol in seen_:
        return True
    seen_.append(mol)
    return False

n = 0
for r in replacements:
    for b in replacements[r]:
        l = -1
        while True:
            try:
                l = molecule.index(r, l+1)
                s = molecule[:l] + b + molecule[l + len(r):]
                if not seen(s):
                    #print s
                    n += 1
            except ValueError:
                break

print n

# Thanks to https://www.reddit.com/r/adventofcode/comments/3xflz8/day_19_solutions/cy4h7ji
count = -1
for c in range(len(molecule)):
    if molecule[c:c+2] == 'Rn' or molecule[c:c+2] == 'Ar':
        continue
    elif molecule[c] == 'Y':
        count -= 1
    elif molecule[c] == molecule[c].upper():
        count += 1

print count
