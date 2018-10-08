#include<iostream>
#include<fstream>
#include "Profiler.h"
using namespace std;
Profiler profiler("Hash_Tables");
#define MOD 10007
int n, m, N, cnt, search_count, max_search_count_found, max_search_count_not_found, x[10007], to_search[3001], avrg_found, avrg_not_found, avrg_found_count, avrg_not_found_count;
vector<int> h[MOD];
int H[10008];

int quad(int a, int i)
{
	return (a%MOD + 2 * i*i + 3 * i) % MOD;
}

int find_value_quad(int a)
{
	int i = 0;
	search_count++;
	while (quad(a, i) < N && H[quad(a, i)] != a && H[quad(a, i)] && i < N)
	{
		search_count++;
		i++;
	}
	if (H[quad(a, i)] == a) return 1;
	return 0;
}

int insert_value_quad(int a)
{
	int i = 0;
	while (quad(a, i) < N && H[quad(a, i)] && i < N)
		i++;
	if (quad(a, i) < N)
	{
		H[quad(a, i)] = a;
		return 1;
	}
	return 0;
}


vector<int>::iterator find_value(int x)
{
	int list = x % MOD;
	for (vector<int>::iterator it = h[list].begin(); it != h[list].end(); ++it)
	{
		search_count++;
		if (*it == x)
			return it;
	}
	return h[list].end();
}

void insert_value(int x)
{
	int list = x % MOD;
	if (find_value(x) == h[list].end())
		h[list].push_back(x);
}

void erase_value(int x)
{
	int list = x % MOD;
	vector<int>::iterator it = find_value(x);

	if (it != h[list].end())
		h[list].erase(it);
}

void solve_quadratic(double alpha)
{
	n = N*alpha;
	cout << n << '\n';
	for (int i = 0; i < N; i++)
		H[i] = 0;

	FillRandomArray(x, n, 0, 200000, true, 0);//add random elements to the hash table
	for (int i = 0; i < n; i++)
		if (!insert_value_quad(x[i]))
			cout << "coud not add " << x[i] << '\n';
	FillRandomArray(to_search, m, 0, 600000, true, 0);
	avrg_found = avrg_found_count = avrg_not_found = avrg_not_found_count = max_search_count_found = max_search_count_not_found = 0;
	for (int i = 0; i < m; i++)
	{
		search_count = 0;
		if (!find_value_quad(to_search[i]))
		{
			if (max_search_count_not_found < search_count)
				max_search_count_not_found = search_count;
			avrg_not_found += search_count;
			avrg_not_found_count++;
		}
		else
		{
			if (max_search_count_found < search_count)
				max_search_count_found = search_count;
			avrg_found += search_count;
			avrg_found_count++;
		}
		//cout << search_count << ' ';
	}
	if (avrg_not_found_count)
		avrg_not_found /= avrg_not_found_count;
	if (avrg_found_count)
		avrg_found /= avrg_found_count;

	profiler.countOperation("avrg_found", alpha * 100, avrg_found);
	profiler.countOperation("avrg_not_found", alpha * 100, avrg_not_found);
	profiler.countOperation("max_found", alpha * 100, max_search_count_found);
	profiler.countOperation("max_not_found", alpha * 100, max_search_count_not_found);
}
void solve(double alpha)
{
	n = N*alpha;
	cout << n << '\n';
	for (int i = 0; i < n; i++)
		h[i].clear();

	FillRandomArray(x, n, 0, 1000000, true, 0);//add random elements to the hash table
	for (int i = 0; i < n; i++)
		insert_value(x[i]);
	FillRandomArray(to_search, m, 0, n * 2, true, 0);
	avrg_found = avrg_found_count = avrg_not_found = avrg_not_found_count = max_search_count_found = max_search_count_not_found = 0;
	for (int i = 0; i < m; i++)
	{
		search_count = 0;
		if (find_value(to_search[i]) == h[to_search[i] % MOD].end())
		{
			if (max_search_count_not_found < search_count)
				max_search_count_not_found = search_count;
			avrg_not_found += search_count;
			avrg_not_found_count++;
		}
		else
		{
			if (max_search_count_found < search_count)
				max_search_count_found = search_count;
			avrg_found += search_count;
			avrg_found_count++;
		}
		cout << search_count << ' ';
	}
	if (avrg_not_found_count)
		avrg_not_found /= avrg_not_found_count;
	if (avrg_found_count)
		avrg_found /= avrg_found_count;

	profiler.countOperation("avrg_found", alpha * 100, avrg_found);
	profiler.countOperation("avrg_not_found", alpha * 100, avrg_not_found);
	profiler.countOperation("max_found", alpha * 100, max_search_count_found);
	profiler.countOperation("max_not_found", alpha * 100, max_search_count_not_found);
}

int main()
{
	m = 3000;
	N = 10007;
#pragma region demo
	if (!insert_value_quad(2))
		cout << "coud not add " << 2 << '\n';
	if (!insert_value_quad(10009))
		cout << "coud not add " << 10009 << '\n';
	if (!insert_value_quad(20016))
		cout << "coud not add " << 20016 << '\n';
	if (!insert_value_quad(3))
		cout << "coud not add " << 3 << '\n';
	if (!insert_value_quad(5))
		cout << "coud not add " << 5 << '\n';
	for (int i = 0; i < 10; i++)
		cout << H[i] << ' ';
	cout << '\n';

#pragma endregion
	solve_quadratic(0.8);
	solve_quadratic(0.85);
	solve_quadratic(0.9);
	solve_quadratic(0.95);
	solve_quadratic(0.99);
	profiler.createGroup("andrei","avrg_found", "max_found", "avrg_not_found", "max_not_found");
	profiler.showReport();
	return 0;
}