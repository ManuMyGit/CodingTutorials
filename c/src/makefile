all: compile link execute clean

compile:
	gcc -Wall -c $(file).c

link:
	gcc -o DEL$(file) $(file).o -lm

execute:
	./DEL$(file)

clean:
	rm DEL* *.o