;program care citeste un sir de la tastatura si scoate spatiul si tab-ul
;citim caractere pana la intalnirea lui ENTER (cod ASCII 13)


DATA1 SEGMENT PARA PUBLIC 'DATA'
SIR DB 100 DUP (0)
LEN DB 0
MESAJ1 DB "Dati sirul de caractere ","$";
MESAJ2 DB 13,10,"Sirul modiicat este :","$";
CONT DW ?
TEN DW 10
DATA1 ENDS

CODE1 SEGMENT PARA PUBLIC 'CODE'
START PROC FAR
ASSUME CS:CODE1, DS:DATA1;



PUSH DS
XOR AX,AX
PUSH AX
MOV AX, DATA1
MOV DS,AX
;start program instructions
;afisam mesaj de intampinare
MOV AH , 09H
LEA DX, MESAJ1
INT 21H;
;initializam contorul de carectrere cu 0
XOR SI,SI
XOR CX,CX
;incepem citirea caracterelor
NEXT:
    ;citim un caracter
    MOV AH, 01H
    INT 21H ; caracterul citit va fi in AL
    CMP AL,0DH
    JZ SFARSIT; daca e ENTER nu mai citim	
    CMP AL,20H ;daca e spatiu citim caracterul urmator
	JZ NEXT2
	CMP AL, 09H;daca e TAB citim caracterul urmator
	JZ NEXT
	;altfel il adugam in sirul nostru de caractere
	MOV SIR[SI], AL;
	INC SI
	JMP NEXT;
NEXT2:
INC CX
JMP NEXT
SFARSIT:
;afisam mesaj
MOV AX,CX
DIVIDE:
	XOR DX,DX
	DIV TEN
	PUSH DX
	INC CONT
	CMP AX,0
	JNE DIVIDE
MOV AH,02H
PRINT:
	POP DX
	ADD DL,48
	INT 21H
	DEC CONT
	CMP CONT,0
	JNE PRINT
	

MOV AH,09H
LEA DX, MESAJ2
INT 21H
;afisam sirul de caratere salvat in SIR
CMP SI,0
JZ SFPROG ; daca nu avem nimic de afisat nu afisam
MOV CX,SI
XOR SI,SI; din nou 0
BUCLA:
MOV AH, 02H
MOV DL,SIR[SI]
INT 21H
INC SI
LOOP BUCLA
SFPROG:
;program ends
MOV AH, 4CH
INT 21H

START ENDP
CODE1 ENDS
END START
