
; Programul calculeaza suma cifrelor unui numar introdus de la tastatura 
  
dosseg 
.model small 
.stack 
.data  
nr DB 10,10 dup(?) 
rezultat DB 10,10 dup(?) 
mesaj  db 13,10,'Introduceti numarul:$' 
mesaj_suma db 13,10,'Suma cifrelor numarului este: $' 
.code 
pstart: 
        mov ax,@data 
        mov ds,ax 
         
        mov ah,09  ; aici se afiseaza mesajul initial de introducere 
        mov dx,offset mesaj ; a numarului 
        int 21h  
    mov ah,0ah  ; functia 10(0ah) citeste un sir de caractere de la       
; tastatura intr-o variabila de memorie 
 mov dx,offset nr 
 int 21h 
   
 mov si,1 
 mov cl,nr[si]  ; incarc in CL numarul de cifre al numarului introdus 
 and cx,00FFh 
 inc cx  ; CX stocheaza acum ultima pozitie din sirul de cifre 
 xor ax,ax ; stocam rezultatul in AX, pe care il initializam cu zero 
     
urmatorul_caracter:  
 inc si  ; SI creste de la inceputul sirului spre sfarsit 
 add al,nr[si] 
   
 sub al,30h ; scadem codul ASCII al lui zero 
 cmp si,cx ; in sir se va merge pana la pozitia cl+1 
 jne urmatorul_caracter 
 xor si,si ; SI este indicele din sirul care va contine rezultatul 
cifra: ; aici incepe afisarea rezultatului din AX 
   
 mov bx,0ah 
 div bx 
 add dl,30h 
 mov rezultat[si],dl 
 inc si 
 xor dx,dx 
 cmp ax,0 
 jne cifra 
   
 mov ah,9 
 mov dx,offset mesaj_suma 
 int 21h 
caracter: 
 dec si 
 mov ah,02   ;apelarea functiei 02 pentru afisarea unui caracter 
 mov dl,rezultat[si]  ;al carui cod ASCII este in DL 
 int 21h 
 cmp si,0  jne caracter 
   
 mov ah,4ch 
 int 21h   ; terminarea programului 
END pstart