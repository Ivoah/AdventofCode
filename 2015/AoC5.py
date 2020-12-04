from collections import Counter

input = open('5.txt').readlines()

vowels = 'aeiou'
naughty_strings = ['ab', 'cd', 'pq','xy']

nice_strings_first = 0
nice_strings_second = 0

for line in input:
    nice_first = True
    nice_second = True

    #First rules for niceness
    vs = 0
    for v in vowels:
        for l in line:
            if v == l:
                vs += 1
    if vs < 3:
        nice_first = False
        #print 'Not enough vowels'

    ll = ''
    n = False
    for l in line:
        if ll == l:
            n = True
        ll = l
    if not n:
        nice_first = False
        #print 'No repeated letters'

    for ns in naughty_strings:
        if ns in line:
            nice_first = False
            #print 'Has a naughty string'

    #Second rules for niceness
    #It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
    pairs = [a+b for a, b in zip(line[:-1], line[1:])]
    i = 0
    while i < len(pairs)-1:
        if pairs[i] == pairs[i+1]:
            pairs.pop(i+1)
            continue
        i += 1

    if sorted(Counter(pairs).values())[-1] < 2:
        nice_second = False
        #print 'No repeat pair'

    #It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
    ll = ['', '']
    n = False
    for l in line:
        if ll[0] == l:
            n = True
        ll[0] = ll[1]
        ll[1] = l
    if not n:
        nice_second = False
        #print 'No repeat separated by 1'

    if nice_first: nice_strings_first += 1
    if nice_second: nice_strings_second += 1

print 'Nice strings (first rules): {}\nNice strings (second rules): {}'.format(nice_strings_first, nice_strings_second + 1)
