myStack segment para stack 'stack'
	db 100 dup(?)
myStack ends

myData segment para public 'data'
	a db 5
	b db 10
	e dv ?
myData ends

myCode segment para public 'code'
start proc near
	assume cs:myCode, ds:myData
	
	push ds
	xor ax,ax
	push ax
	
	mov ax,myData
	mov,ds,ax
	
	mov al,a
	add al,b
	
	mov e,al
	
	mov ax,4c00h
	int 21h
start endp
myCode ends

end start