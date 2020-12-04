from collections import OrderedDict

code = open('9.txt').read()

def decode(string, length):
    part1 = 0
    part2 = 0
    i = 0
    while i < length:
        if string[i] == '(':
            n = ''
            while string[i] != 'x':
                i += 1
                n += string[i]
            a = int(n[:-1])
            n = ''
            while string[i] != ')':
                i += 1
                n += string[i]
            b = int(n[:-1])
            i += 1
            part1 += a*b
            part2 += decode(string[i:i+a], a)[1]*b
            i += a
        else:
            part1 += 1
            part2 += 1
            i += 1

    return (part1, part2)

print(decode(code, len(code)))
