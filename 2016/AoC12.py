code = open('12.txt').readlines()

class CPU:
    def __init__(self, code, initial_state = None):
        self.code = code
        if initial_state:
            self.registers = initial_state
        else:
            self.registers = {'a': 0, 'b': 0, 'c': 0, 'd': 0}
        self.pc = 0

    value = lambda self, v: self.registers[v] if v in self.registers else int(v)

    def step(self):
        instr = self.code[self.pc].split()[0]
        args = self.code[self.pc].split()[1:]
        if instr == 'cpy':
            self.registers[args[1]] = self.value(args[0])
            self.pc += 1
        elif instr == 'inc':
            self.registers[args[0]] += 1
            self.pc += 1
        elif instr == 'dec':
            self.registers[args[0]] -= 1
            self.pc += 1
        elif instr == 'jnz':
            if self.value(args[0]) != 0:
                self.pc += self.value(args[1])
            else:
                self.pc += 1

    def run(self):
        while self.pc < len(self.code):
            self.step()

cpu1 = CPU(code)
cpu2 = CPU(code, {'a': 0, 'b': 0, 'c': 1, 'd': 0})
cpu1.run()
print(cpu1.registers['a'])
cpu2.run()
print(cpu2.registers['a'])
