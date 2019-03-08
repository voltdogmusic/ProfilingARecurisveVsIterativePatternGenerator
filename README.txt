/*
Thomas Lee

This program compares an iterative and recursive implementation of a function that
produces a sequence of numbers, ie: 0 1 2 5 12 29 ... where each term of the sequence is twice the previous term plus the term before the previous term. f(n) = 2(n-1)+n-2

The efficiency of each implementation type is calculated as follows:

//Efficiency Variable Definition:
//for the recursive version, the efficiency variable is defined as the number of function calls to compute the sequence
//for the iterative version, the efficiency variable is defined as the number of loops to compute the sequence

Upon exiting the program, a csv file will be generated with the corresponding efficiency of each of the methods.
*/