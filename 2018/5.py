import string

with open('5.in') as f:
    polymer = f.read().strip()


pair = lambda p, c: p[c] != p[c + 1] and p[c].lower() == p[c + 1].lower()

poly1 = list(polymer)
c = 0
while c < len(poly1) - 1:
    c = max(c, 0)
    if pair(poly1, c):
        del poly1[c:c + 2]
        c -= 2
    c += 1

print(len(poly1))

size = len(polymer)
for letter in string.ascii_lowercase:
    poly2 = list(polymer.replace(letter, '').replace(letter.upper(), ''))
    c = 0
    while c < len(poly2) - 1:
        c = max(c, 0)
        if pair(poly2, c):
            del poly2[c:c + 2]
            c -= 2
        c += 1
    size = min(size, len(poly2))    

print(size)
