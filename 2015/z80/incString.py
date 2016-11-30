import sys

def incString(string):
    string = list(string)
    l = len(string) - 1
    while l >= 0:
        if string[l] < '9':
            string[l] = chr(ord(string[l])+1)
            l = 0
        else:
            if l == 0:
                string[l] = '1'
                string.append('0')
            else:
                string[l] = '0'
        l -= 1
    return ''.join(string)

print incString(sys.argv[1])
