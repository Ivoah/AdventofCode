input = open('8.in').readlines()

literal = 0
mem = 0

for line in input:
    literal += len(line)
    mem += len(eval(line))

print 'literal size - memory size = {}'.format(literal - mem)
