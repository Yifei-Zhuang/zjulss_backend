

import React  from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Profile from "./components/Login/Profile";
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from "./components/Header/Header";
import Home from "./components/Home/Home"
function App() {
  return (

      <Router >
          <Header/>
        <Routes>
          <Route exact path="/"  element={<Home/>} />
            <Route path="/Profile"  element={<Profile/>} />
        </Routes>
      </Router>
  );
}

export default App;
