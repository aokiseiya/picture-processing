# A Java picture processing application
## Introduction
The output executable files have been pre-compiled for convinience.
To execute them, simply run the pictureprocessing script in "executable/bin" directory.
The arguments to use depends on the processing that will be carried out.

## Commands
The following arguments can be passed to the executable:
- invert <input\> <output\>
- grayscale <input\> <output\>
- rotate [90|180|270] <input\> <output\>
- flip [H|V] <input\> <output\>
- blend <input_1\> <input_2\> <input_...\> <input_n\> <output\>
- blur <input\> <output\>

<input\> and <output\> should be directories to image files (input ones must exist).
For example, running "./pictureprocessing rotate 90 input.png output.png" in the "executable/bin" directory will take a image file named "input.png" in the same directory, process the "rotate 90" command, then finally output the result to the "output.png" file.
