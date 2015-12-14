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

gates = {'AND': '&', 'OR': '|', 'NOT': '65535 -', 'LSHIFT': '<<', 'RSHIFT': '>>'}
circuit = {}

def signal(circuit, wire):
	re = True
	answer = circuit.pop('_' + wire + '_')
	while re:
		re = False
		for key in circuit:
			if answer != answer.replace(key, circuit[key]):
				answer = answer.replace(key, circuit[key])
				re = True
		print answer
	return eval(answer)

for ln, line in enumerate(input):
	line = line.split('->')
	wire = line.pop().strip()
	line = line.pop().strip()
	l = ''
	for v in line.split():
		if v in gates:
			l += ' ' + gates[v] + ' '
		else:
			try: l += str(int(v))
			except ValueError: l += '_' + v + '_'
	circuit['_' + wire + '_'] = '(' + l + ')'

print circuit
print signal(circuit, 'a')

#for i in 'defghixy':
#	print '{}: {}'.format(i, signal(circuit, i))
