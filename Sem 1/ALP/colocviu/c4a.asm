TITLE SUMA SI MEDIA UNUI VECTOR

;IF1
;	INCLUDE C:\Tasm\macro4.mac
;ENDIF

.486

STACK1 SEGMENT PARA STACK 'STACK' use16
	DB		100 DUP('my_stack')
STACK1 ENDS

DATA1 SEGMENT PARA PUBLIC 'DATA' use16

DATA1 ENDS

CODE1 SEGMENT PARA PUBLIC 'CODE' use16
MAIN PROC FAR
	ASSUME CS:CODE1, DS:DATA1, SS:STACK1
	MOV AX, DATA1
	MOV DS, AX
	
	MOV AH, 01H
	INT 21H
	
	MOV AH, 01H
	INT 21H
	
	RET
	MAIN ENDP
	CODE1 ENDS
	END MAIN