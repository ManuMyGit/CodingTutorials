all: compile link execute

compile:
	gcc -Wall -c pointers.c

link:
	gcc -o pointers pointers.o -lm

execute:
	./pointers

clean:
	rm *.o pointers