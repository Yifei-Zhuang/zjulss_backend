import React, { Component } from 'react';
import { NavLink } from 'react-router-dom';
import { Menu } from 'semantic-ui-react';
import './HeaderMenu.css';
const sortENUM = {
        "笔记本":0,
        "二手家具":1,
        "二手手机":2,
        "家居日用":3,
        "家用电器":4,
        "乐器/运动":5,
        "门票卡券":6,
        "母婴用品":7,
        "平板电脑":8,
        "手机配件":9,
        "数码产品":10,
        "台式电脑":11,
        "箱包服饰":12,
        "照相机":13,
        "其他":14
    }
const dataHeaderMenu1 = ["笔记本",
        "二手家具",
        "二手手机",
        "家居日用",
        "家用电器",
        "乐器/运动",
        "门票卡券",
        "母婴用品"];
const dataHeaderMenu2 = [
        "平板电脑",
        "手机配件",
        "数码产品",
        "台式电脑",
        "箱包服饰",
        "照相机",
        "其他"];
class HeaderNavigation extends Component {

  constructor(props) {
    super(props);
    this.state = {
      activeItem: 'home'
    }
    this.handleItemClick = this.handleItemClick.bind(this);
  }

  handleItemClick(e, { name }){
    this.setState({ activeItem: name })
  }

  render() {

    const { activeItem } = this.state;

    const { handleItemClick } = this;

    let listMenu1 = dataHeaderMenu1.map(function(item,index){
      return(
        <Menu.Item
          as='span'
          key={index}
          className="nav-item"
          active={activeItem === item}
          onClick={handleItemClick} >
          <NavLink
            to={'/' + sortENUM[item]}
            activeClassName="active"
            >
            {item.toUpperCase()}
          </NavLink>
        </Menu.Item>
      )
    })
    let listMenu2 = dataHeaderMenu2.map(function(item,index){
      return(
        <Menu.Item
          as='span'
          key={index}
          className="nav-item"
          active={activeItem === item}
          onClick={handleItemClick} >
          <NavLink
            to={'/' + sortENUM[item]}
            activeClassName="active"
            >
            {item.toUpperCase()}
          </NavLink>
        </Menu.Item>
      )
    })
    return (
      <div><Menu as='nav' secondary id="header-nav1">
        {listMenu1}
      </Menu>
      <Menu  breaklines="true"  as='nav' secondary id="header-nav2">
        {listMenu2}
      </Menu></div>
    )
  }
}

export default HeaderNavigation;