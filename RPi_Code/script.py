import cv2
import mediapipe as mp
from flask import Flask, render_template, Response
from firebase import firebase
import socket
import socketserver

app = Flask(__name__)
cap = cv2.VideoCapture(0)
mp_pose = mp.solutions.pose
mpDraw = mp.solutions.drawing_utils
pose = mp_pose.Pose()


firebase_url = 'https://minicapstone-f98f1-default-rtdb.firebaseio.com/'
firebase = firebase.FirebaseApplication(firebase_url, None)

### FUNCTION FOR EXTRACTING THE LANDMARKS ###
def extract_pose_data(landmarks):
    pose_data = {}

    if landmarks:
        # Extract specific pose landmarks
        for idx, landmark in enumerate(landmarks.landmark):
            pose_data[f'point_{idx}'] = (landmark.x, landmark.y)
            
        #0 is the X coordinate and Y is 1
        # Calculate vectors
        pose_data['vector_12_to_24'] = (
            pose_data['point_24'][0] - pose_data['point_12'][0],
            pose_data['point_24'][1] - pose_data['point_12'][1]
        )
        pose_data['vector_24_to_26'] = (
            pose_data['point_26'][0] - pose_data['point_24'][0],
            pose_data['point_26'][1] - pose_data['point_24'][1]
        )
        pose_data['vector_11_to_23'] = (
            pose_data['point_23'][0] - pose_data['point_11'][0],
            pose_data['point_23'][1] - pose_data['point_11'][1]
        )
        pose_data['vector_23_to_25'] = (
            pose_data['point_25'][0] - pose_data['point_23'][0],
            pose_data['point_25'][1] - pose_data['point_23'][1]
        )

    return pose_data

def get_local_ip_address():
    ip_address = None
    with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
        s.connect(('8.8.8.8', 80))  # connect to a well-known IP address
        ip_address = s.getsockname()[0]
    return ip_address

local_ip_address = get_local_ip_address()
firebase.put('/ip_address', '/', local_ip_address)

def generate_frames():
    while True:
        ret, frame = cap.read()
        frame1 = cv2.resize(frame, (640, 480))
        rgb_img = cv2.cvtColor(frame1, cv2.COLOR_BGR2RGB)
        result = pose.process(rgb_img)
        mpDraw.draw_landmarks(frame1, result.pose_landmarks, mp_pose.POSE_CONNECTIONS)
        

        ## IF there is a pose the landmarks are posted to the database
        if result.pose_landmarks:
            pose_data = extract_pose_data(result.pose_landmarks)
            firebase.put('/pose_data', '/', pose_data)
        
        _, buffer = cv2.imencode('.jpg', frame1)
        frame_data = buffer.tobytes()

        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame_data + b'\r\n')

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/video_feed')
def video_feed():
    return Response(generate_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)
