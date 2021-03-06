DATA SEGMENT PARA PUBLIC 'DATA' 
	STR1 DB 'DOOG', '$'
	LEN DW $-STR1
	NP DB 'STRING NOT PALINDROME','$'
	P DB 'STRING PALINDROME','$'
DATA ENDS

CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE, DS:DATA
START PROC FAR
	PUSH DS
	XOR AX,AX
	PUSH AX
	MOV AX,DATA
	MOV DS,AX
	MOV SI,0
	MOV DI,LEN
	SUB DI,2
	MOV CX,LEN
	SHR CX,1
	
	L:
		MOV AH,STR1[SI]
		MOV AL,STR1[DI]
		CMP AH,AL
		JNE NOT_PAL
		INC SI
		DEC DI
	LOOP L
	JMP PAL
	NOT_PAL: 
		LEA DX,NP
		MOV AH,09H
		INT 21H
		RET
	PAL:
		LEA DX,P
		MOV AH,09H
		INT 21H
		RET
START ENDP
CODE ENDS
END START