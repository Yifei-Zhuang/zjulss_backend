import React, { Component } from 'react';
import Header from './Header';
import './header.css';
class HeaderToolWrapper extends Component {
  constructor(props) {
    super(props);
    this.state = {
      searchVal: ''
    }
    this.handleFixedMenu = this.handleFixedMenu.bind(this);
  }
  componentDidMount() {
    window.addEventListener('scroll',this.handleFixedMenu);
  }
  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleFixedMenu);
  }
  handleFixedMenu(){
    let menuBar = this.refs.headerTool;
    let menuBarTop = menuBar.offsetTop;
    // 滚动滚动条当菜单贴到顶部的时候让其变成固定定位
    (window.onscroll = function (){
      if(menuBarTop - getScrollTop() <= -30){
        menuBar.style.position = 'fixed';
        menuBar.style.top = '';
      }else{
        menuBar.style.position = '';
        menuBar.style.top = '';
      }
    })();
    function getScrollTop() {
      return document.documentElement.scrollTop || document.body.scrollTop || window.pageYOffset;
    }
  }
  render() {
    return (
      <div
        className='header-tool-wrap'
        ref='headerTool' style={{width:'100%'}}
      >
          <Header/>
      </div>
    )
  }
}

export default HeaderToolWrapper;