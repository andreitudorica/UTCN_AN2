DATA	SEGMENT PARA PUBLIC 'DATA'
;... ... definire zona de date
rez db 0
num1 db 5
num2 db 2


DATA	ENDS
CODE		SEGMENT PARA PUBLIC 'CODE'
		ASSUME CS:CODE, DS:DATA
START:	MOV AX,DATA
		MOV DS,AX;Initializarea segmentului DS
		mov al,14
		mov bl,22
		mov al,cl
		mul num2
		add al,bl
		mov rez,al
		mov al,cl
		mul num1
		add rez,al
		
;-------------------------------------------------
;... ... instructiunile programului principal
;-------------------------------------------------
		MOV AH, 4CH	;terminarea programului 
		INT 21H
CODE		ENDS
END START
