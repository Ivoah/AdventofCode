target = 36000000

def get_presents(house):
    presents = 0
    for i in range(1, house + 1):
        if house%i == 0:
            presents += i*10

    return presents

#n = 1
#while get_presents(n) < target:
#    n += 1

#print n

# Thanks to https://www.reddit.com/r/adventofcode/comments/3xjpp2/day_20_solutions/cy59zd9
houses = [0 for i in range(target/10)]
for i in range(1, target / 10):
    for j in range(i, target / 10, i):
        houses[j] += i * 10

for n, house in enumerate(houses):
    if house >= target:
        print n
        break

houses = [0 for i in range(target/10)]
for i in range(1, target / 10):
    for j in range(i, (i*50 + i) if (i*50 + i) < target/10 else target/10, i):
        houses[j] += i * 10

for n, house in enumerate(houses):
    if house >= target:
        print n
        break
