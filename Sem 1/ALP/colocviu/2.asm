;separare siruri

data segment 
sir db 1,2,-3,-5,2,4
len dw $-sir
SIR1 DB 6 DUP(?)
sir2 db 6 dup(?)
data ends
code segment
assume cs:code, ds:data
start: mov ax,data
	   mov ds,ax
	   mov si,0
	   mov di,0
	   mov bx,0
	   
	   
	   mov cx,len
	et: cmp sir[si],0
		jl negativ
		mov al,sir[si]
		mov sir2[bx],al
		inc bx
		jmp gata
		negativ: mov dl,sir[si]
				 mov sir1[di],dl
				 inc di
	    gata :
			inc si
			loop et
	   
	   
	   	mov ah,4ch
		int 21h
code ends
end start