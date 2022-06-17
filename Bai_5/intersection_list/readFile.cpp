#include <string.h>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

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

int main(int argc, char const* argv[]) {
  vector<string> intersectionList = getListIntersection("input.txt");
  for (int i = 0; i < intersectionList.size(); i++) {
    cout << intersectionList[i] << endl;
  }

  return 0;
}
