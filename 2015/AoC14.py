input = open('14.txt').readlines()

class Reindeer():
    def __init__(self, name, speed, sleep):
        self.name = name
        self.speed = speed
        self.sleep = sleep
        self.ftime = speed[1]
        self.stime = sleep
        self.distance = 0
        self.points = 0

    def getName(self):
        return self.name

    def step(self, n = 1):
        for i in range(n):
            if self.ftime > 0:
                self.ftime -= 1
                self.distance += self.speed[0]
            else:
                self.stime -= 1
                if self.stime == 0:
                    self.stime = self.sleep
                    self.ftime = self.speed[1]

    def getDistance(self):
        return self.distance

    def givePoint(self, n = 1):
        self.points += n

    def takePoint(self, n = 1):
        self.points -= n

    def getPoints(self):
        return self.points

reindeer = []
for line in input:
    line = line.split()
    reindeer.append(Reindeer(line[0], (int(line[3]), int(line[6])), int(line[13])))

for i in range(2503):
    m = ['', 0]
    for r in reindeer:
        r.step()
    for r in reindeer:
        if r.getDistance() > m[1]:
            m[1] = r.getDistance()
            m[0] = r
    m[0].givePoint()

print max(r.getDistance() for r in reindeer)
print max(r.getPoints() for r in reindeer)
