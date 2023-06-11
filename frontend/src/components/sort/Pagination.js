import PT from 'prop-types';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Menu } from 'semantic-ui-react';
import Scroll from '../scroll/Scroll';
const propTypes = {
  page: PT.number,
  currentPage: PT.number
}

class Pagination extends Component {

  constructor(props) {
    super(props);
    this.handleItemClick = this.handleItemClick.bind(this);
  }

  handleItemClick(){
    Scroll(290,300);
    window.location.reload([false])
  }

  render(){
    const {
      page,
      currentPage
    } = this.props;

    console.log("page"+page);
    var discurrentPage=currentPage-10;
    if(discurrentPage<0)
    {
      discurrentPage=0;
    }
    let itemsPagination = new Array();

    for (var i = 0; i < page; i++) {
      const val = window.location.pathname.match('[^/]+(?!.*/)')[0];
      const link = (<Link to={'#'+(i+1)}>{i+1}</Link>)
      // const link = () =>{
      //   const pVal= window.location.hash;
      //   const pval = pVal.split('#')[1];
      //   window.location.href = "/0"+
      // }
      itemsPagination.push(
        <Menu.Item
          as='li'
          key={i}
          className={'page-item' + ' ' + `${i+1 === currentPage?'selected':''}`}
          children={link}
          onClick={this.handleItemClick}
        />
      )
    }

    return(
      <Menu as='ul' className='page-bar' pagination>
        {itemsPagination.slice(discurrentPage,currentPage+13)}
      </Menu>
    )
  }
}

Pagination.propTypes = propTypes;

export default Pagination;