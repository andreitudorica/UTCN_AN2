#include<iostream>
#include<fstream>
#include "Profiler.h"
using namespace std;

Profiler profiler("merge_k_arrays");
struct element {
	int listIndex, value;
	element *nextElement;
};
struct listOfElements {
	element *first, *last;
	int length;
};
listOfElements elements[1001], emptyListOfElements;
int k, hsize, rez[1000001], lrez, cnt, x[10002];

void addElement(int listIndex, int value)
{
	element *e = new element;
	e->listIndex = listIndex;
	e->value = value;
	if (elements[listIndex].length == 0)
		elements[listIndex].first = elements[listIndex].last = e;
	else
	{
		elements[listIndex].last->nextElement = e;
		elements[listIndex].last = e;
	}
	elements[listIndex].length++;
}

void heapify(int poz)
{

	int bst = poz;
	cnt += 2;
	if (2 * poz + 1 <= hsize && elements[2 * poz + 1].first->value < elements[poz].first->value)
		bst = 2 * poz + 1;
	if (2 * poz + 2 <= hsize && elements[2 * poz + 2].first->value < elements[bst].first->value)
		bst = 2 * poz + 2;

	if (bst != poz)
	{
		listOfElements aux;
		cnt += 3;
		aux = elements[poz];
		elements[poz] = elements[bst];
		elements[bst] = aux;
		heapify(bst);
	}
}

void bottom_up()
{
	for (int i = (hsize / 2); i >= 0; i--)
		heapify(i);
}

void solve()
{
	int ec = 0;
	hsize = k - 1;
	bottom_up();
	for (int i = 0; i < k; i++)
		ec += elements[i].length;

	while (ec)
	{
		rez[lrez++] = elements[0].first->value;
		element *e;
		e = elements[0].first;
		elements[0].length--;
		if (elements[0].length)
			elements[0].first = elements[0].first->nextElement;
		else
		{
			listOfElements aux;
			aux = elements[0];
			elements[0] = elements[hsize];
			elements[hsize] = aux;
			hsize--;
		}
		free(e);
		heapify(0);
		ec--;
	}
}

int main()
{
#pragma region demo
	k = 5 + rand() % 5;
	cout << k << '\n';
	for (int i = 0; i < k; i++)
	{
		int n = 2 + rand() % 8;
		FillRandomArray(x, n, 0, 10, false, 1);

		cout << n << " elements in list " << i << " : ";
		for (int j = 0; j < n; j++)
		{
			addElement(i, x[j]);
			cout << x[j] << ' ';
		}
		cout << '\n';
	}
	cout << '\n';
	cout << '\n';
	solve();

	for (int i = 1; i < lrez; i++)
		cout << rez[i] << ' ';
#pragma endregion
	k = 5;
	for (int n = 100; n <= 10000; n += 100)
	{
		for (int i = 0; i < k; i++)
		{
			FillRandomArray(x, n / k, 0, 15000, false, 1);
			for (int j = 0; j < n / k; j++)
				addElement(i, x[j]);
		}
		cnt = 0;
		solve();
		profiler.countOperation("k5", n, cnt);
	}
	////////////////////////////////////////////////////
	k = 10;
	for (int n = 100; n <= 10000; n += 100)
	{
		for (int i = 0; i < k; i++)
		{
			FillRandomArray(x, n / k, 0, 15000, false, 1);
			for (int j = 0; j < n / k; j++)
				addElement(i, x[j]);
		}
		cnt = 0;
		solve();
		profiler.countOperation("k10", n, cnt);
	}
	///////////////////////////////////////////////////////
	k = 100;
	for (int n = 100; n <= 10000; n += 100)
	{
		for (int i = 0; i < k; i++)
		{
			FillRandomArray(x, n / k, 0, 15000, false, 1);
			for (int j = 0; j < n / k; j++)
				addElement(i, x[j]);
		}
		cnt = 0;
		solve();
		profiler.countOperation("k100", n, cnt);
	}
	/////////////////////////////////////////////////////
	int n = 10000;
	for (k = 10; k <= 500; k += 10)
	{
		for (int i = 0; i < k; i++)
		{
			FillRandomArray(x, n / k, 0, 15000, false, 1);
			for (int j = 0; j < n / k; j++)
				addElement(i, x[j]);
		}
		cnt = 0;
		solve();
		profiler.countOperation("n10000", k, cnt);
	}

	profiler.createGroup("graphs", "k5", "k10", "k100");
	profiler.createGroup("fixed n", "n10000");
	profiler.showReport();
	return 0;
}