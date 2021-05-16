# User manual

You can either download the latest release of the program [here](https://github.com/Elhefes/tiralabra-pathfinding/releases) or download the git project as a zip from [here](https://github.com/Elhefes/tiralabra-pathfinding/archive/refs/heads/main.zip). You can run the releases tiralabra-pathfinding.jar with ```java -jar tiralabra-pathfinding.jar```. The program should start and it should first ask for a map run the algorithms on. You should use [MovingAI maps](https://www.movingai.com/benchmarks/street/index.html) with the program. I also included one map in the release too. You can run the git project with command ```./gradlew run``` from the *tiralabra-pathfinding/tiralabra-pathfinding* folder which contains the gradlew script.

After these steps the view should look something like this:

![manual](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/images/manual.png)

Although in the picture the algorithms have already been ran. In the right hand side, you can see a panel with all kinds of buttons and settings to play with. First you should click ```Set start```, after which you can click somewhere on the map (not from a black spot which are the walls) to set the starting node for the algorithm. You should do the same for the goal node by clicking ```Set end```. If you want you can also set the coordinates of the nodes manually by typing them to the field below the buttons. If you type them manually, you have to press enter so that the coordinates update.

After choosing the start and goal nodes, you can choose which algorithms you want to run. Currently, Dijkstra's algorithm, A* search algorithm and iterative deepening A* algorithm are supported. You can choose any number of them at the same time, the program runs them one after another. If you choose to run the iterative deepening a* algorithm, there is an option to choose the timeout period in seconds for the algorithm. This prevents the program from crashing, as often times iterative deepening a* is a very slow algorithm.

When the start and end nodes and the algorithms have been chosen, you can hit ```Run``` to start running the algorithms. When all of the algorithms finish (or timeout), the results will be displayed under the ```Reset``` button, as seen in the picture. If iterative deepening a* timeouts, It's results will not be displayed and the console will print "Timeout!"

You can press the ```Reset``` button to clear the map of all of drawn algorithm paths.
