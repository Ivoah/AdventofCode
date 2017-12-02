print(sum((lambda row: max(row) - min(row))(map(int, line.split())) for line in open('2.in').readlines()))
