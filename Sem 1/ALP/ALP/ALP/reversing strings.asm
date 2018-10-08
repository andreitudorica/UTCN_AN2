data segment
        string1 db 'good', '$'
        str1_len dw $ - string1
        string2 db ?
data ends

code segment
        assume ds:data, cs:code

begin:
        mov ax, data
        mov ds, ax
        mov es, ax

        sub str1_len, 02h

        lea si, string1
        mov ax, si
        add ax, str1_len
        mov si, ax

        lea di, string2
        mov cx, str1_len
        inc cx

        mov ah, 09h
        lea dx, string1
        int 21h

counter:
        mov ax, [si]
        mov [di], ax
        dec si
        inc di
        loop counter
                
        mov [di], '$'

mov ah, 09h
lea dx, string2
int 21h

mov ax, 4c00h
int 21h

code ends
end begin