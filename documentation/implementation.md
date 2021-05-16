# Implementation

## Project structure
The project has four packages:
* algorithms, which contain the three algorithms used in the program.
* datastructures, which contain my own implementations of the data structures used by the algorithms.
* parser, the class that parses [MovingAI maps](https://www.movingai.com/benchmarks/street/index.html) and turns them into 2-dimensional char arrays to run the algorithms on.
* ui, the package that contains the Main class, the JavaFX user interface class and the file chooser class. This package is ignored for unit tests.

## User interface
The project uses JavaFX for rendering a graphical user interface for the program. Inside the UI you can visualize the path finding algorithms. 
You can also compare the different algorithms and how they perform against each other.

## Algorithms

The project has currently three algorithms implemented: Dijkstra's algorithm, A* Search algorithm and Iterative Deepening A* algorithm.

### Dijkstra's algorithm
In the worst case scenario we add every node to the heap, which would result in a time complexity of O(*m* log *m*). We also have to remove them from the heap, resulting in a total time complexity of O(*n* + *m* log *m*)

### A* Search
A* search algorithm improves on Dijkstra's algorithm by introducing a heuristic function, which usually results in finding the path faster than Dijkstra. In the worst case scenario, the time complexity of A* is O(b^d), where b is the average number of branches per node. A* usually uses less memory than Dijkstra because it doesn't have to explore paths that aren't favorable.

### Iterative Deepening A*
IDA* performance iterations, which all include a depth-first search. It cuts branches off when their total cost exceeds a threshold given to the method as a parameter. At the beginning the threshhold is iniated as the estimated cost from the beginning node to the end node. After each iteration, the treshhold is increased so that it is the minimum cost of all the values exceeded by the current threshold.

IDA* is designed for low-memory usage. With short paths without any obstacles in the way of the path, the algorithm can perform very well. However, with longer paths or with paths with obstacles, the IDA* performance is way worse than that of A*'s and Dijkstra's.

## Possible flaws and improvements
It took me a long time to get a working version of iterative deepening a*, and I'm not sure if I still got it working as efficient as it could be. Possible future improvements to the program would be that I could test IDA* more with performance tests and see how it compares to the other algorithms. It would also be interesting to implement more algorithms, such as JPS.

## References

https://www.cs.helsinki.fi/u/ahslaaks/tirakirja/

https://en.wikipedia.org/wiki/A*_search_algorithm

https://en.wikipedia.org/wiki/Iterative_deepening_A*
