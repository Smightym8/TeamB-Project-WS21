import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import './App.css';
import HomeComponent from "./components/HomeComponent";
import BookingDateComponent from "./components/BookingDateComponent";

function App() {
  return (
      <Router>
          <Routes>
            <Route path='/' element={<HomeComponent />} />
            <Route path='/choosedates' element={<BookingDateComponent />} />
          </Routes>
      </Router>
  );
}

export default App;