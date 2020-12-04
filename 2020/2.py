import re

patt = re.compile(r'(\d+)-(\d+) (\w): (\w+)')

with open('2.txt') as f:
    lines = f.readlines()

valid = 0
for line in lines:
    if match := patt.match(line):
        if int(match.group(1)) <= match.group(4).count(match.group(3)) <= int(match.group(2)):
            valid += 1
print(valid)

valid = 0
for line in lines:
    if match := patt.match(line):
        p0, p1, c, pw = int(match.group(1)) - 1, int(match.group(2)) - 1, match.group(3), match.group(4)
        if [pw[p0], pw[p1]].count(c) == 1:
            valid += 1
print(valid)
