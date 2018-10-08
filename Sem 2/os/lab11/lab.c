#include<stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <pthread.h>
#include <errno.h>

void increment(int pid, long long MAX)
{
    long long count = 0;
    for (count=0; count<=MAX; count++)
    printf("Process %d at step %lld\n", pid, count);
}
int main()
{
    
}