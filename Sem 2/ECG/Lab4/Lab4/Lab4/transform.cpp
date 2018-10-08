#include <cmath>
#include <iostream>
#include "vec2.h"
#include "vec3.h"
#include "mat4.h"
#include "mat3.h"
using namespace std;

namespace egc {
	//mat3 translate(const vec2 translateArray);
	const double PI = atan(1.0) * 4;

	mat3 translate(const vec2 translateArray)
	{
		mat3 v;
		v.matrixData[0] = 1;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 1;
		v.matrixData[5] = 0;
		v.matrixData[6] = translateArray.x;
		v.matrixData[7] = translateArray.y;
		v.matrixData[8] = 1;
		return v;
	}

	mat3 translate(float tx, float ty) {
		mat3 v;
		v.matrixData[0] = 1;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 1;
		v.matrixData[5] = 0;
		v.matrixData[6] = tx;
		v.matrixData[7] = ty;
		v.matrixData[8] = 1;
		return v;
	
	}

	mat3 scale(const vec2 scaleArray) {
		mat3 v;
		v.matrixData[0] = scaleArray.x;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = scaleArray.y;
		v.matrixData[5] = 0;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 1;
		return v;
	}

	mat3 scale(float sx, float sy) {
	
		mat3 v;
		v.matrixData[0] = sx;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = sy;
		v.matrixData[5] = 0;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 1;	
		return v;
	}


	mat3 rotate(float angle) {
		mat3 v;
		v.matrixData[0] = cos(angle*(PI/180));
		v.matrixData[1] = sin(angle*(PI / 180));
		v.matrixData[2] = 0;
		v.matrixData[3] = (-1)*sin(angle*(PI / 180));
		v.matrixData[4] = cos(angle*(PI / 180));
		v.matrixData[5] = 0;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 1;
	
		return v;
	}


	mat4 translate(const vec3 translateArray) {
		mat4 v;
		v.matrixData[0] = 1;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 0;
		v.matrixData[5] = 1;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 0;
		v.matrixData[9] = 0;
		v.matrixData[10] = 1;
		v.matrixData[11] = 0;
		v.matrixData[12] = translateArray.x;
		v.matrixData[13] = translateArray.y;
		v.matrixData[14] = translateArray.z;
		v.matrixData[15] = 1;

		return v;
	}

	mat4 translate(float tx, float ty, float tz) {
		mat4 v;
		v.matrixData[0] = 1;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 0;
		v.matrixData[5] = 1;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 0;
		v.matrixData[9] = 0;
		v.matrixData[10] = 1;
		v.matrixData[11] = 0;
		v.matrixData[12] = tx;
		v.matrixData[13] = ty;
		v.matrixData[14] = tz;
		v.matrixData[15] = 1;

		return v;
	}
	mat4 scale(const vec3 scaleArray) {
	
		mat4 v;
		v.matrixData[0] = scaleArray.x;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 0;
		v.matrixData[5] = scaleArray.y;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 0;
		v.matrixData[9] = 0;
		v.matrixData[10] = scaleArray.z;
		v.matrixData[11] = 0;
		v.matrixData[12] = 0;
		v.matrixData[13] = 0;
		v.matrixData[14] = 0;
		v.matrixData[15] = 1;
		return v;
	}

	mat4 scale(float sx, float sy, float sz) {
	
		mat4 v;
		v.matrixData[0] = sx;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 0;
		v.matrixData[5] = sy;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 0;
		v.matrixData[9] = 0;
		v.matrixData[10] = sz;
		v.matrixData[11] = 0;
		v.matrixData[12] = 0;
		v.matrixData[13] = 0;
		v.matrixData[14] = 0;
		v.matrixData[15] = 1;
		return v;
	}

	mat4 rotateX(float angle) {
		mat4 v;
		v.matrixData[0] = 1;
		v.matrixData[1] = 0;
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = 0;
		v.matrixData[5] = cos(angle*(PI / 180));
		v.matrixData[6] = sin(angle*(PI / 180));
		v.matrixData[7] = 0;
		v.matrixData[8] = 0;
		v.matrixData[9] = -1*sin(angle*(PI / 180));
		v.matrixData[10] = cos(angle*(PI / 180));
		v.matrixData[11] = 0;
		v.matrixData[12] = 0;
		v.matrixData[13] = 0;
		v.matrixData[14] = 0;
		v.matrixData[15] = 1;
		return v;
	}
	mat4 rotateY(float angle) {
		mat4 v;
		v.matrixData[0] = cos(angle*(PI / 180));
		v.matrixData[1] = 0;
		v.matrixData[2] = -1*sin(angle*(PI / 180));
		v.matrixData[3] = 0;
		v.matrixData[4] = 0;
		v.matrixData[5] = 1;
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = sin(angle*(PI / 180));
		v.matrixData[9] = 0;
		v.matrixData[10] = cos(angle*(PI / 180));
		v.matrixData[11] = 0;
		v.matrixData[12] = 0;
		v.matrixData[13] = 0;
		v.matrixData[14] = 0;
		v.matrixData[15] = 1;

		return v;
	}

	mat4 rotateZ(float angle) {
		mat4 v;
		v.matrixData[0] = cos(angle*(PI / 180));
		v.matrixData[1] = sin(angle*(PI / 180));
		v.matrixData[2] = 0;
		v.matrixData[3] = 0;
		v.matrixData[4] = -1* sin(angle*(PI / 180));
		v.matrixData[5] = cos(angle*(PI / 180));
		v.matrixData[6] = 0;
		v.matrixData[7] = 0;
		v.matrixData[8] = 0;
		v.matrixData[9] = 0;
		v.matrixData[10] = 1;
		v.matrixData[11] = 0;
		v.matrixData[12] = 0;
		v.matrixData[13] = 0;
		v.matrixData[14] = 0;
		v.matrixData[15] = 1;

		return v;

	}


}