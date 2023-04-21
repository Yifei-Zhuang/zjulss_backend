import React  from "react";
import {BrowserRouter as Router, Route, Routes, Navigate} from 'react-router-dom';
import PersonItem from "./components/Login/PersonItem";
import Profile from "./components/Login/Profile";
import Header from "./components/Header/Header";
import Home from "./components/Home/Home"
import LoginPage from "./components/Login/LoginPage";
import Register from "./components/Register/Register";
import agent from "./agent";
import ChangePassword from "./components/Register/ChangePassword";

//withAuth的组件实例化
const AuthProfile = withAuth(Profile)
const AuthPersonItem = withAuth(PersonItem)
const AuthChangePW = withAuth(ChangePassword)

//检查是否登录
function checkAuth() {
	const token = localStorage.getItem('token');
	console.log("CheckToken:"+token)
	return !!token;
  }

  function withAuth(Component) {
	function AuthenticatedRoute(props) {
	  if (!checkAuth()) {
		alert("未登录")
		return <Navigate to="/Login" />;
	  }
	   return <Component {...props} />;
	};
	return AuthenticatedRoute;
  }

function App() {
	const [token, setToken] = React.useState('');
  return (

      <Router >
          <Header/>
        <Routes>
          	<Route exact path="/"  element={<Home/>} />
            <Route exact path="/Home"  element={<Home/>} />
			<Route path="/Login" element={<LoginPage/>} />
			<Route path="/Register" element={<Register/>} />
			{/* 需要身份验证的部分 */}
			<Route path="/Profile"  element={<AuthProfile />} />
            <Route path="/PersonItem"  element={<AuthPersonItem/>} />
			<Route path="/ChangePassword" element = {<AuthChangePW/>} />
        </Routes>
      </Router>
  );
}

export default App;
