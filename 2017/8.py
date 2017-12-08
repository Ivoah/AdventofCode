import collections
import operator

instructions = open('8.in').readlines()
registers = collections.defaultdict(lambda: 0)

conds = {
    '==': operator.eq,
    '!=': operator.ne,
    '<': operator.lt,
    '<=': operator.le,
    '>': operator.gt,
    '>=': operator.ge
}

m = 0

for instr in instructions:
    reg1, inc_dec, v1, _if, reg2, cond, v2 = instr.split()
    if conds[cond](registers[reg2], int(v2)):
        if inc_dec == 'inc':
            registers[reg1] += int(v1)
        else:
            registers[reg1] -= int(v1)
    m = max(list(registers.values()) + [m])

print(max(registers.values()))
print(m)
