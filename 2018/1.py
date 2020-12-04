print(eval(''.join(open('1.txt').read().split())))

freqs = {0: True}
freq = 0
changes = list(map(int, open('1.txt').read().split()))

c = 0
while True:
    freq += changes[c%len(changes)]
    if freq in freqs:
        print(freq)
        break
    freqs[freq] = True
    c += 1
