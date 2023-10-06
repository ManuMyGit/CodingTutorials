#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * Structs
 */
typedef struct {
    int x;
    int y;
} Point;

/*
 * Nested structs
 */
typedef struct {
    char street[100];
    char city[50];
    char state[3];
    int zipCode;
} Address;

typedef struct {
    char name[50];
    char id[10];
    int age;
    Address address;
} Person;

/*
 * Accessing structs properties.
 * Structs as function parameters
 */
void printPerson(Person);
void printPersonWithPointer(Person *);
void printPoint(Point);
void printPointWithPointer(Point *);

int main(void) {
    /*
     * Initialization
     */
    Person person;
    strcpy(person.name, "Any name");
    strcpy(person.id, "12345");
    person.age = 25;
    strcpy(person.address.street, "1st Ave");
    strcpy(person.address.city, "New York");
    strcpy(person.address.state, "NY");
    person.address.zipCode = 12345;
    printf("Size of person in main: %lu\n", sizeof(person));
    /*
     * Structs as function parameters
     */
    printPerson(person);
    printPersonWithPointer(&person);

    /*
     * Initialization
     */
    Point point = {5, 10};
    /*
     * Structs as function parameters
     */
    printPoint(point);

    /*
     * Initialization
     */
    Point point2 = {
        .y=5,
        .x=10
    };
    /*
     * Structs as function parameters
     */
    printPoint(point2);

    /*
     * Initialization
     * Structs dynamically allocated
     */
    Point *pointPtr = (Point *)malloc(sizeof(Point));
    pointPtr -> y = 7;
    pointPtr -> x = 11;
    /*
     * Structs as function parameters
     */
    printPointWithPointer(pointPtr);
    free(pointPtr);
    return 0;
}

/*
 * Accessing structs properties.
 * Structs as function parameters
 */
void printPerson(Person person) {
    printf("Size of person in printPerson: %lu\n", sizeof(person));
    printf("Person name: %s\n", person.name);
    printf("Person id: %s\n", person.id);
    printf("Person age: %d\n", person.age);
    printf("Person address street: %s\n", person.address.street);
    printf("Person address city: %s\n", person.address.city);
    printf("Person address state: %s\n", person.address.state);
    printf("Person address zip code: %d\n", person.address.zipCode);
}

void printPersonWithPointer(Person *person) {
    printf("Size of person in printPersonWithPointer: %lu\n", sizeof(person));
    printf("Person name: %s\n", person->name);
    printf("Person id: %s\n", person->id);
    printf("Person age: %d\n", person->age);
    printf("Person address street: %s\n", person->address.street);
    printf("Person address city: %s\n", person->address.city);
    printf("Person address state: %s\n", person->address.state);
    printf("Person address zip code: %d\n", person->address.zipCode);
}

void printPoint(Point point) {
    printf("Point coordinates: (%d, %d)\n", point.x, point.y);
}

void printPointWithPointer(Point *point) {
    printf("Point coordinates: (%d, %d)\n", point->x, point->y);
}