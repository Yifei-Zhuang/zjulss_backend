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

    const transENUM = {
        1: "邮寄",
        2: "自提",
        3: "当面交易",
    }

    const sortENUM = {
        1: "电子产品",
        2: "服饰",
        3: "书籍",
        4: "票类",
        5: "食品",
        6: "日用品",
        7: "其他",
    }

    const displayENUM = {
        1: "在售",
        0: "下架",
    }

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
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品成色：
                                </span>
                                <span className="goods-desc-data">
                                    {goodInfo.level}成新
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品描述：
                                </span>
                                <span className="goods-desc-data">
                                    {goodInfo.remark}
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品类别：
                                </span>
                                <span className="goods-desc-data">
                                    {sortENUM[goodInfo.sort]}
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    交易方式：
                                </span>
                                <span className="goods-desc-data">
                                    {transENUM[goodInfo.transaction]}
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品数量：
                                </span>
                                <span className="goods-desc-data">
                                    {goodInfo.count}
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品销量：
                                </span>
                                <span className="goods-desc-data">
                                    {goodInfo.sales}
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品状态：
                                </span>
                                <span className="goods-desc-data">
                                    {displayENUM[goodInfo.display]}
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    商品价格：
                                </span>
                                <span className="goods-desc-data">
                                    {goodInfo.price.toFixed(2)}元
                                </span>
                            </div>
                            <div className="goods-desc-box">
                                <span className="goods-desc-name">
                                    信息更新时间：
                                </span>
                                <span className="goods-desc-data">
                                    {goodInfo.modify}
                                </span>
                            </div>
                            {/*<div>卖家id：{goodInfo.uid}</div>*/}
                        </span>
                    </div>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Details