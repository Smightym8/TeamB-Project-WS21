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
    <div>
      <Router>
        <div className="container">
          <Routes>
            <Route path='/' element={<HomeComponent />} />
            <Route path='/booking' element={<BookingComponent />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;