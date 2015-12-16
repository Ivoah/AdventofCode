input = open('8.in').readlines()

literal = 0
mem = 0
encoded = 0

for line in input:
    line = line.strip()
    literal += len(line)
    mem += len(eval(line))
    encoded += 2
    for c in line:
        if c == '"' or c == '\'' or c == '\\':
            encoded += 2
        else:
            encoded += 1

print 'literal size - memory size: {}'.format(literal - mem)
print 'encoded size - literal size: {}'.format(encoded - literal)
