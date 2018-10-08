reads macro x
local readloop,create,ready
	mov dl,10
	readloop:
		mov ah,1h		;citire
		int 21h
		cmp al, 0dh	; enter ?
		je ready
	create:
		sub al, 48
		mov aux, al 	; punem cifra in aux
		mov al, x		; pregatim pentru x10
		mul dl			; X x 10
		add al, aux		; 	X x 10 + cifra
		mov x, al		;X = X x 10 + cifra
	jmp readloop
		
	ready:
endm

prints macro number
	local decompose, printloop
	mov bl,10
	mov bh,0
	mov cx,0
	mov al,number
	decompose:
		mov ah,0
		inc cx
		div bl
		mov dh,0
		add ah,48
		mov dl,ah
		push dx
		cmp al,0
	jnz decompose
	printloop:
		pop dx
		mov ah,2h
		int 21h
	loop printloop
		mov dx,' '
		mov ah,2h
		int 21h
endm


data segment para public 'data'
	x  db 29
	aux db ?
	k db 5
data ends

code segment para public 'code'
start proc far
	assume cs:code, ds:data
	push ds
	xor ax,ax
	push ax
	mov ax,data
	mov ds,ax
	
	;aici voi citi
	;reads x;cl
	;mov x,cl
	prints x
	;reads k
	prints k
	
	mov ax, 0
go:
	mov al, x
	add al,k
	cmp al,31
	jbe done
over:
	sub al,31
	mov k,al
	mov x,21
	cmp k,0
jnz go
done:
	mov x, al
	prints x
	ret
start endp

;read proc near
;	
;ret
;read endp

code ends
end start
