#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "a2_helper.h"
#include <pthread.h>
#include <errno.h>
pthread_mutex_t lock1, lock2, lock3;
pthread_cond_t cond_start6_4, cond_end3, cond_block_all_until_t10, cond_one_thread_passed_alongside_t10, cond_let_threads_pass_one_by_one, cond_signal_t10_exited, cond_t_3_4_wrote_on_pipe, cond8;
int counter = 0, hadt10, start4, end3, end62, end34, endt10;
int p36[2], p63[2], p1, p2;
void *fcnt2_3(void *arg)
{
    int tid = *((int *)arg);
    pthread_mutex_lock(&lock1);
    if (tid == 4)
    {
        pthread_mutex_unlock(&lock1);
        info(BEGIN, 6, tid);
        pthread_mutex_lock(&lock1);
        start4 = 1;
        pthread_cond_signal(&cond_start6_4);
    }
    else if (tid == 2)
    {
        int fromPipe;
        close(p36[1]);
        read(p36[0], &fromPipe, 4);
        while (fromPipe != 34)
            read(p36[0], &fromPipe, 4);
        pthread_mutex_unlock(&lock1);
        info(BEGIN, 6, tid);
        pthread_mutex_lock(&lock1);
    }
    else
    {
        while (tid == 3 && !start4)
            pthread_cond_wait(&cond_start6_4, &lock1);

        pthread_mutex_unlock(&lock1);
        info(BEGIN, 6, tid);
        pthread_mutex_lock(&lock1);
    }
    while (tid == 4 && !end3)
        pthread_cond_wait(&cond_end3, &lock1);
    if (tid == 3)
    {
        end3 = 1;
        pthread_cond_signal(&cond_end3);
    }
    info(END, 6, tid);
    if (tid == 2)
    {
        int fromPipe;
        close(p63[0]);
        fromPipe = 62;
        write(p63[1], &fromPipe, 4);
        close(p63[1]);
    }
    pthread_mutex_unlock(&lock1);
    return NULL;
}

void *fcnt2_5(void *arg)
{
    int tid = *((int *)arg);
    pthread_mutex_lock(&lock3);
    /*if (tid == 1)
    {
        printf("%d %d\n", p1, p2);
        close(p36[0]);
        int n = 100;
        p1 = write(p36[1], &n, 4);
        printf("%d %d\n", p1, p2);
        close(p36[1]);
        p1 = read(p36[0], &n, 4);
        printf("pipe: %d read: %d\n", p1, n);
        n = 200;
        close(p36[0]);
        p1 = write(p36[1], &n, 4);
        printf("%d %d\n", p1, p2);
        close(p36[1]);
        p1 = read(p36[0], &n, 4);
        printf("pipe: %d read: %d\n", p1, n);
    }*/

    printf("this is thread: %d \n", tid);
    if (tid == 3)
    {
        pthread_cond_wait(&cond_t_3_4_wrote_on_pipe, &lock3);
        int fromPipe;
        close(p63[1]);
        read(p63[0], &fromPipe, 4);
        printf("this is thread: %d found: %d\n", tid, fromPipe);
        while (fromPipe != 62)
            read(p63[0], &fromPipe, 4);
        close(p63[0]);
    }
    pthread_mutex_unlock(&lock3);
    info(BEGIN, 3, tid);
    pthread_mutex_lock(&lock3);
    if (tid == 4)
    {
        pthread_cond_signal(&cond_t_3_4_wrote_on_pipe);
        int fromPipe;
        close(p36[0]);
        fromPipe = 34;
        write(p36[1], &fromPipe, 4);
        close(p36[1]);
    }
    info(END, 3, tid);
    pthread_mutex_unlock(&lock3);
    return NULL;
}

void *fcnt2_4(void *arg)
{
    int tid = *((int *)arg);
    pthread_mutex_lock(&lock2);

    if (tid == 10)
    {
        hadt10 = 1;
        pthread_mutex_unlock(&lock2);
        info(BEGIN, 7, tid);
        pthread_mutex_lock(&lock2);

        pthread_cond_broadcast(&cond_block_all_until_t10);
        pthread_cond_signal(&cond_let_threads_pass_one_by_one);           //1 thread passes
        pthread_cond_signal(&cond_let_threads_pass_one_by_one);           //1 more
        pthread_cond_signal(&cond_let_threads_pass_one_by_one);           //1 more
        pthread_cond_wait(&cond_one_thread_passed_alongside_t10, &lock2); //2 threads
        pthread_cond_wait(&cond_one_thread_passed_alongside_t10, &lock2); //3 threads
        pthread_cond_wait(&cond_one_thread_passed_alongside_t10, &lock2); //4 threads
        info(END, 7, tid);                                                //close t10
        endt10 = 1;
        pthread_cond_broadcast(&cond_signal_t10_exited); //let the others go on
    }
    if (!hadt10)
        pthread_cond_wait(&cond_block_all_until_t10, &lock2);

    pthread_cond_wait(&cond_let_threads_pass_one_by_one, &lock2);
    if (tid != 10)
    {
        pthread_mutex_unlock(&lock2);
        info(BEGIN, 7, tid);
        pthread_mutex_lock(&lock2);
        if (hadt10 < 4)
        {
            hadt10++;
            pthread_cond_signal(&cond_one_thread_passed_alongside_t10);
        }
        if (hadt10 <= 4)
        {
            if (!endt10)
                pthread_cond_wait(&cond_signal_t10_exited, &lock2);
            hadt10 = 5;
        }
        info(END, 7, tid);
    }
    pthread_cond_signal(&cond_let_threads_pass_one_by_one);
    pthread_mutex_unlock(&lock2);
    return NULL;
}

int main(int argc, char **argv)
{
    int pid2, pid3, pid4, pid5, pid6, pid7, pid8;
    int th[40];
    init();

    p1 = pipe(p36);
    p2 = pipe(p63);
    info(BEGIN, 1, 0);
    pid2 = fork();
    if (pid2 == 0)
    {
        info(BEGIN, 2, 0);
        pid3 = fork();
        if (pid3 == 0)
        {
            info(BEGIN, 3, 0);
            pthread_t thrd[40];

            pthread_mutex_init(&lock3, NULL);
            pthread_cond_init(&cond_end3, NULL);
            for (int i = 1; i <= 5; i++)
            {
                th[i] = i;
                pthread_create(&thrd[i], NULL, fcnt2_5, &th[i]);
            }
            for (int i = 1; i <= 5; i++)
                pthread_join(thrd[i], NULL);
            pthread_mutex_destroy(&lock3);
            pthread_cond_destroy(&cond_end3);
            info(END, 3, 0);
        }
        else
        {
            pid4 = fork();
            if (pid4 == 0)
            {
                info(BEGIN, 4, 0);
                info(END, 4, 0);
            }
            else
            {
                pid5 = fork();
                if (pid5 == 0)
                {
                    info(BEGIN, 5, 0);
                    info(END, 5, 0);
                }
                else
                {
                    pid6 = fork();
                    if (pid6 == 0)
                    {
                        info(BEGIN, 6, 0);
                        pthread_t thrd[40];
                        pthread_mutex_init(&lock1, NULL);
                        pthread_cond_init(&cond_start6_4, NULL);
                        pthread_cond_init(&cond_end3, NULL);
                        for (int i = 1; i <= 5; i++)
                        {
                            th[i] = i;
                            pthread_create(&thrd[i], NULL, fcnt2_3, &th[i]);
                        }
                        for (int i = 1; i <= 5; i++)
                            pthread_join(thrd[i], NULL);
                        pthread_mutex_destroy(&lock1);
                        pthread_cond_destroy(&cond_start6_4);
                        pthread_cond_destroy(&cond_end3);
                        info(END, 6, 0);
                    }
                    else
                    {
                        pid8 = fork();
                        if (pid8 == 0)
                        {
                            info(BEGIN, 8, 0);
                            info(END, 8, 0);
                        }
                        else
                        {
                            waitpid(pid3, NULL, 0);
                            waitpid(pid4, NULL, 0);
                            waitpid(pid5, NULL, 0);
                            waitpid(pid6, NULL, 0);
                            waitpid(pid8, NULL, 0);
                            info(END, 2, 0);
                        }
                    }
                }
            }
        }
    }
    else
    {
        pid7 = fork();
        if (pid7 == 0)
        {
            info(BEGIN, 7, 0);
            pthread_t thrd[40];
            pthread_mutex_init(&lock2, NULL);
            pthread_cond_init(&cond_block_all_until_t10, NULL);
            pthread_cond_init(&cond_one_thread_passed_alongside_t10, NULL);
            pthread_cond_init(&cond_let_threads_pass_one_by_one, NULL);
            pthread_cond_init(&cond_signal_t10_exited, NULL);
            for (int i = 1; i <= 38; i++)
            {
                th[i] = i;
                pthread_create(&thrd[i], NULL, fcnt2_4, &th[i]);
            }
            for (int i = 1; i <= 38; i++)
                pthread_join(thrd[i], NULL);
            pthread_mutex_destroy(&lock2);
            pthread_cond_destroy(&cond_block_all_until_t10);
            pthread_cond_destroy(&cond_one_thread_passed_alongside_t10);
            pthread_cond_destroy(&cond_let_threads_pass_one_by_one);
            pthread_cond_destroy(&cond_signal_t10_exited);
            info(END, 7, 0);
        }
        else
        {
            waitpid(pid2, NULL, 0);
            waitpid(pid7, NULL, 0);
            info(END, 1, 0);
        }
    }
}
