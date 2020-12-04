import collections

data = open('6.txt').read()

msg1 = ''
msg2 = ''
for i in range(8):
    c = collections.Counter(data[i::9]).most_common()
    msg1 += c[0][0]
    msg2 += c[-1][0]

print(msg1, msg2)
