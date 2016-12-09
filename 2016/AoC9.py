from collections import OrderedDict

code = open('9.in').read()

output = ''
i = 0
while i < len(code):
    if code[i] == '(':
        n = ''
        while code[i] != 'x':
            i += 1
            n += code[i]
        a = int(n[:-1])
        n = ''
        while code[i] != ')':
            i += 1
            n += code[i]
        b = int(n[:-1])
        i += 1
        output += code[i:i+a]*b
        i += a
    else:
        output += code[i]
        i += 1

print(len(output))

def find_markers(string):
    output = 0
    i = 0
    while i < len(string):
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
            output += find_markers(string[i:i+a])*b
            i += a
        else:
            output += 1
            i += 1

    return output

print(find_markers(code))
