DATA SEGMENT PARA PUBLIC 'DATA'
SIR1 DB 30 DUP (?)
LEN1 DW 0
SIR2 DB 30 DUP (?)
LEN2 DW 0
SIR3 DB 60 DUP (?)
LEN3 DW 0
MESAJ1 DB 'Dati lungimea primului sir: $'
MESAJ2 DB 'Dati lungimea celui de-al doilea sir: $'
MESAJ3 DB 'nr=$'
MESAJ4 DB ' $'
MESAJ5 DB 'Sirul interclasat este: $'
DATA ENDS

EXTRN READ: FAR

IF 1
	INCLUDE E:/print.mac
ENDIF

CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE,DS:DATA
MAIN PROC FAR
	PUSH DS
	MOV AX,0
	PUSH AX
	MOV AX,DATA
	MOV DS,AX
	
	MOV AH,09H
	LEA DX,MESAJ1
	INT 21H
	LEA BX,LEN1
	CALL READ
	MOV SI,0
	CMP SI,LEN1
	JGE P2
P1: MOV AH,09h
	LEA DX,MESAJ3
	INT 21H
	LEA BX,SIR1[SI]
	CALL READ
	INC SI
	CMP SI,LEN1
	JL P1

P2: MOV AH,09H
	LEA DX,MESAJ2
	INT 21H
	LEA BX,LEN2
	CALL READ
	MOV SI,0
	CMP SI,LEN2
	JGE P4
P3: MOV AH,09h
	LEA DX,MESAJ3
	INT 21H
	LEA BX,SIR2[SI]
	CALL READ
	INC SI
	CMP SI,LEN2
	JL P3

P4:	MOV AX,LEN1
	ADD AX,LEN2
	MOV LEN3,AX
	MOV SI,0
	MOV DI,0
	MOV BP,0
	
C1:	MOV AL,SIR1[SI]
	CMP AL,SIR2[DI]
	JG C2
	MOV AL,SIR1[SI]
	MOV SIR3[BP],AL
	INC SI
	INC BP
	CMP SI,LEN1
	JE C3
	JMP C1
C2: MOV AL,SIR2[DI]
	MOV SIR3[BP],AL
	INC DI
	INC BP
	CMP DI,LEN2
	JE C4
	JMP C1
	
C3: MOV AL,SIR2[DI]
	MOV SIR3[BP],AL
	INC DI
	INC BP
	CMP BP,LEN3
	JL C3
	JMP C5
C4: MOV AL,SIR1[SI]
	MOV SIR3[BP],AL
	INC SI
	INC BP
	CMP BP,LEN3
	JL C4
	JMP C5
	
C5:	MOV AH,09h
	LEA DX,MESAJ5
	INT 21H
	MOV SI,0
C6:	CMP SI,LEN3
	JGE FINISH
	PUSH SI
	PRINT SIR3[SI],MESAJ4,MESAJ4
	POP SI
	INC SI
	JMP C6
	
FINISH:
RET
MAIN ENDP
CODE ENDS
END MAIN