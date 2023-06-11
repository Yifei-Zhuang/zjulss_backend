import React from 'react';
import { Link } from 'react-router-dom';
import { Card, Image } from 'semantic-ui-react';
// let propTypes = {
//   id: PropTypes.string,
//   category: PropTypes.string,
//   type: PropTypes.string,
//   name: PropTypes.string,
//   price: PropTypes.objectOf(PropTypes.number),
//   images: PropTypes.object
// }

const ProductItem = (props) => {

   let {
    id,
    name,
    level,
    remark,
    sort,
    count,
    display,
    transaction,
    sales,
    uid,
    price,
    image
  } = props;

    return(
    <Card className='product-info'>
      <Link to={'/Details?id='+id}><Image className='pro-images' src={image} /></Link><Card.Content className='pro-info'>
        <Card.Header as='h4'><Link to={'/Details?id='+id}>{name}</Link></Card.Header><Card.Meta><span className='sale-price'>${price}</span></Card.Meta>
      </Card.Content>
    </Card>
  )
}

// ProductItem.propTypes = propTypes;

export default ProductItem;