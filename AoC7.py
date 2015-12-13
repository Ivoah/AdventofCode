input = open('7.in').readlines()
'''
input = ['123 -> x',
		 '456 -> y',
		 'x AND y -> d',
		 'x OR y -> e',
		 'x LSHIFT 2 -> f',
		 'y RSHIFT 2 -> g',
		 'NOT x -> h',
		 'NOT y -> i'
]
'''

gates = ['AND', 'OR', 'NOT', 'LSHIFT', 'RSHIFT']
circuit = {}

cache = {}
def signal(circuit, wire):
	try:
		return int(wire)
	except ValueError:
		global cache
		if wire not in cache.values():
			if circuit[wire]['gate'] == 'NONE':
				cache[wire] = signal(circuit, circuit[wire]['inputs'][0])
			elif circuit[wire]['gate'] == 'AND':
				cache[wire] = signal(circuit, circuit[wire]['inputs'][0]) & signal(circuit, circuit[wire]['inputs'][1])
			elif circuit[wire]['gate'] == 'OR':
				cache[wire] = signal(circuit, circuit[wire]['inputs'][0]) | signal(circuit, circuit[wire]['inputs'][1])
			elif circuit[wire]['gate'] == 'NOT':
				cache[wire] = 65535 - signal(circuit, circuit[wire]['inputs'][0])
			elif circuit[wire]['gate'] == 'LSHIFT':
				cache[wire] = signal(circuit, circuit[wire]['inputs'][0]) << signal(circuit, circuit[wire]['inputs'][1])
			elif circuit[wire]['gate'] == 'RSHIFT':
				cache[wire] = signal(circuit, circuit[wire]['inputs'][0]) >> signal(circuit, circuit[wire]['inputs'][1])
		return cache[wire]

for ln, line in enumerate(input):
	line = line.split()
	wire = line.pop()
	line.pop()
	inputs = []
	gate = 'NONE'
	for i in line:
		if i in gates:
			gate = i
		else:
			inputs.append(i)

	circuit[wire] = {'gate': gate, 'inputs': inputs, 'line': ln + 1}

#print circuit
print signal(circuit, 'a')

#for i in 'defghixy':
#	print '{}: {}'.format(i, signal(circuit, i))
