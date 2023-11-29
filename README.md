# COEN/ELEC 390

## Description
This project is a posture detection application made for android devices. The application utilizes a microcontroller (Raspberry Pi 4B), an external camera, and Firebase Realtime database to power the application. The application then uses the vector data from the database to calculate the angle between your back and thigh. PostureFit then uses the resulting angle to determine whether your posture is correct.

## Team Members

- Omar Abouelatta
- Mohamed Bedair
- Michel Farah
- Elias Haddad
- Marc Jenno


## Technologies

- Java
- XML
- Python
- Firebase
- OpenCV
- Mediapipe
- Numpy


## How to run

### App set up:
Clone the repository on your computer locally. Using android studio build the app and install on your android device. Now your phone is setup and ready to go.

### RPi set up:

Assuming you have an RPi 4B with the latest firmware install, apt get the latest python version. After installing python, launch a virtual environment using the terminal. Next, pip install mediapipe, opencv, and numpy. After installing all dependencies on the RPi, clone the folder RPi_Code onto your RPi and run the python script with a webcam connected to your RPi.

Once your RPi is running the script, you can now open the app normally and use it.
