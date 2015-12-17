input = 'hxbxwxba'
#input = 'abcdefgh'

def incString(string):
    string = list(string)
    l = len(string) - 1
    while l >= 0:
        if string[l] < 'z':
            string[l] = chr(ord(string[l])+1)
            l = 0
        else:
            string[l] = 'a'
            #if l == len(string)-1:
            #    string.append('a')
        l -= 1
    return ''.join(string)

def isValid(password):
    if len(password) != 8 or password.lower() != password: return False

    n = 0
    lc = '\x00'
    v = False
    for c in password:
        if ord(lc) + 1 == ord(c):
            n += 1
        else:
            n = 0
        lc = c
        if n == 2: v = True
    if not v: return False


    lc = ''
    n = 0
    a = ''
    for c in password:
        if lc == c and a != c:
            n += 1
            a = c
        lc = c
    if n < 2: return False

    any(c in password for c in 'iol')
    for c in 'iol':
        if c in password: return False

    return True

while not isValid(input):
    input = incString(input)
print input

input = incString(input)
while not isValid(input):
    input = incString(input)
print input
