print(sum((lambda row: max(row) - min(row))(list(map(int, line.split()))) for line in open('2.in').readlines()))
print(int(sum((lambda row: [a/b for a, b in __import__('itertools').permutations(row, 2) if a%b == 0][0])(list(map(int, line.split()))) for line in open('2.in').readlines())))
