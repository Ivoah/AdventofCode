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

def signal(wire):
	try:
		return int(wire)
	except ValueError:
		global circuit
		if circuit[wire]['gate'] == 'NONE':
			v = signal(circuit[wire]['inputs'][0])
			circuit[wire] = {'gate': 'NONE', 'inputs': [v]}
			return v
		elif circuit[wire]['gate'] == 'AND':
			v = signal(circuit[wire]['inputs'][0]) & signal(circuit[wire]['inputs'][1])
			circuit[wire] = {'gate': 'NONE', 'inputs': [v]}
			return v
		elif circuit[wire]['gate'] == 'OR':
			v = signal(circuit[wire]['inputs'][0]) | signal(circuit[wire]['inputs'][1])
			circuit[wire] = {'gate': 'NONE', 'inputs': [v]}
			return v
		elif circuit[wire]['gate'] == 'NOT':
			circuit[wire] = 65535 - signal(circuit[wire]['inputs'][0])
		elif circuit[wire]['gate'] == 'LSHIFT':
			v = signal(circuit[wire]['inputs'][0]) << signal(circuit[wire]['inputs'][1])
			circuit[wire] = {'gate': 'NONE', 'inputs': [v]}
			return v
		elif circuit[wire]['gate'] == 'RSHIFT':
			v = signal(circuit[wire]['inputs'][0]) >> signal(circuit[wire]['inputs'][1])
			circuit[wire] = {'gate': 'NONE', 'inputs': [v]}
			return v
		return circuit[wire]

def readCircuit(input):
	c = {}
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

		c[wire] = {'gate': gate, 'inputs': inputs}

	return c

circuit = readCircuit(input)
a = signal('a')
print 'Wire a: {}'.format(a)
circuit = readCircuit(input)
circuit['b'] = {'gate': 'NONE', 'inputs': [a]}
print 'Wire a: {}'.format(signal('a'))

#for i in 'defghixy':
#	print '{}: {}'.format(i, signal(circuit, i))
