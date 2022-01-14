import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import './App.css';
import HomeComponent from "./components/HomeComponent";
import BookingDateComponent from "./components/BookingDateComponent";
import RoomCategoryComponent from "./components/RoomCategoryComponent"

function App() {
  return (
    <div>
      <Router>
        <div className="container">
          <Routes>
            <Route path='/' element={<HomeComponent />} />
            <Route path='/choosedates' element={<BookingDateComponent />} />
            <Route path='/chooseroomcategories' element={<RoomCategoryComponent />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;