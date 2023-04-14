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
    Card,
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


const BuyingItems = ({items}) => {
    return (
        <Box mt={3}>
            <Typography variant="h4">个人求购的商品</Typography>
            <hr/>
            {items.length > 0 ? (
                <Grid container spacing={3}>
                    {items.map(item => (
                        <Grid item xs={12} key={item.id}>
                            <Card>
                                <Box p={2}>
                                    <Typography variant="h5">{item.name}</Typography>
                                    <Typography variant="subtitle1">价格：{item.price}</Typography>
                                    <Button variant="contained" color="primary" onClick={() => {
                                    }}>购买</Button>
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

const SellingItems = ({items}) => {
    return (
        <Box mt={3}>
            <Typography variant="h4">个人出售的商品</Typography>
            <hr/>
            {items.length > 0 ? (
                <Grid container spacing={3}>
                    {items.map(item => (
                        <Grid item xs={12} key={item.id}>
                            <Card>
                                <Box p={2}>
                                    <Typography variant="h5">{item.name}</Typography>
                                    <Typography variant="subtitle1">价格：{item.price}</Typography>
                                    <Button variant="contained" color="primary" onClick={() => {
                                    }}>购买</Button>
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

const CartItems = ({items}) => {
    return (
        <Box mt={3}>
            <Typography variant="h4">购物车</Typography>
            <hr/>
            {items.length > 0 ? (
                <Grid container spacing={3}>
                    {items.map(item => (
                        <Grid item xs={12} key={item.id}>
                            <Card>
                                <Box p={2}>
                                    <Typography variant="h5">{item.name}</Typography>
                                    <Typography variant="subtitle1">价格：{item.price}</Typography>
                                    <Button variant="contained" color="primary" onClick={() => {
                                    }}>购买</Button>
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

const PersonItem = () =>{
    const [buyingItems, setBuyingItems] = useState([]);
    const [sellingItems, setSellingItems] = useState([]);
    const [cartItems, setCartItems] = useState([]);

    const [value, setValue] = useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    useEffect(() => {
        const fetchData = async () => {
            const [buyingItems, sellingItems, cartItems] = await Promise.all([
                agent.Profile.getBuy(),
                agent.Profile.getSell(),
                agent.Profile.getCart()
            ]);
            setBuyingItems(buyingItems);
            setSellingItems(sellingItems);
            setCartItems(cartItems);
            console.log(buyingItems)
            console.log(sellingItems)
            console.log(cartItems)
        };
        fetchData();
    }, []);


    return (
        <div>
            <Container style={{marginTop: "20px"}}>
                <Box marginTop={34}>
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={2}>
                            <Box component="img" src={logo} alt="logo" mb={2}/>
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
                                    <ListItemText primary="个人出售的商品" />
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
                                <CartItems items={cartItems}/>
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