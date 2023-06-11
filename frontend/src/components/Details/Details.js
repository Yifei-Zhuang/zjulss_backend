import { Typography, Avatar, Grid, makeStyles, Box, Card, CardContent, CardMedia, Button, TextField } from '@material-ui/core'
import React, { useEffect, useState } from 'react'
import agent from "../../agent"
import './Details.css';
import { forEach } from "react-bootstrap/ElementChildren"


const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: '#F5F5F5',
        padding: theme.spacing(2)
    }
}))

function Details () {
    const classes = useStyles()
    const [goodInfo, setGoodInfo] = useState([])
    const [adding, setAdding] = useState(false);
    const [details, setDetails] = useState(false);
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
        0: "笔记本",
        1: "二手家具",
        2: "二手手机",
        3: "家居日用",
        4: "家用电器",
        5: "乐器/运动",
        6: "门票卡券",
        7: "母婴用品",
        8: "平板电脑",
        9: "手机配件",
        10: "数码产品",
        11: "台式电脑",
        12: "箱包服饰",
        13: "照相机",
        14: "其他"
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

    async function addComments() {
        if (navigator.onLine) {
            var contents = document.getElementById("content_area").value;
            alert(contents)
            Promise.all([
                agent.Good.addComment(goodInfo.id, contents)
            ]).then(results => {
                const allSucceeded = results.every(result => result.result === 1);
                if (allSucceeded) {
                    alert("评论成功！");
                } else {
                    alert("评论失败");
                }
            }).catch(error => {
                alert("评论失败");
            });
        } else {
            alert("网络连接异常，请检查网络设置！");
        }
    }

    async function showDetails() {
        setDetails(true);
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
                            <div>
                                {details ?
                                <></>
                                    :
                                    <button className="button" onClick={showDetails}>
                                        显示详情
                                    </button>
                                }
                            </div>
                            <div>
                                {details ?
                                    <>
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
                                            <button className="button" onClick={addGoods}>
                                                        加入购物车
                                            </button>
                                                            {/*<div>卖家id：{goodInfo.uid}</div>*/}
                                        </span>
                                        {/*<div className="comment">*/}
                                        {/*    <textarea*/}
                                        {/*        id="content_area"*/}
                                        {/*        cols="50"*/}
                                        {/*        rows="8"*/}
                                        {/*        placeholder="请输入评论内容"*/}
                                        {/*    />*/}
                                        {/*    <br />*/}
                                        {/*    <button className="button" onClick={addComments}>*/}
                                        {/*        发表评论*/}
                                        {/*    </button>*/}
                                        {/*</div>*/}
                                    </>
                                    :
                                    <>
                                    </>
                                }
                            </div>
                        </span>
                    </div>
                </Grid>
            </Grid>
        </Box>
    );
}

export default Details
