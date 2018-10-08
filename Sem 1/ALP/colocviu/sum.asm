data segment
sir db 10,5,20,15
len dw $-sir


data ends
code segment
assume cs:code, ds:data 

start: mov ax,data
mov ds,ax
		 mov ax,0
	   mov cx,len
	   mov si,0
	   mov al,0
		et: add al,sir[si]
		inc si
		loop et
	
		mov ah,4ch
		int 21h

		
code ends
end start
		