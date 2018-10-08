;sa se verifice daca 2 siruri sunt egale si daca da sa se afiseze a intru registru
data segment
a db 1 dup(?)
sir1 db 2,4,-5,1
len dw $-sir1
sir2 db 2,4,-5,3
len1 dw $-sir2 

data ends
code segment 
	assume cs:code,ds:data
start: mov ax,data
	   mov ds,ax
	   mov si,0
	   mov cx,len
	   mov di,0
	  
	 
	   
		et1: 
		mov al,sir1[si]
		cmp al,sir2[si]
			jne et 
			inc si		
		loop et1
		mov a,1
		jmp et2
		et: mov a,0
		et2:
				
				mov ah,4ch
				int 21h
code ends
end start