# Big O
Big O is the language and metric we use to describe the efficiency of algorithms. Basically we use Big O to judge which solutions are "better" than others.

But, what does it mean for a solution to be better? Basically we prefer solutions that use the least resources:
- Time (duration): time complexity.
- Space (memory): space complexity.

In general, we want to minimize both time and space complexity.

## The problem
However, the amount of time an algorithm takes to run depends on a lot of factors:
- Machine resources (RAM, CPU).
- Number of processes running at the same time.
- Single core vs multi core.
- ...

In fact, if we run the same algorithm several times in the same machine we are likely to get different execution times, so we can't use a unit of time (like seconds) to tell how good or bad an algorithm is. Then, how do we control for differences in computer hardware and environment?

## The solution
To solve this problem we use Big O notation, which can objectively describe the efficiency of code without the use of concrete units. It focuses on how the time and space requirements scale depending on the input (for the space complexity, the input itself is not taken into account). Let's say for instance that we need to iterate an array to sum all numbers: it is obvious that the bigger the array is, the more time the algorithm will need. What is more, in this case the growth will be directly proportional to the number of elements of the array.

## Big O vs Big Omega vs Big Theta
Academics use big 0, big 0 (theta), and big O (omega) to describe runtimes.
 - O (Big 0): In academia, big O describes an upper bound on the time. For instance, an algorithm which is O(N) is too `O(N^2), O(N^3)`, ...
 - Ω (Bit Omega): In academia, Ω is the equivalent concept but for lower bound. Printing the values in an array is O(N), as well as O(log N) and 0(1). After all, we know that it won't be faster than those runtimes.
 - Θ (Big theta): In academia, Θ means both O and Ω. That is, an algorithm is 0(N) if it is both Θ(N) and Ω(N). Θ gives a tight bound on runtime.

In industry, Big O is closer to Big Θ. 

## Best case, worst case and expected case
We can actually describe our runtime for an algorithm in three different ways.
 - Best case: rarely discuss this time complexity because it's not a very useful concept.
 - Worst case: for many (probably most) algorithms, the worst case and the expected case are the same
 - Expected case.

# Notation
Big O is described with the following expression:
 - `O(expression)`: the expression defines the complexity of the algorithm.

Let's see the following complexities:
 - O(1): constant complexity. That means that no matter how small of big the input is, the algorithm takes always the same amount of time/space.
 - O(log N): logarithmic complexity.
 - O(N): linear complexity.
 - O(N log N): logarithmic linear complexity.
 - O(N ^ 2): quadratic complexity.
 - O(N ^ 3): cubic complexity.
 - ...
 - O(N ^ C): c > 3.
 - O(2^N): exponential complexity.
 - ...
 - O(C^N): c > 2.
 - O(N!): factorial complexity.

## Rules
There are several rules we can apply to simplify the time and space analysis of algorithms.

### Product rule
This rule says that if the Big O is the product of multiple terms, we can drop the constant term. Constants don't matter. Let's see the following examples:
 - `O(500) => O(1 * 500) = O(1)`
 - `O(4N) => O(N)`
 - `O(N/3) = O(N * (1/3)) => O(N)`
 - `O(5 * N * N) = O(5 * N^2) => O(N^2)`

### Sum rule
This rule says that if the Big O is the sum of multiple terms, only keep the largest one, we can drop the rest. Let's see the following examples:
 - `O(N + 1000) => O(N)`
 - `O(N^2 + N) => O(N^2)`

Let's see the following example where we apply both rules:
 - `O(5N ^ 2 + 100N + 17)` => (product rule) `O(N ^ 2 + N + 17)` => (sum rule) `O(N^2)`.

### Shorthands
 - Arithmetic operations are constant: O(1).
 - Variable assignment is constant: O(1).
 - Accessing elements in an array (by index) or object (by key) is constant: O(1).
 - In a loop, the complexity is the length of the loop times the complexity of whatever happens inside the loop. 

### Multipart algorithms: add vs multiply
Let's suppose we have two arrays, with two different sizes A and B, and we have the two following algorithms:

Algorithm 1
`for(int a : arrayA) {`
`   ...`
`}`
`for(int b : arrayB) {`
`   ...`
`}`

Algorithm 2
`for(int a : arrayA) {`
`   for(int b : arrayB) {`
`      ...`
`   }`
`}`

What is the time complexity of both algorithms?:
 - Algorithm 1: O(A + B).
 - Algorithm 2: O(A * B)

Basically:
 - If our algorithm is in the form "do this, then, when you're all done, do that", then we add the runtimes.
 - If our algorithm is in the form "do this for each time you do that", then we multiply the runtimes.

## Log N Runtimes
We commonly see O(log N) in runtimes. Where does this come from?

Let's look at a binary search as an example. In binary search, we are looking for an example x in an N-element sorted array. We first compare x to the midpoint of the array. If x == middle, then we return. If x < middle, then we search on the left side of the array. If x > middle, then we search on the right side of the array.

We start off with an N-element array to search. Then, after a single step, we're down to N / 2 elements. One more step, and we're down to N / 4 elements. We stop when we either find the value or we're down to just one element.

The total runtime is then a matter of how many steps (dividing N by 2 each time) we can take until N becomes 1:
 - N = 16  
 - N = 8 //divide by 2
 - N = 4 //divide by 2
 - N = 2 //divide by 2
 - N 1

What is k in the expression `2 ^ k = N`? This is exactly what log expresses. 2 ^ K = 16 -> log(base 2) 16 = 4 -> log(base 2) N = k -> 2 ^ k = N.

This is a good takeaway for us to have. When we see a problem where the number of elements in the problem space gets halved each time, that will likely be a 0(log N) runtime.

## Recursive Runtimes
Here's a tricky one. What's the runtime of this code?

`int f(int n) {`
`   if(n <= 1) return 1;`
`   else return f(n - 1) + f(n - 1);`
`}`

Let's derive the runtime by walking through the code. Suppose we call f(4).
 - f(1) = 1
 - f(2) = f(1) + f(1)
 - f(3) = f(2) + f(2) = f(1) + f(1) + f(1) + f(1)
 - f(4) = f(3) + f(3) = f(1) + f(1) + f(1) + f(1) + f(1) + f(1) + f(1) + f(1)

How many calls are in this tree for f(4)? The tree will have depth N. Each node (i.e., function call) has two children. Therefore, each level will have twice as many calls as the one above it.The number of nodes on each level is:
 - Level 0: f(4) -> 1 node
 - Level 1: f(3) + f(3) -> 2 nodes.
 - Level 2: f(2) + f(2) + f(2) + f(2) -> 4 nodes.
 - Level 3: f(1) + f(1) + f(1) + f(1) + f(1) + f(1) + f(1) + f(1) -> 8 nodes.

Therefore, there will be `2^0 + 2^1 + 2^2 + 2^3 + 2^4 + ... + 2 ^ N`. This is equal to `2 ^ (N + 1) - 1` nodes. Generally speaking, when we have a recursive function that makes multiple calls, the runtime will often (but not always) look like O(branches  * depth), where branches is the number of times each recursive call is called. In this case, this gives us `O(2 ^ N)`, where branches = 2 (number of calls to the recursive function per recursion) and N is the input (depth).

With regards to the space complexity, in recursive code our space complexity should consider the space taken by recursive calls on the call stack.

## Simple example
What's the time complexity of the following function?

``` java
public int calculateAverage(int[] numbers) {
   int sum = 0;
   for(int i = 0; i < numbers.length; i ++) {
      int aux = numbers[i];
      sum += aux;
   }
   return sum / numbers.length;
}
```
 - `int sum = 0;` is executed just once, no matter the size of the array -> O(1).
 - `return sum / numbers.length;` is execute just once, no matter the size of the array -> O(1).
 - The loop is executed as many times as the size of the array -> O(N). What's inside this array?
   - `int i = 0;` is executed just once -> O(1).
   - `i < numbers.length` is executed just once per loop -> O(1).
   - `i ++` is executed just once per loop -> O(1).
   - `int aux = numbers[i];` is executed just once per loop -> O(1).
   - `sum += aux;` is executed just once per loop -> O(1).

O(function) = O(1 + 1 + N * (1 * 1 * 1 * 1)) = O(N).

# Space complexity
## Definition
When talking about space complexity there are two type of approaches:
 - Space complexity: space required by the algorithm and the inputs.
 - Auxiliary space complesity: space required by the algorithm, not including space taken up by the inputs. Basically, to focus on what's happening inside the algorithm.

## Rules of thumb
 - Most primitive types (boolean, numbers, undefined, null) are constant space.
 - Strings require O(N) space (where N is the string length).
 - Reference types (arrays and objects) are generally O(N), where N is the length for arrays and the number of keys (properties) for objects.

# Examples
## Example 1
Let's calculate the time complexity of the following algorithm:

``` java
public int foo(int n) {
   for(int a = 0; a < n / 2; a ++) {
      System.out.println(a);
   }
   for(int b = 0; b < n; b ++) {
      for(int c = 0; c < n; c ++) {
         System.out.println(b + ", " + c);
      }
   }
}
```

 - The first loop is executed n / 2 -> O(N/2) = O(N).
 - The second loop is executed n * n times -> O(N * N) = O(N ^ 2).
 - The total time complexity is `O(N ^ 2 + N) = O(N ^ 2)`.

## Example 2
Let's calculate the space complexity of the following algorithm:

``` java
public int calculateAverage(int[] numbers) {
   int sum = 0;
   for(int i = 0; i < numbers.length; i ++) {
      int aux = numbers[i];
      sum += i;
   }
   return sum / numbers.length;
}
```

 - The variable `sum` is gonna be created just once -> O(1).
 - The variable `i` is gonna be created just once -> O(1).
 - The variable `aux` is gonna be created n times, being n the size of the array.  However, the scope of the variable is the for loop, so the variable will be created and destroyed each iteration -> O(1).
 - The total space complexity is `O(1 + 1 + 1) = O(1)`.

## Example 3
Let's calculate the time complexity of the following algorithm:

``` java
public void foo(int[] array) {
   int sum = 0;
   int product = 1;
   for (int i= 0; i < array.length; i++) {
      sum =+ array[i);
   }
   for (int i= 0; i < array.length; i++) {
      product *= array[i];
   }
   System.out.println(sum + ", " + product);
}
```

 - There are two loops, apart from some constant operations (sum = 0, product = 1, println).
 - The total time complexity is `O(1 + 1 + N + N + 1) = O(3 + 2N) = O(N)`.

## Example 4
Let's calculate the time complexity of the following algorithm:

``` java
public void printPairs(int[] array) {
   for (int i = 0; i < array.length; i ++) {
      for (int j = 0; j < array.length; j ++) {
         System.out.println(array[i] + "," + array[j]);
      }
   }
}
```

 - There are two loops, one inside the other one. The first loop is executed n tines, and the second loop is executed n times for each loop one execution.
 - The total time complexity is `O(N * N) = O(N ^ 2)`.

## Example 5
Let's calculate the time complexity of the following algorithm:

``` java
public void printUnorderedPairs(int[] array) {
   for (int i = 0; i < array.length; i++) {
      for (int j = i + 1; j < array.length; j++) {
         System.out.println(array[i] + "," + array[j]);
      }
   }
}
```

 - There are two loops, one inside the other one. The first loop is executed n tines. However, the second loop is not executed n times for each loop one execution since the starting condition for loop 2 is i + 1.
 - First loop -> first iteration -> 2nd loop is executed n - 1 times.
 - First loop -> second iteration -> 2nd loop is executed n - 2 times.
 - ...
 - First loop -> before last iteration -> 2nd loop is executed 1 time.
 - First loop -> last iteration -> 2nd loop is not executed.
 - The number of total steps is (n - 1) + (n - 2) + ... + 2 + 1 = 1 + 2 + 3 + ... + (n - 1).
 - The above sum can be expressed as (N * (N - 1)) / 2.
 - The total time complexity is `O((N * (N - 1)) / 2) = O((N * N) / 2) = O(N* N) = O(N ^ 2)`.

Another way of analyzing this algorithm is the following one:
 - We know the outer loop runs N times.
 - However, the inner loop runs 1 + 2 + 3 + ... + N. The average value on that sequence is N / 2.
 - Hence, the total complexity is `O(N * N / 2) = O(N * N) = O(N ^ 2)`.

## Example 6
Let's calculate the time complexity of the following algorithm:

``` java
public void printUnorderedPairs(int[] arrayA, int[] arrayB) {
   for (int i= 0; i < arrayA.length; i++) {
      for (int j = 0; j < arrayB.length; j++) {
         if(arrayA[i] < arrayB[j])
            System.out.println(arrayA[i] + "," + arrayB[j])
      }
   }
}
```

 - There are two loops, one to iterate the arrayA and another one to iterate the arrayB.
 - The outer loop is executed arrayA.size() times, and the inner loop is executed arrayB.size() times per execution of arrayA.
 - Hence, the total complexity could be `O(A * B)`, where A = arrayA.size() and B = arrayB.size().
 - However, there is an if inside the loop. That if could lead us to make a mistake, because we could think it impacts on the time complexity. It doesn't, because both the condition and the print are constant operations.
 - Hence, the total complexity is `O(A * B)`.
 - An easier way to analyze this algorithm would be to replace the body of the inner loop for a O(1) statement, because as said above, both the condition and the print are constant operations.

## Example 7
Let's calculate the time complexity of the following algorithm:

``` java
public void printUnorderedPairs(int[] arrayA, int[] arrayB) {
   for (int i= 0; i < arrayA.length; i++) {
      for (int j = 0; j < arrayB.length; j++) {
         for (int k = 0; k < 100000; k++) {
            System.out.println(arrayA[i] + "," + arrayB[j]);
         }
      }
   }
}
```

 - There are three loops. The first one is executed arrayA.size() timex. The second one is executed arrayB.size() times per each arrayA element. The third one is executed 100000 times per each arrayB element.
 - The total complexity is `O(A * B * 100000) = O(A * B)`, where A = arrayA.size() and B = arrayB.size().

## Example 8
Let's calculate the time complexity of the following algorithm:

``` java
public void reverse(int[] array) {
   for (int i= 0; i < array.length / 2; i++) {
      int other= array.length - i - 1;
      int temp= array[i];
      array[i] = array[other];
      array[other] = temp;
   }
}
```

 - There is only one loop, whose body is O(1). The loop is executed N / 2 times.
 - The total complexity is `O(N / 2) = O(N)`.

## Example 9
Which ones of the following complexities are equivalent to O(N).
 - `O(N + P)`, where P < N / 2: if P < N / 2 -> the top limit is O(N + N / 2) = O(N).
 - `O(2 * N)`: applying the product rule, O(2 * N) = O(N)
 - `O(N + log N)`: applying the sum rule, N >> log N -> this expression is equivalent to O(N).
 - `O(N + M)`: N and M are two different inputs, this expression can not be reduced.

## Example 10
Suppose we have an algorithm that takes in an array of strings, sorts each string, and then sorts the full array. What would the runtime be?

Assuming the size of the array is N, to go over the entire array will be O(N). However, we don't know that the size of the strings will be. Due to this, we need to define certain boundaries to be able to calculate the time complexity of this algorithm:
 - S: size of the longest string.
 - N: length of the array.

Then, the time complexity will be:
 - To iterate over the array: O(N).
 - To sort every single string: O(S log S) -> So far, O(NS log S).
 - To sort the final array: O(N log N). However, to compare the strings to be able to order the final array is not a O(1) operation, it depends on the size of the string, in this case S. Then, to order the final array takes O(SN log N).
 - Then, the algorithm takes `O(SN(log S + logN))`.

## Example 11
Let's calculate the time complexity of the following algorithm, which sums the values of all the nodes in a balanced binary search tree:

``` java
public int sum(Node node) {
   if (node == null) {
      return 0;
   }
   return sum(node.left) + node.value + sum(node.right);
}
```

This is a recursive pattern, and as we explained in the recursive runtime section, the time complexity of this type of algorithm is O(branches  ^ depth). But before diving into the calculation, let's analyze the perspective from another point of view.
 - It is obvious the algorithm iterates over the entire tree. So if the tree has N nodes, the time complexity is O(N).

So O(branches ^ depth) must be O(N):
 - Since the recursive algorithm calls itself to times per call, branches - 2.
 - What about the depth? Remember we've got N nodes, on average the depth will be floor(log(base 2)N), which is roughly equals to log(base 2)N.
 - `O(2 ^ (log(base 2)N) = O(N)`.

## Example 12
Let's calculate the time complexity of the following algorithm:

``` java
public boolean isPrime(int n) {
   for (int x = 2; x * x <= n; x++) {
      if (n % X == 0) {
         return false;
      }
   }
   return true;
}
```

Let's see a few examples:
 - n = 5
   - x = 2 -> x * x = 4 < 5 -> true
   - x = 3 -> x * x = 9 > 5 -> false
 - n = 10
   - x = 2 -> x * x = 4 < 10 -> true
   - x = 3 -> x * x = 9 < 10 -> true
   - x = 4 -> x * x = 16 > 10 -> false

As we can see, the number of iterations depends on the root square of the input. So the time complexity is O(root square(N))

## Example 13
Let's calculate the time complexity of the following algorithm:

``` java
public int factorial(int n) {
   if (n < 0) {
      return -1;
   } else if (n == 0) {
      return 1;
   } else {
      return n * factorial(n - 1);
   }
}
```

This algorithm calculates the factorial of a number. It is fairly easy to see that the algorithm goes from n to 0, so we can say without any question that the time complexity is O(N).

## Example 14
Let's calculate the time complexity of the following algorithm, which counts all permutations of a string:

``` java
public void permutation(String str) {
   permutation(str, "");
}
public void permutation(String str, String prefix) {
   if (str.length() == 0) {
      System.out.println(prefix);
   } else {
      for (int i= 0; i < str.length(); i++) {
         String rem = str.substring(0, i) + str.substring(i + 1);
         permutation(rem, prefix + str.charAt(i));
      }
   }
}
```

Let's see first how the algorithm would behave with a simple example: permutation("abc", "").

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/algorithmics/images/picture01.png)

As we can see, permutation function is called 6 times on its base case, which makes total sense because what the algorithm does is to "remove" one letter from the string and to call itself with length-1 times, so the base case is called 3 * 2 * 1 times = 3! times.

However, the function is called more times before reaching its base case. If we assume the the base case (N!) is repeated in each level of the tree, we could say that the upper level of the calls would be N * N!.

However, this isn't the end. So far we know the number of times that the function is called, but how many time does it take to each call to be executed? The base case is the print function, which is O(N). The recursive calls takes as well O(N) (substring function).

The total complexity of the algorithm is O(N * N * N!).

## Example 15
Let's calculate the time complexity of the following algorithm:

``` java
int fib(int n) {
	if (n <= 0)
		return 0;
	else if (n == 1)
		return 1;
	return fib(n - 1) + fib(n - 2);
}
```

The tree of this algorithm is simple: it is a binary tree (because the function calls itself twice) whose depth is N (because there is a decrement of N - 1). Hence, the time complexity of this algorithm is O(2 ^ N).

## Example 16
Let's calculate the time complexity of the following algorithm, which is similar to the one above but printing all numbers until reaching to n.

``` java
void allFib(int n) {
	for (int i = 0; i < n; i++) {
		System.out.println(i + ": "+ fib(i));
	}
}
int fib(int n) {
	if (n <= 0)
		return 0;
	else if (n == 1)
		return 1;
	return fib(n - 1) + fib(n - 2);
}
```

We already know that the time complexity of the function fib is O(2 ^ N). This function is called N times due to the for loop, so we've got O(N * 2 ^ N). However, we can't say that because N increases from 0 to n. Not all the times the fib algorithm is called with n but with 1, 2, ..., n. Or n, n-1, ..., 1. Which means 2 ^ 1 + 2 ^ 2 + 2 ^ 3 + ... + 2 ^ N, which is equal to 2 ^ (N + 1). Hence, the time complexity of this algorithm is O(2 ^ (N + 1)) = O(2 ^ N). As we can see, the time complexity is the same as in the previous example.

## Example 16
Let's calculate the time complexity of the following algorithm:

``` java
void allFib(int n) {
	int[] memo = new int[n + 1];
	for(int i = 0; i < n; i++){
		System.out.println(i + ": "+ fib(i, memo));
	}
}

int fib(int n, int[] memo) {
	if(n =< 0)
		return 0;
	else if(n == 1)
		return 1;
	else if(memo[n] > 0)
		return memo[n];
	memo[n] = fib(n - 1, memo)+ fib(n - 2, memo);
	return memo[n];
}
```

What this algorithm does is to calculate the fibonacci number but thanks to the memo array, it doesn't go through the numbers whose fib was already calculated. Let's focus first on the fib function. What happens if we call this function with the value 4?

![](https://github.com/ManuMyGit/CodingTutorials/blob/main/algorithmics/images/picture02.png)

As we can see, there is no ramification since fib(2) was already calculated in the left branch. The action of retrieving the result for fib(2) is O(1), because the access to the array is immediate. Hence, the time complexity of this algorithm is not O(2 ^ N), but O(N).

This technique, called memoization, is a very common one to optimize exponential time recursive algo­rithms.

## Example 17
The following function prints the powers of 2 from 1 through n (inclusive). For example, if n is 4, it would print 1, 2, and 4. What is its runtime?

``` java
int powers0f2(int n) {
	if(n < 1) {
		return 0;
	} else if(n == 1) {
		System.out.println(l);
		return 1;
	} else {
		int prev = powers0f2(n / 2);
		int curr = prev * 2;
		System.out.println(curr);
		return curr;
	}
}
```

There are several ways to approach this problem. Let's see what happens when we call powersOf2(50): powersOf2(50) -> powersOf2(25) -> powersOf2(12) -> powersOf2(6) -> powersOf2(3) -> powersOf2(1). We're splitting N into 2 in every call, which is equivalent to log N. Hence, the time complexity of this algorithm is O(log N).

Let's see it in another way. When N = 50, function was called 6 times. With N = 51, the function will be still called 6 times. What about 52, and 60?

 - powersOf2(51) -> powersOf2(25) -> powersOf2(12) -> powersOf2(6) -> powersOf2(3) -> powersOf2(1).
 - powersOf2(52) -> powersOf2(26) -> powersOf2(13) -> powersOf2(6) -> powersOf2(3) -> powersOf2(1).
 - powersOf2(60) -> powersOf2(30) -> powersOf2(15) -> powersOf2(7) -> powersOf2(3) -> powersOf2(1).

When there will be another call to the function? The function will be called again when N doubles. Every time N doubles, the number of calls gets increased once. Then, 2 ^ (number of calls) = N. Remember that Log (base a) b = c -> a ^ c = b. Hence, number of calls = log (base 2) N. Hence, the time complexity is O(log N).

# Big O and JavaScript
## Rules of thumb
In JavaScript, Big O of objects, which are basically collections of unordered key-value elements, are:
 - Insertion: O(1).
 - Removal: O(1).
 - Searching: O(N).
 - Access: O(1).

Some of the methods Object provides:
 - Object.keys(object): O(N).
 - Object.values(object): O(N).
 - Object.entries(object): O(N).
 - object.hasOwnProperty(key): O(1).

In JavaScript, Big O of arrays, which are basically a collection of ordered elements, are:
 - Insertion at the beginning: O(N).
 - Insertion at the end: O(1).
 - Removal at the beginning: O(N).
 - Removal at the end: O(1).
 - Searching: O(N).
 - Access: O(1).

Some of the methods for arrays:
 - push (add data at the end of the array): O(1).
 - pop (remove data at the end of the array): O(1).
 - shift (remove data at the beginning of the array): O(N).
 - unshift (add data at the begining of the array): O(N).
 - concat (merge two arrays): O(N).
 - slice (return a copy of a part of the array): O(N).
 - splice (remove and add elements to the array): O(N).
 - sort (order an array): O(N * log N).
 - forEach/map/filter/reduce/...: O(N).