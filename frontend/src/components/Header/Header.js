import { Button, Popover, TextField } from "@material-ui/core";
import AppBar from '@material-ui/core/AppBar';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import "../../agent";
import SearchBar from "../Search/SearchBar";
const isDebug = process.env.REACT_APP_DEBUG_MODE === "true";


const TokenSet = () => {
    const [tokenView, setTokenView] = useState(null);

    const handleMenuClick = (event) => {
        setTokenView(event.currentTarget);
    };

    const handleMenuClose = () => {
        setTokenView(null);
    };


    //__________DEBUG ONLY___________
    const [tokenValue, setTokenValue] = useState(localStorage.getItem('token') || '')

    return (
        <React.Fragment>
            <TextField
                id="token-input"
                value={tokenValue}
                label="Token"
                variant="outlined"
                onChange={(event) => setTokenValue(event.target.value)}
            />
            <Button onClick={() => {
                localStorage.setItem("token", tokenValue)
            }}>Set Token</Button>
            <Button onClick={handleMenuClick}>Show Token</Button>
            <Popover
                anchorEl={tokenView}
                open={Boolean(tokenView)}
                onClose={handleMenuClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
            ><p>{localStorage.getItem("token")}</p>
            </Popover>
        </React.Fragment>
    )
}


function Header() {
    const [rightMenu, setrightMenu] = useState(null);

    const handleMenuClick = (event) => {
        setrightMenu(event.currentTarget);
    };

    const handleMenuClose = () => {
        setrightMenu(null);
    };

    const handleLogout = () => {
        if (!localStorage.getItem("token")){
            alert("未登录")
            return
        }
        localStorage.removeItem("token")
        alert("当前成功登出")
    }

    return (
        <AppBar position="static" style={{backgroundColor: '#eee',borderBottom: '1px solid #ccc', boxShadow: 'none'}}>
            <Toolbar style={{color: '#555' }}>
                <Link to="/" className="nav-link">
                    <img src="https://static.itch.io/images/itchio-textless-black.svg" alt="logo"
                         style={{width: 'auto', height: '30px', marginRight: '10px'}}/>
                </Link>

                <Typography variant="h6">Second-hand trading platform</Typography>
                {/* <Search style={{marginLeft: '20px'}}/> */}
                <div style={{flexGrow: 1}}/>
                {/*<TokenSet></TokenSet>*/}
                <SearchBar style={{marginLeft: '10px'}}></SearchBar>
                <div>
                    <IconButton onClick={handleMenuClick} edge="end">
                        <img src="https://picx.zhimg.com/v2-abed1a8c04700ba7d72b45195223e0ff_l.jpg?source=172ae18b"
                             alt="personalInfo" style={{width: 'auto', height: '30px', marginRight: '10px'}}/>
                    </IconButton>
                    <Popover
                        anchorEl={rightMenu}
                        open={Boolean(rightMenu)}
                        onClose={handleMenuClose}
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'center',
                        }}

                    >
                        <MenuItem component={Link} to='/Profile'>个人信息</MenuItem>
                        <MenuItem component={Link} to='/PersonItem'>个人交易</MenuItem>
                        <MenuItem component={Link} to='/ReleaseWanted'>发布求购</MenuItem>
                        <MenuItem component={Link} to='/ReleaseSale'>发布出售</MenuItem>
                        <MenuItem component={Link} to='/Login'>登录</MenuItem>
                        <MenuItem component={Link} to='/Register'>注册</MenuItem>
                        <MenuItem component={Link} onClick={handleLogout} to='/Login'>登出</MenuItem>
                    </Popover>
                </div>
            </Toolbar>
        </AppBar>
    );
}

export default Header;
