#include<iostream>
#include<fstream>
#include "Profiler.h"
using namespace std;
Profiler profiler("Order_Statistics");

typedef struct node
{
	int value, size;
	node *left, *right;
};
node *root, *rootDEMO;
int x[10001], n, ccb, cab, ccs, cas, ccd, cad;

node* newNode(int val)
{
	node* nd = new node;
	nd->value = val;
	nd->left = nd->right = NULL;
	return nd;
}

node* BuildBST(int start, int end)
{
	if (start > end)
		return NULL;
	int mid = (start + end) / 2;
	node* ROOT = newNode(x[mid]);
	cab++;
	ROOT->left = BuildBST(start, mid - 1);
	ROOT->right = BuildBST(mid + 1, end);

	if (ROOT->left == NULL && ROOT->right == NULL)
		cab++, ccb++, ROOT->size = 1;
	else if (ROOT->left != NULL && ROOT->right == NULL)
		cab++, ccb += 2, ROOT->size = ROOT->left->size + 1;
	else if (ROOT->left == NULL && ROOT->right != NULL)
		cab++, ccb += 3, ROOT->size = ROOT->right->size + 1;
	else
		cab++, ccb += 4, ROOT->size = ROOT->left->size + ROOT->right->size + 1;
	return ROOT;
}

void preOrder(node* nd, int lvl)
{
	if (nd == NULL)
		return;
	cout << nd->value << ' ' << lvl << " SIZE:" << nd->size << '\n';
	preOrder(nd->left, lvl + 1);
	preOrder(nd->right, lvl + 1);
}

int OS_SELECT(node *nd, int k)
{
	int count = k;
	ccs++;
	while (nd != NULL)
	{
		int sizeOfLeft = 0;
		if (nd->left != NULL)
			ccs++, sizeOfLeft = nd->left->size;
		if (sizeOfLeft + 1 == count)
		{
			ccs++;
			return nd->value;
		}
		else if (sizeOfLeft < count)
		{
			ccs += 2;
			cas++;
			nd = nd->right;
			count -= sizeOfLeft + 1;
		}
		else
			ccs += 3, cas++, nd = nd->left;
	}
	return -1;
}

node* OS_DELETE(node *root, int data)
{
	if (root != NULL)
		root->size--, ccd++, cad++;
	if (root == NULL)
		return root;
	else if (data < root->value) cad++, ccd += 2, root->left = OS_DELETE(root->left, data);
	else if (data > root->value) cad++, ccd += 3, root->right = OS_DELETE(root->right, data);
	else
	{
		if (root->left == NULL && root->right == NULL)//no child 
		{
			ccd += 5;
			root = NULL;
			cad++;
		}
		else if (root->left == NULL) //one child (right)
		{
			ccd += 6;
			node *temp = root;
			root = root->right;
			cad++;
			delete temp;
			temp = NULL;
		}
		else if (root->right == NULL) //one child (left) 
		{
			ccd += 7;
			node *temp = root;
			root = root->left;
			cad++;
			delete temp;
			temp = NULL;
		}
		else //2 children
		{
			ccd += 8;
			node *temp = root->right;
			while (temp->left != NULL)
				temp = temp->left;//get smallest child
			root->value = temp->value;
			cad += 2;
			root->right = OS_DELETE(root->right, temp->value);
		}
	}
	return root;
}

int main()
{
#pragma region demo
	n = 11;
	for (int i = 1; i <= n; i++)
		x[i] = i;

	rootDEMO = BuildBST(1, n);
	preOrder(rootDEMO, 0);
	cout << OS_SELECT(rootDEMO, 5) << '\n';
	OS_DELETE(rootDEMO, 9);
	OS_DELETE(rootDEMO, 7);
	preOrder(rootDEMO, 0);
#pragma endregion

	for (n = 100; n <= 10000; n += 100)
	{
		cout << n << '\n';
		ccb = cab = ccs = cas = ccd = cad = 0;
		for (int ii = 1; ii <= 5; ii++)
		{
			srand(time(NULL));
			for (int i = 1; i <= n; i++)
				x[i] = i;
			root = BuildBST(1, n);
			for (int i = 1; i <= n; i++)
			{
				int k, kk, j;
				k = rand() % (n - i + 1) + 1;
				kk = OS_SELECT(root, k);
				OS_DELETE(root, kk);
			}
		}

		profiler.countOperation("build", n, ccb / 5 + cab / 5);
		profiler.countOperation("select", n, ccs / 5 + cas / 5);
		profiler.countOperation("delete", n, cad / 5 + ccd / 5);
	}
	profiler.createGroup("Order_Statistics", "build", "select", "delete");
	profiler.showReport();
	return 0;
}