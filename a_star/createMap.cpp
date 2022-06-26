#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

typedef struct Node {
  string name;
  string directIntersection;
  double x;
  double y;
} Node;
typedef pair<string, string> Pair;

vector<Node> nodes;
map<Pair, double> edges;
vector<vector<string> > paths;

string getVertName(string token) {
  string vertName = token.substr(0, token.find("_"));
  return vertName;
}

// convert X_Y to Y
string getDestination(string token) {
  string junction = token.substr(token.find("_") + 1);
  return junction;
}

void makePaths(string filename) {
  ifstream file(filename);
  string line;
  while (getline(file, line)) {
    stringstream ss(line);
    string token;
    vector<string> path;
    while (getline(ss, token, '$')) {
      path.push_back(token);
    }
    string last = path.back();
    string vertName = getDestination(last);
    path.pop_back();
    path.push_back(vertName);
    paths.push_back(path);
  }
}

string findDirectIntersection(string laneID) {
  for (auto path : paths) {
    for (auto vert : path) {
      if (vert == laneID) {
        return path.back();
      }
    }
  }
  return "";
}

bool checkContain(string nodeName) {
  for (int i = 0; i < nodes.size(); i++) {
    if (nodes[i].name == nodeName) {
      return true;
    }
  }
  return false;
}

void createNodes(string inputFile) {
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

    if (!checkContain(vert1)) {
      Node node;
      node.name = vert1;
      node.directIntersection = findDirectIntersection(vert1);
      node.x = 0;
      node.y = 0;
      nodes.push_back(node);
    }

    if (!checkContain(vert2)) {
      Node node;
      node.name = vert2;
      node.directIntersection = findDirectIntersection(vert2);
      node.x = 0;
      node.y = 0;
      nodes.push_back(node);
    }

    Pair pair;
    pair.first = vert1;
    pair.second = vert2;
    edges[pair] = weight;
  }
}

void caculateCoordinates(string crossingFile) {
  ifstream file;
  file.open(crossingFile);
  string line;
  getline(file, line);
  string junction = "\0";

  while (getline(file, line)) {
    stringstream ss(line);
    string junction;
    string no;
    string tmp;
    double xMin;
    double yMin;
    double xMax;
    double yMax;

    // get junction name, no, xMin, yMin, xMax, yMax in line
    getline(ss, junction, ' ');
    getline(ss, no, ' ');
    getline(ss, tmp, ' ');
    xMin = stod(tmp);
    getline(ss, tmp, ' ');
    yMin = stod(tmp);
    getline(ss, tmp, ' ');
    xMax = stod(tmp);
    getline(ss, tmp, ' ');
    yMax = stod(tmp);

    // find node with name junction
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes[i].name == junction) {
        if (no == "c0") {
          nodes[i].x = (xMin + xMax) / 2;
          nodes[i].y = (yMin + yMax) / 2;
        } else if (no == "c1") {
          nodes[i].x = (nodes[i].x + (xMax + xMin) / 2) / 2;
          nodes[i].y = (nodes[i].y + (yMax + yMin) / 2) / 2;
        } else if (no == "c2") {
          nodes[i].x = (nodes[i].x * 2 + (xMax + xMin) / 2) / 3;
          nodes[i].y = (nodes[i].y * 2 + (yMax + yMin) / 2) / 3;
        } else if (no == "c3") {
          nodes[i].x = (nodes[i].x * 3 + (xMax + xMin) / 2) / 4;
          nodes[i].y = (nodes[i].y * 3 + (yMax + yMin) / 2) / 4;
        }
      }
    }
  }

  // calculate coordinates of edge use nearEdge, coordinates of edge is
  // coordinate of node with name nearEdge
  for (int i = 0; i < nodes.size(); i++) {
    if (nodes[i].x == 0 && nodes[i].y == 0) {
      for (int j = 0; j < nodes.size(); j++) {
        if (nodes[j].name == nodes[i].directIntersection) {
          nodes[i].x = nodes[j].x;
          nodes[i].y = nodes[j].y;
        }
      }
    }
  }
}

int main(int argc, char const* argv[]) {
  makePaths("./exitBufferEdges.txt");

  createNodes("input.txt");

  caculateCoordinates("crossing.txt");

  // print nodes to file map.txt
  ofstream file;
  file.open("map.txt");
  for (int i = 0; i < nodes.size(); i++) {
    file << nodes[i].name << " " << nodes[i].x << " " << nodes[i].y << endl;
  }
  file.close();

  return 0;
}
