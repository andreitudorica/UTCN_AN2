/*
	This application computes the number of comparisons and assignations used for 3 sorting algorithms: bubble sort, selection sort and insertion sort.
	the counters are set for 3 scenarios best worst and average cases. For the average cases we generate 5 random arrays to be sorted in order to get a 
	more accurate result (an average).
	On what concernes stability bubble sort and insertion sort are stable, but selection sort is unstable.
	the complexities of the algorithms are as follow:
	bubble sort: O(n^2)
	selection sort: O(n^2)
	insertion srot: O(n^2)
*/

#include<iostream>
#include<fstream>
#include "Profiler.h"
Profiler profiler("demo");

using namespace std;
int n, x[100000], ca, cc;
ofstream g("output.csv");

void BubbleSort()
{
	int p = 1;
	while (p)
	{
		p = 0;
		for (int i = 0; i < n-1; i++)
		{
			cc++;
			if (x[i] > x[i + 1])
			{
				ca += 3;
				int aux;
				aux = x[i];
				x[i] = x[i + 1];
				x[i + 1] = aux;
				p = 1;
			}
		}
	}
}

void InsertSort()
{
	for (int i = 1; i < n; i++)
	{
		int j;
		j = i;
		cc++;
		while (j != 0 && x[j] < x[j - 1])
		{
			cc++;
			ca += 3;
			int aux;
			aux = x[j];
			x[j] = x[j - 1];
			x[j - 1] = aux;
			j--;
		}
	}
}

void SelectionSort()
{
	for (int i = 0; i < n-1; i++)
	{
		int min;
		min = i;
		for (int j = min; j < n; j++)
		{
			cc++;
			if (x[j] < x[min])
				min = j;
		}
		if (min != i)
		{
			ca += 3;
			int aux;
			aux = x[i];
			x[i] = x[min];
			x[min] = aux;
		}
	}
}

int main()
{
	g << "n,comp_Bubble,asign_Bubble,comp_Insertion,asign_Insertion,comp_Selection,asig_Selection\n";

	for (n = 100; n <= 10000; n += 100)
	{
		cout << n << '\n';
		for (int i = 0; i < n; i++)
			x[i] = i;
		BubbleSort();
		profiler.countOperation("BubbleA", n, ca);
		profiler.countOperation("BubbleC", n, cc);
		profiler.countOperation("BubbleT", n, cc + ca);
		ca = cc = 0;
		for (int i = 0; i < n; i++)
			x[i] = i;
		InsertSort();
		profiler.countOperation("InsertSortA", n, ca);
		profiler.countOperation("InsertSortC", n, cc);
		profiler.countOperation("InsertSortT", n, cc + ca);
		ca = cc = 0;
		for (int i = 0; i < n; i++)
			x[i] = i;
		SelectionSort();
		profiler.countOperation("SelectionSortA", n, ca);
		profiler.countOperation("SelectionSortC", n, cc);
		profiler.countOperation("SelectionSortT", n, cc + ca);
		ca = cc = 0;


		profiler.createGroup("Assignments when ordered", "BubbleA", "InsertSortA", "SelectionSortA");
		profiler.createGroup("Comparisons when ordered", "BubbleC", "InsertSortC", "SelectionSortC");
		profiler.createGroup("Total operations when ordered", "BubbleT", "InsertSortT", "SelectionSortT");

		for (int i = n-1; i >= 0; i--)
			x[n - i + 1] = i;
		BubbleSort();
		profiler.countOperation("DBubbleA", n, ca);
		profiler.countOperation("DBubbleC", n, cc);
		profiler.countOperation("DBubbleT", n, cc + ca);
		ca = cc = 0;
		for (int i = n-1; i >= 0; i--)
			x[n - i + 1] = i;
		InsertSort();
		profiler.countOperation("DInsertSortA", n, ca);
		profiler.countOperation("DInsertSortC", n, cc);
		profiler.countOperation("DInsertSortT", n, cc + ca);
		ca = cc = 0;

		for (int i = 0; i <= n / 2; i++)
			x[i] = n - i;
		for (int i = n / 2 + 1; i < n; i++)
			x[i] = i - n / 2;
		SelectionSort();
		profiler.countOperation("DSelectionSortA", n, ca);
		profiler.countOperation("DSelectionSortC", n, cc);
		profiler.countOperation("DSelectionSortT", n, cc + ca);
		ca = cc = 0;


		profiler.createGroup("Assignments when Descending order", "DBubbleA", "DInsertSortA", "DSelectionSortA");
		profiler.createGroup("Comparisons when Descending ordered", "DBubbleC", "DInsertSortC", "DSelectionSortC");
		profiler.createGroup("Total operations when Descending ordered", "DBubbleT", "DInsertSortT", "DSelectionSortT");



		ca = cc = 0;
		for (int j = 1; j <= 5; j++)
		{
			FillRandomArray(x, n);
			BubbleSort();
			if (!IsSorted(x, n))
				cout << "Is not sorted Bubble!!!";
		}
		profiler.countOperation("RBubbleA", n, ca);
		profiler.countOperation("RBubbleC", n, cc);
		profiler.countOperation("RBubbleT", n, cc + ca);
		ca = cc = 0;
		for (int j = 1; j <= 5; j++)
		{
			FillRandomArray(x, n);
			InsertSort();
			if (!IsSorted(x, n))
				cout << "Is not sorted InsertSort!!!";
		}
		profiler.countOperation("RInsertSortA", n, ca);
		profiler.countOperation("RInsertSortC", n, cc);
		profiler.countOperation("RInsertSortT", n, cc + ca);
		ca = cc = 0;
		for (int j = 1; j <= 5; j++)
		{
			FillRandomArray(x, n);
			SelectionSort();
			if (!IsSorted(x, n))
				cout << "Is not sorted SelectionSort!!!";
		}
		profiler.countOperation("RSelectionSortA", n, ca);
		profiler.countOperation("RSelectionSortC", n, cc);
		profiler.countOperation("RSelectionSortT", n, cc + ca);
		ca = cc = 0;


		profiler.createGroup("Assignments when random", "RBubbleA", "RInsertSortA", "RSelectionSortA");
		profiler.createGroup("Assignments when random only selection",  "RSelectionSortA");
		profiler.createGroup("Comparisons when random", "RBubbleC", "RInsertSortC", "RSelectionSortC");
		profiler.createGroup("Total operations when random", "RBubbleT", "RInsertSortT", "RSelectionSortT");



	}
	profiler.showReport();
	return 0;
}