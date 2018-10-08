data segment 
nr dw 0
data ends
code segment 
assume cs:code, ds:data
start: mov ax,data
    mov ds,ax
    mov ax,0
    mov dx, 0
citire: mov ah,01h
    int 21h
    cmp al,13
    je sfarsit
    sub al,'0'
    mov ah,0
    push ax
    mov bx,10
    mov ax, dx
    mov dx,0
    mul bx
    mov dx,ax
    pop ax
    add dx,ax
    jmp citire
sfarsit:
    mov nr,dx
    mov ah, 4ch
int 21h
code ends
end start