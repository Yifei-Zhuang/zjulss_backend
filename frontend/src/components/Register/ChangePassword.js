import React, { useState } from "react";
import "./ChangePassword.css";
import agent from '../../agent';

const API_ROOT = "http://10.214.241.122:8080"

const ChangePassword = () => {
	const [code, setCode] = useState("");
	const [newPassword, setNewPassword] = useState("");
	const [confirmPassword, setConfirmPassword] = useState("");
	const [error, setError] = useState("");

	const handleFormSubmit = (e) => {
		e.preventDefault();
		try {
			if (confirmPassword != newPassword) {
				setError("两次输入的密码不一致");
				return;
			} else {
				const response = agent.Auth.changePassword(code,newPassword)
				if(response.result != 1)
					setError("密码修改失败")
			}
			console.log("密码修改成功");
		}
		catch {
			console.log("Error in PW-Reset")
		}
		// 密码验证逻辑
		
	};

	return (
		<div className="change-password-container">
			<div className="change-password-box">
				<h1 className="change-password-title">密码修改</h1>
				<form className="change-password-form" onSubmit={handleFormSubmit}>
					<input
						type=""
						className="change-password-input"
						placeholder="Code"
						value={code}
						onChange={(e) => setCode(e.target.value)}
					/>
					<input
						type="password"
						className="change-password-input"
						placeholder="新密码"
						value={newPassword}
						onChange={(e) => setNewPassword(e.target.value)}
					/>
					<input
						type="password"
						className="change-password-input"
						placeholder="确认新密码"
						value={confirmPassword}
						onChange={(e) => setConfirmPassword(e.target.value)}
					/>
					<button type="submit" className="change-password-button">
						修改密码
					</button>
					{error && <p className="change-password-error">{error}</p>}
				</form>
			</div>
		</div>
	);
};

export default ChangePassword;
