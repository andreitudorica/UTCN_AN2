#include <fstream>
#include<iostream>
#include<queue>
#include<ctime>
#include<algorithm>
#include "Profiler.h"
using namespace std;
Profiler profiler("Kruskal");
ifstream f("Text.txt");
int type, P[10001], Rank[10001];
queue <pair <int, int> > sol;
pair <int, pair <int, int> > edge[40003];
int x, y, cost, n, m, mst_e, mst_ni, mst_w, tfind, tmake, tunion, opCount;

int FIND_SET(int v)
{
	opCount++;
	if (v != P[v])
	{
		opCount++;
		P[v] = FIND_SET(P[v]);
	}
	return P[v];
}

void MAKE_SET()
{
	for (int i = 1; i <= n; i++)
		Rank[i] = 1, P[i] = i;
	opCount += n;
}

void UNION(int x, int y)
{
	int p1 = FIND_SET(x);
	int p2 = FIND_SET(y);
	if (Rank[p1] > Rank[p2])
		P[p1] = p2;
	else
		P[p2] = p1;
	if (Rank[p1] == Rank[p2])
		Rank[p1]++;
	opCount += 3;
}

int main()
{
	srand(time(NULL));
	//demo
	f >> n >> m;
	MAKE_SET();
	for (int i = 0; i < m; ++i)
	{
		f >> x >> y >> cost;
		edge[i].first = cost;
		edge[i].second.first = x;
		edge[i].second.second = y;
	}
	sort(edge, edge + m);
	while ((mst_e < n - 1) || (mst_ni < m))
	{
		cost = edge[mst_ni].first;
		x = edge[mst_ni].second.first;
		y = edge[mst_ni].second.second;
		if (FIND_SET(x) != FIND_SET(y))
		{
			UNION(x, y);
			mst_w += cost;
			mst_e++;
			sol.push({ x, y });
		}
		mst_ni++;
	}
	cout << mst_w << '\n';
	cout << n - 1 << '\n';
	while (!sol.empty())
		cout << sol.front().second << " " << sol.front().first << '\n', sol.pop();
	//end demo

	for (n = 100; n <= 10000; n += 100)
	{
		cout << n << '\n';
		opCount = 0;
		m = 4 * n;
		MAKE_SET();
		for (int i = 1; i <= n - 1; i++)
		{
			cost = rand() % 1000;
			edge[i].first = cost;
			edge[i].second.first = 0;
			edge[i].second.second = i;
		}
		for (int i = n; i <= m; i++)
		{
			x = rand() % n;
			y = rand() % n;
			cost = rand() % 1000;
			edge[i].first = cost;
			edge[i].second.first = x;
			edge[i].second.second = y;
		}
		mst_e = mst_ni = mst_w = 0;

		sort(edge, edge + m);
		while ((mst_e < n - 1) && (mst_ni < m))
		{
			cost = edge[mst_ni].first;
			x = edge[mst_ni].second.first;
			y = edge[mst_ni].second.second;
			if (FIND_SET(x) != FIND_SET(y))
			{
				UNION(x, y);
				mst_w += cost;
				mst_e++;
			}
			mst_ni++;
		}
		profiler.countOperation("Total_operations_on_disjoint_sets",n, opCount);
	}
	profiler.createGroup("Order_Statistics", "Total_operations_on_disjoint_sets");
	profiler.showReport();
	return 0;
}
