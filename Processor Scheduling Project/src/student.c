/*
 * student.c
 * Multithreaded OS Simulation for CS 2200
 *
 * This file contains the CPU scheduler for the simulation.
 */

#include <assert.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#include <string.h>

#include "os-sim.h"

/** Function prototypes **/
extern void idle(unsigned int cpu_id);
extern void preempt(unsigned int cpu_id);
extern void yield(unsigned int cpu_id);
extern void terminate(unsigned int cpu_id);
extern void wake_up(pcb_t *process);

extern void enqueue(pcb_t *new);
extern pcb_t* dequeue(void);

pcb_t *head = NULL;
static pthread_mutex_t queue_mutex;

/*
 * current[] is an array of pointers to the currently running processes.
 * There is one array element corresponding to each CPU in the simulation.
 *
 * current[] should be updated by schedule() each time a process is scheduled
 * on a CPU.  Since the current[] array is accessed by multiple threads, you
 * will need to use a mutex to protect it.  current_mutex has been provided
 * for your use.
 */
static pcb_t **current;
static pthread_mutex_t current_mutex;
static pthread_cond_t added;

static int timeslice = -1;
static unsigned int cpus;

enum state_t {EMPTY,ADDED} queue_state = EMPTY;
enum algorithm_t {FIFO,RR,SRTF} ALGORITHM = FIFO;

extern void enqueue(pcb_t *new)
{

	if(ALGORITHM == SRTF)
	{
		pthread_mutex_lock(&queue_mutex);
		if (head == NULL)
		{
			head = new;
			head->next = NULL;
			queue_state = ADDED;
			pthread_cond_signal(&added);
			pthread_mutex_unlock(&queue_mutex);
		} else
		{
			unsigned int loop;
			unsigned int remaining = new->time_remaining;
			pcb_t *temp = head;
			while(temp->next != NULL)
			{
				loop = temp->time_remaining;
				if (remaining < loop && remaining >= temp->next->time_remaining)
				{
					new->next = temp->next;
					temp->next = new;
					pthread_cond_signal(&added);
					pthread_mutex_unlock(&queue_mutex);
					return;
				}
				temp = temp->next;
			}

			temp->next = new;
			new->next = NULL;
			pthread_cond_signal(&added);
			pthread_mutex_unlock(&queue_mutex);
		}
	}
				
		

	if(ALGORITHM == FIFO || ALGORITHM == RR)
	{
	pthread_mutex_lock(&queue_mutex);
	if (head == NULL)
	{
		head = new;
		head->next = NULL;
		queue_state = ADDED;
		pthread_cond_signal(&added);
		pthread_mutex_unlock(&queue_mutex);
	} else
	{
		pcb_t *temp = head;
		head = new;
		head->next = temp;
		queue_state = ADDED;
		pthread_cond_signal(&added);
		pthread_mutex_unlock(&queue_mutex);
	}
	}
}


extern pcb_t* dequeue(void)
{
	pcb_t *temp;
	pthread_mutex_lock(&queue_mutex);
	if(head == NULL)
	{
		queue_state = EMPTY;
		pthread_mutex_unlock(&queue_mutex);
		return NULL;
	}
	if(head->next == NULL)
	{
		temp = head;
		head = NULL;
		queue_state = EMPTY;
		pthread_mutex_unlock(&queue_mutex);
		return temp;
	}else
	{
		temp = head;
		while(temp->next->next != NULL)
		{
			temp = temp->next;
		}
		pcb_t *ret = temp->next;
		temp->next = NULL;
		queue_state = ADDED;
		pthread_mutex_unlock(&queue_mutex);
		return ret;
	}
	return NULL;
}

/*
 * schedule() is your CPU scheduler.  It should perform the following tasks:
 *
 *   1. Select and remove a runnable process from your ready queue which 
 *	you will have to implement with a linked list or something of the sort.
 *
 *   2. Set the process state to RUNNING
 *
 *   3. Call context_switch(), to tell the simulator which process to execute
 *      next on the CPU.  If no process is runnable, call context_switch()
 *      with a pointer to NULL to select the idle process.
 *	The current array (see above) is how you access the currently running process indexed by the cpu id. 
 *	See above for full description.
 *	context_switch() is prototyped in os-sim.h. Look there for more information 
 *	about it and its parameters.
 */
static void schedule(unsigned int cpu_id)
{

	if(ALGORITHM == FIFO || ALGORITHM == SRTF)
	{
	pcb_t* process = dequeue();
	if(process == NULL)
	{
		context_switch(cpu_id, NULL, -1);
	} else
	{
		process->state = PROCESS_RUNNING;
		pthread_mutex_lock(&current_mutex);
		current[cpu_id] = process;
		pthread_mutex_unlock(&current_mutex);
		context_switch(cpu_id, process, -1);
	}
	}

	if(ALGORITHM == RR)
	{
		pcb_t *process = dequeue();
		if(process == NULL)
		{
			context_switch(cpu_id, NULL, timeslice);
		} else
		{
			process->state = PROCESS_RUNNING;
			pthread_mutex_lock(&current_mutex);
			current[cpu_id] = process;
			pthread_mutex_unlock(&current_mutex);
			context_switch(cpu_id, process, timeslice);
		}
	}


}


/*
 * idle() is your idle process.  It is called by the simulator when the idle
 * process is scheduled.
 *
 * This function should block until a process is added to your ready queue.
 * It should then call schedule() to select the process to run on the CPU.
 */
extern void idle(unsigned int cpu_id)
{
	
	if(queue_state == EMPTY)
	{
		pthread_mutex_lock(&queue_mutex);
		pthread_cond_wait(&added, &queue_mutex);
	}
	pthread_mutex_unlock(&queue_mutex);

	
    /* FIX ME */
    schedule(cpu_id);

    /*
     * REMOVE THE LINE BELOW AFTER IMPLEMENTING IDLE()
     *
     * idle() must block when the ready queue is empty, or else the CPU threads
     * will spin in a loop.  Until a ready queue is implemented, we'll put the
     * thread to sleep to keep it from consuming 100% of the CPU time.  Once
     * you implement a proper idle() function using a condition variable,
     * remove the call to mt_safe_usleep() below.
     */
    //mt_safe_usleep(1000000);
}


/*
 * preempt() is the handler called by the simulator when a process is
 * preempted due to its timeslice expiring.
 *
 * This function should place the currently running process back in the
 * ready queue, and call schedule() to select a new runnable process.
 */
extern void preempt(unsigned int cpu_id)
{
	pthread_mutex_lock(&current_mutex);
	pcb_t *process = current[cpu_id];
	pthread_mutex_unlock(&current_mutex);

	enqueue(process);
	schedule(cpu_id);
}


/*
 * yield() is the handler called by the simulator when a process yields the
 * CPU to perform an I/O request.
 *
 * It should mark the process as WAITING, then call schedule() to select
 * a new process for the CPU.
 */
extern void yield(unsigned int cpu_id)
{
	pthread_mutex_lock(&current_mutex);
    (current[cpu_id])->state = PROCESS_WAITING;
	pthread_mutex_unlock(&current_mutex);
	schedule(cpu_id);
}


/*
 * terminate() is the handler called by the simulator when a process completes.
 * It should mark the process as terminated, then call schedule() to select
 * a new process for the CPU.
 */
extern void terminate(unsigned int cpu_id)
{
	pthread_mutex_lock(&current_mutex);
    (current[cpu_id])->state = PROCESS_TERMINATED;
	pthread_mutex_unlock(&current_mutex);
	schedule(cpu_id);

}


/*
 * wake_up() is the handler called by the simulator when a process's I/O
 * request completes.  It should perform the following tasks:
 *
 *   1. Mark the process as READY, and insert it into the ready queue.
 *
 *   2. If the scheduling algorithm is SRTF, wake_up() may need
 *      to preempt the CPU with the highest remaining time left to allow it to
 *      execute the process which just woke up.  However, if any CPU is
 *      currently running idle, or all of the CPUs are running processes
 *      with a lower remaining time left than the one which just woke up, wake_up()
 *      should not preempt any CPUs.
 *	To preempt a process, use force_preempt(). Look in os-sim.h for 
 * 	its prototype and the parameters it takes in.
 */
extern void wake_up(pcb_t *process)
{
    process->state = PROCESS_READY;
	enqueue(process);
	if(ALGORITHM == SRTF)
	{
		unsigned int max = 0;
		unsigned int id = 0;
		pthread_mutex_lock(&current_mutex);
		for (unsigned int i = 0; i < cpus; i++)
		{
			if (current[i] == NULL)
			{
				pthread_mutex_unlock(&current_mutex);
				return;
			}
			else if ((current[i])->time_remaining > max)
			{
				max = (current[i])->time_remaining;
				id = i;
			}
		}

		pthread_mutex_unlock(&current_mutex);
		if (process->time_remaining < max)
		{
			force_preempt(id);
		}
	}
}


/*
 * main() simply parses command line arguments, then calls start_simulator().
 * You will need to modify it to support the -r and -s command-line parameters.
 */
int main(int argc, char *argv[])
{
    unsigned int cpu_count;

    /* Parse command-line arguments */
/*    if (argc != 2)
    {
        fprintf(stderr, "CS 2200 OS Sim -- Multithreaded OS Simulator\n"
            "Usage: ./os-sim <# CPUs> [ -r <time slice> | -s ]\n"
            "    Default : FIFO Scheduler\n"
            "         -r : Round-Robin Scheduler\n"
            "         -s : Shortest Remaining Time First Scheduler\n\n");
        return -1;
    }*/
    cpu_count = strtoul(argv[1], NULL, 0);
	cpus = cpu_count;

    /* FIX ME - Add support for -r and -s parameters*/

	if(argc > 3)
	{
	if(strcmp(argv[2], "-r") == 0)
	{
		ALGORITHM = RR;
		timeslice = atoi(argv[3]);
	}

	if(strcmp(argv[2], "-s") == 0)
	{
		ALGORITHM = SRTF;
	}


	}
    /* Allocate the current[] array and its mutex */
    current = malloc(sizeof(pcb_t*) * cpu_count);
    assert(current != NULL);
    pthread_mutex_init(&current_mutex, NULL);

	pthread_mutex_init(&queue_mutex, NULL);

	pthread_cond_init(&added, NULL);

    /* Start the simulator in the library */
    start_simulator(cpu_count);

    return 0;
}


