
;------------------------------------------------------------ COPY FROM HERE
DATA SEGMENT PARA PUBLIC 'DATA'
MESAJ1 DB "INTRODU SIRUL:","$"
PAR_COUNT DW 0
IMPAR_COUNT DW 0
SIR DB 100 DUP (0)
TEN DW 10
ACTUALSIZE DW ?
DATA ENDS
CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE, DS:DATA

ReadSir proc
XOR SI,SI
;MOV AH , 01H
BUCLA:
	MOV AH , 01H
	INT 21H
	CMP AL , 0DH
	JE FINAL
	CMP AL , 20H
	JE NEXT
	MOV CL , AL
	MOV AX , BX
	MUL TEN
	ADD AL , CL
	MOV BX , AX
	SUB BX , 48
	JMP BUCLA
NEXT:
	MOV SIR[SI] , BL
	INC SI
	XOR BX , BX
	JMP BUCLA
FINAL:
	MOV SIR[SI] , BL
	INC SI
	MOV ACTUALSIZE , SI
ret
ReadSir endp

START PROC FAR
 PUSH DS
 XOR AX,AX
 PUSH AX
 MOV AX,DATA
 MOV DS,AX
 
 LEA DX , MESAJ1
 MOV AH , 09H
 INT 21H
 
 CALL ReadSir
 MOV AX, ACTUALSIZE

;OTHER PROGRAM INSTRUCTIONS
RET
START ENDP
CODE ENDS
END START