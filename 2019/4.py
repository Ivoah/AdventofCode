rng = (137683, 596253)

valid1 = lambda pw: sorted(pw) == list(pw) and any(p[0] == p[1] for p in zip(pw, pw[1:]))

def valid2(pw):
    if sorted(pw) != list(pw): return False
    c = ''
    runs = []
    for d in pw:
        if d != c:
            c = d
            runs.append(1)
        else:
            runs[-1] += 1
    return 2 in runs

nvalid1 = 0
nvalid2 = 0
for pw in range(rng[0], rng[1] + 1):
    if valid1(str(pw)): nvalid1 += 1
    if valid2(str(pw)): nvalid2 += 1

print(nvalid1, nvalid2)
