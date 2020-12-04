input = open('2.txt').readlines()
total_paper = 0
total_ribbon = 0

for present in input:
    l, w, h = map(int, present.split('x'))

    paper = 2*l*w + 2*w*h + 2*h*l + min([l*w, w*h, h*l])
    ribbon = 2*sum(sorted([l,w,h])[:2]) + l*w*h

    total_paper += paper
    total_ribbon += ribbon

print 'Total paper needed: {}\nTotal ribbon needed: {}'.format(total_paper, total_ribbon)
