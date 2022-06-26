#ifndef TYPE_H
#define TYPE_H

#include <string>
#include <utility>

using namespace std;

typedef struct Node {
  string name;
  double x;
  double y;
  double f;
  double g;
  double h;
  string parent;
} Node;
typedef pair<string, string> Pair;

#endif  // TYPE_H
