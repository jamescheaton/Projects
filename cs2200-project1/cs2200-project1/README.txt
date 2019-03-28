James Heaton 2019

Since the simulator can be extremely buggy, the best way to see this processor
working is to utlise the simulator found in \assembly. Further instructions
are contained there.
You do not have to use the hexadecimal converter as this file has already been
created - simply copy and paste the entire contents directly into address 0x0
in the ROM.
You will be able to see the contents of the registers changing by stepping
through the program, although it will take a while to finish the program if
you try to step through the entire thing as it is recursive.
To open the circuit, open Brandonsim and select file->open. The ROM is located
in the center of the datapath, you can place values in there by right-clicking
and selecting edit contents.
