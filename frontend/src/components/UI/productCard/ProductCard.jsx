import React from 'react';
import cl from './ProductCard.module.css';
import '../../../styles/bootstrap.min.css';
import Button from '../button/Button';
import card_img from '../../../img/profile_img.png';

const ProductCard = (props) => {
    return (
        <div className={cl.card}>
            <div>
                <img src={card_img} alt="Profile Image" className={cl.card_img} />
                <p className={cl.card_price}>{props.price}</p>
            </div>
            <div className={cl.card_info}>
                <h3 className={cl.card_heading}>{props.title} </h3>
                <p className={cl.card_producer}> {props.supplier}</p>
                <p className={cl.card_description}> {props.description}</p>

                <button  className={cl.card_btn}> add</button>
            </div>
        </div>
    );
};

export default ProductCard;