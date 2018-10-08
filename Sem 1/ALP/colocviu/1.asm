;Suma unui vector, media
data segment 
sir db 1,2,3
sir2 dw 2 dup( 2 dup(10h))
b dd 1,2,3,4,5,6,7,8,9
len dw $-sir
a db 3
data ends

code segment
assume cs:code, ds:data
start: mov ax,data
	   mov ds,ax
	   
	   mov ax,0
	   mov cx,len
	   mov si,0
	   mov al,0
	   
et:    add al,sir[si]
	   inc si
	   loop et
	   div a
	  
	mov ah,4ch
	int 21h
code ends
end start

MOV CX, 0	
		MOV NUMBER, BX
		
		DATA2 SEGMENT PARA PUBLIC 'DATA'
	ORG 50H
	NUMBER_OF_ELEMENTS		DW			0H
	ZECE					DW			0AH
	ENTER_					DB			0DH
	SPACE 					DB			20H
	INTERMEDIARY			DW			0H
	PREVIOUS				DB			0H
DATA2 ENDS
		
		
	COMPARARE:
		CMP NUMBER, 0H
		JE SCRIERE
		
		MOV DX, 0H
		MOV AX, NUMBER
		MOV DX, 0
		MOV NUMBER, 10
		DIV NUMBER
		MOV NUMBER, AX
		ADD DX, 30H
		PUSH DX
		INC CX
		JMP COMPARARE
		
	SCRIERE:
		POP DX
		MOV AH, 02H
		INT 21h
		LOOP SCRIERE