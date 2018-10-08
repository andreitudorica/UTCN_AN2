#include <algorithm>
#include <iostream>
#include "vec3.h"
#include "mat3.h"

namespace egc {

	mat3& mat3::operator =(const mat3& srcMatrix)
	{
		for (int i = 0; i < 9; i++)
			(*this).matrixData[i] = srcMatrix.matrixData[i];

		return *this;
	}

	mat3 mat3::operator *(float scalarValue) const
	{
		mat3 obj;
		
		for (int i = 0; i < 9; i++)
			obj.matrixData[i] = (*this).matrixData[i] * scalarValue;

		return obj;
	}

	mat3 mat3::operator *(const mat3& srcMatrix) const
	{
		int help_i = 0, help_j = 0, current_i_obj = -1;
		mat3 obj;

		
		for (int i = 0; i < 3; i++)
		{
			help_i = 0;

			for (int j = 0; j < 3; j++)
			{
				obj.matrixData[++current_i_obj] = (*this).matrixData[help_i] * srcMatrix.matrixData[3 * help_j] + (*this).matrixData[help_i + 3] * srcMatrix.matrixData[3 * help_j + 1] + (*this).matrixData[help_i + 6] * srcMatrix.matrixData[3 * help_j + 2];
				++help_i;
			}

			++help_j;
		}

		return obj;
	}

	vec3 mat3::operator *(const vec3& srcVector) const
	{
		vec3 obj;

		obj.x = (*this).matrixData[0] * srcVector.x + (*this).matrixData[3] * srcVector.y + (*this).matrixData[6] * srcVector.z;
		obj.y = (*this).matrixData[1] * srcVector.x + (*this).matrixData[4] * srcVector.y + (*this).matrixData[7] * srcVector.z;
		obj.z = (*this).matrixData[2] * srcVector.x + (*this).matrixData[5] * srcVector.y + (*this).matrixData[8] * srcVector.z;

		return obj;
		
	}

	mat3 mat3::operator +(const mat3& srcMatrix) const
	{
		mat3 obj;

		for (int i = 0; i < 9; i++)
			obj.matrixData[i] = (*this).matrixData[i] + srcMatrix.matrixData[i];

		return obj;
	}

	//get element by (row, column)
	float& mat3::at(int i, int j)
	{
		return (*this).matrixData[i + 3 * j];
	}

	const float& mat3::at(int i, int j) const
	{
		return (*this).matrixData[i + 3 * j];
	}

	float mat3::determinant() const
	{
		float detSum, detDiff;
		//det = m0m4m8 + m1m5m6 + m2m3m7 - m2m4m6 - m0m5m7 - m1m3m8

		detSum = (*this).matrixData[0] * (*this).matrixData[4] * (*this).matrixData[8] + (*this).matrixData[1] * (*this).matrixData[5] * (*this).matrixData[6] + (*this).matrixData[2] * (*this).matrixData[3] * (*this).matrixData[7];
		detDiff = (*this).matrixData[2] * (*this).matrixData[4] * (*this).matrixData[6] + (*this).matrixData[0] * (*this).matrixData[5] * (*this).matrixData[7] + (*this).matrixData[1] * (*this).matrixData[3] * (*this).matrixData[8];

		return detSum - detDiff;
	}

	mat3 mat3::inverse() const
	{
		float det = determinant();
		if (abs(det) > 1e-7)
		{
			mat3 obj = transpose();

			obj.matrixData[0] = (*this).matrixData[4] * (*this).matrixData[8] - (*this).matrixData[7] * (*this).matrixData[5];
			obj.matrixData[1] = (-1) * ((*this).matrixData[1] * (*this).matrixData[8] - (*this).matrixData[2] * (*this).matrixData[7]);
			obj.matrixData[2] = (*this).matrixData[1] * (*this).matrixData[5] - (*this).matrixData[4] * (*this).matrixData[2];
			obj.matrixData[3] = (-1) * ((*this).matrixData[3] * (*this).matrixData[8] - (*this).matrixData[6] * (*this).matrixData[5]);
			obj.matrixData[4] = (*this).matrixData[0] * (*this).matrixData[8] - (*this).matrixData[2] * (*this).matrixData[6];
			obj.matrixData[5] = (-1) * ((*this).matrixData[0] * (*this).matrixData[5] - (*this).matrixData[2] * (*this).matrixData[3]);
			obj.matrixData[6] = (*this).matrixData[3] * (*this).matrixData[7] - (*this).matrixData[6] * (*this).matrixData[4];
			obj.matrixData[7] = (-1) * ((*this).matrixData[0] * (*this).matrixData[7] - (*this).matrixData[1] * (*this).matrixData[6]);
			obj.matrixData[8] = (*this).matrixData[0] * (*this).matrixData[4] - (*this).matrixData[1] * (*this).matrixData[3];

			for (int i = 0; i < 9; i++)
				obj.matrixData[i] = obj.matrixData[i] / det;

			return obj;
		}

		mat3 obj;
		return obj;

	}

	mat3 mat3::transpose() const
	{
		mat3 obj;

		obj.matrixData[0] = (*this).matrixData[0];
		obj.matrixData[1] = (*this).matrixData[3];
		obj.matrixData[2] = (*this).matrixData[6];
		obj.matrixData[3] = (*this).matrixData[1];
		obj.matrixData[4] = (*this).matrixData[4];
		obj.matrixData[5] = (*this).matrixData[7];
		obj.matrixData[6] = (*this).matrixData[2];
		obj.matrixData[7] = (*this).matrixData[5];
		obj.matrixData[8] = (*this).matrixData[8];

		return obj;
	}
	
}