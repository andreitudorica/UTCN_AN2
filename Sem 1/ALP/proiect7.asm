reads macro x
local r, final
	mov ebx, 10
	r:
		mov ah,1h
		int 21h
		cmp al,0dh
	je final
		sub al,48
		mov aux,eax
		mov eax, x
		mul bx
		add eax, aux
		mov x,eax
	jmp r
	
	final:
endm

prints macro x
local decompose, printloop
	mov eax, x
	mov ebx,10		; to extract last digit
	mov cx,0			; contor
	decompose:
		inc cx			; numara cifrele
		mov edx,0		; initializam cu 0 locul unde vom gasi cifra
		div ebx			; restul (ultima cifra) se gaseste in EDX iar catul in EAX
		add edx, 48
		push edx
		cmp eax,0
	jnz decompose
	printloop:
		pop edx
		mov ah,2h
		int 21h
	loop printloop
	mov dl,' '
	mov ah,2h
	int 21h
		
endm

data segment para public 'data'
	x dd ?
	k dd ?
	aux dd ?
data ends

stack1 segment para stack 'stack'
	dd 1024	dup(?)
stack1 ends

code segment para public 'code'
.386
start proc far
	assume ds:data, cs:code
	push ds
	xor ax,ax
	push ax
	mov ax,data
	mov ds,ax
	
	reads x;
	prints x
	reads k;
	prints k
	ret 
start endp
code ends
end start