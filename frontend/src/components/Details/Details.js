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
    const [adding, setAdding] = useState(false);
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

    async function addGoods() {
        if (navigator.onLine) {
            Promise.all([
                agent.Cart.addToCart(goodInfo.id)
            ]).then(results => {
                const allSucceeded = results.every(result => result.result === 1);
                if (allSucceeded) {
                    alert("加入购物车成功！");
                } else {
                    alert("加入购物车失败！");
                }
                setAdding(false)
            }).catch(error => {
                alert("加入购物车失败！");
            });
        } else {
            alert("网络连接异常，请检查网络设置！");
        }
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
                            <div>商品成色：{goodInfo.level}成新</div>
                            <div>商品描述：{goodInfo.remark}</div>
                            {/*<div>商品类别：{sortENUM[goodInfo.sort]}</div>*/}
                            <div>交易方式：{transENUM[goodInfo.transaction]}</div>
                            <div>商品数量：{goodInfo.count}</div>
                            <div>商品销量：{goodInfo.sales}</div>
                            <div>商品价格：{goodInfo.price.toFixed(2)}元</div>
                            {/*<div>信息更新时间：{goodInfo.modify}</div>*/}
                            <button className="button" onClick={addGoods}>
                                        加入购物车
                            </button>
                        </span>
                    </div>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Details