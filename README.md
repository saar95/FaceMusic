
# FaceMusic
This app detects your emotions based on your picture and creates playlists according to your mood.  

YouTube Link: [here](https://www.youtube.com/watch?v=qLjUGwrKTVs)

Collaborates: [Omer Aden](https://github.com/omer6546), [Almog Reuveny](https://github.com/almogre02), [Saar Barel](https://github.com/saar95)

[Project summary](https://github.com/almogre02/FaceMusic/blob/main/%D7%9E%D7%A6%D7%92%D7%AA%20%D7%A1%D7%99%D7%95%D7%9D%20%D7%A4%D7%A8%D7%95%D7%99%D7%99%D7%A7%D7%98.pdf)


<img src="https://user-images.githubusercontent.com/20986238/146235701-4a91d541-29f8-4fdb-9999-c7a6fdd8f42e.jpg" width="288" height="512">



## Screens:
  * [LogIn Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/LoginActivity.java)
  * [Welcome Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/WelcomeActivity.java)
  * [Main Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/MainActivity.java)
  * [Register Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/RegisterActivity.java)
  * [User Class](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/UserClass.java)
  * [User Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/UserActivity.java)
  * [Creator Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/CreatorActivity.java)
  * [History Activity](https://github.com/almogre02/FaceMusic/blob/main/app/src/main/java/com/lampa/emotionrecognition/HistoryActivity.java)
  


## The classification (by CNN model)
To detect faces on an image the application uses [ML Kit](https://developers.google.com/ml-kit).
After detection complete the face image area converted into greyscale 48*48 pixel format, each pixel represents as [0, 1] float number.
Finally, converted area fed to the [TensorFlow Light](https://www.tensorflow.org/lite/guide) convolutional neural network model (simple_classifier.tflite).
The model provide output that consist of probabilities for each class: angry, disgust, fear, happy, neutral, sad, surprise.  

