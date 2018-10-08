
#include <iostream>
#include "include\SDL.h"

//define window dimensions
const int WINDOW_WIDTH = 640;
const int WINDOW_HEIGHT = 480;

//The window
SDL_Window *window = NULL;
//The window renderer
SDL_Renderer* renderer = NULL;
SDL_Event currentEvent;

bool quit = false;

int mouseX, mouseY;

typedef struct _Bline
{
	int startX;
	int startY;
	int endX;
	int endY;
} BresenhamLine;

typedef struct _Bcircle
{
	int centerX;
	int centerY;
	int radius;
} BresenhamCircle;

BresenhamLine myLine;
BresenhamCircle myCircle;

bool initWindow()
{
	bool success = true;

	//Try to initialize SDL
	if (SDL_Init(SDL_INIT_VIDEO) < 0)
	{
		std::cout << "SDL initialization failed" << std::endl;
		success = false;
	}
	else {
		//Try to create the window
		window = SDL_CreateWindow("SDL Hello World Example", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, WINDOW_WIDTH, WINDOW_HEIGHT, SDL_WINDOW_SHOWN | SDL_WINDOW_ALLOW_HIGHDPI);

		if (window == NULL)
		{
			std::cout << "Failed to create window: " << SDL_GetError() << std::endl;
			success = false;
		}
		else
		{
			//Create renderer for window
			renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
			if (renderer == NULL)
			{
				printf("Renderer could not be created! SDL Error: %s\n", SDL_GetError());
				success = false;
			}
			else
			{
				//Initialize renderer color
				SDL_SetRenderDrawColor(renderer, 0xFF, 0xFF, 0xFF, 0xFF);
			}
		}
	}

	return success;
}

void destroyWindow()
{
	//Destroy window
	SDL_DestroyRenderer(renderer);
	SDL_DestroyWindow(window);
	window = NULL;
	renderer = NULL;
	//Quit SDL
	SDL_Quit();
}

void handleMouseEvents()
{
	//Mouse event -> pressed button
	if (currentEvent.type == SDL_MOUSEBUTTONDOWN)
	{
		if (currentEvent.button.button == SDL_BUTTON_LEFT)
		{
			SDL_GetMouseState(&mouseX, &mouseY);
			myLine.startX = myLine.endX = mouseX;
			myLine.startY = myLine.endY = mouseY;
		}

		if (currentEvent.button.button == SDL_BUTTON_RIGHT)
		{
			SDL_GetMouseState(&mouseX, &mouseY);
			myCircle.centerX = mouseX;
			myCircle.centerY = mouseY;
		}
	}

	//Mouse event -> mouse movement
	if (currentEvent.type == SDL_MOUSEMOTION)
	{
		if (currentEvent.button.button & SDL_BUTTON_LMASK)
		{
			SDL_GetMouseState(&mouseX, &mouseY);
			myLine.endX = mouseX;
			myLine.endY = mouseY;
		}

		if (currentEvent.button.button & SDL_BUTTON_RMASK)
		{
			SDL_GetMouseState(&mouseX, &mouseY);
			myCircle.radius = static_cast<int> (
				sqrt(static_cast<float>((mouseX - myCircle.centerX) * (mouseX - myCircle.centerX) +
				(mouseY - myCircle.centerY) * (mouseY - myCircle.centerY)))
				);
		}
	}
}

void handleKeyboardEvents()
{
	//Keyboard event
	if (currentEvent.type == SDL_KEYDOWN)
	{
		switch (currentEvent.key.keysym.sym)
		{
		case SDLK_ESCAPE:
			quit = true;
			break;

		default:
			break;
		}
	}
}

//Detects in which octant a line is situated
int getOctant(BresenhamLine line)
{
	int deltaY = (line.endY - line.startY);
	int deltaX = (line.endX - line.startX);
	//Slope		
	float m = abs(deltaY) / (float)abs(deltaX);

	if ((m <= 1) && (deltaX > 0) && (deltaY <= 0)) //first octant
		return 1;
	if ((m > 1) && (deltaX > 0) && (deltaY <= 0)) //second octant
		return 2;
	if ((m > 1) && (deltaX <= 0) && (deltaY <= 0)) //third octant
		return 3;
	if ((m > -1) && (deltaX <= 0) && (deltaY <= 0)) //forth octant
		return 4;
	if ((m <= 1) && (deltaX <= 0) && (deltaY > 0)) //fith octant
		return 5;
	if ((m > 1) && (deltaX <= 0) && (deltaY > 0)) //sixth octant
		return 6;
	if ((m > 1) && (deltaX > 0) && (deltaY > 0)) //sixth octant
		return 7;
	if ((m > -1) && (deltaX > 0) && (deltaY > 0)) //sixth octant
		return 8;
	return 0;
}

//Draws a new raster line using Bresenham's algorithm
void BresenhamDraw(BresenhamLine line)
{
	int tmpStartX = 0;
	int tmpStartY = 0;
	int tmpEndX = 0;
	int tmpEndY = 0;
	int tmpCurrentX = 0;
	int tmpCurrentY = 0;

	int dy = abs(line.endY - line.startY);
	int dx = abs(line.endX - line.startX);

	int d, inc1, inc2;

	int octant = getOctant(line);
	//std::cout << octant;
	if (octant == 1)
	{
		tmpStartX = tmpCurrentX = line.startX;
		tmpEndX = line.endX;
		tmpStartY = tmpCurrentY = line.startY;
		tmpEndY = line.endY;

		d = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);

		while (tmpCurrentX < tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			++tmpCurrentX;
			if (d < 0) 
				d += inc1;
			else
			{
				d += inc2;
				--tmpCurrentY;
			}
		}
	}
	if (octant == 2)
	{
		tmpStartX = tmpCurrentX = line.startX;
		tmpEndX = line.endX;
		tmpStartY = tmpCurrentY = line.startY;
		tmpEndY = line.endY;

		d = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);

		while (tmpCurrentX < tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			--tmpCurrentY;
			if (d < 0)
				d += inc1;
			else
			{
				d += inc2;
				++tmpCurrentX;
			}
		}
	}
	if (octant == 3)
	{
		tmpStartX = tmpCurrentX = line.startX;
		tmpEndX = line.endX;
		tmpStartY = tmpCurrentY = line.startY;
		tmpEndY = line.endY;

		d = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);

		while (tmpCurrentX > tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			--tmpCurrentY;
			if (d < 0)
				d += inc1;
			else
			{
				d += inc2;
				--tmpCurrentX;
			}
		}
	}
	if (octant == 4)
	{
		tmpStartX = tmpCurrentX = line.startX;
		tmpEndX = line.endX;
		tmpStartY = tmpCurrentY = line.startY;
		tmpEndY = line.endY;

		d = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);

		while (tmpCurrentX > tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			--tmpCurrentX;
			if (d < 0) d += inc1;
			else
			{
				d += inc2;
				--tmpCurrentY;
			}
		}
	}
	if (octant == 5)
	{
		tmpStartX = tmpCurrentX = line.endX;
		tmpEndX = line.startX;
		tmpStartY = tmpCurrentY = line.endY;
		tmpEndY = line.startY;

		d = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);

		while (tmpCurrentX < tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			++tmpCurrentX;
			if (d < 0) d += inc1;
			else
			{
				d += inc2;
				--tmpCurrentY;
			}
		}
	}
	if (octant == 6)
	{
		tmpStartX = tmpCurrentX = line.endX;
		tmpEndX = line.startX;
		tmpStartY = tmpCurrentY = line.endY;
		tmpEndY = line.startY;


		d = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);

		while (tmpCurrentX < tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			--tmpCurrentY;
			if (d < 0)
				d += inc1;
			else
			{
				d += inc2;
				++tmpCurrentX;
			}
		}
	}
	if (octant == 7) 
	{
		tmpStartX = tmpCurrentX = line.endX;
		tmpEndX = line.startX;
		tmpStartY = tmpCurrentY = line.endY;
		tmpEndY = line.startY;
		d = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);

		while (tmpCurrentX > tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			--tmpCurrentY;
			if (d < 0)
				d += inc1;
			else
			{
				d += inc2;
				--tmpCurrentX;
			}
		}
	}
	if (octant == 8) 
	{
		tmpStartX = tmpCurrentX = line.endX;
		tmpEndX = line.startX;
		tmpStartY = tmpCurrentY = line.endY;
		tmpEndY = line.startY;
		d = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);

		while (tmpCurrentX > tmpEndX)
		{
			//Draw current point
			SDL_RenderDrawPoint(renderer, tmpCurrentX, tmpCurrentY);
			--tmpCurrentX;
			if (d < 0) d += inc1;
			else
			{
				d += inc2;
				--tmpCurrentY;
			}
		}
	}
}

//Draws a new raster circle using Bresenham's algorithm
void BresenhamDraw(BresenhamCircle circle)
{

}

int main(int argc, char * argv[])
{
	if (!initWindow())
	{
		std::cout << "Failed to initialize" << std::endl;
		return -1;
	}

	while (!quit) {
		//Handle events on queue
		while (SDL_PollEvent(&currentEvent) != 0)
		{
			//User requests quit
			if (currentEvent.type == SDL_QUIT)
			{
				quit = true;
			}

			handleMouseEvents();
			handleKeyboardEvents();

			//Clear screen
			SDL_SetRenderDrawColor(renderer, 0xFF, 0xFF, 0xFF, 0xFF);
			SDL_RenderClear(renderer);

			//Draw Bresenham line
			SDL_SetRenderDrawColor(renderer, 0x00, 0x00, 0x00, 0x00);
			BresenhamDraw(myLine);

			//Draw Bresenham circle
			SDL_SetRenderDrawColor(renderer, 0x00, 0xFF, 0xFF, 0x00);
			BresenhamDraw(myCircle);

			//Update screen
			SDL_RenderPresent(renderer);
		}
	}

	destroyWindow();
	return 0;
}
