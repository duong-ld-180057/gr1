#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>
#include "AStar.h"
#include "type.h"

using namespace std;

vector<Node> nodes;
map<Pair, double> edges;

string getVertName(string token) {
  string vertName = token.substr(0, token.find("_"));
  return vertName;
}

void readMap(string filename) {
  ifstream file(filename);
  string line;
  while (getline(file, line)) {
    stringstream ss(line);
    string token;
    vector<string> tokens;
    while (getline(ss, token, ' ')) {
      tokens.push_back(token);
    }
    Node node;
    node.name = tokens[0];
    node.x = stod(tokens[1]);
    node.y = stod(tokens[2]);
    nodes.push_back(node);
  }
}

void readEdges(string inputFile) {
  ifstream file(inputFile);
  string line;
  while (getline(file, line)) {
    stringstream ss(line);
    vector<string> tokens;
    string token;

    while (getline(ss, token, ' ')) {
      tokens.push_back(token);
    }

    if (tokens.size() < 3) {
      continue;
    }

    string vert1 = getVertName(tokens[0]);
    double weight = stod(tokens[1]);
    string vert2 = getVertName(tokens[2]);

    Pair pair;
    pair.first = vert1;
    pair.second = vert2;
    edges[pair] = weight;
  }
}

Node findNode(string name) {
  for (Node node : nodes) {
    if (node.name == name) {
      return node;
    }
  }
  throw "Node not found";
}

int main(int argc, char const* argv[]) {
  readMap("map.txt");
  readEdges("input.txt");

  cout << "Enter start: ";
  string start;
  cin >> start;
  cout << "Enter destination: ";
  string destination;
  cin >> destination;

  try {
    Node startNode = findNode(start);
    Node destinationNode = findNode(destination);
    vector<Node> path = AStar::findPath(startNode, destinationNode, nodes, edges);
    cout << "=====================" << endl;
    cout << "Find a path: " << endl;
    for (Node node : path) {
      cout << node.name << " ";
    }
    cout << endl;
  } catch (const char* msg) {
    cout << msg << endl;
  }
  return 0;
}
