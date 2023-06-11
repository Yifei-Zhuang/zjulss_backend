import PropTypes from 'prop-types';
import React from 'react';
import ProductItem from './ProductItem';
const propTypes = {
  dataProducts: PropTypes.arrayOf(PropTypes.object)
}

const ProductList = (props) => {

  const itemsProduct = props.dataProducts.map(function(item){
    return(
      <ProductItem
        key={item.id}
        id={item.id}
        sort={item.sort}
        type={item.type}
        name={item.name}
        price={item.price}
        image={item.image}
      />
    )
  })

  return(
    <div id='product-list'>
      {itemsProduct}
    </div>
  )
}

ProductList.propTypes = propTypes;

export default ProductList;