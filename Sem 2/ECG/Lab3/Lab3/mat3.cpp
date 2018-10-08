#include<iostream>
#include "vec3.h"
#include "mat3.h"

namespace egc {
	mat3& mat3::operator =(const mat3& srcMatrix)
	{
		for (int i = 0; i < 9; i++)
			this->matrixData[i] = srcMatrix.matrixData[i];
		return *this;
	}
	mat3 mat3::operator *(float scalarValue) const
	{
		mat3 m;
		m = *this;
		for (int i = 0; i < 9; i++)
			m.matrixData[i] *= scalarValue;
		return m;
	}

	mat3 mat3::operator *(const mat3& srcMatrix) const
	{
		mat3 rez;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
			{
				rez.at(i, j) = 0;
				for (int k = 0; k < 3; k++)
					rez.at(i, j) += (this->at(i, k)*srcMatrix.at(k, i));
			}
		return rez;
	}
	vec3 mat3::operator *(const vec3& srcVector) const
	{
		vec3 v;

		v.x = this->at(0, 0)*srcVector.x + this->at(0, 1)*srcVector.y + this->at(0, 2)*srcVector.z;
		v.y = this->at(1, 0)*srcVector.x + this->at(1, 1)*srcVector.y + this->at(1, 2)*srcVector.z;
		v.z = this->at(2, 0)*srcVector.x + this->at(2, 1)*srcVector.y + this->at(2, 2)*srcVector.z;
		return v;
	}
	mat3 mat3::operator +(const mat3& srcMatrix) const
	{
		mat3 rez;
		for (int i = 0; i < 9; i++)
			rez.matrixData[i] = this->matrixData[i] + srcMatrix.matrixData[i];
		return rez;
	}
	//get element by (row, column)
	float& mat3::at(int i, int j)
	{
		return this->matrixData[j * 3 + i];
	}
	const float& mat3::at(int i, int j) const
	{
		return this->matrixData[j * 3 + i];
	}
	float mat3::determinant() const
	{
		float det = 0;
		det = this->at(0, 0)*this->at(1, 1)*this->at(2, 2) +
			this->at(1, 0)*this->at(2, 1)*this->at(0, 2) +
			this->at(0, 1)*this->at(1, 2)*this->at(2, 0) -
			this->at(0, 2)*this->at(1, 1)*this->at(2, 0) -
			this->at(0, 1)*this->at(1, 0)*this->at(2, 2) -
			this->at(0, 0)*this->at(2, 1)*this->at(1, 2);
		return det;
	}
	/*mat3 mat3::inverse() const
	{

	}*/
	mat3 mat3::transpose() const
	{
		mat3 inv;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				inv.at(i, j) = this->at(j, i);
		return inv;
	}
}