# Skipping Work

## Description

Commander Lambda is all about efficiency, including using her bunny "workers" for manual labor. But no one's been properly monitoring the labor shifts for a while and they've gotten quite mixed up. You've been given the task of fixing them, but after you wrote up new shifts you realized that some bunny workers had been transferred to a different area and aren't available for their assigned shifts. Manually sorting through each shift list to compare against work area lists will take forever -- remember, Commander Lambda loves efficiency!

<details><summary>Details about this assignment</summary>
> Given two almost identical lists of worker IDs x and y where one of the lists contains an additional ID, write a function solution(x, y) that compares the lists and returns the additional ID.
> 
> For example, given the lists x = [13, 5, 6, 2, 5] and y = [5, 2, 5, 13], the function solution(x, y) would return 6 because the list x contains the integer 6 and the list y doesn't. Given the lists x = [14, 27, 1, 4, 2, 50, 3, 1] and y = [2, 4, -4, 3, 1, 1, 14, 27, 50], the function solution(x, y) would return -4 because the list y contains the integer -4 and the list x doesn't.
> 
> In each test case, the lists x and y will always contain n non-unique integers where n is at least 1 but never more than 99, and one of the lists will contain an additional unique integer which should be returned by the function. The same n non-unique integers will be present on both lists, but they might appear in a different order like in the examples above. Commander Lambda likes to keep the numbers short, so every worker ID will be between -1000 and 1000.

</details>

## Languages

To provide a Java solution, edit **solution.java** ( In my case, I used java to complete this task )
To provide a Python solution, edit solution.py

## Test Cases

Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

### Test Case 1

Inputs:

    (int list) list1 = {13, 5, 6, 2, 5}

    (int list) list2 = {5, 2, 5, 13}

Output:

    (int) 6

### Test Case 2

Inputs:

    (int list) list1 = {14, 27, 1, 4, 2, 50, 3, 1}

    (int list) list2 = {2, 4, -4, 3, 1, 1, 14, 27, 50}

Output:

    (int) -4


## Constraints

### Java

Your code will be compiled using standard Java 7. It must implement the answer() method in the solution stub.

Execution time is limited. Some classes are restricted (e.g. java.lang.ClassLoader). You will see a notice if you use a restricted class when you verify your solution.

Third-party libraries, input/output operations, spawning threads or processes and changes to the execution environment are not allowed.

### Python

Your code will run inside a Python 2.7.6 sandbox.

Standard libraries are supported except for bz2, crypt, fcntl, mmap, pwd, pyexpat, select, signal, termios, thread, time, unicodedata, zipimport, zlib.
