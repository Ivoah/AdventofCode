print((lambda inp: sum(inp[i] for i in range(len(inp)) if inp[i] == inp[(i + 1)%len(inp)]))(list(map(int, open('1.txt').read()))))
print((lambda inp: sum(inp[i] for i in range(len(inp)) if inp[i] == inp[(i + len(inp)//2)%len(inp)]))(list(map(int, open('1.txt').read()))))
