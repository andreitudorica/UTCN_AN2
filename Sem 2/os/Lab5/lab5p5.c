#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(int argc, char **argv)
{
	int childPid; 
	printf("[Root P1] PID=%d\n", getpid());	
	// Create a new child	
	childPid = fork();
	
	// Check if successfully created the child
	if (childPid < 0) { // if not, terminate the parent too
		perror("Error creating new process");
		exit(1);
	}
		
	// Here we separare between the code executed by the parent and child
	if (childPid > 0) 
	{ //parent
		printf("[lvl1 P2] first child PID=%d ParentPID=%d\n", childPid, getpid());
		childPid=fork();
		if (childPid > 0)
		{
			printf("[lvl1 P3] second child PID=%d ParentPID=%d\n", childPid, getpid());
			sleep(1);
		}
	 	else 
		{	 
			childPid=fork();
			if(childPid==0)
			{
				printf("[lvl2 P4] PID=%d ParentPID=%d\n", getpid(), getppid());
				childPid=fork();
				if(childPid==0)
				{
					printf("[lvl3 P4] PID=%d ParentPID=%d\n", getpid(), getppid());
					childPid=fork();
					if(childPid>0)
					{

						printf("[lvl4 P5] PID=%d ParentPID=%d\n", childPid, getpid());
						childPid=fork();
						if(childPid>0)
						{
							printf("[lvl4 P6] PID=%d ParentPID=%d\n", childPid, getpid());
							childPid=fork();
							if(childPid>0)
							{
								printf("[lvl4 P7] PID=%d ParentPID=%d\n", childPid, getpid());
								childPid=fork();
								if(childPid>0)
								{
									printf("[lvl4 P8] PID=%d ParentPID=%d\n", childPid, getpid());
							
								}
							}
						}
					}

				}
				else
					sleep(1);
			}
			else
				sleep(1);
		}
	}
	
}

