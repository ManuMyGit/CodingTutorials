#include <stdio.h>
#include <stdlib.h>

/*
 * Pointers as function arguments - call by reference
 */
void increment(int *);

/*
 * Pointers as function return
 */
int * add(int *, int *);

/*
 * Pointers to functions
 */
float multiply(int,int);
float divide(int,int);
float subtract(int,int);

/*
 * Pointer functions as function parameters
 */
void bubbleSort(int *, int, int (*)(const void*, const void*));
int compare(const void *, const void *);
int compare2(const void *, const void *);

/*
 * Arrays as function arguments
 */
void sumOfElements(int [], int);
void sumOfElements2(int *, int);

/*
 * Multidimensional arrays passed as argument function
 */
void printMatrix(int (*)[3]);
void printMatrix2(int [][3]);

int main() {
    /*
     * Simple example of pointer;
     */
    int a = 5;
    int *p = &a;
    printf("Variable memory address = %p\n", &a);
    printf("Variable value = %d\n", a);
    printf("Pointer memory address = %p\n", &p);
    printf("Pointer value = %p\n", p);
    printf("Pointer referenced value (dereference) = %d\n", *p);
    /*
     * Updating the address of the pointer
     */
    int b = 10;
    p = &b;
    printf("Variable memory address = %p\n", &b);
    printf("Variable value = %d\n", b);
    printf("Pointer memory address = %p\n", &p);
    printf("Pointer value = %p\n", p);
    printf("Pointer referenced value (dereference) = %d\n", *p);
    /*
     * Pointer arithmetic (going over an int with a char pointer) and casting
     */
    int number = 65536;
    unsigned char *pNumberChar = (unsigned char*)&number;
    printf("Variable number = %d\n", number);
    printf("Variable number via pointer = %x\n", *pNumberChar);
    printf("Variable number via pointer = %x\n", *(pNumberChar + 1));
    printf("Variable number via pointer = %x\n", *(pNumberChar + 2));
    printf("Variable number via pointer = %x\n", *(pNumberChar + 3));
    /*
     * Pointer to pointer
     */
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
    /*
     * Pointers as function arguments - call by reference
     */
    printf("Variable value = %d\n", number);
    increment(&number);
    printf("Variable value = %d\n", number);
    /*
     * Pointers as function return. This will generate a warning as the pointer is pointing to a region of the memory stack of add, which will be removed after
     * the function add ends, hence c would be pointing to an unsafe space. This code will work as there is no other function called before retrieving the
     * value of c.
     */
    a = 2, b = 4;
    printf("Address of a in main: %p\n", &a);
    printf("Address of b in main: %p\n", &b);
    printf("Value of a in main: %d\n", a);
    printf("Value of b in main: %d\n", b);
    int *c = add(&a, &b);
    printf("Sum = %d\n", *c);
    /*
     * Pointers to functions
     */
    a = 2, b = 4;
    float (*operation[3])(int, int); //Declaration of array of pointers to functions

    operation[0] = subtract;
    operation[1] = multiply;
    operation[2] = divide;

    float result = (*operation[0])(a, b);
    printf("Subtraction (a-b) = %.1f\n", result);

    result = (*operation[1])(a, b);
    printf("Multiplication (a*b) = %.1f\n", result);

    result = (*operation[2])(a, b);
    printf("Division (a/b) = %.1f\n", result);

    /*
     * Pointer functions as function parameters
     */
    int i, myArray[] = {3, 2, 1, 5, 6, 4};
    bubbleSort(myArray, 6, compare);
    for(i = 0; i < 6; i ++) {
        printf("%d ", myArray[i]);
    }
    printf("\n");
    bubbleSort(myArray, 6, compare2);
    for(i = 0; i < 6; i ++) {
        printf("%d ", myArray[i]);
    }
    printf("\n");

    /*
     * Pointers and arrays
     */
    int *pArray = myArray;
    printf("First element of the array = %d\n", *pArray);
    printf("Address of the first element via pointer = %p and via array itself = %p and individual access = %p\n", pArray, myArray, &myArray[0]);
    printf("Second element of the array = %d\n", *(pArray + 1));
    printf("Address of the second element via pointer = %p and via array itself = %p and individual access = %p\n", pArray + 1, myArray + 1, &myArray[1]);
    int size = sizeof(myArray)/sizeof(int);
    for(i = 0; i < size ; i ++) {
        printf("Element %d of the array = %d\n", i, *(pArray + i));
        printf("Element %d of the array = %d\n", i, myArray[i]);
    }

    /*
     * Arrays as function arguments
     */
    printf("Main - Size of array %lu; size of first element = %lu\n", sizeof(myArray), sizeof(myArray[0]));
    sumOfElements(myArray, sizeof(myArray) / sizeof(myArray[0]));
    sumOfElements2(pArray, sizeof(myArray) / sizeof(myArray[0]));

    /*
     * Pointers and multidimensional arrays
     */
    int matrix[3][3] = {{1, 2, 3}, {11, 12, 13}, {21, 22, 23}};
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
    int *pMatrix = &matrix[0][0];
    printf("Value and address of the first element of the array: %d, %p\n", *pMatrix, pMatrix);
    printf("Value and address of the second element of the array: %d, %p\n", *(pMatrix + 1), pMatrix + 1);
    printf("Value and address of the third element of the array: %d, %p\n", *(pMatrix + 2), pMatrix + 2);
    printf("Value and address of the fourth element of the array: %d, %p\n", *(pMatrix + 3), pMatrix + 3);
    printf("Value and address of the fifth element of the array: %d, %p\n", *(pMatrix + 4), pMatrix + 4);
    printf("Value and address of the sixth element of the array: %d, %p\n", *(pMatrix + 5), pMatrix + 5);
    printf("Value and address of the seventh element of the array: %d, %p\n", *(pMatrix + 6), pMatrix + 6);
    printf("Value and address of the eighth element of the array: %d, %p\n", *(pMatrix + 7), pMatrix + 7);
    printf("Value and address of the ninth element of the array: %d, %p\n", *(pMatrix + 8), pMatrix + 8);

    /*
     * Multidimensional arrays passed as argument function
     */
     printMatrix(matrix);
     printMatrix2(matrix);
    return 0;
}

/*
 * Pointers as function arguments - call by reference
 */
void increment(int * number) {
    *number = *number + 1;
}

/*
 * Pointers as function return
 */
int * add(int *a, int *b) {
    int c = *a + *b;
    return &c;
}

/*
 * Pointers to functions
 */
float subtract(int a, int b) { return a - b; }

float multiply(int a, int b) { return a * b; }

float divide(int a, int b) { return a / (b * 1.0); }

/*
 * Pointer functions as function parameters
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

/*
 * Arrays as function arguments
 */
void sumOfElements(int elements[], int size) {
    printf("SOE - Size of array %lu; size of first element = %lu\n", sizeof(elements), sizeof(elements[0]));
    for(int i = 0; i < size; i ++) {
        printf("Element %d of the array = %d\n", i, elements[i]);
    }
}

void sumOfElements2(int *elements, int size) {
    printf("SOE2 - Size of array via pointer %lu; size of first element = %lu\n", sizeof(elements), sizeof(*elements));
        for(int i = 0; i < size; i ++) {
            printf("Element %d of the array = %d\n", i, *(elements + i));
        }
}

/*
 * Multidimensional arrays passed as argument function
 */
void printMatrix(int (*matrix)[3]) {
    for(int i = 0; i < 3 * 3; i++) {
        printf("Element (%d) of the matrix as an array = %d\n", i, *(*matrix + i));
    }
}

void printMatrix2(int matrix[][3]) {
    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            printf("Element (%d, %d) of the matrix = %d\n", i, j, matrix[i][j]);
        }
    }
}