/*
The following restrictions apply to the values certain fields in a SF file could
take:
• The MAGIC’s value is “iS”.
• VERSION’s value must be between 44 and 163, including that values.
• The NO OF SECTIONS’s value must be between 4 and 15, including that
values.
• Valid SECT TYPE’s values are: 37 74 97 11 16 53 93 .
• Section lines are separated by the following sequence of line-ending bytes
(values are hexadecimal): 0A.
*/
#include<stdio.h>
#include<dirent.h>
#include<string.h>
#include<stdlib.h>

int main(int argc,char **argv)
{
	if(argc==2 && strcmp(argv[1],"variant")==0)
		printf("42526\n");
	return 0;
}