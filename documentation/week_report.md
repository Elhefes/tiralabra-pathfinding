## [Week 4](#week-4)
This week I began implementing IDA* search algorithm. However I didn't quite get it to work yet. I also made the UI better, now you can run multiple algorithms at once and see how they compare in terms of performance. It displays the time used by each algorithm.

Next week's goal is to fix the broken IDA* and add more tests and replace some of the data structures I have used (PriorityQueue, Stack)

Time used: 10 hours.

## [Week 3](#week-3)
This week I got the Dijkstra's algorithm working properly. I added A* search algorithm as well to the project so that the two algorithms can now be compared easily. I also improved the UI and added a file chooser to choose different maps. Then I initiated some unit test files but I didn't have the time to write the actual tests yet. This week I got jacoco and checkstyle working properly with Gradle after some problems.

Next week's goal is to implement IDA* search algorithm. I will also start creating tests for the application.

Time used: 11 hours.

## [Week 2](#week-2)
I started implementing the actual program. I started with building a simple file parser for the map files. After that I started the user interface to display the maps on, so it would be easy to see if the algorithms would work right from the start. I also coded the basic functionality for Dijkstra and it's visualisation. However I couldn't finish it quite yet, so that is something that I will do next. I also managed to add checkstyle to the project after lots of error troubleshooting with gradle. I also added some javadoc comments to the classes.

Next week's goal is to finish Dijkstra's algorithm and start to implement IDA* search algorithm. I will also improve the ui by adding more options and perhaps an ability to choose different maps. I will also start to build tests to my classes as that is something I didn't have time for this week.

### Problems
I have a weird bug with the Dijkstra's algorithm. It looks like it almost can find the optimal path, but not quite the right one. I haven't yet figured out why the bug occurs.

Time used: 12 hours.

## [Week 1](#week-1)
After familiarizing myself with the course material, I initialized the project with Gradle and setup Git. I also read some online materials on different path finding algorithms such as Dijkstra, A* and IDA*. After that I started to write some basic documentation.

I am not yet sure that in which kind of format should I get my data to run the algorithms on. One possibility could be that I could get photos of maps for example from [here](https://www.movingai.com/benchmarks/street/index.html) and perform the path finding based on the colours of the photo. The black pixels in the photos could be walls and everything else space where the algorithms could traverse.

A fun feature to implement would be that the user could draw a map himself, and run the path finding algorithms on the drawing. That shouldn't be very hard to implement if I can display the algorithm results on the maps visually. I wonder if there would be any drawbacks for example in performance in showing the algorithm working in real time with a graphical interface such as JavaFX?

Next week I plan on beginning to implement some core features such as at least one of the pathfinding algorithms, and some kind of a UI to display the results.

Time used: 6 hours.
