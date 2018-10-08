#include "vec2.h"
#include "vec3.h"
#include "mat3.h"
#include "mat4.h"
#include "transform.h"
#include <math.h>

namespace egc {

	mat3 translate(const vec2 translateArray)
	{
		//mat3 *translationMatrix = new mat3();

		//(*translationMatrix).matrixData[6] = translateArray.x;
		//(*translationMatrix).matrixData[7] = translateArray.y;
		//return (*translationMatrix);

		mat3 translationMatrix;
		translationMatrix.matrixData[6] = translateArray.x;
		translationMatrix.matrixData[7] = translateArray.y;

		return translationMatrix;
	}

	mat3 translate(float tx, float ty)
	{
		mat3 *translationMatrix = new mat3();

		(*translationMatrix).matrixData[6] = tx;
		(*translationMatrix).matrixData[7] = ty;

		return (*translationMatrix);
	}

	mat3 scale(const vec2 scaleArray)
	{
		mat3 *scaleMatrix = new mat3();

		(*scaleMatrix).matrixData[0] = scaleArray.x;
		(*scaleMatrix).matrixData[4] = scaleArray.y;

		return (*scaleMatrix);
	}

	mat3 scale(float sx, float sy)
	{
		mat3 *scaleMatrix = new mat3();

		(*scaleMatrix).matrixData[0] = sx;
		(*scaleMatrix).matrixData[4] = sy;

		return (*scaleMatrix);

	}

	mat3 rotate(float angle)
	{
		mat3 *rotationMatrix = new mat3();

		double radian = angle *  PI / 180.;

		(*rotationMatrix).matrixData[0] = (*rotationMatrix).matrixData[4] = (float)cos(radian); ///cost
		(*rotationMatrix).matrixData[1] = (float)sin(radian); //sint
		(*rotationMatrix).matrixData[3] = (float)(-1.) * (float)sin(radian);//-sint

		return (*rotationMatrix);
	}

	//transformation matrices in 3D
	mat4 translate(const vec3 translateArray)
	{
		mat4 *translationMatrix = new mat4();

		(*translationMatrix).matrixData[12] = translateArray.x;
		(*translationMatrix).matrixData[13] = translateArray.y;
		(*translationMatrix).matrixData[14] = translateArray.z;

		return (*translationMatrix);
	}

	mat4 translate(float tx, float ty, float tz)
	{
		mat4 *translationMatrix = new mat4();

		(*translationMatrix).matrixData[12] = tx;
		(*translationMatrix).matrixData[13] = ty;
		(*translationMatrix).matrixData[14] = tz;

		return (*translationMatrix);
	}

	mat4 scale(const vec3 scaleArray)
	{
		mat4 *scaleMatrix = new mat4();

		(*scaleMatrix).matrixData[0] = scaleArray.x;
		(*scaleMatrix).matrixData[5] = scaleArray.y;
		(*scaleMatrix).matrixData[10] = scaleArray.z;

		return (*scaleMatrix);
	}
	mat4 scale(float sx, float sy, float sz)
	{
		mat4 *scaleMatrix = new mat4();

		(*scaleMatrix).matrixData[0] = sx;
		(*scaleMatrix).matrixData[5] = sy;
		(*scaleMatrix).matrixData[10] = sz;

		return (*scaleMatrix);
	}

	mat4 rotateX(float angle)
	{
		mat4 *rotationMatrix = new mat4();

		double radian = angle *  PI / 180.;

		(*rotationMatrix).matrixData[5] = (*rotationMatrix).matrixData[10] = (float)cos(radian); ///cost
		(*rotationMatrix).matrixData[6] = (float)sin(radian); //sint
		(*rotationMatrix).matrixData[9] = (float)(-1.) * (float)sin(radian);//-sint

		return (*rotationMatrix);
	}

	mat4 rotateY(float angle)
	{
		mat4 *rotationMatrix = new mat4();

		double radian = angle *  PI / 180.;

		(*rotationMatrix).matrixData[0] = (*rotationMatrix).matrixData[10] = (float)cos(radian); ///cost
		(*rotationMatrix).matrixData[8] = (float)sin(radian); //sint
		(*rotationMatrix).matrixData[2] = (float)(-1.) * (float)sin(radian);//-sint

		return (*rotationMatrix);
	}

	mat4 rotateZ(float angle)
	{
		mat4 *rotationMatrix = new mat4();

		double radian = angle *  PI / 180.;

		(*rotationMatrix).matrixData[0] = (*rotationMatrix).matrixData[5] = (float)cos(radian); ///cost
		(*rotationMatrix).matrixData[1] = (float)sin(radian); //sint
		(*rotationMatrix).matrixData[4] = (float)(-1.) * (float)sin(radian);//-sint

		return (*rotationMatrix);
	}
}