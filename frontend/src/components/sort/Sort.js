import axios from 'axios';
import React, { Component } from 'react';
import { Grid, Loader, Segment } from 'semantic-ui-react';
import '../main/main.css';
import Scroll from '../scroll/Scroll';
import ProductContainer from './ProductContainer';

class Sort1 extends Component {

  constructor(props) {
    super(props);
    this.state = {
      dataProducts: [],
      isNotFound: false,
      totalpage:0
    }
  }

  componentDidMount() {
    const Val = window.location.pathname.match('[^/]+(?!.*/)')[0];
    const val = Val.split('/')[0];
    const pVal= window.location.hash;
    console.log(val);
    const pval = pVal.split('#')[1];
    let pval1=0;
    if(typeof(pval) != "undefined"){
      pval1 = pval;
    }
    console.log('????????');
    console.log(pval);
    const regSearch = new RegExp(val,'gim');
   axios
    .get('http://10.214.241.122:8080/good/getBySort?sort='+val+'&page='+pval1)
    .then(res => {
  console.log('-----------');
      console.log(res);
console.log(pval1);

console.log('++++++');
      const temp = new Array;
      const totalpage=res.data.total;
      console.log(totalpage);
      res.data.list.forEach(function(item,index){
        temp.push(item);
        // console.log(item);
//         console.log(item.image);
      })
if (!temp.length) {this.setState({isNotFound: true})}

      // temp.sort(function(){ return 0.5 - Math.random() })

      this.setState({dataProducts: temp})
      this.setState({totalpage: totalpage})
})
    .catch(err => console.log(err))

    Scroll(290,300);
  }


  render(){
    const {
      dataProducts,
      isNotFound,
      totalpage
    } = this.state;

    let contentDisplay = null;
    const Val = window.location.pathname.match('[^/]+(?!.*/)')[0];
    const val = Val.split('/')[0];
    const productsCon = (
            <ProductContainer
              location={window.location}
              dataProducts={dataProducts}
              totalpage={totalpage}
              // showcaseHeader={'SEARCH RESULTS FOR : "'+val+'"'}
            />
    )

    if (isNotFound) {
      contentDisplay = <Segment className='not-found-wrap'><h2>SORRY, WE DID NOT FIND ANY RESULT FOR YOUR SEARCH.</h2><p>Please check the spelling or try again with a less specific term</p></Segment>
    }else if (!dataProducts.length){
      contentDisplay = (<Segment className='loader-wrap'><Loader active size='massive'>Loading</Loader></Segment>)
    }else {
      contentDisplay = productsCon;
      // contentDisplay =
      //       <ProductContainer
      //         location={window.location}
      //         dataProducts={dataProducts}
      //         showcaseHeader={'SEARCH RESULTS FOR : "' + searchVal + '"'}
      //       />
    }

    return(
      <Grid as='section' textAlign='center' style={{width:'100%'}}>
        <Grid.Column width='15' id='search-wrap'>
          {contentDisplay}
        </Grid.Column>
      </Grid>
    )
  }
}
export default Sort1;
