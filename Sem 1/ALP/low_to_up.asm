; ENUNT PROBLEMA: citire text de max 100 caractere de la stdin, si
; convertirea textului in lowercase
; afisarea textului si a numarului de cuvinte citite

DATA SEGMENT PARA PUBLIC 'DATA'
SIR DB 100 DUP (?)
newline DB 10,13,'$'
DATA ENDS

CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE, DS:DATA, ES:DATA

; procedura de conversie
fctie PROC FAR
; IF AL<'A'
	cmp 	al, 'A'
JB iesire
; IF AL>'Z'
	cmp 	al, 'Z'
JA iesire
	ADD 	AL, 20H

iesire: RET
fctie ENDP

START PROC FAR
	PUSH 	DS
	XOR 	AX,AX
	PUSH 	AX
	MOV 	AX,DATA
	MOV 	DS,AX
	MOV 	ES,AX

;inceput START

;initializari
	MOV 	BL, 0
	MOV 	DI, OFFSET SIR
	cld

;citirea unui caracter din sir
L1:
	MOV 	AH, 01H
	INT 	21H

;vf dc e caracterul space
	CMP 	AL, ' '
JE increment

	CMP 	AL, '$'
JE auxL2

CALL fctie
; ca sa evitam intrarea automata in increment 
JMP auxL
increment: INC BL
auxL:

;rescrierea caracterului in lowercase
;-instructions equiv to stosb
;MOV ES:[DI], AL 
;INC DI
STOSB

auxL2:
;vf. dc e sf. de sir
	CMP 	AL, '$' 
LOOPNE L1

; linie noua
	LEA 	DX, newline
	MOV 	AH, 09H
	INT 	21H

;afisare 
	mov 	DX, offset SIR
	MOV 	AH, 09H
	INT 	21H

; afisare nr de cuvinte
	ADD 	BL, 01
	MOV 	DL, BL
	MOV 	AH, 02H
	ADD 	DL, 30h; convertire in string pentru ca nr sa afiseze: 30H e val lui 0 hexa in ASCII
	INT		21H

;iesire program
	MOV 	AH, 4CH
	INT 	21H
START ENDP
CODE ENDS
END START