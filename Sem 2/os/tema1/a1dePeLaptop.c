#include<stdio.h>
#include<dirent.h>
#include<string.h>
#include<unistd.h>
#include<stdlib.h>
#include<fcntl.h>
#include<sys/types.h>
#include<sys/stat.h>
void list(const char *path,int recursive,int name_starts_with, const char *starts_with,int has_perm_write,int lvl)
{
	DIR *directory=opendir(path);
	struct dirent *d;
	if(directory!=NULL)
	{
		if(!lvl) printf("SUCCESS\n");
		d=readdir(directory);
		while(d!=NULL)
		{
			if(strcmp(d->d_name,".") && strcmp(d->d_name,".."))
			{
				char *file_path=(char*)malloc((strlen(path)+1)*sizeof(char)+sizeof(char)+(strlen(d->d_name)+1)*sizeof(char));
				sprintf(file_path,"%s/%s",path,d->d_name);
				if(recursive) list(file_path , recursive , name_starts_with , starts_with , has_perm_write , 1);
				if((!name_starts_with && !has_perm_write) || ((name_starts_with  && !strncmp(d->d_name,starts_with,strlen(starts_with)))  && !has_perm_write) || (!name_starts_with && has_perm_write && access(file_path,W_OK)==0)) printf("%s\n",file_path);
				free(file_path);
			}
			d=readdir(directory);
		}
	}
	else if(!lvl) printf("ERROR\ninvalid directory path\n");
	closedir(directory);
}
void parse(const char *path)
{
	char section_names[16][13];
	char magic[2];
	int section_types[16],section_offsets[16],section_sizes[16],errors=0,wmagic=0,wversion=0,wsection_count=0,wsection_types=0,hsize=0,version=0;
	short int section_count=0;
	int file=open(path , O_RDONLY);
	for(int i=0;i<16;i++)
		for(int j=0;j<13;j++)
			section_names[i][j]='\0';
	read(file,&magic,2);
	if(strcmp(magic,"iS")) errors=wmagic=1;
	read(file,&hsize,2);
	read(file,&version,2);
	if(version<44 || version>163) errors=wversion=1;
	read(file,&section_count,1);
	if(section_count<4 || section_count>15) errors=wsection_count=1;
	for(int i=0;i<section_count && !wsection_count && !wsection_types;i++)
	{
		read(file,&section_names[i],12);
		read(file,&section_types[i],4);
		read(file,&section_offsets[i],4);
		read(file,&section_sizes[i],4);
		if(section_types[i]!=37 && section_types[i]!=74 && section_types[i]!=97 && section_types[i]!=11 && section_types[i]!=16 && section_types[i]!=53 && section_types[i]!=93) errors=wsection_types=1;
	}
	if(errors)
	{
		if(wmagic) printf("ERROR\nwrong magic");
		else if(wversion) printf("ERROR\nwrong version");
		else if(wsection_count) printf("ERROR\nwrong sect_nr");
		else if(wsection_types) printf("ERROR\nwrong sect_types");
	}
	else
	{
		printf("SUCCESS\nversion=%d\nnr_sections=%d\n",version,section_count);
		for(int i=0;i<section_count;i++)
			printf("section%d: %s %d %d\n",i+1,section_names[i],section_types[i],section_sizes[i]);
	}
}
void extract(const char *path,int section, int line)
{
	int file=open(path,O_RDONLY);
	if(file==-1) {printf("ERROR\ninvalid file");return;}
	lseek(file,6,SEEK_SET);
	int sc=0,offs=0,size=0,lc=1;
	read(file,&sc,1);
	if(sc<section) {printf("ERROR\ninvalid section");return;}
	lseek(file,(section-1)*(12+4+4+4)+(12+4),SEEK_CUR);
	read(file,&offs,4);
	read(file,&size,4);
	lseek(file,offs,SEEK_SET);
	int i=0,al=0;
	char el;
	char *txt=(char*)malloc(size);
	for(i=0;i<size;i++)
	{
		read(file,&el,1);
		if(el=='\n') lc++;
		txt[al++]=el;
	}
	if(line>lc) {printf("ERROR\ninvalid line");return;}
	lseek(file,offs,SEEK_SET);
	printf("SUCCESS\n");
	int dl=1;
	while(dl<line)
	{
		if(txt[al]=='\n') dl++;
		al--;
	}
	while(txt[al]!='\n')
	{
		printf("%c", txt[al]);
		al--;
	}
	free(txt);
}
int isok(const char *path)
{
	char section_names[16][13];
	char magic[2];
	int section_types[16],section_offsets[16],section_sizes[16],errors=0,wmagic=0,wversion=0,wsection_count=0,wsection_types=0,hsize=0,version=0;
	short int section_count=0;
	int file=open(path , O_RDONLY);
	for(int i=0;i<16;i++)
		for(int j=0;j<13;j++)
			section_names[i][j]='\0';
	read(file,&magic,2);
	if(strcmp(magic,"iS")) errors=wmagic=1;
	read(file,&hsize,2);
	read(file,&version,2);
	if(version<44 || version>163) errors=wversion=1;
	read(file,&section_count,1);
	if(section_count<4 || section_count>15) errors=wsection_count=1;
	for(int i=0;i<section_count && !wsection_count && !wsection_types;i++)
	{
		read(file,&section_names[i],12);
		read(file,&section_types[i],4);
		read(file,&section_offsets[i],4);
		read(file,&section_sizes[i],4);
		if(section_types[i]!=37 && section_types[i]!=74 && section_types[i]!=97 && section_types[i]!=11 && section_types[i]!=16 && section_types[i]!=53 && section_types[i]!=93) errors=wsection_types=1;
	}
	if(errors)
		return 0;
	for(int i=0;i<section_count;i++)
	{
		lseek(file,section_offsets[i],SEEK_SET);
		int  s=0,cnt=1;
		char c;
		while(s<section_sizes[i])
		{
			read(file,&c,1);
			s++;
			if(c=='\n')
				cnt++;
		}
		if(cnt>15)
			return 1;
	}
	return 0;
}


void findall(const char *path,int lvl)
{
	DIR *directory=opendir(path);
	struct dirent *d;
	if(directory!=NULL)
	{
		if(!lvl) printf("SUCCESS\n");
		d=readdir(directory);
		while(d!=NULL)
		{
			if(strcmp(d->d_name,".") && strcmp(d->d_name,".."))
			{
				char *file_path=(char*)malloc((strlen(path)+1)*sizeof(char)+sizeof(char)+(strlen(d->d_name)+1)*sizeof(char));
				sprintf(file_path,"%s/%s",path,d->d_name);
				findall(file_path , 1);
				if(isok(file_path))
					printf("%s\n",file_path);
				free(file_path);
			}
			d=readdir(directory);
		}
	}
	else if(!lvl) printf("ERROR\ninvalid directory path\n");
	closedir(directory);
}

int main(int argc,char **argv)
{
	if(argc>=2)
	{
		int op=0;
		char *path,*section,*line,*starts_with;
		int recursive=0,name_starts_with=0,has_perm_write=0;
		if(strcmp(argv[1],"variant")==0)
		{	
			printf("42526\n");
			return 0;
		}
		for(int i=1;i<argc;i++)
		{
			if(strstr(argv[i],"path="))
			{
				path = (char*)malloc( (strlen(argv[i])-4) * sizeof(char));
				strcpy(path,argv[i]+5);
			}
			if(strstr(argv[i],"line="))
			{
				line = (char*)malloc( (strlen(argv[i])-4) * sizeof(char));
				strcpy(line,argv[i]+5);
			}
			if(strstr(argv[i],"section="))
			{
				section = (char*)malloc( (strlen(argv[i])-7) * sizeof(char));
				strcpy(section,argv[i]+8);
			}
			if(strstr(argv[i],"name_starts_with="))
			{
				name_starts_with=1;
				starts_with = (char*)malloc( (strlen(argv[i])-16) * sizeof(char));
				strcpy(starts_with,argv[i]+17);
			}
			if(strstr(argv[i],"list")) op=1;
			if(strstr(argv[i],"parse")) op=2;
			if(strstr(argv[i],"extract")) op=3;
			if(strstr(argv[i],"findall")) op=4;
			if(strstr(argv[i],"recursive")) recursive=1;
			if(strstr(argv[i],"has_perm_write")) has_perm_write=1;
		}
		if(op==1) list(path , recursive , name_starts_with , starts_with , has_perm_write , 0);
		if(op==2) parse(path);

		if(op==3) extract(path,atoi(section),atoi(line));
		if(op==4) findall(path,0);
		free(path);
		free(line);
		free(section);
		free(starts_with);
	}
	return 0;
}
