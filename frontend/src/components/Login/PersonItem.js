import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import agent from '../../agent.js'
import logo from '../../logo.svg'
import Header from "../Header/Header";
import {
    AppBar,
    Badge,
    Box,
    Button,
    Card, CardMedia,
    Container,
    Grid,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Tab,
    Tabs,
    Toolbar,
    Typography,

} from '@material-ui/core';

const ItemInfo = ({item}) => {
    return (
        <Card>
            <Box display="flex">
                <CardMedia
                    component="img"
                    height="300"
                    image={item.image}
                    style={{objectFit: "contain"}}
                    onError={(e) => {
                        e.target.src = "https://api.dujin.org/bing/1366.php";
                    }}
                />
                <Box p={2} flexGrow={1}>
                    <Typography variant="h5">名称：{item.name}</Typography>
                    <Typography variant="subtitle1">价格：{item.price}</Typography>
                    <Typography variant="subtitle1">详情：{item.remark}</Typography>
                    <Typography variant="subtitle1">类别：{item.sort}</Typography>
                    <Typography variant="subtitle1">数量：{item.count}</Typography>
                    <Typography variant="subtitle1">交易方式：{item.transaction}</Typography>

                </Box>
            </Box>
        </Card>
    )
}

const BuyingItems = ({items}) => {
    return (
        <Box mt={3}>
            <Typography variant="h4">个人求购的商品</Typography>
            <hr/>
            {items.length > 0 ? (
                <Grid container spacing={3}>
                    {items.map(item => (
                        <Grid item xs={12} key={item.id}>
                            <ItemInfo item={item}/>
                        </Grid>
                    ))}
                </Grid>
            ) : ("空空如也。")
            }
        </Box>
    );
};

const SellingItems = ({items}) => {
    return (
        <Box mt={3}>
            <Typography variant="h4">个人出售的商品</Typography>
            <hr/>
            {items.length > 0 ? (
                <Grid container spacing={3}>
                    {items.map(item => (
                        <Grid item xs={12} key={item.id}>
                            <ItemInfo item={item}/>
                        </Grid>
                    ))}
                </Grid>
            ) : ("空空如也。")
            }
        </Box>
    );
};

const CartItems = ({items, dic}) => {
    return (
        <Box mt={3}>
            <Typography variant="h4">购物车</Typography>
            <hr/>
            {items.length > 0 ? (
                <Grid container spacing={7}>
                    {items.map(item => (
                        <Grid item xs={12} key={item.id} className={"border-dark"}>
                            <Card>
                                {dic[item.qid] != null ?
                                    <ItemInfo item={dic[item.qid]}/>
                                    :
                                    "商品不存在 !"
                                }
                                <Box display="flex">
                                    <Typography variant="subtitle1">数量：{item.quantity}</Typography>
                                    <Typography variant="subtitle1">收货地址：{item.address}</Typography>
                                </Box>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            ) : ("空空如也。")
            }
        </Box>
    );
};


const PersonItem = () => {
    const [buyingItems, setBuyingItems] = useState([]);
    const [sellingItems, setSellingItems] = useState([]);
    const [cartItems, setCartItems] = useState([]);
    const [goodsDict, setGoodsDict] = useState({});
    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const fetchData = async () => {
        const [buyingItems, sellingItems, cartItems] = await Promise.all([
            agent.Profile.getBuy(),
            agent.Profile.getSell(),
            agent.Profile.getCart()
        ]);
        setBuyingItems(buyingItems);
        setSellingItems(sellingItems);
        setCartItems(cartItems);

        const goodsDict = {};
        for (const item of cartItems) {
            //注意这个有个qid
            const id = item.qid;
            if (id in goodsDict) continue
            const goodDetail = await agent.Good.getGoodDetail(id);
            goodsDict[id] = goodDetail;
        }
        setGoodsDict(goodsDict);

        console.log(buyingItems)
        console.log(sellingItems)
        console.log(cartItems)
        console.log(goodsDict)
    };

    useEffect(() => {
        fetchData();
    }, []);


    return (
        <div>
            <Container style={{marginTop: "20px"}}>
                <Box marginTop={34}>
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={2}>

                            <List component="nav">
                                <ListItem
                                    button
                                    selected={value === 0}
                                    onClick={() => handleChange(null, 0)}
                                >
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary="个人求购的商品"/>
                                    <Badge color="primary" badgeContent={buyingItems.length}/>
                                </ListItem>
                                <ListItem
                                    button
                                    selected={value === 1}
                                    onClick={() => handleChange(null, 1)}
                                >
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary="个人出售的商品"/>
                                    <Badge color="primary" badgeContent={sellingItems.length}/>
                                </ListItem>
                                <ListItem
                                    button
                                    selected={value === 2}
                                    onClick={() => handleChange(null, 2)}
                                >
                                    <ListItemIcon></ListItemIcon>
                                    <ListItemText primary="购物车"/>
                                    <Badge color="primary" badgeContent={cartItems.length}/>
                                </ListItem>
                            </List>
                        </Grid>
                        <Grid item xs={12} md={7}>
                            <TabPanel value={value} index={0}>
                                <BuyingItems items={buyingItems}/>
                            </TabPanel>
                            <TabPanel value={value} index={1}>
                                <SellingItems items={sellingItems}/>
                            </TabPanel>
                            <TabPanel value={value} index={2}>
                                <CartItems items={cartItems} dic={goodsDict}/>
                            </TabPanel>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </div>
    );
};
const TabPanel = ({children, value, index}) => {
    return (
        <div hidden={value !== index}>
            {value === index && (
                <Box>
                    {children}
                </Box>
            )}
        </div>
    );
};

export default PersonItem;