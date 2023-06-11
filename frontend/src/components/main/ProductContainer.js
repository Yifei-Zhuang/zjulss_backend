import PropTypes from 'prop-types';
import { default as React } from 'react';
import { Dropdown, Header } from 'semantic-ui-react';
import './main.css';
import './page.css';
import './product.css';
import ProductList from './ProductList';
const propTypes = {
  dataProducts: PropTypes.array,
  showcaseHeader: PropTypes.string
}
// Product Filter 商品排序
const options = [
  { text: 'Best Match', value: 'Best Match' },
  { text: 'Lowest Price', value: 'Lowest Price' },
  { text: 'Highest Price', value: 'Highest Price' }
];

class ProductContainer extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      filter: 'Best Match'
    }
    this.handleSelectSort = this.handleSelectSort.bind(this);
    this.filterHighestPrice = this.filterHighestPrice.bind(this);
    this.filterLowestPrice = this.filterLowestPrice.bind(this);
  }

  handleSelectSort(e, { value }){
    this.setState({filter: value})
  }

  filterHighestPrice(arr){
   return arr.length <= 1 ? arr : this.filterHighestPrice(arr.slice(1).filter(item => item.price >= arr[0].price)).concat(arr[0], this.filterHighestPrice(arr.slice(1).filter(item => item.price < arr[0].price)));
  }

  filterLowestPrice(arr){
    // 快速排序 Quick Sort
    return arr.length <= 1 ? arr : this.filterLowestPrice(arr.slice(1).filter(item => item.price <= arr[0].price)).concat(arr[0], this.filterLowestPrice(arr.slice(1).filter(item => item.price > arr[0].price)));
  }

  render() {
    const {
      pagee,
      location,
      dataProducts,
      showcaseHeader
    } = this.props;

    const {
      filter
    } = this.state;
		const searchVa = window.location.search;
    const searchVal = searchVa.substring(1);
    const link = () => {
    const pageee=document.getElementById('page-input').value 
		console.log("转至注册页面")
		const Val= window.location.pathname.match('[^/]+(?!.*/)');
    const val = Val.input.split('/')[1];
        console.log(Val);
        if(val === "search"){
            const searchVa = window.location.search;
            window.location.href = '/search'+searchVa+"#" + pageee;}
        else{
            const val = Val.input.split('/')[1];
            const searchVa = window.location.search;
            window.location.href = '/'+val+'/search'+searchVa+"#" + pageee;}
    }
    const {
      handleSelectSort
    } = this;

    // Total Quantity of Products 商品总数量
    let itemsTotal = dataProducts.length;
    console.log(dataProducts.length);
    // Total Pages 总页码
    // const [phoneNumber, setPhoneNumber] = useState('');
    const currentPage = this.props.location.hash?this.props.location.hash.substr(1)*1:1;
    let dataDisplay = new Array()

    switch(filter){
      case 'Best Match':
        dataDisplay = dataProducts;
        console.log(dataDisplay);
        break;
      case 'Lowest Price':
        dataDisplay = this.filterLowestPrice(dataProducts.slice(0))
        console.log(dataDisplay);
        break;
      case 'Highest Price':
        dataDisplay = this.filterHighestPrice(dataProducts.slice(0))
        break;
    }
    return (
      <div id='product-showcase'>
        <Header as='h2' textAlign='left'  charset='' content={showcaseHeader}/><div className='pro-filter-bar'><Dropdown className='filter-menu' defaultValue='Best Match' options={options} selection item onChange={handleSelectSort}/></div>
        <ProductList dataProducts={dataDisplay}/>
      </div>
    )
  }
}

ProductContainer.propTypes = propTypes;

export default ProductContainer;