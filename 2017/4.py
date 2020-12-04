print(len([pp for pp in open('4.txt').readlines() if len(pp.split()) == len(set(pp.split()))]))
print(len([pp for pp in open('4.txt').readlines() if len(pp.split()) == len(set(map(frozenset, pp.split())))]))
