#include <stdio.h>

void bitwiseOperators();
void printBinary(unsigned char);

int main() {
    bitwiseOperators();
    return 0;
}

void bitwiseOperators() {
    unsigned char myChar = 0b11001100;
    printf("Original value: ");
    printBinary(myChar);
    //NOT operation
    printf("NOT operation: ");
    printBinary(~myChar);
    //AND operation
    printf("AND operation with 11000000: ");
    printBinary(myChar & 0b11000000);
    //NAND operation
    printf("NAND operation with 11000000: ");
    printBinary(~(myChar & 0b11000000));
    //OR operation
    printf("OR operation with 00000011: ");
    printBinary(myChar | 0b00000011);
    //NOR operation
    printf("NOR operation with 00000011: ");
    printBinary(~(myChar | 0b00000011));
    //XOR operation
    printf("XOR operation with 10100000: ");
    printBinary(myChar ^ 0b10100000);
    //NO XOR operation
    printf("NO XOR operation with 10100000: ");
    printBinary(~(myChar ^ 0b10100000));
    //Left shift 2 bits
    printf("Left shift 2 bits: ");
    printBinary(myChar << 2);
    //Left shift 2 bits
    printf("Right shift 2 bits: ");
    printBinary(myChar >> 2);
}

void printBinary(unsigned char myChar) {
    unsigned char mask = 0b10000000;
    unsigned char temp = (myChar & mask) >> 7;
    printf("%u", temp);
    mask = 0b01000000;
    temp = (myChar & mask) >> 6;
    printf("%u", temp);
    mask = 0b00100000;
    temp = (myChar & mask) >> 5;
    printf("%u", temp);
    mask = 0b00010000;
    temp = (myChar & mask) >> 4;
    printf("%u", temp);
    mask = 0b00001000;
    temp = (myChar & mask) >> 3;
    printf("%u", temp);
    mask = 0b00000100;
    temp = (myChar & mask) >> 2;
    printf("%u", temp);
    mask = 0b00000010;
    temp = (myChar & mask) >> 1;
    printf("%u", temp);
    mask = 0b00000001;
    temp = (myChar & mask);
    printf("%u", temp);
    printf(" (character %c) (hex %x)\n", myChar, myChar);
}