// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAndoDBDr8Ek-0zrP8A_iLxygT1_uM7jRo",
  authDomain: "quiz-b1f0e.firebaseapp.com",
  projectId: "quiz-b1f0e",
  storageBucket: "quiz-b1f0e.appspot.com",
  messagingSenderId: "382537016102",
  appId: "1:382537016102:web:4a5776cec340be4bc357e7",
  measurementId: "G-NS08Z90W98",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const storage = getStorage(app);

export { storage, analytics };
