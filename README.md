# MyLinkedList

Project #1

Due Dates:  Tuesday, June 15 at 11:59pm 

Submit:    eLearning

Late Policy:  24-hour late period, then zero

Instructions: This is an individual assignment.  Answers should be your own work.



Introduction:

In this project you will add methods to an existing linked list class.



Description:

Modify the author's "MyLinkedList" http://users.cs.fiu.edu/~weiss/dsaajava3/code/ 
class to add the following methods.
Perform checking of the parameters and throw exceptions where appropriate.  
  

10 points each (a-h)

   a.  itemCount
        receives a value and returns a count of the number of times this item
        is found in the list.

   b.  swap
        receives two index positions as parameters and swaps the two nodes 
        (the nodes, not just the values inside) at these positions, provided 
        both positions are within the current size.

   c.  sublist
        receives two indexes and returns an ArrayList of node values from the first
        index to the second index, provided the indexes are valid.

   d.  select
        receives a variable number of indexes, and returns an ArrayList of node values
        corresponding to each index given, provided the indexes are valid.

   e.  reverse
        returns a new MyLinkedList that has the elements in reverse order.

   f.  erase 
        receives an index position and number of elements as parameters, and
        removes elements beginning at the index position for the number of 
        elements specified, provided the index position is within the size
        and together with the number of elements does not exceed the size.

   g.  insertList
        receives a List and an index position as parameters, and copies all of the 
        passed list into the existing list at the position specified by the parameter,
        provided the index position does not exceed the size.

   h.  shift
        receives an integer and shifts the list this many nodes forward or backward,
        for example, if passed 2, the first two nodes move to the tail, or if 
        passed -3, the last three nodes move to the front.  
           
              +2:  abcde -> cdeab       -3:  abcde ->  cdeab
          

20 points
   i.  main
        change the main method to demonstrate each of your methods.
  
   


Submit to eLearning:
 MyLinkedList.java
