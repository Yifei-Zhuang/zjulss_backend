import axios from 'axios';
import React, { Component } from 'react';
import { Menu, Search } from 'semantic-ui-react';
import './SearchBar.css';
class SearchBar extends Component {

    constructor(props){
        super(props);
        this.state = {
        isLoading: false,
        loadData: [],
        results: [],
        value: ''
        };
        this.handleResultSelect = this.handleResultSelect.bind(this);
        this.handleSearchChange = this.handleSearchChange.bind(this);
        this.handleSearching = this.handleSearching.bind(this);
        this.handleReachClear = this.handleReachClear.bind(this);
    }

    componentDidMount() {
        // axios
        // .get('/data/productData/database.json')
        // .then(res=>this.setState({loadData: res.data}))
        // .catch(err=>console.log(err))
        axios
        .get('http://10.214.241.122:8080/good/name?name=&page=0&sortByPrice=false&searchSort=false')
        .then(res=>this.setState({loadData: res.data}))
    }

    // 点击搜索结果现在在文本框内
    handleResultSelect(e){
        this.setState({ value: e.target.innerHTML })
    }

    handleSearchChange(e){
        this.setState({ isLoading: true, value: e.target.value })

        setTimeout(() => {
        // 文本框内容为空，返回
        if (this.state.value.length < 1) return;

        const regSearch = new RegExp(this.state.value,'gim');

        let resultMatch = new Array();
        const Val= window.location.pathname.match('[^/]+(?!.*/)');
        console.log(Val);
        if(Val === null){
            console.log(regSearch);
            this.state.loadData.forEach(
            function(item,index){
            if (item.name.search(regSearch) !== -1) resultMatch.push({title: item.name});
        })}
        else{
            console.log('!!!!!!!!!!!!!!!!!');
            const val = Val.input.split('/')[1];
            if(val === 'search'){
                this.state.loadData.forEach(
                function(item,index){
                if (item.name.search(regSearch) !== -1) resultMatch.push({title: item.name});
                })
            }
            else{
            const regSort = new RegExp(val,'gim');
            this.state.loadData.forEach(
            function(item,index){
            if (item.name.search(regSearch) !== -1 && item.sort !== regSort) resultMatch.push({title: item.name});
            })}
        }
        resultMatch = resultMatch.length <= 5?resultMatch:resultMatch.slice(0,5);

        this.setState({
            isLoading: false,
            results: resultMatch
        })

        }, 500)
    }

    handleSearching(e){
        if (e.keyCode !== 13 ) return;
        if (e.target.value.trim() === '') return;
        const Val= window.location.pathname.match('[^/]+(?!.*/)');
        console.log(Val);
        if(Val === null){
            window.location.href = '/search?' + e.target.value;}
        else{
            const val = Val.input.split('/')[1];
            if(val === 'search') window.location.href = '/search?' + e.target.value;
            else window.location.href = '/'+val+'/search?' + e.target.value;}
    }

    handleReachClear(e){
        this.setState({
        isLoading: false,
        results: [],
        value: ''
        })
    }

    render() {

        const {
        value,
        results,
        isLoading,
        } = this.state;

        const {
        handleResultSelect,
        handleSearchChange,
        handleSearching
        } = this;

        return (
        <Menu.Menu position='right'>
            <Search
            className='search-bar'
            placeholder='Search Products'
            loading={isLoading}
            onResultSelect={handleResultSelect}
            onSearchChange={handleSearchChange}
            onKeyDown={handleSearching}
            noResultsMessage=""
            showNoResults={false}
            // results={null}
            value={value}
            />
        </Menu.Menu>
        )
    }
}

export default SearchBar;