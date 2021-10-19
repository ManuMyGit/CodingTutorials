# Sorting Data
## Sorting algorithms
There are a lot of algorithms to sort data (such as arrays). Each one has pros and cons. Here a webpage with animations about different sorting algorithms: https://www.toptal.com/developers/sorting-algorithms.

# Bubble sort
## Introduction
This algorithm is not that common to use, it's not that efficient. Generally we're not going to use it. There is one use case that excels.

It is called bubble sort because the largest values bubble up to the top! This is an example of how the algorithm would work:
- Input: [29, 10, 14, 30, 37, 14, 18].
- Step 1: Compare 29 and 10. 29 > 10 -> [10, 29, 14, 30, 37, 14, 18].
- Step 2: Compare 29 and 14. 29 > 14 -> [10, 14, 29, 30, 37, 14, 18].
- Step 3: Compare 29 and 30. 30 > 29 -> array remains the same.
- Step 4: Compare 30 and 37. 37 > 30 -> array remains the same.
- Step 5: Compare 37 and 14. 37 > 14 -> [10, 14, 29, 30, 14, 37, 18].
- Step 6: Compare 37 and 18. 37 > 18 -> [10, 14, 29, 30, 14, 18, 37]. As we can see, the largest value bubbled to the top in the first iteration.
- Now, we repeat the process but until array.length -1, because for sure the latest number (37) is the largest one, so it is out of the next step.

## Implementation
```java
public static void bubleSort(int[] arr) {
        for(limit = arr.length; limit > 0; limit --) {
            for (int i = 0; i < limit - 1; i++) {
                if(arr[i] > arr[i + 1]) {
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                }
            }
        }
    }
```

## Optimization
Even if the array is ordered, our code will try to order it and will iterate over and over again without doing nothing.

To improve this algorithm the only thing we need to do is to include a flag, asking whether in the last loop there was any swap. If there wasn't, we're done, we can break the loop.

```java
public static void bubleSort(int[] arr) {
        for(int limit = arr.length; limit > 0; limit --)  {
            boolean swaps = false;
            for (int i = 0; i < limit - 1; i++) {
                if(arr[i] > arr[i + 1]) {
                    swaps = true;
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                }
            }
            if(!swaps) break;
        }
    }
```

## Big O
- In general, time complexity is O(N ^ 2) because we have a nested loop.
- If the data is nearly ordered, with the previous and best version of the algorithm, time complexity is O(N).

# Selection sort
## Introduction
This similar is similar to bubble sort, but instead of first placing large values into sorted position (at the end of the array), it places small values into sorted position (at the beginning of the array). In contrast to bubble sort, we're not swaping elements in every comparison, but finding the smallest element in evey iteration and placing it at the beginning of the array.

Let's see this example:
- Input: [5, 3, 4, 1, 2].
- Iteration 1. Number 1 is the smallest number: [1, 3, 4, 5, 2].
- Iteration 1. Number 2 is the smallest number: [1, 2, 4, 5, 3].
- Iteration 3. Number 3 is the smallest number: [1, 2, 3, 5, 4].
- Iteration 4. Number 4 is the smallest number: [1, 2, 3, 4, 5].

## Implementation
```java
public static void selectionSort(int[] arr) {
        for(int i = 0; i < arr.length - 1; i ++) {
            int indexMinimum = i;
            for(int j = i + 1; j < arr.length; j ++) {
                if(arr[indexMinimum] > arr[j]))
                    indexMinimum = j;
            }
            int tmp = arr[i];
            arr[i] = arr[indexMinimum];
            arr[indexMinimum] = tmp;
        }
    }
```

## Big O
- In general, time complexity is O(N ^ 2) because we have a nested loop.
- Selection sort is better than bubble sort if we want to minimize the swaps since there is just 1 swap at the end of the loop. If for some reason we're worried about that, selection is better than bubble. Otherwise, they are equally bad.

# Insertion sort
## Introduction
This algorithm builds up the sort by gradually creating a larger left portion which is always sorted. This is probably the most intuitive one from the logic perspective:

Let's see this example:
- Input: [5, 3, 4, 1, 2].
- Iteration 1. The array on the left is just the 5, and 3 > 5 so 3 and 5 are reordered: [3, 5, 4, 1, 2].
- Iteration 2. The array on the left is [3, 5], so 4 is placed oreded within the array: [3, 4, 5, 1, 2].
- Iteration 3. The array on the left is [3, 4, 5], so 1 is placed ordered within the array: [1, 3, 4, 5, 2].
- Iteration 4. The array on the left is [1, 3, 4, 5], so 2 is place ordered within the array: [1, 2, 3, 4, 5].

## Implementation
```java
public static void insertionSort(int[] arr) {
        for(int i = 1; i < arr.length; i ++) {
            int currentValue = arr[i];
            int j;
            for(j = i - 1; j >= 0 && arr[j] > currentValue; j --) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = currentValue;
        }
    }
```

## Big O
- In general, time complexity is O(N ^ 2) because we have a nested loop.
- If the array is almost sorted, it is O(N).
- Worst case scenario for this algorithm is when the array is sorted but the other way around, from the largest to the smallest number.
- This algorithm works as data is coming in, as it receives new data. It doesn't have to have the entire array at once

## Bubble vs selection vs insertion
Algorithm | Time Complexity (Best) | TC (Average) | TC (Worst) | Space Complexity
--- | --- | --- | --- | --- 
Bubble sort | O(N) | O(N ^ 2) | O(N ^ 2) | O(1)
Selection sort | O(N ^ 2) | O(N ^ 2) | O(N ^ 2) | O(1)
Insertion sort | O(N) | O(N ^ 2) | O(N ^ 2) | O(1)

In the best scenario, almost sorted data, bubble sort with optimization and insertion performs better than selection. Selection doesn't performs well because it needs to iterate the outer and inner loop for the entire array.

Since we're not using auxiliar temporary, like new arrays, just using specific variables, space complexity is O(1).

# Merge sort
## Introduction
Why to learn new sorting algorithms? Because the sorting algorithms learnt so far don't scale well. This means that the bigger the array of the size is, the worst the algorithm behave, exponentially worse. For instance, with 10000 elements array, bubble sort can take up to 20 seconds to sort the array, wereas merge sort takes less than 1 second. Basically, we're going from O(N^2) to O(NlogN).

The idea behind this algorithm is merging and sorting:
- It consists of splitting up, sorting and merging.
- It exploits the fact that arrays of 0 or 1 elements are always sorted.
- It works by decomposing an array into smaller arrays of 0 or 1 elements, then building up a new sorted array.

Let's see this example:
- Input: [8, 3, 5, 4, 7, 6, 1, 2].
- Iteration 1. Array is divided into two: [8, 3, 5, 4] and [7, 6, 1, 2].
- Iteration 2. Both arrays are divided into two: [8, 3], [5, 4], [7, 6] and [1, 2]
- Iteration 3. The four arrays are divided into two: [8], [3], [5], [4], [7], [6], [1] and [2].
- Now each array is sorted (arrays of 0 or 1 elements are always sorted). The next step is to order and merge them.
- Iteration 4. [3, 8], [4, 5], [6, 7], [1, 2].
- Iteration 5. [3, 4, 5, 8] and [1, 2, 6, 7]. This can be done quickly because each subarray is already sorted. Mergin sorted arrays is always easier than unsorted ones.
- Iteration 6. Final array: [1, 2, 3, 4, 5, 6, 7, 8].

## Merging arrays
- In order to implement merge sort, it is always useful to implement first a function responsible for merged to sorted arrays.
- Given two sorted arrays, this helper function should create a new array which is also sorted, and consists of all of the elements in the two input arrays.
- This function should run O(N + M) in time and O(N + M) in space and should not modify the parameters passed to it.

This is the pseudocode:
1. Create an empty array.
2. Take a look at the smallest values in each input array.
3. While there are still values we haven't looked at:
4. If the value in the first array is smaller than the value in the second array, push the value in the first array into the result and move on to the next value on the first array.
5. If the value in the first array is larger than the value in the second array, push the value in the second array into the result and move on to the next value on the second array.
6. Once we exhaust one array, just push in all remaining values from the other array.

Let's see this example: arr1 = [1, 10, 50], arr2 = [2, 14, 99, 100].
1. Step 1: create empty array [].
2. Step 2: since 1 < 2, I take 1 and push into the result array -> [1]. I move the pointer to the next element of the first array (10).
3. Step 3: since 10 > 2, I take 2 and push into the result array -> [1, 2]. I move the pointer to the next element of the second array (14).
4. Step 4: since 10 < 14, I take 10 and push into the result array -> [1, 2, 10]. I move the pointer to the next element of the first array (50).
5. Step 5: since 50 > 14, I take 14 and push into the result array -> [1, 2, 10, 14]. I move the pointer to the next element of the second array (99).
6. Step 6: since 50 < 99, I take 50 and push into the result array -> [1, 2, 10, 14, 50]. Since we're pointing to the last element of one of the arrays, I just merge the rest of the other array.
7. Step 7: [1, 2, 10, 14, 50, 99, 100].

Here the real code for this helper function:

```java
public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        if (arr1.length == 0)
            result = arr2;
        else if (arr2.length == 0)
            result = arr1;
        else {
            int pointerArr1 = 0, pointerArr2 = 0, index = 0;
            while (pointerArr1 < arr1.length && pointerArr2 < arr2.length) {
                if (arr1[pointerArr1] < arr2[pointerArr2]) {
                    result[index] = arr1[pointerArr1];
                    pointerArr1++;
                } else {
                    result[index] = arr2[pointerArr2];
                    pointerArr2++;
                }
                index++;
            }
            while (pointerArr1 < arr1.length) {
                result[index] = arr1[pointerArr1];
                pointerArr1++;
                index++;
            }
            while (pointerArr2 < arr2.length) {
                result[index] = arr2[pointerArr2];
                pointerArr2++;
                index++;
            }
        }
        return result;
    }
```

## Implementation
Here is the implementation using the previous function:
```java
public static int[] mergeSort(int[] arr) {
        if(arr.length == 0 || arr.length == 1)
            return arr;
        else {
            int[] left = mergeSort(Arrays.copyOfRange(arr, 0, arr.length / 2));
            int[] right = mergeSort(Arrays.copyOfRange(arr, arr.length / 2, arr.length));
            return mergeSortedArrays(left, right);
        }
    }
```

## Big O
- Time complexity for best, worst and average scenario: O(NlogN).
  - Time complexity is the same for all scenarios because the algorith iterates over the entire array half by half, no matter whether the array is order or not.
- Space complexity: O(N + logN) = O(N). LogN is due to the call stack, but it can be removed according to Big O rules.
  - This complexity is O(N) because we need a brand new array, whose size is the same as the intput. Hence, O(N).

Let's talk about the time complexity: how many times do we need to divide the array (half by half) until reaching the case base? How many times do we need to divide the array to decompose it in base cases? This is the definition of logarithm: logN. But, once the array has been divided into base cases, we need to merge the pieces to build the final array. In every ordered merge we iterate over the two arrays to build the merged array, so in order to merge the arrays, in every logN step, we're iterating lineally the arrays. This is why O(N). So, we go logN steps, and in each step we go over the entire array lineally -> O(NlogN).
 
# Quick sort
## Introduction
This algorithm is based on:
- Like merge sort, exploits the fact that arrays of 0 and 1 elements are always ordered.
- It works by selecting an element (called "pivot") and finding the index where the pivot should end up in the sorted array.
- Basically we move all the numbers smaller than the pivot to the left, and the ones bigger than the pivot to the right. We're not sorting them all, we're just moving them. But we do know that the pivot is in the correct spot, only this number. Then, we repeat the process with the left and the right side.

Let's see this example:
- Input: [5, 2, 1, 8, 4, 7, 6, 3].
- Step 1: select the pivot, in this case 5. Then, we'll move the elements smaller than 5 to the left, and bigger than 5 to the right.
- Step 2: [2, 1, 4, 3, 5, 8, 7, 6]. Now we know that the number 5 is where it should be. Now we do the same with the left side and the right side.
- Step 3: Left side: [2, 1, 4, 3]. Pivot 2.
- Step 4: Left side: [1, 2, 4, 3]. The number 2 is where it should be. Now, the same with left and right side.
- Step 5: Left left side: [1]. Base case.
- Step 6: Left right side: [4, 3]. Pivot 4.
- Step 7: Left right left side: [3, 4]. The number 4 is where it should be.
- Step 8: Left right left left side: [3]. Base case.
- Step 9: Left right left right side: empty. Base case.
- Step 10: Merge [1], 2 and [3, 4] -> [1, 2, 3, 4].
- ... Same with the right array [8, 7, 6].

## Pivot helper
In order to implement quick sort, it's useful to first implement a function resposible for arranging elements in an array on either side of the pivot.
- Given an array, this helper function should designate an element as a pivot.
- In should then rearrange elements in the array so that all values smaller than the pivot get moved to the left of it, and the ones bigger than the pivot, to the right side.
- The order of the elements on either side of the pivot doesn't matter.
- The helper should do this in place, that is, it shouldn't create any new array.

To pick the pivot:
- The runtime of this algorithm depends in part on how one selects the pivot.
- Ideally, the pivot should be chosen so that it's roughly the median value in the data we're sorting.
- For simplicity, we'll always choose the pivot to be the first element.

```java
private static int pickPivot(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int swapIndex = startIndex;
        for(int i = startIndex + 1; i < endIndex; i ++) {
            if(pivot > arr[i]) {
                swapIndex ++;
                swap(arr, swapIndex, i);
            }
        }
        swap(arr, startIndex, swapIndex);
        return swapIndex;
    }

private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
```

## Implementation
This is how the algorithm should behave:
- Call the pivot helper on the array.
- When the helper returns the updated pivot array, recursively call the pivot helper on the array to the left and to the right of the pivot.

```java
public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = pickPivot(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
```

## Big O
- Time complexity depends on the scenario:
  - Time complexity is the same for best and average: O(NlogN). This case is pretty similiar to merge sort, we divide the array (well, not literally because we work with the same array and change the pointers) by half, so we're under O(logN). But for very half, we iterate over the half to find the pivot, which is O(N). Hence, the total time complexity is O(NlogN).
  - Time complexity for worst case scenario is: O(N^2). The worst case scenario happens when the array is already ordered. In that case, if we pick the pivot as the first element always, there will be no half/half division. The left side will be the pivot, and the right side will be the entire array. That means that we'll have N partitions instead of logN, and since we iterate the entire partition to compare, we're talking about O(N^2). That can't be solved if we change the way we pick the pivot. If we pick the pivot for instance as the last element, and the array is ordered the other way around, we'll have the same scenario. Basically, worst case is sceanrio when the pivot pivots around the min or max value of the array. This could be "improved" by picking a random number, but if by any chance the randon number pivots around min or max, we're on the same scenario.
- Space complexity: O(logN).
  - Time complexity is the same for best and average: O(logN). This space is the one we need for the stack call.
  - Time complexity for worst case scenario is: O(N). Worst case scenario, we're calling N times, so the stack memory is up to N calls.

# Radix sort
## Introduction
So far, all sorting algorithms are based on comparisons. We end up comparing elements to set the order. Based on this strategy, we get to a time and space complexity for each one. The question is, can we do better? The answer is "sort of", but not making comparisons. Mathematically, there is a lower boundary which prevent the comparison-based algorithms from doing better.

There is a way to do it better, but only in certain cases. The algorithms used in those cases do not compare data, they take advantage of special properties of the data. For instance, there is a group called "Sorted Integer Algorithms" and they only work with Integers. We don't actually do direct comparisons. One of those is Radix sort.

Let's see the properties of this algorithm:
- Radix sort is a special sorting algorithm that works on lists of numbers.
- It never makes comparisons between elements.
- Instead, it exploits the fact that information about the size of a number is encoded in the number of digits. More digits mean a bigger numbers!

Let's see this example:
- Input: [1556, 4, 3556, 593, 408, 4386, 902, 7, 8157, 86, 9637, 29].
- We create what it's called buckets and we iterate over the array and we pick the last digit of each number, and we group them in one of the buckets created. We can create base-10 buckets, so we would have 10 buckets, or base-7 buckets, having 7 buckets, ...
- In this case we have the following buckets, which are not ordered.
   - Bucket 0: empty.
   - Bucket 1: empty.
   - Bucket 2: 902.
   - Bucket 3: 593.
   - Bucket 4: 4.
   - Bucket 5: empty.
   - Bucket 6: 1556, 3556, 4386, 86.
   - Bucket 7: 7, 8157, 9637.
   - Bucket 8: 408.
   - Bucket 9: 29.
- Then we build a list with the numbers of the buckets: [902, 593, 4, 1556, 3556, 4386, 86, 7, 8157, 9637, 408, 29]
- Next we repeat the process but with the second digit of the elements in the array. If there is no 2nd digit, it counts as 0. These would be the buckets:
   - Bucket 0: 902, 4, 7, 408.
   - Bucket 1: empty.
   - Bucket 2: 29.
   - Bucket 3: 9637.
   - Bucket 4: empty.
   - Bucket 5: 1556, 3556, 8157.
   - Bucket 6: empty.
   - Bucket 7: empty.
   - Bucket 8: 4386, 86.
   - Bucket 9: 593.
- Then we build a list with the numbers of the buckets: [902, 4, 7, 408, 29, 9637, 1556, 3556, 8157, 4386, 86, 593]
- Now we do the same with the 3rd digit. The result would be: [4, 7, 29, 86, 8157, 4386, 408, 1556, 3556, 593, 902]
- Now we do the same with the 4rd digit. The result would be: [4, 7, 29, 86, 408, 593, 902, 1556, 3556, 4386, 8157]

## Helpers
We need to build a couple of helpers to run this algorithm.

### getDigit
Given a number and a position, to return the digit on hat position. For instance, getDigit(12345, 0) = 5, getDigit(12345, 2) = 3 ,getDigit(12345, 5) = 0.

```java
private int getDigit(int number, int position) {
        return (int) (Math.floor(Math.abs(number)/ Math.pow(10, position)) % 10);
    }
```

### Repetitions
We need a function which tells us how many times we need to keep building and filling the buckets.

```java
private int digitCount(int number) {
        if(number == 0) return 1;
        return (int) (Math.floor(Math.log10(Math.abs(number)))) + 1;
    }
```

### Most digits
We need a function which takes a list of numbers and tells us which number has the most digits by using digitCount.

```java
private int digitCount(int[] numbers) {
        int maxDigits = 0;
        for(int i = 0; i < numbers.length; i ++) {
            maxDigits = Math.max(maxDigits, digitCount(numbers[i]));
        }
        return maxDigits;
    }
```

## Implementation
This is how the algorithm should behave:
- Define the function which accepts the list of numbers.
- Figure out how many digits the largest number has.
- Loop from k = 0 to the largest number of digits.
- For each iteration: create 10 buckets (we're working with base-10), place each number in the corresponding bucket based on its kth digit.
- Replace the existing array with the values in the bucket.

Here the code:

```java
public static int[] radixSort(int [] arr) {
        int maxDigits = maxDigits(arr);
        for(int k = 0; k < maxDigits; k ++) {
            Map<Integer, List<Integer>> mapa = new HashMap<>();
            for(int i = 0; i < 10; i ++) {
                mapa.put(i, new ArrayList<Integer>(arr.length));
            }
            for(int i = 0; i < arr.length; i ++) {
                int digit = getDigit(arr[i], k);
                mapa.get(digit).add(arr[i]);
            }
            arr = new int[arr.length];
            int index = 0;
            for(int i = 0; i < 10; i ++) {
                List bucket = mapa.get(i);
                for(int j = 0; j < bucket.size(); j ++) {
                    arr[index] = (int)bucket.get(j);
                    index++;
                }
            }
        }
        return arr;
    }
```

## Big O
Let's analyze this algorithm:
1. We have an external loop controlled by k. So far, O(K).
2. Within the loop, we have a creation operation (O(1)), a loop of 10 (O(10)), a loop over the array (O(N)), initialization (O(1)), initialization (O(1)), a loop of 10 (O(10)), and a inner loop of something equal or smaller than N (the numbers are spread across the buckets) -> O(1 + 10 + N + 1 + 1 + 10 * N) = O(N + 10N) = O(11N) = O(N).
3. The time complexity then is O(KN).

With regards to the space complexity, O(K + N).

# Heapsort
## Introduction
Heap sort is another comparison-based sorting technique based on Binary Heap data structure. It is similar to selection sort where we first find the minimum element and place the minimum element at the beginning. We repeat the same process for the remaining elements.

A Binary Heap is a Binary Tree with the following properties:
- It's a complete tree (all levels are completely filled except possibly the last level and the last level has all keys as left as possible).
- A Binary Heap is either Min Heap or Max Heap. In a Min Binary Heap, the key at root must be minimum among all keys present in Binary Heap. The same property must be recursively true for all nodes in Binary Tree. Max Binary Heap is similar to MinHeap.

## Implementation
Basically the algorithm consists of building the Max Binary Heap, take the root (which is the max element of the array) and repeat the process.

This algorithm behaves well with unsorted data, where it is as good as the quicksort and doesn't make use of additional memory, but it's too inefficient with ordered data, and it's quite too complex.

These are the steps:
1. Binary Heap is built from the original array.
2. The root (which is the max element of the tree) is placed at the end of the array.
3. The last element of the Binary Heap is placed as root.
4. The new root is switched with the maximum element. The root is again the max element of the tree.
5. We get back to step 2 until the array gets sorted.

## Big O
- Time complexity depends on the scenario:
  - Time complexity is the same for worst, best and average scenario: O(NlogN).
- Space complexity: O(1).

# Smooth sort
## Introduction
Like heapsort, smoothsort organizes the input into a priority queue and then repeatedly extracts the maximum. Also like heapsort, the priority queue is an implicit heap data structure (a heap-ordered implicit binary tree), which occupies a prefix of the array. Each extraction shrinks the prefix and adds the extracted element to a growing sorted suffix. When the prefix has shrunk to nothing, the array is completely sorted.

## Big O
- Time complexity depends on the scenario:
  - Time complexity is the same for worst and average: O(NlogN).
  - Time complexity for best case scenario is: O(N). The best case scenario happens when the array is relatively ordered.
- Space complexity: O(1).

# Shell short
## Introduction
Shell sort is a highly efficient sorting algorithm and is based on the insertion sort algorithm. This algorithm avoids large shifts as in case of insertion sort, if the smaller value is to the far right and has to be moved to the far left.

## Big O
- Time complexity depends on the scenario:
  - Time complexity is the same for worst and average: O(NlogN^2).
  - Time complexity for best case scenario is: O(N).
- Space complexity: O(1).

# Sorting algorithm comparisons
Algorithm | Space Complexity | TC Best | TC Average | TC Worst
---| ---| ---| --- | ---
Bubble Sort | O(1) | O(N) | O(N^2) | O(N^2)
Selection Sort | O(1) | O(N^2) | O(N^2) | O(N^2)
Insertion Sort | O(1) | O(N) | O(N^2) | O(N^2)
Merge Sort | O(N) | O(NlogN) | O(NlogN) | O(NlogN)
Quick Sort | O(logN) | O(NlogN) | O(NlogN) | O(NlogN)
Radix Sort | O(K + N) | O(KN) | O(KN) | O(KN)
Heap Sort | O(1) | O(NlogN) | O(NlogN) | O(NlogN)
Smooth Sort | O(1) | O(N) | O(NlogN) | O(NlogN)
Shell Short | O(1) | O(NlogN) | O(NlogN^2) | O(NlogN^2)