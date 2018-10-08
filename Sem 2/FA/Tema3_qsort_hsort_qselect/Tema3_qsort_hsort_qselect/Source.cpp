#include<iostream>
#include<fstream>
#include "Profiler.h"
using namespace std;

Profiler profiler("heaps");

int n, x[10002], y[10002], cqs, chs, hsize, poz;

void heapify(int poz)
{

	int bst, aux;
	chs += 2;
	if (2 * poz + 1 <= hsize && x[2 * poz + 1] > x[poz])
		bst = 2 * poz + 1;
	else bst = poz;
	if (2 * poz + 2 <= hsize && x[2 * poz + 2] > x[bst])
		bst = 2 * poz + 2;
	if (bst != poz)
	{
		aux = x[poz];
		x[poz] = x[bst];
		x[bst] = aux;
		chs += 3;
		heapify(bst);
	}
}

void build_heap()
{
	for (int i = (n / 2) - 1; i >= 0; i--)
		heapify(i);
}

void hsort()
{
	hsize = n;
	build_heap();
	for (int i = n; i > 0; i--)
	{
		int aux;
		aux = x[i];
		x[i] = x[0];
		x[0] = aux;
		chs += 3;
		hsize--;
		heapify(0);
	}
}

void qsort(int mod, int st, int dr)
{
	if (st < dr)
	{
		//partitioning
		int pivin, piv, i, aux;
		if (mod == 0)
		{
			pivin = st + rand() % (dr - st + 1);
			piv = x[pivin];
			aux = x[pivin];
			x[pivin] = x[dr];
			x[dr] = aux;
			cqs += 3;
		}
		if (mod == 1)
		{
			/*pivin = st + rand()%(dr - st +1);
			piv = x[pivin];
			aux = x[pivin];
			x[pivin] = x[dr];
			x[dr] = aux;
			cqs += 3;*/
			pivin = dr;
			piv = x[pivin];
		}
		if (mod == 2)
		{
			pivin = (st + dr) / 2;
			piv = x[pivin];
			aux = x[pivin];
			x[pivin] = x[dr];
			x[dr] = aux;
			cqs += 3;
		}
		i = st - 1;
		for (int j = st; j < dr; j++)
		{
			if (x[j] <= piv)
			{
				i++;
				aux = x[i];
				x[i] = x[j];
				x[j] = aux;
				cqs += 3;
			}
		}
		i++;
		aux = x[i];
		x[i] = x[dr];
		x[dr] = aux;
		cqs += 3;
		//end partitioning
		qsort(mod, st, i - 1);
		qsort(mod, i + 1, dr);
	}
}

int randomized_Select(int st, int dr, int el)
{
	if (st == dr)
		return x[st];

	int pivin, piv, i, aux,k;
	pivin = st + rand() % (dr - st + 1);
	piv = x[pivin];
	aux = x[pivin];
	x[pivin] = x[dr];
	x[dr] = aux;
	cqs += 3;
	i = st - 1;
	for (int j = st; j < dr; j++)
	{
		if (x[j] <= piv)
		{
			i++;
			aux = x[i];
			x[i] = x[j];
			x[j] = aux;
			cqs += 3;
		}
	}
	i++;
	aux = x[i];
	x[i] = x[dr];
	x[dr] = aux;
	cqs += 3;
	k = i - st + 1;
	if (el == k)
		return x[i];
	else if (el < k)
		return randomized_Select(st, dr - 1, el);
	return randomized_Select(st + 1, dr, el - k);
}

int main()
{
	///////////////////demo
	n = 10;
	FillRandomArray(y, 11);
	for (int k = 0; k <= n; k++)
		x[k] = y[k];

	cout << "initial:";
	for (int k = 0; k <= n; k++)
		cout << x[k] << ' ';
	cout << '\n';
	qsort(0, 0, n);

	cout << "qsort  :";
	for (int k = 0; k <= n; k++)
		cout << x[k] << ' ';
	cout << '\n';

	FillRandomArray(y, 11);
	for (int k = 0; k <= n; k++)
		x[k] = y[k];
	cout << "initial:";
	for (int k = 0; k <= n; k++)
		cout << x[k] << ' ';
	cout << '\n';
	cout << randomized_Select(0, n-1, 5);
	hsort();
	cout << "hsort  :";
	for (int k = 0; k <= n; k++)
		cout << x[k] << ' ';
	///////////////////demo

	for (n = 100; n <= 10000; n += 100)
	{
		////////////////////////////////average
		cqs = chs = 0;
		for (int j = 1; j <= 5; j++)
		{
			FillRandomArray(y, n + 1);
			for (int k = 0; k <= n; k++)
				x[k] = y[k];
			qsort(0, 0, n);
			if (!IsSorted(x, n)) cout << "q";

			for (int k = 0; k <= n; k++)
				x[k] = y[k];
			hsort();
			if (!IsSorted(x, n)) cout << "h";

		}
		profiler.countOperation("random_quick_sort", n, cqs);
		profiler.countOperation("random_heap_sort", n, chs);
		profiler.createGroup("RANDOM", "random_quick_sort", "random_heap_sort");
		////////////////////////////////worst
		cqs = chs = 0;
		for (int k = 0; k <= n + 1; k++)
			x[k] = k;
		qsort(1, 0, n);
		if (!IsSorted(x, n)) cout << "w";
		for (int k = 0; k <= n + 1; k++)
			x[k] = k;
		hsort();
		if (!IsSorted(x, n)) cout << "w";


		profiler.countOperation("worst_quick_sort", n, cqs);
		profiler.countOperation("worst_heap_sort", n, chs);
		profiler.createGroup("WORST", "worst_quick_sort", "worst_heap_sort");
		////////////////////////////////best
		cqs = chs = 0;
		for (int k = 0; k <= n + 1; k++)
			x[k] = n - k;
		qsort(2, 0, n);
		if (!IsSorted(x, n)) cout << "b";

		for (int k = 0; k <= n + 1; k++)
			x[k] = n - k;
		hsort();
		if (!IsSorted(x, n)) cout << "b";

		profiler.countOperation("best_quick_sort", n, cqs);
		profiler.countOperation("best_heap_sort", n, chs);
		profiler.createGroup("BEST", "best_quick_sort", "best_heap_sort");
	}
	profiler.showReport();
	return 0;
}