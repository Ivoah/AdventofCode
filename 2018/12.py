with open('12.in') as f:
    input = f.readlines()
initial_state = set(i for i, p in enumerate(input[0][len('initial state: '):]) if p == '#')
rules = {tuple(r[i] == '#' for i in range(5)): r[9] == '#' for r in input[2:]}

def gen_states(prev_states):
    last_state = prev_states[-1]
    next_state = set(i for i in range(min(last_state) - 2, max(last_state) + 3) if rules[tuple(j in last_state for j in range(i - 2, i + 3))])
    for i in range(-1, 2):
        if {j + i for j in last_state} == next_state: return prev_states + [next_state]
    return gen_states(prev_states + [next_state])

states = gen_states([initial_state])
print(sum(states[20]))
shift = 0
for i in range(-1, 2):
    if {j + i for j in states[-2]} == states[-1]:
        shift = i
print(shift)
print(sum(states[-1]) + shift*(50000000000 - len(states) + 1)*len(states[-1]))
