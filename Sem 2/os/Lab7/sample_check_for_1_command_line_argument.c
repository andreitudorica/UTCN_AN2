#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{

    if (argc != 2)
    {
        printf("Wrong number of parameters\n");
        exit(1);
    }

    int N = atoi(argv[1]);
    return 0;
}
