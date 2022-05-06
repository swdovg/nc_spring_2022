import React, {useState, useEffect, useRef} from 'react';
import '../styles/main.css';
import '../styles/style.css';
import '../styles/bootstrap.min.css';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import Menu from '../components/UI/menu/Menu.jsx';
import Button from '../components/UI/button/Button.jsx';
import Search from '../components/UI/search/Search.jsx';
import CardList from '../components/UI/cardList/CardList.jsx';
import Pagination from '../components/UI/pagination/Pagination.jsx';


const Main = () => {

    const [selectedCategory, setSelectedCategory] = useState();
    const [search, setSearch] = useState("");

    const updateCategory = (value) => {
       setSelectedCategory(value);
    }

    const updateSearchInput = (value) => {
        setSearch(value);
    }

/*     useEffect( () => {
        if (isLoading) return;
        if(observer.current) observer.current.disconnect();

        var callback = function(entries, observer) {
            if (entries[0].isIntersecting){
                console.log(page);
                setPage(page+1);
            }
        };
        observer.current = new IntersectionObserver(callback);
        observer.current.observe(lastElement.current);

    }, [isLoading] ) */

    return (
        <div>
            <Header />
            <div className="container main-cont">
            <div className="row">
            <div className="col-xl-3 col-lg-3 d-sm-none d-none d-md-none d-lg-block">
                <h2 className="main_heading">Categories </h2>
            </div>
            <div className="col-xl-9 col-lg-9 col-sm-12 ">
                <Search onUpdateSearchInput={updateSearchInput}/>
            </div>
            </div>
                <div className="row">
                    <div className="col-xl-3 col-lg-3 d-sm-none d-none d-md-none d-lg-block">
                        <Menu updateCategory={updateCategory} />
                    </div>
                    <div className="col-xl-9 col-lg-9 main_cards">
                        <CardList
                            selectedCategory={selectedCategory}
                            searchValue={search}/>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
        );
    };

export default Main;