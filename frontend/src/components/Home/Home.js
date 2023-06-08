import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import agent from '../../agent.js'


const Home = () => {
    return (
        // <>test home</>
        <ul>
            <li><Link to='/Details?id=14289'>Details</Link></li>
        </ul>
    )
}
export default Home