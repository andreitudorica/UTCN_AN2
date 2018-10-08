DATA SEGMENT PARA PUBLIC 'DATA'
BUFFER DB 200 DUP(?)
FLAG DB 0
DATA ENDS

CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE, DS:DATA

; read procedure reads a string character by character from the keyboard 

READ PROC FAR
	PUSH 	DS
	XOR 	AX,AX
	PUSH 	AX
	MOV 	AX,DATA
	MOV 	DS,AX
	POP 	AX
	POP 	DS

	XOR 	DI,DI
	
; readloop is the loop for reading the string character by character

READLOOP:
	MOV 	AH,01H
	INT 	21H				; al will get the character
	MOV 	BUFFER[DI],AL	; the character is stored
	INC 	DI
	CMP 	AL,0DH
	JNZ READLOOP			; the reading continues until the ENTER key is pressed

; we display the string

	MOV 	AL,0
	MOV 	BUFFER[DI],24H
	LEA 	DX,BUFFER
	MOV 	AH,09H
	INT 	21H

; converting the string to a real number
	XOR 	SI,SI
	XOR 	DX,DX
	MOV 	DL,BUFFER[SI]
	XOR 	AX,AX
	XOR 	CX,CX
	
; converter loop converts the string to a real number memorizing where the decimal point was

CONVERTER:
		CMP 	DL,2EH		; searching for the decimal point
	JZ FLOATINGPOINT
		CMP 	FLAG,1
	JNZ NEXT2
		INC 	CX			; position of the decimal point stored
	NEXT2:
		SUB 	DL,30H		; deducing the character which digit is
		PUSH 	DX			; storing the value of dx on the stack
		MOV 	BX,10
		MUL 	BX
		POP 	DX
		ADD 	AX,DX		; retrieving the value as an integer in AX
	JMP NEXT
	
FLOATINGPOINT:
		MOV 	FLAG,1
	NEXT:
		INC 	SI
		MOV 	DL,BUFFER[SI]
		CMP 	DL,0DH		; doing the conversion until the ENTER key is pressed
	JNZ CONVERTER
RET
READ ENDP
SAMPLE PROC FAR
RET
SAMPLE ENDP
CODE ENDS
END SAMPLE