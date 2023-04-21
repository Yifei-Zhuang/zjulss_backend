import React, { useState } from "react";
import "./Register.css";
import axios from 'axios';

const API_ROOT = "http://10.214.241.122:8080";

const Register = () => {
	const [userName, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [phoneNumber, setPhoneNumber] = useState("");
	const [code, setcode] = useState("");
	const [errorMessage, setErrorMessage] = useState("");
	const [isCodeSent, setIsCodeSent] = useState(false);
	//大陆用户手机号正则表达式
	const phoneNumRegex = /^1(3\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\d|9[0-35-9])\d{8}$/;
	//密码正则限制
	const PWRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,20}$/;
	const RegisteredMsg = "you may call this interface too frequently";

	const handleSendcode = async () => {
		if (phoneNumber === "") {
			setErrorMessage("请输入手机号")
			return
		}
		try {
			// 发送手机验证码的接口调用
			// 假设发送成功后，将 isCodeSent 置为 true，表示验证码已发送
			const response = await axios.get(API_ROOT + "/code/send/" + phoneNumber)
			console.log(response.data)
			if (RegisteredMsg===response.data.msg)
				setErrorMessage("该手机号已注册")
			else
				setErrorMessage("验证码已发送");
			setIsCodeSent(true);
		} catch (error) {
			console.error(error);
			setErrorMessage("发送验证码失败，请稍后重试");
		}
	};

	const handleRegister = async () => {
		try {
			// 进行用户名、密码和手机号的格式验证（密码验证未做）
			if (!userName) {
				setErrorMessage("请输入用户名");
				return
			}
			if (!password || !PWRegex.test(password)) {
				setErrorMessage("请输入正确格式的密码,6到20字符,至少包含1个英文字母和数字")
				return
			}
			if (!phoneNumRegex.test(phoneNumber)) {
				setErrorMessage("请输入正确的中国大陆手机号");
				return
			}
			// 校验验证码
			if (!code) {
				setErrorMessage("请输入验证码");
				return;
			}

			// 调用后端接口进行用户注册
			const response = await axios.post(API_ROOT + "/user/register", {
				userName: userName,
				password: password,
				phoneNumber: phoneNumber,
				code: code,
			});

			// 判断注册是否成功
			if (response.status === 200) {
				// 注册成功后进行页面跳转
				alert("注册成功")
				window.location.href = "./login";
			} else {
				setErrorMessage("注册失败，请稍后重试");
			}
		} catch (error) {
			console.error(error);
			setErrorMessage("注册失败，请稍后重试");
		}
	};

	const handlecodeChange = (e) => {
		setcode(e.target.value);
	};

	return (
		<div className="register-container">
			<div className="register-box">
				<h1 className="register-title">用户注册</h1>
				<form className="register-form" onSubmit={handleRegister}>
					<input
						type="text"
						className="register-input"
						placeholder="用户名"
						value={userName}
						onChange={(e) => setUsername(e.target.value)}
					/>
					<input
						type="password"
						className="register-input"
						placeholder="密码"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
					/>
					<input
						type="text"
						className="register-input"
						placeholder="手机号"
						value={phoneNumber}
						onChange={(e) => setPhoneNumber(e.target.value)}
					/>
					<div >
						<input
							type="text"
							className="register-input"
							placeholder="请输入手机验证码"
							value={code}
							onChange={handlecodeChange}
						/>
						<button type="button" className="codeSend-button" onClick={handleSendcode}>
							发送验证码
						</button>
					</div>

					<button type="submit" className="register-button">
						注册
					</button>
					{errorMessage && (
						<p className="register-error">{errorMessage}</p>
					)}
				</form>
			</div>
		</div>
	);
};

export default Register;
