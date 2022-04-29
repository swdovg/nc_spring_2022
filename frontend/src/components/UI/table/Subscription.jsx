import React, {useEffect, useRef, useState} from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";
import Modal from '../modal/Modal.jsx';
import Button from '../button/Button.jsx';
import DeleteModal from '../deleteModal/DeleteModal.jsx';
import EditSubscriptionModal from '../editSubscriptionModal/EditSubscriptionModal.jsx';
import Cookies from 'js-cookie';


const Subscription = (props)=>{

    const [modalVisible, setDeleteModalVisible] = useState(false);
    const [editModalVisible, setEditModalVisible] = useState(false);
    const role = JSON.parse(Cookies.get("user")).role;
    const axiosPrivate = useAxiosPrivate();
    const [subscription, setSubscription] = useState({});

    const getSubscription=() => {

        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting
        const URL = `/api/v1/subscription/${props.id}`;
        const getSubscription = async () => {
            try {
                const response = await axiosPrivate.get(URL, {
                    signal: controller.signal      //to allow to cansel a request
                });
                setSubscription(response.data?.payload);
            } catch(err) {
                console.log(err);
            }
        }
        getSubscription();
        setEditModalVisible(true);
        console.log(subscription)
        return () =>{
            isMounted=false;
            controller.abort();
        }
    }

    return(
        <>
            <tr className="table_row">
                <td className="table_cont_item col-xl-8 col-lg-8">{props.title}</td>
                <td className="table_cont_item col-xl-4 col-lg-4">{props.price} {props.currency}
                    <button className="edit_btn" onClick={getSubscription}/>
                    <button className="remove_btn" onClick={()=>setDeleteModalVisible(true)}/>
                </td>
            </tr>
            <DeleteModal orderId={props.id} visible = {modalVisible} setVisible={setDeleteModalVisible}/>
            <EditSubscriptionModal
                id={props.id}
                visible = {editModalVisible}
                setVisible={setEditModalVisible}
                title={subscription.title}
                price={subscription.price}
                description={subscription.description}
                currency={subscription.currency}
                category={subscription.category}
            />
        </>
    )
};

export default Subscription;