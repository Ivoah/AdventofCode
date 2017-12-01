from hashlib import md5

#salt = 'zpqevtbw' # Adam's
salt = 'yjdafjpo' # Mine
#zpqevtbw, 16106, 22423
hashes = {}

def hsh(s):
    if s in hashes:
        return hashes[s]
    else:
        h = md5(s.encode()).hexdigest()
        hashes[s] = h
        return h

def valid(thing, i):
    for j in range(len(thing) - 3):
        c = thing[j:j+3]
        if c[0] == c[1] and c[1] == c[2]:
            for k in range(1, 1001):
                h = hsh(salt + str(i + k))
                if c[0]*5 in h:
                    #print(h, c[0], i + k)
                    return i, h
            break
    return False, False

i = 0
keys = []
while False:
    s = hsh(salt + str(i))
    k = valid(s, i)
    if k:
        keys.append(k)
        print(len(keys), k, s)
    i += 1

adam = '''873: 147b97ee83e7aefd23d2e088892d5e21
1442: 75f7f3c88801b621c5380e860cd467e6
1494: 63d0f4b04288803d424142a693846045
2025: b3c1e7290dad776ddc9b8eae30efff4e
2065: a8ae6cb86cebe6f8d60a1b363c1dddf2
2275: eed6f58af32bddd7df381ae02d008fde
2408: 0b16225478ddd48843125c15b438af8d
2489: 05a4075dbc17a9741dd248e10416fddd
2966: 5953f194045e56acbbb41fc44f49ec83
3126: dcf475eaf5ed1bbbac529b3fa3239749
3409: e474abbb4c6f15af27f37a422ae18762
12362: 4c313f666f5a59eba5234e8da007f225
12513: 34f83ec923833b71fe21aaad72cce6a0
12529: de6bb047b404c7d9666672242459c40e
12620: 901ec6bedaa0a78bde04016661a5c7a0
12625: 1ba276c778aa652bbf40777d813ea8bf
12638: 014794de4faaac366a5c76c9ee32f780
12662: 953b0c129f6fe3b21807702bee87775b
12679: cc312aaa262adb67e8b0e100f934be62
12742: 4e6740e8d549799cbfe274321e41aaae
12793: 87acfee93f877835e2ebe1aaae53bbd5
12888: 80042ec0f40e0a611d5aaa2fe6ed1e4e
12894: cc3c4aff831740fe5fb7c666d5a57e9b
12947: f0aa666de7122c21c804d5ec5c52bb53
13012: f7dfd666d5f01617facf11068d9bb8e8
13032: f6638be7771ddb1f98d83ecac83c6627
13136: 8bd67771935cd395e9afd626c769b0ba
13138: 136f54308777963401314d89de59b960
13145: c5aee978bb626c35777bc3cbc20448b2
13155: cc42acdb61db4956d12be8ece3777dcf
13222: 38bfd91f5e61caaa16420c75267da08c
13238: b09cbfc636baff277be00baaa2d7011e
13370: a0362d3b509c19fcbd10e4faaa8d9f82
13519: da1a12f8cbf8006cc6b98777dbca78f1
16945: ba92fbd8885381dfab116c03bdd31764
17072: ab2d15a208880a60ebf9a8e7b7493aec
17112: e73047b38ffbbf8e02d2bcb55888c91b
17316: b7bb39f4959383d6ed64d888af2a5355
17721: 36de749e5d5860dddd6bf51e505c3b65
17725: a5bd39a7adddad25f02b004308aaa56d
17793: c606e099d8eb5dddd09a777c22107856
17813: 5730849d781d3ddde1ac162fc23c3a58
17833: 98df92113badd27e58ddd04776d3413a
21631: 082f5300a3a22d55b33397e99be4bcd3
21641: 2b3dc7b6684f333d171f48aae019c590
21805: 09efbec15eb333fbbf4500e458b9bb25
21963: b1f8612695a476e9213373b40527a333
22235: 297d1eb469e92b086e8cbbe9333909ab
22442: b1b2cab6447e31a3564d6633371a3199
22546: 99f702152a95a71092db39b74333eb03
24182: 4f2c5b2ffd961c49d315e614222838b8
24367: 74f5a4196534ec2a29634b3948022230
24474: 7c5bfcbdbaca222ae901c9164501b4a4
24672: be476437c2223716e748dd1b120ce9f4
24725: 43e057d23a79a0ea292b8b46a1b8c222
24832: 729fa7a1f58818a07ddd3778c28f3339
24844: 896c885eb10e62f9d22041f47a4ddd28
24942: 22266ae3941634d84253d8e5b516e69a
25188: 25f3880db22af9fdddc844f8709cd421
25195: 44faf7ddd8dde375c5973b4d43f38b20
25263: 75c4af159dddd1972cb96f395b5a776c
25284: bd5eeddd4d9acec8b75d35dd758db6a5
25327: eddd19b11df33f4cb77dbc6b2d4dd73b
25427: d95b61890f0df9add71f1427ddd7bf54'''

for p in adam.splitlines():
    i = int(p.split(':')[0])
    h = p.split(':')[1].strip()
    v, hh = valid(h, i)
    if not v:
        print(i, h, hh)
