#ifndef ASTAR_H
#define ASTAR_H

#include <iostream>
#include <map>
#include <string>
#include <vector>
#include "type.h"

using namespace std;

class AStar {
 public:
  static double heuristic(Node next, Node goal);
  static vector<Node> getPosibleNodes(Node start,
                                      vector<Node> nodes,
                                      map<Pair, double> edges);
  static vector<Node> findPath(Node start,
                               Node destination,
                               vector<Node> nodes,
                               map<Pair, double> edges);
  static bool checkClosedList(Node node, vector<Node> closedList);

  static vector<Node> sortOpenList(vector<Node> openList);

  static bool compareNodes(Node node1, Node node2);
};

#endif  // ASTAR_H
