import React, {useContext, useState, useEffect, useRef} from 'react';
import Button from "../button/Button";
import '../../../styles/bootstrap.min.css';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";
import ProductCard from '../productCard/ProductCard.jsx';
import {getPageCount} from "../../../utils/getPageCount";

const CardList = (props) => {

    const axiosPrivate = useAxiosPrivate();
    const [cardList, setCardList] = useState([]);
    const [page, setPage] = useState(0);
    const [isMounted, setIsMounted] = useState(false);
    const [totalsPages, setTotalPages] = useState(0);

     useEffect( () => {
        setIsMounted(true);
        let URL = `/api/v1/subscription`;
        if (props.selectedCategory!=null){
            URL = `api/v1/subscription/category/${props.selectedCategory}`;
        }
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getCards = async () => {
            try {
                let response = await axiosPrivate.get(URL,
                {
                    signal: controller.signal      //to allow to cansel a request
                });
                setCardList(response.data.payload?.content);
            } catch(err) {
                console.log(err);
            }
        }
        getCards();
        return () =>{
            setIsMounted(false);
            controller.abort();
        }
    }, [props.selectedCategory]);

     const filteredCards = cardList.filter( card => {
            return card.title.toLowerCase().includes(props.searchValue.toLowerCase())
    } );

    return (
    <>
        <div className="row">
             {filteredCards.map((card) =>
                <div className="col-xl-6 col-lg-6">
                    <ProductCard
                        key={card.id}
                        id={card.id}
                        price={card.price}
                        currency={card.currency}
                        title={card.title}
                        supplier={card.supplier.name}
                        description={card.description}
                        image={card.imageUrl}/>
                </div>)}
        </div>

        </>

    );
};

export default CardList;