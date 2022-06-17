#include <string.h>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;
struct Node {
  double time;
  double x;
  double y;
  string laneId;
  double lanePosition;
  double speed;
  double acceleration;
  double totalDistance;
  int carId;
};

// convert E2_1 to E2
string convertLaneId(string laneId) {
  string newLaneId = "";
  for (int i = 0; i < laneId.length(); i++) {
    if (laneId[i] == '_') {
      break;
    }
    newLaneId += laneId[i];
  }
  return newLaneId;
}

int main(int argc, char const* argv[]) {
  // read file carlog.txt into nodes
  ifstream file("carlog.txt");
  string line;
  vector<Node*> nodes;
  while (getline(file, line)) {
    Node* node = new Node();
    stringstream ss(line);
    ss >> node->time >> node->x >> node->y >> node->laneId >>
        node->lanePosition >> node->speed >> node->acceleration >>
        node->totalDistance >> node->carId;
    nodes.push_back(node);
  }
  file.close();

  double count = 0;
  for (int i = 1; i < nodes.size(); i++) {
    if ((nodes[i]->laneId == nodes[i - 1]->laneId) &&
        (nodes[i]->lanePosition == nodes[i - 1]->lanePosition)) {
      count += nodes[i]->time - nodes[i - 1]->time;
    } else if (count != 0) {
      cout << convertLaneId(nodes[i - 1]->laneId) << " " << count << endl;
      count = 0;
    }
  }

  if (count != 0) {
    cout << convertLaneId(nodes[nodes.size() - 1]->laneId) << " " << count
         << endl;
  }

  return 0;
}
