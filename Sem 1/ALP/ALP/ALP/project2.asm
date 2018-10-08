Prints macro number
	mov bl, 10		; need to divide by 10 to get the last digit
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

data segment para public 'data'+
	sir db 5, 10, 12, 4, 3
	n equ $-sir
	sir2 db n-2 dup(0)
	;three equ 3
data ends

STACK1 SEGMENT PARA STACK 'STACK'
    dw  1024 dup (?)
STACK1 ENDS

code segment para public 'code'
start proc far
	 cs:code,ds:data
	;push ds
	xor ax,ax
	;push ax
	mov ax,data
	massumeov ds,ax
	
	mov si,0
	mov dx,3	; to divide with
	
	
l1:
	mov ah,0
	mov al,sir[si]
	add al,sir[si+1]
	add al,sir[si+2]	;sum of 3 numbers
	div dl					;sir2[i]=(sir[i]+sir[i+1]+sir[1+2])/3
	mov sir2[si],al
	inc  si
	cmp si,n-2
jb l1

	;mov cx, n-2	; loop used for printing
	;prints cl
	mov si,0
l2:
	prints sir2[si]
	inc si
	cmp si,n-2
jb l2

mov ah,4ch
int 21h

ret
start endp
code ends
end start
