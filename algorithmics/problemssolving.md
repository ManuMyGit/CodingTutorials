# Problems solving approach
In an interview, interaction with the interviewer is crucial. You've got to show them your thinking process.

## Step 1: understanding the problem
1. Can I restate the problem in my own words?
2. What are the inputs which go into the problem? Do they have a limit?
3. What are the outputs that should come from the solution of the problem?
4. Can the outputs be determined from the inputs? In other words, do I have enough information to solve the problem?
5. How should I label the important pieces of data that are part of the problem?

Let's analyze the following example: write a function which takes two numbers and returns their sum.
1. Write a function that adds two numbers, implement addition.
2. The inputs are the two numbers. But, are we talking about integer numbers, floating points, how large the numbers will be, string with the value of the number, ...? Are the two inputs required, can we get a null input?
3. The outputs will depend on the inputs, the type will be the same.
4. No. What if somebody passes a null or non defined value?
5. Only important piece of data is the input and the output. We've got the function "add", the input "number1", the input "number2" and the output "result".

## Step 2: explore concrete examples
This step is tight very closely to understanding the problem:
1. Coming up with examples can help us understand the problem better.
2. Examples also provide sanity checks that our eventual solution works how it should do.

These are the steps recommended:
1. Start with simple examples. Input + output.
2. Progress to more complex examples.
3. Explore examples with empty inputs.
4. Explore examples with invalid inputs.

Let's analyze the following example: write a function which takes in a string and returns the count of each character in the string.
1. charCount("aaa") -> {a: 3}. charCount("hello") -> {h: 1, e: 1, l: 2, o: 1}. It might seem simple, but what if I return the entire alphabet with the count = 0 for the characters which don't appear in the string?
2. charCount("Hello hi, my phone number is 123123123"). Do we distinguish between lower case and upper case? What about special characters like blank space?
3. charCount(""). What is the output in this scenario? Do we return null, undefined, the entire alphabet with counter = 0?
4. charCount(null) or charCount(3) or charCount({field: "value"}). What should we return in those scenarios?

## Step 3: break it down
This is an interaction with the interviewer, in the meantime we're doing this, we can interact with the interviewer: I'm gonna try this, do you think this will work? do this sound crazy to you?
1. Explicitly write out the steps you need to take. This is not pseudocode, it's just words, basic components of the solution. This forces us to think about the code we'll write before writing it, and helps us catch any lingering conceptual issues or misunderstandings before we dive in and have to worry about details.

Let's analyze the following example: write a function which takes in a string and returns the count of each character in the string.
function charCount(str) {
    //make object to return at the end
    //loop over string, foreach character
        //if the char is a number-letter key, add 1
        //if the char is a number-letter and not a key, add it and set it to one
        //else ignore
    //Return an object with keys that are lowercase alphanumeric characters (that was the agreement with the interviewer) and values should be the counter of those characters
}

## Step 4: solve/simplify
Solve the problem and if you can't, solve a simpler one!
1. Find the core difficulty in what you're trying to do.
2. Temporarily ignore that difficulty.
3. Write a simplified solution.
4. Then incorporate that difficulty back in.

Let's analyze the following example: write a function which takes in a string and returns the count of each character in the string.
1. I can't remember the exact Javascript method to identify uppercase, lowercase in Javascript. You can tell the interviewer: "hey, I can't remember how to lowercase a character in JS, I'm making the method name up". Same for instance for the function that checks whether the character is alphanumeric. Of perhaps you don't remember the foreach loop, so you say outloud and you use a for loop (for(var i = 0; i < str.length; i ++)).

Write the solution:
function charCount(str) {
    var result = {};
    for(var i = 0; i < str.length; i ++) {
        var char = str[i].toLowerCase();
        if(result[char] > 0) {
            result[char] ++;
        } else {
            result[char] = 1;
        }
    }
    return result;
}

## Step 5: look back and refactor
During this step we can interact with the interviewer:
- You know, this is not ideal, I think there is a better way to solve it, but I'm not confidence, I'd Google it to try to find it, ...

This is a set of questions we can ask ourselves:
1. Can you check the result?
2. Can you derive the result differently?
3. Can you understand it at a glance? How intuitive is my solution?
4. Can you use the result of method for some other problem?
5. Can you improve the performance of your solution?
6. Can you think of other ways to refactor?
7. How have other people solved this problem?

Final code:
function charCount(str) {
    var result = {};
    for(var char of str) {
        if(/[a-zA-Z0-9]/.test(char)) {
        char = char.toLowerCase();
            result[char] = ++result[char] || 1;
        }
    }
    return result;
}

# Problems solving patterns
There are two approach for solving problems:
1. Devise a plan for solving problems (previous section).
2. Master common problems solving patterns.

Some patterns are:
- Frequency counter.
- Multiple pointers.
- Sliding window.
- Divide and counter.
- Dynamic programming.
- Greedy algorithms.
- Backtracking.
- ...

## Frequency counter pattern
The idea behind it is to use an object to collect a bunch of values and their frequencies. This is useful in algorithms where you have multiple inputs and you need to compare them. This can often avoid the need for nested loops or O(N^2) operations with arrays or strings. This algorithm is usually O(N).
Let's see an example: write a function called "same", which accepts two arrays. The function should return true if every value in the first array has its corresponding square value in the other array. The frequency of the values must be the same.
1. Understanding the problem: basically the function must return true if all numbers in the first array has its square value in the 2nd one. Besides, the number of squares in the 2nd array must be the same, I mean, if there is just one 2 in the first array, there can be just one 4 in the second array. The order doesn't matter.
2. Concrete examples:
   - same([1, 2, 3], [4, 1, 9]) -> true.
   - same([1, 2, 3], [1, 9]) -> false.
   - same([1, 2, 1], [4, 4, 1]) -> false (must be same frequency, the 1st 1 in the first array matches with the unique 1 in the second array, but there is no match for the 2nd 1 in the first array).
3. Break it down. In this case, this solution is O(N^2).
function same(arr1, arr2) {
   boolean result; //We can initiate result to true so when we found a element which don't match, return false
   //Iterate arr1 while result = true
   //For each element of arr1, iterate arr2 to find it
   //If found, remove it from arr2 because frequency matters
   return result;
}
4. Solve the problem based on 3.
5. Optimize the problem. Here is the general frequency counter pattern algorithm:

```java
public output functionName(input1, input2) {
    //Initial condition to avoid running the algorithm. For instance, size comparison
    //Iterate over the 1st input to build a map whose key is the value, and the value is the frequency of the key over the input1
    //Iterate over the 2nd input to build a map whose key is the value, and the value is the frequency of the key over the input2
    //Iterate over the 1st frequency map.
    //Compare key-valye of frequency map 1 with key-value of frequency map 2 and apply validation rules
}
```

There is another pattern:

```java
public output functionName(input1, input2) {
    //Initial condition to avoid running the algorithm. For instance, size comparison
    //Iterate over the 1st input to build a map whose key is the value, and the value is the frequency of the key over the input1
    //Iterate over the 2nd input
    //Find the element of the 2nd input within the frequency map
    //If found, decrease the frequency in 1
    //If not found or found but frequency <= 0, return false
}
```

The complexity of this algorithm is, being A the size of intpu1 and B the size of input2: O(1 + A + B + A + 1 + 1) = O(2A + B + 3) = O(A + B). Let's see the algorithm to solve this problem, both with the 1st pattern and the 2nd pattern:

```java
public static boolean same(int[] arr1, int[] arr2) {
        //Initial condition. Since the frequency must be the same, arr1 must have the same size or being shorter than arr2
        //O(1)
        if(arr1.length > arr2.length)
            return false;
        boolean result = true;
        Map<Integer, Integer> frequencyCounter1 = new HashMap<>();
        Map<Integer, Integer> frequencyCounter2 = new HashMap<>();
        //Build 1st map counter: O(A).
        for(int value : arr1) {
            frequencyCounter1.put(value, frequencyCounter1.get(value) == null ? 1 : frequencyCounter1.get(value) + 1);
        }
        //Build 2nd map counter: O(B).
        for(int value : arr2) {
            frequencyCounter2.put(value, frequencyCounter2.get(value) == null ? 1 : frequencyCounter2.get(value) + 1);
        }
        //Iterate over 1st map counter: O(A).
        for (Map.Entry<Integer, Integer> pair : frequencyCounter1.entrySet()) {
            int keySquare = pair.getKey() * pair.getKey();
            //Compare frequency exists and it's equal between frequency maps
            if(frequencyCounter2.get(keySquare) == null || frequencyCounter1.get(pair.getKey()) != frequencyCounter2.get(keySquare)) {
                result = false;
                break;
            }
        }
        return result;
    }
```
```java
public static boolean anagram(String arr1, String arr2) {
        if(arr1.length() != arr2.length())
            return false;
        boolean result = true;
        Map<Character, Integer> frequencyCounter1 = new HashMap<>();
        Map<Character, Integer> frequencyCounter2 = new HashMap<>();
        for (int i = 0; i < arr1.length(); i++) {
            char aux = arr1.charAt(i);
            frequencyCounter1.put(aux, frequencyCounter1.get(aux) == null ? 1 : frequencyCounter1.get(aux) + 1);
        }
        for (int i = 0; i < arr2.length(); i++) {
            char aux = arr2.charAt(i);
            if(frequencyCounter1.get(aux) == null || frequencyCounter1.get(aux) == 0) {
                result = false;
                break;
            } else
                frequencyCounter1.put(aux, frequencyCounter1.get(aux) - 1);
        }
        return result;
    }
```

## Multiple pointers pattern
The idea is to create pointers of values that correspond to an index or position, and move towards the beginning, end or middle based on a certain condition. Very efficient for solving problems with minimal space complexity as well.

Let's see this example: write a function called sumZero which accepts a sorted array of integers. The function should find the first pair where the sum is 0. Return an array which includes both values that sum 0 or null in any other case.
1. Understanding the problem: basically the function must iterate the array to find the first couple of numbers which sum 0. First is left, and the array is ordered, which is key to apply this algorithm.
2. Concrete examples:
   - sumZero([-3, -2, -1, 0, 1, 2, 3]) -> [-3, 3].
   - sumZero([-2, 0, 1, 3]) -> null.
   - sumZero([1, 2, 3]) -> null.
3. Break it down. In this case, this solution is O(N ^ 2), space complexity O(1).
function sumZero(arr1) {
   boolean result; //We can initiate result to false so when we found the pair, true is returned
   //Iterate the array starting from 1
   //Iterate the array starting from previous loop + 1
   //Compare both numbers
}
4. Solve the problem based on 3.
5. Optimize the problem. Here is the general multiple pointers pattern algorithm:

```java
public output functionName(input) {
    //Initial validations, such as compare size or null values
    //Create left pointer pointing to the 1st element
    //Create left pointer pointint to the last element
    //Condition based on pointers
    //Copmarisions and pointers and result updating
    //Return result
}
```

The complexity of this algorithm is O(N), and space complexity O(1). Here the algorithm:

```java
public static Object sumZero(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while(left < right) {
            int sum = arr[left] + arr[right];
            if(sum == 0)
                return new Integer[]{arr[left], arr[right]};
            else if(sum > 0)
                right --;
            else
                left ++;
        }
        return null;
    }
```

In the following example we're counting the unique occurrences within an ordered array. Again, the key is to have the array ordered:

```java
public static Integer countUniqueValues(int[] arr) {
        //Initial validations
        if(arr == null || arr.length == 0)
            return 0;
        if(arr.length == 1)
            return 1;
        //Initialize left and right pointers
        int left = 0;
        int right = 1;
        int counter = 1;
        //Condition based on pointers 
        while(right < arr.length) {
            //Comparison
            if(arr[left] == arr[right])
                right ++;
            else {
                //Pointers and result updating
                counter ++;
                left = right;
                right ++;
            }
        }
        //Return result
        return counter;
    }
```

## Sliding Window Pattern
This pattern involves creating a window which can be either an array or a number from one position to another. Depending on a certain condition, the window increases or closes (and a new window is created). Very useful for keeping track of a subset of data in an array/string etc.

This pattern is very useful to find a max of something within a string or array, such us the longest substring with no repetitions, or the subarray with whose sum of elements is maximum.

Let's see this example: write a function called maxSubarraySum which accepts an array of integers and a number called n. The function should calculate the maximum sum of n consecutive elements in the array.
1. Understanding the problem: basically the function must find the subarray of length whose sum is max.
2. Concrete examples:
   - maxSubarraySum([1, 2, 5, 2, 8, 1, 5], 2) -> 10.
   - maxSubarraySum([1, 2, 5, 2, 8, 1, 5], 4) -> 17.
   - maxSubarraySum([4, 2, 1, 6], 1) -> 6.
   - maxSubarraySum([4, 2, 1, 6, 2], 4) -> 13.
   - maxSubarraySum([], 4) -> 0.
3. Break it down. In this case, this solution is O(N).
function maxSubarraySum(arr1) {
   //Initial validations, such us array empty or null, and limit of the number n
   //Initiate left to 0.
   //Initiate right to n - 1. Control the value of n, because if it's bigger than the size of the array, we need to set right to the array size.
   //Calculate the value of the first window. Set this value as max value too.
   //Iterate over the array starting from the first window.
   //Calculate the value of the next window based on the value of the previous windows + the next element - the first element.
   //If the value is bigger, update max value.
   //Update the current value to the value calculated, since we always need to know the last calculation.
   //Return biggest value.
}
4. Solve the problem based on 3.
5. Optimize the problem. In this case, we've solved the problem by using this algorithm. Here is the general sliding window pattern algorithm:

```java
public output functionName(input) {
   //Initial validations, such us array empty or null, and limit of the number n
   //Initiate left to 0.
   //Calculate the value of the first window, from left to n. Stablish initial result, such as the max result.
   //Iterate over the array starting from the first window, starting from n.
   //Run calculations needed
   //Apply conditions to update the result if validations meet the criteria
   //Return biggest value.
}
```

Here the algorithm:

```java
public static Integer maxSubarraySum(int[] arr, int n) {
        if(arr == null || arr.length == 0)
            return 0;
        int currentWindow = 0, maxWindow = 0;
        int left = 0;
        int right = n > arr.length ? arr.length - 1 : n - 1;
        for(int i = left; i <= right; i ++) {
            currentWindow = currentWindow + arr[i];
        }
        maxWindow = currentWindow;
        while(right < arr.length - 1) {
            currentWindow = currentWindow + arr[right + 1] - arr[left];
            if(currentWindow > maxWindow)
                maxWindow = currentWindow;
            right ++;
            left ++;
        }
        return maxWindow;
    }
```

## Divide and conquer
This pattern involves dividing a data set into smaller chunks and then repeating a process with a subset of data. This pattern can tremendously decrease time complexity.

Let's see this example: write a function called search which accepts an ordered array and a number, and returns the index of the number, or null otherwise.
1. Understanding the problem: basically the function must find a number. The key is to have the array ordered.
2. Concrete examples:
   - search([1, 2, 3, 4, 5, 6], 4) -> 3.
   - search([1, 2, 3, 4, 5, 6], 6) -> 5.
   - search([1, 2, 3, 4, 5, 6], 11) -> null.
3. Break it down. In this case, this solution is O(N).
function maxSubarraySum(arr1) {
   //Iterate over the array until finding the value.
}
4. Solve the problem based on 3.
5. Optimize the problem. In this case, since the array is orderd, we can split up the array by half, and search only on that half. Since we're having size / 2, size / 2 / 2, size / 2 / 2 / 2, ..., we're in a solution O(logN). Here is the general divide and conquer algorithm:

```java
public output functionName(input1, input2) {
    //Initiate the pointer or min for the initial size = 0.
    //Initiate the pointer or max for the final size = input.max - 1.
    //While min < max.
    //Calculate middle
    //Run comparisons
    //If not matches, pick the left side of the input of the rigth side on the input depending on whether input2 is smaller than the middle (pick left side) or vice versa (pick right side).
    //Update pointers
}
```

Here the algorithm:

```java
public static Integer search(int[] arr, int n) {
        //Initiate min
        int min = 0;
        //Initiate max
        int max = arr.length - 1;
        while(min < max) {
            //Calculate middle
            int middle = max - min / 2;
            //Comparison
            if(arr[middle] == n)
                return middle;
            else {
                //Pick left side or right side depending on the comparison
                if(n > arr[middle]) {
                    min = middle + 1;
                } else {
                    max = middle - 1;
                }
            }
        }
        return null;
    }
```

# Recursion
## The call stack
Basically recursion is a function which calls itself. What does it happen behind the scenes? There is a data structure which manages what happens when function are invoked:
- We're talking about a stack (first in last out). When a function is called, its called gets stacked into the stack, so when the function calls is done, it gets remove from the top of the stack.
- Any time a function is called, it is placed on the top of the stack (pushed).
- When the function is done, it gets removed from the top of the stack (pop).

## Recursive function pattern
This is how to define a recursive function:
1. Define the base case(s).
2. Do some stuff.
3. Call the recursive function with a subset of the current input data.

This is Fibonacci's function:
```java
public int calculateFibonacci(int number) {
    if(number == 0) return 1;
    else if(number == 1) return 1;
    else return calculateFibonacci(number - 1) + calculateFibonacci(number - 2);
}
```

## Common recursion pitfalls
- Not to define any base case.
- Forgetting to return in the case base or return the wrong thing.

Both cases lead to stack overflow!

# Search algorithms
## Linear search
To implement linear search with an array implies just to iterate the array from the first element up to the last one, just to try to find the value:
- Time complexity = O(N).
- Best case scenario = O(1).
- Worst case scenario = O(N).
- Average = O(N/2) = O(N).

## Binary search
In this case, it is mandatory that the input is sorted. Thanks to this order, you can remove half of the array at a time.
- Time complexity = O(logN).
- Best case scenario = O(1).
- Worst case scenario = O(logN).
- Average = O(logN).

## Native String Search
Suppose you want to count the number of times a smaller string appears in a longer string. A straightforward approach involves checking pairs of characters individually.

The pseudocode of this function would be:
- Loop over the larger string.
- Loop over the shorter string to compare it with a substring of the larger one.
- If the characters don't match, break out the inner loop.
- If the characters matches, increase the counter and keep looping over the larger string.
- Return the counter.

# Dijkstra's algorithms
## Introduction
This algorithm calculates the shortest path between 2 nodes or vertices. What's the fastest way to go from point A to point B?
- It makes use of a graph, because it searches across a graph.
- It makes use of a priority queue.

It is used for:
- GPS - finding the fastest route.
- Networking routing - finds open shortest path for data.
- Biology - used to model the spread of viruses among humans.
- Airline tickets - finding cheapest route to your destination.
- ...

## Example
Let's see the following graph:
```
{"A": [{"B", 4}, {"C", 2}], "B": [{"A", 4}, {"E", 3}], "C": [{"A", 2}, {"D", 2}, {"F", 4}], "D": [{"C", 2}, {"E", 3}, {"F", 1}], "E": [{"B", 3}, {"D", 3}, {"F", 1}], "F": [{"C", 4}, {"D", 1}, {"E", 1}]}
```

There are several paths:
- A -> B -> E: 7.
- A -> C -> D -> F -> E: 6.
- A -> C -> D -> E: 7.
- A -> C -> F -> E: 7.

How can we find the shortest path between A and E, the one weighted 6?
1. Every time we look to visit a new node, we pick the node with the smallest known distance to visit first.
2. Once we've moved to the node we're going to visit, we look at each of its neighbors.
3. For each neighboring node, we calculate the distance by summing the total edges that lead to the node we're checking from he starting node.
4. If the new total distance to a node is less than the previous total, we store the new shorter distance to the node.

Apart from that, we need two auxiliar structures to apply this algorithm:
1. Array of visited nodes to avoid an infinite loop.
2. Hash table of previous nodes to be able to do the backtracking.
3. Map with the distance from A to each node. The key of that map would be the destination node, and he value the distance.

Let's apply the algorithm:
1. Initialize the visited nodes to empty ([]).
2. Initialize the hash table of previous nodes to null: {A: null, B: null, C: null, D: null, E: null, F: null}. This table tells us how we got to each node.
3. We populate the map of distances. We put all of them to infinite, but A, because the distance from A to A is 0: {A: 0, B: infinite, C: infinite, D: infinite, E: infinite, F: infinite}
4. What are the neighbors of A? A, C and B. Edge from A to A weights 0, A to C weights 2, and the one from A to B weights 4. Hence, we pick A.
5. We updated the visited array with the node picked: [A].
6. We calculate the distance for all its neighbors having in mind the previous weights (ignoring visited) and we validate whether the current weight is greater: A to B is 4. 4 < infinite. A to C is 2. 2 < infinite.
7. We update the distances hash table having in mind the previous weights: {A: 0, B: 4, C: 2, D: infinite, E: infinite, F: infinite}
8. We update the previous hash table: {A: null, B: A, C: A, D: null, E: null, F: null} (because we got to B from A, and to C from A).
9. Next we pick the node with the smallest distance, ignoring the visited nodes. In this case, we pick C (weight 2).
10. We update the visited array with the node picked: [A, C].
11. We calculate the distance for all its neighbors having in mind the previous weights (ignoring visited) and we validate whether the current weight is greater: C to D is 2 + 2 from A, 4 < infinite. C to F is 4 + 2 from A, 6 < infinite.
12. We update the distances hash table having in mind the previous weights: {A: 0, B: 4, C: 2, D: 4, E: infinite, F: 6}
13. We update the previous hash table: {A: null, B: A, C: A, D: C, E: null, F: C}.
14. Next we pick the node with the smallest distance, ignoring the visited nodes. In this case, we can pick either B or D (weight 4).
15. We update the visited array with the node picked: [A, C, B].
16. We calculate the distance for all its neighbors having in mind the previous weights (ignoring visited) and we validate whether the current weight is greater: B to E is 3 + 4 from A, 7 < infinite.
17. We update the distances hash table having in mind the previous weights: {A: 0, B: 4, C: 2, D: 4, E: 7, F: 6}
18. We update the previous hash table: {A: null, B: A, C: A, D: C, E: B, F: C}.
19. Next we pick the node with the smallest distance, ignoring the visited nodes. In this case, only D (weight 4).
20. We update the visited array with the node picked: [A, C, B, D].
21. We calculate the distance for all its neighbors having in mind the previous weights (ignoring visited) and we validate whether the current weight is greater: D to E is 3 + 4 from A going through C, 7 is not < 7 -> ignore this path, D to F is 1 + 4 from A going through C, 5 < 6.
22. We update the distances hash table having in mind the previous weights: {A: 0, B: 4, C: 2, D: 4, E: 7, F: 5}
23. We update the previous hash table: {A: null, B: A, C: A, D: C, E: B, F: D}.
24. Next we pick the node with the smallest distance, ignoring the visited nodes. In this case, only F (weight 5).
25. We update the visited array with the node picked: [A, C, B, D, F].
26. We calculate the distance for all its neighbors having in mind the previous weights (ignoring visited) and we validate whether the current weight is greater: F to E is 1 + 5 from A going through C and D, 6 < 7. 
27. We update the distances hash table having in mind the previous weights: {A: 0, B: 4, C: 2, D: 4, E: 6, F: 5}.
28. We update the previous hash table: {A: null, B: A, C: A, D: C, E: F, F: D}.
24. Next we pick the node with the smallest distance, ignoring the visited nodes. In this case, only E (weight 6) and since it is our destination, we're done. We've found the shortest path: 6.

# Dynamic programming
## Introduction
DP is a method for solving a complex problem by breaking it down into a collection of simpler subproblems, solving each of those subproblems just once, and sorthing their solutions.

A problem is said o have overlapping subproblems if it can be broken down into subproblems which are reused several times. Not only it needs to break down into subproblems, but subproblems must be reusable.
- Fibonacci is an example of overlapping problem. For instance, F(6) = F(5) + F(4), and F(5) = F(4) + F(3), and so on. As we can see, F(4) appears twice, and the same happens with F(3), F(2) and F(1).
- Mergesort is an example of NOT overlapping problem. It can be broken down into subproblems, recursion is used for this algorithm, but subproblems can not be reused.

A problem is said to have an optimal substructure if an optimal solution can be constructed from optimal solutions of its problems:
- Basically if the optimal solution can be built by using the optimal solution of the subproblems, it it an optimal substructure.
- Imagine that we're looking for a flight from Madrid to Dallas. We find that the cheapest flight is going to New York: Madrid -> New York -> Dallas. If this problem were optimal substructure, it would mean that there is no cheapest flight from Madrid to New York, but it turns out there is a cheapest flight from Madrid to Ney Work going through London. Hence, the optional solution can't be built by the optimal solution of its subproblems.

To apply dynamic programming:
- The problem must have overlapping problems.
- The problem must have an optimal substructure.

## Memoization approach
This method consists of storing the result of expensive function calls and returning the cached result when the same input occurs again. This applies perfectly to Fibonacci, where there are several calls to Fibonacci with the same number (subnumbers of the original number).

## Botton up approach
In the previous approach, we went from Fib(X) to Fib(1) (base case), from the top to the bottom. In this approach, we will go from the bottom to the top. To do this we're using what we call Tabulation. Tabulation consists of storing the results of a previous result in a table (usually an array). Usually done using interation. Better space complexity can be achieved using tabulation because we're not fillint the call stack.

To calculate Fibonacci in this way, we start from Fib(0) and go up to the top:
public static int fib(int number) {
    if(n <= 2) return 1;
    int fibNums = [0, 1, 1];
    for(int i = 3; i <= number; i ++) {
        fibNums[i] = fibNums[i - 1] + fibNums[i - 2];
    }
    return fibNums[number];
}