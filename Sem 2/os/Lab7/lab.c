#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

long long count = 0;
void increment(int th_id)
{
  long long aux;
  aux = count;
  aux++;
  usleep(100);
  count = aux;
}

int main(int argc, char *argv[])
{

    if (argc != 2)
    {
        printf("Wrong number of parameters\n");
        exit(1);
    }
    int N = atoi(argv[1]);
    int i;
    pthread_t th[1000];

    for (i = 0; i < N; i++)
        if (pthread_create(&th[i], NULL, (void *(*)(void *))increment, (void *)i) != 0)
        {
            perror("Cannot create threads");
            exit(1);
        }

    return 0;
}
