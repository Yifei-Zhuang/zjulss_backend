import { Typography, Avatar, Grid, makeStyles, Box, Card, CardContent, CardMedia, Button, TextField } from '@material-ui/core'
import React, { useEffect, useState } from 'react'
import agent from "../../agent"
import './Details.css';
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
    },
    name: {
        fontWeight: 'bold',
        backgroundColor: '#F5F5F5',
        margin: 60


    },
    cardMedia: {

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
                    <div className="goods-box">
                        <span className="goods-image">
                            <div className="goods-name">{goodInfo.name}</div>
                            <CardMedia
                                component="img"
                                height="350"
                                image={goodInfo.image}
                                style={{}}
                                onError={(e) => {
                                    e.target.src = "https://api.dujin.org/bing/1366.php";
                                }}
                            />
                        </span>
                        <span className="goods-desc">
                            <div>商品信息更新时间：{goodInfo.modify}</div>
                            <div>商品成色：{goodInfo.level}</div>
                            <div>商品描述：{goodInfo.remark}</div>
                            <div>商品价格：{goodInfo.price}</div>
                            <div>商品类别：{goodInfo.sort}</div>
                            <div>商品数量：{goodInfo.count}</div>
                            <div>商品状态：{goodInfo.display}</div>
                            <div>交易方式：{goodInfo.transaction}</div>
                            <div>商品销量：{goodInfo.sales}</div>
                            <div>卖家id：{goodInfo.uid}</div>
                        </span>


                    </div>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Details