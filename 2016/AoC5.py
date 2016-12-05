from hashlib import md5

door_id = 'wtnhxymk'
password1 = ''
password2 = ['_']*8

index = 0
while '_' in password2:
    d = ''
    while d[:5] != '00000':
        d = md5(bytes(door_id + str(index), 'ascii')).hexdigest()
        index += 1

    if len(password1) < 8:
        password1 += d[5]
    if d[5] in '01234567' and password2[int(d[5])] == '_':
        password2[int(d[5])] = d[6]

print(password1)
print(''.join(password2))
