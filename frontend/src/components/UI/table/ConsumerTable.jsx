import React, {useEffect, useRef, useState} from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";
import Modal from '../modal/Modal.jsx';
import Button from '../button/Button.jsx';
import DeleteModal from '../deleteModal/DeleteModal.jsx';
import Cookies from 'js-cookie';


const Subscription = (prop)=>{

    const [modalVisible, setModalVisible] = useState(false);
    const [isChanged, setIsChanged] = useState(false);

    useEffect( () => {
        if(isChanged) {
            prop.isChanged(isChanged);
            console.log("aaaa");
        }
    }, [isChanged] );

    return(
        <>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">{prop.title}</td>
                <td className="table_cont_item col-xl-3 col-lg-3">{prop.price} {prop.currency}</td>
                <td className="table_cont_item col-xl-3 col-lg-3">
                    {prop.date}th
                    <button className="remove_btn" onClick={()=>setModalVisible(true)}/>
                </td>
            </tr>
            <DeleteModal orderId={prop.orderId} visible = {modalVisible} setVisible={setModalVisible} deleted = {(value)=>setIsChanged(true)}/>
        </>
    )
};

const ConsumerTable = (props) => {

    const [subscriptions, setSubscriptions] = useState([]);
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();
    var amount = 0;
    const [number, setNumber] = useState(0);
    const [isChanged, setIsChanged] = useState(false);

    const updateAmount = ()=> {
        {subscriptions.length>0
        ?
            subscriptions.map((subscription) => {
                amount = amount+subscription.subscription.price;
            })
        :
            amount =0;
        }
        props.updateAmount(amount);
        console.log(amount);
    }

     useEffect( () => {

         let isMounted = true;
         const controller = new AbortController(); //to cansel request if the component on mounting

         const getSubscriptions = async () => {
             const URL = "api/v1/order";
            try {
                 const response = await axiosPrivate.get(URL, {
                     signal: controller.signal      //to allow to cansel a request
                 });
                 isMounted && setSubscriptions(response.data?.payload.content);

                 setNumber(subscriptions.length);
                 props.updateNumber(number);
            } catch(err) {
                 console.log(err);
            }
         }
         getSubscriptions();

         return () =>{
             isMounted=false;
             controller.abort();
         }
     },[number, isChanged]);

    return (
    <>
        <table className="user_table col-xl-8 col-lg-8 col-md-12 col-sm-12 col-12">
            <thead>
                <tr className="table_heading">
                    <th className="table_heading_item col-xl-6 col-lg-6">service:</th>
                    <th className="table_heading_item col-xl-3 col-lg-3">price:</th>
                    <th className="table_heading_item col-xl-3 col-lg-3">date of payment:</th>
                </tr>
            </thead>
            <tbody>
                {subscriptions.length>0
                ? (
                    <>
                        {subscriptions?.map((subscription, i) =>
                            <Subscription
                                key={i}
                                title={subscription.subscription.title}
                                price = {subscription.subscription.price}
                                currency = {subscription.subscription.currency}
                                orderId={subscription.orderId}
                                date = {(subscription.date).slice(8, 10)}
                                isChanged = {(value) => setIsChanged(true)}
                            />
                        )}
                    </>
                ) : <tr><td></td></tr>
            }
            </tbody>
        </table>

        </>
    )
};

export default ConsumerTable;