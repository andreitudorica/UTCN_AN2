INIT MACRO
	PUSH DS
	XOR AX,AX 
	PUSH AX 
	MOV AX,DATA
	MOV DS,AX
	XOR BX,BX
	XOR SI,SI
	XOR AX,AX
ENDM

READ_ARRAY MACRO
	LREAD:
		MOV AH,01H
		INT 21H
		CMP AL,0DH
		JE EXIT_LOOP_READ
		MOV STR1[LEN],AL
		INC LEN
		JMP LREAD
	EXIT_LOOP_READ
ENDM 

PRINT_ARRAY MACRO
	MOV CX,LEN
	LPRINT:
		MOV AH,02H
		MOV BX,LEN
		SUB BX,CX
		MOV DX,STR1[BX]
		INT 21H
		LOOP LPRINT
ENDM