import collections

cnts = collections.Counter()

ids = open('2.txt').readlines()
    
for id in ids:
    cnt = collections.Counter(id)
    for v in set(cnt.values()):
        cnts[v] += 1

print(cnts[2]*cnts[3])

for i in range(len(ids)):
    for j in range(i + 1, len(ids)):
        chars = []
        for c in range(len(ids[i])):
            if ids[i][c] == ids[j][c]:
                chars.append(ids[i][c])
        if len(chars) == len(ids[i]) - 1:
            print(''.join(chars))
            break
