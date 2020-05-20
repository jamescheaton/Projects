#include "types.h"
#include "pagesim.h"
#include "paging.h"
#include "swapops.h"
#include "stats.h"
#include "util.h"

pfn_t select_victim_frame(void);

/*  --------------------------------- PROBLEM 7 --------------------------------------
    Make a free frame for the system to use.

    You will first call the page replacement algorithm to identify an
    "available" frame in the system.

    In some cases, the replacement algorithm will return a frame that
    is in use by another page mapping. In these cases, you must "evict"
    the frame by using the frame table to find the original mapping and
    setting it to invalid. If the frame is dirty, write its data to swap!
 * ----------------------------------------------------------------------------------
 */
pfn_t free_frame(void) {

    /* Call your function to find a frame to use, either one that is
       unused or has been selected as a "victim" to take from another
       mapping. */
    pfn_t victim_pfn = select_victim_frame();

    /*
     * If victim frame is currently mapped:
     *
     * 1) Look up the corresponding page table entry
     * 2) If the entry is dirty, write it to disk with swap_write()
     * 3) Mark the original page table entry as invalid
     */
    /* If the victim is in use, we must evict it first */

	fte_t* victim_frame = frame_table + victim_pfn;
	vpn_t vpn = victim_frame->vpn;
	pfn_t ptbr = victim_frame->process->saved_ptbr;
	if (victim_frame->mapped) {
		pte_t* pte = (pte_t*)(mem + (ptbr * PAGE_SIZE) + (vpn * sizeof(pte_t)));
		if (pte->dirty) {
			void* src = (void*)(mem + (victim_pfn * PAGE_SIZE));
			swap_write(pte, src);
			pte->dirty = 0;
			stats.writebacks++;
		}
		pte->valid = 0;
	}
    /* Return the pfn */
    return victim_pfn;
}



/*  --------------------------------- PROBLEM 9 --------------------------------------
    Finds a free physical frame. If none are available, uses either a
    randomized or clock sweep algorithm to find a used frame for
    eviction.

    When implementing clock sweep, make sure you set the reference
    bits to 0 for each frame that had its referenced bit set. Your
    clock sweep should remember the index at which it leaves off and
    resume at the same place for each run (you may need to add a
    global or static variable for this).

    Return:
        The physical frame number of a free (or evictable) frame.

    HINTS: Use the global variables MEM_SIZE and PAGE_SIZE to calculate
    the number of entries in the frame table.
    ----------------------------------------------------------------------------------
*/
pfn_t select_victim_frame() {

    /* See if there are any free frames first */
    size_t num_entries = MEM_SIZE / PAGE_SIZE;
    for (size_t i = 0; i < num_entries; i++) {
        if (!frame_table[i].protected && !frame_table[i].mapped) {
            return i;
        }
    }

    if (replacement == RANDOM) {
        /* Play Russian Roulette to decide which frame to evict */
        pfn_t last_unprotected = NUM_FRAMES;
        for (pfn_t i = 0; i < num_entries; i++) {
            if (!frame_table[i].protected) {
                last_unprotected = i;
                if (prng_rand() % 2) {
                    return i;
                }
            }
        }
        /* If no victim found yet take the last unprotected frame
           seen */
        if (last_unprotected < NUM_FRAMES) {
            return last_unprotected;
        }
    } else if (replacement == CLOCKSWEEP) {
        /* Implement a clock sweep algorithm here */
		static size_t head = 0;
		size_t i = 0;
		while (i < 3 * num_entries) {
			fte_t* fte = frame_table + ((head + i)%num_entries);
			if(fte->protected == 0 && fte->referenced) {
				fte->referenced = 0;
				} else if (fte->protected == 0) {
					size_t temp = (head + i);
					head = (head + i + 1) % num_entries;
					return temp % num_entries;
				}
			i++;
		}

    }

    /* If every frame is protected, give up. This should never happen
       on the traces we provide you. */
    panic("System ran out of memory\n");
	return 0;
}