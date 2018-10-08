; sa se verifice daca elem din 2 siruri sunt egale si daca da sa se mute in al 3 sir
data segment
sir1 db 2,4,-5,4,8,9
len dw $-sir1
sir2 db 2,4,4,-5,12,9
len1 dw $-sir2 
sir3 db 10 dup(?)
data ends
code segment 
	assume cs:code,ds:data
start: mov ax,data
	   mov ds,ax
	   mov si,0
	   mov cx,len
	   mov di,0
	   mov bx,0
	   
		et1: mov al,sir1[si]
		cmp al,sir2[di]
			jne et
			mov dl,sir1[si]
			mov sir3[bx],dl
			inc bx 
			et: inc si
			    inc di
				loop et1
				mov ah,4ch
				int 21h
code ends
end start