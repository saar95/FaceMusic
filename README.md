
# FaceMusic
This app detects your emotions based on your picture and creates playlists according to your mood.  

YouTube Link:[here](https://github.com/LeveI-Up/AI_Ex8/blob/main/%D7%9E%D7%98%D7%9C%D7%94%208.pdf)

## The classification (by CNN model)
To detect faces on an image the application uses [ML Kit](https://developers.google.com/ml-kit).
After detection complete the face image area converted into greyscale 48*48 pixel format, each pixel represents as [0, 1] float number.
Finally, converted area fed to the [TensorFlow Light](https://www.tensorflow.org/lite/guide) convolutional neural network model (simple_classifier.tflite).
The model provide output that consist of probabilities for each class: angry, disgust, fear, happy, neutral, sad, surprise.  

(https://user-images.githubusercontent.com/20986238/146235701-4a91d541-29f8-4fdb-9999-c7a6fdd8f42e.jpg)


<img src="/images/example.png" width="288" height="512">

