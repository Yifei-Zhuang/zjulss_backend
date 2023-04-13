import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import agent from '../../agent.js'
import logo from '../../logo.svg'
import {AppBar, Badge, IconButton, Toolbar, Typography} from "@material-ui/core";

export default function Header() {
    return (
        <AppBar position="static" style={{backgroundColor: '#eee', borderBottom: '1px solid #ccc', boxShadow: 'none'}}>
            <Toolbar  style={{color: '#555'}}>
                <Link to="/" className="nav-link">
                <img src="https://static.itch.io/images/itchio-textless-black.svg" alt="logo"
                     style={{width: 'auto', height: '30px', marginRight: '10px'}}/>
                </Link>
                <Typography variant="h6" >Second-hand trading platform</Typography>
                <div style={{flexGrow: 1}}></div>
                    <Link to="/Profile" className="nav-link">
                        <img src="https://ts1.cn.mm.bing.net/th/id/R-C.0de849455e8b26a86ed20c7ede549de0?rik=6qgpqRjH9ZfUFA&riu=http%3a%2f%2fcdn.onlinewebfonts.com%2fsvg%2fimg_266351.png&ehk=MGAevLueHXm4kBUecqJpFIory4vHyHTATf4kHUQsOFM%3d&risl=&pid=ImgRaw&r=0" alt="personalInfo"  style={{width: 'auto', height: '30px', marginRight: '10px'}}/>
                    </Link>
            </Toolbar>
        </AppBar>
    )

}