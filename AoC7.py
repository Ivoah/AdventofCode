input = open('7.in').readlines()
#input = ['123 -> x',
#		 '456 -> y',
#		 'x AND y -> d',
#		 'x OR y -> e',
#		 'x LSHIFT 2 -> f',
#		 'y RSHIFT 2 -> g',
#		 'NOT x -> h',
#		 'NOT y -> i'
#]

gates = ['AND', 'OR', 'NOT', 'LSHIFT', 'RSHIFT']
circuit = {}

def signal(circuit, wire):
	try:
		return int(wire)
	except ValueError:
		if circuit[wire]['gate'] == 'NONE':
			return signal(circuit, circuit[wire]['inputs'][0])
		elif circuit[wire]['gate'] == 'AND':
			return signal(circuit, circuit[wire]['inputs'][0]) & signal(circuit, circuit[wire]['inputs'][1])
		elif circuit[wire]['gate'] == 'OR':
			return signal(circuit, circuit[wire]['inputs'][0]) | signal(circuit, circuit[wire]['inputs'][1])
		elif circuit[wire]['gate'] == 'NOT':
			return 65535 - signal(circuit, circuit[wire]['inputs'][0])
		elif circuit[wire]['gate'] == 'LSHIFT':
			return signal(circuit, circuit[wire]['inputs'][0]) << signal(circuit, circuit[wire]['inputs'][1])
		elif circuit[wire]['gate'] == 'RSHIFT':
			return signal(circuit, circuit[wire]['inputs'][0]) >> signal(circuit, circuit[wire]['inputs'][1])

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
