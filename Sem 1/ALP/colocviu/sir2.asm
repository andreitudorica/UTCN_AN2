;sir cu litere mici si mari ,selecteaza in 2 siruri 
;diferite,unul cu litere mari si unul cu litere mici

DATA	SEGMENT PARA PUBLIC 'DATA'
sir db '123asdWER*/klOPBn*/-+'
len dw $-sir
SIR1 DB 8 DUP(?)
SIR2 DB 8 DUP(?)

DATA	ENDS
CODE	SEGMENT PARA PUBLIC 'CODE'
		ASSUME CS:CODE, DS:DATA
START:	MOV AX,DATA
		MOV DS,AX;Initializarea segmentului DS
		
		mov cx,len
		MOV SI,0
		MOV DI,0
		MOV BX,0
			
		ETH:
			CMP SIR[SI],'A'
			JB GATA
			CMP SIR[SI],'Z'
			JA ET1
			MOV AL,SIR[SI]
			MOV SIR1[DI],AL
			INC DI
			JMP GATA
		ET1:
			CMP SIR[SI],'a'
			JB GATA
			CMP SIR[SI],'z'
			JA GATA
			MOV AL,SIR[SI]
			MOV SIR2[BX],AL
			INC BX
		GATA:
			INC SI
		LOOP ETH

		MOV AH, 4CH	;terminarea programului 
		INT 21H
CODE		ENDS
END START
