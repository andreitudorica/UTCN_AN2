DATA SEGMENT PARA PUBLIC 'DATA'

DATA ENDS
CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE, DS:DATA

START PROC FAR
PUSH DS
XOR AX,AX
PUSH AX
MOV AX,DATA
MOV DS,AX

	mov ah,9
	mov cx,hello_world
	int 21h
	
	mov ah, 4ch
	int 21h

RET

hello_world:
	db 'Hello!' , 0aH
START ENDP
CODE ENDS
END START
