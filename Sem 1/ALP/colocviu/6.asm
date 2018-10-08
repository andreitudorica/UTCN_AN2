data segment
data ends
code segment 
assume cs:code, ds:data
start: mov ax,data
	   mov cs,ax
	   
	   
	   
	   
	   
	   mov ah,4ch
	   int 21h
end start 
code ends
