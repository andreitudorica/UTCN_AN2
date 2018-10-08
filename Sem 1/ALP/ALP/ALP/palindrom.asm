; Programul verifica daca un numar sau sir de caractere este palindrom  
dosseg 
.model small 
.stack .data  
nr DB 10,10 dup(0) 
mesaj  db 13,10,'Introduceti numarul:$' 
mesaj_nu db 13,10,'Numarul nu este palindrom!$' 
mesaj_da db 13,10,'Numarul este palindrom!$' 
.code 
pstart: 
        mov ax,@data 
        mov ds,ax 
         
        mov ah,09 
 mov dx,offset mesaj 
        int 21h  
    
 mov ah,0ah  
 mov dx,offset nr 
 int 21h 
   
 mov si,1 
 mov cl,nr[si]  ; incarc in CL numarul de cifre al numarului introdus 
 and cx,00FFh 
   
 mov ax,cx 
 mov bl,2 
 div bl   ; in AL este catul impartirii lui AX la 2 
 and ax,00FFh 
 inc ax 
 inc cx 
 mov di,cx 
   
urmatorul_caracter:  
 inc si  ; SI creste de la inceputul sirului spre mijloc 
 mov bl,nr[di] 
 cmp nr[si],bl 
 jne nu_este 
 dec di  ; DI scade de la sfarsitul sirului spre mijloc 
 cmp si,ax ; in sir se va merge pana la pozitia cl+1 
 jne urmatorul_caracter 
 mov ah,9  mov dx,offset mesaj_da 
 int 21h 
 jmp sfarsit 
nu_este:  
 mov ah,9 
 mov dx,offset mesaj_nu 
 int 21h 
sfarsit: 
 mov ah,4ch 
 int 21h ; stop program 
END pstart 


