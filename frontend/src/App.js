import React  from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import PersonItem from "./components/Login/PersonItem";
import Profile from "./components/Login/Profile";
import Header from "./components/Header/Header";
import Home from "./components/Home/Home"


function App() {
  return (

      <Router >
          <Header/>
        <Routes>
          <Route exact path="/"  element={<Home/>} />
            <Route exact path="/Home"  element={<Home/>} />
            <Route path="/Profile"  element={<Profile/>} />
            <Route path="/PersonItem"  element={<PersonItem/>} />
        </Routes>
      </Router>
  );
}

export default App;
