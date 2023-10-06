# POINTERS
## What is a pointer?
A pointer is a variable that holds the memory address of another variable instead of a real value.

## Variables in C
Before talking about pointers, we need to understand how variables are stored in C. There are two key aspects when storing a variable:
- Value and address
- Size

### Value
Every variable has two different values associated:
- Memory address where the variable is stored. In general for a 32-bit computer machine the size of a pointer would be 4 bytes while for a 64-bit computer machine, it would be 8 bytes.
- Value of the variable.
```shell
int number = 5;
```

In the previous example, the computer will store the value `5` linked to the variable `number` in a memory address (e.g. `0x7ff7bae9f428`).

### Size
Each variable requires a specific amount of memory. Here the most common variables and the space required for each one:

|     Type      | Range | Size (in bytes) |
|:-------------:| :---: | :---: |
| unsigned char | 0 to 255 | 1 |
| signed char or char | -128 to +127 | 1 |
| unsigned int | 0 to 65535 | 2 |
| signed int or int | -32,768 to +32767 | 2 |
| unsigned short int | 0 to 65535 | 2 |
| signed short int or short int | -32,768 to +32767 | 2 |
| unsigned long int | 0 to +4,294,967,295 | 4 |
| signed long int or long int | -2,147,483,648 to +2,147,483,647 | 4 |
| long double | 3.4E-4932 to 1.1E+4932 | 10 |
| double | 1.7E-308 to 1.7E+308 | 8 |
| float | 3.4E-38 to 3.4E+38 | 4 |

When the computer runs the program, it allocates some amount of memory based on the variable type.

## Pointer declaration and manipulation
### Pointer declaration (*)
Pointers are declared with `*`. With `x`, we are telling the compiler that this variable will store an address, not a real value. The following line declares a pointer to a variable type integer:
```shell
int *pNumber;
```

Similar to regular variables, pointers have a value and are stored in a specific memory address. Additionally, its value points to another memory address. These are the key aspects of a pointer:
- Memory address: as any other variable, pointers are stored in a memory address. In general for a 32-bit computer machine the size of a pointer would be 4 bytes while for a 64-bit computer machine, it would be 8 bytes.
- Value: the value of the pointer is another memory address.
- Value referenced: we will use this terminology to talk about the value of the variable pointed by the pointer. When declaring a pointer, we must say the type of value that it is pointing to.

When a pointer is declared, it must be given a type (e.g. int * -> int, char * -> char) (i.e. strongly typed), reason being that we do not use the pointer variables only to store memory addresses, but we also use them to dereference these addresses so that we can access and modify the values in these addressed. Without the type, the compiler won't know what type of value we will be working with. An easy example is a point to integer; an integer requires 4 bytes, hence 4 positions of memory. If we declare the pointer as unsigned char, the compiler will build the value based only on the 1st address (LSB) of the whole integer, leaving the three other byes out.

### Pointer initialization (&)
Pointers are initialized with `&`, where &x can be read as "the address of the variable x".
```shell
printf("Variable memory address = %p\n", &number);
printf("Variable value = %d\n", number);
printf("Pointer memory address = %p\n", &pNumber);
printf("Pointer value = %p\n", pNumber);
printf("Pointer referenced value = %d\n", *pNumber);
```

This is the outcome of that code:
```shell
Variable memory address = 0x7ff7bed9d428
Variable value = 65536
Pointer memory address = 0x7ff7bed9d420
Pointer value = 0x7ff7bed9d428
Pointer referenced value = 65536
```

In the previous instruction, the computer allocates 4 bytes for the variable `number`. Since `number` is an integer, it means the computer allocates 4 bytes for that variable. Assuming that the address of the variable is `0x7ff7bed9d428`, and knowing that the minimum value addressed by an address is 1 byte, the program will understand that integer will be stored in the following positions:
- `0x7ff7bed9d428`, `0x7ff7bed9d429`, `0x7ff7bed9d42a`, `0x7ff7bed9d42b`

### Pointer access (*, dereference, value at address)
Pointers value can be access with `*`, call the dereference operator, which can be read as "value at address". The following example will print the same value, 5:
```shell
int number = 5;
int *pNumber = &number;
printf("Variable number = %d\n", number);
printf("Variable number address = %d\n", *pNumber);
```

This means we can modify the value of the original variable through the pointer. Let's see the following example:
```shell
int number = 65536;
int *pNumber = &number;
printf("Variable value = %d\n", number);
*pNumber = 3;
printf("Variable value = %d\n", number);
```

The code above will print 65536 first and then, 3, even though we are not using the variable number but to assign the original value.

### Pointer manipulation or arithmetic
In the same way we can modify the value of a variable, we can modify the value of a pointer. The only different is that in the case of the variable, we are modifying the value itself and in the case of a pointer, we are modifying the address at which the pointer points to. Let's see an example:
```shell
int number = 65536;
int number2 = 1;
int *pNumber = &number;
printf("Variable referred value = %d\n", *pNumber);
pNumber = &number2;
printf("Variable referred value = %d\n", *pNumber);
```

The code above would print:
```shell
Variable referred value = 65536
Variable referred value = 1
```

Following the Pointer access section, if we increment in 1 the value of the pointer, it will point to the next integer, which will be located 4 bytes away. Let's see the following instruction:
```shell
printf("Variable number address = %p\n", pNumber + 1);
```

That instruction above will print `0x7ff7bed9d42c` (4 bytes after the original address).

Let's see now another example where the same variable is declared, but we declared the pointer as unsigned char (1 byte):
```shell
int number = 65535;
unsigned char *pNumber = &number;
printf("Variable number = %d\n", number);
printf("Variable number via pointer = %x\n", *pNumber);
printf("Variable number via pointer = %x\n", *(pNumber + 1));
printf("Variable number via pointer = %x\n", *(pNumber + 2));
printf("Variable number via pointer = %x\n", *(pNumber + 3));
```

The value 65535 is 0x0000FFFF. The previous code will print:
```shell
Variable number = 65535
Variable number via pointer = ff
Variable number via pointer = ff
Variable number via pointer = 0
Variable number via pointer = 0
```

If we add just 1 to the number, the new value will be 65536, which is 0x00010000. This is what is printed with the value 65536:
```shell
Variable number = 65536
Variable number via pointer = 0
Variable number via pointer = 0
Variable number via pointer = 1
Variable number via pointer = 0
```

Here we can see:
1. We are accessing to the individual bytes of the integer.
2. The computer stores the LSB in the 1st position of the memory.

### Pointer casting
Pointers can be cast from one type of another. For instance, I could have a function that returns a pointer to void, and we could convert that pointer to void to pointer to integer.
```shell
int a = 1000;
int *p = &a;
char *p0 = (char*)p;
```

### Pointer of pointer
In the same way a pointer stores the value to a variable, a pointer could store the address of a pointer which could point to the variable. Let's see an example:
```shell
int number = 65536;
int *pNumber = &number;
int **ppNumber = &pNumber;
printf("Variable memory address = %p\n", &number);
printf("Variable value = %d\n", number);
printf("Pointer memory address = %p\n", &pNumber);
printf("Pointer value = %p\n", pNumber);
printf("Pointer referenced value = %d\n", *pNumber);
printf("Pointer memory address = %p\n", &ppNumber);
printf("Pointer value = %p\n", ppNumber);
printf("Pointer referenced value = %p\n", *ppNumber);
printf("Pointer referenced referenced value = %d\n", **ppNumber);
```

The outcome of that code would be:
```shell
Variable memory address = 0x7ff7b385d428
Variable value = 65536
Pointer memory address = 0x7ff7b385d420 (this is the memory address of the pointer)
Pointer value = 0x7ff7b385d428 (this is the memory address or the variable)
Pointer referenced value = 65536 (this is the value of the variable)
Pointer memory address = 0x7ff7b385d418 (this is the memory address of the pointer pointer)
Pointer value = 0x7ff7b385d420 (this is the memory address of the pointer)
Pointer referenced value = 0x7ff7b385d428 (this is value of the pointer, which is the address of the variable)
Pointer referenced referenced value = 65536 (this is the value of the variable)
```

## Generic pointer
C allows the creation of generic pointers called Void pointers. Let's see the following example:
```shell
int a = 1000;
int *p = &a;
void *p0; //declaration of generic pointer
p0 = p;
```

As we can see, we don't need a special casting to convert from int* to void* as we are converting from the specific to the generic pointer. We would require the casing if it were the other way around (from generic to specific).

Additionally, since the generic or void pointer type is not mapped to a particular data type, if we try to dereference this particular pointer we will get a compilation error. We will get a compilation error as well if we try to do arithmetic with the generic pointer as if we do `p0+1`, since the pointer does not have any specific data type, the compiler does not know how many bytes that `+1` represents.

## Pointers and functions
### Pointers as function arguments - call by reference
Arguments are passed by value in C (as they are created as local variables within the function). if we want the arguments to be passed by reference, we can use pointers as function arguments. Let's see this example:
```shell
void increment(int *);
int main() {
    int number = 10;
    printf("Variable value = %d\n", number);
    increment(&number);
    printf("Variable value = %d\n", number);
    return 0;
}
void increment(int * number) {
    *number = *number + 1;
}
```

The result of the code above would be:
```shell
Variable value = 10
Variable value = 11
```

### Pointers as function returns
A pointer is just another type of data, hence they can be returned by a function. This is useful when we want the calling function to access a variable created within the called function. Let's see the following example:
```shell
int * add(int *a, int *b) {
    int c = *a + *b;
    return &c;
}

int main() {
    int a = 2, b = 4;
    printf("Address of a in main: %p\n", &a);
    printf("Address of b in main: %p\n", &b);
    printf("Value of a in main: %d\n", a);
    printf("Value of b in main: %d\n", b);
    int *c = add(&a, &b);
    printf("Sum = %d\n", *c);
}
```

The result of the previous code is:
```shell
Address of a in main: 0x7ff7bce833bc
Address of b in main: 0x7ff7bce833b8
Value of a in main: 2
Value of b in main: 4
Sum = 6
```

But let's do just a single change in the code, let's add a printf call after add:
```shell
int main() {
    int a = 2, b = 4;
    printf("Address of a in main: %p\n", &a);
    printf("Address of b in main: %p\n", &b);
    printf("Value of a in main: %d\n", a);
    printf("Value of b in main: %d\n", b);
    int *c = add(&a, &b);
    printf("Address of c in main: %p\n", c);
    printf("Sum = %d\n", *c);
}
```

Now this is the result we get:
```shell
Address of a in main: 0x7ff7b45563bc
Address of b in main: 0x7ff7b45563b8
Value of a in main: 2
Value of b in main: 4
Address of c in main: 0x7ff7b455638c
Sum = 32759
```

As we can see, now the result is not the one expected. The reason is very simple:
- Once the function add is done, it gets removed from the stack memory (but c is still pointing to the region where the stack memory was dedicated for the add function).
- After the add function, we call the printf function. For that function, another part of the memory will be allocated on top of the main function (which is the only one in the stack right now). That memory allocation is very likely to be put where the add memory was, and since the pointer c is still pointing to that memory allocation, the value will be overwritten.

Anything on the heap or global section can be accessed safely across the entire lifecycle of the program, but not the stack memory. To solve the previous issue, we need to store c in the heap memory. Let's see the example:
```shell
int * add(int *a, int *b) {
    int *c = (int*)malloc(sizeof(int));
    *c = *a + *b;
    return c;
}

int main() {
    int a = 2, b = 4;
    printf("Address of a in main: %p\n", &a);
    printf("Address of b in main: %p\n", &b);
    printf("Value of a in main: %d\n", a);
    printf("Value of b in main: %d\n", b);
    int *c = add(&a, &b);
    printf("Address of c in main: %p\n", c);
    printf("Sum = %d\n", *c);
    free(c);
}
```

Now, even with the printf statement after the call to add, the result returned is the right one.

### Pointers to functions
Function pointers in C are variables that can store the memory address of functions and can be used in a program to create a function call to functions pointed by them. Function pointers in C need to be declared with an asterisk symbol and function parameters (same as the function they will point to) before using them in the program. Declaration of function pointers in C includes the return type and data type of different function arguments.
```shell
void test() {
    return ;
}

int main() {
    int a = 5;
    printf("Address of variable = %p\n", &a);
    printf("Address of a function = %p", test);
    return 0;
}
```

In the previous example, we are printing the address of a function. A function is just a set of instructions that are stored in memory, and that function has an initial instruction, which is where the pointer points. Depending on the architecture and the instruction set, each instruction occupies a certain amount of memory. This is the output of the code above:
```shell
Address of variable = 0x7ff7b9a193b8
Address of a function = 0x1064e9f20
```

This is the pattern to declare a point to function in C: `return_type (* pointer_name) (datatype_arg_1, datatype_arg_1, ...);`. The parenthesis around the pointer name is required to declare the pointer because if we don't use it, the syntax is the same as the one to declare a regular function. Let's see the following example:
```shell
int areaRectangle(int, int);

int main() {
    int length, breadth, area;
    int (*fp)(int, int); //Pointer to function, returning an int and accepting two different int as parameters
    printf("Enter length and breadth of a rectangle\n");
    scanf("%d%d", &length, &breadth);
    fp = areaRectangle; //Asigning the address of areaRectangle to the pointer
    area = (*fp)(length, breadth); //Calling the function by using the pointer
    printf("Area of rectangle = %d", area);
    return 0;
}

int areaRectangle(int l, int b) {
    int area_of_rectangle = l * b;
    return area_of_rectangle;
}
```

We can use array of pointers if we want to add more than one function to the pointer. Let's see the following example:
```shell
float add(int, int);
float multiply(int,int);
float divide(int,int);
float subtract(int,int);

int main() {
    int a, b;
    float (*operation[4])(int, int); //Declaration of array of pointers to functions

    operation[0] = add;
    operation[1] = subtract;
    operation[2] = multiply;
    operation[3] = divide;
    
    printf("Enter two values ");
    scanf("%d%d", &a, &b);
    
    float result = (*operation[0])(a, b);
    printf("Addition (a+b) = %.1f\n", result);
    
    result = (*operation[1])(a, b);
    printf("Subtraction (a-b) = %.1f\n", result);
    
    result = (*operation[2])(a, b);
    printf("Multiplication (a*b) = %.1f\n", result);
    
    result = (*operation[3])(a, b);
    printf("Division (a/b) = %.1f\n", result);
    
    return 0;
}

float add(int a, int b) { return a + b; }

float subtract(int a, int b) { return a - b; }

float multiply(int a, int b) { return a * b; }

float divide(int a, int b) { return a / (b * 1.0); }
```

### Pointer functions as function parameters
Function pointers can be passed as an argument to functions, and then a function that would receive a function pointer as argument can call back the function that this pointer will point to.

When reference to a function is passed to another function, that particular function is called a callback function. Since the declaration of a function itself creates a pointer, we don't need to explicitly create a pointer and assign it to send the function as a parameter. The following two lines of code have the same effect:
```shell
void (*ptf)() = a;
b(ptf); //ptf pointing to a is the callback function
b(a); //a is the callback function.
```

When a callback function comes in handy? Let's assume that we have a function that orders an array, but we don't know in advance whether it'll be ascending or descending order, or even if we can implement a non-standard order. We can pass the compare function as a parameter. Let's see the following example where two different functions are passed to the same ordering function:
```shell
/*
 * Callback function compares two integers, should return 1 if the first element has higher rank, -1 if the second element has higher rank, 0 if both have the same rank
 */
void bubbleSort(int *array, int size, int (*compare)(const void*, const void*)) {
    int i, j, temp;
    for(i = 0; i < size; i ++) {
        for(j = 0; j < size - 1; j ++) {
            if(compare(array + j, &array[j + 1]) > 0) { //Two different ways of passing the pointer to the element
                temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
            }
        }
    }
}

int compare(const void *a, const void *b) {
    int A = *((int*)a);
    int B = *((int*)b);
    return A - B;
}

int compare2(const void *a, const void *b) {
    int A = *((int*)a);
    int B = *((int*)b);
    return B - A;
}

int main() {
    int i, a[] = {3, 2, 1, 5, 6, 4};
    bubbleSort(a, 6, compare);
    for(i = 0; i < 6; i ++) {
        printf("%d ", a[i]);
    }
    printf("\n");
    bubbleSort(a, 6, compare2);
    for(i = 0; i < 6; i ++) {
        printf("%d ", a[i]);
    }
}
```


## Pointers and arrays
By using pointer arithmetics, we can work with arrays and iterate them. Let's see this example;
```shell
int array[5] = {1, 2, 3, 4, 5};
int *pArray = array; //this is the same as = &array[0], as array = address of array[0]
printf("First element of the array = %d\n", *pArray); //this is the same as *array, as array is the address of array[0], hence it can be dereferenced
printf("Address of the first element via pointer = %p and via array itself = %p and individual access = %p\n", pArray, array, &array[0]);
printf("Second element of the array = %d\n", *(pArray + 1));
printf("Address of the second element via pointer = %p and via array itself = %p and individual access = %p\n", pArray + 1, array + 1, &array[1]);
printf("Third element of the array = %d\n", *(pArray + 2));
printf("Address of the third element via pointer = %p and via array itself = %p and individual access = %p\n", pArray + 2, array + 2, &array[2]);
```

The code above would print:
```shell
First element of the array = 1
Address of the first element via pointer = 0x7ff7b2fab410 and via array itself = 0x7ff7b2fab410 and individual access = 0x7ff7b2fab410
Second element of the array = 2
Address of the second element via pointer = 0x7ff7b2fab414 and via array itself = 0x7ff7b2fab414 and individual access = 0x7ff7b2fab414
Third element of the array = 3
Address of the third element via pointer = 0x7ff7b2fab418 and via array itself = 0x7ff7b2fab418 and individual access = 0x7ff7b2fab418
```

To iterate over an array, we can just check its size and divide by the size of one of the elements (or the data type):
```shell
int array[5] = {1, 2, 3, 4, 5};
int *pArray = array; //this is the same as = &array[0], as array = address of array[0]
int i, size = sizeof(array)/sizeof(int);
for(i = 0; i < size ; i ++) {
    printf("Element %d of the array = %d\n", i, *(pArray + i));
}
```

The outcome of the code above would be:
```shell
Element 0 of the array = 1
Element 1 of the array = 2
Element 2 of the array = 3
Element 3 of the array = 4
Element 4 of the array = 5
```

### Arrays as function arguments
Let's see the following example:
```shell
void sumOfElements(int elements[]) {
    printf("SOE - Size of array %lu; size of first element = %lu\n", sizeof(elements), sizeof(elements[0]));
}

int main() {
    int array[5] = {1, 2, 3, 4, 5};
    printf("Main - Size of array %lu; size of first element = %lu\n", sizeof(array), sizeof(array[0]));
    sumOfElements(array);
    return 0;
}
```

When compiling the previous code, there are a couple of things to consider:
1. The compiler throws the following warning: `warning: sizeof on array function parameter will return size of 'int *' instead of 'int[]'`.
2. The size of the arrays is different between the main function and the sumOfElements function:
```shell
Main - Size of array 20; size of first element = 4
SOE - Size of array 8; size of first element = 4
```

The reason for both things is the same: when passing an array as function argument, what the compiler does when creating the local stack of the function, is to copy only the first element of the array, which is the same as to say that to copy the pointer of the array to the local stack. Arrays are not passed by value to functions, arrays are always passed by reference. Both declarations below are the same:
```
void sumOfElements(int elements[]);
void sumOfElements(int *elements);
```

This is important because if we want to calculate the number of elements of an array within the calling function by using the `sizeof(array)/sizeof(int)` formula, it will never work due to the fact that array is passed by reference. This makes sense because arrays can be large, and there is no point of creating a full copy of an array every time the array is passed as an argument in a function.

There are some advantages of passing the array as an address:
1. If passing as an array, we can use pointer arithmetics.
2. Nomenclature is the same as, even when passed as pointer, elements can be accessed as array[i] (as this is the same as *(array + i)).

## Pointers and multidimensional arrays
Multidimensional arrays or matrix can be read as array of arrays. In case of 2 dimensions, a matrix would be an array of arrays. In case of 3 dimensions, an array of arrays of arrays. The way c stores the arrays is sequentially, meaning the initial address of the matrix will store the element (0, 0), the next one (0, 1), ..., (0, n), (n, 1), ..., (n, m). Let's see the following example:
```shell
int matrix[3][3] = {{1, 2, 3},
                        {11, 12, 13},
                        {21, 22, 23}};
printf("Address of the matrix: %p\n", matrix);
printf("The 1st element of the array another array. Address: %p\n", *matrix);
printf("The 1st element of the 1st array is an element. Value: %d, Value: %d\n", **matrix, matrix[0][0]);
printf("The 1st element of the 1st array is an element. Value: %d, Value: %d\n", *(*matrix + 1), matrix[0][1]);
printf("The 1st element of the 1st array is an element. Value: %d, Value: %d\n", *(*matrix + 2), matrix[0][2]);
printf("The 2nd element of the array another array. Address: %p\n", *(matrix + 1));
printf("The 1st element of the 2nd array is an element. Value: %d, Value: %d\n", *(*(matrix + 1)), matrix[1][0]);
printf("The 1st element of the 2nd array is an element. Value: %d, Value: %d\n", *(*(matrix + 1) + 1), matrix[1][1]);
printf("The 1st element of the 2nd array is an element. Value: %d, Value: %d\n", *(*(matrix + 1) + 2), matrix[1][2]);
printf("The 3rd element of the array another array. Address: %p\n", *(matrix + 2));
printf("The 1st element of the 3rd array is an element. Value: %d, Value: %d\n", *(*(matrix + 2)), matrix[2][0]);
printf("The 1st element of the 3rd array is an element. Value: %d, Value: %d\n", *(*(matrix + 2) + 1), matrix[2][1]);
printf("The 1st element of the 3rd array is an element. Value: %d, Value: %d\n", *(*(matrix + 2) + 2), matrix[2][2]);
```

This is how the previous code needs to be analyzed:
- *matrix dereferences the first element of the bi-dimensional array, which is a regular array ({1, 2, 3}).
- *(matrix + 1) dereferences the second element of the bi-dimensional array, which is a regular array ({11, 12, 13}).
- *(matrix + 2) dereferences the third element of the bi-dimensional array, which is a regular array ({21, 22, 23}). In this and the previous instruction, +1 increases the position of the pointer a whole array, in this case 12 bytes.
- **matrix dereferences the first element of the first array, which is 1.
- Once the 1st element of each subarray is dereferenced (*matrix, *(matrix + 1), *(matrix + 2)), the pointer points to a type int, so the + 1 instruction will move 4 bytes the pointer.

With the following code we can easily prove that the bi-dimensional array is stored lineally following (0,0), (0, 1), (0,2), (1,0), ...
```shell
int *p = &matrix[0][0];
printf("Value and address of the first element of the array: %d, %p\n", *p, p);
printf("Value and address of the second element of the array: %d, %p\n", *(p + 1), p + 1);
printf("Value and address of the third element of the array: %d, %p\n", *(p + 2), p + 2);
printf("Value and address of the fourth element of the array: %d, %p\n", *(p + 3), p + 3);
printf("Value and address of the fifth element of the array: %d, %p\n", *(p + 4), p + 4);
printf("Value and address of the sixth element of the array: %d, %p\n", *(p + 5), p + 5);
printf("Value and address of the seventh element of the array: %d, %p\n", *(p + 6), p + 6);
printf("Value and address of the eighth element of the array: %d, %p\n", *(p + 7), p + 7);
printf("Value and address of the ninth element of the array: %d, %p\n", *(p + 8), p + 8);
```

As we can see, now the pointer is to integer, so each arithmetic operation will increase the pointer 4 bytes. This is the result of the previous code;
```shell
Value and address of the first element of the array: 1, 0x7ff7bfeb0400
Value and address of the second element of the array: 2, 0x7ff7bfeb0404
Value and address of the third element of the array: 3, 0x7ff7bfeb0408
Value and address of the fourth element of the array: 11, 0x7ff7bfeb040c
Value and address of the fifth element of the array: 12, 0x7ff7bfeb0410
Value and address of the sixth element of the array: 13, 0x7ff7bfeb0414
Value and address of the seventh element of the array: 21, 0x7ff7bfeb0418
Value and address of the eighth element of the array: 22, 0x7ff7bfeb041c
Value and address of the ninth element of the array: 23, 0x7ff7bfeb0420
```

### Multidimensional arrays passed as argument function
The same rules apply to multidimensional arrays. When a multidimensional array is passed to a function, the compiler does not copy the whole array and create a local copy to the function. Instead, it creates a pointer to the first element. In case of bi-dimentional arrays, it creates a pointer to an array. This is how the function should be declared for a bi-dimensional 3x3 array:
```shell
void myFunction(int (*pointer)[3]);
void myFunction(int pointer[][3]);
```

## Pointers and character of arrays or strings
A String is nothing but a character of arrays. Due to that, to work with a String is pretty similar as to work with an array of characters, hence the same properties of pointers and arrays apply.

To work with Strings we need to have the following things in mind:
1. The size of the array must be higher or equal than the characters in the string + 1. The reason of the "+ 1" is that every String ends with the character `\0` or null, which marks the end of the String.
   - For instance, if we have the array `c[8]` and we want to store 'Manu': `c[0] = M, c[1] = a, c[2] = n, c[3] = u, c[4] = '\0'`.
2. Arrays and pointers are different types that are used in similar manner.
   - `c[i]` is the same as `*(c + i)`.
   - Being `c[10]` and `char *c1 = c`:
     - We can do `c1 = c` (as c1 is a pointer).
     - We can do `c1++` (as c1 is a pointer).
     - We can't do `c = c1` (as c is an array).
     - We can't do `c++` (as c is an array).
3. Arrays are passed by reference to functions, not by value.
4. To iterate a String there is no need to calculate the size of the string, we can simply iterate until finding the null or '\0' character. Here two ways of printing a string:
```shell
void print(char * s) {
  int i = 0;
  while(s[i] != '\0') {
    printf("%c", s[i]);
    i ++;
  }
  printf("\n");
}
void print(char * s) {
  while(*s != '\0') {
    printf("%c", *s);
    s++; //This s is a pointer, we can increment the address where it is pointing and get the value via dereferencing.
  }
  printf("\n");
}
```
5. This convention is used for all string functions in the library <string.h> (for instance, the string below would return size = 4 even if the array has 8 elements).
6. We can force a String is not modified within a function by using the keyword `const` (e.g. `void print(const char *c)`).
7. Strings must be created and initialized in the same line.
8. When initializing a String like `char c[5] = "Manu";`, the end null character is added automatically.
9. When initializing a String like `char c[5] = {'M', 'a', 'n', 'u', '\0'};`, the end null character must be added manually.
10. The size of a String will be the number of characters times 1 byte + 1 byte (the size of '\0').
11. The length of the string will be the number of characters (without including the last null character);

## Pointers and dynamic memory
There are four type/segment of memories in a c program:
- Code (Text): it is assigned to store the instructions that need to be executed.
- Static/Global: it stores all the static or global variables that are not declared inside a function and that have the whole lifetime of an application. They are accessible anywhere during the entire lifecycle of the application as long as the application is running. We should create global variables only when they are required across multiple functions and during the entire lifecycle of the program. Otherwise, it would be a waste of memory.
- Stack: it is used to store all the information of function calls and the local variables. Local variables are declared within a function, and they live only until the time the function is executing.
- Heap: a heap is an area of pre-reserved computer main storage (memory) that a program process can use to store data in some variable amount that won't be known until the program is running. It is sometime called free store of memory. The use/manage of heap memory varies across programming languages/compilers.

The amount of memory set aside for the first three segments does not grow while the application is running (that's the reason why we could have a stack overflow error), and is decided when the program is compiling.

The use of the heap memory is called dynamic memory allocation, which is what we will cover in this segment. To use dynamic memory allocation, C offers the following functions:
- `malloc`
- `calloc`
- `realloc`
- `free`

### `malloc` function
The function `void *malloc(size_t size)` allocates the requested memory and returns a pointer to it, being the size the number of bytes. Since the function returns a void pointer, casting is required. Let's see the following example:
```shell
int main() {
    int a; //goes on stack
    int *p;
    printf("Stack memory address: %p\n", &a);
    p = (int*)malloc(sizeof(int)); //goes on heap
    *p = 10; //it is stored on the memory allocated on the heap space
    printf("First memory address: %p\n", p);
    p = (int*)malloc(sizeof(int)); //goes on heap
    printf("Second memory address: %p\n", p);
    *p = 20; //it is stored on the memory allocated on the heap space
}
```

- The variable a goes to the stack memory, as its a local variable with a defined size.
- We have declared a pointer p to use the malloc function, as malloc works always with references since the value it returns is a pointer to the memory.
- In the first malloc instruction, we have allocated 4 bytes for p, and then store the number 10 on that memory.
- In the second malloc instruction, we have allocated additional 4 bytes for p, and then store the number 20 on that memory.
- As we can see below, the addresses returned by malloc is different both times, and due to the fact that we reuse the same pointer, we have lost the reference to the first heap memory allocated, meaning that we can not free up that memory which is a bad memory management. We should have used the `free` function before reusing the pointer.

This is the result of the previous code:
```shell
Stack memory address: 0x7ff7b3f523bc
First memory address: 0x60000177c040
Second memory address: 0x60000177c050
```

We can use the malloc function to allocate a range of memory, for instance to store an array. In the following example, we're allocating memory for a 20-element array.

```shell
int main() {
    int *p;
    p = (int*)malloc(20*sizeof(int)); //goes on heap
    p[0] = 1; //same as *p = 1
    p[1] = 10; //same as *(p + 1) = 10
}
```

If malloc is not able to allocate any memory, it returns null, so we need to have that in mind for error control.

### `calloc` function
The function `void *calloc(size_t nitems, size_t size)` allocates the requested memory and returns a pointer to it. There are two differences bewteen malloc and calloc:
1. Calloc function allows to define the number of elements plus the size of each element. Let's see how we would allocate memory for a 20-element array in malloc and calloc:
   - `int *p = (int*)malloc(20*sizeof(int));`
   - `int *p = (int*)malloc(20, sizeof(int));`
2. Calloc function initializes all byte positions with the value 0 whereas malloc doesn't, meaning that with malloc we will have garbage data until we write the data.

### `realloc` function
The function `void *realloc(void *ptr, size_t size)` attempts to resize the memory block pointed to by ptr that was previously allocated with a call to malloc or calloc. This function returns a pointer to the newly allocated memory, or NULL if the request fails. Let's see the following example:
```shell
int main () {
   char *str;
   /* Initial memory allocation */
   str = (char *) malloc(15);
   strcpy(str, "tutorialspoint");
   printf("String = %s,  Address = %u\n", str, str);
   /* Reallocating memory */
   str = (char *) realloc(str, 25);
   strcat(str, ".com");
   printf("String = %s,  Address = %u\n", str, str);
   free(str);
   return(0);
}
```

### `free` function
The function `void free(void *ptr)` deallocates the memory previously allocated by a call to calloc, malloc, or realloc. The fact that a memory has been freed up does not mean the memory address is not available. Let's see the following example:
```shell
int main() {
    int n;
    printf("Enter size of array\n");
    scanf("%d", &n);
    int *a = (int*)calloc(n, sizeof(int));
    for(int i = 0; i < n; i ++)
        printf("%d ", a[i]);
    free(a);
    printf("\n");
    a[1] = 5;
    for(int i = 0; i < n; i ++)
        printf("%d ", a[i]);
}
```

The outcome of the previous code is:
```shell
0 0 0 0 0 
996278272 5 0 0 0
```

As we can see, even after freeing up a, we still have access o a. There is no restriction on the memory itself once we know the address. This is dangerous as that memory could be allocated to another program and we could interfere with the execution of that program. To make any free execution safe, the recommendation is to make the pointer null after using free:
```shell
free(a);
a = NULL;
```

### Memory leak
As we have mentioned before, there are different regions within a program memory:
- Code.
- Static/Global.
- Stack
- Heap.

Let's see the following example:
```shell
void showArraySize() {
    int * array = {1, 2, 3};
    printf("%d\n", 3);
}

int main() {
    char action = 'y';

    while(action == 'y') {
        showArraySize();
        printf("Still want to print the array size?\n");
        scanf(" %c", &action);
    }
}
```

No matter how many times the loop is executed, the memory of the program remains constant: 348Kb. However, let's modify the way the array is created:
```shell
void showArraySize() {
    int * array = (int*)calloc(3, sizeof(int));
    array[0] = 0;
    array[1] = 1;
    array[2] = 2;
    printf("%d\n", 3);
}

int main() {
    char action = 'y';

    while(action == 'y') {
        showArraySize();
        printf("Still want to print the array size?\n");
        scanf(" %c", &action);
    }
}
```

As we keep executing the loop, the size of the program in memory increases non-stop. Why? This is what is called memory leak. Let's see the execution of the program line by line:
1. When the program is executed, a new section of memory is allocated in the stack for the execution of the main method.
2. When the function showArraySize is called, another section on top of the main section is created on the stack memory.
3. When showArraySize, the memory allocated on the stack gets freed up.

Let's analyse the first example:
1. When the program is executed, a new section of memory is allocated in the stack for the execution of the main method.
2. When the function showArraySize is called, another section on top of the main section is created on the stack memory.
3. Within showArraySize, the local variable array is created on the stack.
4. Within showArraySize, the element array[3] is created on the stack.
5. When showArraySize, the memory allocated on the stack gets freed up, and that includes the array.

Let's analyse the second example:
1. When the program is executed, a new section of memory is allocated in the stack for the execution of the main method.
2. When the function showArraySize is called, another section on top of the main section is created on the stack memory.
3. Within showArraySize, the local variable array is created on the stack.
4. Within showArraySize, the element array[3] is created on the heap memory. Due to that, there is a link between the stack variable array and the heap array itself.
5. When showArraySize, the memory allocated on the stack gets freed up. But that only includes the local variable array. The array itself still remains in the heap variable, and now, since the local variable array has been removed, the link between the local variable and the array itself gets removed. Due to that, the part of the heap memory where the array has been stored remains busy, allocated, unable to be used again.

If we repeat the loop over and over again, the step number 5 will make the heap memory being more and more busy as that memory allocation never gets released. This is what causes the memory leak, and if not controller, it can consume all the memory of the computer.