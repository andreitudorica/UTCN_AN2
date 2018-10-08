Data Segment
  str1 db 'StRiNg CaSe CoNvErSiOn','$'
  strlen1 dw $-str1
Data Ends

Code Segment
  Assume cs:code, ds:data
  Begin:   
    mov ax, data
    mov ds, ax
    mov es, ax
    mov cx, strlen1
    add cx, -1
    lea bx, str1
    L1:
       mov ah, [bx]
       cmp ah, 41h
       jb L2
       cmp ah, 5Ah
       ja Upper_Check

    Lower_Case:
       XOR ah, 00100000B
       mov [bx], ah

    L2:
       inc bx
       loop L1

    Upper_Check:
       cmp ah, 61h
       ja Upper_Case

    Upper_Case:
       AND ah, 11011111B
       mov [bx], ah
       inc bx
       loop L1

    Print:
       mov ah, 09h
       lea dx, str1
       int 21h
    Exit:
       mov ax, 4c00h
       int 21h
Code Ends
End Begin