import React, {useContext, useState, useEffect} from 'react';
import Button from "../button/Button";
import '../../../styles/bootstrap.min.css';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";
import ProductCard from '../productCard/ProductCard.jsx';

const CardList = (props) => {

    const axiosPrivate = useAxiosPrivate();
    const [cardList, setCardList] = useState([]);

     useEffect( () => {
        let URL = "api/v1/subscription";
        if (props.selectedCategory!=null)
            URL = `api/v1/subscription/category/${props.selectedCategory}`;
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getCards = async () => {
            try {
                let response = await axiosPrivate.get(URL, {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setCardList(response.data.payload.content);
            } catch(err) {
                console.log(err);
            }
        }
        getCards();
        return () =>{
            isMounted=false;
            controller.abort();
        }
    }, [props.selectedCategory]);

    return (
        <div className="row">
             {cardList.map((card, i) =>
                <div className="col-xl-6 col-lg-6">
                    <ProductCard key={i} id={card.id} price={card.price} currency={card.currency} title={card.title} supplier={card.supplier.name} description={card.description}/>
                </div>)}
        </div>

    );
};

export default CardList;