# Testing

## Junit

This project uses Junit for automatic unit testing. You can run the tests yourself by running the command 
```./gradlew test```
inside the *tiralabra-pathfinding/tiralabra-pathfinding* folder. All implemented algorithms and data structures are tested.

## Performance testing

Performance testing can be done on the graphical user interface. You can compare Dijkstra's A* search's and IDA*'s performance.

The following tests were all made with the London_2_1024 map. The results are sorted by the length of the shortest path. The times are the averages of 10 runs.

| Algorithm | Nodes visited | Time (ms) | Start node | Goal node | Shortest path length |
|-----------|---------------|-----------|------------|-----------|----------------------|
| Dijkstra  | 5757          | 1         | 65,310     | 87,346    | 45.1126983           |
| A*        | 655           | 1         | 65,310     | 87,346    | 45.1126983           |
| IDA*      | 1636017       | 43        | 65,310     | 87,346    | 45.1126983           |
| Dijkstra  | 6569          | 1         | 428,254    | 390,288   | 52.08326112          |
| A*        | 388           | 0         | 428,254    | 390,288   | 52.08326112          |
| IDA*      | 140534        | 7         | 428,254    | 390,288   | 52.08326112          |
| Dijkstra  | 9408          | 2         | 994,404    | 952,452   | 65.39696962          |
| A*        | 728           | 0         | 994,404    | 952,452   | 65.39696962          |
| IDA*      | 205           | 0         | 994,404    | 952,452   | 65.39696962          |
| Dijkstra  | 12706         | 2         | 350,346    | 390,288   | 74.56854249          |
| A*        | 1806          | 1         | 350,346    | 390,288   | 74.56854249          |
| IDA*      | 2945095       | 79        | 350,346    | 390,288   | 74.56854249          |
| Dijkstra  | 14835         | 2         | 846,52     | 916,94    | 90.71067811          |
| A*        | 1562          | 0         | 846,52     | 916,94    | 90.71067811          |
| IDA*      | 350262        | 13        | 846,52     | 916,94    | 90.71067811          |
| Dijkstra  | 23545         | 4         | 483,875    | 533,787   | 108.7106781          |
| A*        | 3631          | 1         | 483,875    | 533,787   | 108.7106781          |
| IDA*      | 404           | 0         | 483,875    | 533,787   | 108.7106781          |
| Dijkstra  | 37,525        | 7         | 517,398    | 603,490   | 127.6223664          |
| A*        | 1980          | 1         | 517,398    | 603,490   | 127.6223664          |
| IDA*      | 1464          | 0         | 517,398    | 603,490   | 127.6223664          |
| Dijkstra  | 80440         | 13        | 236,392    | 398,334   | 186.0243866          |
| A*        | 10335         | 1         | 236,392    | 398,334   | 186.0243866          |
| IDA*      | 917           | 0         | 236,392    | 398,334   | 186.0243866          |
| Dijkstra  | 107123        | 21        | 513,386    | 425,562   | 212.4507935          |
| A*        | 15831         | 4         | 513,386    | 425,562   | 212.4507935          |
| IDA*      | 5858887       | 170       | 513,386    | 425,562   | 212.4507935          |
| Dijkstra  | 175077        | 35        | 382,16     | 64,292    | 432.3229432          |
| A*        | 29066         | 5         | 382,16     | 64,292    | 432.3229432          |
| IDA*      | -             | -         | 382,16     | 64,292    | 432.3229432          |

Here are the same results as charts:

![dijkstra](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/images/dijkstra.png)
![astar](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/images/astar.png)
![ida](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/images/ida.png)

The y-axis represents the shortest paths length and the x-axis represents the time used by the algorithm.

You should note that the y-axis values are scaled between the minimum and the maximum path length values each algorithm encountered. That's why they look almost the same speed, but in reality IDA* is much slower in many cases. Here is the combined chart:

![total](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/images/total.png)

Where the green line is Dijkstra, orange line is A* and the blue line is IDA*.

As we can clearly see Dijkstra and A* performed very well even with longer paths. However iterative deepening A* starts to struggle already at paths longer than 200. I noticed also that IDA* struggles to find the path also with short paths with obstacles in the way of the path.

## Jacoco test report
Jacoco test report can be generated by running ```./gradlew jacocoTestReport``` inside the *tiralabra-pathfinding/tiralabra-pathfinding* folder. The report will be generated to *tiralabra-pathfinding/build/reports/jacoco/test/html/index.html*. Jacoco ignores the UI as that is difficult if not impossible to unit test. Here are the current results:

![jacocoTestReport](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/images/jacoco.png)
