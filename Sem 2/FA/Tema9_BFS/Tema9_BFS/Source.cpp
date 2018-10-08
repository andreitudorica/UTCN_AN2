#include <fstream>
#include <iostream>
#include <vector>
#include<ctime>
#include<algorithm>
#include "Profiler.h"
using namespace std;
Profiler profiler("BFS");
ifstream f("Text.txt");
int E, V, QueueLength,QL, Start, opCount;
vector <int> Neighbours[1000];
int Queue[1000], Distance[1000], used[10001], printed[10001];

void BFS(int root)
{
	int i, j;
	Distance[root] = 0;
	Queue[++QueueLength] = root;
	QL++;
	used[root] = 1;
	opCount++;

	for (i = 0; i <= QueueLength; i++)
		for (j = 0; j < Neighbours[Queue[i]].size(); j++)
		{
			if (Distance[Neighbours[Queue[i]][j]] == -1)
			{
				used[Neighbours[Queue[i]][j]] = 1;
				Queue[++QueueLength] = Neighbours[Queue[i]][j];
				QL++;
				Distance[Queue[QueueLength]] = Distance[Queue[i]] + 1;
				opCount++;
				
			}
			opCount++;
		}
}

void prettyPrint(int x)
{

	if (!printed[x])
	{
		for (int i = 0; i < Distance[Queue[x]]; i++)
			cout << "	";
		cout << Queue[x] << endl;
		printed[x] = 1;
		for (int i = 0; i < Neighbours[Queue[x]].size(); i++)
		{
			for (int j = 1; j <= QL; j++)
				if (Queue[j] == Neighbours[Queue[x]][i])
					prettyPrint(j);
		}
	}
}

bool findYinX(int x, int y)
{
	for (int i = 0; i < Neighbours[x].size(); i++)
		if (Neighbours[x][i] == y)
			return true;
	return false;
}

int main()
{
	int i, x, y;
	f >> V >> E;
	for (i = 0; i < E; i++)
	{
		f >> x >> y;
		Neighbours[x].push_back(y);
		Neighbours[y].push_back(x);
	}

	for (i = 0; i <= V; i++)
		Distance[i] = -1;
	QueueLength = 0;

	for (i = 0; i < V; i++)
	{
		if (!used[i])
			BFS(i);
	}
	for (i = 1; i <= QueueLength; i++)
	{
		if (!printed[i])
			prettyPrint(i);
	}
	f.close();
	srand(time(NULL));
	opCount = 0;
	V = 100;
	for (E = 1000; E <= 5000; E += 100)
	{

		opCount = 0;
		for (int i = 0; i <= V; i++)
			Neighbours[i].clear();
		for (int i = 0; i <= E; i++)
		{
			x = rand() % V;
			y = rand() % V;
			while (findYinX(x, y))
				x = rand() % V, y = rand() % V;
			Neighbours[x].push_back(y);
		}

		for (i = 0; i <= V; i++)
			Distance[i] = -1, used[i] = 0;
		QueueLength = 0;

		for (i = 0; i < V; i++)
		{
			if (!used[i])
				BFS(i);
		}

		profiler.countOperation("Number of Operations V fixed", E, opCount);
	}
	E = 9000;
	for (V = 100; V <= 200; V += 10)
	{

		opCount = 0;
		for (int i = 0; i <= V; i++)
			Neighbours[i].clear();
		for (int i = 0; i <= E; i++)
		{
			x = rand() % V;
			y = rand() % V;
			while (findYinX(x, y))
				x = rand() % V, y = rand() % V;
			Neighbours[x].push_back(y);
		}

		for (i = 0; i <= V; i++)
			Distance[i] = -1, used[i] = 0;
		QueueLength = 0;

		for (i = 0; i < V; i++)
		{
			if (!used[i])
				BFS(i);
		}

		profiler.countOperation("Number of Operations E fixed", V, opCount);
	}
	profiler.showReport();
	return 0;
}