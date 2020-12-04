stream = open('9.txt').read()

total_score = 0
current_score = 0
garbage = 0

i = 0
while i < len(stream):
    c = stream[i]
    if c == '{':
        current_score += 1
    elif c == '}':
        total_score += current_score
        current_score -= 1
    elif c == '<':
        while stream[i] != '>':
            garbage += 1
            if stream[i] == '!':
                garbage -= 1
                i += 1
            i += 1
        garbage -= 1

    i += 1

print(total_score)
print(garbage)
