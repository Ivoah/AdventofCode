banks = list(map(int, open('6.txt').read().split()))

prev = [banks[:]]
n = 0

while True:
    bank = banks.index(max(banks))
    redistrib = banks[bank]
    banks[bank] = 0
    for i in range(bank + 1, redistrib + bank + 1):
        banks[i%len(banks)] += 1

    if banks in prev:
        print(len(prev))
        print(len(prev) - prev.index(banks))
        break

    prev.append(banks[:])
