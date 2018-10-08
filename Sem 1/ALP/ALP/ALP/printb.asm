
DATA SEGMENT PARA PUBLIC 'DATA' 
	TEN DB 10
DATA ENDS 

STCK SEGMENT PARA STACK 'stack' use16
	DB 64 DUP ('my_stack')	; reserve 256 bytes for stack
STCK ENDS

CODE3 SEGMENT PARA PUBLIC 'CODE' 
PUBLIC printb
ASSUME CS:CODE3

PRINT_DIGIT_PROC PROC
	ADD DL, 30H
	MOV AH, 02H
	INT 21H
	RET
PRINT_DIGIT_PROC ENDP	

PRINTB  PROC FAR 
	XOR CX, CX
	MOV BL,10
	
	GET_DIGITS:
		CMP AX,0H
		JE PRINT_NUMBER
		DIV BL
		MOV DL, AH
		PUSH DX
		MOV AH, 0
		INC CL
		JMP GET_DIGITS
	
	PRINT_NUMBER:
		POP DX
		CALL PRINT_DIGIT_PROC
	LOOP PRINT_NUMBER
    RET   ; FAR return to DOS 
PRINTB  ENDP 
CODE3 ENDS 
END  