import axios from 'axios';
import React, { Component } from 'react';
import { Grid, Loader, Segment } from 'semantic-ui-react';
import Scroll from '../scroll/Scroll';
import './main.css';
import './page.css';
import './product.css';
import ProductContainer from './ProductContainer';
class SearchSite extends Component {

  constructor(props) {
    super(props);
    this.state = {
      dataProducts: [],
      isNotFound: false
    }
  }

  componentDidMount() {
    // const pageee=document.getElementById('page-input').value
    // this.setState({page: pageee})
    const temp = new Array;
    const searchVa = window.location.search;
    const searchVal = searchVa.substring(1);
    const regSearch = new RegExp(searchVal,'gim');
    const Val= window.location.pathname.match('[^/]+(?!.*/)');
    console.log(Val);
    const val = Val.input.split('/')[1];
    const uurl = window.location.hash;
    console.log(uurl);
    const pagee = uurl.split('#')[1];
    let page = 0;
    if(pagee == null){
    }
    else {page = pagee;}
    console.log(page);
    if(val!=='search'){
      // console.log('!!!!!!!!!!!!!!!!!!!!!!!!!');
      const regSort = new RegExp(val,'gim');
      axios
    .get('http://10.214.241.122:8080/good/name?name='+searchVal+'&sort='+val+'&page='+page+'&sortByPrice=false&searchSort=true')
    .then(res => {
      console.log(res);
      const temp = new Array;

      res.data.forEach(function(item,index){
        temp.push(item);
        // console.log(item);
        console.log(item.image);
      })
    
      if (!temp.length) {this.setState({isNotFound: true})}

      // temp.sort(function(){ return 0.5 - Math.random() })

      this.setState({dataProducts: temp})

    })
    .catch(err => console.log(err))
    console.log(temp.length)
    Scroll(290,300);
    }
    else{
      axios
    .get('http://10.214.241.122:8080/good/name?name='+searchVal+'&page='+page+'&sortByPrice=false&searchSort=false')
    .then(res => {
      console.log(res);
      const temp = new Array;

      res.data.forEach(function(item,index){
        temp.push(item);
        // console.log(item);
        console.log(item.image);
      })
    
      if (!temp.length) {this.setState({isNotFound: true})}

      // temp.sort(function(){ return 0.5 - Math.random() })

      this.setState({dataProducts: temp})

    })
    .catch(err => console.log(err))
    console.log(temp.length)
    Scroll(290,300);
    }
  }


  render(){
    const searchVa = window.location.search;
    const searchVal = searchVa.substring(1);
    const v = decodeURI(searchVal);
    const {
      page,
      dataProducts,
      isNotFound
    } = this.state;
    console.log(dataProducts.length);
    let contentDisplay = null;
    const searchVa1 = window.location.search;
    const searchVal1 = searchVa.substring(1);
    const link = () => {
    const pageee=document.getElementById('page-input').value;
		const Val= window.location.pathname.match('[^/]+(?!.*/)');
    const val = Val.input.split('/')[1];
        console.log(Val);
        if(val === "search"){
            console.log('############################');
            const searchVa = window.location.search;
            window.location.href = '/search'+searchVa1+"#" + pageee;
            window.location.reload([false]);
            console.log('############################');
          }
        else{
          console.log('############################');
            const val = Val.input.split('/')[1];
            const searchVa1 = window.location.search;
            window.location.href = '/'+val+'/search'+searchVa1+"#" + pageee;
            window.location.reload([false]);
            console.log('############################');
          }
    }
    const productsCon = (
      <div>
            {/* <div className='pro-filter-bar'> */}
          {/* <Dropdown className='filter-menu' defaultValue='Best Match' options={options} selection item onChange={handleSelectSort}/> */}
        {/* </div> */}

            <ProductContainer
              location={window.location}
              dataProducts={dataProducts}
              showcaseHeader={'SEARCH RESULTS FOR : "' + v + '"'}
            />
            <p className='page-num'>
            <strong>Turn to page:  </strong>
            <input
						type="text"
            className='page-input'
						id="page-input"
						placeholder="page"
            /><button type='button' className="page-button" onClick={link}>ok</button>
          </p>
            </div>
    )
    
    if (isNotFound) {
      contentDisplay = <Segment className='not-found-wrap'><h2>SORRY, THERE IS NO MORE RESULT FOR YOUR SEARCH "{searchVal}".</h2><p>Please check the spelling or try again with a less specific term</p></Segment>
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
export default SearchSite;
