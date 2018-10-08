
/*
1 l D LEFT.i /
2 r D RIGHT.i /
3 if l<= A:heap-size and A[l] > A[i]
4  largest D l
5 else largest D i
6 if r<= A:heap-size and A[r] > A[largest]
7 largest D r
8 if largest != i
9 exchange A[i] with A[largest]
10 MAX-HEAPIFY(A, largest)
*/
#include<iostream>
#include<fstream>
#include "Profiler.h"
using namespace std;

Profiler profiler("heaps");

int n, x[10001], ctd,cbu,heap[10001],hsize,poz;

void heapify(int poz)
{

	int bst, aux;
	cbu += 2;
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
		cbu += 3;
		heapify(bst);
	}
}

void bottom_up()
{
	for (int i = (n / 2) - 1; i >= 0; i--)
		heapify(i);
}

void top_down()
{
	hsize = 0;
	for (int j = 1; j <= n; j++)
	{
		heap[++hsize] = x[j];
		
		poz = hsize;
		ctd += 2;
		while (poz!= 0 && heap[poz] > heap[poz / 2])
		{
			int aux;
			ctd += 3;
			aux = heap[poz];
			heap[poz] = heap[poz / 2];
			heap[poz / 2] = aux;
			poz /= 2;
		}
	}
}

int main()
{
	///////////////////demo
	n = 10;
	for (int j = 1; j <= n; j++)
	{
		x[j] =  j;
		cout << x[j]<<' ';
	}
	cout << '\n'<<"top down: ";
	top_down();
	for (int i = 1; i <= n; i++)
		cout << heap[i] << ' ';
	cout << '\n' << "bottom up: ";
	bottom_up();
	for (int i = 1; i <= n; i++)
		cout << x[i] << ' ';
	///////////////////demo




	for (n = 100; n <= 9000; n += 100)
	{
		////////////////////////////////average
		ctd=cbu = 0;
		for (int j = 1; j <= 5; j++)
		{
			FillRandomArray(x, n+1);
			top_down();
			bottom_up();
		}
		profiler.countOperation("RBottomUp", n, cbu); 
		profiler.countOperation("RTopDown", n, ctd);
		profiler.createGroup("Computation when random", "RBottomUp", "RTopDown");
		////////////////////////////////worst
		ctd = cbu = 0;
		for (int j = 1; j <= n; j++)
			x[j] = j;
			top_down();
			bottom_up();
		profiler.countOperation("WBottomUp", n, cbu);
		profiler.countOperation("WTopDown", n, ctd);
		profiler.createGroup("Computation when worst", "WBottomUp", "WTopDown");

	}
	profiler.showReport();
	return 0;
}