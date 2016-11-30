from hashlib import md5

key = 'bgvyzdsv'
i = 1
five = 0
six = 0

while True:
	hexd = md5(key + str(i)).hexdigest()
	if hexd[:5] == '0'*5 and five == 0:
		five = i
	if hexd[:6] == '0'*6 and six == 0:
		six = i
		break
	i += 1

print 'Five zeros: {}\nSix zeros: {}'.format(five, six)
