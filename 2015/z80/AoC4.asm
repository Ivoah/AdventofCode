.binarymode TI8X
.variablename AOC4
.include "ti83plus.inc.min"

.org userMem - 2
.db $BB, $6D

main:
    bcall(_RclAns)
    ld c,(hl) \ inc hl \ ld b,(hl) \ inc hl ; Get length of Ans in bc
    ld (ans), bc
    ld (ans + 2), hl
loop:
    call increment
    bcall(_MD5Init)
    ld bc, (ans) \ ld hl, (ans + 2)
    bcall(_MD5Update)
    ld bc, (suffix) \ ld hl, suffix + 1
    bcall(_MD5Update)
    bcall(_MD5Final)

    ld hl, MD5Hash
    ld b, 1
@:  ld a, (hl)
    cp 0 \ jr nz, loop
    inc hl
    djnz {@-1}

    ld a, (hl)
    cp $0F \ jr nc, loop

    bcall(_ClrLCDFull)
    ld hl, 0 \ ld (curRow), hl
    ld hl, suffix + 1
    bcall(_PutS)
    bcall(_newline)

    ret

increment:
    .endasm
    def incString(string):
        l = len(string) - 1
        while l >= 0:
                if string[l] < '9':
                        string[l] = string[l])+1
                        l = 0
                else:
                    if l == 0:
                        string[l] = '1'
                        string.append('0')
                    else:
                        string[l] = '0'
                l -= 1
        return string
    .asm
    ld hl, suffix
    ld b, (hl)
    push bc
@:  inc hl
    djnz {@-1}
    pop bc
@:
    ld a, (hl) \ cp '9' \ jr nz, {@}
    inc (hl)
    ld b, 0
    jr {@4}
@:
    ld a, b \ cp 0 \ jr nz, {@1}
    ld (hl), '1'
    ld hl, suffix
    inc (hl)
    ld b, (hl)
    inc b
@:  inc hl
    djnz {@-1}
    ld (hl), '0'
    jr {@2}
@:
    ld (hl), '0'
@:
    dec hl
    djnz {@-5}

ans:
    .dw 0
    .dw 0

suffix:
    .db 6
    .db '254573'
    .fill 8, 0
