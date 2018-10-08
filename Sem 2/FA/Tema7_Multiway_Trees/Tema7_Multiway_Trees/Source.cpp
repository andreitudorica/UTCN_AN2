#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("Text.txt");

int r1[10001], n;
typedef struct nodeR2
{
	int value;
	vector<nodeR2*> children;
};
nodeR2 *rootR2, *above;

typedef struct nodeR3
{
	int key;
	nodeR3 *left, *right, *parent;
};
nodeR3 *rootR3;

void R1toR2(nodeR2 *root)
{
	nodeR2 *node;
	for (int i = 0; i < n; i++)
		if (r1[i] == root->value)
		{
			node = new nodeR2;
			node->value = i;
			root->children.push_back(node);
			R1toR2(node);
		}
}

nodeR3* R2toR3(nodeR2 *root, nodeR2 *parent, int child_index)
{
	nodeR3 *p = new nodeR3;
	p->key = root->value;
	if (root->children.size())
		p->left = R2toR3(root->children.at(0), root, 0);
	else
		p->left = NULL;
	if (parent && child_index < parent->children.size() - 1)
		p->right = R2toR3(parent->children.at(child_index + 1), parent, child_index + 1);
	else
		p->right = NULL;
	return p;
}

void prettyPrint(nodeR3 *x, int d)
{
	if (x != NULL)
	{
		for (int i = 0; i < d; i++)
			printf("     ");
		cout << x->key << endl;
		prettyPrint(x->left, d + 1);
		prettyPrint(x->right, d);
	}
}

int main()
{
	f >> n;
	for (int i = 0; i < n; i++)
		f >> r1[i];
	above = new nodeR2;
	above->value = -1;
	R1toR2(above);
	rootR2 = above->children.at(0);
	rootR3 = R2toR3(rootR2, NULL, 0);
	prettyPrint(rootR3, 0);
	return 0;
}