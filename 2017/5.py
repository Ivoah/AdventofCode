jumps = list(map(int, open('5.txt').readlines()))

n = 0
pc = 0
while 0 <= pc < len(jumps):
    jump = jumps[pc]
    jumps[pc] += 1
    pc += jump
    n += 1

print(n)

jumps = list(map(int, open('5.txt').readlines()))

n = 0
pc = 0
while 0 <= pc < len(jumps):
    jump = jumps[pc]
    jumps[pc] += -1 if jump >= 3 else 1
    pc += jump
    n += 1

print(n)
