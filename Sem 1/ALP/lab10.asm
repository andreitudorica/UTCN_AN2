DATA SEGMENT PARA PUBLIC 'DATA'
	COLOR DB ?
	X DW ?
	Y DW ?
DATA ENDS

CODE SEGMENT PARA PUBLIC 'CODE'
	ASSUME CS:CODE, ES:DATA
	
	DSQ PROC NEAR
		
		MOV 	AX, X
		MOV 	BX, 130H
		MUL 	BX
		ADD 	AX, Y
		MOV 	BX, AX
		
		MOV 	CX, 20
	L1:
		PUSH 	CX
		MOV 	CX, 20
	L2:
		MOV 	AL, COLOR
		MOV 	DS:[BX], AL
		INC 	BX
	LOOP L2
		
		POP 	CX
		ADD 	BX, 300
	LOOP L1
		
	RET
		
	DSQ ENDP
	
	START PROC FAR
		PUSH 	DS
		XOR 	AX,AX
		PUSH 	AX
		mov 	ah,00h
		mov 	al,13h
		int 	10h 
		Mov 	dx,40960	;draw pixel directly direct into video memory
		mov 	ds, dx
		
		MOV 	AX, DATA
		MOV 	ES, AX
		
		MOV 	X, 10
		MOV 	Y, 20
		MOV 	COLOR, 2
		
		CALL DSQ
		
		MOV 	X, 100
		MOV 	Y, 37
		MOV 	COLOR, 3
		
		CALL DSQ
		
		MOV 	X, 300
		MOV 	Y, 57
		MOV 	COLOR, 4
		
		CALL DSQ
		
		MOV 	AH,01H
        INT 	21H
		RET
	START ENDP
CODE ENDS
END START