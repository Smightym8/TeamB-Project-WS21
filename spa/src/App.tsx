import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import './App.css';
import HomeComponent from "./components/HomeComponent";
import BookingDateComponent from "./components/BookingDateComponent";
import GuestComponent from "./components/GuestComponent";

function App() {
  return (
    <div>
      <Router>
        <div className="container">
          <Routes>
            <Route path='/' element={<HomeComponent />} />
            <Route path='/choosedates' element={<BookingDateComponent />} />
            <Route path='/guestinformation' element={<GuestComponent />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;