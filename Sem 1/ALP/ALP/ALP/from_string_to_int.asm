
;------------------------------------------------------------ COPY FROM HERE
DATA SEGMENT PARA PUBLIC 'DATA'
BUFFERSIZE DB 4 ; 20 CHAR + RETURN
INPUTLENGTH DB 0 ;ACTUAL INPUT ,NR OF READ CHARS
BUFFER DB 4 DUP(0); ACTUAL BUFFER
TEN DW 10
DATA ENDS
CODE SEGMENT PARA PUBLIC 'CODE'
ASSUME CS:CODE, DS:DATA
START PROC FAR
 PUSH DS
 XOR AX,AX
 PUSH AX
MOV AX,DATA
MOV DS,AX

LEA DX, BUFFERSIZE
MOV AH,0AH
INT 21H

XOR AX,AX
XOR BX,BX
MUL TEN
ADD AL,BUFFER[0]
SUB AX,48
MUL TEN
ADD AL,BUFFER[1]
SUB AX,48
;ADD AX,BX


;OTHER PROGRAM INSTRUCTIONS
RET
START ENDP
CODE ENDS
END START