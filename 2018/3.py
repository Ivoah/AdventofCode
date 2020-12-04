cloth = [0]*1000*1000

claims = (lambda i: [((int(c[2].split(',')[0]), int(c[2].split(',')[1][:-1])), (int(c[3].split('x')[0]), int(c[3].split('x')[1]))) for c in i])(list(map(str.split, open('3.txt').readlines())))

for claim in claims:
    for r in range(claim[0][1], claim[0][1] + claim[1][1]):
        for c in range(claim[0][0], claim[0][0] + claim[1][0]):
            cloth[r*1000 + c] += 1

print(len(list(filter(lambda c: c >= 2, cloth))))
# print(cloth)

for i, claim in enumerate(claims):
    overlaps = False
    for r in range(claim[0][1], claim[0][1] + claim[1][1]):
        for c in range(claim[0][0], claim[0][0] + claim[1][0]):
            if cloth[r*1000 + c] > 1: overlaps = True
    if not overlaps:
        print(i + 1)

from PIL import Image

img = Image.new('L', (1000, 1000))
for i in range(1000*1000):
    img.putpixel((i%1000, i//1000), cloth[i]*35)

img.save('cloth.png')
