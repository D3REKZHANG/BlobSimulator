# BlobSimulator
ICS4U0 Final Project - A Healthy Living Game

Culminating Project for AP Computer Science at William Lyon Mackenzie Collegiate Institute. 

Completed June 7, 2018 - Final Mark: 98%

<h2>Description</h2>

Welcome to Blob Simulator! You will follow and control the life of a blob, an abstract representation of an unhealthy teen. Throughout the levels, you will improve the physical health of the blob by moving around, becoming faster, lighter, and smaller. You will also learn the importance of maintaining a healthy lifestyle, specifically the importance of exercise, proper nutrition, and adequate rest/sleep

The jar file will run the game as long as the resource and highscores folder is in the same directory. 

<h2>Implementation Notes</h2>

Coded entirely in Java with Swing and no external libraries. The code is very clean and sizeable. The game is structured in an object oriented approach, with proper use of inheritance. Messages integrated into the game world (walls, backgrounds) communicate with the player. A quiz at the end of each level will test the players knowledge of the information presented throughout the level. Highscores are saved in the three text files found in the highscores folder.

<p></p>

Levels are loaded in by reading a png image file. Each pixel represents a 40px by 40px square on the map, and different colours represent different objects (i.e. green = waypoint, blue = water bottle). Levels are loaded everytime before the start, so changes can be made while the game is running. 

<h2>License</h2>

Copyright (c) 2018 Derek Zhang

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
