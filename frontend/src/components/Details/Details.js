import { Typography, Avatar, Grid, makeStyles, Box, Card, CardContent, Button, TextField } from '@material-ui/core'
import React, { useEffect, useState } from 'react'
import agent from "../../agent"
import { forEach } from "react-bootstrap/ElementChildren"

const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: '#F5F5F5',
        padding: theme.spacing(2)
    },
    title: {
        fontWeight: 'bold',
        marginBottom: theme.spacing(2)
    },
    avatar: {
        width: theme.spacing(20),
        height: theme.spacing(20),
        margin: 'auto'
    },
    info: {
        border: '1px solid #ccc',
        padding: theme.spacing(2),
        borderRadius: theme.spacing(1)
    }

}))

function Details () {
    const classes = useStyles()
    const [goodInfo, setGoodInfo] = useState([])
    const fetchData = async () => {
        const info = await agent.Good.getGoodDetail(3);
        console.log(info.msg)
        setGoodInfo(info)
        console.log(goodInfo)
    }

    useEffect(() => {
        fetchData()
    }, [])

    return (
        <Box className={classes.root} >
            <Grid container spacing={3} >
                <Grid item xs={12} className={classes.textCenter} >
                    <div class="goods-detail">
                        <h2>{goodInfo.name}</h2>
                    </div>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Details