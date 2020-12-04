import json

input = json.load(open('12.txt'))

def count(j):
    if type(j) == int:
        return j
    elif type(j) == list:
        return sum([count(v) for v in j])
    elif type(j) == dict:
        if 'red' in j.values(): return 0
        return sum([count(v) for v in j.values()])
    else:
        return 0

print count(input)
