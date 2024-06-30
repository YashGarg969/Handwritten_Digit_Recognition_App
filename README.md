# HANDWRITTEN DIGIT RECOGNITION ANDROID APP

# Introduction:
This project is all about creating an Android app that can recognize and predict handwritten digits. Imagine you draw a number, like 5, on your phone's screen using your finger. The app is designed to take that drawing and tell you that you drew a "5." It's like magic!

# How It Works:
The app has two main parts: the drawing canvas and the prediction engine.
1. Drawing Canvas:
Think of the drawing canvas as a virtual paper where you can draw with your finger. The app provides you with a choice of colors - black and red. When you select a color, the drawing brush changes to that color. When you start drawing on the canvas, the app keeps track of the path you're drawing, like the route you take.
2. Prediction Engine:
Now comes the clever part. After you draw a digit, you press a button to predict what number you drew. The app takes the drawing and uses a special computer "brain" called a machine learning model to figure out what number it looks like.
We have used tflite model for prediction of digits.

# Behind the Scenes:
Getting the Drawing: The app takes the drawing you made on the canvas and converts it into a picture that the computer can understand.
The Machine Learning Magic: The app uses a smart model that we trained earlier. This model has "learned" from lots of examples how different drawings correspond to different numbers. It looks at the picture you drew and thinks really hard to decide which number it's most likely to be.
Making the Prediction: The model gives the app a suggestion, like "Hey, I think this looks like the number 7." The app takes this suggestion and shows it to you.

# User Interaction:
You can choose between black and red colors to draw with.
There's a button to clear the canvas if you want to start over.
Another button makes the magic happen: it predicts the number you've drawn and shows you its guess.

# Benefits and Fun:
This app can be really fun to play with! You can challenge yourself to draw different numbers and see if the app guesses correctly. It's also useful because it shows how computers can learn and understand things, just like humans.

# Conclusion:
In a nutshell, this project is about creating an Android app that turns your finger drawings of numbers into predictions. It uses a smart model to understand your drawings and guess the number you intended. It's a mix of creativity, technology, and a little bit of magic!

# Google Drive Video Link:
https://drive.google.com/file/d/16SbBgib_mkzwK6cDpBr0vXwV5pGR129L/view?usp=drive_link



