input = '1113222113'

def look_and_say(number):
    next = ''
    lc = number[0]
    n = 0
    for c in number:
        if c != lc:
            next += str(n) + lc
            lc = c
            n = 1
        else:
            n += 1
    next += str(n) + lc
    return next

for i in range(40):
    input = look_and_say(input)

print '40 times: {}'.format(len(input))

for i in range(10):
    input = look_and_say(input)

print '50 times: {}'.format(len(input))
