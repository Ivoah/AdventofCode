instructions = '''value 5 goes to bot 2
bot 2 gives low to bot 1 and high to bot 0
value 3 goes to bot 1
bot 1 gives low to output 1 and high to bot 0
bot 0 gives low to output 2 and high to output 0
value 2 goes to bot 2
'''.split('\n')
instructions = open('10.in').readlines()

class Bot:
    def __init__(self, bots, outputs, bot_num):
        self.bots = bots
        self.outputs = outputs
        self.bot_num = bot_num
        self.bay = []
        self.low_to = None
        self.high_to = None

    def add(self, val):
        self.bay.append(val)
        self.bay.sort()

        if len(self.bay) == 2:
            if self.bay == [17, 61]:
                print(self.bot_num)
            if self.low_to[0] == 'bot':
                self.bots[self.low_to[1]].add(self.bay[0])
            else:
                self.outputs[self.low_to[1]] = self.bay[0]

            if self.high_to[0] == 'bot':
                self.bots[self.high_to[1]].add(self.bay[1])
            else:
                self.outputs[self.high_to[1]] = self.bay[1]
            self.bay = []

bots = {}
outputs = {}

for inst in instructions:
    inst = inst.split()
    if 'gives' in inst:
        b = int(inst[1])
        l = (inst[5], int(inst[6]))
        h = (inst[10], int(inst[11]))
        if b not in bots:
            bots[b] = Bot(bots, outputs, b)
        bots[b].low_to = l
        bots[b].high_to = h

for inst in instructions:
    inst = inst.split()
    if 'value' in inst:
        n = int(inst[1])
        b = int(inst[5])
        bots[b].add(n)

print(outputs[0] * outputs[1] * outputs[2])
