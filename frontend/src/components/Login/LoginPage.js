import React, { useState } from 'react';
import './LoginPage.css';
import agent from '../../agent';

const API_ROOT = "http://10.214.241.122:8080";
const TestURL = "http://localhost:3000"


const LoginPage = () => {
	const [phoneNumber, setPhoneNumber] = useState('');
	const [password, setPassword] = useState('');
	const [errorMessage, setErrorMessage] = useState("");

	const handleLogin = async () => {
		try {
			const response = await agent.Auth.login(password, phoneNumber)
			console.log(response)
			if (response.result === 1) {
				// agent.setToken(response.msg)
				// 登录成功后进行页面跳转
				localStorage.setItem('token', `${response.msg}`);
				setErrorMessage("登陆成功");

				// const userInfo = await agent.Profile.getUserInfo()
				// console.log(userInfo)
				// 重定向到受保护的路由
				window.location.href = '/Profile';

			} else {
				setErrorMessage("登录失败，请检查用户名和密码");
			}
		} catch (error) {
			console.error(error);
			setErrorMessage("登录失败，请稍后重试");
		}
	};

	const handleRegister = () => {
		console.log("转至注册页面")
		window.location.href = TestURL + "/Register";
	}

	return (
		<div className="login-container">
			<div className="login-box">
				<img src="../../public/logo512.png" alt="Logo" className="login-logo" />
				<h1 className="login-title">用户登录</h1>
				<div className="login-form">
					<input
						type="text"
						className="login-input"
						placeholder="手机号"
						value={phoneNumber}
						onChange={(e) => setPhoneNumber(e.target.value)}
					/>
					<input
						type="password"
						className="login-input"
						placeholder="密码"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
					/>

					<button type='button' className="login-button" onClick={handleLogin}>
						登录
					</button>
					<button type='button' className="register-button" onClick={handleRegister}>
						注册
					</button>
					<div className="login-error">
						{errorMessage && (
							<p className="login-error">{errorMessage}</p>
						)}</div>
				</div>
			</div>
		</div>
	);
};

export default LoginPage;
