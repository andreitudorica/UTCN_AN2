prints macro number
	mov bl,10
	mov cx,0
	mov al,number
decompose:		;decomposing into digits pushed on the stack
	inc cx
	mov ah,0
	div bl
	mov dl,ah		;remainder = digit
	mov dh,0
	add dl,48		;to convert it to it's character value()
	push dx
	cmp al,0
jnz decompose
print:
	pop dx
	mov ah,2h
	int 21h
loop print
	mov dl,' '
	mov ah,2h
	int 21h
	
endm

data segment para public 'data'
	k db 2
	sir db 100, 10, 200, 5, 150
	lungime_sir equ $-sir
data ends

stack1 segment para public 'stack'
	dw 1024 dup(?)
stack1 ends

code segment para public 'code'
start proc far
	assume cs:code,ds:data
	;push ds
    xor ax,ax
    ;push ax
    mov ax,data
    mov ds,ax
	
	mov ax,0
	mov al,k
	mov si,ax
l1:
	mov al,255
	sub al,sir[si]
	mov sir[si],al
	inc si
	cmp si,lungime_sir
jb l1
	mov si,0
l2:
	prints sir[si]
	inc si
	cmp si,lungime_sir
jb l2
	
	mov ah,4ch
	int 21h
	ret
start endp
code ends
end start