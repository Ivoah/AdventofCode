from hashlib import md5

door_id = 'wtnhxymk'
password = ['_']*8

index = 0
while '_' in password:
    d = ''
    while d[:5] != '00000':
        d = md5(bytes(door_id + str(index), 'ascii')).hexdigest()
        index += 1
    if d[5] in '01234567' and password[int(d[5])] == '_':
        password[int(d[5])] = d[6]
        
print(''.join(password))
