import React from 'react';
import Button from "../button/Button";
import '../../../styles/bootstrap.min.css';
import ProductCard from '../productCard/ProductCard.jsx';

const CardList = () => {
    return (
        <div className="row">
            <div className="col-xl-6 col-lg-6">
                <ProductCard />
            </div>
            <div className="col-xl-6 col-lg-6">
               <ProductCard />
            </div>
            <div className="col-xl-6 col-lg-6">
               <ProductCard />
            </div>
            <div className="col-xl-6 col-lg-6">
               <ProductCard />
            </div>
        </div>

    );
};

export default CardList;