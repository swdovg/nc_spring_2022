import React, {useContext, useState, useEffect} from 'react';
import '../styles/main.css';
import '../styles/style.css';
import '../styles/bootstrap.min.css';
import Button from '../components/UI/button/Button';
import Select from '../components/UI/select/Select';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import Menu from '../components/UI/menu/Menu.jsx';
import Search from '../components/UI/search/Search.jsx';
import ProductCard from '../components/UI/productCard/ProductCard.jsx';
import CardList from '../components/UI/cardList/CardList.jsx';


const Main = () => {

    return (
        <div>
            <Header />
            <div className="container main-cont">
            <div className="row">
            <div className="col-xl-3 col-lg-3 d-sm-none d-none d-md-none d-lg-block">
                <h2 className="main_heading">Categories </h2>
            </div>
            <div className="col-xl-9 col-lg-9 col-sm-12 ">
                <Search/>
            </div>
            </div>
                <div className="row">
                    <div className="col-xl-3 col-lg-3 d-sm-none d-none d-md-none d-lg-block">
                        <Menu/>
                    </div>
                    <div className="col-xl-9 col-lg-9 main_cards">
                        <CardList />
                    </div>

                </div>
            </div>
            <Footer />

        </div>
        );
    };

export default Main;