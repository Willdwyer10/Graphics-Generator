## Inspiration
Since April 2020, I have found myself repeatedly creating the same advertisement with slightly different text, which was both time-consuming and costly. The hackathon presented an ideal opportunity to develop an efficient program that could save time and money for the organization.

## What it does
The Automated Graphics Generator allows users to select a .txt file through the GUI, confirm their intent to create and implement graphics, and watch the program create the advertisements almost instantly. It has utilities to work with multiple locations at once, and fully incorporates error-checking to prevent problems from happening in other parts of the program. Additionally, it includes funtionality allowing users to access the other external softwares necessary to create the graphics.

## How it was built
The first goal was to read data from the .txt file and create objects representing individual bands. After that, creating and exporting image files, ensuring that the text on the graphics was properly formatted and aligned, was the primary goal. The next challenge was to automate the process of implementing the formats by using the Robot class. After adding error checking to ensure the best algorithmically-determined string format, the graphics were uploaded to the advertising software. To create the GUI, I used the Netbeans IDE, which made the process much easier. I tried to make it as straightforward as possible by including buttons for every function necessary, including for external software. Furthermore, tool tips were added to help users understand the purpose of each button. To make the program the most user-friendly, I used serialization to save data between multiple runs of the program.

## Challenges run into
The biggest challenge was formatting the text on the graphics, ensuring that it aligned correctly and fit the size of the image. Additionally, developing an algorithm to determine where the text should be split into two lines to fill the space correctly was also a major challenge.

## Accomplishments
Proudly automating the graphics upload process into the advertising software and optimizing it, developing an algorithm to split the text in half to fill the space, and creating a user-friendly, professional-looking GUI. 

## Lessons Learned
Through the development of Automated Graphics Generator, I learned how to create a GUI in Java, automate computer processes, and create and export images. I worked with serialization to create a program that "remembers" what happened between multiple runs. I learned how to use the Netbeans IDE, a completely unfamiliar system for Java GUI production.

## Future developments
I hope to develop Automated Graphics Generator into a .exe program that reads from a spreadsheet, allowing users to work with less idealized documents.
