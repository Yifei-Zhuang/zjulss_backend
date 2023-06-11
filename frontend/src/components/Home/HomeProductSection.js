import axios from 'axios';
import React, { Component } from 'react';
import { Button, Grid } from 'semantic-ui-react';
import ShowcaseHeader from '../Header/ShowcaseHeader.js';
import '../main/product.css';
import ProductItem from '../main/ProductItem.js';
class HomeProductSection extends Component {

  constructor(props) {
    super(props);
    this.state = {
      dataHotSaleProduct: [{id:"Loading",category:"",type:"",name:"Loading",price:1,detail:{color:['red','blue'],size:['','','','',''],des:""},quantity:"",images:{imgProduct:"",imgModel:"",imgDetail:""}}],
      isReadMore: false
    }
    this.handleReadMoreProduct = this.handleReadMoreProduct.bind(this);
  }

  componentDidMount() {
    axios
    .get('http://10.214.241.122:8080/good/feed')
    .then(res => {
      this.setState({dataHotSaleProduct: res.data})
    })
    .catch(err => console.log(err))
  }
  

  handleReadMoreProduct(){
    this.setState({
      isReadMore: true
    })
  }

  render() {
    //  const fetchData = async () => {
    //     const [buyingItems, sellingItems, cartItems] = await Promise.all([
    //         agent.Profile.getBuy(),
    //         agent.Profile.getSell(),
    //         agent.Profile.getCart()
    //     ]);
    //     setBuyingItems(buyingItems);
    //     setSellingItems(sellingItems);
    //     setCartItems(cartItems);

    //     const goodsDict = {};
    //     for (const item of cartItems) {
    //         //注意这个有个qid
    //         const id = item.qid;
    //         if (id in goodsDict) continue
    //         const goodDetail = await agent.Good.getGoodDetail(id);
    //         goodsDict[id] = goodDetail;
    //     }
    //     setGoodsDict(goodsDict);

    //     console.log(buyingItems)
    //     console.log(sellingItems)
    //     console.log(cartItems)
    //     console.log(goodsDict)
    // };
    const {
      isReadMore,
      dataHotSaleProduct
    } = this.state;

    let itemsProductCard = dataHotSaleProduct.map(function(item){
      return(
        <ProductItem
          key={item.id}
          {...item}
        />
      )
    })

    const btnReadMore = (
      <Button
        className='load-more-btn'
        fluid
        onClick={this.handleReadMoreProduct}
      >
        READ MORE
      </Button>
    )

    let controllerReadMore = null ,itemsReadMoreProduct = null;

    return (
      <Grid id='hot-products' textAlign='center'>
        <Grid.Column width={15
        }>
          <ShowcaseHeader
            headerMain='FEATURE PRODUCTS'
            headerSub='Best Collection for You'
            iconHeader='gift'
          />
          <div id="product-list">
            {itemsProductCard}
            {isReadMore?itemsProductCard:''}
          </div>
          {!isReadMore?btnReadMore:''}
        </Grid.Column>
      </Grid>
    )
  }
}

export default HomeProductSection;