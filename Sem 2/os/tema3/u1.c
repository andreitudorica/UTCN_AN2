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
int main()
{
    int file = open("test_root/FXMKZeHMLE.cax", O_RDWR);
    if (file == -1)
        printf("Failed");
    else
        printf("Success");
    return 0;
}