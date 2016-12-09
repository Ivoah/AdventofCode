import collections
rooms = open('4.in').read().split()
ids = 0

def real(room):
    global ids
    frn = '-'.join(room.split('-')[:-1])
    rn = ''.join(room.split('-')[:-1])
    id = int(room.split('-')[-1].split('[')[0])
    cs = room.split('[')[1][:-1]
    if cs == ''.join(list(zip(*sorted(collections.Counter(rn).most_common(), key = lambda i: -(i[1]*100 - ord(i[0])))[:5]))[0]):
        ids += id

    decname = ''
    for c in frn:
        if c == '-':
            decname += ' '
        else:
            decname += chr(
                ((ord(c) + id) - 97)%26 + 97
            )

    if decname == 'northpole object storage':
        print(id)

for room in rooms:
    real(room)

print(ids)
