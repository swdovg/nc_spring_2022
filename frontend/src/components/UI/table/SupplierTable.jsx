import React, {useEffect, useRef, useState} from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";
import Modal from '../modal/Modal.jsx';
import Button from '../button/Button.jsx';
import DeleteModal from '../deleteModal/DeleteModal.jsx';
import Subscription from './Subscription.jsx';
import OrderInfoTable from './OrderInfoTable.jsx';
import EditSubscriptionModal from '../editSubscriptionModal/EditSubscriptionModal.jsx';
import Cookies from 'js-cookie';


const SupplierTable = (props) => {

    const [subscriptions, setSubscriptions] = useState({});
    const [amount, setAmount] = useState(0);
    const axiosPrivate = useAxiosPrivate();

     useEffect( () => {

         let isMounted = true;
         const controller = new AbortController(); //to cansel request if the component on mounting

         const getSubscriptions = async () => {
             const URL = "api/v1/subscription/supplier";
            try {
                 const response = await axiosPrivate.get(URL, {
                     signal: controller.signal      //to allow to cansel a request
                 });
                 isMounted && setSubscriptions(response.data?.payload.content);
            } catch(err) {
                 console.log(err);
            }
         }
         getSubscriptions();

         return () =>{
             isMounted=false;
             controller.abort();
         }
     }, []);

    return (
    <>
        <table className="user_table col-xl-8 col-lg-8 col-md-12 col-sm-12 col-12">
            <thead>
                <tr className="table_heading">
                    <th className="table_heading_item col-xl-7 col-lg-7">service:</th>
                    <th className="table_heading_item col-xl-4 col-lg-4">price:</th>
                    {/* <th className="table_heading_item col-xl-3 col-lg-3">subscribers:</th> */}
                </tr>
            </thead>
            <tbody>
                {subscriptions.length>0
                ? (
                    <>
                        {subscriptions?.map((subscription, i) =>
                            <Subscription
                                key={i}
                                title={subscription.title}
                                price = {subscription.price}
                                currency = {subscription.currency}
                                id={subscription.id}
                                subscription={subscription}
                                category={subscription.category}
                                description={subscription.description}
                                //date = {(subscription.date).slice(8, 10)}
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

export default SupplierTable;