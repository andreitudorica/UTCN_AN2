#include <stdio.h>
#include <unistd.h>
#include <dirent.h>
#include <stdlib.h>
#include <pthread.h>
#include <fcntl.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
int req, resp, variant = 42526, messageSize, byteCount, shmId, fileNameLength, fileLength, fileMapped = 0, memoryRegionOpened = 0, readFromFileOffset, readFromFileOffsetNoOfBytes;
unsigned int offset, value;
short connectSize = 7, errorSize = 5, create_shmSize = 10, successSize = 7, write_to_shmSize = 12, map_fileSize = 8, read_from_file_offsetSize = 21, read_from_file_sectionSize = 22;
char message[40], *mem, *fileName, *fileMap;

void ping()
{
    short length = 4;
    write(resp, &length, 1);
    write(resp, "PING", length);
    write(resp, &length, 1);
    write(resp, "PONG", length);
    write(resp, &variant, sizeof(int));
}

void create_shm()
{
    read(req, &byteCount, sizeof(int));
    shmId = shmget(10835, byteCount, IPC_CREAT | 0664);
    write(resp, &create_shmSize, 1);
    write(resp, "CREATE_SHM", create_shmSize);
    if (shmId < 0)
    {
        write(resp, &errorSize, 1);
        write(resp, "ERROR", errorSize);
    }
    else
    {
        mem = (char *)shmat(shmId, 0, 0);
        memoryRegionOpened = 1;
        write(resp, &successSize, 1);
        write(resp, "SUCCESS", successSize);
    }
}

void write_to_shm()
{
    read(req, &offset, 4);
    read(req, &value, 4);
    write(resp, &write_to_shmSize, 1);
    write(resp, "WRITE_TO_SHM", write_to_shmSize);
    if (offset > 0 && offset + sizeof(value) < byteCount)
    {
        mem[offset] = value % 256;
        value = value >> 8;
        mem[offset + 1] = value % 256;
        value = value >> 8;
        mem[offset + 2] = value % 256;
        value = value >> 8;
        mem[offset + 3] = value % 256;
        write(resp, &successSize, 1);
        write(resp, "SUCCESS", successSize);
    }
    else
    {
        write(resp, &errorSize, 1);
        write(resp, "ERROR", errorSize);
    }
}

void map_file()
{
    read(req, &fileNameLength, 1);
    fileName = (char *)malloc((fileNameLength + 1) * sizeof(char));
    read(req, fileName, fileNameLength);
    fileName[fileNameLength] = '\0';
    write(resp, &map_fileSize, 1);
    write(resp, "MAP_FILE", map_fileSize);
    int file = open(fileName, O_RDONLY);
    if (file == -1)
    {
        write(resp, &errorSize, 1);
        write(resp, "ERROR", errorSize);
    }
    else
    {
        struct stat st;
        if (stat(fileName, &st) == 0)
            fileLength = st.st_size;
        fileMap = mmap(0, fileLength, PROT_READ, MAP_SHARED, file, 0);

        fileMapped = 1;
        write(resp, &successSize, 1);
        write(resp, "SUCCESS", successSize);
    }
    close(file);
}

void read_from_file_offset()
{
    read(req, &readFromFileOffset, 4);
    read(req, &readFromFileOffsetNoOfBytes, 4);
    write(resp, &read_from_file_offsetSize, 1);
    write(resp, "READ_FROM_FILE_OFFSET", read_from_file_offsetSize);
    if (!memoryRegionOpened || !fileMapped || (readFromFileOffset + readFromFileOffsetNoOfBytes > fileLength))
    {
        write(resp, &errorSize, 1);
        write(resp, "ERROR", errorSize);
    }
    else
    {
        memcpy(mem, fileMap + readFromFileOffset, readFromFileOffsetNoOfBytes);
        write(resp, &successSize, 1);
        write(resp, "SUCCESS", successSize);
    }
}

void read_from_file_section()
{
    //memcpy(mem, fileMap, fileLength);
    unsigned int section, offset, bytes, offs = 0, sc = 0, ptr = 0;
    sc = fileMap[6];
    read(req, &section, 4);
    read(req, &offset, 4);
    read(req, &bytes, 4);
    write(resp, &read_from_file_sectionSize, 1);
    write(resp, "READ_FROM_FILE_SECTION", read_from_file_sectionSize);
    //memcpy(mem, fileMap, fileLength);
    if (sc < section)
    {
        write(resp, &errorSize, 1);
        write(resp, "ERROR", errorSize);
    }
    else
    {

        ptr = (2 + 2 + 2 + 1) + (section - 1) * (12 + 4 + 4 + 4) + (12 + 4);
        //printf("%d %d %d %d - %d %d %d %d - %d %d %d %d\n",(mem[ptr-4]), mem[ptr -3], (mem[ptr - 2]), (mem[ptr -1]), (mem[ptr]), mem[ptr + 1], (mem[ptr + 2]), (mem[ptr + 3]),(mem[ptr+4]), mem[ptr + 5], (mem[ptr + 6]), (mem[ptr + 7]));
        memcpy(&offs, fileMap + ptr, 4);
        printf("offset %d\n", offs);
        if (offs < 0)
        {
            write(resp, &errorSize, 1);
            write(resp, "ERROR", errorSize);
        }
        else
        {
            memcpy(mem, fileMap + offs + offset, bytes);
            write(resp, &successSize, 1);
            write(resp, "SUCCESS", successSize);
        }
    }
}

void read_from_logical_space_offset()
{
    unsigned int Logicaloffset, Logicalbytes, sc;
    sc = fileMap[6];
    read(req, &Logicaloffset, 4);
    read(req, &Logicalbytes, 4);
    int READ_FROM_LOGICAL_SPACE_OFFSETSIZE = 30, page = 0;
    char logicalMem[1000001];
    write(resp, &READ_FROM_LOGICAL_SPACE_OFFSETSIZE, 1);
    write(resp, "READ_FROM_LOGICAL_SPACE_OFFSET", READ_FROM_LOGICAL_SPACE_OFFSETSIZE);
    for (int i = 0; i < sc; i++)
    {
        int ss, so, ptr;
        ptr = (2 + 2 + 2 + 1) + i * (12 + 4 + 4 + 4) + 12 + 4;
        memcpy(&so, fileMap + ptr, 4);
        ptr += 4;
        memcpy(&ss, fileMap + ptr, 4);
        ptr += 4;
        memcpy(logicalMem + page * 3072, fileMap + so, ss);
        
        page += ss / 3072;
        if (ss % 3072)
            page++;
    }
    if (Logicaloffset + Logicalbytes > page * 3072) // -1?
    {
        write(resp, &errorSize, 1);
        write(resp, "ERROR", errorSize);
    }
    else
    {
        memcpy(mem, logicalMem + Logicaloffset, Logicalbytes);
        write(resp, &successSize, 1);
        write(resp, "SUCCESS", successSize);
    }
}

int main(int argc, char **argv)
{
    if (mkfifo("RESP_PIPE_42526", 0640) < 0)
    {
        printf("ERROR\ncannot create the response pipe\n");
        return 0;
    }

    req = open("REQ_PIPE_42526", O_RDONLY);
    if (req < 0)
    {
        printf("ERROR\ncannot open the request pipe\n");
        return 0;
    }
    resp = open("RESP_PIPE_42526", O_WRONLY);
    if (resp < 0)
    {
        printf("ERROR\ncannot open the response pipe\n");
        return 0;
    }
    write(resp, &connectSize, 1);
    write(resp, "CONNECT", sizeof(char) * connectSize);
    printf("SUCCESS\n");

    read(req, &messageSize, 1);
    read(req, &message, messageSize);
    while (strstr(message, "EXIT") == NULL)
    {
        if (strstr(message, "PING") != NULL)
            ping();
        if (strstr(message, "CREATE_SHM") != NULL)
            create_shm();
        if (strstr(message, "WRITE_TO_SHM") != NULL)
            write_to_shm();
        if (strstr(message, "MAP_FILE") != NULL)
            map_file();
        if (strstr(message, "READ_FROM_FILE_OFFSET") != NULL)
            read_from_file_offset();
        if (strstr(message, "READ_FROM_FILE_SECTION") != NULL)
            read_from_file_section();
        if (strstr(message, "READ_FROM_LOGICAL_SPACE_OFFSET") != NULL)
            read_from_logical_space_offset();
        read(req, &messageSize, 1);
        read(req, &message, messageSize);
    }
    close(resp);
    close(req);
    shmctl(shmId, IPC_RMID, 0);
    shmdt(mem);
    return 0;
}