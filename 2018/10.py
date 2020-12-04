from PIL import Image

import re
import copy

pattern = re.compile(r'position=< ?(-?\d+), +(-?\d+)> velocity=< ?(-?\d+), +(-?\d+)>')

stars = [
    {
        'position': (int(match[1]), int(match[2])),
        'velocity': (int(match[3]), int(match[4]))
    } for match in pattern.finditer(open('10.txt').read())
]

size = lambda stars: (
    max(map(lambda star: star['position'][0], stars)) - min(map(lambda star: star['position'][0], stars)),
    max(map(lambda star: star['position'][1], stars)) - min(map(lambda star: star['position'][1], stars))
)

add_tuple = lambda t1, t2: (t1[0] + t2[0], t1[1] + t2[1])
sub_tuple = lambda t1, t2: (t1[0] - t2[0], t1[1] - t2[1])

last_size = size(stars)
seconds = 0
while True:
    for star in stars:
        star['position'] = add_tuple(star['position'], star['velocity'])
    
    new_size = size(stars)
    if new_size[0] > last_size[0] or new_size[1] > last_size[1]:
        break

    seconds += 1
    last_size = new_size

for star in stars:
    star['position'] = sub_tuple(star['position'], star['velocity'])

img = Image.new('1', add_tuple(size(stars), (1, 1)))
corner = (min(map(lambda star: star['position'][0], stars)), min(map(lambda star: star['position'][1], stars)))
for star in stars:
    img.putpixel(sub_tuple(star['position'], corner), 1)
img.save(f'10.png')

print(seconds)
