Prints macro number
	mov bx, 10		; need to divide by 10 to get the last digit
	mov al, number

	mov cx,0
	decompose:
		mov ah,0	
		inc cx
		div bl			; reminder stored in ah, quotient in al
		mov dl,ah
		mov number,al
		add dl,48		; to convert to char
		push dx
		cmp number,0		;the nr=0 means that the loop ends
		jnz decompose
	
	printloop:	;loops for as many times as counted before, in cx
		pop dx	
		mov ah,2h		; prints whatever is in dl
		int 21h
	loop printloop
	
		mov dl,' '		; the space after a number
		mov ah,2h
		int 21h
	
endm

data segment para public 'data'
	sir DB 15, 78, 12, 39, 42, 88
	lungime_sir EQU $-sir
data ends

;stack segment para public 'stack'
;	dw stack_size dup(?)
;	stack_start label word
;stack ends

code segment para public 'code'
start proc far
	assume cs:code, ds:data
	push ds
	xor ax,ax
	push ax
	mov ax, data
	mov ds, ax
	
	mov si,0
	mov cx,lungime_sir
	
next: 
	cmp sir[si],60
	jb mic
	mov sir[si],2
	inc si
cmp si,lungime_sir
jbe next
	
mic: 
	mov sir[si],1
	inc si
	;dec cx
	cmp si,lungime_sir
jbe next
		

	;mov cx,lungime_sir
	mov si,0
	
	;put values on a stack and pop the out	
print:
	Prints sir[si]
	inc si
	cmp si,lungime_sir
jb print


		ret
start endp
code ends
end start