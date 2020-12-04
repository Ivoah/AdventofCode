input = open('1.txt').read()
floor = 0
basement = 0

for i, char in enumerate(input):
    if char == '(': floor += 1
    else: floor -= 1
    if floor == -1 and basement == 0:
        basement = i + 1

print 'Floor: {}\nBasement: {}'.format(floor, basement)
