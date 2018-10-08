;nr pare in alt sir
DATA	SEGMENT PARA PUBLIC 'DATA'
sir dw 10,13,50,67
LEN DW $-sir
sir2 dw 4 dup(?)

DATA	ENDS
CODE		SEGMENT PARA PUBLIC 'CODE'
		ASSUME CS:CODE, DS:DATA
START:	MOV AX,DATA
		MOV DS,AX;Initializarea segmentului DS		
		mov cx,len
		mov si,0
		mov di,0
		mov bl,2
	et:	mov ax,sir[si]
		div bl
		cmp ah,0
		jne et1
		mov dx,sir[si]
		mov sir2[di],dx
		add di,2
		et1:add si,2
		loop et
		
		
		MOV AH, 4CH	;terminarea programului 
		INT 21H
CODE		ENDS
END START