input = open('7.in').readlines()

circuit = {}

def signal(circuit, wire):
	try:
		return int(wire)
	except ValueError:
		try:
			if circuit[wire]['gate'] == 'NONE':
				return signal(circuit, circuit[wire]['inputs'][0])
			elif circuit[wire]['gate'] == 'AND':
				return signal(circuit, circuit[wire]['inputs'][0]) & signal(circuit, circuit[wire]['inputs'][1])
			elif circuit[wire]['gate'] == 'OR':
				return signal(circuit, circuit[wire]['inputs'][0]) | signal(circuit, circuit[wire]['inputs'][1])
			elif circuit[wire]['gate'] == 'NOT':
				return ~signal(circuit, circuit[wire]['inputs'][0])
			elif circuit[wire]['gate'] == 'LSHIFT':
				return signal(circuit, circuit[wire]['inputs'][0]) << signal(circuit, circuit[wire]['inputs'][1])
			elif circuit[wire]['gate'] == 'RSHIFT':
				return signal(circuit, circuit[wire]['inputs'][0]) >> signal(circuit, circuit[wire]['inputs'][1])
		except TypeError as e:
			print wire, circuit[wire]
			raise e

for i, line in enumerate(input):
	line = line.split()
	line.pop(-2)
	wire = line.pop(-1)
	inputs = []
	gate = 'NONE'
	for i in line:
		if i.upper() == i:
			gate = i
		else:
			inputs.append(i)

	circuit[wire] = {'gate': gate, 'inputs': inputs, 'line': i}

print signal(circuit, 'a')
