fav = 1364
target = (31, 39)

floor_plan = {}

def get_floor(pos):
    if pos[0] < 0 or pos[1] < 0: return '#'
    global floor_plan
    if pos in floor_plan: return 'v' #floor_plan[pos]
    i = pos[0]*pos[0] + 3*pos[0] + 2*pos[0]*pos[1] + pos[1] + pos[1]*pos[1]
    i += fav
    i = bin(i)[2:]
    i = ''.join('1' for c in i if c == '1')
    floor_plan[pos] = '#' if len(i)%2 else '.'
    return '#' if len(i)%2 else '.'

def search(x, y, n = 0):
    f = get_floor((x, y))
    if x == target[0] and y == target[1]:
        print('found in {} steps'.format(n))
        return True
    elif f == '#':
        #print('wall at %d,%d' % (x, y))
        return False
    elif f == 'v':
        #print('visited at %d,%d' % (x, y))
        return False

    #print('visiting %d,%d' % (x, y))

    # mark as visited
    #grid[x][y] = 3

    # explore neighbors clockwise starting by the one on the right
    if search(x+1, y, n + 1) or search(x, y-1, n + 1) or search(x-1, y, n + 1) or search(x, y+1, n + 1):
        return True

    return False

search(1, 1)
