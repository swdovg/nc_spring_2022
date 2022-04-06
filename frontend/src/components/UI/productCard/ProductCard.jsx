import React from 'react';
import cl from './ProductCard.module.css';
import '../../../styles/bootstrap.min.css';
import Button from '../button/Button';
import card_img from '../../../img/profile_img.png';

const ProductCard = () => {
    return (
        <div className={cl.card}>
            <img src={card_img} alt="Profile Image" className={cl.card_img} />
            <div className={cl.card_info}>
                <h3 className={cl.card_heading}>Spotify </h3>
                <p className={cl.card_producer}> By Spotify Music</p>
                <p className={cl.card_description}> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                <button className={cl.card_btn}> add</button>
            </div>
        </div>
    );
};

export default ProductCard;