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