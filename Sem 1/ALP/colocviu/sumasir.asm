DATA    SEGMENT PARA PUBLIC 'DATA'
sir dw 10,20,30,40,50
suma dw 0

data ends
CODE        SEGMENT PARA PUBLIC 'CODE'
        ASSUME CS:CODE, DS:DATA
START:    MOV AX,DATA
		mov ds,ax
	   mov si,0
	   mov suma,0
   et: mov ax,sir[si]
   add suma,ax
	   
	   inc si
loop et
		
	   
	   
	   
	   
	   
	   mov ah,4ch
	   int 21h
	   code ends
	   end start