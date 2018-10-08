;se da un sir de caractere oarecare. Sa se puna intru sir litere mari in celalat litere mici
data segment 
sir db 'ab!BScT('
len dw $-sir
SIR1 DB 7 DUP(?)
sir2 db 7 dup(?)
data ends
code segment
assume cs:code, ds:data
start: mov ax,data
	   mov ds,ax
	   mov cx,len
	   mov bx,0
	   mov si,0
	   mov di,0
	   mov al,sir[si]
	   mov sir1[di],al
	 et: cmp sir[si], 'A'
		jae mici 
		cmp sir[si], 'a'
		jae mari
		
		jmp gata
			mici: cmp sir[si], 'Z'
				jb alta 
					alta: mov sir1[di],al
					inc di
		mari: cmp sir[si],'z'
				jb alta1
alta1:				
					mov sir2[bx],dl
					inc bx
	  gata: inc si
	   
code ends
end start