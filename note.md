# 1 Introduction to Java
## 1.1 Essentials
Java is an object oriented language with strict requirements  
1. All code in Java must be part of a class
2. We delimit hte beginning and end of segments of code with { }
3. All staements in Java must end in a semi-colon
4. For code to run we need `public static void main(String[] args){}`

statically typed  
1. Before Java variables can be used, they must be declared
2. Java variables must have a specific type
3. Java variable types can never change
4. In java, types are verified before the code runs! If there are type issues, the code will not compile

1. All parameters of a function must have a type, and the function itself must have a return type
2. All functions must be part of a clss, In programming language terminology, a function that is part of a class is called a "method", so all fucntions in Java are methods

A sequence of two programs to run a Java program  
- `javac` to compile the code
- `java` to run it

Static Typing  

## 1.2 Objects

Defining and Instantiating Classes
- Every method is asociated with some class
- To run a class, we must define a main method
  - Not all calsses have a main method

Arrays of Objects  
To create an array of objects  
- First use the new keyword to careate the array
- Then use new again for each object that you want to put in the array

Static Non-static  
key differences between static and non-static methods  
- Static methods are invoked using the class name
- Instance methods are invoked using an instance name
- Static methods can't acess instance variables

A class may have a mix of static and non-static memebers  
- A variable or method defined in a class is also called a member of that calss
- Static members are accessed using class name
- Non-static members are accessed using class name
- Static methods must access instance variables via a specific instance

`public static void main(String[] args)`
- public: So far, all of our methods start with this keyword
- static: It is a static method, not associated with any particular instance
- void: It has no return type
- main: This is the name of the method
- String[] args: This is a parameter that is passed to the main method

Command Line Arguments  
```java
public class ArgsDemo {
    public static void main(String[] args) {
        System.out.println(args[0]);
    }
}
```

# 3 Testing and Selection Sort

## 3.1 A New Way
### Ad Hoc Testing
hoc testing
`java.util.Arrays.equals` for `==` in array
### JUnit Testing
junit assert  
```java
org.junit.Assert.assertArrayEquals(expected, input);
```

### Selection Sort

### Better JUnit
- Precede each method with `@org.junit.Test` (`Test` after `import org.junit.Test;`).
- Change each test method to be non-static.
- Remove our main method from the TestSort class.

### Testing Philosophy
Test-Driven Development (TDD)  
1. Identify a new feature.
2. Write a unit test for that feature.
3. Run the test. It should fail.
4. Write code that passes the test. Yay!
5. Optional: refactor code to make it faster, cleaner, etc. Except now we have a reference to tests that should pass.


# 2 Lists

## 2.1 Mystery of the Walrus
- primitive types in Java  
  - byte, short, int, long, float, double, boolean, char  
- reference type  
  - Everything else, including arrays  

### Reference Types

#### Object Instantiation
When we instantiate an Object using new (e.g. Dog, Walrus, Planet)  
Java first allocates a box for each instance variable of the class, and fills them with a default value.  
The constructor then usually (but not always) fills every box with some other value.

#### Reference Variable Declaration
When we declare a variable of any reference type (Walrus, Dog, Planet, array, etc.),  
Java allocates a box of 64 bits, no matter what type of object.

**This 64 bits of memory is the address of the object**

#### Box and Pointer Notation
- If an address is all zeros, we will represent it with null.
- A non-zero address will be represented by an arrow pointing at an object instantiation.

#### Parameter Passing
When you pass parameters to a function, you are also simply copying the bits.  
In other words, the GRoE also applies to parameter passing.  
Copying the bits is usually called "pass by value".  
**In Java, we always pass by value.**

#### Instantiation of Arrays
Objects can be lost if you lose the bits corresponding to the address  

#### IntLists
- link list
```java
public class IntList {
    public int first;
    public IntList rest;        

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }
}
```

## 2.2 The SLList
naked recursive data structure  

### Public and Private
**When you create a `public` member (i.e. method or variable), be careful, because you're effectively committing to supporting that member's behavior exactly as it is now, forever.**

### Nested Classes
Java provides us with the ability to embed a class declaration inside of another  
If the nested class has no need to use any of the instance methods or variables of outer class, you may declare the nested class `static`  

### hepler method (**overloaded**)
create a private helper method that interacts with the underlying naked recursive data structure.  

```java
/** returns the size of the list starting at IntNode p */
private static int size(IntNode p) {
    if (p.next == null) {
      return 1;
    }
    return 1 + size(p.next);
}

public int size() {
    return size(first);
}
```

We say that two methods with the same name but different signatures are overloaded  


### Sentinel Nodes
A cleaner, though less obvious solution, is to make it so that all SLLists are the "same", even if they are empty.  
We can do this by creating a special node that is always there, which we will call a **sentinel node**.

Introduce a new node before all nodes to avoid crash of addlast to empty node  

```java
public void addLast(int x) {
    size += 1;
    IntNode p = sentinel;
    while (p.next != null) {
        p = p.next;
    }

    p.next = new IntNode(x, null);
}
```

### Invariants

A SLList with a sentinel node has at least the following invariants:  
- The sentinel reference always points to a sentinel node.
- The front item (if it exists), is always at sentinel.next.item.
- The size variable is always the total number of items that have been added.

## 2.3 The DLList

### Improvement #7: Looking Back
add a previous pointer to each IntNode  
```java
public class IntNode {
    public IntNode prev;
    public int item;
    public IntNode next;
}
```

### Improvement #8: Sentinel Upgrade
- add a second sentinel node to the back of the list
- implement the list so that it is circular, with the front and back pointers sharing the same sentinel node.

### Generic DLLists
```java
public class DLList<BleepBlorp> {
    private IntNode sentinel;
    private int size;

    public class IntNode {
        public IntNode prev;
        public BleepBlorp item;
        public IntNode next;
        ...
    }
    ...
}
```
```java
DLList<String> d2 = new DLList<>("hello");
d2.addLast("world");
```

- In the .jave file implementing your dasta structure, specify your "generic type" only once at the very top of the file
- In .jave files that use your data structure, specify d3esired type onec:
  - Write out desired type during declaration
  - Use the empty diamond operator <> during instantiation
- When declaring or instantiating your data structure, use the reference type
  - int: Integer
  - double: Double
  - char: Character
  - boolean: Boolean
  - long: Long

## 2.4 Arrays
### Array Basics
- `int x;` gives us a 32 bit memory box that stores ints.
- `Walrus w1;` gives us a 64 bit memory box that stores Walrus references.
- `Walrus w2 = new Walrus(30, 5.6);` gets us 3 total memory boxes. One 64 bit box that stores Walrus references, one 32 bit box that stores the int size of the Walrus, and a 64 bit box that stores the double tuskSize of the Walrus.

Arrays consist of: 
- A fixed integer length, N
- A sequence of N memory boxes (N = length) where all boxes are of the same type, and are numbered 0 through N - 1.

### Array Creation
- `x = new int[3];`
  - create an array of the specified length and fill in each memory box with a default value
  - In this case, it will create an array of length 3, and fill each of the 3 boxes with the default int value 0
- `y = new int[]{1, 2, 3, 4, 5};`
  - creates an array with the exact size needed to accommodate the specified starting values
  - In this case, it creates an array of length 5, with those five specific elements
- `int[] z = {9, 10, 11, 12, 13};`
  - has the same behavior as the second notation
  - can only be used when combined with a variable declaration

### Array Access and Modification

Arraycopy  
Two ways to copy arrays  
- item by item using a loop
- Using arraycopoy. Takes 5 parameters:
  - Source array
  - Start position in source
  - Target array
  - Start position in target
  - Number to copy

### 2D Arrays in Java
`int[][] bamboozle = new int[4][]`
This creates exactly four memory boxes, each of which can point to an array of integers (of unspecified length).  
`matrix = new int[4][4]`
create 16 memory boxs, and the sub array are set to default value of int array, which is 0  

### Arrays vs. Classes
The key differences between memory boxes in arrays and classes:  
- Array boxes are numbered and accessed using [] notation, and class boxes are named and accessed using dot notation.
- Array boxes must all be the same type. Class boxes can be different types.

One particularly notable impact of these difference is that [] notation allows us to specify which index we'd like at **runtime**

## 2.5 The AList

### First Attempt: The Naive Array Based List
- The position of the next item to be inserted (using addLast) is always size.
- The number of items in the AList is always size.
- The position of the last item in the list is always size - 1.

### Naive Resizing Arrays
```java
int[] a = new int[size + 1];
System.arraycopy(items, 0, a, 0, size);
a[size] = 11;
items = a;
size = size + 1;
```
### Analyzing the Naive Resizing Array
Creating all those memory boxes and recopying their contents takes time.  
By contrast, the naive array list shows a parabola, indicating that each operation takes linear time, since the integral of a line is a parabola  
N^2/N  

### Geometric Resizing
```java

public void insertBack(int x) {
    if (size == items.length) {
           resize(size * RFACTOR);
    }
    items[size] = x;
    size += 1;
}
```
We instead resize by **multiplying** the number of boxes by RFACTOR

### Memory Performance
we can also downsize our array when it starts looking empty.  
Specifically, we define a "usage ratio" R which is equal to the size of the list divided by the length of the items array.  
For example, in the figure below, the usage ratio is 0.04.  

In a typical implementation, we halve the size of the array when R falls to less than 0.25  

### Generic ALists
Java does not allow us to create an array of generic objects due to an obscure issue with the way generics are implemented

- [ ] FALSE  
  `Glorp[] items = new Glorp[8];`
- [x] CORRECT  
  `Glorp[] items = (Glorp []) new Object[8];`

This will yield a compilation warning, but it's just something we'll have to live with

# 4 Inheritance, Implements
## 4.1 Intro and interfaces
interface is a specification of what a list is able to do, not how to do it

### Overriding
When implementing the required functions in the subclass, it's useful (and actually required in 61B) to include the `@Override` tag right on top of the method signature

### Interface Inheritance
Interface Inheritance refers to a relationship in which a subclass inherits all the methods/behaviors of the superclass  
the interface includes all the method signatures, but not implementations. It's up to the subclass to actually provide those implementations  

### Implementation Inheritance
These methods identify how hypernyms of List61B should behave  
In order to do this, you must include the default keyword in the method signature  

### Static Type and Dynamic Type
`List61B<String> lst = new SLList<String>();`  
In the above declaration and instantiation, lst is of type "List61B". This is called the "static type"  

When Java runs a method that is overriden, it searches for the appropriate method signature in it's dynamic type and runs it  
**IMPORTANT: This does not work for overloaded methods!**  

### Interface Inheritance vs Implementation Inheritance
- Interface inheritance (what): Simply tells what the subclasses should be able to do.
  - EX) all lists should be able to print themselves, how they do it is up to them.
- Implementation inheritance (how): Tells the subclasses how they should behave.
  - EX) Lists should print themselves exactly this way: by getting each element in order and then printing them.

When you are creating these hierarchies, remember that the relationship between a subclass and a superclass should be an **"is-a"** relationship  

## 4.2 Extends, Casting, Higher Order Functions

### Extends
inheritance allows subclasses to reuse code from an already defined class  
`public class RotatingSLList<Item> extends SLList<Item>`

By using the extends keyword, subclasses inherit all members of the parent class. "Members" includes:  
- All instance and static variables
- All methods
- All nested classes

Note that constructors are not inherited, and private members cannot be directly accessed by subclasses.  

### Super
`super.methodname` allows you to use method inherited from superclass
`super()` allows you to use the superclass default constructor

- Constructors Are Not Inherited
While constructors are not inherited, Java requires that all constructors must start with a call to one of its superclass's constructors.  

### The Object Class
Every class in Java is a descendant of the Object class, or extends the Object class  
Even classes that do not have an explicit extends in their class still implicitly extend the Object class  

### Encapsulation
Encapsulation is one of the fundamental principles of object oriented programming, and is one of the approaches that we take as programmers to resist our biggest enemy: complexity  
Managing complexity is one of the major challenges we must face when writing large programs.

**Inheritance May Breaks Encapsulation**

### Type Checking and Casting
In general, the compiler only allows method calls and assignments based on compile-time types.  

### Expressions
expressions using the new keyword also have compile-time types  
The compiler check if the compile-time type of the right-hand side of the expression matchs the left-hand side  

### Casting
`(Item)`
Java has a special syntax where you can tell the compiler that a specific expression has a specific compile-time type.  
Caution: Casting is a powerful but dangerous tool.  
Essentially, casting is telling the compiler not to do its type-checking duties - telling it to trust you and act the way you want it to  

### Higher Order Functions
In old school Java (Java 7 and earlier), memory boxes (variables) could not contain pointers to functions  

IntUnaryFunction

```java
public interface IntUnaryFunction {
    int apply(int x);
}
```
```java
public class TenX implements IntUnaryFunction {
    /* Returns ten times the argument. */
    public int apply(int x) {
        return 10 * x;
    }
}
```
```java
public static int do_twice(IntUnaryFunction f, int x) {
    return f.apply(f.apply(x));
}

public static void main(String[] args) {
    IntUnaryFunction tenX = new TenX();
    System.out.println(do_twice(tenX(), 2));
}
```

above equals to  
```python
def tenX(x):
    return 10 * x
def do_twice(f, x):
    return f(f(x))

print(do_twice(tenX, 2))
```

### Inheritance Cheatsheet
Invocation of overridden methods follows two simple rules:

- Compiler plays it safe and only allows us to do things according to the static type.
- For overridden methods (not overloaded methods), the actual method invoked is based on the dynamic type of the invoking expression
- Can use casting to overrule compiler type checking.

### Dynamic Method Selection Puzzle

Rules:
- Compiler allows memory box to hold any subtype
- Compiler allows calls based on static type
- Overridden non-static methods are selected at run time based on dynamic type
  - Everything else is based on static type, including overloaded methods
  - Note, no overloadedmethods for problem at left

## 4.3 Subtype Polymorphism vs. HoFs
Subtype Polymorphism
Polymorphism: providing a single interface to entities of different types  

In Java, polymorphism refers to how objects can have many forms or types  
In object-oriented programming, polymorphism relates to how an object can be regarded as an instance of its own class, an instance of its superclass, an instance of its superclass's superclass, and so on.  

### Max Function
In Python or C++, the way that the > operator works could be **redefined** to work in different ways when applied to different types.  
Java does not have this capability  

```java
public int compareTo(Object o) {
        Dog uddaDog = (Dog) o;
        // below can be replaced with return this.size - uddaDog.size;
        if (this.size < uddaDog.size) {
            return -1;
        } else if (this.size == uddaDog.size) {
            return 0;
        }
        return 1;
    }
```
Notice that since compareTo takes in any arbitrary Object o, we have to cast the input to a Dog to make our comparison using the size instance variable.  

### Comparables
Instead of using our personally created interface `OurComparable`, we now use the real, built-in interface, `Comparable`. As a result, we can take advantage of all the libraries that already exist and use `Comparable`. 

### Comparator
Since a comparator is an object, the way we'll use `Comparator` is by writing a nested class inside Dog that implements the `Comparator` interface.
```java
import java.util.Comparator;

public class Dog implements Comparable<Dog> {
    ...
    public int compareTo(Dog uddaDog) {
        return this.size - uddaDog.size;
    }

    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
}
```

```java
Comparator<Dog> nc = Dog.getNameComparator();
```

### Summarize
interfaces in Java provide us with the ability to make **callbacks**  

- A Comparable is imbedded within the object itself, and it defines the natural ordering of a type. 
- A Comparator, on the other hand, is more like a third party machine that compares two objects to each other. 
  - Since there's only room for **one** compareTo method, if we want multiple ways to compare, we must turn to Comparator.

# 6 Exceptions, Iterators, Iterables, Object Methods

## 6.1 Lists, Sets, ArraySet

### Lists in Real Java Code
`java.util.List<Integer> L = new java.util.ArrayList<>();`  

### Sets
Sets are a collection of unique elements - you can only have one copy of each element. There is also no sense of order.  

### ArraySet
- `add(value)`: add the value to the set if not already present
- `contains(value)`: check to see if ArraySet contains the key
- `size()`: return number of values

## 6.2 Throwing Exceptions

`throw new ExceptionObject(parameter1, ...)`  
`IllegalArgumentExceptio` takes in one parameter  

## 6.3 Iteration

### Enhanced For Loop
`public Iterator<E> iterator();`  

We check that there are still items left with `seer.hasNext()`, which will return true if there are unseen items remaining, and false if all items have been processed.  
`seer.next()` does two things at once.  
It returns the next element of the list, and here we print it out.  
It also advances the iterator by one item. In this way, the iterator will only inspect each item once.  

### Implementing Iterators
When `iterator()` being called, we must check
- Does the List interface have an `iterator()` method
- Does the Iterator interface have `next/hasNext()` method

The List interface extends the Iterable interface, inheriting the abstract iterator() method.  
(Actually, List extends Collection which extends Iterable, but it's easier to codethink of this way to start.)  

## 6.4 Object Methods
All classes inherit from the overarching Object class. The methods that are inherited are as follows:
- `String toString()`
- `boolean equals(Object obj)`
- `Class <?> getClass()`
- `int hashCode()`
- `protected Objectclone()`
- `protected void finalize()`
- `void notify()`
- `void notifyAll()`
- `void wait()`
- `void wait(long timeout)`
- `void wait(long timeout, int nanos)`

### `toString()`
The toString() method provides a string representation of an object  
The default Object class' toString() method prints the location of the object in memory  

string builder way  
```java
public String toString() {
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size - 1; i += 1) {
            returnSB.append(items[i].toString());
            returnSB.append(", ");
        }
        returnSB.append(items[size - 1]);
        returnSB.append("}");
        return returnSB.toString();
    }
```

### `equals()`
`equals()` and `==` have different behaviors in Java.  
`==` Checks if two objects are actually the same object in memory.   
Remember, pass-by-value! == checks if two boxes hold the same thing

Overrides `equals(Object o)` method in object class
```java
public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArraySet<T> o = (ArraySet<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (T item : this) {
            if (!o.contains(item)) {
                return false;
            }
        }
        return true;
    }
```
A couple of rules to adhere to while implementing your `.equals()` method are as follows:  
1. equals must be an equivalence relation
   1. reflexive: x.equals(x) is true
   2. symmetric: x.equals(y) if and only if y.equals(x)
   3. transitive: x.equals(y) and y.equals(z) implies x.equals(z)
2. It must take an Object argument, in order to override the original .equals() method
3. It must be consistent if x.equals(y), then as long as x and y remain unchanged: x must continue to equal y
4. It is never true for null x.equals(null) must be false

# extra
## Command Line Compilation

`public static void main(string[] args)`

args can be used as a list, like `args[0]` for the first arg

## Git
coverd these part:  
- Maps
- Hashing
- File I/O
- Graphs

Would need roughly 2^80 files before we expect to see two files with the same SHA hash  

Every commit in git stores (at least):
- An author.
- A date.
- A commit message.
- A list of all files and their versions.
  - Versions are git-SHA1 hashes.
- The parent’s commit ID.
  - Example: aa45f...db’s parent ID is d1bd...61.

### store file
Java has a built-in feature called Serializable that lets you store arbitrary objects.  
Easy to use: Just make your class implement Serializable.  
There are no methods to implement (weird).  
Then use our Utils class to write/read objects to/from files.  

# 8 Efficient Programming

## 8.1 Encapsulation， API's, ADT's

### Encapsulation
- Module: A set of methods that work together as a whole to perform some task or set of related tasks.
- Encapsulated: A module is said to be encapsulated if its implementation is completely hidden, and it can be accessed only through a documented interface.

### API's
An API(Application Programming Interface) of an ADT is the list of constructors and methods and a short description of each.  

API consists of syntactic and semantic specification.

### ADT's
ADT's (Abstract Data Structures) are high-level types that are defined by their behaviors, not their implementations.  


## 8.2 Asymptotics I

### Example of Algorithm Cost
Objective: Determine if a sorted array contains any duplicates.

- Silly Algorithm: Consider every pair, returning true if any match!  

- Better Algorithm: Take advantage of the sorted nature of our array.  

### Techniques for Measuring Computational Cost
- Technique 1: Measure execution time in seconds using a client program (i.e. actually seeing how quick our program runs in physical seconds)
- Technique 2
  - Procedure
    - Look at your code and the various operations that it uses (i.e. assignments, incrementations, etc.)  
    - Count the number of times each operation is performed.  
  - Observations
    - Some counts get tricky to count.
    - How did we get some of these numbers? It can be complicated and tedious.
  - Pros vs. Cons
    - Pros: Machine independent (for the most part). Input dependence captured in model.
    - Cons: Tedious to compute. Array size was arbitrary

### Asymptotic Behavior
In most cases, we only care about what happens for very large N (asymptotic behavior).  

### Simplification Summary
- Only consider the worst case.
- Pick a representative operation (aka: cost model)
- Ignore lower order terms
- Ignore multiplicative constants.

### Big-Theta
$$R(N) \in \Theta(f(N)) $$  
means that there exists positive constants $k_1$, $k_2$ such that:
$$k_1 \cdot f(N) <= R(N) <= k_2 \cdot f(N)$$  

### Big O
Similarly, here's the formal definition of Big O:  
$$R(N) \in O(f(N))$$ 
means that there exists positive constants $k_2$ such that: $R(N)≤k_​2 \cdot f(N)$ for all values of N greater than some $N_0$ (a very large N).  
Observe that this is a looser condition than Big Theta since Big O does not care about the lower bound.

Summary
- Given a piece of code, we can express its runtime as a function R(N)
  - N is some property of the input of the function
  - i.e. oftentimes, N represents the size of the input
- Rather than finding R(N) exactly, we instead usually only care about the order of growth of R(N).
- One approach (not universal):
  - Choose a representative operation
  - Let $C(N) = count$ of how many times that operation occurs, as a function of $N$.
  - Determine order of growth $f(N)$ for $C(N)$, i.e. $C(N)∈Θ(f(N))$
  - Often (but not always) we consider the worst case count.
  - If operation takes constant time, then $R(N)∈Θ(f(N))$

## 8.3 Asymptotics II

### There is no magic shortcut
Techniques:
- Find exact sum
- Write out examples
- Draw pictures

Sum Things to Know Here are two important sums you'll see quite often, and should remember:  

$$1+2+3+...+Q=\frac{Q(Q+1)}{2} =Θ(Q^​2)$$ 
(Sum of First Natural Numbers)  
$$1+2+4+8+...+Q=2Q−1=Θ(Q)$$ 
(Sum of First Powers of 2)  

### Binary Search
Binary search is a nice way of searching a list for a particular item.  
It requires the list to be in sorted order, and uses that fact to find an element quickly. 

We start with n options, then n/2, then n/4 ... until we have just 1.  
Each time, we cut the array in half, so in the end we must perform a total of $log2(n)$ operations. 
Each of the $log2(n)$ operations

A couple properties worth knowing (see below for proofs)  

- $⌊f(N)⌋=Θ(f(N))$
- $⌈f(N)⌉=Θ(f(N))$
- $logp(N)=Θ(logq(N))$
- $Θ(⌊log2(N)⌋)=Θ(logN)$

Log time is super good! It's almost as fast as constant time, and way better than linear time.  


### Merge Sort
Selection sort works off two basic steps:  
- Find the smallest item among the unsorted items, move it to the front, and ‘fix’ it in place.
- Sort the remaining unsorted/unfixed items using selection sort.

If we analyze selection sort, we see that it's $Θ(N^2)$.  

Say we have two sorted arrays that we want to combine into a single big sorted array.  
We could append one to the other, and then re-sort it, but that doesn't make use of the fact that each individual array is already sorted.   

This is the essence of merge sort:  
- If the list is size 1, return. Otherwise:
- Mergesort the left half 
- Mergesort the right half
- Merge the results

Mergesort has worst case runtime $Θ(NlogN)$. 


# 9 Disjoint Sets
## 9.1 Introduction
Two sets are named disjoint sets if they have no elements in common.  
A Disjoint-Sets (or Union-Find) data structure keeps track of a fixed number of elements partitioned into a number of disjoint sets.  

The data structure has two operations:  
- `connect(x, y)`: connect x and y. Also known as union
- `isConnected(x, y)`: returns true if x and y are connected (i.e. part of the same set).

## 9.2 Quick Find
- ListOfSets
`List<Set<Integer>>`

- Quick Find
- The indices of the array represent the elements of our set.
- The value at an index is the set number it belongs to.

## 9.3 Quick Union
Instead of an id, we assign each item the index of its parent.  
If an item has no parent, then it is a 'root' and we assign it a negative value.
For QuickUnion we define a helper function find(int item) which returns the root of the tree item is in.  

### Performance
There is a potential performance issue with QuickUnion: the tree can become very long.  
In this case, finding the root of an item (find(item)) becomes very expensive. 

## 9.4 Weighted Quick Union(WQU)
whenever we call connect, we always link the root of the smaller tree to the larger tree.  

### Maximum height: Log N
By extension, the runtimes of connect and isConnected are bounded by O(log N).  


## 9.5 Weighted Quick Union with Path Compression
The clever insight is realizing that whenever we call find(x) we have to traverse the path from x to root.   So, along the way we can connect all the items we visit to their root at no extra asymptotic cost.  
Connecting all the items along the way to the root will help make our tree shorter with each call to find.  

By extension, the average runtime of connect and isConnected becomes almost constant in the long term! This is called the amortized runtime. 


## Summary
- Represent sets as connected components (don't track individual connections).
 - ListOfSetsDS: Store connected components as a List of Sets (slow, complicated).
 - QuickFindDS: Store connected components as set ids.
 - QuickUnionDS: Store connected components as parent ids.
 - WeightedQuickUnionDS: Also track the size of each set, and use size to decide on new tree root.
 - WeightedQuickUnionWithPathCompressionDS: On calls to connect and isConnected, set parent id to the root for all items seen.

|Implementation|Constructor|connect|isConnected|
|----|----|----|----|
|ListOfSets|Θ(N)1|O(N)|O(N)|
|QuickFind|Θ(N)|Θ(N)|Θ(1)|
|QuickUnion|Θ(N)|O(N)|O(N)|
|Weighted Quick Union|Θ(N)|O(log N)|O(log N)|
|WQU with Path Compression||O(α(N))*|O(α(N))*|

# 10 ADTs

## 10.1 Intro to ADTs
An Abstract Data Type (ADT) is defined only by its operations, not by its implementation. 

Some commonly used Collections ADT's are:  

- Stacks: Structures that support last-in first-out retrieval of elements
  - `push(int x)`: puts x on the top of the stack
  - `int pop()`: takes the element on the top of the stack
- Lists: an ordered set of elements
  - `add(int i)`: adds ands an element
  - `int get(int i)`: gets element at index i
- Sets: an unordered set of unique elements (no repeats)
  - `add(int i)`: adds an element
  - `contains(int i)`: returns a boolean for whether or not the set contains the value
- Maps: set of key/value pairs
  - `put(K key, V value)`: puts a key value pair into the map
  - `V get(K key)`: gets the value corresponding to the key

## 10.2 Trees

### Binary Search Trees

### Properties of trees
Trees are composed of:  
- nodes
- edges that connect those nodes.
  - Constraint: there is only one path between any two nodes.

```java
private class BST<Key> {
    private Key key;
    private BST left;
    private BST right;

    public BST(Key key, BST left, BST Right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public BST(Key key) {
        this.key = key;
    }
}
```

### Binary Search Tree Operations

#### Search
We know that the BST is structured such that all elements to the right of a node are greater and all elements to the left are smaller.   
Knowing this, we can start at the root node and compare it with the element, X, that we are looking for.  
If X is greater to the root, we move on to the root's right child.  
If its smaller, we move on to the root's left child.  
We repeat this process recursively until we either find the item or we get to a leaf in which case the tree does not contain the item.

#### Insert
First, we search in the tree for the node.   
If we find it, then we don't do anything.   
If we don't find it, we will be at a leaf node already.  
At this point, we can just add the new element to either the left or right of the leaf, preserving the BST property.  

#### Delete
Deleting from a binary tree is a little bit more complicated because whenever we delete, we need to make sure we reconstruct the tree and still maintain its BST property.  

- Let's break this problem down into three categories:  
  - the node we are trying to delete has no children
  - has 1 child
  - has 2 children

- No children
If the node has no children, it is a leaf, and we can just delete its parent pointer and the node will eventually be swept away by the garbage collector.

- One child
If the node only has one child, we know that the child maintains the BST property with the parent of the node because the property is recursive to the right and left subtrees.  
Therefore, we can just reassign the parent's child pointer to the node's child and the node will eventually be garbage collected.  

- Two children
If the node has two children, the process becomes a little more complicated because we can't just assign one of the children to be the new root.  
This might break the BST property.  
Instead, we choose a new node to replace the deleted one.  

We know that the new node must:  
  - be > than everything in left subtree.
  - be < than everything right subtree.
In the below tree, we show which nodes would satisfy these requirements given that we are trying to delete the dog node.  

This is called Hibbard deletion, and it gloriously maintains the BST property amidst a deletion.  

### BSTs as Sets and Maps
We can also make a binary tree into a map by having each BST node hold (key,value) pairs instead of singular values. 

### Summary
Abstract data types (ADTs) are defined in terms of operations, not implementation.  

- Several useful ADTs:
  - Disjoint Sets, Map, Set, List.
  - Java provides Map, Set, List interfaces, along with several implementations.

We’ve seen two ways to implement a Set (or Map):

ArraySet: $Θ(N)$ operations in the worst case.
BST: $Θ(logN)$ operations if tree is balanced.

BST Implementations:
- Search and insert are straightforward (but insert is a little tricky).
- Deletion is more challenging. Typical approach is “Hibbard deletion”.  

# 11 Balanced Trees

## 11.1 Intro to B-Trees

### Binary Tree Height
- Worst case: $Θ(N)$
- Best case: $Θ(logN)$

The runtimes are dependent on the structure of the tree.  
If the tree is really spindly, then its basically a linked list and the runtime is linear.  
If the tree is bushy, then the height of the tree is 
$logN$ and therefore the runtime grows in $logN$ time

### A short detour into BigO and worst case

BigO is not equivalent to worst case!  
Remember, BigO is an upper bound.  
As long as a function falls within that bound, it is considered to be inside the BigO of that function.   Worst-case is more restrictive than BigO.  

### BST Performance
Some terminology for BST performance:
- depth: the number of links between a node and the root.
- height: the lowest depth of a tree.
- average depth: average of the total depths in the tree.  You calculate this by taking $\frac{{\textstyle \sum_{i=0}^{D}}d_in_i}{N}$ where $d_i$ is depth and $n_i$ is number of nodes at that depth.

### BST insertion order
when we insert randomly into a BST the average depth and height are expected to be $Θ(logN)$.  

## 11.2 B-Trees
The problem with BST's is that we always insert at a leaf node.  
This is what causes the height to increase. Take this nice balanced tree below:  
When we start inserting nodes, we could potentially break the balanced structure.  

Set a limit on the number of elements in a single node. 
When it reach the limit, split the node in half, by bumping up the middle left node.  

By splitting nodes in the middle, we maintain perfect balance. 
These trees are called B-trees or **2-3-4/2-3 Trees**(limit = 3).   
2-3-4 and 2-3 refer to the number of children each node can have. 

### Insertion Process
The process of adding a node to a 2-3-4 tree is:  
- We still always inserting into a leaf node, so take the node you want to insert and traverse down the tree with it, going left and right according to whether or not the node to be inserted is greater than or smaller than the items in each node.
- After adding the node to the leaf node, if the new node has 4 nodes, then pop up the middle left node and re-arrange the children accordingly.
- If this results in the parent node having 4 nodes, then pop up the middle left node again, rearranging the children accordingly.
- Repeat this process until the parent node can accommodate or you get to the root.  

## 11.3 B-Tree invariants and runtime

### B-Tree invariants
A B-tree has the following helpful invariants:
- All leaves must be the same distance from the source.
- A non-leaf node with $k$ items must have exactly $k+1$ children.
- In tandem, these invariants cause the tree to always be bushy. 

### B-Tree runtime analysis
The worst-case runtime situation for search in a B-tree would be if each node had the maximum number of elements in it and we had to traverse all the way to the bottom. 

### Summary
BSTs have best case height $Θ(logN)$, and worst case height $Θ(N)$.  
- Big O is not the same thing as worst case!  

B-Trees are a modification of the binary search tree that avoids $Θ(N)$ worst case.  
- Nodes may contain between $1$ and $L$ items.
- contains works almost exactly like a normal BST.
- add works by adding items to existing leaf nodes.
- If nodes are too full, they split.
- Resulting tree has perfect balance. Runtime for operations is $O(logN)$.
- Have not discussed deletion. See extra slides if you’re curious.
- Have not discussed how splitting works if $L>3$ (see some other class).
- B-trees are more complex, but they can efficiently handle ANY insertion order.

## 11.4 Rotating Trees

### BST structure
However, insertion is not the only way to yield different structures for the same BST. One thing we can do is change the tree with the nodes already in place through a process called rotating.  

### Tree Rotation
The formal definition of roatation is:  
- `rotateLeft(G)`: Let x be the right child of G. Make G the new left child of x.
- `rotateRight(G)`: Let x be the left child of G. Make G the new right child of x.

```java
private Node rotateRight(Node h) {
    // assert (h != null) && isRed(h.left);
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    return x;
}

// make a right-leaning link lean to the left
private Node rotateLeft(Node h) {
    // assert (h != null) && isRed(h.right);
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    return x;
}
```

## 11.5 Red-Black Trees
We choose arbitrarily to make the left element a child of the right one. This results in a left-leaning tree.    
We show that a link is a glue link by making it red.  
Normal links are black. Because of this, we call these structures left-leaning red-black trees (LLRB).  

Left-Leaning Red-Black trees have a 1-1 correspondence with 2-3 trees.  
Every 2-3 tree has a unique LLRB red-black tree associated with it.  
As for 2-3-4 trees, they maintain correspondence with standard Red-Black trees.  

### Properties of LLRB's
- 1-1 correspondence with 2-3 trees.
- No node has 2 red links.
- There are no red right-links.
- Every path from root to leaf has same number of black links (because 2-3 trees have same number of links to every leaf).
- Height is no more than 2x height of corresponding 2-3 tree.

### Inserting into LLRB

- Task 1: insertion color: because in a 2-3 tree, we are always inserting by adding to a leaf node, the color of the link we add should always be red.
- Task 2: insertion on the right: recall, we are using left-leaning red black trees, which means we can never have a right red link. If we insert on the right, we will need to use a rotation in order to maintain the LLRB invariant.
  However, if we were to insert on the right with a red link and the left child is also a red link, then we will temporarily allow it for purposes that will become clearer in task 3.
- Task 3: double insertion on the left: If there are 2 left red links, then we have a 4-node which is illegal. First, we will rotate to create the same tree seen in task 2 above. Then, in both situations, we will flip the colors of all edges touching S. This is equivalent to pushing up the middle node in a 2-3 tree.

#### summary of all the operations:

- When inserting: Use a red link.
- If there is aright leaning “3-node”, we have a Left Leaning Violation
  - Rotate left the appropriate node to fix.
- If there are two consecutive left links, we have an incorrect 4 Node Violation!
  - Rotate right the appropriate node to fix.
- If there are any nodes with two red children, we have a temporary 4 Node.
  - Color flip the node to emulate the split operation.

### Runtime
Because a left-leaning red-black tree has a 1-1 correspondence with a 2-3 tree and will always remain within 2x the height of its 2-3 tree, the runtimes of the operations will take $logN$ time.  

### abstracted code for insertion into LLRB:
```java
private Node put(Node h, Key key, Value val) {
    if (h == null) { return new Node(key, val, RED); }

    int cmp = key.compareTo(h.key);
    if (cmp < 0)      { h.left  = put(h.left,  key, val); }
    else if (cmp > 0) { h.right = put(h.right, key, val); }
    else              { h.val   = val;                    }

    if (isRed(h.right) && !isRed(h.left))      { h = rotateLeft(h);  }
    if (isRed(h.left)  &&  isRed(h.left.left)) { h = rotateRight(h); }
    if (isRed(h.left)  &&  isRed(h.right))     { flipColors(h);      } 

    return h;
}
```

### Summary
- Binary search trees are simple, but they are subject to imbalance which leads to crappy runtime.
- 2-3 Trees (B Trees) are balanced, but painful to implement and relatively slow.
- LLRBs insertion is simple to implement (but deletion is hard).
- Works by maintaining mathematical bijection with a 2-3 trees.
- Java’s TreeMap is a red-black tree (but not left leaning).
- LLRBs maintain correspondence with 2-3 tree, Standard Red-Black trees maintain correspondence with 2-3-4 trees.
- Allows glue links on either side (see Red-Black Tree).
- More complex implementation, but significantly faster.

# 12 Hashing

## 12.1 A first attempt

### Issues with what we've seen so far
- They require that iitmes be comparable
- They give a complexitiy of $\Theta(logN)$

### A first attempt: `DataIndexedIntegerSet`
create an ArrayList of type boolean and size 2 billion.  
Leteverything be false by default.  

- The `add(int x)` method simply sets the x position in our ArrayList to true. This takes $\Theta(1)$ time.
- The `contains(int x)` method simply returns whether the x position in our ArrayList is true or false. This also takes $\Theta(1)$time!

#### **issues** with this approach
- Extremely wasteful. If we assume that a boolean takes 1 byte to store, the above needs 2GB of space per new DataIndexedIntegerSet(). Moreover, the user may only insert a handful of items...
- What do we do if someone wants to insert a String?
  - Let's look at this next. Of course, we may want to insert other things

## 12.2 Inserting words

### Strategy: Avoiding collisions
give each one a number: 
$a=1,b=2,…,z=26$. Now, we can write any unique lowercase string in base 26. 

This representation gives a unique integer to every english word containing lowercase letters, much like using base 10 gives a unique representation to every number.   
We are guaranteed to not have collisions.  

## 12.3 Inserting Strings and Overflow

### Handling Integer Overflow and Hash Codes

### Overflow issues
The largest possible value for integers in Java is 2,147,483,647. The smallest value is -2,147,483,648.  

If you try to take the largest value and add 1, you get the smallest value!  

### Hash Codes
In computer science, taking an object and converting it into some integer is called "computing the hash code of the object"  

- Every Object in Java has a default `.hashcode()` method, which we can use. Java computes this by figuring out where the Object sits in memory (every section of the memory in your computer has an address!), and uses that memory's address to do something similar to what we did with Strings. This methods gives a unique hashcode for every single Java object.
- Sometimes, we write our own hashcode method. For example, given a Dog, we may use a combination of its name, age and breed to generate a hashcode.

### Properties of HashCodes
Hash codes have three necessary properties, which means a hash code must have these properties in order to be valid:
1. It must be an Integer
2. If we run `.hashCode()` on an object twice, it should return the **same** number
3. Two objects that are considered `.equal()` must have the same hash code.

Not all hash codes are created equal, however. If you want your hash code to be considered a good hash code, it should:
- Distribute items evenly

**Note that at this point, we know how to add arbitrary objects to our data structure, not only strings.**  

## 12.4 Handling Collisions

### Handling Collisions
Everything in the array is originally empty.  
If we get a new item, and its hashcode is \$h\$:

- If there is nothing at index \$h\$ at the moment, we'll create a new `LinkedList` for index \$h\$, place it there, and then add the new item to the newly created LinkedList.
- If there is already something at index \$h\$, then there is already a `LinkedList` there. We simply add our new item to that `LinkedList`. 

  Note: Our data structure is not allowed to have any duplicate items / keys.  
  Therefore, we must first check whether the item we are trying to insert is already in this `LinkedList`.  
  If it is, we do nothing!  
  This also means that we will insert to the END of the linked list, since we need to check all of the elements anyways.

### Concrete workflow
- `add` item
  - Get hashcode (i.e., index) of item.
  - If index has no item, create new List, and place item there.
  - If index has a List already, check the List to see if item is already in there. If not, add item to List.
- `contains` item
  - Get hashcode (i.e., index) of item.
  - If index is empty, return false.
  - Otherwise, check all items in the List at that index, and if the item exists, return true.

### Runtime Complexity
- **Why is contains $Θ(Q)$?**
  Because we need to look at all the items in the LinkedList at the hashcode (i.e., index).

- **Why is add $Θ(Q)$?**
  Can't we just add to the beginning of the LinkedList, which takes $Θ(1)$ time? No! Because we have to check to make sure the item isn't already in the linked list.


## 12.5 Hash Table and Fixing Runtime

### Our Final Data Structure: HashTable
What we've created now is called a `HashTable`.

- Inputs are converted by a hash function (`hashcode`) into an integer. Then, they're converted to a valid index using the modulus operator. Then, they're added at that index (dealing with collisions using LinkedLists).
- `contains` works in a similar fashion by figuring out the valid index, and looking in the corresponding LinkedList for the item

### Dynamically growing the hash table

Suppose we have $M$ buckets (indices) and $N$ items. We say that our **load factor** is $N/M$.  

(Note that the load factor is equivalent to our best case runtime from above.)

And note that if we keep M (the number of buckets) fixed, and N keeps increasing, the load factor consistently keeps increasing.  

- Create a new HashTable with 2M buckets.
- Iterate through all the items in the old HashTable, and one by one, add them into this new HashTable.
  - We need to add elements one by one again because since the size of the array changes, the modulus also changes, therefore the item probably belongs in a different bucket in the new hashtable than in the old one.

**Note that resizing the hash table also helps with shuffling the items in the hashtable!**

At this point, assuming items are evenly distributed, all the lists will be approximately $N/M$ items long, resulting in $Θ(N/M)$ runtime. Remember that $N/M$ is only allowed to be under a constant load factor threshold, and so, $Θ(N/M)=Θ(1)$.

Note also that resizing takes $Θ(N)$ time. And each add takes $Θ(1)$ time.  

### Assuming that items are evenly distributed
Items will distribute evenly if we have good hash codes (i.e. hashcodes which give fairly random values for different items.)  


Some general good rules of thumb:

- Use a 'base' strategy similar to the one we developed earlier.
- Use a 'base' that's a small prime.
  - Base 126 isn't actually very good, because using base 126 means that any string that ends in the same last 32 characters has the same hashcode.
  - This happens because of overflow.
  - Using prime numbers helps avoid overflow issues (i.e., collisions due to overflow).
  - Why a small prime? Because it's easier to compute.

# 13 Heaps and Priority Queues

## 13.1 PQ Interface

### The Priority Queue Interface
```java
/** (Min) Priority Queue: Allowing tracking and removal of 
  * the smallest item in a priority queue. */
public interface MinPQ<Item> {
    /** Adds the item to the priority queue. */
    public void add(Item x);
    /** Returns the smallest item in the priority queue. */
    public Item getSmallest();
    /** Removes the smallest item from the priority queue. */
    public Item removeSmallest();
    /** Returns the size of the priority queue. */
    public int size();
}
```

### Priority Queue Implementation
Analyzing the worst case runtimes of our desired operations  

- Ordered Array
  - `add`: $Θ(N)$
  - `getSmallest`: $Θ(1)$
  - `removeSmallest`: $Θ(N)$
- Bushy BST
  - `add`: $Θ(logN)$
  - `getSmallest`: $Θ(logN)$
  - `removeSmallest`: $Θ(logN)$
- HashTable
  - `add`: $Θ(1)$
  - `getSmallest`: $Θ(N)$
  - `removeSmallest`: $Θ(N)$

## 13.2 Heaps

### Heap Structure

We will define our binary min-heap as being complete and obeying min-heap property:  
- Min-heap: Every node is less than or equal to both of its children
- Complete: Missing items only at the bottom level (if any), all nodes are as far left as possible.

### Heap Operations
- `add`: Add to the end of heap temporarily. Swim up the hierarchy to the proper place.
  - Swimming involves swapping nodes if child < parent
- `getSmallest`: Return the root of the heap This is guaranteed to be the minimum by our min-heap property
- `removeSmallest`: Swap the last item in the heap into the root. Sink down the hierarchy to the proper place.
  - Sinking involves swapping nodes if parent > child. Swap with the smallest child to preserve min-heap property.

### Tree Representation

#### Approach 1a, 1b, and 1c
- In approach Tree1A, we consider creating pointers to our children and storing the value inside of the node object. These are hardwired links that give us fixed-width nodes.  
- Alternatively, in Tree1B, we explore the use of arrays as representing the mapping between children and nodes. This would give us variable-width nodes, but also awkward traversals and performance will be worse.
- Lastly, we can use the approach for Tree1C. This will be slightly different from the usual approaches that we've seen. Instead of only representing a node's children, we say that nodes can also maintain a reference to their siblings.

### Approach 2
For representing a tree, we can store the keys array as well as a parents array. The keys array represent which index maps to which key, and the parents array represents which key is a child of another key.  

- The tree is complete. This is a property we have defined earlier.
- The parents array has a sort of redundant pattern where elements are just doubled.
- Reading the level-order of the tree, we see that it matches exactly the order of the keys in the keys array.

### Approach 3
In this approach, we assume that our tree is complete. This is to ensure that there are no "gaps" inside of our array representation. Thus, we will take this complex 2D structure of the tree and flatten it into an array.  

### Swim
Given this implementation, we define the following code for the "swim" described in the Heap Operations section.  
```java
public void swim(int k) {
    if (keys[parent(k)] ≻ keys[k]) {
       swap(k, parent(k));
       swim(parent(k));              
    }
}
```

## 13.3 Implementation Consideration

### The Implementation
- `leftChild(k)` = $k∗2$
- `rightChild(k)`= $k∗2+1$
- `parent(k)` = $k/2$

### Comparing to alternative implementations  

|Methods|Ordered Array|Bushy BST|Hash Table|Heap|
|---|---|---|---|---|
|`add`|$Θ(N)$|$Θ(logN)$|$Θ(1)$|$Θ(logN)$|
|`getSmallest`|$Θ(1)$|$Θ(logN)$|$Θ(N)$|$Θ(1)$|
|`removeSmallest`|$Θ(N)$|$Θ(logN)$|$Θ(N)$|$Θ(logN)$|

- Heap operations are amortized analysis, since the array will have to resize (not a big deal)
- BST's can have constant time getSmallest if pointer is stored to smallest element
- Array-based heaps take around 1/3rd the memory it takes to represent a heap using approach 1A (direct pointers to children)


# 14 A Brief Summary

## 14.1 Data Structures Summary

Search Data Structures

|||||
|---|---|---|---|
|**Name**|**Store Operation(s)**|**Primary Retrieval Operation**|**Retrieve By**|
|List|add(key), insert(key, index)|get(index)|index|
|Map|put(key, value)|get(key)|key identity|
|Set|add(key)|containsKey(key)|key identity|
|PQ|add(key)|getSmallest()|key order (aka key size)|
|Disjoint Sets|connect(int1, int2)|isConnected(int1, int2)|two integer values|

### Abstraction  
Abstraction often happens in layers.  
Abstract Data Types can often contain two abstract ideas boiling down to one implementation.

These two examples tell us that we can often think of an ADT by the use of another ADT.  
And that Abstract Data Types have layers of abstraction, each defining a behavior that is more specific than the idea that came before it.  

# 15 Tries

## 15.1 Introduction to Tries

### Tries
- Balanced Search Tree:
  - contains(x): $Θ(logN)$
  - add(x): $Θ(logN)$
- Resizing Separate Chaining Hash Table:
  - contains(x): $Θ(1)$ (assuming even spread)
  - add(x): $Θ(1)$ (assuming even spread and amortized)

### Inventing the Trie
Tries are a very useful data structure used in cases where keys can be broken into "characters" and share prefixes with other keys  

Here are some key ideas that we will use:  
- Every node stores only one letter
- Nodes can be shared by multiple keys

The Trie is a specific implementation for Sets and Maps that is specialized for strings.  
We give each node a single character and each node can be a part of several keys inside of the trie.  
Searching will only fail if we hit an unmarked node or we fall off the tree  
Short for Retrieval tree, almost everyone pronounces it as "try" but Edward Fredkin suggested it be pronounced as "tree"  


## 15.2 Implementation and Performance

### Implementation

```java
public class TrieSet {
   private static final int R = 128; // ASCII
   private Node root;    // root of trie

   private static class Node {
      private char ch;  
      private boolean isKey;   
      private DataIndexedCharMap next;

      private Node(char c, boolean blue, int R) {
         ch = c; 
         isKey = blue;
         next = new DataIndexedCharMap<Node>(R);
      }
   }
}
```
Each link corresponds to a character if and only if that character exists.  
Therefore, we can remove the Node's character variable and instead base the value of the character from its position in the parent DataIndexedCharMap  

### Performance
Given a Trie with N keys the runtime for our Map/Set operations are as follows:
- add: $Θ(1)$
- contains: $Θ(1)$

We only traverse the length of one key in the worst case ever, which is never related to the number of keys in the trie.  
Therefore, let's look at the runtime through a measurement that can be measured; in terms of L, the length of the key:  
- add: $Θ(L)$
- contains: $O(L)$

### Child Tracking
The problem with this approach was that we would have initialized many null spots that don't contain any children.  

- Alternate Idea #1: Hash-Table based Trie. This won't create an array of 128 spots, but instead initialize the default value and resize the array only when necessary with the load factor.
- Alternate Idea #2: BST based Trie. Again this will only create children pointers when necessary, and we will store the children in the BST. Obviously, we still have the worry of the runtime for searching in this BST, but this is not a bad approach.

- DataIndexedCharMap
  - Space: 128 links per node
  - Runtime: $Θ(1)$
- BST
  - Space: C links per node, where C is the number of children
  - Runtime: $O(logR)$, where R is the size of the alphabet
- Hash Table
  - Space: C links per node, where C is the number of children
  - Runtime: $O(R)$, where R is the size of the alphabet


## 15.3 String Operations

### Prefix Matching
Define a method collect which returns all of the keys in a Trie. The pseudocode will be as follows:
```java
collect():
    Create an empty list of results x
    For character c in root.next.keys():
        Call colHelp(c, x, root.next.get(c))
    Return x

colHelp(String s, List<String> x, Node n):
    if n.isKey:
        x.add(s)
    For character c in n.next.keys():
        Call colHelp(s + c, x, n.next.get(c))
```

Method keysWithPrefix which returns all keys that contain the prefix passed in as an argument  
```java
keysWithPrefix(String s):
    Find the end of the prefix, alpha
    Create an empty list x
    For character in alpha.next.keys():
        Call colHelp("sa" + c, x, alpha.next.get(c))
    Return x
```

### Autocomplete
One way to achieve this is using a Trie! We will build a map from strings to values.  
Values will represent how important Google thinks that string is (Probably frequency)
Store billions of strings efficiently since they share nodes, less wasteful duplicates
When a user types a query, we can call the method keysWithPrefix(x) and return the 10 strings with the highest value  

### Summary
- Tries theoretically have better performances for searching and insertion than hash tables or balanced search trees  
- There are more implementations for how to store the children of every node of the trie, specifically three. These three are all fine, but hash table is the most natural
  - `DataIndexedCharMap` (Con: excessive use of space, Pro: speed efficient)
  - `Bushy BST` (Con: slower child search, Pro: space efficient)
  - `Hash Table` (Con: higher cost per link, Pro: space efficient)
- Tries may not actually be faster in practice, but they support special string operations that other implementations don't
  - `longestPrefixOf` and `keysWithPrefix` are easily implemented since the trie is stored character by character
  - `keysWithPrefix` allows for algorithms like autocomplete to exist, which can be optimized through use of a priority queue.=

|||||
|---|---|---|---|
|key|type|get(x)|add(x)|
|Balanced BST|comparable|$Θ(logN)$|$Θ(logN)$|
|RSC Hash Table|hashable|$Θ(1)​†$|$Θ(1)​∗†$|
|Data Indexed Array|chars|$Θ(1)$|$Θ(1)$|
|Tries (BST, HT, DICM)|Strings|$Θ(1)$|$Θ(1)$|

$*$: Indicates "on average";   
$†$: Indicates items are evenly spread.  


# 16 QuadTrees

## 16.1 Uniform Partitioning

Initial Attempt: HashTable  
Question: If our set of suns were stored in a HashTable, what is the runtime for finding the answer to our Nearest Neighbors question?  

Solution: The bucket that each object resides in is effectively random, and so we would have to iterate over all $N$ items to check if each sun could possibly be the closest to the horse. $Θ(N)$.  

The problem with hash tables is that the bucket number of an item is effectively random. Hash tables are, by definitely, unordered collections. One fix is to ensure that the bucket numbers depend only on position!  

## 16.2 QuadTrees

### X-Based Tree or Y-Based Tree
One key advantage of Search Trees over Hash Tables is that trees explicitly track the order of items. For example, finding the minimum item in a BST is $Θ(logN)$ time, but $Θ(N)$ in a hash table. Let's try to leverage that to our advantage here to give us better performance for our motivating goals.  

### QuadTree
Also note that QuadTrees are a form of spatial partitioning in disguise. Similar to how uniform partitioning created a perfect grid before, QuadTrees hierarchically partition by having each node "own" 4 subspaces.  

Effectively, spaces where there are many points are broken down into more finely divided regions, and in many cases this gives better performances.  

### Range Search using a QuadTree
Notice that with the 4-way division imposed by each node of the QuadTree, we still have the pruning effect that was so advantageous in our X-Based Tree and Y-Based Tree!  
If we are looking for points inside a green rectangle as shown below, from any node we can decide whether the green rectangle lies within one or more quadrants, and only explore the branches/subtrees corresponding to those quadrants.  
All other quadrants can be safely ignored and pruned away. Below, we see that the green rectangle lies only in the northeast quadrant, and so the NW, SE, and SW quadrants can all be pruned away and left unexplored. We can proceed recursively.


## 16.3 K-D Trees

One way we can extend the hierarchical partitioning idea to dimensions greater than two is by using a K-D Tree. It works by rotating through all the dimensions level by level.  

So for the 2-D case, it partitions like an X-based Tree on the first level, then like a Y-based Tree on the next, then as an X-based Tree on third level, a Y-based Tree on the fourth, etc.  

For the 3-D case, it rotates between each of the three dimensions every three levels, and so on and so forth for even higher dimensions.  
Here you can see the advantages in a K-D Tree in how it is more easily generalized to higher dimensions.  
But, no matter how high the dimensions get, a K-D tree will always be a binary tree, since each level is partitioned into "greater" and "less than".  

### Nearest Neighbor using a K-D Tree

To find the point that is the nearest neighbor to a query point, we follow this procedure in our K-D Tree:  

- Start at the root and store that point as the "best so far". Compute its distance to the query point, and save that as the "score to beat". In the image above, we start at A whose distance to the flagged point is 4.5.
- This node partitions the space around it into two subspaces. For each subspace, ask: "Can a better point be possibly found inside of this space?" This question can be answered by computing the shortest distance between the query point and the edge of our subspace (see dotted purple line below).
- Continue recursively for each subspace identified as containing a possibly better point.
- In the end, our "best so far" is the best point; the point closest to the query point.


# 17 Tree Traversals and Graphs

## 17.1 Tree recap
A tree consists of:  
- A set of nodes (or vertices). We use both terms interchangeably.
- A set of edges that connect those nodes.
  - Constraint: There is exactly one path between any two nodes.

## 17.2 Tree Traversal
Traversal a tree  
1. Level order traversal.
2. Depth-First traversals –– of which there are three: pre-order, in-order and post-order.

### Level Order Traversal
Iterate by levels, left to right.

### Pre-order Traversal
Visit the root (aka, do the action you want to do.) The action here is "print".  
```java
preOrder(BSTNode x) {
    if (x == null) return;
    print(x.key)
    preOrder(x.left)
    preOrder(x.right)
}
```

### In-order Traversal
Here, instead of visiting (aka printing) first, we'll first visit the left branch. Then we'll print. Then we'll visit the right branch.  
```java
inOrder(BSTNode x) {
    if (x == null) return;    
    inOrder(x.left)
    print(x.key)
    inOrder(x.right)
}
```

### Post-order Traversal
Again, same big-picture idea, but now we'll print left branch, then right branch, then ourselves.  
```java
postOrder(BSTNode x) {
    if (x == null) return;    
    postOrder(x.left)
    postOrder(x.right)
    print(x.key)   
}
```

## 17.3 Graphs
A graph consists of:  
- A set of nodes (or vertices)
- A set of zero of more edges, each of which connects two nodes.

In general, note that all trees are also graphs, but not all graphs are trees.  

### Simple Graphs only
Graphs can be divided into two categories: simple graphs and multigraphs  

And then there are cyclic graphs, i.e., there exists a way to start at a node, follow some unique edges, and return back to the same node you started from.  

## 17.4 Graph Problems

- s-t Path: Is there a path between vertices s and t?
- Connectivity: Is the graph connected, i.e. is there a path between all vertices?
- Biconnectivity: Is there a vertex whose removal disconnects the graph?
- Shortest s-t Path: What is the shortest path between vertices s and t?
- Cycle Detection: Does the graph contain any cycles?
- Euler Tour: Is there a cycle that uses every edge exactly once?
- Hamilton Tour: Is there a cycle that uses every vertex exactly once?
- Planarity: Can you draw the graph on paper with no crossing edges?
- Isomorphism: Are two graphs isomorphic (the same graph in disguise)?

### s-t path
```java
mark s  // i.e., remember that you visited s already
if (s == t):
    return true;

for child in unmarked_neighbors(s): // if a neighbor is marked, ignore!
    if isconnected(child, t):
        return true;

return false;
```

we just developed a depth-first traversal (like pre-order, post-order, in-order) but for graphs. What did we do? Well, we marked ourself.  
Then we visited our first child. Then our first child marked itself, and visited its children.  
Then our first child's first child marked itself, and visited its children.  

# 18 Graph Traversals and representation

## 18.1 BFS  
BFS (Breadth First Search) (also known as Level Order Traversal).  
In DFS, we visit down the entire lineage of our first child before we even begin to look at our second child - we literally search depth first.  
In BFS, we visit all of our immediate children before continuing on to any of our grandchildren.  
In other words, we visit all nodes 1 edges from our source. Then, all nodes 2 edges from our source, etc.  
The pseudocode for BFS  
```
Initialize the fringe (a queue with the starting vertex) and mark that vertex.
Repeat until fringe is empty:
Remove vertex v from the fringe.
For each unmarked neighbor n of v:
Mark n.
Add n to fringe.
Set edgeTo[n] = v.
Set distTo[n] = distTo[v] + 1.
```

A fringe is just a term we use for the data structure we are using to store the nodes on the frontier of our traversal's discovery process (the next nodes it is waiting to look at). For BFS, we use a queue for our fringe.  
`edgeTo[...]` is a map that helps us track how we got to node n; we got to it by following the edge from `v` to to `n`.  
`distTo[...]` is a map that helps us track how far n is from the starting vertex.  
Assuming that each edge is worth a distance of 1, then the distance to n is just one more than the distance to get to `v`  

### DFS vs BFS
Note however that DFS and BFS differ in more than just their fringe data structure.  
They differ in the order of marking nodes.  
For DFS we mark nodes only once we visit a node - aka pop it from the fringe.  
As a result, it's possible to have multiple instances of the same node on the stack at a time if that node has been queued but not visited yet.  
With BFS we mark nodes as soon as we add them to the fringe so this is not possible.
Recursive DFS implements this naturally via the recursive stack frames; iterative DFS implements it manually:  
```
Initialize the fringe, an empty stack
    push the starting vertex on the fringe
    while fringe is not empty:
        pop a vertex off the fringe
        if vertex is not marked:
            mark the vertex
            visit vertex
            for each neighbor of vertex:
                if neighbor not marked:
                    push neighbor to fringe
```

## 18.2 Representing Graphs

### Graph API
An API (Application Programming Interface) is a list of methods available to a user of our class, including the method signatures (what arguments/parameters each function accepts) and information regarding their behaviors.  

For our Graph API, let's use the common convention of assigning each unique node to an integer number.  
Doing so allows us to define our API to work with integers specifically, rather than introducing the need for generic types.  

```java
public class Graph {
    public Graph(int V):               // Create empty graph with v vertices
    public void addEdge(int v, int w): // add an edge v-w
    Iterable<Integer> adj(int v):      // vertices adjacent to v
    int V():                           // number of vertices
    int E():                           // number of edges
```

### Graph Representations
#### Adjacency Matrix
One way we can do this is by using a 2D array. There is an edge connecting vertex s to t iff that corresponding cell is 1 (which represents true).  
Notice that if the graph is undirected, the adjacency matrix will be symmetric across its diagonal (from the top left to the bottom right corners).  

#### Adjacency Lists
Another way is to maintain an array of lists, indexed by vertex number. Iff there is an edge from s to t, the list at array index s will contain t.  

In practice, adjacency lists are most common since graphs tend to be sparse (there are not many edges in each bucket).  

Further, DFS/BFS on a graph backed by adjacency lists runs in 
$O(V+E)$, while on a graph backed by an adjacency matrix runs in $O(V^2)$

Runtime of some basic operations for each representation:  

|||||||
|---|---|---|---|---|---|
|idea|`addEdge(s, t)`|`for(w : adj(v))`|`print()`|`hasEdge(s, t)`|`space used`|
|adjacency matrix|$Θ(1)$|$Θ(V)$|$Θ(V2)$|$Θ(1)$|$Θ(V2)$|
|list of edges|$Θ(1)$|$Θ(E)$|$Θ(E)$|$Θ(E)$|$Θ(E)$|
|adjacency list|$Θ(1)$|$Θ(1) to Θ(V)$|$Θ(V+E)$|$Θ(degree(v))$|$Θ(E+V)$|

# 19 Shortest Paths

## 19.1 Recap

### recalls
- DFS is worse for spindly graphs. Imagine a graph with 10000 nodes all spindly. We'll end up making 10000 recursive calls, which is bad for space.
- BFS is worse for "bushy" graphs, because our queue gets used a lot.

We developed an algorithm that works well on graphs with no edge labels. Here's what we did: we developed an algorithm that finds us the shortest (where shortest means the fewest number of edges) paths from a given source vertex.  

## 19.2 Dijkstra's

### Dijkstra's Algorithm

Dijkstra's algorithm takes in an input vertex $s$, and outputs the shortest path tree from $s$. How does it work?

1. Create a priority queue.
2. Add $s$ to the priority queue with priority $0$ Add all other vertices to the priority queue with priority $∞$.
3. While the priority queue is not empty: pop a vertex out of the priority queue, and relax all of the edges going out from the vertex.

Look at your current best distance to `w` from the source, call it 
`curBestDistToW`.  
Now, look at your `curBestDistToV+weight(v,w)` (let's call it `potentialDistToWUsingV`) .  
Is `potentialDistToWUsingV` better, i.e., smaller than `curBestDistToW`?  
In that case, set `curBestDistToW=potentialDistToWUsingV`, and update the `edgeTo[w]` to `v`.  

Important note: we never relax edges that point to already visited vertices.  

### Pseudocode

```python
def relax(edge p,q):
   if q is visited (i.e., q is not in PQ):
       return
   if (distTo[p] + weight(edge)) < distTo[q]:
       distTo[q] = distTo[p] + w
       edgeTo[q] = p
       PQ.changePriority(q, distTo[q])

def dijkstras(source):
    PQ.add(source, 0)
    For all other vertices, v, PQ.add(v, infinity)
    while PQ is not empty:
        p = PQ.removeSmallest()
        relax(all edges from p)
```

Dijkstra's algorithm is not guaranteed to be correct for negative edges. It might work... but it isn't guaranteed to work.  

### A noteworthy invariant
Observe that once a vertex is popped off the priority queue, it is never re-added. Its distance is never re-updated. So, in other words, once a vertex is popped from the priority queue, we know the true shortest distance to that vertex from the source.  

## 19.3 A*

### Introducing: A Star
In Dijkstra's, we used `bestKnownDistToV` as the priority in our algorithm.  
This time, we'll use `bestKnownDistToV+estimateFromVToGoal` as our heuristic.  

Well, it's called an estimate because it's exactly that. We use A* to get the true shortest path from a source to a target, but the estimate is something we approximate.  

### Bad Heuristics

The takeaway here is that heuristics need to be good. There are two definitions required for goodness.

1. Admissibility. `heuristic(v, target) ≤ trueDistance(v, target)`. (Think about the problem above. The true distance from the neighbor of C to C wasn't infinity, it was much, much smaller. But our heuristic said it was ∞, so we broke this rule.)
2. Consistency. For each neighbor v of w:
   - `heuristic(v, target) ≤ dist(v, w) + heuristic(w, target)`
   - where dist(v, w) is the weight of the edge from v to w.

# 20 Minimum Spanning Trees

## 20.1 MSTs and Cut Property
### Minimum Spanning Trees

A minimum spanning tree (MST) is the lightest set of edges in a graph possible such that all the vertices are connected.   Because it is a tree, it must be connected and acyclic. And it is called "spanning" since all vertices are included.  

In this chapter, we will look at two algorithms that will help us find a MST from a graph.  

Before we do that, let's introduce ourselves to the Cut Property, which is a tool that is useful for finding MSTs.  

### Cut Property

We can define a cut as an assignment of a graph’s nodes to two non-empty sets (i.e. we assign every node to either set number one or set number two).  

We can define a crossing edge as an edge which connects a node from one set to a node from the other set.  

With these two definitions, we can understand the Cut Property; given any cut, the minimum weight crossing edge is in the MST.  

## 20.2 Prim's and Kruskal's

### Prim's Algorithm
This is one algorithm to find a MST from a graph. It is as follows:  
1. Start from some arbitrary start node.
2. Repeatedly add the shortest edge that has one node inside the MST under construction.
3. Repeat until there are V-1 edges.

Prim's algorithm works because at all stages of the algorithm, if we take all the nodes that are part of our MST under construction as one set, and all other nodes as a second set, then this algorithm always adds the lightest edge that crosses this cut, which is necessarily part of the final MST by the Cut Property.  

Essentially, this algorithm runs via the same mechanism as Dijkstra's algorithm, but while Dijkstra's considers candidate nodes by their distance from the source node, Prim's looks at each candidate node's distance from the MST under construction.  

Thus, the runtime of Prim's if done using the same mechanism as Dijkstra's, would be the same as Dijkstra's, which is $O((∣V∣+∣E∣)log∣V∣)$.  
Remember, this is because we need to add to a priority queue fringe once for every edge we have, and we need to dequeue from it once for every vertex we have.  

### Kruskal's Algorithm

This is another algorithm that can be used to find a MST from a graph.  
The MST returned by Kruskal's might not be the same one returned by Prim's, but both algorithms will always return a MST; since both are minimal (optimal), they will both give valid optimal answers (they are tied as equally minimal / same total weight, and this is as low as it can be).  

The algorithm is as follows:

1. Sort all the edges from lightest to heaviest.
2. Taking one edge at a time (in sorted order), add it to our MST under construction if doing so does not introduce a cycle.
3. Repeat until there are {% math %}V-1{% endmath %} edges.

Kruskal's algorithm works because any edge we add will be connecting one node, which we can say is part of one set, and a second node, which we can say is part of a second set.  
This edge we add is not part of a cycle, because we are only adding an edge if it does not introduce a cycle.  
Further, we are looking at edge candidates in order from lightest to heaviest.  
Therefore, this edge we are adding must be the lightest edge across this cut (if there was a lighter edge that would be across this cut, it would have been added before this, and adding this one would cause a cycle to appear).  
Therefore, this algorithm works by the Cut Property as well.  

Kruskal's runs in $O(∣E∣log∣E∣)$ time because the bottleneck of the algorithm is sorting all of the edges to start (for example, we can use heap sort, in which we insert all of the edges into a heap and remove the min one at a time).  
If we are given pre-sorted edges and don't have to pay for that, then the runtime is $O(∣E∣log∗∣V∣)$.  
This is because with every edge we propose to add, we need to check whether it will introduce a cycle or not.  
One way we know how to do this is by using Weighted Quick Union with Path Compression; this will efficiently tell us whether two nodes are connected (unioned) together already or not.  
This will cost $∣E∣$ calls on isConnected, which costs $O(log∗∣V∣)$ each, where $log∗$ is the Ackermann function.  