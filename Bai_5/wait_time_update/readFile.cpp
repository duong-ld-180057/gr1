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

vector<string> getListIntersection(string fileName) {
  ifstream file(fileName);
  string line;
  vector<string> intersectionList;
  map<string, int> junctionMap;

  while (getline(file, line)) {
    // get first word of line
    stringstream ss(line);
    string word;
    getline(ss, word, ' ');
    string laneId = convertLaneId(word);
    if (junctionMap.find(laneId) == junctionMap.end()) {
      junctionMap[laneId] = 1;
    } else {
      junctionMap[laneId]++;
    }
  }

  for (auto it = junctionMap.begin(); it != junctionMap.end(); it++) {
    if (it->second > 2) {
      intersectionList.push_back(it->first);
    }
  }
  return intersectionList;
}

map<string, vector<string> > makeDirectEgde(string fileName) {
  vector<string> intersectionList = getListIntersection(fileName);
  map<string, vector<string> > directEdgeMap;

  ifstream file(fileName);
  string line;
  while (getline(file, line)) {
    // get first word of line
    stringstream ss(line);
    string word;
    getline(ss, word, ' ');
    string directEdge = convertLaneId(word);

    // get third word of line
    getline(ss, word, ' ');
    getline(ss, word, ' ');
    string laneId = convertLaneId(word);

    if (directEdge == laneId) {
      continue;
    }

    if (directEdgeMap.find(laneId) == directEdgeMap.end()) {
      vector<string> listDirectEdge;
      listDirectEdge.push_back(directEdge);
      directEdgeMap[laneId] = listDirectEdge;
    } else {
      directEdgeMap[laneId].push_back(directEdge);
    }
  }
  return directEdgeMap;
}

string normalizeLaneId(string laneId) {
  string newLaneId = "";
  // if laneId is -E2_1, then newLaneId is E2_1
  if (laneId[0] == '-') {
    newLaneId = laneId.substr(1);
  } else {
    newLaneId = laneId;
  }
  return newLaneId;
}

// check if laneId or normalizeLaneId is not in directEdge, return
string getDirectLane(vector<string> directEdge,
                     vector<string> listPosibleLane) {
  for (int i = 0; i < listPosibleLane.size(); i++) {
    int check = 0;
    for (int j = 0; j < directEdge.size(); j++) {
      string normalDirectEdge = normalizeLaneId(directEdge[j]);
      string normalLaneId = normalizeLaneId(listPosibleLane[i]);
      if (normalDirectEdge == normalLaneId) {
        check = 1;
        break;
      }
    }

    if (check == 0) {
      return listPosibleLane[i];
    }
  }
}

map<string, vector<vector<string> > > getIntersectionDirectLane(
    string filename) {
  map<string, vector<string> > directEdgeMap = makeDirectEgde(filename);
  vector<string> intersectionList = getListIntersection(filename);
  map<string, vector<vector<string> > > intersectionDirectLaneMap =
      map<string, vector<vector<string> > >();

  for (int i = 0; i < intersectionList.size(); i++) {
    string intersectionId = intersectionList[i];
    vector<string> listDirectEdge = directEdgeMap[intersectionId];
    vector<vector<string> > listPossibleLane = vector<vector<string> >();

    for (auto it = listDirectEdge.begin(); it != listDirectEdge.end(); it++) {
      vector<string> directEdge;
      string laneId = *it;

      while (find(intersectionList.begin(), intersectionList.end(), laneId) ==
             intersectionList.end()) {
        directEdge.push_back(laneId);

        if (directEdgeMap[laneId].empty()) {
          break;
        }
        laneId = getDirectLane(directEdge, directEdgeMap[laneId]);
      }
      listPossibleLane.push_back(directEdge);
    }
    intersectionDirectLaneMap[intersectionId] = listPossibleLane;
  }

  return intersectionDirectLaneMap;
}

string getDirectIntersection(
    string laneId,
    map<string, vector<vector<string> > > intersectionDirectLaneMap) {
  for (auto it = intersectionDirectLaneMap.begin();
       it != intersectionDirectLaneMap.end(); it++) {
    vector<vector<string> > listPossibleLane = it->second;
    for (int i = 0; i < listPossibleLane.size(); i++) {
      vector<string> directEdge = listPossibleLane[i];
      if (find(directEdge.begin(), directEdge.end(), laneId) !=
          directEdge.end()) {
        return it->first;
      }
    }
  }
}

void findWaitPoint(string carlogFile, string mapFile) {
  ifstream file(carlogFile);
  string line;
  vector<Node*> nodes;
  double count = 0;
  map<string, vector<vector<string> > > intersectionDirectLaneMap =
      getIntersectionDirectLane(mapFile);
  vector<string> intersectionList = getListIntersection(mapFile);

  while (getline(file, line)) {
    Node* node = new Node();
    stringstream ss(line);
    ss >> node->time >> node->x >> node->y >> node->laneId >>
        node->lanePosition >> node->speed >> node->acceleration >>
        node->totalDistance >> node->carId;
    nodes.push_back(node);
  }
  file.close();

  for (int i = 1; i < nodes.size(); i++) {
    if ((nodes[i]->laneId == nodes[i - 1]->laneId) &&
        (nodes[i]->lanePosition == nodes[i - 1]->lanePosition)) {
      count += nodes[i]->time - nodes[i - 1]->time;
    } else if (count != 0) {
      string convertLane = convertLaneId(nodes[i]->laneId);

      if (find(intersectionList.begin(), intersectionList.end(), convertLane) !=
          intersectionList.end()) {
        cout << convertLane << " " << count << endl;
      } else {
        cout << "Before entering "
             << getDirectIntersection(convertLane, intersectionDirectLaneMap)
             << " " << count << endl;
      }
      count = 0;
    }
  }

  if (count != 0) {
    cout << convertLaneId(nodes[nodes.size() - 1]->laneId) << " " << count
         << endl;
  }
}

int main(int argc, char const* argv[]) {
  findWaitPoint("carlog.txt", "input.txt");
  return 0;
}
