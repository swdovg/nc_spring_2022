import React from 'react';
import cl from './ProductCard.module.css';
import '../../../styles/bootstrap.min.css';
import Button from '../button/Button';
import card_img from '../../../img/profile_img.png';
import Cookies from 'js-cookie';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";

const ProductCard = (props) => {

    const role = JSON.parse(Cookies.get("user")).role;
    const axiosPrivate = useAxiosPrivate();

    const addSubscription = () => {
    console.log(props.id);
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

            const addSubscription = async () => {
                try {
                    const response = await axiosPrivate.post(`api/v1/subscription/${props.id}`, {
                        signal: controller.signal      //to allow to cansel a request
                    });
                } catch(err) {
                    console.log(err);
                }
            }
            addSubscription();
            return () =>{
                isMounted=false;
                controller.abort();
            }
    }

    return (
        <>
            <div className={cl.card}>
                <div>
                    <img src={card_img} alt="Profile Image" className={cl.card_img} />
                    <p className={cl.card_price}>{props.price} {props.currency}</p>
                </div>
                <div className={cl.card_info}>
                    <h3 className={cl.card_heading}>{props.title} </h3>
                    <p className={cl.card_producer}> {props.supplier}</p>
                    <p className={cl.card_description}> {props.description}</p>
                    {role==="ROLE_CONSUMER"
                        ?
                        <button  className={cl.card_btn} onClick= {addSubscription}> add</button>
                        :
                        <> </>
                    }
                </div>
            </div>
        </>
    );
};

export default ProductCard;