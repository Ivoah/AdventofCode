print(sum(int(m)//3 - 2 for m in open('1.in').read().split()))

masses = list(map(int, open('1.in').read().split()))
fuel = 0
for m in masses:
    f = m//3 - 2
    if f > 0:
        fuel += f
        masses.append(f)

print(fuel)
