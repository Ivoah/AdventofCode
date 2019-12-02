def run(a0, a1):
    mem = list(map(int, open('2.in').read().split(',')))

    mem[1] = a0
    mem[2] = a1

    pc = 0
    while True:
        op = mem[pc]
        if op == 1: # Add
            mem[mem[pc + 3]] = mem[mem[pc + 1]] + mem[mem[pc + 2]]
        elif op == 2: # Multiply
            mem[mem[pc + 3]] = mem[mem[pc + 1]] * mem[mem[pc + 2]]
        elif op == 99: # Halt
            break
        pc += 4

    return mem[0]

print(run(12, 2))
for a0 in range(100):
    for a1 in range(100):
        if run(a0, a1) == 19690720:
            print(100*a0 + a1)
