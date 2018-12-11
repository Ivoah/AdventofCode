import operator

serial_number = 9221

def get_default(o, i):
    try:
        return int(o[i])
    except IndexError:
        return 0

power_level = lambda x, y: get_default(str(((x + 10)*y + serial_number)*(x + 10)), -3) - 5

max_power = ((), -1000)

for x in range(1, 300):
    for y in range(1, 300):
        area_power = sum([
            sum([
                power_level(_x, _y)
                for _x in range(x, x + 3)
            ])
            for _y in range(y, y + 3)
        ])
        
        max_power = max(max_power, ((x, y), area_power), key=operator.itemgetter(1))

print(max_power)
