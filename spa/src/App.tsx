import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import './App.css';
import HomeComponent from "./components/HomeComponent";
import BookingComponent from "./components/BookingComponent";

function App() {
  return (
      <Router>
          <Routes>
            <Route path='/spa' element={<HomeComponent />} />
            <Route path='/spa/booking' element={<BookingComponent />} />
          </Routes>
      </Router>
  );
}

export default App;