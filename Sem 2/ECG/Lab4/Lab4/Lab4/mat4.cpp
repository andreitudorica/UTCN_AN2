#include <algorithm>
#include <iostream>
#include "vec4.h"
#include "mat4.h"
#include "vec3.h"
#include "mat3.h"

namespace egc {

	mat4& mat4::operator =(const mat4& srcMatrix)
	{
		for (int i = 0; i < 16; i++)
			(*this).matrixData[i] = srcMatrix.matrixData[i];

		return *this;
	}

	mat4 mat4::operator *(float scalarValue) const
	{
		mat4 obj;

		for (int i = 0; i < 16; i++)
			obj.matrixData[i] = (*this).matrixData[i] * scalarValue;

		return obj;
	}

	mat4 mat4::operator *(const mat4& srcMatrix) const
	{
		int help_i = 0, help_j = 0, current_i_obj = -1;
		mat4 obj;


		for (int i = 0; i < 4; i++)
		{
			help_i = 0;

			for (int j = 0; j < 4; j++)
			{
				obj.matrixData[++current_i_obj] = (*this).matrixData[help_i] * srcMatrix.matrixData[4 * help_j] + (*this).matrixData[help_i + 4] * srcMatrix.matrixData[4 * help_j + 1] + (*this).matrixData[help_i + 8] * srcMatrix.matrixData[4 * help_j + 2] + (*this).matrixData[help_i + 12] * srcMatrix.matrixData[4 * help_j + 3];
				++help_i;
			}

			++help_j;
		}

		return obj;
	}

	vec4 mat4::operator *(const vec4& srcVector) const
	{
		vec4 obj;

		obj.x = (*this).matrixData[0] * srcVector.x + (*this).matrixData[4] * srcVector.y + (*this).matrixData[8] * srcVector.z + (*this).matrixData[12] * srcVector.w;
		obj.y = (*this).matrixData[1] * srcVector.x + (*this).matrixData[5] * srcVector.y + (*this).matrixData[9] * srcVector.z + (*this).matrixData[13] * srcVector.w;
		obj.z = (*this).matrixData[2] * srcVector.x + (*this).matrixData[6] * srcVector.y + (*this).matrixData[10] * srcVector.z + (*this).matrixData[14] * srcVector.w;
		obj.w = (*this).matrixData[3] * srcVector.x + (*this).matrixData[7] * srcVector.y + (*this).matrixData[11] * srcVector.z + (*this).matrixData[15] * srcVector.w;

		return obj;
	}

	mat4 mat4::operator +(const mat4& srcMatrix) const
	{
		mat4 obj;

		for (int i = 0; i < 16; i++)
			obj.matrixData[i] = (*this).matrixData[i] + srcMatrix.matrixData[i];

		return obj;
	}

	//get element by (row, column)
	float& mat4::at(int i, int j)
	{
		return (*this).matrixData[i + 4 * j];
	}

	const float& mat4::at(int i, int j) const
	{
		return (*this).matrixData[i + 4 * j];
	}

	float mat4::determinant() const
	{
		float sum = 0.f, elem;

		for (int i = 0; i < 4; i++)
		{//row i, column 0
			float matrixData[9];

			elem = (float) pow((-1), (i % 2)) * at(i, 0); //element at row i col 0
			
			//(-1)^(i%2): if the row+col is odd then we have to multiply the element by -1 and then by the 3x3 determinant
			// if it is even then we have to multiply by 1 the element and then by the 3x3 determinant
			switch (i)
				{//we create a 3 x 3 matrix rezulted by eliminating the i row and 0 column
				case 0:	matrixData[0] = at(1, 1);	//eliminate line 0 and column 0
						matrixData[1] = at(2, 1);
						matrixData[2] = at(3, 1);
						matrixData[3] = at(1, 2);
						matrixData[4] = at(2, 2);
						matrixData[5] = at(3, 2);
						matrixData[6] = at(1, 3);
						matrixData[7] = at(2, 3);
						matrixData[8] = at(3, 3);
						break;
				case 1:	matrixData[0] = at(0, 1);	//eliminate line 1 and column 0
						matrixData[1] = at(2, 1);
						matrixData[2] = at(3, 1);
						matrixData[3] = at(0, 2);
						matrixData[4] = at(2, 2);
						matrixData[5] = at(3, 2);
						matrixData[6] = at(0, 3);
						matrixData[7] = at(2, 3);
						matrixData[8] = at(3, 3);
					break;
				case 2:	matrixData[0] = at(0, 1);	//eliminate line 2 and column 0
						matrixData[1] = at(1, 1);
						matrixData[2] = at(3, 1);
						matrixData[3] = at(0, 2);
						matrixData[4] = at(1, 2);
						matrixData[5] = at(3, 2);
						matrixData[6] = at(0, 3);
						matrixData[7] = at(1, 3);
						matrixData[8] = at(3, 3);
					break;
				default:matrixData[0] = at(0, 1);	//eliminate line 3 and column 0
						matrixData[1] = at(1, 1);
						matrixData[2] = at(2, 1);
						matrixData[3] = at(0, 2);
						matrixData[4] = at(1, 2);
						matrixData[5] = at(2, 2);
						matrixData[6] = at(0, 3);
						matrixData[7] = at(1, 3);
						matrixData[8] = at(2, 3);
					break;
				}

				mat3* matrix = new mat3(matrixData);
				elem *= (*matrix).determinant();
				sum += elem;
			

		}
		
		return sum;
	}

	
	mat4 mat4::inverse() const
	{
		float matrixData[16];

		float det = determinant();

		if (abs(det) > 1e-7)
		{
			mat4 this_trasnspose = transpose();

			int current_col = -1, current_row;

			for (int i = 0; i < 16; i++)//building the adjoint of matrix 4
			{
				if (i % 4 == 0)
					++current_col;

				float matrixData_3x3[9];
				int size_matrixData = -1;

				current_row = i % 4;
			
				for(int col = 0; col < 4; col++)//building the 3 x 3 matrix resulted by eliminating current_row row and current_col column
					for (int row = 0; row < 4; row++)
					{
						if (col != current_col && row != current_row)
						{
							matrixData_3x3[++size_matrixData] = this_trasnspose.at(row, col);
						}
					}
			
				mat3 *matrix3 = new mat3(matrixData_3x3);
				
				matrixData[i] = (float) pow(-1., (double) current_row +	current_col) * (*matrix3).determinant();
				
			}
			
			for (int i = 0; i < 16; i++)
				matrixData[i] = matrixData[i] / det;
			

			mat4 *matrix4 = new mat4(matrixData);

			return *matrix4;
		}

		return matrixData;
	}

	mat4 mat4::transpose() const
	{
		mat4 obj;
		int col_nr = -1;

		for (int i = 0; i < 16; i++)
		{
			if (i % 4 == 0)
				++col_nr;		//for i between (0, 3) col_nr == 0, i between (4, 7) col_nr == 1, i between (8, 11) col_nr == 2, and from 12 to 15 it will be 3
			
			obj.matrixData[i] = (*this).matrixData[(i % 4) * 4 + col_nr];
			
		}
		return obj;

	}
}
