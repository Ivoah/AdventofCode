code = '''#include <stdio.h>

int main() {
    for (int i = 0; i <= 1; i++) {
        int a = 0;
        int b = 0;
        int c = i;
        int d = 0;
'''


for i, line in enumerate(open('12.in').readlines()):
    instr = line.split()[0]
    args = line.split()[1:]
    if instr == 'cpy':
        code += '        line_{}: {} = {};\n'.format(i, args[1], args[0])
    elif instr == 'inc':
        code += '        line_{}: {}++;\n'.format(i, args[0])
    elif instr == 'dec':
        code += '        line_{}: {}--;\n'.format(i, args[0])
    elif instr == 'jnz':
        code += '        line_{}: if ({} != 0) goto line_{};\n'.format(i, args[0], i + int(args[1]))

code += '''        printf("%d\\n", a);
    }
    return 0;
}
'''

print(code)
