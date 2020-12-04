ips = open('7.txt').readlines()
samples = '''aba[bab]xyz
xyx[xyx]xyx
aaa[kek]eke
zazbz[bzb]cdb
'''.split()

def tls(ip):
    in_bracket = False
    maybe = False
    for i in range(len(ip)):
        if ip[i] == '[':
            in_bracket = True
        elif ip[i] == ']':
            in_bracket = False
        if ip[i:i+2] == ip[i+3:i+1:-1] and ip[i] != ip[i+1]:
            if in_bracket:
                return False
            else:
                maybe = True

    return maybe

def ssl(ip):
    in_bracket = False
    abas = []
    babs = []
    for i in range(len(ip)):
        if ip[i] == '[':
            in_bracket = True
        elif ip[i] == ']':
            in_bracket = False
        try:
            if ip[i] == ip[i+2] and ip[i] != ip[i+1]:
                if in_bracket:
                    babs.append(ip[i:i+3])
                    for aba in abas:
                        for bab in babs:
                            if aba and aba[0] == bab[1] and aba[1] == bab[0]:
                                return True
                else:
                    abas.append(ip[i:i+3])
                    for aba in abas:
                        for bab in babs:
                            if bab and aba[0] == bab[1] and aba[1] == bab[0]:
                                return True
        except IndexError:
            return False

    return False


total_tls = 0
total_ssl = 0
for ip in ips:
    if tls(ip):
        total_tls += 1
    if ssl(ip):
        total_ssl += 1

print(total_tls)
print(total_ssl)
