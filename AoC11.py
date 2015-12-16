input = 'hxbxwxba'

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
    if len(password) != 8 or password.lower() != password:
        return False

    return True
