#include "AStar.h"
#include <math.h>
#include <algorithm>
#include <iostream>

double AStar::heuristic(Node next, Node goal) {
  double x = next.x - goal.x;
  double y = next.y - goal.y;
  return sqrt(x * x + y * y);
}

bool AStar::checkClosedList(Node node, vector<Node> closedList) {
  for (Node closed : closedList) {
    if (closed.name == node.name) {
      return true;
    }
  }
  return false;
}

vector<Node> AStar::getPosibleNodes(Node start,
                                    vector<Node> nodes,
                                    map<Pair, double> edges) {
  vector<Node> posibleNodes;
  for (Node node : nodes) {
    if (node.name == start.name) {
      continue;
    } else {
      Pair pair;
      pair.first = start.name;
      pair.second = node.name;
      if (edges.find(pair) != edges.end()) {
        posibleNodes.push_back(node);
      } else {
        continue;
      }
    }
  }

  return posibleNodes;
}

bool AStar::compareNodes(Node node1, Node node2) {
  return node1.f < node2.f;
}

vector<Node> AStar::findPath(Node start,
                             Node destination,
                             vector<Node> nodes,
                             map<Pair, double> edges) {
  vector<Node> path;
  vector<Node> openList;
  vector<Node> closedList;
  openList.push_back(start);
  while (!openList.empty()) {
    Node current = openList.front();
    openList.erase(openList.begin());
    closedList.push_back(current);

    if (current.name == destination.name) {
      break;
    }
    vector<Node> posibleNodes = getPosibleNodes(current, nodes, edges);

    for (Node node : posibleNodes) {
      Pair pair;
      pair.first = current.name;
      pair.second = node.name;
      double g = current.g + edges[pair];
      double h = heuristic(node, destination);
      double f = g + h;
      node.g = g;
      node.h = h;
      node.f = f;
      node.parent = current.name;

      if (checkClosedList(node, closedList)) {
        continue;
      }
      openList.push_back(node);
    }

    // sort openList by f
    sort(openList.begin(), openList.end(), compareNodes);
  }

  // build path
  Node current = closedList.back();
  if (current.name != destination.name) {
    throw "No path found";
  }

  while (current.name != start.name) {
    path.push_back(current);

    for (auto node : closedList) {
      if (node.name == current.parent) {
        current = node;
        break;
      }
    }
  }
  path.push_back(start);
  reverse(path.begin(), path.end());
  return path;
}