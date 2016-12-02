instructions = open('2.in').readlines()
keypad = [
    [' ', ' ', '1', ' ', ' '],
    [' ', '2', '3', '4', ' '],
    ['5', '6', '7', '8', '9'],
    [' ', 'A', 'B', 'C', ' '],
    [' ', ' ', 'D', ' ', ' ']
]
pos1 = [1, 1]
pos2 = [0, 2]
key1 = lambda p: p[0] + 3*p[1] + 1
key2 = lambda p: keypad[p[1]][p[0]]
code1 = []
code2 = []
for line in instructions:
    for ins in line:
        if ins == 'U':
            pos1[1] = max(0, pos1[1] - 1)
            if pos2[1] > 0 and key2([pos2[0], pos2[1] - 1]) != ' ':
                    pos2[1] -= 1
        elif ins == 'D':
            pos1[1] = min(2, pos1[1] + 1)
            if pos2[1] < 4 and key2([pos2[0], pos2[1] + 1]) != ' ':
                    pos2[1] += 1
        elif ins == 'L':
            pos1[0] = max(0, pos1[0] - 1)
            if pos2[0] > 0 and key2([pos2[0] - 1, pos2[1]]) != ' ':
                    pos2[0] -= 1
        elif ins == 'R':
            pos1[0] = min(2, pos1[0] + 1)
            if pos2[0] < 4 and key2([pos2[0] + 1, pos2[1]]) != ' ':
                    pos2[0] += 1
    code1.append(key1(pos1))
    code2.append(key2(pos2))

print(''.join(map(str, code1)))
print(''.join(map(str, code2)))
