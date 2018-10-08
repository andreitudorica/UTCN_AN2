#include<fstream>
#include<iostream>
#include<vector>
#include<stack>
using namespace std;
ifstream f("Text.txt");
stack <int> S,topo;
int N, M, nrc,i,ind, a, b,viz[200001],node,cycle=0;
vector <int>  con, index, lowlink, A[200001];
vector < vector <int> > C;

void DFS(int v)
{
	vector <int>::iterator it;
	int w;
	viz[v] = 1;
	cout << v << '\n';
	for (it = A[v].begin(); it != A[v].end(); it++)
	{
		w = *it;
		if (!viz[w])
			DFS(w);
		else
			cycle = 1;
	}
	S.push(v);
	topo.push(v);
}

int min(int a, int b)
{
	if (a < b)
		return a;
	return b;
}

void tarjan(const int n)
{
	index[n] = lowlink[n] = ind;
	ind++;
	S.push(n);
	viz[n] = 1;
	for (vector <int>::const_iterator it = A[n].begin(); it != A[n].end(); it++)
	{
		if (index[*it] == -1)
		{
			tarjan(*it);
			lowlink[n] = min(lowlink[n], lowlink[*it]);
		}
		else if (viz[*it])
			lowlink[n] = min(lowlink[n], lowlink[*it]);
	}
	if (index[n] == lowlink[n])
	{
		con.clear();
		do{
			con.push_back(node = S.top());
			S.pop();
			viz[node] = 0;
		} while (node != n);
		C.push_back(con);
	}
}

int main()
{
	f >> N >> M;
	for (i = 1; i <= M; i++)
	{
		f >> a >> b;
		A[a].push_back(b);
	}
	for (i = 0; i < N; i++)
		if (!viz[i])
			DFS(i);
			
	index.resize(N);
	lowlink.resize(N);
	con.resize(N);
	index.assign(N,-1);
	for (i = 0; i <= N; i++)
		viz[i] = 0;
	if (!cycle)
	{
		cout << "topological sort: ";
		while (!topo.empty())
		{
			cout << topo.top() << ' ';
			topo.pop();
		}
	}
	else
		cout << "cycle detected";
	cout << endl << "componentele tare conexe: ";
	for (int i = 0; i < N; i++) 
		if (index[i] == -1)
			tarjan(i);

	cout << C.size() << "\n";
	for (size_t i = 0; i < C.size(); i++) 
	{
		for (size_t j = 0; j < C[i].size(); j++)
			cout << C[i][j] << " ";
		cout << "\n";
	}
	

	f.close();
	return 0;
}